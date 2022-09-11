package com.HospitalManagementSystem.entity;

import java.time.LocalDateTime;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.HospitalManagementSystem.entity.master.Department;
import com.HospitalManagementSystem.entity.master.Floor;
import com.HospitalManagementSystem.entity.master.Role;

import lombok.Data;

@Entity
@Table(name = "user")
@Data
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long userId;

	private String username;

	private String password;

	/**
	 * 1 password reset by admin
	 */
	private Integer passwordResetType;

	@ManyToMany(fetch = FetchType.EAGER)
	private Set<Role> roles;

	private String name;

	private String designation;

	private String employeeNumber;

	@OneToOne
	@JoinColumn(name = "department_id")
	private Department department;

	private String emailId;

	private String mobileNumber;

	private Boolean isActive = Boolean.FALSE;

	@OneToOne
	@JoinColumn(name = "floor_id")
	private Floor floor;

	private LocalDateTime createdOn;

	private Long createdBy;

	private LocalDateTime modifiedOn;

	private Long modifiedBy;

	private Long currenUserHistoryId;

	private Long createdUserHistoryId;

	private Long modifiedUserHistoryId;
}