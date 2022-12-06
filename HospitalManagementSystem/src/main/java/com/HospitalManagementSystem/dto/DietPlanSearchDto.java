package com.HospitalManagementSystem.dto;

import java.util.List;

import org.springframework.data.jpa.datatables.mapping.DataTablesInput;

import lombok.Data;

@Data
public class DietPlanSearchDto extends DataTablesInput {

	private String searchText;
	
	private String searchItemText;

	private String dateSelection;

	private List<Long> medicalComorbiditiesIds;

	private List<Long> floorIds;

	private List<Long> bedIds;

	private List<Long> dietTypeOralSolidIds;

	private List<Long> dietTypeOralLiquidTFIds;

	private List<Long> dietSubTypeIds;

	private Boolean isVip = Boolean.FALSE;

	private Boolean extraLiquid = Boolean.FALSE;

	private List<Long> serviceMasterIds;
}
