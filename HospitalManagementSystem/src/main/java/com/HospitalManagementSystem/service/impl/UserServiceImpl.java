package com.HospitalManagementSystem.service.impl;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.apache.commons.lang3.ObjectUtils;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.HospitalManagementSystem.auth.service.PrincipalUser;
import com.HospitalManagementSystem.dto.ChangePasswordDto;
import com.HospitalManagementSystem.dto.UserDto;
import com.HospitalManagementSystem.entity.User;
import com.HospitalManagementSystem.entity.history.UserHistory;
import com.HospitalManagementSystem.entity.master.Department;
import com.HospitalManagementSystem.entity.master.Role;
import com.HospitalManagementSystem.repository.DepartmentRepository;
import com.HospitalManagementSystem.repository.FloorRepository;
import com.HospitalManagementSystem.repository.UserHistoryRepository;
import com.HospitalManagementSystem.repository.UserRepository;
import com.HospitalManagementSystem.service.UserService;
import com.HospitalManagementSystem.utility.CommonUtility;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private UserHistoryRepository userHistoryRepository;

	@Autowired
	private DepartmentRepository departmentRepository;

	@Autowired
	private FloorRepository floorRepository;

	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;

	@Autowired
	private ModelMapper modelMapper;

	@Autowired
	private CommonUtility commonUtility;

	@Value("${default.password}")
	private String defaultPassword;

	@Override
	public void save(User user) {
		UserHistory userHistory = modelMapper.map(user, UserHistory.class);
		userHistory.setHistoryCreatedOn(user.getModifiedOn());
		userHistory.setHistoryCreatedBy(user.getModifiedBy());
		userHistory.setRoles(String.valueOf((new ArrayList<>(user.getRoles())).get(0).getRoleId()));
		userHistory.setDepartmentId(ObjectUtils.isNotEmpty(user.getDepartment()) ? user.getDepartment().getDepartmentId() : null);
		userHistory.setFloorId(ObjectUtils.isNotEmpty(user.getFloor()) ? user.getFloor().getFloorId() : null);
		userHistory = userHistoryRepository.save(userHistory);
		user.setCurrenUserHistoryId(userHistory.getUserHistoryId());
		user = userRepository.save(user);
		if (user.getModifiedBy() == user.getUserId()) {
			commonUtility.removeCurrentUser();
		}
		if (ObjectUtils.isEmpty(userHistory.getUserId())) {
			userHistory.setUserId(user.getUserId());
			userHistory = userHistoryRepository.save(userHistory);
		}
	}

	@Override
	public User findByUsername(String username) {
		return userRepository.findByUsername(username);
	}

	@Override
	public List<UserDto> getUserData() {
		List<User> userList = userRepository.findAll();
		return modelMapper.map(userList, new TypeToken<List<UserDto>>() {}.getType());
	}

	@Override
	public String addEditUser(Long userId, Model model) {
		model.addAttribute("userDto", ObjectUtils.isNotEmpty(userId) ? userRepository.findById(userId).get() : new UserDto());
		model.addAttribute("departmentList", departmentRepository.findAllByIsActive(Boolean.TRUE));
		model.addAttribute("floorList", floorRepository.findAllByIsActive(Boolean.TRUE));
		return "users/AddEditUser";
	}

	@Override
	public String checkUniqueUsername(String username, Long userId) {
		User user = userRepository.findByUsername(username);
		if (ObjectUtils.isEmpty(user) || user.getUserId() == userId) {
			return "true";
		}
		return "false";
	}

	@Override
	public String addEditUser(UserDto userDto) {
		LocalDateTime now = LocalDateTime.now();
		User currentUser = commonUtility.getCurrentUser();
		Optional<User> user = Optional.empty();
		if (ObjectUtils.isNotEmpty(userDto.getUserId()) && userDto.getUserId() > 0) {
			user = userRepository.findById(userDto.getUserId());
		}
		User saveUser = modelMapper.map(userDto, User.class);
		if (user.isPresent()) {
			User userEntity = user.get();
			saveUser.setPassword(userEntity.getPassword());
			saveUser.setPasswordResetType(userEntity.getPasswordResetType());
			saveUser.setIsActive(userEntity.getIsActive());
			saveUser.setCreatedOn(userEntity.getCreatedOn());
			saveUser.setCreatedBy(userEntity.getCreatedBy());
			saveUser.setModifiedOn(now);
			saveUser.setModifiedBy(currentUser.getUserId());
			saveUser.setCreatedUserHistoryId(userEntity.getCreatedUserHistoryId());
			saveUser.setModifiedUserHistoryId(currentUser.getCurrenUserHistoryId());
		} else {
			saveUser.setPassword(bCryptPasswordEncoder.encode(defaultPassword));
			saveUser.setPasswordResetType(1);
			saveUser.setIsActive(Boolean.TRUE);
			saveUser.setCreatedOn(now);
			saveUser.setCreatedBy(currentUser.getUserId());
			saveUser.setModifiedOn(now);
			saveUser.setModifiedBy(currentUser.getUserId());
			saveUser.setCreatedUserHistoryId(currentUser.getCurrenUserHistoryId());
			saveUser.setModifiedUserHistoryId(currentUser.getCurrenUserHistoryId());
		}
		Department department = departmentRepository.findById(userDto.getDepartment().getDepartmentId()).get();
		Set<Role> roles = new HashSet<>();
		roles.add(department.getRole());
		if (!department.getDepartmentName().equalsIgnoreCase("Nursing")) {
			saveUser.setFloor(null);
		} else {
			saveUser.setFloor(floorRepository.findById(userDto.getFloor().getFloorId()).get());
		}
		saveUser.setRoles(roles);
		save(saveUser);
		return "redirect:/users";
	}

	@Override
	public ResponseEntity<String> changeUserStatus(Long userId, Boolean isActive) {
		User currentUser = commonUtility.getCurrentUser();
		Optional<User> user = userRepository.findById(userId);
		if (user.isPresent()) {
			User userEntity = user.get();
			userEntity.setIsActive(isActive);
			userEntity.setModifiedOn(LocalDateTime.now());
			userEntity.setModifiedBy(currentUser.getUserId());
			userEntity.setModifiedUserHistoryId(currentUser.getCurrenUserHistoryId());
			save(userEntity);
			return ResponseEntity.ok().body("User status changed");
		}
		return ResponseEntity.badRequest().body("User not found");
	}

	@Override
	public ResponseEntity<String> resetPassword(Long userId) {
		User currentUser = commonUtility.getCurrentUser();
		Optional<User> user = userRepository.findById(userId);
		if (user.isPresent()) {
			User userEntity = user.get();
			userEntity.setPassword(bCryptPasswordEncoder.encode(defaultPassword));
			userEntity.setPasswordResetType(1);
			userEntity.setModifiedOn(LocalDateTime.now());
			userEntity.setModifiedBy(currentUser.getUserId());
			userEntity.setModifiedUserHistoryId(currentUser.getCurrenUserHistoryId());
			save(userEntity);
			return ResponseEntity.ok().body("User password reset");
		}
		return ResponseEntity.badRequest().body("User not found");
	}

	@Override
	public String getHomePage() {
		String homePageUrl = "redirect:/login";
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		if (!ObjectUtils.isEmpty(principal)) {
			PrincipalUser principalUser = (PrincipalUser) principal;
			Optional<User> user = userRepository.findById(principalUser.getUserId());
			if (user.get().getPasswordResetType() == 1) {
				homePageUrl = "redirect:/change-password";
			} else {
				homePageUrl = "redirect:/dashboard";
			}
		}
		return homePageUrl;
	}

	@Override
	public String changePassword(Model model) {
		ChangePasswordDto changePassword = new ChangePasswordDto();
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		if (!ObjectUtils.isEmpty(principal)) {
			PrincipalUser principalUser = (PrincipalUser) principal;
			Optional<User> user = userRepository.findById(principalUser.getUserId());
			changePassword.setUserId(user.get().getUserId());
			changePassword.setPasswordResetType(user.get().getPasswordResetType());
		}
		model.addAttribute("changePassword", changePassword);
		return "users/ChangePassword";
	}

	@Override
	public String updatePassword(RedirectAttributes redir, ChangePasswordDto changePassword) {
		User currentUser = commonUtility.getCurrentUser();
		Optional<User> user = userRepository.findById(changePassword.getUserId());
		if (user.isPresent()) {
			User userEntity = user.get();
			if (changePassword.getPasswordResetType() == 0) {
				if (!bCryptPasswordEncoder.matches(changePassword.getOldPassword(), userEntity.getPassword())) {
					redir.addFlashAttribute("changePasswordError", "Old password does not match with current password");
					return "redirect:/change-password";
				}
			}
			userEntity.setPassword(bCryptPasswordEncoder.encode(changePassword.getPassword()));
			userEntity.setPasswordResetType(0);
			userEntity.setModifiedOn(LocalDateTime.now());
			userEntity.setModifiedBy(currentUser.getUserId());
			userEntity.setModifiedUserHistoryId(currentUser.getCurrenUserHistoryId());
			save(userEntity);
			return "redirect:/logout";
		}
		return "redirect:/change-password";
	}

}
