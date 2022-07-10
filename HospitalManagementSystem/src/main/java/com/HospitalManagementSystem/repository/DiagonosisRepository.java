package com.HospitalManagementSystem.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.HospitalManagementSystem.entity.master.Diagonosis;

public interface DiagonosisRepository extends JpaRepository<Diagonosis, Long> {

	List<Diagonosis> findAllByIsActive(Boolean isActive);

}