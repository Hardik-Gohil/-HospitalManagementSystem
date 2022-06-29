package com.HospitalManagementSystem.dto;

import lombok.Data;

@Data
public class FloorDto {

	private Long floorId;

	private String floorName;

	private Boolean isActive = Boolean.FALSE;

}
