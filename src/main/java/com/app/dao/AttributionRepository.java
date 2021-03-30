package com.app.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.app.entities.ApOffre;
import com.app.entities.Attribution;



public interface AttributionRepository extends JpaRepository<Attribution, String>{

    @Query("select a from Attribution a where a.ctg like :x and a.obj like :y")
	public Page<Attribution> chercherAttribution(@Param("y")String mc,@Param("x")String catégorie ,Pageable pageable);
	
}
