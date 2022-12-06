package com.HospitalManagementSystem.utility;

import org.apache.commons.lang3.ObjectUtils;
import org.springframework.stereotype.Component;

@Component
public class ReportServiceUtility {

	public static final String PATIENT_SERVICE_REPORT_DIET_TYPE = "SELECT dietTypeOralSolid.value AS DietType,\r\n"
			+ "SUM(CASE WHEN comorbidities.medical_comorbidities_medical_comorbidities_id=1 THEN 1 ELSE 0 END) AS Normal,\r\n"
			+ "SUM(CASE WHEN comorbidities.medical_comorbidities_medical_comorbidities_id=2 THEN 1 ELSE 0 END) AS DD,\r\n"
			+ "SUM(CASE WHEN comorbidities.medical_comorbidities_medical_comorbidities_id=3 THEN 1 ELSE 0 END) AS Renal,\r\n"
			+ "SUM(CASE WHEN comorbidities.medical_comorbidities_medical_comorbidities_id=4 THEN 1 ELSE 0 END) AS SRD,\r\n"
			+ "SUM(CASE WHEN comorbidities.medical_comorbidities_medical_comorbidities_id=5 THEN 1 ELSE 0 END) AS SFD,\r\n"
			+ "SUM(CASE WHEN comorbidities.medical_comorbidities_medical_comorbidities_id=6 THEN 1 ELSE 0 END) AS FFD,\r\n"
			+ "COUNT(patient.patient_id) AS Total\r\n"
			+ "FROM `patient` patient\r\n"
			+ "INNER JOIN `patient_medical_comorbidities` comorbidities ON comorbidities.patient_patient_id=patient.patient_id\r\n"
			+ "INNER JOIN `diet_type_oral_solid` dietTypeOralSolid ON dietTypeOralSolid.diet_type_oral_solid_id=patient.diet_type_oral_solid_id\r\n"
			+ "WHERE DATE(patient.admitted_date) >= :startDate AND DATE(patient.admitted_date) <= :endDate AND dietTypeOralSolid.diet_type_oral_solid_id IN (:dietTypeOralSolidIds) AND patient.patient_status IN (1,2)\r\n"
			+ "GROUP BY dietTypeOralSolid.value \r\n"
			+ "ORDER BY dietTypeOralSolid.diet_type_oral_solid_id ASC";
	
	public static final String PATIENT_SERVICE_REPORT_EXTRA_LIQUID = "SELECT 'Extra Liquid' AS DietTyp,\r\n"
			+ "SUM(CASE WHEN comorbidities.medical_comorbidities_medical_comorbidities_id=1 THEN 1 ELSE 0 END) AS Normal,\r\n"
			+ "SUM(CASE WHEN comorbidities.medical_comorbidities_medical_comorbidities_id=2 THEN 1 ELSE 0 END) AS DD,\r\n"
			+ "SUM(CASE WHEN comorbidities.medical_comorbidities_medical_comorbidities_id=3 THEN 1 ELSE 0 END) AS Renal,\r\n"
			+ "SUM(CASE WHEN comorbidities.medical_comorbidities_medical_comorbidities_id=4 THEN 1 ELSE 0 END) AS SRD,\r\n"
			+ "SUM(CASE WHEN comorbidities.medical_comorbidities_medical_comorbidities_id=5 THEN 1 ELSE 0 END) AS SFD,\r\n"
			+ "SUM(CASE WHEN comorbidities.medical_comorbidities_medical_comorbidities_id=6 THEN 1 ELSE 0 END) AS FFD,\r\n"
			+ "COUNT(patient.patient_id) AS Total\r\n"
			+ "FROM `patient` patient\r\n"
			+ "INNER JOIN `patient_medical_comorbidities` comorbidities ON comorbidities.patient_patient_id=patient.patient_id\r\n"
			+ "WHERE patient.extra_liquid=TRUE AND DATE(patient.admitted_date) >= :startDate AND DATE(patient.admitted_date) <= :endDate AND patient.patient_status IN (1,2)";
	
	public static final String PATIENT_SERVICE_REPORT_DIET_SUB_TYPE = "SELECT dietTypeOralLiquidTF.value AS DietType,\r\n"
			+ "dietSubType.value AS DietSubType,\r\n"
			+ "SUM(CASE WHEN comorbidities.medical_comorbidities_medical_comorbidities_id=1 THEN 1 ELSE 0 END) AS Normal,\r\n"
			+ "SUM(CASE WHEN comorbidities.medical_comorbidities_medical_comorbidities_id=2 THEN 1 ELSE 0 END) AS DD,\r\n"
			+ "SUM(CASE WHEN comorbidities.medical_comorbidities_medical_comorbidities_id=3 THEN 1 ELSE 0 END) AS Renal,\r\n"
			+ "SUM(CASE WHEN comorbidities.medical_comorbidities_medical_comorbidities_id=4 THEN 1 ELSE 0 END) AS SRD,\r\n"
			+ "SUM(CASE WHEN comorbidities.medical_comorbidities_medical_comorbidities_id=5 THEN 1 ELSE 0 END) AS SFD,\r\n"
			+ "SUM(CASE WHEN comorbidities.medical_comorbidities_medical_comorbidities_id=6 THEN 1 ELSE 0 END) AS FFD,\r\n"
			+ "COUNT(patient.patient_id) AS Total\r\n"
			+ "FROM `patient` patient\r\n"
			+ "INNER JOIN `patient_medical_comorbidities` comorbidities ON comorbidities.patient_patient_id=patient.patient_id\r\n"
			+ "INNER JOIN `diet_sub_type` dietSubType ON dietSubType.diet_sub_type_id=patient.diet_sub_type_id\r\n"
			+ "INNER JOIN `diet_type_oral_liquid_tf` dietTypeOralLiquidTF ON dietTypeOralLiquidTF.diet_type_oral_liquidtfid=patient.diet_type_oral_liquid_tf_id\r\n"
			+ "WHERE DATE(patient.admitted_date) >= :startDate AND DATE(patient.admitted_date) <= :endDate AND dietSubType.diet_sub_type_id IN (:dietSubTypeIds) AND patient.patient_status IN (1,2)\r\n"
			+ "GROUP BY dietSubType.value \r\n"
			+ "ORDER BY dietTypeOralLiquidTF.diet_type_oral_liquidtfid ASC,dietSubType.diet_sub_type_id ASC";
	
	public static final String PATIENT_SERVICE_REPORT_DIAGONOSIS = "SELECT diagonosis.value AS Diagonosis,\r\n"
			+ "SUM(CASE WHEN comorbidities.medical_comorbidities_medical_comorbidities_id=1 THEN 1 ELSE 0 END) AS Normal,\r\n"
			+ "SUM(CASE WHEN comorbidities.medical_comorbidities_medical_comorbidities_id=2 THEN 1 ELSE 0 END) AS DD,\r\n"
			+ "SUM(CASE WHEN comorbidities.medical_comorbidities_medical_comorbidities_id=3 THEN 1 ELSE 0 END) AS Renal,\r\n"
			+ "SUM(CASE WHEN comorbidities.medical_comorbidities_medical_comorbidities_id=4 THEN 1 ELSE 0 END) AS SRD,\r\n"
			+ "SUM(CASE WHEN comorbidities.medical_comorbidities_medical_comorbidities_id=5 THEN 1 ELSE 0 END) AS SFD,\r\n"
			+ "SUM(CASE WHEN comorbidities.medical_comorbidities_medical_comorbidities_id=6 THEN 1 ELSE 0 END) AS FFD,\r\n"
			+ "COUNT(patient.patient_id) AS Total\r\n"
			+ "FROM `patient` patient\r\n"
			+ "INNER JOIN `patient_medical_comorbidities` comorbidities ON comorbidities.patient_patient_id=patient.patient_id\r\n"
			+ "INNER JOIN `patient_diagonosis` patientDiagonosis ON patientDiagonosis.patient_patient_id=patient.patient_id\r\n"
			+ "INNER JOIN `diagonosis` diagonosis ON diagonosis.diagonosis_id=patientDiagonosis.diagonosis_diagonosis_id\r\n"
			+ "WHERE DATE(patient.admitted_date) >= :startDate AND DATE(patient.admitted_date) <= :endDate AND diagonosis.diagonosis_id IN (:diagonosisIds) AND patient.patient_status IN (1,2)\r\n"
			+ "GROUP BY diagonosis.value \r\n"
			+ "ORDER BY diagonosis.diagonosis_id ASC";
	
	public static String getString(Object obj) {
		return ObjectUtils.isNotEmpty(obj) ? String.valueOf(obj) : "";
	}
}
