package com.HospitalManagementSystem.entity;

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
public class AdHocOrderItems {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long adHocOrderItemsId;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "adhoc_items_id")
	private AdHocItems adHocItems;

	private Integer quantity;
}
