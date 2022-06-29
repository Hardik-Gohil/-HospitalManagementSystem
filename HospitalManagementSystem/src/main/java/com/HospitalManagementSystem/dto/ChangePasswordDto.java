package com.HospitalManagementSystem.dto;

import lombok.Data;

@Data
public class ChangePasswordDto {

	private Long userId;

	private Integer passwordResetType;

	private String oldPassword;

	private String password;

	private String confirmPassword;

}
