package com.HospitalManagementSystem.repository;

import org.springframework.data.jpa.datatables.repository.DataTablesRepository;

import com.HospitalManagementSystem.entity.AdHocOrder;

public interface AdHocOrderDataTablesRepository extends DataTablesRepository<AdHocOrder, Long> {

}