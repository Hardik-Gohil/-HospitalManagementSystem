package com.HospitalManagementSystem.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.HospitalManagementSystem.entity.master.Role;

public interface RoleRepository extends JpaRepository<Role, Long> {

}
