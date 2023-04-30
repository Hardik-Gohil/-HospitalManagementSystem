package com.HospitalManagementSystem.entity.master;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Table(name = "diet_type_oral_solid")
@Data
public class DietTypeOralSolid implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long dietTypeOralSolidId;

	private String value;

	private Boolean isActive = Boolean.FALSE;
}
