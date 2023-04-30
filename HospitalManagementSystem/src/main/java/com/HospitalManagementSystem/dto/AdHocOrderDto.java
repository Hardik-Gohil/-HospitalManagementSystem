package com.HospitalManagementSystem.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;

@Data
public class AdHocOrderDto {

	private Long adHocOrderId;

	private PatientDto patient;
	
	private String orderId;
	
	/**
	 * 1 Immediate Service 
	 * 2 AdHoc Service
	 */
	private Integer serviceType;
	
	private ServiceSubTypeDto serviceSubType;

	/**
	 * 1 Active 2 Cancel 3 Deleted
	 */
	private Integer orderStatus;

	@JsonFormat(pattern = "MM/dd/yyyy hh:mm:ss a")
	private LocalDateTime serviceDeliveryDateTime;

	private List<AdHocOrderItemsDto> adHocOrderItems;
	
	private BigDecimal totalRate;

	private String remarks;
	
	private String nursingName;
	
	private Boolean chargable = Boolean.FALSE;

	@JsonFormat(pattern = "MM/dd/yyyy hh:mm:ss a")
	private LocalDateTime createdOn;

	private Long createdBy;

	private LocalDateTime modifiedOn;

	private Long modifiedBy;

	private Long createdUserHistoryId;

	private Long modifiedUserHistoryId;

}
