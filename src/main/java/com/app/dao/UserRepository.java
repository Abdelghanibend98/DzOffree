package com.app.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.app.entities.User;

public interface UserRepository extends JpaRepository<User, String>{
	
	  
	  @Query("select c from User c where c.nom like :x")
	  public Page<User> listClient(@Param("x")String nom,Pageable pageable);
}
