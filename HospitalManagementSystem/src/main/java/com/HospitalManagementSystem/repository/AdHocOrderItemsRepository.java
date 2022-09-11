package com.HospitalManagementSystem.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.HospitalManagementSystem.entity.AdHocOrderItems;

public interface AdHocOrderItemsRepository extends JpaRepository<AdHocOrderItems, Long>, JpaSpecificationExecutor<AdHocOrderItems> {


}