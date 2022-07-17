package com.HospitalManagementSystem.service;

import java.util.List;

import org.springframework.ui.Model;

import com.HospitalManagementSystem.dto.DietInstructionDto;
import com.HospitalManagementSystem.entity.DietInstruction;

public interface DietInstructionService {

	void save(DietInstruction dietInstruction);

	String getDietInstruction(Long patientId, Long dietInstructionId, Model model);

	String saveDietInstruction(DietInstructionDto dietInstructionDto);

	List<DietInstruction> getDietInstructionData(Long patientId);

	String deleteDietInstruction(Long dietInstructionId);

}
