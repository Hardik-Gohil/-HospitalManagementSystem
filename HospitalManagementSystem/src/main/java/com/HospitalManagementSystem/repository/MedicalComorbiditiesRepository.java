package com.HospitalManagementSystem.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.HospitalManagementSystem.entity.master.MedicalComorbidities;

public interface MedicalComorbiditiesRepository extends JpaRepository<MedicalComorbidities, Long> {

	List<MedicalComorbidities> findAllByIsActive(Boolean isActive);

}