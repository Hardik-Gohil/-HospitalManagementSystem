package com.HospitalManagementSystem.entity.master;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Table(name = "special_notes_by_nursing")
@Data
public class SpecialNotesByNursing implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long specialNotesByNursingId;

	private String value;

	private Boolean isActive = Boolean.FALSE;
}
