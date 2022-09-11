package com.HospitalManagementSystem.entity;

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
import javax.persistence.Transient;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.ObjectUtils;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;
import lombok.Getter;

@Entity
@Table(name = "adhoc_order")
@Data
public class AdHocOrder {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long adHocOrderId;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "patient_id")
	private Patient patient;
	
	private String orderId;
	
	/**
	 * 1 Immediate Service
	 * 2 AdHoc Service
	 */
	private Integer serviceType;
	
	/**
	 * 1 Active
	 * 2 Cancel
	 * 3 Deleted
	 */
	private Integer orderStatus;

	@JsonFormat(pattern = "MM/dd/yyyy hh:mm:ss a")
	private LocalDateTime serviceDeliveryDateTime;
	
	@ManyToMany(fetch = FetchType.LAZY)
	private List<AdHocOrderItems> adHocOrderItems;

	private String remarks;
	private String nursingName;
	
	private Boolean chargable = Boolean.FALSE;

	@JsonFormat(pattern = "MM/dd/yyyy hh:mm:ss a")
	private LocalDateTime createdOn;

	private Long createdBy;

	private LocalDateTime modifiedOn;

	private Long modifiedBy;

	private Long createdUserHistoryId;

	private Long modifiedUserHistoryId;
	
	@Getter(lombok.AccessLevel.NONE)
	@Transient
	private String coMordibitiesDiagnosisItemQuantityString;
	
	public String getCoMordibitiesDiagnosisItemQuantityString() {
		StringBuilder str = new StringBuilder();
		str.append(ObjectUtils.isNotEmpty(patient) ? patient.getMedicalComorbiditiesString() : "");
		if (CollectionUtils.isNotEmpty(adHocOrderItems)) {
			for (AdHocOrderItems items : adHocOrderItems) {
				str.append("<Br>");
				str.append(items.getAdHocItems().getItemName() + " (" + items.getQuantity() + ")");
			}
		}
		this.setCoMordibitiesDiagnosisItemQuantityString(str.toString());
		return coMordibitiesDiagnosisItemQuantityString;
	}
	
}
