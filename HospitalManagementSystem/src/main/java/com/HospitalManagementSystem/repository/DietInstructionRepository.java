package com.HospitalManagementSystem.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.HospitalManagementSystem.entity.DietInstruction;

public interface DietInstructionRepository extends JpaRepository<DietInstruction, Long> {

	List<DietInstruction> findAllByPatientPatientIdAndDietInstructionIdNot(Long patientId, Long dietInstructionId);

	List<DietInstruction> findAllByPatientPatientId(Long patientId);

	@Modifying
	@Query(value = "DELETE FROM diet_plan_diet_instructions WHERE diet_instructions_diet_instruction_id=:dietInstructionId", nativeQuery = true)
	void deleteDietInstructionFromDietPlan(@Param("dietInstructionId") Long dietInstructionId);
}
