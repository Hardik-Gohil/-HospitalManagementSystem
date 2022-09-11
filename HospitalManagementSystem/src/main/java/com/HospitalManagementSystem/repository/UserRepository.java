package com.HospitalManagementSystem.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.HospitalManagementSystem.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {

	User findByUsername(String username);

	List<User> findAllByIsActive(Boolean isActive);
}