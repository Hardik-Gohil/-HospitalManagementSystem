package com.HospitalManagementSystem.repository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import com.HospitalManagementSystem.entity.DietPlan;
import com.HospitalManagementSystem.entity.Patient;

public interface DietPlanRepository extends JpaRepository<DietPlan, Long>, JpaSpecificationExecutor<DietPlan> {

	List<DietPlan> findAllByPatientPatientIdAndServiceMasterFromTimeGreaterThan(Long patientId, LocalTime localTime);
	
	@Query("SELECT DISTINCT dietPlan.patient FROM DietPlan dietPlan WHERE dietPlan.dietDate = :dietDate")
	List<Patient> findAllPatientsByDietDate(LocalDate dietDate);

	List<DietPlan> findAllByPatientPatientIdAndDietDate(Long patientId, LocalDate date);

	List<DietPlan> findAllByServiceMasterServiceMasterId(Long serviceMasterId);

	List<DietPlan> findAllByServiceMasterServiceMasterIdAndPatientPatientId(Long serviceMasterId, Long patientId);

	List<DietPlan> findAllByDietDate(LocalDate date);

	List<DietPlan> findAllByServiceMasterServiceMasterIdAndDietDate(Long serviceMasterId, LocalDate now);

	List<DietPlan> findAllByServiceMasterServiceMasterIdAndPatientPatientIdAndDietDate(Long serviceMasterId, Long patientId, LocalDate dietDate);
	
	Optional<DietPlan> findByServiceMasterServiceMasterIdAndPatientPatientIdAndDietDate(Long serviceMasterId, Long patientId, LocalDate dietDate);

	void deleteAllByPatientPatientId(Long patientId);

}
