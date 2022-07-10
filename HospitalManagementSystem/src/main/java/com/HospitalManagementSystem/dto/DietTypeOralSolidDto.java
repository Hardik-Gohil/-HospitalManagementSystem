package com.HospitalManagementSystem.dto;

import lombok.Data;

@Data
public class DietTypeOralSolidDto {

	private Long dietTypeOralSolidId;

	private String value;

	private Boolean isActive = Boolean.FALSE;
	
}
