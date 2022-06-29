package com.HospitalManagementSystem.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.HospitalManagementSystem.dto.ChangePasswordDto;
import com.HospitalManagementSystem.dto.UserDto;
import com.HospitalManagementSystem.service.UserService;

@Controller
public class UserController {

	@Autowired
	private UserService userService;

	@GetMapping("/login")
	public String login(Model model, String error, String logout) {
		if (logout != null) {
			model.addAttribute("message", "You have been logged out successfully.");
		}
		return "Login";
	}

	@GetMapping({ "/" })
	public String homePage(Model model) {
		return userService.getHomePage();
	}

	@GetMapping({ "/dashboard" })
	public String dashboard(Model model) {
		return "Dashboard";
	}

	@GetMapping("/users")
	public String userListing(Model model) {
		return "users/UserListing";
	}

	@GetMapping("/user-data")
	@ResponseBody
	public List<UserDto> getUserData() {
		return userService.getUserData();
	}

	@GetMapping("/add-edit-user")
	public String addEditUser(@RequestParam(name = "userId", required = false) Long userId, Model model) {
		return userService.addEditUser(userId, model);
	}

	@PostMapping("/add-edit-user")
	public String addEditUser(@ModelAttribute("userDto") UserDto userDto) {
		return userService.addEditUser(userDto);
	}

	@PostMapping("/check-unique-username")
	@ResponseBody
	public String checkUniqueUsername(@RequestParam("username") String username, @RequestParam(name = "userId", required = false) Long userId) {
		return userService.checkUniqueUsername(username, userId);
	}

	@PostMapping("/change-user-status")
	@ResponseBody
	public ResponseEntity<String> changeUserStatus(@RequestParam("userId") Long userId, @RequestParam("isActive") Boolean isActive) {
		return userService.changeUserStatus(userId, isActive);
	}

	@PostMapping("/reset-password")
	@ResponseBody
	public ResponseEntity<String> resetPassword(@RequestParam("userId") Long userId) {
		return userService.resetPassword(userId);
	}

	@GetMapping("/change-password")
	public String changePassword(Model model) {
		return userService.changePassword(model);
	}

	@PostMapping("/update-password")
	public String updatePassword(RedirectAttributes redir, @ModelAttribute("changePassword") ChangePasswordDto changePassword) {
		return userService.updatePassword(redir, changePassword);
	}
}