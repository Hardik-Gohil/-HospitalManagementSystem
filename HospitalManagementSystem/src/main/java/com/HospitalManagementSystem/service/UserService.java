package com.HospitalManagementSystem.service;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.HospitalManagementSystem.dto.ChangePasswordDto;
import com.HospitalManagementSystem.dto.UserDto;
import com.HospitalManagementSystem.entity.User;

public interface UserService {
	void save(User user);

	User findByUsername(String username);

	List<UserDto> getUserData();

	String addEditUser(Long userId, Model model);

	String addEditUser(UserDto userDto);

	ResponseEntity<String> changeUserStatus(Long userId, Boolean isActive);

	ResponseEntity<String> resetPassword(Long userId);

	String getHomePage();

	String changePassword(Model model);

	String updatePassword(RedirectAttributes redir, ChangePasswordDto changePassword);

	String checkUniqueUsername(String username, Long userId);
}