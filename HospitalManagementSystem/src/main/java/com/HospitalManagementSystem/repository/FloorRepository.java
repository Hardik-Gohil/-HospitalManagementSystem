package com.HospitalManagementSystem.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.HospitalManagementSystem.entity.master.Floor;

public interface FloorRepository extends JpaRepository<Floor, Long> {

	List<Floor> findAllByIsActive(Boolean isActive);

}
