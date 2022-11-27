package com.HospitalManagementSystem.service;

import java.util.List;

import org.springframework.core.io.Resource;
import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.HospitalManagementSystem.dto.AdHocOrderDto;
import com.HospitalManagementSystem.dto.AdHocSearchDto;
import com.HospitalManagementSystem.entity.AdHocOrder;

public interface AdHocOrderService {

	AdHocOrder save(AdHocOrder adHocOrder);

	String getAdHocOrder(Long patientId, Boolean immediateService, Model model);

	String saveAdHocOrder(RedirectAttributes redir, AdHocOrderDto adHocOrderDto, String adHocItemsIds, String quantities);

	List<AdHocOrder> getAdHocOrderData(Long patientId);

	String deleteAdHocOrder(RedirectAttributes redir, Long adHocOrderId);

	ResponseEntity<String> cancelAdHocOrder(Long adHocOrderId);

	ResponseEntity<Resource> adhocOrderKOT(Long adHocOrderId);

	DataTablesOutput<AdHocOrder> getAdhocOrderListing(AdHocSearchDto adHocSearchDto);

	ResponseEntity<String> chargableAdHocOrder(Long adHocOrderId, Boolean chargable);

}
