package com.HospitalManagementSystem.repository;

import org.springframework.data.jpa.datatables.repository.DataTablesRepository;

import com.HospitalManagementSystem.entity.Notifications;

public interface NotificationsDataTablesRepository extends DataTablesRepository<Notifications, Long> {

}