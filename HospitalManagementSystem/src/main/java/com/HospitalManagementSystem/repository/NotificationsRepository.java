package com.HospitalManagementSystem.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.HospitalManagementSystem.entity.Notifications;

public interface NotificationsRepository extends JpaRepository<Notifications, Long>, JpaSpecificationExecutor<Notifications> {

	Integer countByUserUserId(Long userId);

	Integer countByUserUserIdAndIsRead(Long userId, Boolean isRead);

}
