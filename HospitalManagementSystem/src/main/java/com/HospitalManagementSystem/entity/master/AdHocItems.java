package com.HospitalManagementSystem.entity.master;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.HospitalManagementSystem.enums.YesNo;

import lombok.Data;

@Entity
@Table(name = "adhoc_items")
@Data
public class AdHocItems {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long adHocItemsId;

	private String itemName;

	@Column(columnDefinition = "ENUM('YES', 'NO')")
	@Enumerated(EnumType.STRING)
	private YesNo fullDiet;
	@Column(columnDefinition = "ENUM('YES', 'NO')")
	@Enumerated(EnumType.STRING)
	private YesNo softDiet;
	@Column(columnDefinition = "ENUM('YES', 'NO')")
	@Enumerated(EnumType.STRING)
	private YesNo semiSolidDiet;

	@Column(columnDefinition = "ENUM('YES', 'NO')")
	@Enumerated(EnumType.STRING)
	private YesNo normal;
	@Column(columnDefinition = "ENUM('YES', 'NO')")
	@Enumerated(EnumType.STRING)
	private YesNo diabeticDiet;
	@Column(columnDefinition = "ENUM('YES', 'NO')")
	@Enumerated(EnumType.STRING)
	private YesNo renal;
	@Column(columnDefinition = "ENUM('YES', 'NO')")
	@Enumerated(EnumType.STRING)
	private YesNo hypertensive;
	@Column(columnDefinition = "ENUM('YES', 'NO')")
	@Enumerated(EnumType.STRING)
	private YesNo saltFree;
	@Column(columnDefinition = "ENUM('YES', 'NO')")
	@Enumerated(EnumType.STRING)
	private YesNo fatFree;

	private Boolean isActive = Boolean.FALSE;
}
