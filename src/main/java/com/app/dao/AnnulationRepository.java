package com.app.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.app.entities.Annulation;



public interface AnnulationRepository extends JpaRepository<Annulation, String> {

    @Query("select a from Annulation a where a.ctg like :x and a.obj like :y")
	public Page<Annulation> chercherAnnulation(@Param("y")String mc,@Param("x")String catégorie ,Pageable pageable);
	
	

}
