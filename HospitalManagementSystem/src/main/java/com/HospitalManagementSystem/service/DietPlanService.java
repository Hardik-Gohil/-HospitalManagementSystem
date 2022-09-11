package com.HospitalManagementSystem.service;

import java.util.List;

import org.springframework.data.jpa.datatables.mapping.DataTablesInput;
import org.springframework.http.ResponseEntity;

import com.HospitalManagementSystem.dto.PatientDataTablesOutputDto;
import com.HospitalManagementSystem.entity.Patient;
import com.HospitalManagementSystem.entity.User;

public interface DietPlanService {

	void prepareDietPlan(List<Patient> patientList, User currentUser, Boolean prepareAll);

	PatientDataTablesOutputDto getDietPlanData(DataTablesInput input, String dateSelection, boolean extraLiquid, boolean isVip);

	ResponseEntity<String> updateDietPlanItem(Long dietPlanId, String item);

	void prepareDietPlan();

}
