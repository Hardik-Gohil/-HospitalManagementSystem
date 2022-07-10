package com.HospitalManagementSystem.service;

import java.util.Map;

import org.springframework.http.ResponseEntity;

public interface IntegrationInterfaceService {

	ResponseEntity<String> addPatient(Map<String, String> params);

	ResponseEntity<String> transferPatient(Map<String, String> params);

	ResponseEntity<String> dischragePatient(Map<String, String> params);

}
