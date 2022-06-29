package com.HospitalManagementSystem.dto;

import javax.persistence.Transient;

import lombok.Data;

@Data
public class UserDto {

	private Long userId;

	private String username;

	@Transient
	private String passwordConfirm;

	private String name;

	private String designation;

	private String employeeNumber;

	private DepartmentDto department;

	private String emailId;

	private String mobileNumber;

	private Boolean isActive = Boolean.FALSE;

	private FloorDto floor;

}
