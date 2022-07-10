package com.HospitalManagementSystem.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.HospitalManagementSystem.entity.master.DietTypeOralLiquidTF;

public interface DietTypeOralLiquidTFRepository extends JpaRepository<DietTypeOralLiquidTF, Long> {

	List<DietTypeOralLiquidTF> findAllByIsActive(Boolean isActive);

}