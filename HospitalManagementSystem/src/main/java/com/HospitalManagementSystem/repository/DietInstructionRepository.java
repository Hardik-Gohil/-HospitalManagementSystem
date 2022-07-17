package com.HospitalManagementSystem.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.HospitalManagementSystem.entity.DietInstruction;

public interface DietInstructionRepository extends JpaRepository<DietInstruction, Long> {

	List<DietInstruction> findAllByPatientPatientIdAndDietInstructionIdNot(Long patientId, Long dietInstructionId);

	List<DietInstruction> findAllByPatientPatientId(Long patientId);

}
