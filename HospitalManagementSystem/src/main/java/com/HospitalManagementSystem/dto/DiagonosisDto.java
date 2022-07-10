package com.HospitalManagementSystem.dto;

import lombok.Data;

@Data
public class DiagonosisDto {

	private Long diagonosisId;

	private String value;

	private Boolean isActive = Boolean.FALSE;
	
}