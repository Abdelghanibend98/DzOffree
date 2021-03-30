package com.app.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.app.entities.UserRole;

public interface UserRoleRepository extends JpaRepository<UserRole, Integer>{

}
