package com.HospitalManagementSystem.service;

import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.ResponseEntity;

public interface ExportService {

	ResponseEntity<ByteArrayResource> getPdfPatientData(String searchText, String orderColumn, boolean direction, Integer patientStatus, boolean nbm, boolean extraLiquid, boolean startServiceImmediately, boolean isVip);

	ResponseEntity<ByteArrayResource> getExcelPatientData(String searchText, String orderColumn, boolean direction, Integer patientStatus, boolean nbm, boolean extraLiquid, boolean startServiceImmediately, boolean isVip);

}
	