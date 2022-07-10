package com.HospitalManagementSystem.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.HospitalManagementSystem.entity.master.SpecialNotesByNursing;

public interface SpecialNotesByNursingRepository extends JpaRepository<SpecialNotesByNursing, Long> {

	List<SpecialNotesByNursing> findAllByIsActive(Boolean isActive);

}