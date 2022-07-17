package com.HospitalManagementSystem.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.HospitalManagementSystem.entity.master.ServiceMaster;

public interface ServiceMasterRepository extends JpaRepository<ServiceMaster, Long>, JpaSpecificationExecutor<ServiceMaster> {

	List<ServiceMaster> findAllByIsActive(Boolean isActive);

}
