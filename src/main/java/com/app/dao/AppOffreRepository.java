package com.app.dao;


import java.util.ArrayList;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.RequestParam;

import com.app.entities.ApOffre;

public interface AppOffreRepository extends JpaRepository<ApOffre, String>{
    @Query("select a from ApOffre a where a.ctg like :x and a.description like :y")
	public Page<ApOffre> chercherAppOffre(@Param("y")String mc,@Param("x")String catégorie ,Pageable pageable);
	
    @Query("select a from ApOffre a where a.ctg like :x")
    public Page<ApOffre> findByCatégorie(@Param("x")String catégorie,Pageable pageable);
}
