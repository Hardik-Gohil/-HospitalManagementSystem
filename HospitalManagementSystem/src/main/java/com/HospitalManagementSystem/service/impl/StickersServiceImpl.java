package com.HospitalManagementSystem.service.impl;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections4.ListUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import com.HospitalManagementSystem.entity.AdHocOrder;
import com.HospitalManagementSystem.entity.AdHocOrderItems;
import com.HospitalManagementSystem.entity.DietPlan;
import com.HospitalManagementSystem.entity.Patient;
import com.HospitalManagementSystem.entity.master.ServiceItems;
import com.HospitalManagementSystem.repository.AdHocOrderRepository;
import com.HospitalManagementSystem.repository.DietPlanRepository;
import com.HospitalManagementSystem.repository.PatientRepository;
import com.HospitalManagementSystem.repository.ServiceMasterRepository;
import com.HospitalManagementSystem.service.StickersService;
import com.HospitalManagementSystem.utility.CommonUtility;
import com.HospitalManagementSystem.utility.DietUtility;

import net.sf.jasperreports.engine.JREmptyDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleOutputStreamExporterOutput;

@Service
public class StickersServiceImpl implements StickersService {

	@Autowired
	private PatientRepository patientRepository;
	@Autowired
	private ServiceMasterRepository serviceMasterRepository;
	@Autowired
	private DietPlanRepository dietPlanRepository;
	@Autowired
	private AdHocOrderRepository adHocOrderRepository;
	
	@Autowired
	private DietUtility dietUtility;

	private static final String BACKSLASH = " / ";
	private static final String PIPE = " | ";
	private static final String BRTAG = "<br/>";
	private static final List<Long> dietTypeSolid = List.of(1l, 2l, 3l, 5l, 7l, 8l);
	private static final List<Long> extraLiquid = List.of(4l, 6l);
	private static final List<Long> dietTypeLiquidOralTF = List.of(9l, 10l, 11l, 12l, 13l, 14l, 15l, 16l, 17l, 18l, 19l, 20l, 21l, 22l, 23l, 24l, 25l, 26l, 27l) ;
	
	@Override
	public String stickers(Model model, Long patientId) {
		if (ObjectUtils.isNotEmpty(patientId)) {
			Patient patient = patientRepository.findById(patientId).get();
			model.addAttribute("patient", patient);
			model.addAttribute("patientId", patientId);
			model.addAttribute("serviceMasterList", serviceMasterRepository.findAll(dietUtility.getServiceMasterSpecification(patient, null)));
			model.addAttribute("localDateFormatter", CommonUtility.localDateFormatter);
			model.addAttribute("localDateTimeFormatter", CommonUtility.localDateTimeFormatter);
		} else {
			model.addAttribute("serviceMasterList", serviceMasterRepository.findAllByIsActive(Boolean.TRUE));
		}
		return "diet/Stickers";
	}

	private ResponseEntity<Resource> generateStickers(List<String> stickersList) throws JRException {
		List<JasperPrint> jasperPrints = new ArrayList<JasperPrint>();
		InputStream jasperInput = ExportServiceImpl.class.getResourceAsStream("/" + "jasper/StickersTemplate1.jrxml");
		JasperDesign jasperDesign = JRXmlLoader.load(jasperInput);
		JasperReport jasperReport = JasperCompileManager.compileReport(jasperDesign);			
		if (CollectionUtils.isNotEmpty(stickersList)) {
//			List<List<String>> lists = ListUtils.partition(stickersList, 18);
//			for (List<String> list : lists) {
//				Map<String, Object> parameters = new HashMap<String, Object>();
//				int index = 1;
//				for (String stickersData : list) {
//					parameters.put("stickersData_" + index++, stickersData);
//				}
//				JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, new JREmptyDataSource());
//				jasperPrints.add(jasperPrint);
//			}
			for (String stickersData : stickersList) {
				Map<String, Object> parameters = new HashMap<String, Object>();
				parameters.put("stickersData", stickersData);
				JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, new JREmptyDataSource());
				jasperPrints.add(jasperPrint);
			}
		} else {
			Map<String, Object> parameters = new HashMap<String, Object>();
			JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, new JREmptyDataSource());
			jasperPrints.add(jasperPrint);
		}
		
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		JRPdfExporter exporter = new JRPdfExporter();
		exporter.setExporterInput(SimpleExporterInput.getInstance(jasperPrints));
		exporter.setExporterOutput(new SimpleOutputStreamExporterOutput(out));
		exporter.exportReport();
		ByteArrayResource resource = new ByteArrayResource(out.toByteArray());
		return ResponseEntity.ok().contentType(MediaType.APPLICATION_PDF).body(resource);
	}
	
	@Override
	public ResponseEntity<Resource> generateStickers(Long serviceMasterId, Long patientId) {
		try {
			List<String> stickersList = new ArrayList<String>(); 
			List<DietPlan> dietPlanList = new ArrayList<>();
			LocalDate stickersDate = serviceMasterId == 27l ? LocalDate.now().plusDays(1) : LocalDate.now();
			if (ObjectUtils.isEmpty(patientId)) {
				dietPlanList = dietPlanRepository.findAllByServiceMasterServiceMasterIdAndDietDate(serviceMasterId, LocalDate.now());
			} else {
				dietPlanList = dietPlanRepository.findAllByServiceMasterServiceMasterIdAndPatientPatientIdAndDietDate(serviceMasterId, patientId, LocalDate.now());
			}
			
			if (CollectionUtils.isNotEmpty(dietPlanList)) {
				for (DietPlan dietPlan : dietPlanList) {
					if (dietPlan.getIsPaused()) {
						continue;
					}
					StringBuilder stickers = new StringBuilder(1000);
					stickers.append(dietPlan.getServiceMaster().getService() + BACKSLASH + dietPlan.getServiceMaster().getTimeStr() + BACKSLASH + stickersDate.format(CommonUtility.localDateFormatterSticker) + BRTAG);
					stickers.append(dietPlan.getPatient().getIpNumber() + BACKSLASH + "<b>" + dietPlan.getPatient().getBed().getBedCode() + "</b>" + BRTAG);
					stickers.append("<b>" + dietPlan.getPatient().getPatientName()+ "</b>" + BRTAG);
					if(dietTypeSolid.contains(serviceMasterId)) {
						stickers.append(dietPlan.getPatient().getDietTypeOralSolid().getValue() + BACKSLASH + dietPlan.getPatient().getMedicalComorbiditiesString() + BRTAG);
					}
					if (extraLiquid.contains(serviceMasterId)) {
						stickers.append(dietPlan.getPatient().getDietTypeOralSolid().getValue() + BACKSLASH + dietPlan.getPatient().getMedicalComorbiditiesString() + BACKSLASH + dietPlan.getItem() + BRTAG);
					}
					if(dietTypeLiquidOralTF.contains(serviceMasterId)) {
						String proteinCalories = "";
						if (CollectionUtils.isNotEmpty(dietPlan.getServiceItems()) && dietPlan.getServiceItems().size() == 1
								&& StringUtils.equalsAnyIgnoreCase(StringUtils.stripToEmpty(dietPlan.getItem()), StringUtils.stripToEmpty(dietPlan.getOriginalItem()))) {
							ServiceItems serviceItems = dietPlan.getServiceItems().get(0);
							if (ObjectUtils.isNotEmpty(serviceItems.getCalories()) && ObjectUtils.isNotEmpty(serviceItems.getProtien())) {
								BigDecimal multiplicand = new BigDecimal(dietPlan.getPatient().getQuantity().getValue() / 5);
								proteinCalories = "P:" + serviceItems.getProtien().multiply(multiplicand).setScale(2, RoundingMode.HALF_EVEN) + " gms ";	
								proteinCalories += "C:" + serviceItems.getCalories().multiply(multiplicand).setScale(2, RoundingMode.HALF_EVEN) + " kcal " + PIPE;	
							}
						}
						
						stickers.append(dietPlan.getPatient().getDietTypeOralLiquidTF().getValue() + BACKSLASH
								+ dietPlan.getPatient().getDietSubType().getValue() + BACKSLASH
								+ dietPlan.getPatient().getMedicalComorbiditiesString() + BACKSLASH + dietPlan.getItem()
								+ PIPE  + proteinCalories + dietPlan.getPatient().getQuantity().getValueStr() + BACKSLASH
								+ dietPlan.getPatient().getFrequency().getValueStr() + BRTAG);
					}
					if (CollectionUtils.isNotEmpty(dietPlan.getDietInstructions())) {
						stickers.append(dietPlan.getDietInstructions().stream().map(di -> di.getInstruction()).collect(Collectors.joining(", ")));
						if (StringUtils.isNotEmpty(dietPlan.getPatient().getSpecialNotesByNursingString())) {
							stickers.append(PIPE + dietPlan.getPatient().getSpecialNotesByNursingString());
						}
					} else if (StringUtils.isNotEmpty(dietPlan.getPatient().getSpecialNotesByNursingString())) {
						stickers.append(dietPlan.getPatient().getSpecialNotesByNursingString());
					}
					stickersList.add(stickers.toString());
				}
			}
			return generateStickers(stickersList);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public ResponseEntity<Resource> generateAdhocOrderStickers(Long adHocOrderId) {
		try {
			List<String> stickersList = new ArrayList<String>();
			Optional<AdHocOrder> adHocOrderOptional = adHocOrderRepository.findById(adHocOrderId);
//			LocalDate stickersDate = LocalDate.now();
			if (adHocOrderOptional.isPresent()) {
				AdHocOrder adHocOrder = adHocOrderOptional.get();
				StringBuilder stickers = new StringBuilder(1000);
				stickers.append(adHocOrder.getOrderId() + BACKSLASH + adHocOrder.getServiceDeliveryDateTime().format(CommonUtility.localDateTimeFormatter) + BRTAG);
				stickers.append(adHocOrder.getPatient().getIpNumber() + BACKSLASH + "<b>" + adHocOrder.getPatient().getBed().getBedCode() + "</b>" + BRTAG);
				stickers.append("<b>" + adHocOrder.getPatient().getPatientName() + "</b>" + BRTAG);
				if (ObjectUtils.isNotEmpty(adHocOrder.getPatient().getDietTypeOralSolid())) {
					stickers.append(adHocOrder.getPatient().getDietTypeOralSolid().getValue() + BACKSLASH);
				}
				if (ObjectUtils.isNotEmpty(adHocOrder.getPatient().getDietTypeOralLiquidTF())) {
					stickers.append(adHocOrder.getPatient().getDietTypeOralLiquidTF().getValue() + BACKSLASH);
				}
				if (ObjectUtils.isNotEmpty(adHocOrder.getPatient().getDietSubType())) {
					stickers.append(adHocOrder.getPatient().getDietSubType().getValue() + BACKSLASH);
				}
				stickers.append(adHocOrder.getPatient().getMedicalComorbiditiesString() + BRTAG);

				if (CollectionUtils.isNotEmpty(adHocOrder.getAdHocOrderItems())) {
					for (AdHocOrderItems adHocOrderItems : adHocOrder.getAdHocOrderItems()) {
						stickersList.add(stickers.toString() + adHocOrderItems.getAdHocItems().getItemName() + " (" + adHocOrderItems.getQuantity() + ")" + BRTAG + adHocOrder.getRemarks());
					}
				} else {
					stickersList.add(stickers.toString() + adHocOrder.getRemarks());
				}
			}
			return generateStickers(stickersList);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

}