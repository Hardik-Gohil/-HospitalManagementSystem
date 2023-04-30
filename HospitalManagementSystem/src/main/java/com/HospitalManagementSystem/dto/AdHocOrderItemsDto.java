package com.HospitalManagementSystem.dto;

import java.math.BigDecimal;

import lombok.Data;

@Data
public class AdHocOrderItemsDto {

	private Long adHocOrderItemsId;

	private AdHocItemsDto adHocItems;

	private Integer quantity;
	
	private BigDecimal itemRate;
	
	private BigDecimal totalRate;
}
