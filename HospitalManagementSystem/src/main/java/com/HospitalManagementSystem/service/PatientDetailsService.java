package com.HospitalManagementSystem.service;

import org.springframework.data.jpa.datatables.mapping.DataTablesInput;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.HospitalManagementSystem.dto.PatientDataTablesOutputDto;
import com.HospitalManagementSystem.dto.PatientDto;
import com.HospitalManagementSystem.entity.Patient;

public interface PatientDetailsService {

	String getPatientDetails(Long patientId, Model model);

	PatientDataTablesOutputDto getPatientData(DataTablesInput input, Integer patientStatus, boolean nbm, boolean extraLiquid, boolean startServiceImmediately, boolean isVip);
	
	String savePatientDetails(RedirectAttributes redir, PatientDto patientDto);
	
	ResponseEntity<String> addPatient(PatientDto patientDto);
	
	ResponseEntity<String> transferPatient(PatientDto patientDto);

	ResponseEntity<String> dischragePatient(PatientDto patientDto);

	Patient save(Patient patient);

	String checkUniqueIpNumber(String ipNumber, Long patientId);

	ResponseEntity<String> deletePatient(Long patientId);

}
