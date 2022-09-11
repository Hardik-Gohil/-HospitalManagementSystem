package com.HospitalManagementSystem.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.HospitalManagementSystem.entity.master.ServiceItems;
import com.HospitalManagementSystem.entity.master.ServiceMaster;

public interface ServiceItemsRepository extends JpaRepository<ServiceItems, Long>, JpaSpecificationExecutor<ServiceItems> {

	List<ServiceItems> findAllByIsActive(Boolean isActive);

}
