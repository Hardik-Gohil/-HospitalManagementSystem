package com.HospitalManagementSystem.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.HospitalManagementSystem.entity.history.PatientHistory;

public interface PatientHistoryRepository extends JpaRepository<PatientHistory, Long> {

}