package com.HospitalManagementSystem.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.HospitalManagementSystem.entity.master.DietTypeOralSolid;

public interface DietTypeOralSolidRepository extends JpaRepository<DietTypeOralSolid, Long> {

	List<DietTypeOralSolid> findAllByIsActive(Boolean isActive);

}