package com.HospitalManagementSystem.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.datatables.mapping.DataTablesInput;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.HospitalManagementSystem.dto.NotificationsDataTablesOutputDto;
import com.HospitalManagementSystem.service.NotificationsService;

@Controller
public class NotificationsController {
	
	@Autowired
	private NotificationsService notificationsService;

	@GetMapping("/notifications")
	public String patientListing(Model model) {
		return "Notifications";
	}
	
	@PostMapping("/notifications-data")
	@ResponseBody
	public NotificationsDataTablesOutputDto notificationsData(@RequestBody DataTablesInput input) {
		return notificationsService.notificationsData(input);
	}
	
	@GetMapping("/notifications-count")
	@ResponseBody
	public Integer notificationsCount() {
		return notificationsService.notificationsCount();
	}
	
	@PostMapping("/update-read-notifications")
	@ResponseBody
	public ResponseEntity<String> updateReadNotifications(@RequestParam("notificationsId") Long notificationsId) {
		return notificationsService.updateReadNotifications(notificationsId);
	}
}
