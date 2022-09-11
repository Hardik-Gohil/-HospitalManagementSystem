package com.HospitalManagementSystem.utility;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.Objects;

import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import com.HospitalManagementSystem.entity.Notifications;
import com.HospitalManagementSystem.entity.Patient;
import com.HospitalManagementSystem.entity.User;

@Component
public class NotificationsUtility {

	public static final String ADD_PATIENT = "Diet of the $PatientName, $BedCd is added";
	public static final String UPDATE_PATIENT = "Diet Details ($UpdatedFields) for the $PatientName is updated on $UpdatedDateTime";
	public static final String DIET_INSTRUCTION = "Diet Instruction for $Services against $PatientName, $BedCd is $Action on $DateTime";
	public static final String ADHOC_ORDER = "$ServiceType order is $Action for $PatientName, $BedCd with expected delivery on $ExpectedDeliveryTime";
	public static final String PATIENT_DISCHARGE = "$PatientName, $BedCd got discharged on $DischargedTime";
	public static final String PATIENT_TRANSFERRED = "$PatientName got Transferred from $BedCd to $UpdatedBedCd on $TransferredDateTime";
	
	
	public String getTemplates(String templates, Map<String, String> params) {
		for (String key : params.keySet()) {
			templates = templates.replace(key, params.get(key));
		}
		return templates;
	}


	public Notifications getNotifications(User user, String code, String templates, LocalDateTime now, User currentUser, Long objectId) {
		Notifications notifications = new Notifications();
		notifications.setIsRead(false);
		notifications.setObjectId(objectId);
		notifications.setUser(user);
		notifications.setCode(code);
		notifications.setTemplates(templates);
		notifications.setCreatedOn(now);
		notifications.setCreatedBy(ObjectUtils.isNotEmpty(currentUser) ? currentUser.getUserId() : 0);
		notifications.setModifiedOn(now);
		notifications.setModifiedBy(ObjectUtils.isNotEmpty(currentUser) ? currentUser.getUserId() : 0);
		notifications.setCreatedUserHistoryId(ObjectUtils.isNotEmpty(currentUser) ? currentUser.getCurrenUserHistoryId() : 0);
		notifications.setModifiedUserHistoryId(ObjectUtils.isNotEmpty(currentUser) ? currentUser.getCurrenUserHistoryId() : 0);
		return notifications;
	}


	public String getUpdatedFields(Patient oldPatient, Patient newPatient, boolean extraLiquidChange, boolean dietTypeOralSolidChange, boolean dietSubTypeChange, boolean frequencyChange) {
		String updatedFields = "";

		if (oldPatient.getNbm() != newPatient.getNbm()) {
			updatedFields += "NBM: " + (newPatient.getExtraLiquid() ? "Yes" : "No") + ", ";
		}
		if (!newPatient.getNbm()) {
			if (dietTypeOralSolidChange) {
				updatedFields += "Diet Type- Oral Solid: " + (ObjectUtils.isNotEmpty(newPatient.getDietTypeOralSolid()) ? newPatient.getDietTypeOralSolid().getValue(): "") + "/" + (ObjectUtils.isNotEmpty(oldPatient.getDietTypeOralSolid()) ? oldPatient.getDietTypeOralSolid().getValue() : "") + ", ";
			}
			if (extraLiquidChange) {
				updatedFields += "Extra Liquid: " + (newPatient.getExtraLiquid() ? "Yes" : "No") + ", ";
			}
			if (!Objects.equals(ObjectUtils.isNotEmpty(oldPatient.getDietTypeOralLiquidTF()) && ObjectUtils.isNotEmpty(oldPatient.getDietTypeOralLiquidTF().getDietTypeOralLiquidTFId()) ? oldPatient.getDietTypeOralLiquidTF().getDietTypeOralLiquidTFId() : 0l,
					ObjectUtils.isNotEmpty(newPatient.getDietTypeOralLiquidTF()) && ObjectUtils.isNotEmpty(newPatient.getDietTypeOralLiquidTF().getDietTypeOralLiquidTFId()) ? newPatient.getDietTypeOralLiquidTF().getDietTypeOralLiquidTFId() : 0l)) {
				updatedFields += "Diet Type- Oral Liquid/TF: " + (ObjectUtils.isNotEmpty(newPatient.getDietTypeOralLiquidTF())? newPatient.getDietTypeOralLiquidTF().getValue() : "") + "/" + (ObjectUtils.isNotEmpty(oldPatient.getDietTypeOralLiquidTF()) ? oldPatient.getDietTypeOralLiquidTF().getValue() : "") + ", ";
			}
			if (ObjectUtils.isNotEmpty(newPatient.getDietTypeOralLiquidTF()) && dietSubTypeChange) {
				updatedFields += "Diet Sub Type: " + (ObjectUtils.isNotEmpty(newPatient.getDietSubType()) ? newPatient.getDietSubType().getValue() : "") + "/" + (ObjectUtils.isNotEmpty(oldPatient.getDietSubType()) ? oldPatient.getDietSubType().getValue() : "") + ", ";
			}
			if (ObjectUtils.isNotEmpty(newPatient.getDietTypeOralLiquidTF()) && 
				!Objects.equals(ObjectUtils.isNotEmpty(oldPatient.getQuantity()) && ObjectUtils.isNotEmpty(oldPatient.getQuantity().getQuantityId()) ? oldPatient.getQuantity().getQuantityId() : 0l,
					ObjectUtils.isNotEmpty(newPatient.getQuantity()) && ObjectUtils.isNotEmpty(newPatient.getQuantity().getQuantityId()) ? newPatient.getQuantity().getQuantityId() : 0l)) {
				updatedFields += "Quantity: " + (ObjectUtils.isNotEmpty(newPatient.getQuantity()) ? newPatient.getQuantity().getValueStr() : "") + "/" + (ObjectUtils.isNotEmpty(oldPatient.getQuantity()) ? oldPatient.getQuantity().getValueStr() : "") + ", ";
			}
			if (ObjectUtils.isNotEmpty(newPatient.getDietTypeOralLiquidTF()) && frequencyChange) {
				updatedFields += "Frequency: " + (ObjectUtils.isNotEmpty(newPatient.getFrequency()) ? newPatient.getFrequency().getValueStr() : "") + "/" + (ObjectUtils.isNotEmpty(oldPatient.getFrequency()) ? oldPatient.getFrequency().getValueStr() : "") + ", ";
			}
		}
		if (!StringUtils.equalsAnyIgnoreCase(oldPatient.getMedicalComorbiditiesString(), newPatient.getMedicalComorbiditiesString())) {
			updatedFields += "Medical Co-morbidities: " + newPatient.getMedicalComorbiditiesString() + "/" + oldPatient.getMedicalComorbiditiesString() + ", ";
		}
		if (!StringUtils.equalsAnyIgnoreCase(oldPatient.getDiagonosisIds(), newPatient.getDiagonosisIds())) {
			updatedFields += "Diagonosis" + ", ";
		}
		if (oldPatient.getIsVip() != newPatient.getIsVip()) {
			updatedFields += "Is VIP: " + (newPatient.getIsVip() ? "Yes" : "No") + ", ";
		}
		if (!StringUtils.equalsAnyIgnoreCase(oldPatient.getNursingName(), newPatient.getNursingName())) {
			updatedFields += "Nursing Name: " + newPatient.getNursingName() + "/" + oldPatient.getNursingName() + ", ";
		}
		updatedFields = StringUtils.removeEnd(updatedFields, ", ");
		return updatedFields;
	}
	

}
