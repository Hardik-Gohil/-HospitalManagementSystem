package com.HospitalManagementSystem.entity.master;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Table(name = "bed")
@Data
public class Bed {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long bedId;

	private String bedCode;

	private String roomNo;

	private String wardName;

	@OneToOne
	@JoinColumn(name = "floor_id")
	private Floor floor;
	
	private Boolean isActive = Boolean.FALSE;
}
