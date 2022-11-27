package com.HospitalManagementSystem.dto;

import java.util.List;

import org.springframework.data.jpa.datatables.mapping.DataTablesInput;

import lombok.Data;

@Data
public class AdHocSearchDto extends DataTablesInput {

	private String searchText;

	private List<Integer> serviceType;

	private List<Long> medicalComorbiditiesIds;

	private List<Long> floorIds;

	private List<Long> bedIds;

	private List<Long> dietTypeOralSolidIds;
	
	private List<Long> dietTypeOralLiquidTFIds;
	
	private List<Long> dietSubTypeIds;

	private Boolean isVip = Boolean.FALSE;

	private String orderPlacedDateAndTime;

	private List<Integer> delivered;

	private List<Integer> statusList;

}
