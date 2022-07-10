package com.HospitalManagementSystem.entity.master;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Table(name = "department")
@Data
public class Department {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long departmentId;

	private String departmentName;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "role_id")
	private Role role;

	private Boolean isActive = Boolean.FALSE;
}
