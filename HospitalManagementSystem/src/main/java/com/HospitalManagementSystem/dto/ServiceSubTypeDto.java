package com.HospitalManagementSystem.dto;

import lombok.Data;

@Data
public class ServiceSubTypeDto {

	private Long serviceSubTypeId;

	private String serviceSubTypeName;

	private Integer serviceType;

	private Boolean isActive = Boolean.FALSE;
}
