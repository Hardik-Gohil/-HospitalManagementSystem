package com.HospitalManagementSystem.service.impl;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import javax.persistence.criteria.Predicate;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.datatables.mapping.DataTablesInput;
import org.springframework.data.jpa.datatables.mapping.Order;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.HospitalManagementSystem.HttpReqRespUtils;
import com.HospitalManagementSystem.dto.DiagonosisDto;
import com.HospitalManagementSystem.dto.MedicalComorbiditiesDto;
import com.HospitalManagementSystem.dto.PatientDataTablesOutputDto;
import com.HospitalManagementSystem.dto.PatientDto;
import com.HospitalManagementSystem.dto.SpecialNotesByNursingDto;
import com.HospitalManagementSystem.entity.Patient;
import com.HospitalManagementSystem.entity.User;
import com.HospitalManagementSystem.entity.history.PatientHistory;
import com.HospitalManagementSystem.entity.master.Diagonosis;
import com.HospitalManagementSystem.repository.BedRepository;
import com.HospitalManagementSystem.repository.DiagonosisRepository;
import com.HospitalManagementSystem.repository.DietSubTypeRepository;
import com.HospitalManagementSystem.repository.DietTypeOralLiquidTFRepository;
import com.HospitalManagementSystem.repository.DietTypeOralSolidRepository;
import com.HospitalManagementSystem.repository.FrequencyRepository;
import com.HospitalManagementSystem.repository.MedicalComorbiditiesRepository;
import com.HospitalManagementSystem.repository.PatientDataTablesRepository;
import com.HospitalManagementSystem.repository.PatientHistoryRepository;
import com.HospitalManagementSystem.repository.PatientRepository;
import com.HospitalManagementSystem.repository.QuantityRepository;
import com.HospitalManagementSystem.repository.SpecialNotesByNursingRepository;
import com.HospitalManagementSystem.service.DietPlanService;
import com.HospitalManagementSystem.service.NotificationsService;
import com.HospitalManagementSystem.service.PatientDetailsService;
import com.HospitalManagementSystem.utility.CommonUtility;
import com.HospitalManagementSystem.utility.NotificationsUtility;

@Service
public class PatientDetailsServiceImpl implements PatientDetailsService {

	@Autowired
	private PatientRepository patientRepository;
	@Autowired
	private PatientHistoryRepository patientHistoryRepository;
	@Autowired
	private BedRepository bedRepository;
	@Autowired
	private PatientDataTablesRepository patientDataTablesRepository;
	@Autowired
	private DietTypeOralSolidRepository dietTypeOralSolidRepository;
	@Autowired
	private DietTypeOralLiquidTFRepository dietTypeOralLiquidTFRepository;
	@Autowired
	private DietSubTypeRepository dietSubTypeRepository;
	@Autowired
	private QuantityRepository quantityRepository;
	@Autowired
	private FrequencyRepository frequencyRepository;
	@Autowired
	private MedicalComorbiditiesRepository medicalComorbiditiesRepository;
	@Autowired
	private DiagonosisRepository diagonosisRepository;
	@Autowired
	private SpecialNotesByNursingRepository specialNotesByNursingRepository;	
	
	@Autowired
	private ModelMapper modelMapper;
	@Autowired
	private CommonUtility commonUtility;
	@Autowired
	private NotificationsUtility notificationsUtility;
	
	@Autowired
	private DietPlanService dietPlanService;
	@Autowired
	private NotificationsService notificationsService;
	
	@Override
	public Patient save(Patient patient) {
		fillPatientData(patient);
		patient.setIpAddress(HttpReqRespUtils.getClientIpAddressIfServletRequestExist());
		patient = patientRepository.save(patient);
		PatientHistory patientHistory = modelMapper.map(patient, PatientHistory.class);
		patientHistory.setHistoryCreatedOn(patient.getModifiedOn());
		patientHistory.setHistoryCreatedBy(patient.getModifiedBy());
		patientHistory = patientHistoryRepository.save(patientHistory);
		return patient;
	}
	
	private Patient fillPatientData(Patient patient) {
		if(ObjectUtils.isEmpty(patient.getBed()) || (ObjectUtils.isNotEmpty(patient.getBed()) && ObjectUtils.isEmpty(patient.getBed().getBedId()))) {
			patient.setBed(null);
		} else {
			patient.setBed(bedRepository.findById(patient.getBed().getBedId()).get());
		}
		
		if(ObjectUtils.isEmpty(patient.getDietTypeOralSolid()) || (ObjectUtils.isNotEmpty(patient.getDietTypeOralSolid()) && ObjectUtils.isEmpty(patient.getDietTypeOralSolid().getDietTypeOralSolidId()))) {
			patient.setDietTypeOralSolid(null);
		} else {
			patient.setDietTypeOralSolid(dietTypeOralSolidRepository.findById(patient.getDietTypeOralSolid().getDietTypeOralSolidId()).get());
		}
		
		if(ObjectUtils.isEmpty(patient.getDietTypeOralLiquidTF()) || (ObjectUtils.isNotEmpty(patient.getDietTypeOralLiquidTF()) && ObjectUtils.isEmpty(patient.getDietTypeOralLiquidTF().getDietTypeOralLiquidTFId()))) {
			patient.setDietTypeOralLiquidTF(null);
		} else {
			patient.setDietTypeOralLiquidTF(dietTypeOralLiquidTFRepository.findById(patient.getDietTypeOralLiquidTF().getDietTypeOralLiquidTFId()).get());
		}
		
		if(ObjectUtils.isEmpty(patient.getDietSubType()) || (ObjectUtils.isNotEmpty(patient.getDietSubType()) && ObjectUtils.isEmpty(patient.getDietSubType().getDietSubTypeId()))) {
			patient.setDietSubType(null);
		} else {
			patient.setDietSubType(dietSubTypeRepository.findById(patient.getDietSubType().getDietSubTypeId()).get());
		}
		
		if(ObjectUtils.isEmpty(patient.getQuantity()) || (ObjectUtils.isNotEmpty(patient.getQuantity()) && ObjectUtils.isEmpty(patient.getQuantity().getQuantityId()))) {
			patient.setQuantity(null);
		} else {
			patient.setQuantity(quantityRepository.findById(patient.getQuantity().getQuantityId()).get());
		}
		
		if (ObjectUtils.isEmpty(patient.getFrequency()) || (ObjectUtils.isNotEmpty(patient.getFrequency()) && ObjectUtils.isEmpty(patient.getFrequency().getFrequencyId()))) {
			patient.setFrequency(null);
		} else {
			patient.setFrequency(frequencyRepository.findById(patient.getFrequency().getFrequencyId()).get());
		}

		if (CollectionUtils.isEmpty(patient.getMedicalComorbidities())) {
			patient.setMedicalComorbidities(null);
		} else {
			patient.setMedicalComorbidities(medicalComorbiditiesRepository.findAllById(patient.getMedicalComorbidities().stream().map(medicalComorbidities -> medicalComorbidities.getMedicalComorbiditiesId()).toList()));
		}
		return patient;
	}
	
	
	@Override
	public String getPatientDetails(Long patientId, Model model) {
		if (ObjectUtils.isNotEmpty(patientId)) {
			Patient patient = patientRepository.findById(patientId).get();
			PatientDto patientDto = modelMapper.map(patient, PatientDto.class);
			patientDto.setAdmittedDate(patient.getAdmittedDate());
			patientDto.getMedicalComorbiditiesIds();
			patientDto.getDiagonosisIds();
			patientDto.getSpecialNotesByNursingIds();
			model.addAttribute("patientDto", patientDto);
		} else {
			model.addAttribute("patientDto", new PatientDto());
		}
		List<Diagonosis> diagonosisList = diagonosisRepository.findAllByIsActive(Boolean.TRUE);
		Diagonosis otherDiagonosis = diagonosisList.get(0);
		diagonosisList.remove(0);	
		diagonosisList.add(otherDiagonosis);	
		model.addAttribute("localDateTimeFormatter", CommonUtility.localDateTimeFormatter);
		model.addAttribute("bedList", bedRepository.findAllByIsActive(Boolean.TRUE));
		model.addAttribute("dietTypeOralSolidList", dietTypeOralSolidRepository.findAllByIsActive(Boolean.TRUE));
		model.addAttribute("dietTypeOralLiquidTFList", dietTypeOralLiquidTFRepository.findAllByIsActive(Boolean.TRUE));
		model.addAttribute("dietSubTypeList", dietSubTypeRepository.findAllByIsActive(Boolean.TRUE));
		model.addAttribute("quantityList", quantityRepository.findAllByIsActive(Boolean.TRUE));
		model.addAttribute("frequencyList", frequencyRepository.findAllByIsActive(Boolean.TRUE));
		model.addAttribute("medicalComorbiditiesList", medicalComorbiditiesRepository.findAllByIsActive(Boolean.TRUE));
		model.addAttribute("diagonosisList", diagonosisList);
		model.addAttribute("specialNotesByNursingList", specialNotesByNursingRepository.findAllByIsActive(Boolean.TRUE));
		return "diet/PatientDetails";
	}

	@Override
	public String savePatientDetails(RedirectAttributes redir, PatientDto patientDto) {
		boolean isValid = true;
		Optional<Patient> patient = Optional.empty();
		if (ObjectUtils.isNotEmpty(patientDto.getPatientId()) && patientDto.getPatientId() > 0) {
			patient = patientRepository.findById(patientDto.getPatientId());
		}		
		if (patient.isPresent() && patient.get().getPatientStatus() == 2) {
			isValid = false;
			redir.addFlashAttribute("errorMsg", "Patien has been discharged");
		}
		if (isValid && !"true".equalsIgnoreCase(checkUniqueIpNumber(patientDto.getIpNumber(), patientDto.getPatientId()))) {
			isValid = false;
			redir.addFlashAttribute("errorMsg", "IP number already in use");
		}
		
		if (!isValid) {
			return "redirect:/diet/patients";
		}
		boolean sendAddPatient = false;
		String updatedFields = "";
		boolean extraLiquidChange = false;
		boolean dietTypeOralSolidChange = false;
		boolean dietSubTypeChange = false;
		boolean frequencyChange = false;
		LocalDateTime now = LocalDateTime.now();
		User currentUser = commonUtility.getCurrentUser();
		if(StringUtils.isNotEmpty(patientDto.getMedicalComorbiditiesIds())) {
			List<MedicalComorbiditiesDto> medicalComorbidities = new ArrayList<>();
			String[] ids = patientDto.getMedicalComorbiditiesIds().split(",");
			for (String id : ids) {
				MedicalComorbiditiesDto medicalComorbiditiesDto = new MedicalComorbiditiesDto();
				medicalComorbiditiesDto.setMedicalComorbiditiesId(Long.valueOf(id));
				medicalComorbidities.add(medicalComorbiditiesDto);;
			}
			patientDto.setMedicalComorbidities(medicalComorbidities);
		}
		if(StringUtils.isNotEmpty(patientDto.getDiagonosisIds())) {
			List<DiagonosisDto> diagonosis = new ArrayList<>();
			String[] ids = patientDto.getDiagonosisIds().split(",");
			for (String id : ids) {
				DiagonosisDto diagonosisDto = new DiagonosisDto();
				diagonosisDto.setDiagonosisId(Long.valueOf(id));
				diagonosis.add(diagonosisDto);
			}
			patientDto.setDiagonosis(diagonosis);
		}
		if(StringUtils.isNotEmpty(patientDto.getSpecialNotesByNursingIds())) {
			List<SpecialNotesByNursingDto> specialNotesByNursing = new ArrayList<>();
			String[] ids = patientDto.getSpecialNotesByNursingIds().split(",");
			for (String id : ids) {
				if (Long.valueOf(id) > 0) {
					SpecialNotesByNursingDto specialNotesByNursingDto = new SpecialNotesByNursingDto();
					specialNotesByNursingDto.setSpecialNotesByNursingId(Long.valueOf(id));
					specialNotesByNursing.add(specialNotesByNursingDto);
				}
			}
			patientDto.setSpecialNotesByNursing(specialNotesByNursing);
		}
		boolean updateDiet = false;
		Patient savePatient = modelMapper.map(patientDto, Patient.class);
		savePatient.setPatientStatus(1);
		if (patient.isPresent()) {
			Patient patientEntity = patient.get();
			savePatient.setUmrNumber(patientEntity.getUmrNumber());
			savePatient.setIpNumber(patientEntity.getIpNumber());
			savePatient.setRoom(patientEntity.getRoom());
			savePatient.setBed(patientEntity.getBed());
			savePatient.setBedStatus(patientEntity.getBedStatus());
			savePatient.setPatientName(patientEntity.getPatientName());
			savePatient.setAge(patientEntity.getAge());
			savePatient.setGender(patientEntity.getGender());
			savePatient.setAllergicto(patientEntity.getAllergicto());
			savePatient.setDoctor(patientEntity.getDoctor());
			savePatient.setAdmissionType(patientEntity.getAdmissionType());
			savePatient.setProcedureStr(patientEntity.getProcedureStr());
			savePatient.setPaymentType(patientEntity.getPaymentType());
			savePatient.setAdmittedDate(patientEntity.getAdmittedDate());
			savePatient.setDischargedTime(patientEntity.getDischargedTime());
			savePatient.setBillStatus(patientEntity.getBillStatus());
			
			savePatient.setCreatedOn(patientEntity.getCreatedOn());
			savePatient.setCreatedBy(patientEntity.getCreatedBy());
			savePatient.setModifiedOn(now);
			savePatient.setModifiedBy(currentUser.getUserId());
			savePatient.setCreatedUserHistoryId(patientEntity.getCreatedUserHistoryId());
			savePatient.setModifiedUserHistoryId(currentUser.getCurrenUserHistoryId());
			
			extraLiquidChange = patientEntity.getExtraLiquid() != patientDto.getExtraLiquid();
			dietTypeOralSolidChange = !Objects.equals(ObjectUtils.isNotEmpty(patientEntity.getDietTypeOralSolid()) && ObjectUtils.isNotEmpty(patientEntity.getDietTypeOralSolid().getDietTypeOralSolidId()) ? patientEntity.getDietTypeOralSolid().getDietTypeOralSolidId() : 0l,
					ObjectUtils.isNotEmpty(patientDto.getDietTypeOralSolid()) && ObjectUtils.isNotEmpty(patientDto.getDietTypeOralSolid().getDietTypeOralSolidId()) ? patientDto.getDietTypeOralSolid().getDietTypeOralSolidId() : 0l);
			dietSubTypeChange = !Objects.equals(ObjectUtils.isNotEmpty(patientEntity.getDietSubType()) && ObjectUtils.isNotEmpty(patientEntity.getDietSubType().getDietSubTypeId()) ? patientEntity.getDietSubType().getDietSubTypeId() : 0l,
					ObjectUtils.isNotEmpty(patientDto.getDietSubType()) && ObjectUtils.isNotEmpty(patientDto.getDietSubType().getDietSubTypeId()) ? patientDto.getDietSubType().getDietSubTypeId() : 0l);
			frequencyChange = !Objects.equals(ObjectUtils.isNotEmpty(patientEntity.getFrequency()) && ObjectUtils.isNotEmpty(patientEntity.getFrequency().getFrequencyId()) ? patientEntity.getFrequency().getFrequencyId() : 0l,
					ObjectUtils.isNotEmpty(patientDto.getFrequency()) && ObjectUtils.isNotEmpty(patientDto.getFrequency().getFrequencyId()) ? patientDto.getFrequency().getFrequencyId() : 0l);
			
			if (extraLiquidChange || dietTypeOralSolidChange || dietSubTypeChange || frequencyChange) {
				updateDiet = true;
			}
			
			updatedFields = notificationsUtility.getUpdatedFields(fillPatientData(patientEntity), fillPatientData(savePatient), extraLiquidChange, dietTypeOralSolidChange, dietSubTypeChange, frequencyChange);
			
			if (!StringUtils.equals(patientEntity.getMedicalComorbiditiesIds(), patientDto.getMedicalComorbiditiesIds())
					|| !StringUtils.equals(patientEntity.getDiagonosisIds(), patientDto.getDiagonosisIds())
					|| !Objects.equals(ObjectUtils.isNotEmpty(patientEntity.getDietTypeOralSolid()) && ObjectUtils.isNotEmpty(patientEntity.getDietTypeOralSolid().getDietTypeOralSolidId()) ? patientEntity.getDietTypeOralSolid().getDietTypeOralSolidId() : 0l,
							ObjectUtils.isNotEmpty(patientDto.getDietTypeOralSolid()) && ObjectUtils.isNotEmpty(patientDto.getDietTypeOralSolid().getDietTypeOralSolidId()) ? patientDto.getDietTypeOralSolid().getDietTypeOralSolidId() : 0l)
					|| !Objects.equals(ObjectUtils.isNotEmpty(patientEntity.getDietSubType()) && ObjectUtils.isNotEmpty(patientEntity.getDietSubType().getDietSubTypeId()) ? patientEntity.getDietSubType().getDietSubTypeId() : 0l,
							ObjectUtils.isNotEmpty(patientDto.getDietSubType()) && ObjectUtils.isNotEmpty(patientDto.getDietSubType().getDietSubTypeId()) ? patientDto.getDietSubType().getDietSubTypeId() : 0l)) {
				savePatient.setShowUpdated(true);
			}
			
			if (patientEntity.getPatientStatus() == 0) {
				sendAddPatient = true;
			}
			
		} else {
			sendAddPatient = true;
			updateDiet = true;
			
			savePatient.setAdmittedDate(patientDto.getAdmittedDate());
			
			savePatient.setCreatedOn(now);
			savePatient.setCreatedBy(currentUser.getUserId());
			savePatient.setModifiedOn(now);
			savePatient.setModifiedBy(currentUser.getUserId());
			savePatient.setCreatedUserHistoryId(currentUser.getCurrenUserHistoryId());
			savePatient.setModifiedUserHistoryId(currentUser.getCurrenUserHistoryId());
		}
		savePatient = save(savePatient);
		if (updateDiet) {
			dietPlanService.prepareDietPlan(List.of(savePatient), currentUser, false);
		}
		
		if (sendAddPatient) {
			notificationsService.sendAddPatient(savePatient.getPatientId());
		} else if (StringUtils.isNoneEmpty(updatedFields)) {
			notificationsService.sendUpdatePatient(savePatient.getPatientId(), updatedFields);
		}
		String redirectUrl = patientDto.getImmediateService()
				? "adhoc-order?immediateService=TRUE&patientId=" + savePatient.getPatientId()
				: "patients";
		return "redirect:/diet/" + redirectUrl;
	}
	
	@Override
	public ResponseEntity<String> addPatient(PatientDto patientDto) {
		LocalDateTime now = LocalDateTime.now();
		try {
			if (ObjectUtils.isEmpty(patientDto.getBed())) {
				return ResponseEntity.ok().body("{\"status\":\"bedcd not matched\"}");
			}
			Patient patientEntity = patientRepository.findByIpNumber(patientDto.getIpNumber());	
			Patient savePatient = modelMapper.map(patientDto, Patient.class);
			if(ObjectUtils.isNotEmpty(patientEntity)) {
				savePatient = patientEntity;
				savePatient.setUmrNumber(patientDto.getUmrNumber());
				savePatient.setIpNumber(patientDto.getIpNumber());
				savePatient.setRoom(patientDto.getRoom());
				savePatient.setBed(bedRepository.findById(patientDto.getBed().getBedId()).get());
				savePatient.setBedStatus(patientDto.getBedStatus());
				savePatient.setPatientName(patientDto.getPatientName());
				savePatient.setAge(patientDto.getAge());
				savePatient.setGender(patientDto.getGender());
				savePatient.setAllergicto(patientDto.getAllergicto());
				savePatient.setDoctor(patientDto.getDoctor());
				savePatient.setAdmissionType(patientDto.getAdmissionType());
				savePatient.setProcedureStr(patientDto.getProcedureStr());
				savePatient.setPaymentType(patientDto.getPaymentType());
				savePatient.setAdmittedDate(patientDto.getAdmittedDate());
				savePatient.setDischargedTime(patientDto.getDischargedTime());
				savePatient.setBillStatus(patientDto.getBillStatus());
				
				savePatient.setPatientStatus(patientEntity.getPatientStatus());
				savePatient.setCreatedOn(patientEntity.getCreatedOn());
				savePatient.setCreatedBy(patientEntity.getCreatedBy());
				savePatient.setModifiedOn(now);
				savePatient.setModifiedBy(0l);
				savePatient.setCreatedUserHistoryId(patientEntity.getCreatedUserHistoryId());
				savePatient.setModifiedUserHistoryId(0l);
			} else {
				savePatient.setAdmittedDate(patientDto.getAdmittedDate());
				savePatient.setDischargedTime(patientDto.getDischargedTime());
				
				savePatient.setPatientStatus(0);
				savePatient.setCreatedOn(now);
				savePatient.setCreatedBy(0l);
				savePatient.setModifiedOn(now);
				savePatient.setModifiedBy(0l);
				savePatient.setCreatedUserHistoryId(0l);
				savePatient.setModifiedUserHistoryId(0l);
			}
			save(savePatient);
			return ResponseEntity.ok().body("{\"status\":\"sucess\"}");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ResponseEntity.ok().body("{\"status\":\"failed\"}");
	}
	
	@Override
	public ResponseEntity<String> transferPatient(PatientDto patientDto) {
		LocalDateTime now = LocalDateTime.now();
		try {
			if (ObjectUtils.isEmpty(patientDto.getBed())) {
				return ResponseEntity.ok().body("{\"status\":\"bedcd not matched\"}");
			}
			Patient patientEntity = patientRepository.findByIpNumber(patientDto.getIpNumber());	
			Patient savePatient = modelMapper.map(patientDto, Patient.class);
			if(ObjectUtils.isNotEmpty(patientEntity)) {
				savePatient = patientEntity;
				savePatient.setBed(bedRepository.findById(patientDto.getBed().getBedId()).get());
				
				savePatient.setModifiedOn(now);
				savePatient.setModifiedBy(0l);
				savePatient.setModifiedUserHistoryId(0l);
				savePatient = save(savePatient);
				notificationsService.sendPatientTransferred(savePatient.getPatientId(), patientDto.getBed().getBedId());
				return ResponseEntity.ok().body("{\"status\":\"sucess\"}");
			} 
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ResponseEntity.ok().body("{\"status\":\"failed\"}");
	}
	
	@Override
	public ResponseEntity<String> dischragePatient(PatientDto patientDto) {
		LocalDateTime now = LocalDateTime.now();
		try {
			if (ObjectUtils.isEmpty(patientDto.getBed())) {
				return ResponseEntity.ok().body("{\"status\":\"bedcd not matched\"}");
			}
			Patient patientEntity = patientRepository.findByIpNumber(patientDto.getIpNumber());	
			Patient savePatient = modelMapper.map(patientDto, Patient.class);
			if(ObjectUtils.isNotEmpty(patientEntity)) {
				savePatient = patientEntity;
				savePatient.setBed(bedRepository.findById(patientDto.getBed().getBedId()).get());
				savePatient.setDischargedTime(patientDto.getDischargedTime());
				savePatient.setBillStatus(patientDto.getBillStatus());
				savePatient.setPatientStatus(2);
				
				savePatient.setModifiedOn(now);
				savePatient.setModifiedBy(0l);
				savePatient.setModifiedUserHistoryId(0l);
				savePatient = save(savePatient);
				dietPlanService.prepareDietPlan(List.of(savePatient), null, false);
				notificationsService.sendPatientDischarge(savePatient.getPatientId());
				return ResponseEntity.ok().body("{\"status\":\"sucess\"}");
			} 
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ResponseEntity.ok().body("{\"status\":\"failed\"}");
	}

	@Override
	public PatientDataTablesOutputDto getPatientData(DataTablesInput input, Integer patientStatus, boolean nbm, boolean extraLiquid, boolean startServiceImmediately, boolean isVip) {
		User currentUser = commonUtility.getCurrentUser();
		input.addColumn("bed.bedCode", true, true, null);
		input.addColumn("bed.wardName", true, true, null);
		input.addColumn("bed.floor.floorName", true, true, null);
		input.addColumn("modifiedOn", false, true, null);
		input.addOrder("modifiedOn", false);
		List<Order> orders = input.getOrder();
		orders.add(0, orders.get(orders.size()-1));
		orders.remove(orders.size()-1);
		final boolean isNursing;
		if (currentUser.getRoles().stream().filter(role -> role.getName().equals("ROLE_NURSING")).findFirst().isPresent()) {
			isNursing = true;
		} else {
			isNursing = false;
		}
		
		Specification<Patient> additionalSpecification = (root, query, criteriaBuilder) -> {
			List<Predicate> predicates = new ArrayList<>();
			predicates.add(criteriaBuilder.equal(root.get("patientStatus"), patientStatus));
			if (nbm) {
				predicates.add(criteriaBuilder.equal(root.get("nbm"), nbm));
			}
			if (extraLiquid) {
				predicates.add(criteriaBuilder.equal(root.get("extraLiquid"), extraLiquid));
			}
			if (startServiceImmediately) {
				predicates.add(criteriaBuilder.equal(root.get("startServiceImmediately"), startServiceImmediately));
			}
			if (isVip) {
				predicates.add(criteriaBuilder.equal(root.get("isVip"), isVip));
			}
			if (isNursing) {
				predicates.add(criteriaBuilder.equal(root.get("bed").get("floor").get("floorId"), currentUser.getFloor().getFloorId()));
			}
			return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
		};
		PatientDataTablesOutputDto patientDataTablesOutputDto = new PatientDataTablesOutputDto();
		patientDataTablesOutputDto.setData(patientDataTablesRepository.findAll(input, additionalSpecification));
		if (isNursing) {
			patientDataTablesOutputDto.setCount(patientRepository.countByPatientStatusAndBedFloorFloorId(patientStatus, currentUser.getFloor().getFloorId()));
		} else {
			patientDataTablesOutputDto.setCount(patientRepository.countByPatientStatus(patientStatus));
		}
		return patientDataTablesOutputDto;
	}

	@Override
	public String checkUniqueIpNumber(String ipNumber, Long patientId) {
		try {
			Patient patient = patientRepository.findByIpNumber(ipNumber);
			if (ObjectUtils.isEmpty(patient) || patient.getPatientId() == patientId) {
				return "true";
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "false";
	}

}
