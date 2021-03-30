package com.app.metier;

import org.springframework.data.domain.Page;

import com.app.entities.User;

public interface UserMetier {
        
	public boolean inscrire(User Cl);
	public Page<User> listClients(String nom ,int page , int size);
	public int hasRole(String mail);
}
