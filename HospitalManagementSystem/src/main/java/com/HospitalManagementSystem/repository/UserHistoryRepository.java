package com.HospitalManagementSystem.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.HospitalManagementSystem.entity.UserHistory;

public interface UserHistoryRepository extends JpaRepository<UserHistory, Long> {
}