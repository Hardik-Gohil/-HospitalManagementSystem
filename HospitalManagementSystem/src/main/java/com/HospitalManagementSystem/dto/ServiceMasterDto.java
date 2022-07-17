package com.HospitalManagementSystem.dto;

import java.time.LocalTime;

import com.HospitalManagementSystem.enums.YesNo;

import lombok.Data;

@Data
public class ServiceMasterDto {

	private Long serviceMasterId;

	private String service;

	private LocalTime fromTime;

	private LocalTime toTime;

	private YesNo fullDiet;

	private YesNo softDiet;

	private YesNo semiSolidDiet;

	private YesNo extraLiquid;

	private YesNo clearLiquids;

	private YesNo allLiquidsOrally;

	private YesNo Bariatrics;

	private YesNo rtFeeding;

	private YesNo pegFeeding;

	private YesNo ngFeeding;

	private YesNo jjFeeding;

	private YesNo clearLiquidsThroughTubeFeeding;

	private Boolean isActive = Boolean.FALSE;

}
