package com.HospitalManagementSystem.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.HospitalManagementSystem.entity.master.Department;

public interface DepartmentRepository extends JpaRepository<Department, Long> {

	List<Department> findAllByIsActive(Boolean isActive);

}
