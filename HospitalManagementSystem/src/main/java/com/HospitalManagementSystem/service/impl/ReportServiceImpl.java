package com.HospitalManagementSystem.service.impl;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import com.HospitalManagementSystem.dto.PatientServiceReportDto;
import com.HospitalManagementSystem.entity.master.Diagonosis;
import com.HospitalManagementSystem.repository.DiagonosisRepository;
import com.HospitalManagementSystem.repository.DietSubTypeRepository;
import com.HospitalManagementSystem.repository.DietTypeOralSolidRepository;
import com.HospitalManagementSystem.service.ReportService;
import com.HospitalManagementSystem.utility.CommonUtility;
import com.HospitalManagementSystem.utility.ReportServiceUtility;

@Service
@SuppressWarnings("unchecked")
public class ReportServiceImpl implements ReportService {

	@PersistenceContext
	EntityManager em;

	@Autowired
	private DietTypeOralSolidRepository dietTypeOralSolidRepository;
	@Autowired
	private DietSubTypeRepository dietSubTypeRepository;
	@Autowired
	private DiagonosisRepository diagonosisRepository;

	@Override
	public String patientServiceReport(Model model) {
		List<Diagonosis> diagonosisList = diagonosisRepository.findAllByIsActive(Boolean.TRUE);
		Diagonosis otherDiagonosis = diagonosisList.get(0);
		diagonosisList.remove(0);
		diagonosisList.add(otherDiagonosis);
		model.addAttribute("localDateTimeFormatter", CommonUtility.localDateTimeFormatter);
		model.addAttribute("dietTypeOralSolidList", dietTypeOralSolidRepository.findAllByIsActive(Boolean.TRUE));
		model.addAttribute("dietSubTypeList", dietSubTypeRepository.findAllByIsActive(Boolean.TRUE));
		model.addAttribute("diagonosisList", diagonosisList);
		return "report/PatientServiceReport";
	}

	@Override
	public String patientServiceReport(Model model, Integer patientServiceReport, String dateSelection, String diagonosisIds, String dietTypeOralSolidIds, String dietSubTypeIds) {
		List<PatientServiceReportDto> patientServiceReportList = new ArrayList<>();
		String[] dateSelections = dateSelection.split(" - ");
		LocalDate startDate = LocalDate.parse(dateSelections[0], CommonUtility.localDateFormatter);
		LocalDate endDate = LocalDate.parse(dateSelections[1], CommonUtility.localDateFormatter);
		if (patientServiceReport == 1) {
			if (StringUtils.isNotEmpty(dietTypeOralSolidIds)) {
				List<String> dietTypeOralSolidIdsList = Stream.of(dietTypeOralSolidIds.split(",", -1)).collect(Collectors.toList());
				Boolean extraLiquid = dietTypeOralSolidIdsList.contains("0");
				dietTypeOralSolidIdsList.remove("0");
				
				if (CollectionUtils.isNotEmpty(dietTypeOralSolidIdsList)) {
					Query query = em.createNativeQuery(ReportServiceUtility.PATIENT_SERVICE_REPORT_DIET_TYPE);
					query.setParameter("startDate", startDate);
					query.setParameter("endDate", endDate);
					query.setParameter("dietTypeOralSolidIds", Stream.of(dietTypeOralSolidIds.split(",", -1)).map(x -> Long.valueOf(x)).collect(Collectors.toList()));
					List<Object[]> list = query.getResultList();
					
					if (CollectionUtils.isNotEmpty(list)) {
						for (Object[] object : list) {
							PatientServiceReportDto patientServiceReportDto = new PatientServiceReportDto();
							patientServiceReportDto.setDietType(ReportServiceUtility.getString(object[0]));
							patientServiceReportDto.setNormal(ReportServiceUtility.getString(object[1]));
							patientServiceReportDto.setDd(ReportServiceUtility.getString(object[2]));
							patientServiceReportDto.setRenal(ReportServiceUtility.getString(object[3]));
							patientServiceReportDto.setSrd(ReportServiceUtility.getString(object[4]));
							patientServiceReportDto.setSfd(ReportServiceUtility.getString(object[5]));
							patientServiceReportDto.setFfd(ReportServiceUtility.getString(object[6]));
							patientServiceReportDto.setTotal(ReportServiceUtility.getString(object[7]));
							patientServiceReportDto.setDietSubType("");
							patientServiceReportList.add(patientServiceReportDto);
						}
					}
				}
				
				if (extraLiquid) {
					Query query = em.createNativeQuery(ReportServiceUtility.PATIENT_SERVICE_REPORT_EXTRA_LIQUID);
					query.setParameter("startDate", startDate);
					query.setParameter("endDate", endDate);
					List<Object[]> list = query.getResultList();
					
					if (CollectionUtils.isNotEmpty(list)) {
						for (Object[] object : list) {
							PatientServiceReportDto patientServiceReportDto = new PatientServiceReportDto();
							patientServiceReportDto.setDietType(ReportServiceUtility.getString(object[0]));
							patientServiceReportDto.setNormal(ReportServiceUtility.getString(object[1]));
							patientServiceReportDto.setDd(ReportServiceUtility.getString(object[2]));
							patientServiceReportDto.setRenal(ReportServiceUtility.getString(object[3]));
							patientServiceReportDto.setSrd(ReportServiceUtility.getString(object[4]));
							patientServiceReportDto.setSfd(ReportServiceUtility.getString(object[5]));
							patientServiceReportDto.setFfd(ReportServiceUtility.getString(object[6]));
							patientServiceReportDto.setTotal(ReportServiceUtility.getString(object[7]));
							patientServiceReportDto.setDietSubType("");
							patientServiceReportList.add(patientServiceReportDto);
						}
					}
				}
				
			}
			
			if (StringUtils.isNotEmpty(dietSubTypeIds)) { 
				Query query = em.createNativeQuery(ReportServiceUtility.PATIENT_SERVICE_REPORT_DIET_SUB_TYPE);
				query.setParameter("startDate", startDate);
				query.setParameter("endDate", endDate);
				query.setParameter("dietSubTypeIds", Stream.of(dietSubTypeIds.split(",", -1)).map(x -> Long.valueOf(x)).collect(Collectors.toList()));
				List<Object[]> list = query.getResultList();
				
				if (CollectionUtils.isNotEmpty(list)) {
					for (Object[] object : list) {
						PatientServiceReportDto patientServiceReportDto = new PatientServiceReportDto();
						patientServiceReportDto.setDietType(ReportServiceUtility.getString(object[0]));
						patientServiceReportDto.setDietSubType(ReportServiceUtility.getString(object[1]));
						patientServiceReportDto.setNormal(ReportServiceUtility.getString(object[2]));
						patientServiceReportDto.setDd(ReportServiceUtility.getString(object[3]));
						patientServiceReportDto.setRenal(ReportServiceUtility.getString(object[4]));
						patientServiceReportDto.setSrd(ReportServiceUtility.getString(object[5]));
						patientServiceReportDto.setSfd(ReportServiceUtility.getString(object[6]));
						patientServiceReportDto.setFfd(ReportServiceUtility.getString(object[7]));
						patientServiceReportDto.setTotal(ReportServiceUtility.getString(object[8]));
						patientServiceReportList.add(patientServiceReportDto);
					}
				}
			}
		} else if (patientServiceReport == 2) {
			Query query = em.createNativeQuery(ReportServiceUtility.PATIENT_SERVICE_REPORT_DIAGONOSIS);
			query.setParameter("startDate", startDate);
			query.setParameter("endDate", endDate);
			query.setParameter("diagonosisIds", Stream.of(diagonosisIds.split(",", -1)).map(x -> Long.valueOf(x)).collect(Collectors.toList()));
			List<Object[]> list = query.getResultList();
			
			if (CollectionUtils.isNotEmpty(list)) {
				for (Object[] object : list) {
					PatientServiceReportDto patientServiceReportDto = new PatientServiceReportDto();
					patientServiceReportDto.setDiagnosis(ReportServiceUtility.getString(object[0]));
					patientServiceReportDto.setNormal(ReportServiceUtility.getString(object[1]));
					patientServiceReportDto.setDd(ReportServiceUtility.getString(object[2]));
					patientServiceReportDto.setRenal(ReportServiceUtility.getString(object[3]));
					patientServiceReportDto.setSrd(ReportServiceUtility.getString(object[4]));
					patientServiceReportDto.setSfd(ReportServiceUtility.getString(object[5]));
					patientServiceReportDto.setFfd(ReportServiceUtility.getString(object[6]));
					patientServiceReportDto.setTotal(ReportServiceUtility.getString(object[7]));
					patientServiceReportDto.setDietType("");
					patientServiceReportDto.setDietSubType("");
					patientServiceReportList.add(patientServiceReportDto);
				}
			}
		}

		model.addAttribute("patientServiceReportList", patientServiceReportList);
		model.addAttribute("patientServiceReport", patientServiceReport);
		return patientServiceReport(model);
	}

}
