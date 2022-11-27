package com.HospitalManagementSystem.service.impl;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.persistence.criteria.Join;
import javax.persistence.criteria.Predicate;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.data.jpa.datatables.mapping.DataTablesInput;
import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;
import org.springframework.data.jpa.datatables.mapping.Search;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.HospitalManagementSystem.dto.AdHocItemsDto;
import com.HospitalManagementSystem.dto.AdHocOrderDto;
import com.HospitalManagementSystem.dto.AdHocOrderItemsDto;
import com.HospitalManagementSystem.dto.AdHocSearchDto;
import com.HospitalManagementSystem.dto.PatientDto;
import com.HospitalManagementSystem.entity.AdHocOrder;
import com.HospitalManagementSystem.entity.AdHocOrderItems;
import com.HospitalManagementSystem.entity.Patient;
import com.HospitalManagementSystem.entity.User;
import com.HospitalManagementSystem.entity.master.MedicalComorbidities;
import com.HospitalManagementSystem.repository.AdHocItemsRepository;
import com.HospitalManagementSystem.repository.AdHocOrderDataTablesRepository;
import com.HospitalManagementSystem.repository.AdHocOrderItemsRepository;
import com.HospitalManagementSystem.repository.AdHocOrderRepository;
import com.HospitalManagementSystem.repository.PatientRepository;
import com.HospitalManagementSystem.service.AdHocOrderService;
import com.HospitalManagementSystem.service.NotificationsService;
import com.HospitalManagementSystem.utility.CommonUtility;
import com.HospitalManagementSystem.utility.DietUtility;

import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleOutputStreamExporterOutput;

@Service
public class AdHocOrderServiceImpl implements AdHocOrderService {

	@Autowired
	private PatientRepository patientRepository;
	@Autowired
	private AdHocItemsRepository adHocItemsRepository;
	@Autowired
	private AdHocOrderRepository adHocOrderRepository;
	@Autowired
	private AdHocOrderItemsRepository adHocOrderItemsRepository;
	@Autowired
	private AdHocOrderDataTablesRepository adHocOrderDataTablesRepository;

	@Autowired
	private ModelMapper modelMapper;
	@Autowired
	private CommonUtility commonUtility;
	@Autowired
	private DietUtility dietUtility;

	@Autowired
	private NotificationsService notificationsService;
	
	
	@Override
	@Transactional
	public AdHocOrder save(AdHocOrder adHocOrder) {
		adHocOrder.setPatient(patientRepository.findById(adHocOrder.getPatient().getPatientId()).get());
		if (CollectionUtils.isNotEmpty(adHocOrder.getAdHocOrderItems())) {
			List<AdHocOrderItems> adHocOrderItems = adHocOrderItemsRepository.saveAll(adHocOrder.getAdHocOrderItems());
			adHocOrder.setAdHocOrderItems(adHocOrderItems);
		}
		adHocOrder = adHocOrderRepository.save(adHocOrder);
		if (StringUtils.isEmpty(adHocOrder.getOrderId())) {
			adHocOrder.setOrderId("#" + String.format("%07d", adHocOrder.getAdHocOrderId()));
		}
		adHocOrder = adHocOrderRepository.save(adHocOrder);
		return adHocOrder;
	}

	@Override
	public String getAdHocOrder(Long patientId, Boolean immediateService, Model model) {
		Patient patient = patientRepository.findById(patientId).get();
		PatientDto patientDto = new PatientDto();
		AdHocOrderDto adHocOrderDto = new AdHocOrderDto();
		patientDto.setPatientId(patientId);
		adHocOrderDto.setPatient(patientDto);
		model.addAttribute("adHocOrderDto", adHocOrderDto);
		model.addAttribute("patient", patient);
		model.addAttribute("immediateService", ObjectUtils.isNotEmpty(immediateService) ? immediateService : Boolean.FALSE);
		model.addAttribute("adHocItemsList", adHocItemsRepository.findAll(dietUtility.getAdHocItemsSpecification(patient)));
		model.addAttribute("localDateFormatter", CommonUtility.localDateFormatter);
		model.addAttribute("localDateTimeFormatter", CommonUtility.localDateTimeFormatter);
		return "diet/AdHocOrder";
	}

	@Override
	public String saveAdHocOrder(RedirectAttributes redir, AdHocOrderDto adHocOrderDto, String adHocItemsIds, String quantities) {
		boolean isValid = true;
		Patient patient = patientRepository.findById(adHocOrderDto.getPatient().getPatientId()).get();
		if (patient.getPatientStatus() == 2) {
			isValid = false;
			redir.addFlashAttribute("errorMsg", "Patien has been discharged");
		}
		if (!isValid) {
			return "redirect:/diet/patients";
		}	
		LocalDateTime now = LocalDateTime.now();
		User currentUser = commonUtility.getCurrentUser();
		
		if(adHocOrderDto.getServiceType() == 2 && StringUtils.isNotEmpty(adHocItemsIds)) {
			List<AdHocOrderItemsDto> adHocOrderItems = new ArrayList<>();
			String[] ids = adHocItemsIds.split(",");
			String[] qtys = quantities.split(",");
			
			for (int i = 0; i < ids.length; i++) {
				AdHocOrderItemsDto adHocOrderItemsDto = new AdHocOrderItemsDto();
				AdHocItemsDto adHocItems = new AdHocItemsDto();
				adHocItems.setAdHocItemsId(Long.valueOf(ids[i]));
				adHocOrderItemsDto.setAdHocItems(adHocItems);
				adHocOrderItemsDto.setQuantity(Integer.parseInt(qtys[i]));
				adHocOrderItems.add(adHocOrderItemsDto);
			}
			adHocOrderDto.setAdHocOrderItems(adHocOrderItems);
		}
		
		AdHocOrder saveAdHocOrder = modelMapper.map(adHocOrderDto, AdHocOrder.class);
		saveAdHocOrder.setServiceDeliveryDateTime(adHocOrderDto.getServiceDeliveryDateTime());
		saveAdHocOrder.setOrderStatus(1);
		saveAdHocOrder.setCreatedOn(now);
		saveAdHocOrder.setCreatedBy(currentUser.getUserId());
		saveAdHocOrder.setModifiedOn(now);
		saveAdHocOrder.setModifiedBy(currentUser.getUserId());
		saveAdHocOrder.setCreatedUserHistoryId(currentUser.getCurrenUserHistoryId());
		saveAdHocOrder.setModifiedUserHistoryId(currentUser.getCurrenUserHistoryId());
		saveAdHocOrder = save(saveAdHocOrder);
		notificationsService.sendAdHocOrder(saveAdHocOrder.getAdHocOrderId());
		return "redirect:/diet/adhoc-order?patientId=" + adHocOrderDto.getPatient().getPatientId();
	}

	@Override
	public List<AdHocOrder> getAdHocOrderData(Long patientId) {
		return adHocOrderRepository.findAllByPatientPatientId(patientId);
	}

	@Override
	public String deleteAdHocOrder(RedirectAttributes redir, Long adHocOrderId) {
		boolean isValid = true;
		LocalDateTime now = LocalDateTime.now();
		User currentUser = commonUtility.getCurrentUser();
		Optional<AdHocOrder> adHocOrder = adHocOrderRepository.findById(adHocOrderId);
		Long patientId = adHocOrder.get().getPatient().getPatientId();
		AdHocOrder saveAdHocOrder = adHocOrder.get();
		
		if (now.isAfter(saveAdHocOrder.getServiceDeliveryDateTime())) {
			isValid = false;
			redir.addFlashAttribute("errorMsg", "You can not Delete the Oder whose Service Delivery Date Time lapsed");
		}
		
		if (isValid) {
			saveAdHocOrder.setOrderStatus(3);
			saveAdHocOrder.setModifiedOn(now);
			saveAdHocOrder.setModifiedBy(currentUser.getUserId());
			saveAdHocOrder.setModifiedUserHistoryId(currentUser.getCurrenUserHistoryId());
			saveAdHocOrder = save(saveAdHocOrder);
			notificationsService.sendAdHocOrder(saveAdHocOrder.getAdHocOrderId());
		}
		
		return "redirect:/diet/adhoc-order?patientId=" + patientId;
	}

	@Override
	public ResponseEntity<String> cancelAdHocOrder(Long adHocOrderId) {
		boolean isValid = true;
		LocalDateTime now = LocalDateTime.now();
		User currentUser = commonUtility.getCurrentUser();
		Optional<AdHocOrder> adHocOrder = adHocOrderRepository.findById(adHocOrderId);
		if (adHocOrder.isPresent()) {
			AdHocOrder adHocOrderEntity = adHocOrder.get();
			if (now.isAfter(adHocOrderEntity.getServiceDeliveryDateTime())) {
				isValid = false;
				return ResponseEntity.ok().body("You can not cancelled the Oder whose Service Delivery Date Time lapsed");
			}
			
			if (isValid) {
				adHocOrderEntity.setOrderStatus(2);
				adHocOrderEntity.setModifiedOn(LocalDateTime.now());
				adHocOrderEntity.setModifiedBy(currentUser.getUserId());
				adHocOrderEntity.setModifiedUserHistoryId(currentUser.getCurrenUserHistoryId());
				adHocOrderEntity = save(adHocOrderEntity);
				notificationsService.sendAdHocOrder(adHocOrderEntity.getAdHocOrderId());
			}
			return ResponseEntity.ok().body("Order has been updated");
		}
		return ResponseEntity.badRequest().body("Order not found");		
	}
	
	@Override
	public ResponseEntity<Resource> adhocOrderKOT(Long adHocOrderId) {
		try {
			Optional<AdHocOrder> adHocOrderOptional = adHocOrderRepository.findById(adHocOrderId);
			AdHocOrder adHocOrder = adHocOrderOptional.get();
			Map<String, Object> parameters = new HashMap<String, Object>();
			parameters.put("localDateTimeFormatter", CommonUtility.localDateTimeFormatter);
			parameters.put("printedOn", LocalDateTime.now().format(CommonUtility.localDateTimeFormatter));
			parameters.put("tableData", new JRBeanCollectionDataSource(adHocOrder.getAdHocOrderItems()));

			InputStream jasperInput = ExportServiceImpl.class.getResourceAsStream("/" + "jasper/"+ (adHocOrder.getServiceType() == 1 ? "KOT_ImmediateService" : "KOT_AdHocService") + ".jrxml");
			JasperDesign jasperDesign = JRXmlLoader.load(jasperInput);
			JasperReport jasperReport = JasperCompileManager.compileReport(jasperDesign);
			List<JasperPrint> jasperPrints = new ArrayList<JasperPrint>();
			List<Object> fieldList = new ArrayList<Object>();
			fieldList.add(adHocOrder);
			JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, new JRBeanCollectionDataSource(fieldList));
			jasperPrints.add(jasperPrint);
			
			ByteArrayOutputStream out = new ByteArrayOutputStream();
			JRPdfExporter exporter = new JRPdfExporter();
			exporter.setExporterInput(SimpleExporterInput.getInstance(jasperPrints));
			exporter.setExporterOutput(new SimpleOutputStreamExporterOutput(out));
			exporter.exportReport();
			ByteArrayResource resource = new ByteArrayResource(out.toByteArray());
			return ResponseEntity.ok().contentType(MediaType.APPLICATION_PDF).body(resource);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public DataTablesOutput<AdHocOrder> getAdhocOrderListing(AdHocSearchDto adHocSearchDto) {
		DataTablesInput input = adHocSearchDto;
		input.addColumn("patient.bed.bedCode", true, true, null);
		input.addColumn("patient.bed.wardName", true, true, null);
		input.addColumn("patient.bed.floor.floorName", true, true, null);
		input.setSearch(new Search(adHocSearchDto.getSearchText(), false));
		
		Specification<AdHocOrder> additionalSpecification = (root, query, criteriaBuilder) -> {
			List<Predicate> predicates = new ArrayList<>();
			if (CollectionUtils.isNotEmpty(adHocSearchDto.getServiceType())) {
				predicates.add(root.get("serviceType").in(adHocSearchDto.getServiceType()));
			}
			if (CollectionUtils.isNotEmpty(adHocSearchDto.getMedicalComorbiditiesIds())) {
				Join<Patient, AdHocOrder> patientJoin = root.join("patient");
				Join<MedicalComorbidities, Patient> medicalComorbiditiesJoin = patientJoin.join("medicalComorbidities");
				predicates.add(medicalComorbiditiesJoin.get("medicalComorbiditiesId").in(adHocSearchDto.getMedicalComorbiditiesIds()));
			}
			if (CollectionUtils.isNotEmpty(adHocSearchDto.getFloorIds())) {
				predicates.add(root.get("patient").get("bed").get("floor").get("floorId").in(adHocSearchDto.getFloorIds()));
			}
			if (CollectionUtils.isNotEmpty(adHocSearchDto.getBedIds())) {
				predicates.add(root.get("patient").get("bed").get("bedId").in(adHocSearchDto.getBedIds()));
			}
			if (CollectionUtils.isNotEmpty(adHocSearchDto.getDietTypeOralSolidIds())) {
				predicates.add(root.get("patient").get("dietTypeOralSolid").get("dietTypeOralSolidId").in(adHocSearchDto.getDietTypeOralSolidIds()));
			}
			if (CollectionUtils.isNotEmpty(adHocSearchDto.getDietTypeOralLiquidTFIds())) {
				predicates.add(root.get("patient").get("dietTypeOralLiquidTF").get("dietTypeOralLiquidTFId").in(adHocSearchDto.getDietTypeOralLiquidTFIds()));
			}
			if (CollectionUtils.isNotEmpty(adHocSearchDto.getDietSubTypeIds())) {
				predicates.add(root.get("patient").get("dietSubType").get("dietSubTypeId").in(adHocSearchDto.getDietSubTypeIds()));
			}
			if (adHocSearchDto.getIsVip()) {
				predicates.add(criteriaBuilder.equal(root.get("patient").get("isVip"), adHocSearchDto.getIsVip()));
			}
			if (StringUtils.isNotEmpty(adHocSearchDto.getOrderPlacedDateAndTime())) {
				String[] dates = adHocSearchDto.getOrderPlacedDateAndTime().split(" - ");
				predicates.add(criteriaBuilder.between(root.get("createdOn"),
						LocalDateTime.parse(dates[0], CommonUtility.localDateTimeFormatter),
						LocalDateTime.parse(dates[1], CommonUtility.localDateTimeFormatter)));
			}
			if (CollectionUtils.isNotEmpty(adHocSearchDto.getDelivered()) && adHocSearchDto.getDelivered().size() == 1) {
				predicates.add(criteriaBuilder.equal(root.get("chargable"), adHocSearchDto.getDelivered().get(0) == 1));
			}
			if (CollectionUtils.isNotEmpty(adHocSearchDto.getStatusList())) {
				predicates.add(root.get("orderStatus").in(adHocSearchDto.getStatusList()));
			}
			return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
		};
		
		return adHocOrderDataTablesRepository.findAll(input, additionalSpecification);
	}

	@Override
	public ResponseEntity<String> chargableAdHocOrder(Long adHocOrderId, Boolean chargable) {
		User currentUser = commonUtility.getCurrentUser();
		Optional<AdHocOrder> adHocOrder = adHocOrderRepository.findById(adHocOrderId);
		if (adHocOrder.isPresent()) {
			AdHocOrder adHocOrderEntity = adHocOrder.get();
			adHocOrderEntity.setChargable(chargable);
			adHocOrderEntity.setModifiedOn(LocalDateTime.now());
			adHocOrderEntity.setModifiedBy(currentUser.getUserId());
			adHocOrderEntity.setModifiedUserHistoryId(currentUser.getCurrenUserHistoryId());
			save(adHocOrderEntity);
			return ResponseEntity.ok().body("Order has been updated");
		}
		return ResponseEntity.badRequest().body("Order not found");
	}

}
