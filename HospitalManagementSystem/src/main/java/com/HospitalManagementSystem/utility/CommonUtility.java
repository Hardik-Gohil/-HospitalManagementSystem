package com.HospitalManagementSystem.utility;

import java.util.Optional;

import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import com.HospitalManagementSystem.auth.service.PrincipalUser;
import com.HospitalManagementSystem.entity.User;
import com.HospitalManagementSystem.repository.UserRepository;

@Component
public class CommonUtility {

	@Autowired
	private HttpSession httpSession;

	@Autowired
	private UserRepository userRepository;

	public User getCurrentUser() {
		if (ObjectUtils.isNotEmpty(httpSession.getAttribute("currentUser"))) {
			return (User) httpSession.getAttribute("currentUser");
		}
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (!(authentication instanceof AnonymousAuthenticationToken)) {
			PrincipalUser principalUser = (PrincipalUser) authentication.getPrincipal();
			Optional<User> user = userRepository.findById(principalUser.getUserId());
			if (user.isPresent()) {
				httpSession.setAttribute("currentUser", user.get());
				return user.get();
			}
		}
		httpSession.invalidate();
		return null;
	}

	public void removeCurrentUser() {
		httpSession.removeAttribute("currentUser");
	}
}
