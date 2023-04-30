package com.HospitalManagementSystem.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.HospitalManagementSystem.entity.master.ServiceSubType;

public interface ServiceSubTypeRepository extends JpaRepository<ServiceSubType, Long>, JpaSpecificationExecutor<ServiceSubType> {

	List<ServiceSubType> findAllByIsActive(Boolean isActive);

}
