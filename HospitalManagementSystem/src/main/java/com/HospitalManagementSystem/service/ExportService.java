package com.HospitalManagementSystem.service;

import java.util.List;
import java.util.Map;

import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;

import com.HospitalManagementSystem.dto.AdHocSearchDto;
import com.HospitalManagementSystem.dto.PatientSearchDto;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperPrint;

public interface ExportService {

	ResponseEntity<ByteArrayResource> getPdfPatientData(PatientSearchDto patientSearchDto, Integer patientStatus);

	ResponseEntity<ByteArrayResource> getExcelPatientData(PatientSearchDto patientSearchDto, Integer patientStatus);

	ResponseEntity<ByteArrayResource> getByteArrayResource(String reportName, String type, List<JasperPrint> jasperPrints, Map<String, Object> parameters) throws JRException;

	ResponseEntity<ByteArrayResource> patientServiceReportExport(Model model, String type, Integer patientServiceReport, String dateSelection, String diagonosisIds, String dietTypeOralSolidIds, String dietSubTypeIds);

	ResponseEntity<ByteArrayResource> getPdfAdhocOrderData(AdHocSearchDto adHocSearchDto);

	ResponseEntity<ByteArrayResource> getExcelAdhocOrderData(AdHocSearchDto adHocSearchDto);

	ResponseEntity<ByteArrayResource> getExcelMISAdhocOrderData(AdHocSearchDto adHocSearchDto);

}
	