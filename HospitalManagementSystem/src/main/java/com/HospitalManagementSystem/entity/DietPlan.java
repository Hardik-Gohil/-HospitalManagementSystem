package com.HospitalManagementSystem.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.HospitalManagementSystem.entity.master.ServiceItems;
import com.HospitalManagementSystem.entity.master.ServiceMaster;
import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;

@Entity
@Table(name = "diet_plan")
@Data
public class DietPlan {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long dietPlanId;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "patient_id")
	@JsonIgnore
	private Patient patient;
	
	@OneToOne
	@JoinColumn(name = "service_master_id")
	private ServiceMaster serviceMaster;

	@ManyToMany(fetch = FetchType.LAZY)
	private List<DietInstruction> dietInstructions;

	@ManyToMany(fetch = FetchType.EAGER)
	private List<ServiceItems> serviceItems;
	
	private String item;

	private String originalItem;

	private LocalDate dietDate;

	private LocalDateTime createdOn;

	private Long createdBy;
	
	private Long createdUserHistoryId;
	
	private LocalDateTime modifiedOn;

	private Long modifiedBy;
	
	private Long modifiedUserHistoryId;
}
