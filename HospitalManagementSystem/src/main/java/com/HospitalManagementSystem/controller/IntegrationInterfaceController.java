package com.HospitalManagementSystem.controller;

import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class IntegrationInterfaceController {

	@PostMapping("/triggers/addpatient")
	public ResponseEntity<String> addPatient(@RequestParam Map<String, String> params) throws UnsupportedEncodingException {
		System.out.println(java.net.URLDecoder.decode(params.toString(), StandardCharsets.UTF_8.name()));
		return ResponseEntity.ok().body("data received");
	}

	@PostMapping("/triggers/transferPatient")
	public ResponseEntity<String> transferPatient(@RequestParam Map<String, String> params) throws UnsupportedEncodingException {
		System.out.println(java.net.URLDecoder.decode(params.toString(), StandardCharsets.UTF_8.name()));
		return ResponseEntity.ok().body("data received");
	}

	@PostMapping("/triggers/dischragePatient")
	public ResponseEntity<String> dischragePatient(@RequestParam Map<String, String> params) throws UnsupportedEncodingException {
		System.out.println(java.net.URLDecoder.decode(params.toString(), StandardCharsets.UTF_8.name()));
		return ResponseEntity.ok().body("data received");
	}

}