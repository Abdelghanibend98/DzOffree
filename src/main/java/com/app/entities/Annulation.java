package com.app.entities;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;


@Entity
public class Annulation implements Serializable{
    
	  private String prt;
	  private String ctg;
	  @Column(name="ref", length=30)
	  @Id
	  private String ref;
	  private String type;
	  @Column(name="obj", length=100000)
	  private String obj;
	  
	  
	  
	  
	public Annulation() {
		super();
	}




	public Annulation(String parution, String ctg, String ref, String type, String objet) {
		super();
		this.prt = parution;
		this.ctg = ctg;
		this.ref = ref;
		this.type = type;
		this.obj = objet;
	}







	public String getCtg() {
		return ctg;
	}




	public void setCtg(String ctg) {
		this.ctg = ctg;
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




	public String getPrt() {
		return prt;
	}




	public void setPrt(String prt) {
		this.prt = prt;
	}




	public String getObj() {
		return obj;
	}




	public void setObj(String obj) {
		this.obj = obj;
	}




    




	
}
