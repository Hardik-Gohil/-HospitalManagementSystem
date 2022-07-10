package com.HospitalManagementSystem.repository;

import org.springframework.data.jpa.datatables.repository.DataTablesRepository;

import com.HospitalManagementSystem.entity.Patient;

public interface PatientDataTablesRepository extends DataTablesRepository<Patient, Long> {

}