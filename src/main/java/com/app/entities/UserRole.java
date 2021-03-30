package com.app.entities;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.PrimaryKeyJoinColumn;




@Entity
public class UserRole implements Serializable{
     
	 @Id
	 @GeneratedValue
	 private int id;
	 private String mail;
     @Column(name="role", length=20)
	 private String role;
     
     
     
	public UserRole() {
		super();
	}
	public UserRole(String mail, String role) {
		super();
		this.mail = mail;
		this.role = role;
	}
	public String getMail() {
		return mail;
	}
	public void setMail(String mail) {
		this.mail = mail;
	}
	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
    
	
	
     
}
