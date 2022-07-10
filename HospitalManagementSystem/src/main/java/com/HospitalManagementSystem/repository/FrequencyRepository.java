package com.HospitalManagementSystem.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.HospitalManagementSystem.entity.master.Frequency;

public interface FrequencyRepository extends JpaRepository<Frequency, Long> {

	List<Frequency> findAllByIsActive(Boolean isActive);

}