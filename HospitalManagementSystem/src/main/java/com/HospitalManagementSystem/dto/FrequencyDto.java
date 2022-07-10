package com.HospitalManagementSystem.dto;

import lombok.Data;

@Data
public class FrequencyDto {

	private Long frequencyId;

	private String valueStr;

	private Integer value;

	private Boolean isActive = Boolean.FALSE;
	
}