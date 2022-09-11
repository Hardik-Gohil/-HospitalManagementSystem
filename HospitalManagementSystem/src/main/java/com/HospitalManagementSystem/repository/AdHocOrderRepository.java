package com.HospitalManagementSystem.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.HospitalManagementSystem.entity.AdHocOrder;

public interface AdHocOrderRepository extends JpaRepository<AdHocOrder, Long>, JpaSpecificationExecutor<AdHocOrder> {

	List<AdHocOrder> findAllByPatientPatientId(Long patientId);

}
