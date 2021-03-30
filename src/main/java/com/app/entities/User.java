package com.app.entities;

import java.io.Serializable;
import java.security.Key;
import java.util.Date;

import javax.crypto.spec.SecretKeySpec;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Email;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.security.authentication.encoding.PasswordEncoder;



@Entity
public class User implements Serializable{
	 

	@Id
	private String mail;
	@Column(name="password", length=200)
	private String password;
	@Column(name="nom", length=40)
	private String nom;
	@Column(name="prenom", length=40)
	private String prenom;
	@DateTimeFormat(pattern="yyyy-MM-dd")
	private Date date_naissance;
	@Column(name="telephone", length=20)
	private String telephone;
	private String ctg;
	private String entreprise;
	

    
	public User() {
		super();
		// TODO Auto-generated constructor stub
	}

    


	public User(String mail, String password, String nom, String prenom, Date date_naissance, String telephone,
			String ctg, String entreprise) {
		
		super();
		this.mail = mail;
		this.password = password;
		this.nom = nom;
		this.prenom = prenom;
		this.date_naissance = date_naissance;
		this.telephone = telephone;
		this.ctg = ctg;
		this.entreprise = entreprise;
	}



	public String getMail() {
		return mail;
	}




	public void setMail(String mail) {
		this.mail = mail;
	}




	public String getPassword() {
		return password;
	}




	public void setPassword(String password) {
		this.password = password;
	}




	public String getNom() {
		return nom;
	}




	public void setNom(String nom) {
		this.nom = nom;
	}




	public String getPrenom() {
		return prenom;
	}




	public void setPrenom(String prenom) {
		this.prenom = prenom;
	}




	public Date getDate_naissance() {
		return date_naissance;
	}




	public void setDate_naissance(Date date_naissance) {
		this.date_naissance = date_naissance;
	}




	public String getTelephone() {
		return telephone;
	}




	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}






	public String getCtg() {
		return ctg;
	}




	public void setCtg(String ctg) {
		this.ctg = ctg;
	}




	public String getEntreprise() {
		return entreprise;
	}




	public void setEntreprise(String entreprise) {
		this.entreprise = entreprise;
	}





}
