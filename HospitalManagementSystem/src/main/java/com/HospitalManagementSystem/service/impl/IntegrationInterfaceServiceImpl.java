package com.HospitalManagementSystem.service.impl;

import java.util.Map;

import org.apache.commons.collections.MapUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.HospitalManagementSystem.service.IntegrationInterfaceService;
import com.HospitalManagementSystem.service.PatientDetailsService;
import com.HospitalManagementSystem.utility.CommonUtility;

@Service
public class IntegrationInterfaceServiceImpl implements IntegrationInterfaceService {
	
	@Autowired
	private CommonUtility commonUtility;
	
	@Autowired
	private PatientDetailsService patientDetailsService;

	@Override
	public ResponseEntity<String> addPatient(Map<String, String> params) {
		if (MapUtils.isNotEmpty(params)) {
			return patientDetailsService.addPatient(commonUtility.getPatientDto(params));
		}
		return ResponseEntity.ok().body("{\"status\":\"failed\"}");
	}

	@Override
	public ResponseEntity<String> transferPatient(Map<String, String> params) {
		if (MapUtils.isNotEmpty(params)) {
			return patientDetailsService.transferPatient(commonUtility.getPatientDto(params));
		}
		return ResponseEntity.ok().body("{\"status\":\"failed\"}");
	}

	@Override
	public ResponseEntity<String> dischragePatient(Map<String, String> params) {
		if (MapUtils.isNotEmpty(params)) {
			return patientDetailsService.dischragePatient(commonUtility.getPatientDto(params));
		}
		return ResponseEntity.ok().body("{\"status\":\"failed\"}");
	}

}
