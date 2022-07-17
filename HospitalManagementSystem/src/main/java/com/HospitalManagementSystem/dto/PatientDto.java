package com.HospitalManagementSystem.dto;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;
import lombok.Getter;

@Data
public class PatientDto {
	
	private Long patientId;
	
	private String umrNumber;
	
	private String ipNumber;
	
	private String room;
	
	private BedDto bed;
	
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
	
	private DietTypeOralSolidDto dietTypeOralSolid;
	
	private DietTypeOralLiquidTFDto dietTypeOralLiquidTF;
	
	private DietSubTypeDto dietSubType;
	
	private QuantityDto quantity;
	
	private FrequencyDto frequency;
	
	private List<MedicalComorbiditiesDto> medicalComorbidities;
	
	private List<DiagonosisDto> diagonosis;
	
	private List<SpecialNotesByNursingDto> specialNotesByNursing;
	
	private String othersSpecialNotesByNursing;
	
	@Getter(lombok.AccessLevel.NONE)
	private String medicalComorbiditiesIds;
	
	@Getter(lombok.AccessLevel.NONE)
	private String diagonosisIds;
	
	@Getter(lombok.AccessLevel.NONE)
	private String specialNotesByNursingIds;
	
	@Getter(lombok.AccessLevel.NONE)
	private String bedString;
	
	@Getter(lombok.AccessLevel.NONE)
	private String medicalComorbiditiesString;
	
	@Getter(lombok.AccessLevel.NONE)
	private String diagonosisString;
	
	@Getter(lombok.AccessLevel.NONE)
	private String specialNotesByNursingString;
	
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

	public String getMedicalComorbiditiesIds() {
		this.setMedicalComorbiditiesIds(StringUtils.isEmpty(medicalComorbiditiesIds) && CollectionUtils.isNotEmpty(medicalComorbidities)
						? medicalComorbidities.stream().map(x -> String.valueOf(x.getMedicalComorbiditiesId())).collect(Collectors.joining(","))
						: medicalComorbiditiesIds);
		return medicalComorbiditiesIds;
	}

	public String getDiagonosisIds() {
		this.setDiagonosisIds(StringUtils.isEmpty(diagonosisIds) && CollectionUtils.isNotEmpty(diagonosis)
				? diagonosis.stream().map(x -> String.valueOf(x.getDiagonosisId())).collect(Collectors.joining(","))
				: diagonosisIds);
		return diagonosisIds;
	}

	public String getSpecialNotesByNursingIds() {
		this.setSpecialNotesByNursingIds(StringUtils.isEmpty(specialNotesByNursingIds) && CollectionUtils.isNotEmpty(specialNotesByNursing)
						? specialNotesByNursing.stream().map(x -> String.valueOf(x.getSpecialNotesByNursingId())).collect(Collectors.joining(","))
						: specialNotesByNursingIds);
		return specialNotesByNursingIds;
	}
	
}
