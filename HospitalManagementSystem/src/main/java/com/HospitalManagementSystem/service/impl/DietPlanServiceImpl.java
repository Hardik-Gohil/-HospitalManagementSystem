package com.HospitalManagementSystem.service.impl;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.persistence.criteria.Join;
import javax.persistence.criteria.Predicate;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.SerializationUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.datatables.mapping.DataTablesInput;
import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;
import org.springframework.data.jpa.datatables.mapping.Search;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.HospitalManagementSystem.dto.DietPlanSearchDto;
import com.HospitalManagementSystem.dto.PatientDataTablesOutputDto;
import com.HospitalManagementSystem.entity.AdHocOrder;
import com.HospitalManagementSystem.entity.DietInstruction;
import com.HospitalManagementSystem.entity.DietPlan;
import com.HospitalManagementSystem.entity.Patient;
import com.HospitalManagementSystem.entity.User;
import com.HospitalManagementSystem.entity.master.MedicalComorbidities;
import com.HospitalManagementSystem.entity.master.ServiceItems;
import com.HospitalManagementSystem.entity.master.ServiceMaster;
import com.HospitalManagementSystem.repository.DietInstructionRepository;
import com.HospitalManagementSystem.repository.DietPlanRepository;
import com.HospitalManagementSystem.repository.PatientDataTablesRepository;
import com.HospitalManagementSystem.repository.PatientRepository;
import com.HospitalManagementSystem.repository.ServiceMasterRepository;
import com.HospitalManagementSystem.service.DietPlanService;
import com.HospitalManagementSystem.utility.CommonUtility;
import com.HospitalManagementSystem.utility.DietUtility;

@Service
public class DietPlanServiceImpl implements DietPlanService {

	@Autowired
	private DietPlanRepository dietPlanRepository;
	@Autowired
	private ServiceMasterRepository serviceMasterRepository;
	@Autowired
	private DietInstructionRepository dietInstructionRepository;
	@Autowired
	private PatientDataTablesRepository patientDataTablesRepository;
	@Autowired
	private PatientRepository patientRepository;
	
	@Autowired
	private CommonUtility commonUtility;
	@Autowired
	private DietUtility dietUtility;
	
	@Override
	@Transactional
	public synchronized void prepareDietPlan() {
		List<Patient> patientList = patientRepository.findAllByPatientStatus(1);
		List<Long> preparedDietPlanPatientIdList = new ArrayList<Long>();
		List<Patient> dietPlanPatientList = new ArrayList<Patient>();
		if (CollectionUtils.isNotEmpty(patientList)) {
			List<Patient> preparedDietPlanPatientList = dietPlanRepository.findAllPatientsByDietDate(LocalDate.now().plusDays(1));
			if (CollectionUtils.isNotEmpty(preparedDietPlanPatientList)) {
				preparedDietPlanPatientIdList = preparedDietPlanPatientList.stream().map(patient -> patient.getPatientId()).collect(Collectors.toList());
			}
			for (Patient patient : patientList) {
				if (!preparedDietPlanPatientIdList.contains(patient.getPatientId())) {
					dietPlanPatientList.add(patient);
				}
			}
		}
		prepareDietPlan(dietPlanPatientList, null, true);
	}

	@Override
	public synchronized void prepareDietPlan(List<Patient> patientList, User currentUser, Boolean prepareAll) {
		if (CollectionUtils.isNotEmpty(patientList)) {
			LocalDateTime now = LocalDateTime.now();
			Map<String, List<ServiceItems>> serviceItemsMap = dietUtility.getServiceItemsMap();
			List<DietPlan> previousDietPlanList = new ArrayList<>();
			if (prepareAll) {
				previousDietPlanList = dietPlanRepository.findAllByDietDate(now.toLocalDate());
			}
			for (Patient patient : patientList) {
				if (!prepareAll) {
					List<DietPlan> upcommingDietPlans = dietPlanRepository.findAllByPatientPatientIdAndServiceMasterFromTimeGreaterThan(patient.getPatientId(), now.toLocalTime());
					if (CollectionUtils.isNotEmpty(upcommingDietPlans)) {
						dietPlanRepository.deleteAll(upcommingDietPlans);
					}
					List<DietPlan> upcommingDietPlansForTomorrow = dietPlanRepository.findAllByPatientPatientIdAndDietDate(patient.getPatientId(), now.toLocalDate().plusDays(1));
					if (CollectionUtils.isNotEmpty(upcommingDietPlansForTomorrow)) {
						dietPlanRepository.deleteAll(upcommingDietPlansForTomorrow);
					}
				}
				
				if (patient.getPatientStatus() == 1) {
					List<DietPlan> dietPlanList = new ArrayList<>();
					List<DietPlan> dietPlanListForTomorrow = new ArrayList<>();
					List<DietInstruction> dietInstructionList = dietInstructionRepository.findAllByPatientPatientIdAndDietInstructionStatus(patient.getPatientId(), 1);
					List<ServiceMaster> serviceMasterList = serviceMasterRepository.findAll(dietUtility.getServiceMasterSpecification(patient, Collections.EMPTY_LIST));
					if (CollectionUtils.isNotEmpty(serviceMasterList)) {
						for (ServiceMaster serviceMaster : serviceMasterList) {
							Optional<DietPlan> previousDietPlan = previousDietPlanList.stream().filter(dp -> dp.getPatient().getPatientId() == patient.getPatientId() && dp.getServiceMaster().getServiceMasterId() == serviceMaster.getServiceMasterId()).findFirst();
							if(!prepareAll && serviceMaster.getFromTime().isAfter(now.toLocalTime())) {
								dietPlanList.add(getDietPlan(patient, serviceMaster, dietInstructionList, now, now.toLocalDate(), serviceItemsMap, currentUser, previousDietPlan));
							}
							dietPlanListForTomorrow.add(getDietPlan(patient, serviceMaster, dietInstructionList, now, now.toLocalDate().plusDays(1), serviceItemsMap, currentUser, previousDietPlan));
						}
					}
					dietPlanList.addAll(dietPlanListForTomorrow);
					if (CollectionUtils.isNotEmpty(dietPlanList)) {
						dietPlanRepository.saveAll(dietPlanList);
					}
				}
			}
		}
	}
	
	private DietPlan getDietPlan(Patient patient, ServiceMaster serviceMaster, List<DietInstruction> dietInstructionList, LocalDateTime now, LocalDate dietDate, Map<String, List<ServiceItems>> serviceItemsMap, User currentUser, Optional<DietPlan> previousDietPlan) {
		DietPlan dietPlan = new DietPlan();
		dietPlan.setPatient(patient);
		dietPlan.setServiceMaster(serviceMaster);
		dietPlan.setDietInstructions(dietInstructionList.stream()
				.filter(di -> Arrays.asList(di.getServiceMasterIds().split(",")).contains(String.valueOf(serviceMaster.getServiceMasterId())))
				.filter(di -> di.getApplicableFor() == 2 || (!(dietDate.isBefore(di.getApplicableFrom()) || dietDate.isAfter(di.getApplicableTo()))))
				.collect(Collectors.toList()));
		
		dietPlan.setServiceItems(previousDietPlan.isPresent() ? previousDietPlan.get().getServiceItems().stream().map(x -> SerializationUtils.clone(x)).collect(Collectors.toList()) : serviceItemsMap.get(serviceMaster.getServiceItemsColumnName() + "_" + dietUtility.getKeySuffix(patient)));
		if (CollectionUtils.isNotEmpty(dietPlan.getServiceItems())) {
			dietPlan.setOriginalItem(previousDietPlan.isPresent() ? previousDietPlan.get().getOriginalItem() : dietPlan.getServiceItems().stream().map(si -> si.getItemName()).collect(Collectors.joining(", ")));
			dietPlan.setItem(previousDietPlan.isPresent() ? previousDietPlan.get().getItem() : dietPlan.getOriginalItem());
		}
		dietPlan.setIsPaused(previousDietPlan.isPresent() ? previousDietPlan.get().getIsPaused() : false);
		dietPlan.setCreatedOn(now);
		dietPlan.setDietDate(dietDate);
		dietPlan.setCreatedBy(ObjectUtils.isNotEmpty(currentUser) ? currentUser.getUserId() : null);
		dietPlan.setCreatedUserHistoryId(ObjectUtils.isNotEmpty(currentUser) ? currentUser.getCurrenUserHistoryId() : null);
		
		return dietPlan;
	}
	
	

	@Override
	public PatientDataTablesOutputDto getDietPlanData(DietPlanSearchDto dietPlanSearchDto) {
		DataTablesInput input = dietPlanSearchDto;
		input.setSearch(new Search(dietPlanSearchDto.getSearchText(), false));
		LocalDate date = LocalDate.now();
		if (StringUtils.isNotEmpty(dietPlanSearchDto.getDateSelection())) {
			date = LocalDate.parse(dietPlanSearchDto.getDateSelection(), CommonUtility.localDateFormatter);
		}
		PatientDataTablesOutputDto patientDataTablesOutputDto = new PatientDataTablesOutputDto();
		List<Patient> patientList;
		if (StringUtils.isNotEmpty(dietPlanSearchDto.getSearchItemText().trim()) && CollectionUtils.isNotEmpty(dietPlanSearchDto.getServiceMasterIds())) {
			patientList = dietPlanRepository.findAllPatientsByDietDateAndItemAndServiceMasterIds(date, "%" + dietPlanSearchDto.getSearchItemText().trim() + "%", dietPlanSearchDto.getServiceMasterIds());
		} else if (StringUtils.isNotEmpty(dietPlanSearchDto.getSearchItemText().trim())) {
			patientList = dietPlanRepository.findAllPatientsByDietDateAndItem(date, "%" + dietPlanSearchDto.getSearchItemText().trim() + "%");
		} else if (CollectionUtils.isNotEmpty(dietPlanSearchDto.getServiceMasterIds())) {
			patientList = dietPlanRepository.findAllPatientsByDietDateAndServiceMasterIds(date, dietPlanSearchDto.getServiceMasterIds());
		} else {
			patientList = dietPlanRepository.findAllPatientsByDietDate(date);
		}
		
		if(CollectionUtils.isNotEmpty(patientList)) {
			Specification<Patient> additionalSpecification = (root, query, criteriaBuilder) -> {
				List<Predicate> predicates = new ArrayList<>();
				predicates.add(root.get("patientId").in(patientList.stream().map(p -> p.getPatientId()).toList()));
				if (CollectionUtils.isNotEmpty(dietPlanSearchDto.getMedicalComorbiditiesIds())) {
					Join<MedicalComorbidities, Patient> medicalComorbiditiesJoin = root.join("medicalComorbidities");
					predicates.add(medicalComorbiditiesJoin.get("medicalComorbiditiesId").in(dietPlanSearchDto.getMedicalComorbiditiesIds()));
				}
				if (CollectionUtils.isNotEmpty(dietPlanSearchDto.getFloorIds())) {
					predicates.add(root.get("bed").get("floor").get("floorId").in(dietPlanSearchDto.getFloorIds()));
				}
				if (CollectionUtils.isNotEmpty(dietPlanSearchDto.getBedIds())) {
					predicates.add(root.get("bed").get("bedId").in(dietPlanSearchDto.getBedIds()));
				}
				if (CollectionUtils.isNotEmpty(dietPlanSearchDto.getDietTypeOralSolidIds())) {
					predicates.add(root.get("dietTypeOralSolid").get("dietTypeOralSolidId").in(dietPlanSearchDto.getDietTypeOralSolidIds()));
				}
				if (CollectionUtils.isNotEmpty(dietPlanSearchDto.getDietTypeOralLiquidTFIds())) {
					predicates.add(root.get("dietTypeOralLiquidTF").get("dietTypeOralLiquidTFId").in(dietPlanSearchDto.getDietTypeOralLiquidTFIds()));
				}
				if (CollectionUtils.isNotEmpty(dietPlanSearchDto.getDietSubTypeIds())) {
					predicates.add(root.get("dietSubType").get("dietSubTypeId").in(dietPlanSearchDto.getDietSubTypeIds()));
				}				
				if (dietPlanSearchDto.getIsVip()) {
					predicates.add(criteriaBuilder.equal(root.get("isVip"), dietPlanSearchDto.getIsVip()));
				}				
				if (dietPlanSearchDto.getExtraLiquid()) {
					predicates.add(criteriaBuilder.equal(root.get("extraLiquid"), dietPlanSearchDto.getExtraLiquid()));
				}
				return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
			};
			DataTablesOutput<Patient> data = patientDataTablesRepository.findAll(input, additionalSpecification);
			patientDataTablesOutputDto.setData(data);
			patientDataTablesOutputDto.setCount(patientList.size());
			if (CollectionUtils.isNotEmpty(data.getData())) {
				for (Patient patient : data.getData()) {
					patient.setDietPlans(dietPlanRepository.findAllByPatientPatientIdAndDietDate(patient.getPatientId(), date));
				}
			}
		} else {
			patientDataTablesOutputDto.setData(new DataTablesOutput<Patient>());
		}
		return patientDataTablesOutputDto;
	}

	@Override
	public ResponseEntity<String> updateDietPlanItem(Long dietPlanId, String item) {
		boolean isValid = true;
		LocalDateTime now = LocalDateTime.now();
		User currentUser = commonUtility.getCurrentUser();
		Optional<DietPlan> dietPlan = dietPlanRepository.findById(dietPlanId);
		if (dietPlan.isPresent()) {
			DietPlan dietPlanEntity = dietPlan.get();
			if (now.isAfter(dietPlanEntity.getDietDate().atTime(dietPlanEntity.getServiceMaster().getFromTime()))) {
				isValid = false;
				return ResponseEntity.ok().body("You can not update the Item whose Delivery Date Time lapsed");
			}
			
			if (isValid) {
				dietPlanEntity.setItem(item);
				dietPlanEntity.setModifiedOn(LocalDateTime.now());
				dietPlanEntity.setModifiedBy(currentUser.getUserId());
				dietPlanEntity.setModifiedUserHistoryId(currentUser.getCurrenUserHistoryId());
				dietPlanRepository.save(dietPlanEntity);
				
				Optional<DietPlan> dietPlanForTomorrow = dietPlanRepository.findByServiceMasterServiceMasterIdAndPatientPatientIdAndDietDate(dietPlanEntity.getServiceMaster().getServiceMasterId(), dietPlanEntity.getPatient().getPatientId(), dietPlanEntity.getDietDate().plusDays(1));
				if (dietPlanForTomorrow.isPresent()) {
					DietPlan dietPlanEntityForTomorrow = dietPlanForTomorrow.get();
					dietPlanEntityForTomorrow.setItem(item);
					dietPlanEntityForTomorrow.setModifiedOn(LocalDateTime.now());
					dietPlanEntityForTomorrow.setModifiedBy(currentUser.getUserId());
					dietPlanEntityForTomorrow.setModifiedUserHistoryId(currentUser.getCurrenUserHistoryId());
					dietPlanRepository.save(dietPlanEntityForTomorrow);
				}
				return ResponseEntity.ok().body("Item has been updated");
			}
		}
		return ResponseEntity.badRequest().body("Diet Plan not found");
	}

	
	@Override
	public ResponseEntity<String> updateDietPlanPausedUnpaused(Long dietPlanId, boolean isPaused) {
		boolean isValid = true;
		LocalDateTime now = LocalDateTime.now();
		User currentUser = commonUtility.getCurrentUser();
		Optional<DietPlan> dietPlan = dietPlanRepository.findById(dietPlanId);
		if (dietPlan.isPresent()) {
			DietPlan dietPlanEntity = dietPlan.get();
			if (now.isAfter(dietPlanEntity.getDietDate().atTime(dietPlanEntity.getServiceMaster().getFromTime()))) {
				isValid = false;
				return ResponseEntity.ok().body("You can not update the Item whose Delivery Date Time lapsed");
			}
			
			if (isValid) {
				dietPlanEntity.setIsPaused(isPaused);
				dietPlanEntity.setModifiedOn(LocalDateTime.now());
				dietPlanEntity.setModifiedBy(currentUser.getUserId());
				dietPlanEntity.setModifiedUserHistoryId(currentUser.getCurrenUserHistoryId());
				dietPlanRepository.save(dietPlanEntity);
				
				Optional<DietPlan> dietPlanForTomorrow = dietPlanRepository.findByServiceMasterServiceMasterIdAndPatientPatientIdAndDietDate(dietPlanEntity.getServiceMaster().getServiceMasterId(), dietPlanEntity.getPatient().getPatientId(), dietPlanEntity.getDietDate().plusDays(1));
				if (dietPlanForTomorrow.isPresent()) {
					DietPlan dietPlanEntityForTomorrow = dietPlanForTomorrow.get();
					dietPlanEntityForTomorrow.setIsPaused(isPaused);
					dietPlanEntityForTomorrow.setModifiedOn(LocalDateTime.now());
					dietPlanEntityForTomorrow.setModifiedBy(currentUser.getUserId());
					dietPlanEntityForTomorrow.setModifiedUserHistoryId(currentUser.getCurrenUserHistoryId());
					dietPlanRepository.save(dietPlanEntityForTomorrow);
				}
				return ResponseEntity.ok().body("Item has been updated");
			}
		}
		return ResponseEntity.badRequest().body("Diet Plan not found");
	}

	@Override
	public void updateDietInstruction(Patient patient) {
		LocalDateTime now = LocalDateTime.now();
		List<DietPlan> dietPlanList = new ArrayList<DietPlan>();
		List<DietPlan> upcommingDietPlans = dietPlanRepository.findAllByPatientPatientIdAndServiceMasterFromTimeGreaterThan(patient.getPatientId(), now.toLocalTime());
		if (CollectionUtils.isNotEmpty(upcommingDietPlans)) {
			dietPlanList.addAll(upcommingDietPlans);
		}
		List<DietPlan> upcommingDietPlansForTomorrow = dietPlanRepository.findAllByPatientPatientIdAndDietDate(patient.getPatientId(), now.toLocalDate().plusDays(1));
		if (CollectionUtils.isNotEmpty(upcommingDietPlansForTomorrow)) {
			dietPlanList.addAll(upcommingDietPlansForTomorrow);
		}

		if (CollectionUtils.isNotEmpty(dietPlanList)) {
			List<DietInstruction> dietInstructionList = dietInstructionRepository.findAllByPatientPatientIdAndDietInstructionStatus(patient.getPatientId(), 1);
			for (DietPlan dietPlan : dietPlanList) {
				dietPlan.setDietInstructions(dietInstructionList.stream()
						.filter(di -> Arrays.asList(di.getServiceMasterIds().split(",")).contains(String.valueOf(dietPlan.getServiceMaster().getServiceMasterId())))
						.filter(di -> di.getApplicableFor() == 2 || (!(dietPlan.getDietDate().isBefore(di.getApplicableFrom()) || dietPlan.getDietDate().isAfter(di.getApplicableTo()))))
						.collect(Collectors.toList()));
			}
			dietPlanRepository.saveAll(dietPlanList);
		}
	}
}
