package com.HospitalManagementSystem.dto;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.datatables.mapping.DataTablesInput;

import com.fasterxml.jackson.annotation.JsonFormat;

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

//	@JsonFormat(pattern = "MM/dd/yyyy hh:mm:ss a", shape = JsonFormat.Shape.STRING)
	private String orderPlacedStartDateAndTime;
	
//	@JsonFormat(pattern = "MM/dd/yyyy hh:mm:ss a", shape = JsonFormat.Shape.STRING)
	private String orderPlacedEndDateAndTime;

	private List<Integer> delivered;

	private List<Integer> statusList;

}
