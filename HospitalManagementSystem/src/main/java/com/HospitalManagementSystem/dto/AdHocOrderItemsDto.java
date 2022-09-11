package com.HospitalManagementSystem.dto;

import lombok.Data;

@Data
public class AdHocOrderItemsDto {

	private Long adHocOrderItemsId;

	private AdHocItemsDto adHocItems;

	private Integer quantity;
}
