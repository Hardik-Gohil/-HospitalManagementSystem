package com.HospitalManagementSystem.entity.master;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Table(name = "medical_comorbidities")
@Data
public class MedicalComorbidities {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long medicalComorbiditiesId;

	private String value;

	private Boolean isActive = Boolean.FALSE;
}
