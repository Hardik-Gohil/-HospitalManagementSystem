package com.HospitalManagementSystem.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import com.HospitalManagementSystem.entity.master.Diagonosis;
import com.HospitalManagementSystem.repository.BedRepository;
import com.HospitalManagementSystem.repository.DiagonosisRepository;
import com.HospitalManagementSystem.repository.DietSubTypeRepository;
import com.HospitalManagementSystem.repository.DietTypeOralLiquidTFRepository;
import com.HospitalManagementSystem.repository.DietTypeOralSolidRepository;
import com.HospitalManagementSystem.repository.FloorRepository;
import com.HospitalManagementSystem.repository.MedicalComorbiditiesRepository;
import com.HospitalManagementSystem.repository.ServiceMasterRepository;
import com.HospitalManagementSystem.repository.ServiceSubTypeRepository;
import com.HospitalManagementSystem.service.SearchService;

@Service
public class SearchServiceImpl implements SearchService {

	@Autowired
	private BedRepository bedRepository;
	@Autowired
	private FloorRepository floorRepository;
	@Autowired
	private DietTypeOralSolidRepository dietTypeOralSolidRepository;
	@Autowired
	private DietTypeOralLiquidTFRepository dietTypeOralLiquidTFRepository;
	@Autowired
	private DietSubTypeRepository dietSubTypeRepository;
	@Autowired
	private MedicalComorbiditiesRepository medicalComorbiditiesRepository;
	@Autowired
	private DiagonosisRepository diagonosisRepository;
	@Autowired
	private ServiceMasterRepository serviceMasterRepository;
	@Autowired
	private ServiceSubTypeRepository serviceSubTypeRepository;

	
	@Override
	public void setMasterData(Model model) {
		List<Diagonosis> diagonosisList = diagonosisRepository.findAllByIsActive(Boolean.TRUE);
		Diagonosis otherDiagonosis = diagonosisList.get(0);
		diagonosisList.remove(0);
		diagonosisList.add(otherDiagonosis);
		model.addAttribute("searchMedicalComorbiditiesList", medicalComorbiditiesRepository.findAllByIsActive(Boolean.TRUE));
		model.addAttribute("searchFloorList", floorRepository.findAllByIsActive(Boolean.TRUE));
		model.addAttribute("searchBedList", bedRepository.findAllByIsActive(Boolean.TRUE));
		model.addAttribute("searchDietTypeOralSolidList", dietTypeOralSolidRepository.findAllByIsActive(Boolean.TRUE));
		model.addAttribute("searchDietTypeOralLiquidTFList", dietTypeOralLiquidTFRepository.findAllByIsActive(Boolean.TRUE));
		model.addAttribute("searchDietSubTypeList", dietSubTypeRepository.findAllByIsActive(Boolean.TRUE));
		model.addAttribute("searchServiceMasterList", serviceMasterRepository.findAllByIsActive(Boolean.TRUE));
		model.addAttribute("serviceSubTypeList", serviceSubTypeRepository.findAllByIsActive(Boolean.TRUE));
	}

}
