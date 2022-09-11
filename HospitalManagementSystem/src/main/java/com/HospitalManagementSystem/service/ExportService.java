package com.HospitalManagementSystem.service;

import java.util.List;
import java.util.Map;

import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperPrint;

public interface ExportService {

	ResponseEntity<ByteArrayResource> getPdfPatientData(String searchText, String orderColumn, boolean direction, Integer patientStatus, boolean nbm, boolean extraLiquid, boolean startServiceImmediately, boolean isVip);

	ResponseEntity<ByteArrayResource> getExcelPatientData(String searchText, String orderColumn, boolean direction, Integer patientStatus, boolean nbm, boolean extraLiquid, boolean startServiceImmediately, boolean isVip);

	ResponseEntity<ByteArrayResource> getByteArrayResource(String reportName, String type, List<JasperPrint> jasperPrints, Map<String, Object> parameters) throws JRException;

	ResponseEntity<ByteArrayResource> patientServiceReportExport(Model model, String type, Integer patientServiceReport, String dateSelection, String diagonosisIds, String dietTypeOralSolidIds, String dietSubTypeIds);

	ResponseEntity<ByteArrayResource> getPdfAdhocOrderData();

	ResponseEntity<ByteArrayResource> getExcelAdhocOrderData();

}
	