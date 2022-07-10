package com.HospitalManagementSystem.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.HospitalManagementSystem.entity.Patient;

public interface PatientRepository extends JpaRepository<Patient, Long> {

	Patient findByIpNumber(String ipNumber);

	Integer countByPatientStatus(Integer patientStatus);
}