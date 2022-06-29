package com.HospitalManagementSystem.auth.service;

import java.util.Set;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import lombok.Data;

@Data
public class PrincipalUser extends User {

	private static final long serialVersionUID = -3531439484732724601L;

	private Long userId;

	private final String name;

	private Boolean isActive;

	public PrincipalUser(String username, String password, Set<GrantedAuthority> authorities, Long userId, String name, Boolean isActive) {
		super(username, password, authorities);
		this.userId = userId;
		this.name = name;
		this.isActive = isActive;
	}

}
