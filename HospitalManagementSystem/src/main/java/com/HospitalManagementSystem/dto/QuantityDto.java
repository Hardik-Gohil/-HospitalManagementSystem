package com.HospitalManagementSystem.dto;

import lombok.Data;

@Data
public class QuantityDto {

	private Long quantityId;

	private String valueStr;

	private Integer value;

	private Boolean isActive = Boolean.FALSE;
	
}
