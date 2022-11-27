package com.HospitalManagementSystem.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.HospitalManagementSystem.entity.Notifications;

public interface NotificationsRepository extends JpaRepository<Notifications, Long>, JpaSpecificationExecutor<Notifications> {

	Integer countByUserUserId(Long userId);

	Integer countByUserUserIdAndIsRead(Long userId, Boolean isRead);

	void deleteAllByCodeInAndObjectId(List<String> notificationCodeList, Long objectId);

}
