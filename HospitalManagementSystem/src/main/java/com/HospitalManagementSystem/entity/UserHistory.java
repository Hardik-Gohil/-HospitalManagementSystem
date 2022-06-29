package com.HospitalManagementSystem.entity;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Table(name = "user_history")
@Data
public class UserHistory {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long userHistoryId;

	private Long userId;

	private String username;

	private String password;

	private Integer passwordResetType;

	private String roles;

	private String name;

	private String designation;

	private String employeeNumber;

	private Long departmentId;

	private String emailId;

	private String mobileNumber;

	private Boolean isActive = Boolean.FALSE;

	private Long floorId;

	private LocalDateTime createdOn;

	private Long createdBy;

	private LocalDateTime modifiedOn;

	private Long modifiedBy;

	private LocalDateTime historyCreatedOn;

	private Long historyCreatedBy;

	private Long createdUserHistoryId;

	private Long modifiedUserHistoryId;
}
