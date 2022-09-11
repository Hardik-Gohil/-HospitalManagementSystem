package com.HospitalManagementSystem.service.impl;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

import javax.persistence.criteria.Predicate;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.datatables.mapping.DataTablesInput;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.HospitalManagementSystem.dto.NotificationsDataTablesOutputDto;
import com.HospitalManagementSystem.entity.AdHocOrder;
import com.HospitalManagementSystem.entity.DietInstruction;
import com.HospitalManagementSystem.entity.Notifications;
import com.HospitalManagementSystem.entity.Patient;
import com.HospitalManagementSystem.entity.User;
import com.HospitalManagementSystem.entity.master.Bed;
import com.HospitalManagementSystem.repository.AdHocOrderRepository;
import com.HospitalManagementSystem.repository.BedRepository;
import com.HospitalManagementSystem.repository.NotificationsDataTablesRepository;
import com.HospitalManagementSystem.repository.NotificationsRepository;
import com.HospitalManagementSystem.repository.PatientRepository;
import com.HospitalManagementSystem.repository.UserRepository;
import com.HospitalManagementSystem.service.NotificationsService;
import com.HospitalManagementSystem.utility.CommonUtility;
import com.HospitalManagementSystem.utility.NotificationsUtility;

@Service
public class NotificationsServiceImpl implements NotificationsService {

	@Autowired
	private NotificationsUtility notificationsUtility;
	@Autowired
	private CommonUtility commonUtility;

	@Autowired
	private NotificationsRepository notificationsRepository;
	@Autowired
	private NotificationsDataTablesRepository notificationsDataTablesRepository;
	@Autowired
	private PatientRepository patientRepository;
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private AdHocOrderRepository adHocOrderRepository;
	@Autowired
	private BedRepository bedRepository;
	
	private void saveAll(List<Notifications> notificationsList) {
		if (CollectionUtils.isNotEmpty(notificationsList)) {
			notificationsRepository.saveAll(notificationsList);
		}
	}

	@Override
	public void sendAddPatient(Long patientId) {
		LocalDateTime now = LocalDateTime.now();
		User currentUser = commonUtility.getCurrentUser();
		Optional<Patient> patientOptional = patientRepository.findById(patientId);
		if (patientOptional.isPresent()) {
			Patient patient = patientOptional.get();
			Map<String, String> params = new HashMap<>();
			params.put("$PatientName", patient.getPatientName());
			params.put("$BedCd", patient.getBedString());
			String templates = notificationsUtility.getTemplates(NotificationsUtility.ADD_PATIENT, params);
			List<Notifications> notificationsList = new ArrayList<>();	
			List<User> users = userRepository.findAllByIsActive(true);
			
			for (User user : users) {
				boolean isadd = true;
				if (user.getRoles().stream().filter(role -> role.getName().equals("ROLE_NURSING")).findFirst().isPresent()) {
					isadd = user.getFloor().getFloorId() == patient.getBed().getFloor().getFloorId();
				}
				if(isadd) {
					notificationsList.add(notificationsUtility.getNotifications(user, "ADD_PATIENT", templates, now, currentUser, patientId));
				}
			}
			saveAll(notificationsList);
		}
	}

	@Override
	public void sendUpdatePatient(Long patientId, String updatedFields) {
		LocalDateTime now = LocalDateTime.now();
		User currentUser = commonUtility.getCurrentUser();
		Optional<Patient> patientOptional = patientRepository.findById(patientId);
		if (patientOptional.isPresent()) {
			Patient patient = patientOptional.get();
			Map<String, String> params = new HashMap<>();
			params.put("$UpdatedFields", updatedFields);
			params.put("$PatientName", patient.getPatientName());
			params.put("$UpdatedDateTime", now.format(CommonUtility.localDateTimeFormatter));
			String templates = notificationsUtility.getTemplates(NotificationsUtility.UPDATE_PATIENT, params);
			List<Notifications> notificationsList = new ArrayList<>();	
			List<User> users = userRepository.findAllByIsActive(true);
			
			for (User user : users) {
				boolean isadd = true;
				if (user.getRoles().stream().filter(role -> role.getName().equals("ROLE_NURSING")).findFirst().isPresent()) {
					isadd = user.getFloor().getFloorId() == patient.getBed().getFloor().getFloorId();
				}
				if(isadd) {
					notificationsList.add(notificationsUtility.getNotifications(user, "UPDATE_PATIENT", templates, now, currentUser, patientId));
				}
			}
			saveAll(notificationsList);
		}
	}

	@Override
	public void sendDietInstruction(DietInstruction dietInstruction) {
		LocalDateTime now = LocalDateTime.now();
		User currentUser = commonUtility.getCurrentUser();
		Map<String, String> params = new HashMap<>();
		params.put("$Services", dietInstruction.getServiceMastersString());
		params.put("$PatientName", dietInstruction.getPatient().getPatientName());
		params.put("$BedCd", dietInstruction.getPatient().getBedString());
		params.put("$Action", Objects.equals(dietInstruction.getCreatedOn(), dietInstruction.getModifiedOn()) ? "Updated" : "Added");
		params.put("$DateTime", now.format(CommonUtility.localDateTimeFormatter));
		String templates = notificationsUtility.getTemplates(NotificationsUtility.DIET_INSTRUCTION, params);
		List<Notifications> notificationsList = new ArrayList<>();	
		List<User> users = userRepository.findAllByIsActive(true);
		
		for (User user : users) {
			boolean isadd = false;
			if (user.getRoles().stream().filter(role -> !role.getName().equals("ROLE_NURSING")).findFirst().isPresent()) {
				isadd = true;
			}
			if(isadd) {
				notificationsList.add(notificationsUtility.getNotifications(user, "DIET_INSTRUCTION", templates, now, currentUser, dietInstruction.getPatient().getPatientId()));
			}
		}
		saveAll(notificationsList);
	}

	@Override
	public void sendAdHocOrder(Long adHocOrderId) {
		LocalDateTime now = LocalDateTime.now();
		User currentUser = commonUtility.getCurrentUser();
		Optional<AdHocOrder> adHocOrderOptional = adHocOrderRepository.findById(adHocOrderId);
		if (adHocOrderOptional.isPresent()) {
			AdHocOrder adHocOrder = adHocOrderOptional.get();
			Map<String, String> params = new HashMap<>();
			params.put("$ServiceType", adHocOrder.getServiceType() == 1 ? "Immediate Service" : "AdHoc Service");
			params.put("$Action", adHocOrder.getOrderStatus() == 1 ? "Created" : adHocOrder.getOrderStatus() == 2 ? "Cancelled" : "Deleted ");
			params.put("$PatientName", adHocOrder.getPatient().getPatientName());
			params.put("$BedCd", adHocOrder.getPatient().getBedString());
			params.put("$ExpectedDeliveryTime", adHocOrder.getServiceDeliveryDateTime().format(CommonUtility.localDateTimeFormatter));
			String templates = notificationsUtility.getTemplates(NotificationsUtility.ADHOC_ORDER, params);
			List<Notifications> notificationsList = new ArrayList<>();	
			List<User> users = userRepository.findAllByIsActive(true);
			
			for (User user : users) {
				boolean isadd = true;
				if (user.getRoles().stream().filter(role -> role.getName().equals("ROLE_NURSING")).findFirst().isPresent()) {
					isadd = user.getFloor().getFloorId() == adHocOrder.getPatient().getBed().getFloor().getFloorId();
				}
				if(isadd) {
					notificationsList.add(notificationsUtility.getNotifications(user, "ADHOC_ORDER", templates, now, currentUser, adHocOrder.getPatient().getPatientId()));
				}
			}
			saveAll(notificationsList);
		}
	}

	@Override
	public void sendPatientDischarge(Long patientId) {
		LocalDateTime now = LocalDateTime.now();
		User currentUser = commonUtility.getCurrentUser();
		Optional<Patient> patientOptional = patientRepository.findById(patientId);
		if (patientOptional.isPresent()) {
			Patient patient = patientOptional.get();
			Map<String, String> params = new HashMap<>();
			params.put("$PatientName", patient.getPatientName());
			params.put("$BedCd", patient.getBedString());
			params.put("$DischargedTime", patient.getDischargedTime().format(CommonUtility.localDateTimeFormatter));
			String templates = notificationsUtility.getTemplates(NotificationsUtility.PATIENT_DISCHARGE, params);
			List<Notifications> notificationsList = new ArrayList<>();	
			List<User> users = userRepository.findAllByIsActive(true);
			
			for (User user : users) {
				boolean isadd = true;
				if (user.getRoles().stream().filter(role -> role.getName().equals("ROLE_NURSING")).findFirst().isPresent()) {
					isadd = user.getFloor().getFloorId() == patient.getBed().getFloor().getFloorId();
				}
				if(isadd) {
					notificationsList.add(notificationsUtility.getNotifications(user, "PATIENT_DISCHARGE", templates, now, currentUser, patientId));
				}
			}
			saveAll(notificationsList);
		}
	}

	@Override
	public void sendPatientTransferred(Long patientId, Long oldBedId) {
		LocalDateTime now = LocalDateTime.now();
		User currentUser = commonUtility.getCurrentUser();
		Optional<Patient> patientOptional = patientRepository.findById(patientId);
		Optional<Bed> bedOptional = bedRepository.findById(oldBedId);
		if (patientOptional.isPresent()) {
			Patient patient = patientOptional.get();
			Bed bed = bedOptional.get();
			Map<String, String> params = new HashMap<>();
			params.put("$PatientName", patient.getPatientName());
			params.put("$BedCd", bed.getBedString());
			params.put("$UpdatedBedCd", patient.getBedString());
			params.put("$TransferredDateTime",now.format(CommonUtility.localDateTimeFormatter));
			String templates = notificationsUtility.getTemplates(NotificationsUtility.PATIENT_TRANSFERRED, params);
			List<Notifications> notificationsList = new ArrayList<>();	
			List<User> users = userRepository.findAllByIsActive(true);
			
			for (User user : users) {
				boolean isadd = true;
				if (user.getRoles().stream().filter(role -> role.getName().equals("ROLE_NURSING")).findFirst().isPresent()) {
					isadd = user.getFloor().getFloorId() == patient.getBed().getFloor().getFloorId() || user.getFloor().getFloorId() == bed.getFloor().getFloorId();
				}
				if(isadd) {
					notificationsList.add(notificationsUtility.getNotifications(user, "PATIENT_TRANSFERRED", templates, now, currentUser, patientId));
				}
			}
			saveAll(notificationsList);
		}
	}

	@Override
	public NotificationsDataTablesOutputDto notificationsData(DataTablesInput input) {
		User currentUser = commonUtility.getCurrentUser();
		Specification<Notifications> additionalSpecification = (root, query, criteriaBuilder) -> {
			List<Predicate> predicates = new ArrayList<>();
			predicates.add(criteriaBuilder.equal(root.get("user").get("userId"), currentUser.getUserId()));
			return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
		};
		NotificationsDataTablesOutputDto notificationsDataTablesOutputDto = new NotificationsDataTablesOutputDto();
		notificationsDataTablesOutputDto.setData(notificationsDataTablesRepository.findAll(input, additionalSpecification));
		notificationsDataTablesOutputDto.setCount(notificationsRepository.countByUserUserId(currentUser.getUserId()));
		return notificationsDataTablesOutputDto;
	}

	@Override
	public Integer notificationsCount() {
		User currentUser = commonUtility.getCurrentUser();
		return notificationsRepository.countByUserUserIdAndIsRead(currentUser.getUserId(), Boolean.FALSE);
	}

	@Override
	public ResponseEntity<String> updateReadNotifications(Long notificationsId) {
		User currentUser = commonUtility.getCurrentUser();
		Optional<Notifications> notifications = notificationsRepository.findById(notificationsId);
		if (notifications.isPresent()) {
			Notifications notificationsEntity = notifications.get();
			notificationsEntity.setIsRead(true);
			notificationsEntity.setModifiedOn(LocalDateTime.now());
			notificationsEntity.setModifiedBy(currentUser.getUserId());
			notificationsEntity.setModifiedUserHistoryId(currentUser.getCurrenUserHistoryId());
			notificationsRepository.save(notificationsEntity);
			return ResponseEntity.ok().body("Notifications has been updated");
		}
		return ResponseEntity.badRequest().body("Notifications not found");		
	}

}
