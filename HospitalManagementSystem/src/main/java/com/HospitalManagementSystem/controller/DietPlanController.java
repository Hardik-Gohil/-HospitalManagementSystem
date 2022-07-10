package com.HospitalManagementSystem.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.data.jpa.datatables.mapping.DataTablesInput;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.HospitalManagementSystem.dto.PatientDataTablesOutputDto;
import com.HospitalManagementSystem.dto.PatientDto;
import com.HospitalManagementSystem.service.ExportService;
import com.HospitalManagementSystem.service.PatientDetailsService;

@Controller
@RequestMapping("/diet")
public class DietPlanController {
	
	@Autowired
	private PatientDetailsService patientDetailsService;
	
	@Autowired
	private ExportService exportService;
	
	@GetMapping("/patients")
	public String patientListing(Model model) {
		return "diet/PatientListing";
	}
	
	@PostMapping("/patient-data")
	@ResponseBody
	public PatientDataTablesOutputDto getPatientData(@RequestBody DataTablesInput input,
			@RequestParam("patientStatus") Integer patientStatus,
			@RequestParam(name = "nbm", required = false) boolean nbm,
			@RequestParam(name = "extraLiquid", required = false) boolean extraLiquid,
			@RequestParam(name = "startServiceImmediately", required = false) boolean startServiceImmediately,
			@RequestParam(name = "isVip", required = false) boolean isVip) {
		return patientDetailsService.getPatientData(input, patientStatus, nbm, extraLiquid, startServiceImmediately, isVip);
	}
	
	@GetMapping("/patient-details")
	public String patientDetails(@RequestParam(name = "patientId", required = false) Long patientId, Model model) {
		return patientDetailsService.getPatientDetails(patientId, model);
	}
	
	@PostMapping("/check-unique-ipNumber")
	@ResponseBody
	public String checkUniqueIpNumber(@RequestParam("ipNumber") String ipNumber, @RequestParam(name = "patientId", required = false) Long patientId) {
		return patientDetailsService.checkUniqueIpNumber(ipNumber, patientId);
	}
	
	@PostMapping("/patient-details")
	public String addEditUser(@ModelAttribute("patientDto") PatientDto patientDto) {
		return patientDetailsService.savePatientDetails(patientDto);
	}
	
	@GetMapping("/export/pdf/patient-details")
	@ResponseBody
	public ResponseEntity<ByteArrayResource> getPdfPatientData(
			@RequestParam("searchText") String searchText,
			@RequestParam("orderColumn") String orderColumn,
			@RequestParam("direction") boolean direction,
			@RequestParam("patientStatus") Integer patientStatus,
			@RequestParam(name = "nbm", required = false) boolean nbm,
			@RequestParam(name = "extraLiquid", required = false) boolean extraLiquid,
			@RequestParam(name = "startServiceImmediately", required = false) boolean startServiceImmediately,
			@RequestParam(name = "isVip", required = false) boolean isVip) {
		return exportService.getPdfPatientData(searchText, orderColumn, direction, patientStatus, nbm, extraLiquid, startServiceImmediately, isVip);
	}
	
	@GetMapping("/export/excel/patient-details")
	@ResponseBody
	public ResponseEntity<ByteArrayResource> getExcelPatientData(
			@RequestParam("searchText") String searchText,
			@RequestParam("orderColumn") String orderColumn,
			@RequestParam("direction") boolean direction,
			@RequestParam("patientStatus") Integer patientStatus,
			@RequestParam(name = "nbm", required = false) boolean nbm,
			@RequestParam(name = "extraLiquid", required = false) boolean extraLiquid,
			@RequestParam(name = "startServiceImmediately", required = false) boolean startServiceImmediately,
			@RequestParam(name = "isVip", required = false) boolean isVip) {
		return exportService.getExcelPatientData(searchText, orderColumn, direction, patientStatus, nbm, extraLiquid, startServiceImmediately, isVip);
	}
}
