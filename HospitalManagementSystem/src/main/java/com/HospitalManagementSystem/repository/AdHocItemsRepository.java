package com.HospitalManagementSystem.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.HospitalManagementSystem.entity.master.AdHocItems;

public interface AdHocItemsRepository extends JpaRepository<AdHocItems, Long>, JpaSpecificationExecutor<AdHocItems> {

	List<AdHocItems> findAllByIsActive(Boolean isActive);

}
