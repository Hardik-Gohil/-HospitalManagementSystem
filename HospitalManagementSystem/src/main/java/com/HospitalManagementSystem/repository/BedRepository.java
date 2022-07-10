package com.HospitalManagementSystem.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.HospitalManagementSystem.entity.master.Bed;

public interface BedRepository extends JpaRepository<Bed, Long> {

	List<Bed> findAllByIsActive(Boolean isActive);

	Bed findByBedCode(String bedcode);

}