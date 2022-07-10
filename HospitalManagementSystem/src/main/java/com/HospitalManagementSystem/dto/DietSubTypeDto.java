package com.HospitalManagementSystem.dto;

import lombok.Data;

@Data
public class DietSubTypeDto {

	private Long dietSubTypeId;

	private String value;

	private Boolean isActive = Boolean.FALSE;

	private DietTypeOralLiquidTFDto dietTypeOralLiquidTF;
	
}