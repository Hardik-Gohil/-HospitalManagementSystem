package com.HospitalManagementSystem.dto;

import com.HospitalManagementSystem.enums.YesNo;

import lombok.Data;

@Data
public class AdHocItemsDto {

	private Long adHocItemsId;

	private String itemName;

	private YesNo fullDiet;
	private YesNo softDiet;
	private YesNo semiSolidDiet;

	private YesNo normal;
	private YesNo diabeticDiet;
	private YesNo renal;
	private YesNo hypertensive;
	private YesNo saltFree;
	private YesNo fatFree;

	private Boolean isActive = Boolean.FALSE;
}
