package com.HospitalManagementSystem.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.data.jpa.datatables.mapping.DataTablesInput;
import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.HospitalManagementSystem.dto.AdHocOrderDto;
import com.HospitalManagementSystem.dto.AdHocSearchDto;
import com.HospitalManagementSystem.dto.DietInstructionDto;
import com.HospitalManagementSystem.dto.DietPlanSearchDto;
import com.HospitalManagementSystem.dto.PatientDataTablesOutputDto;
import com.HospitalManagementSystem.dto.PatientDto;
import com.HospitalManagementSystem.dto.PatientSearchDto;
import com.HospitalManagementSystem.entity.AdHocOrder;
import com.HospitalManagementSystem.entity.DietInstruction;
import com.HospitalManagementSystem.service.AdHocOrderService;
import com.HospitalManagementSystem.service.DietInstructionService;
import com.HospitalManagementSystem.service.DietPlanService;
import com.HospitalManagementSystem.service.ExportService;
import com.HospitalManagementSystem.service.PatientDetailsService;
import com.HospitalManagementSystem.service.SearchService;
import com.HospitalManagementSystem.service.StickersService;

@Controller
@RequestMapping("/diet")
public class DietPlanController {
	
	@Autowired
	private PatientDetailsService patientDetailsService;
	
	@Autowired
	private ExportService exportService;
	
	@Autowired
	private DietInstructionService dietInstructionService;
	
	@Autowired
	private DietPlanService dietPlanService;
	
	@Autowired
	private AdHocOrderService adHocOrderService;
	
	@Autowired
	private StickersService stickersService;
	
	@Autowired
	private SearchService searchService;
	
	@GetMapping("/patients")
	public String patientListing(Model model) {
		searchService.setMasterData(model);
		return "diet/PatientListing";
	}
	
	@PostMapping("/patient-data")
	@ResponseBody
	public PatientDataTablesOutputDto getPatientData(@RequestBody PatientSearchDto patientSearchDto, @RequestParam("patientStatus") Integer patientStatus) {
		return patientDetailsService.getPatientData(patientSearchDto, patientStatus);
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
	
	@DeleteMapping("/delete-patient")
	@ResponseBody
	public ResponseEntity<String> deletePatient(@RequestParam("patientId") Long patientId) {
		return patientDetailsService.deletePatient(patientId);
	}
	
	@PostMapping("/patient-details")
	public String savePatientDetails(RedirectAttributes redir, @ModelAttribute("patientDto") PatientDto patientDto) {
		return patientDetailsService.savePatientDetails(redir, patientDto);
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
	
	@GetMapping("/diet-instruction")
	public String dietInstruction(@RequestParam("patientId") Long patientId, @RequestParam(name = "dietInstructionId", required = false) Long dietInstructionId, Model model) {
		return dietInstructionService.getDietInstruction(patientId, dietInstructionId, model);
	}
	
	@PostMapping("/diet-instruction")
	public String saveDietInstruction(RedirectAttributes redir, @ModelAttribute("dietInstructionDto") DietInstructionDto dietInstructionDto) {
		return dietInstructionService.saveDietInstruction(redir, dietInstructionDto);
	}
	
	@PostMapping("/diet-instruction-data")
	@ResponseBody
	public List<DietInstruction> getDietInstructionData(@RequestParam("patientId") Long patientId) {
		return dietInstructionService.getDietInstructionData(patientId);
	}
	
	@PostMapping("/delete-diet-instruction")
	public String deleteDietInstruction(@RequestParam("dietInstructionId") Long dietInstructionId) {
		return dietInstructionService.deleteDietInstruction(dietInstructionId);
	}

	@GetMapping("/diet-plan")
	public String dietPlan(Model model) {
		searchService.setMasterData(model);
		dietPlanService.prepareDietPlan();
		return "diet/DietPlan";
	}
	
	@PostMapping("/diet-plan-data")
	@ResponseBody
	public PatientDataTablesOutputDto getDietPlanData(@RequestBody DietPlanSearchDto dietPlanSearchDto) {
		return dietPlanService.getDietPlanData(dietPlanSearchDto);
	}
	
	@PostMapping("/update-diet-plan-item")
	@ResponseBody
	public ResponseEntity<String> updateDietPlanItem(@RequestParam("dietPlanId") Long dietPlanId, @RequestParam("item") String item) {
		return dietPlanService.updateDietPlanItem(dietPlanId, item);
	}
	
	@PostMapping("/update-diet-plan-item-paused-unpaused")
	@ResponseBody
	public ResponseEntity<String> updateDietPlanPausedUnpaused(@RequestParam("dietPlanId") Long dietPlanId, @RequestParam("isPaused") boolean isPaused) {
		return dietPlanService.updateDietPlanPausedUnpaused(dietPlanId, isPaused);
	}
	
	@GetMapping("/adhoc-order")
	public String adHocOrder(@RequestParam("patientId") Long patientId, @RequestParam(name = "immediateService", required = false) Boolean immediateService, Model model) {
		return adHocOrderService.getAdHocOrder(patientId, immediateService, model);
	}
	
	@PostMapping("/adhoc-order")
	public String saveAdHocOrder(RedirectAttributes redir, @ModelAttribute("adHocOrderDto") AdHocOrderDto adHocOrderDto,
			@RequestParam(name = "adHocItemsIds", required = false) String adHocItemsIds,
			@RequestParam(name = "quantities", required = false) String quantities) {
		return adHocOrderService.saveAdHocOrder(redir, adHocOrderDto, adHocItemsIds, quantities);
	}
	
	@PostMapping("/adhoc-order-data")
	@ResponseBody
	public List<AdHocOrder> getAdHocOrderData(@RequestParam("patientId") Long patientId) {
		return adHocOrderService.getAdHocOrderData(patientId);
	}
	
	@PostMapping("/delete-adhoc-order")
	public String deleteAdHocOrder(RedirectAttributes redir, @RequestParam("adHocOrderId") Long adHocOrderId) {
		return adHocOrderService.deleteAdHocOrder(redir, adHocOrderId);
	}
	
	@PostMapping("/cancel-adhoc-order")
	public ResponseEntity<String> cancelAdHocOrder(@RequestParam("adHocOrderId") Long adHocOrderId) {
		return adHocOrderService.cancelAdHocOrder(adHocOrderId);
	}
	
	@PostMapping("/chargable-adhoc-order")
	public ResponseEntity<String> chargableAdHocOrder(@RequestParam("adHocOrderId") Long adHocOrderId, @RequestParam("chargable") Boolean chargable) {
		return adHocOrderService.chargableAdHocOrder(adHocOrderId, chargable);
	}
	
	@GetMapping("/adhoc-order-kot")
	public ResponseEntity<Resource> adhocOrderKOT(@RequestParam("adHocOrderId") Long adHocOrderId) {
		return adHocOrderService.adhocOrderKOT(adHocOrderId);
	}
	
	@GetMapping("/adhoc-order-listing")
	public String adhocOrderListing(Model model) {
		searchService.setMasterData(model);
		return "diet/AdHocOrderListing";
	}
	
	@PostMapping("/adhoc-order-listing-data")
	@ResponseBody
	public DataTablesOutput<AdHocOrder> getAdhocOrderListing(@RequestBody AdHocSearchDto adHocSearchDto) {
		return adHocOrderService.getAdhocOrderListing(adHocSearchDto);
	}
	
	@GetMapping("/export/pdf/adhoc-order")
	@ResponseBody
	public ResponseEntity<ByteArrayResource> getPdfAdhocOrderData() {
		return exportService.getPdfAdhocOrderData();
	}

	@GetMapping("/export/excel/adhoc-order")
	@ResponseBody
	public ResponseEntity<ByteArrayResource> getExcelAdhocOrderData() {
		return exportService.getExcelAdhocOrderData();
	}
	
	@GetMapping("/stickers")
	public String stickers(Model model, @RequestParam(name = "patientId", required = false) Long patientId) {
		return stickersService.stickers(model, patientId);
	}
	
	@GetMapping("/generate-stickers")
	public ResponseEntity<Resource> generateStickers(@RequestParam("serviceMasterId") Long serviceMasterId, @RequestParam(name = "patientId", required = false) Long patientId) {
		return stickersService.generateStickers(serviceMasterId, patientId);
	}
	
	@GetMapping("/generate-adhoc-stickers")
	public ResponseEntity<Resource> generateAdhocOrderStickers(@RequestParam("adHocOrderId") Long adHocOrderId) {
		return stickersService.generateAdhocOrderStickers(adHocOrderId);
	}
}
