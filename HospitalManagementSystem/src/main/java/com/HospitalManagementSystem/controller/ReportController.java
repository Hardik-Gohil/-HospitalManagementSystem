package com.HospitalManagementSystem.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.HospitalManagementSystem.service.ExportService;
import com.HospitalManagementSystem.service.ReportService;

@Controller
@RequestMapping("/reports")
public class ReportController {

	@Autowired
	private ReportService reportService;
	
	@Autowired
	private ExportService exportService;

	@GetMapping("/patient-service-report")
	public String patientServiceReport(Model model) {
		return reportService.patientServiceReport(model);
	}
	
	@PostMapping("/patient-service-report")
	public String patientServiceReport(Model model,
			@RequestParam(name = "patientServiceReport", required = false) Integer patientServiceReport,
			@RequestParam(name = "dateSelection", required = false) String dateSelection,
			@RequestParam(name = "diagonosisIds", required = false) String diagonosisIds,
			@RequestParam(name = "dietTypeOralSolidIds", required = false) String dietTypeOralSolidIds,
			@RequestParam(name = "dietSubTypeIds", required = false) String dietSubTypeIds) {
		return reportService.patientServiceReport(model, patientServiceReport, dateSelection, diagonosisIds, dietTypeOralSolidIds, dietSubTypeIds);
	}
	
	@PostMapping("/patient-service-report-export")
	public ResponseEntity<ByteArrayResource> patientServiceReportExport(Model model,
			@RequestParam(name = "type", required = true) String type,
			@RequestParam(name = "patientServiceReport", required = false) Integer patientServiceReport,
			@RequestParam(name = "dateSelection", required = false) String dateSelection,
			@RequestParam(name = "diagonosisIds", required = false) String diagonosisIds,
			@RequestParam(name = "dietTypeOralSolidIds", required = false) String dietTypeOralSolidIds,
			@RequestParam(name = "dietSubTypeIds", required = false) String dietSubTypeIds) {
		return exportService.patientServiceReportExport(model, type, patientServiceReport, dateSelection, diagonosisIds, dietTypeOralSolidIds, dietSubTypeIds);
	}
}
