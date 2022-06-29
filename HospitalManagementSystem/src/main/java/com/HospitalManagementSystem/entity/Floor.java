package com.HospitalManagementSystem.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Table(name = "floor")
@Data
public class Floor {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long floorId;

	private String floorName;

	private Boolean isActive = Boolean.FALSE;
}
