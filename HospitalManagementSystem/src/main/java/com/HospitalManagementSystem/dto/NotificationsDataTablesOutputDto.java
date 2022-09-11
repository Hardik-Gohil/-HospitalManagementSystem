package com.HospitalManagementSystem.dto;

import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;

import com.HospitalManagementSystem.entity.Notifications;

import lombok.Data;

@Data
public class NotificationsDataTablesOutputDto {

	private DataTablesOutput<Notifications> data;

	private Integer count;

}