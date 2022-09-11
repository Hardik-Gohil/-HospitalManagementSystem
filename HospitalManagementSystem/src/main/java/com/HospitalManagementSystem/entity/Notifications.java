package com.HospitalManagementSystem.entity;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;

@Entity
@Table(name = "notifications")
@Data
public class Notifications {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long notificationsId;

	@OneToOne
	@JoinColumn(name = "user_id")
	private User user;

	private String code;

	@Column(columnDefinition = "longtext")
	private String templates;

	private Boolean isRead = Boolean.FALSE;
	
	private Long objectId;

	@JsonFormat(pattern = "MM/dd/yyyy hh:mm:ss a")
	private LocalDateTime createdOn;

	private Long createdBy;

	private Long createdUserHistoryId;

	private LocalDateTime modifiedOn;

	private Long modifiedBy;

	private Long modifiedUserHistoryId;
}
