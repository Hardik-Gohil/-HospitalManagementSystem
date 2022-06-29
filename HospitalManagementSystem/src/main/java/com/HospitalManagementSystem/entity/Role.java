package com.HospitalManagementSystem.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Table(name = "role")
@Data
public class Role {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long roleId;

	private String name;

//	@ManyToMany(mappedBy = "roles", fetch = FetchType.LAZY)
//	private Set<User> users;

}
