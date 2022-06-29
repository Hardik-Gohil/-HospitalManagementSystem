package com.HospitalManagementSystem.dto;

import lombok.Data;

@Data
public class DepartmentDto {

	private Long departmentId;

	private String departmentName;

	private Boolean isActive = Boolean.FALSE;

}
