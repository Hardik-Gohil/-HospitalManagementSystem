package com.HospitalManagementSystem.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;

import com.HospitalManagementSystem.entity.master.DietSubType;
import com.HospitalManagementSystem.entity.master.DietTypeOralSolid;
import com.HospitalManagementSystem.entity.master.ServiceMaster;
import com.HospitalManagementSystem.utility.CommonUtility;
import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;
import lombok.Getter;

@Entity
@Table(name = "diet_instruction")
@Data
public class DietInstruction {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long dietInstructionId;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "patient_id")
	@JsonIgnore
	private Patient patient;

	@Column(length = 1024)
	private String instruction;

	@ManyToMany(fetch = FetchType.LAZY)
	private List<ServiceMaster> serviceMasters;

	/*
	 * 1 Selected Date
	 * 2 Daily
	 */
	private Integer applicableFor;

	private LocalDate applicableFrom;

	private LocalDate applicableTo;

	@Getter(lombok.AccessLevel.NONE)
	@Transient
	private String dateSelection;

	@Column(columnDefinition = "longtext")
	private String serviceMasterIds;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "diet_type_oral_solid_id")
	private DietTypeOralSolid dietTypeOralSolid;
	
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "diet_sub_type_id")
	private DietSubType dietSubType;
	
	private Boolean extraLiquid = Boolean.FALSE;
	
	private LocalDateTime createdOn;

	private Long createdBy;

	private LocalDateTime modifiedOn;

	private Long modifiedBy;

	private Long createdUserHistoryId;

	private Long modifiedUserHistoryId;
	
	@Getter(lombok.AccessLevel.NONE)
	@Transient
	private String serviceMastersString;
	
	@Getter(lombok.AccessLevel.NONE)
	@Transient
	private String applicableForString;
	
	public String getDateSelection() {
		this.setDateSelection(
				StringUtils.isEmpty(dateSelection) && ObjectUtils.isNotEmpty(applicableFrom) && ObjectUtils.isNotEmpty(applicableTo)
						? applicableFrom.format(CommonUtility.localDateFormatter) + " - " + applicableTo.format(CommonUtility.localDateFormatter)
						: dateSelection);
		return dateSelection;
	}

	public String getServiceMastersString() {
		this.setServiceMastersString(
				StringUtils.isEmpty(serviceMastersString) && CollectionUtils.isNotEmpty(serviceMasters)
						? serviceMasters.stream().map(x -> String.valueOf(x.getService())).collect(Collectors.joining(", "))
						: serviceMastersString);
		return serviceMastersString;
	}

	public String getApplicableForString() {
		this.setApplicableForString(
				StringUtils.isEmpty(applicableForString) && ObjectUtils.isNotEmpty(applicableFor)
						? applicableFor == 1 ? "Selected Date" : "Daily"
						: applicableForString);
		return applicableForString;
	}
}
