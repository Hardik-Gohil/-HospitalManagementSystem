package com.HospitalManagementSystem.dto;

import lombok.Data;

@Data
public class SpecialNotesByNursingDto {

	private Long specialNotesByNursingId;

	private String value;

	private Boolean isActive = Boolean.FALSE;
	
}