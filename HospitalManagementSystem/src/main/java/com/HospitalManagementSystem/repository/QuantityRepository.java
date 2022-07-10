package com.HospitalManagementSystem.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.HospitalManagementSystem.entity.master.Quantity;

public interface QuantityRepository extends JpaRepository<Quantity, Long> {

	List<Quantity> findAllByIsActive(Boolean isActive);

}