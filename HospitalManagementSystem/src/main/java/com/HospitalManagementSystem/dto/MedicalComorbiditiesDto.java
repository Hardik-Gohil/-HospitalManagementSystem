package com.HospitalManagementSystem.dto;

import lombok.Data;

@Data
public class MedicalComorbiditiesDto {

	private Long medicalComorbiditiesId;

	private String value;

	private Boolean isActive = Boolean.FALSE;
	
}