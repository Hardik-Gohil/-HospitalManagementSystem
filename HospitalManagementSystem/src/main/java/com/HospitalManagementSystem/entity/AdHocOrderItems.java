package com.HospitalManagementSystem.entity;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.HospitalManagementSystem.entity.master.AdHocItems;

import lombok.Data;

@Entity
@Table(name = "adhoc_order_items")
@Data
public class AdHocOrderItems implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long adHocOrderItemsId;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "adhoc_items_id")
	private AdHocItems adHocItems;

	private Integer quantity;
	
	@Column(precision = 13, scale = 2)
	private BigDecimal itemRate;
	
	@Column(precision = 13, scale = 2)
	private BigDecimal totalRate;

}
