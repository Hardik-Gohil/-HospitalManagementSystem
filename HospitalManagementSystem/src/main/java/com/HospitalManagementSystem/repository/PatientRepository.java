package com.HospitalManagementSystem.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.HospitalManagementSystem.entity.Patient;

public interface PatientRepository extends JpaRepository<Patient, Long> {

	Patient findByIpNumber(String ipNumber);

	Integer countByPatientStatus(Integer patientStatus);

	List<Patient> findAllByPatientStatus(int patientStatus);

	@Modifying
	@Query("UPDATE Patient patient SET patient.showUpdated = FALSE WHERE patient.showUpdated = TRUE")
	void updatePatientData();

	Integer countByPatientStatusAndBedFloorFloorId(Integer patientStatus, Long floorId);
}