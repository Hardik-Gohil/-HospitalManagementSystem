package com.HospitalManagementSystem.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.HospitalManagementSystem.entity.DietInstruction;

public interface DietInstructionRepository extends JpaRepository<DietInstruction, Long> {

	List<DietInstruction> findAllByPatientPatientIdAndDietInstructionIdNotAndDietInstructionStatus(Long patientId, Long dietInstructionId, Integer dietInstructionStatus);

	List<DietInstruction> findAllByPatientPatientIdAndDietInstructionStatus(Long patientId, Integer dietInstructionStatus);

//	@Modifying
//	@Query(value = "DELETE FROM diet_plan_diet_instructions WHERE diet_instructions_diet_instruction_id=:dietInstructionId", nativeQuery = true)
//	void deleteDietInstructionFromDietPlan(@Param("dietInstructionId") Long dietInstructionId);
	
	@Modifying
	@Query(value = "UPDATE diet_instruction set diet_instruction_status=:dietInstructionStatus WHERE diet_instruction_id=:dietInstructionId", nativeQuery = true)
	void updateDietInstructionStatus(@Param("dietInstructionStatus") Integer dietInstructionStatus, @Param("dietInstructionId") Long dietInstructionId);

	void deleteAllByPatientPatientId(Long patientId);
}
