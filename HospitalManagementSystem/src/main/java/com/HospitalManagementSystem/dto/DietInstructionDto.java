package com.HospitalManagementSystem.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import com.HospitalManagementSystem.entity.master.DietSubType;
import com.HospitalManagementSystem.entity.master.DietTypeOralSolid;

import lombok.Data;

@Data
public class DietInstructionDto {

	private Long dietInstructionId;

	private PatientDto patient;

	private String instruction;

	private List<ServiceMasterDto> serviceMasters;

	/*
	 * 1 Selected Date
	 * 2 Daily
	 */
	private Integer applicableFor;

	private LocalDate applicableFrom;

	private LocalDate applicableTo;
	
	private String dateSelection;

	private String serviceMasterIds;
	
	private DietTypeOralSolid dietTypeOralSolid;
	
	private DietSubType dietSubType;
	
	private Boolean extraLiquid = Boolean.FALSE;

	private LocalDateTime createdOn;

	private Long createdBy;

	private LocalDateTime modifiedOn;

	private Long modifiedBy;

	private Long createdUserHistoryId;

	private Long modifiedUserHistoryId;

}
