package com.HospitalManagementSystem.entity.master;

import java.io.Serializable;
import java.time.LocalTime;

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
@Table(name = "diet_sub_type")
@Data
public class DietSubType implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long dietSubTypeId;

	private String value;

	private Boolean isActive = Boolean.FALSE;
	
	private LocalTime fromTime;
	
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "diet_type_oral_liquid_tf_id")
	private DietTypeOralLiquidTF dietTypeOralLiquidTF;
}
