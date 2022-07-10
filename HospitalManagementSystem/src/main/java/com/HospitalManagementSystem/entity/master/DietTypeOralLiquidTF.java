package com.HospitalManagementSystem.entity.master;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Table(name = "diet_type_oral_liquid_tf")
@Data
public class DietTypeOralLiquidTF {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long dietTypeOralLiquidTFId;

	private String value;

	private Boolean isActive = Boolean.FALSE;
}
