package com.HospitalManagementSystem.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.HospitalManagementSystem.entity.master.DietSubType;

public interface DietSubTypeRepository extends JpaRepository<DietSubType, Long> {

	List<DietSubType> findAllByIsActive(Boolean isActive);

}