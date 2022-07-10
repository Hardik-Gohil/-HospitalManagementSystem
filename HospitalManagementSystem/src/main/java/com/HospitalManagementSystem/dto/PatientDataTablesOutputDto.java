package com.HospitalManagementSystem.dto;

import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;

import com.HospitalManagementSystem.entity.Patient;

import lombok.Data;

@Data
public class PatientDataTablesOutputDto {

	private DataTablesOutput<Patient> data;

	private Integer count;

}
