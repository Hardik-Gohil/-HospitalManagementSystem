package com.HospitalManagementSystem.service.impl;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.HospitalManagementSystem.dto.DietInstructionDto;
import com.HospitalManagementSystem.dto.PatientDto;
import com.HospitalManagementSystem.dto.ServiceMasterDto;
import com.HospitalManagementSystem.entity.DietInstruction;
import com.HospitalManagementSystem.entity.Patient;
import com.HospitalManagementSystem.entity.User;
import com.HospitalManagementSystem.entity.master.ServiceMaster;
import com.HospitalManagementSystem.repository.DietInstructionRepository;
import com.HospitalManagementSystem.repository.PatientRepository;
import com.HospitalManagementSystem.repository.ServiceMasterRepository;
import com.HospitalManagementSystem.service.DietInstructionService;
import com.HospitalManagementSystem.service.DietPlanService;
import com.HospitalManagementSystem.service.NotificationsService;
import com.HospitalManagementSystem.utility.CommonUtility;
import com.HospitalManagementSystem.utility.DietUtility;

@Service
public class DietInstructionServiceImpl implements DietInstructionService {

	@Autowired
	private PatientRepository patientRepository;
	@Autowired
	private ServiceMasterRepository serviceMasterRepository;
	@Autowired
	private DietInstructionRepository dietInstructionRepository;

	@Autowired
	private ModelMapper modelMapper;
	@Autowired
	private CommonUtility commonUtility;
	@Autowired
	private DietUtility dietUtility;
	
	@Autowired
	private DietPlanService dietPlanService;
	@Autowired
	private NotificationsService notificationsService;

	@Override
	public DietInstruction save(DietInstruction dietInstruction) {
		if (CollectionUtils.isNotEmpty(dietInstruction.getServiceMasters())) {
			dietInstruction.setServiceMasters(serviceMasterRepository.findAllById(dietInstruction.getServiceMasters().stream().map(serviceMasters -> serviceMasters.getServiceMasterId()).toList()));
		}
		dietInstruction = dietInstructionRepository.save(dietInstruction);
//		PatientHistory patientHistory = modelMapper.map(patient, PatientHistory.class);
//		patientHistory.setHistoryCreatedOn(patient.getModifiedOn());
//		patientHistory.setHistoryCreatedBy(patient.getModifiedBy());
//		patientHistory = patientHistoryRepository.save(patientHistory);
		return dietInstruction;
	}
	
	@Override
	public String getDietInstruction(Long patientId, Long dietInstructionId, Model model) {
		Map<Long, List<String>> serviceInvalidDateRangeMap = new HashMap<>();
		Patient patient = patientRepository.findById(patientId).get();
		List<DietInstruction> dietInstructions = dietInstructionRepository.findAllByPatientPatientIdAndDietInstructionIdNot(patientId, ObjectUtils.isNotEmpty(dietInstructionId) ? dietInstructionId : 0l);
		List<Long> dailyServiceMaster = new ArrayList<Long>();
		if (CollectionUtils.isNotEmpty(dietInstructions)) {
			for (DietInstruction dietInstruction : dietInstructions) {
				if (dietInstruction.getApplicableFor() == 1) {
					List<String> invalidDateRange = dietInstruction.getApplicableFrom()
							.datesUntil(dietInstruction.getApplicableTo().plusDays(1))
							.map(dt -> "'" + dt.format(CommonUtility.localDateFormatter) + "'").collect(Collectors.toList());

					for (ServiceMaster serviceMaster : dietInstruction.getServiceMasters()) {
						if (CollectionUtils.isNotEmpty(serviceInvalidDateRangeMap.get(serviceMaster.getServiceMasterId()))) {
							serviceInvalidDateRangeMap.get(serviceMaster.getServiceMasterId()).addAll(invalidDateRange);
						} else {
							serviceInvalidDateRangeMap.put(serviceMaster.getServiceMasterId(), invalidDateRange);
						}
					}
				} else if (dietInstruction.getApplicableFor() == 2) {
					dailyServiceMaster.addAll(dietInstruction.getServiceMasters().stream().map(sm -> sm.getServiceMasterId()).collect(Collectors.toList()));
				}
			}
		}
		
		serviceInvalidDateRangeMap.forEach((key, value) -> {
			Collections.sort(value);
		});
		
		if (ObjectUtils.isNotEmpty(dietInstructionId)) {
			DietInstruction dietInstruction = dietInstructionRepository.findById(dietInstructionId).get();
			DietInstructionDto dietInstructionDto = modelMapper.map(dietInstruction, DietInstructionDto.class);
			dietInstructionDto.setApplicableFrom(dietInstruction.getApplicableFrom());
			dietInstructionDto.setApplicableTo(dietInstruction.getApplicableTo());;
			model.addAttribute("dietInstructionDto", dietInstructionDto);
		} else {
			PatientDto patientDto = new PatientDto();
			DietInstructionDto dietInstructionDto = new DietInstructionDto();
			patientDto.setPatientId(patientId);
			dietInstructionDto.setPatient(patientDto);
			model.addAttribute("dietInstructionDto", dietInstructionDto);
		}
		model.addAttribute("patient", patient);
		model.addAttribute("serviceInvalidDateRangeMap", serviceInvalidDateRangeMap);
		model.addAttribute("serviceMasterList", serviceMasterRepository.findAll(dietUtility.getServiceMasterSpecification(patient, dailyServiceMaster)));
		model.addAttribute("localDateFormatter", CommonUtility.localDateFormatter);
		model.addAttribute("localDateTimeFormatter", CommonUtility.localDateTimeFormatter);
		return "diet/DietInstruction";
	}

	
	@Override
	public String saveDietInstruction(RedirectAttributes redir, DietInstructionDto dietInstructionDto) {
		boolean isValid = true;
		Patient patient = patientRepository.findById(dietInstructionDto.getPatient().getPatientId()).get();
		if (patient.getPatientStatus() == 2) {
			isValid = false;
			redir.addFlashAttribute("errorMsg", "Patien has been discharged");
		}
		if (!isValid) {
			return "redirect:/diet/patients";
		}		
		LocalDateTime now = LocalDateTime.now();
		User currentUser = commonUtility.getCurrentUser();
		Optional<DietInstruction> dietInstruction = Optional.empty();
		if (ObjectUtils.isNotEmpty(dietInstructionDto.getDietInstructionId()) && dietInstructionDto.getDietInstructionId() > 0) {
			dietInstruction = dietInstructionRepository.findById(dietInstructionDto.getDietInstructionId());
		}		
		
		if(StringUtils.isNotEmpty(dietInstructionDto.getServiceMasterIds())) {
			List<ServiceMasterDto> serviceMasters = new ArrayList<>();
			String[] ids = dietInstructionDto.getServiceMasterIds().split(",");
			for (String id : ids) {
				ServiceMasterDto serviceMasterDto = new ServiceMasterDto();
				serviceMasterDto.setServiceMasterId(Long.valueOf(id));
				serviceMasters.add(serviceMasterDto);;
			}
			dietInstructionDto.setServiceMasters(serviceMasters);
		}
		
		if(StringUtils.isNotEmpty(dietInstructionDto.getDateSelection())) {
			String[] dateSelections = dietInstructionDto.getDateSelection().split(" - ");
			dietInstructionDto.setApplicableFrom(LocalDate.parse(dateSelections[0], CommonUtility.localDateFormatter));
			dietInstructionDto.setApplicableTo(LocalDate.parse(dateSelections[1], CommonUtility.localDateFormatter));
		}
		
		DietInstruction saveDietInstruction = modelMapper.map(dietInstructionDto, DietInstruction.class);
		saveDietInstruction.setApplicableFrom(dietInstructionDto.getApplicableFrom());
		saveDietInstruction.setApplicableTo(dietInstructionDto.getApplicableTo());
		if (dietInstruction.isPresent()) {
			DietInstruction dietInstructionEntity = dietInstruction.get();
			saveDietInstruction.setCreatedOn(dietInstructionEntity.getCreatedOn());
			saveDietInstruction.setCreatedBy(dietInstructionEntity.getCreatedBy());
			saveDietInstruction.setModifiedOn(now);
			saveDietInstruction.setModifiedBy(currentUser.getUserId());
			saveDietInstruction.setCreatedUserHistoryId(dietInstructionEntity.getCreatedUserHistoryId());
			saveDietInstruction.setModifiedUserHistoryId(currentUser.getCurrenUserHistoryId());
		} else {
			saveDietInstruction.setCreatedOn(now);
			saveDietInstruction.setCreatedBy(currentUser.getUserId());
			saveDietInstruction.setModifiedOn(now);
			saveDietInstruction.setModifiedBy(currentUser.getUserId());
			saveDietInstruction.setCreatedUserHistoryId(currentUser.getCurrenUserHistoryId());
			saveDietInstruction.setModifiedUserHistoryId(currentUser.getCurrenUserHistoryId());
		}
		saveDietInstruction.setPatient(patient);
		saveDietInstruction.setDietTypeOralSolid(patient.getDietTypeOralSolid());
		saveDietInstruction.setDietSubType(patient.getDietSubType());
		saveDietInstruction.setExtraLiquid(patient.getExtraLiquid());
		saveDietInstruction = save(saveDietInstruction);
		dietPlanService.prepareDietPlan(List.of(patient), currentUser, false);
		notificationsService.sendDietInstruction(saveDietInstruction);
		return "redirect:/diet/diet-instruction?patientId=" + dietInstructionDto.getPatient().getPatientId();
	}
	
	@Override
	public List<DietInstruction> getDietInstructionData(Long patientId) {
		return dietInstructionRepository.findAllByPatientPatientId(patientId);
	}
	
	@Override
	public String deleteDietInstruction(Long dietInstructionId) {
		Optional<DietInstruction> dietInstruction = dietInstructionRepository.findById(dietInstructionId);
		Long patientId = dietInstruction.get().getPatient().getPatientId();
		dietInstructionRepository.deleteDietInstructionFromDietPlan(dietInstructionId);
		dietInstructionRepository.deleteById(dietInstructionId);
		return "redirect:/diet/diet-instruction?patientId=" + patientId;
	}
}
