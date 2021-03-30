package com.app.entities;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.PrimaryKeyJoinColumn;

import org.hibernate.annotations.Table;
import org.springframework.context.annotation.Primary;


@Entity
public class ApOffre implements Serializable{
      
	  private String date_ajout;
	  private String ctg;
	  @Column(name="ref", length=30)
	  @Id
	  private String ref;
	  private String type;
	  private String description;
	  private String echeance;
	  private boolean ett;
	  
	  
	public ApOffre() {
		super();
	}
	
	
	public ApOffre(String date_ajout, String ctg, String ref, String type, String descrition, String date_éch, boolean ett) {
		super();
		this.date_ajout = date_ajout;
		this.ctg = ctg;
		this.ref = ref;
		this.type = type;
		this.description = descrition;
		this.echeance = date_éch;
		this.ett = ett;
	}
	

	public String getDate_ajout() {
		return date_ajout;
	}
	public void setDate_ajout(String date_ajout) {
		this.date_ajout = date_ajout;
	}
    
	public String getRef() {
		return ref;
	}
	public void setRef(String ref) {
		this.ref = ref;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getDescrition() {
		return description;
	}


	public String getCtg() {
		return ctg;
	}


	public void setCtg(String ctg) {
		this.ctg = ctg;
	}


	public String getDescription() {
		return description;
	}


	public void setDescription(String description) {
		this.description = description;
	}


	public String getEcheance() {
		return echeance;
	}


	public void setEcheance(String echeance) {
		this.echeance = echeance;
	}


	public boolean isEtt() {
		return ett;
	}


	public void setEtt(boolean ett) {
		this.ett = ett;
	}

	  
	  
	  
}
