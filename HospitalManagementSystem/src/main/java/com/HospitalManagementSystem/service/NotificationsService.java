package com.HospitalManagementSystem.service;

import org.springframework.data.jpa.datatables.mapping.DataTablesInput;
import org.springframework.http.ResponseEntity;

import com.HospitalManagementSystem.dto.NotificationsDataTablesOutputDto;
import com.HospitalManagementSystem.entity.DietInstruction;

public interface NotificationsService {

	void sendAddPatient(Long patientId);

	void sendUpdatePatient(Long patientId, String updatedFields);

	void sendDietInstruction(DietInstruction dietInstruction);

	void sendAdHocOrder(Long adHocOrderId);

	void sendPatientDischarge(Long patientId);

	void sendPatientTransferred(Long patientId, Long oldBedId);

	NotificationsDataTablesOutputDto notificationsData(DataTablesInput input);

	Integer notificationsCount();

	ResponseEntity<String> updateReadNotifications(Long notificationsId);

}
