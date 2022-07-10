package com.HospitalManagementSystem.dto;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.Data;

@Data
public class BedDto {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long bedId;

	private String bedCode;

	private String roomNo;

	private String wardName;

	private FloorDto floor;
	
	private Boolean isActive = Boolean.FALSE;
	
}
