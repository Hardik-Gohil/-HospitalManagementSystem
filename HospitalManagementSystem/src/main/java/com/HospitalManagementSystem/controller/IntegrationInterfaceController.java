package com.HospitalManagementSystem.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.HospitalManagementSystem.service.IntegrationInterfaceService;

@RestController
public class IntegrationInterfaceController {

	@Autowired
	private IntegrationInterfaceService integrationInterfaceService;

	@PostMapping("/triggers/addpatient")
	public ResponseEntity<String> addPatient(@RequestParam Map<String, String> params) {
		return integrationInterfaceService.addPatient(params);
	}

	@PostMapping("/triggers/transferPatient")
	public ResponseEntity<String> transferPatient(@RequestParam Map<String, String> params) {
		return integrationInterfaceService.transferPatient(params);
	}

	@PostMapping("/triggers/dischragePatient")
	public ResponseEntity<String> dischragePatient(@RequestParam Map<String, String> params) {
		return integrationInterfaceService.dischragePatient(params);
	}

}