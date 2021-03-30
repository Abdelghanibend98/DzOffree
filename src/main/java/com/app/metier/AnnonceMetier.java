package com.app.metier;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.app.entities.Annulation;
import com.app.entities.ApOffre;
import com.app.entities.Attribution;

public interface AnnonceMetier {

	  public ArrayList<String> getAppOffre(String lns) throws IOException;
	  public void updateAppOffre(String page) throws IOException;
      
	  public List<ApOffre> findByCatégorie(String catégorie);
	  public Page<ApOffre> findByCatégorie(String catégorie,Pageable pageable);
	  public Page<ApOffre> chercherAppOffre(String mc,String catégorie ,int page ,int size);
	  public Page<Attribution> chercherAttribution(String mc,String catégorie ,int page ,int size);
	  public Page<Annulation> chercherAnnulation(String mc,String catégorie ,int page ,int size);

}
