package com.HospitalManagementSystem.entity;

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

import com.HospitalManagementSystem.entity.master.Bed;
import com.HospitalManagementSystem.entity.master.Diagonosis;
import com.HospitalManagementSystem.entity.master.DietSubType;
import com.HospitalManagementSystem.entity.master.DietTypeOralLiquidTF;
import com.HospitalManagementSystem.entity.master.DietTypeOralSolid;
import com.HospitalManagementSystem.entity.master.Frequency;
import com.HospitalManagementSystem.entity.master.MedicalComorbidities;
import com.HospitalManagementSystem.entity.master.Quantity;
import com.HospitalManagementSystem.entity.master.SpecialNotesByNursing;
import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;
import lombok.Getter;

@Entity
@Table(name = "patient")
@Data
public class Patient {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long patientId;
	
	private String umrNumber;
	
	private String ipNumber;
	
	private String room;
	
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "bed_id")
	private Bed bed;
	
	private String bedStatus;
	
	private String patientName;
	
	private String age;
	
	private String gender;
	
	private String allergicto;
	
	private String doctor;
	
	private String admissionType;
	
	private String procedureStr;
	
	private String paymentType;
	
	@JsonFormat(pattern = "MM/dd/yyyy hh:mm:ss a")
	private LocalDateTime admittedDate;
	
	@JsonFormat(pattern = "MM/dd/yyyy hh:mm:ss a")
	private LocalDateTime dischargedTime;
	
	private String billStatus;
	
	private Boolean nbm = Boolean.FALSE;
	
	private Boolean extraLiquid = Boolean.FALSE;
	
	private Boolean startServiceImmediately = Boolean.FALSE;
	
	private Boolean isVip = Boolean.FALSE;
	
	private Boolean showUpdated = Boolean.FALSE;
	
	private String nursingName;
	
	private String employeeNo;
	
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "diet_type_oral_solid_id")
	private DietTypeOralSolid dietTypeOralSolid;
	
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "diet_type_oral_liquid_tf_id")
	private DietTypeOralLiquidTF dietTypeOralLiquidTF;
	
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "diet_sub_type_id")
	private DietSubType dietSubType;
	
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "quantity_id")
	private Quantity quantity;
	
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "frequency_id")
	private Frequency frequency;
	
	@ManyToMany(fetch = FetchType.LAZY)
	private List<MedicalComorbidities> medicalComorbidities;
	
	@ManyToMany(fetch = FetchType.LAZY)
	private List<Diagonosis> diagonosis;
	
	@ManyToMany(fetch = FetchType.LAZY)
	private List<SpecialNotesByNursing> specialNotesByNursing;
	
	private String othersSpecialNotesByNursing;
	
	@Column(columnDefinition = "longtext")
	private String medicalComorbiditiesIds;
	
	@Column(columnDefinition = "longtext")
	private String diagonosisIds;

	@Column(columnDefinition = "longtext")
	private String specialNotesByNursingIds;
	
	@Getter(lombok.AccessLevel.NONE)
	@Transient
	private String bedString;
	
	@Getter(lombok.AccessLevel.NONE)
	@Transient
	private String medicalComorbiditiesString;
	
	@Getter(lombok.AccessLevel.NONE)
	@Transient
	private String diagonosisString;
	
	@Getter(lombok.AccessLevel.NONE)
	@Transient
	private String specialNotesByNursingString;
	
	@Getter(lombok.AccessLevel.NONE)
	@Transient
	private String dietTypeSolidLiquidQuantityFrequencyString;
	
	@Transient
	private List<DietPlan> dietPlans;
	
	/**
	 * 0 New
	 * 1 Active
	 * 2 Discharged
	 */
	private Integer patientStatus;
	
	private LocalDateTime createdOn;

	private Long createdBy;

	private LocalDateTime modifiedOn;

	private Long modifiedBy;

	private Long createdUserHistoryId;

	private Long modifiedUserHistoryId;
	
	private String ipAddress;
	
	
	public String getBedString() {
		this.setBedString(StringUtils.stripToEmpty(bed.getWardName()) + "/"
				+ StringUtils.stripToEmpty(ObjectUtils.isNotEmpty(bed.getFloor()) ? (bed.getFloor().getFloorName() + "/") : "")
				+ StringUtils.stripToEmpty(bed.getBedCode()));
		return bedString;
	}

	public String getMedicalComorbiditiesString() {
		this.setMedicalComorbiditiesString(
				StringUtils.isEmpty(medicalComorbiditiesString) && CollectionUtils.isNotEmpty(medicalComorbidities)
						? medicalComorbidities.stream().map(x -> String.valueOf(x.getValue())).collect(Collectors.joining(", "))
						: medicalComorbiditiesString);
		return medicalComorbiditiesString;
	}
	
	public String getDiagonosisString() {
		this.setDiagonosisString(
				StringUtils.isEmpty(diagonosisString) && CollectionUtils.isNotEmpty(diagonosis)
						? diagonosis.stream().map(x -> String.valueOf(x.getValue())).collect(Collectors.joining(", "))
						: diagonosisString);
		return diagonosisString;
	}

	public String getSpecialNotesByNursingString() {
		this.setSpecialNotesByNursingString(StringUtils.isEmpty(specialNotesByNursingString) && CollectionUtils.isNotEmpty(specialNotesByNursing)
				? specialNotesByNursing.stream().map(x -> String.valueOf(x.getValue())).collect(Collectors.joining(", "))
				: specialNotesByNursingString);
		if (StringUtils.isNotEmpty(othersSpecialNotesByNursing)) {
			if (StringUtils.isNotEmpty(specialNotesByNursingString)) {
				this.setSpecialNotesByNursingString(specialNotesByNursingString + ", " + othersSpecialNotesByNursing);
			} else {
				this.setSpecialNotesByNursingString(othersSpecialNotesByNursing);
			}
		}
		return specialNotesByNursingString;
	}

	public String getDietTypeSolidLiquidQuantityFrequencyString() {
		StringBuilder str = new StringBuilder();
		str.append(ObjectUtils.isNotEmpty(dietTypeOralSolid)
				? dietTypeOralSolid.getValue() + (ObjectUtils.isNotEmpty(dietTypeOralLiquidTF) ? "/" : "")
				: "");
		str.append(ObjectUtils.isNotEmpty(dietTypeOralLiquidTF) ? dietTypeOralLiquidTF.getValue() + "/" + quantity.getValueStr() + "/" + frequency.getValueStr() : "");
		this.setDietTypeSolidLiquidQuantityFrequencyString(str.toString());
		return dietTypeSolidLiquidQuantityFrequencyString;
	}
}
