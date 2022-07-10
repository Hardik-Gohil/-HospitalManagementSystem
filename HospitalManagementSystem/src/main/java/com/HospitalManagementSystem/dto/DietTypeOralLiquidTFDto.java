package com.HospitalManagementSystem.dto;

import lombok.Data;

@Data
public class DietTypeOralLiquidTFDto {

	private Long dietTypeOralLiquidTFId;

	private String value;

	private Boolean isActive = Boolean.FALSE;
	
}