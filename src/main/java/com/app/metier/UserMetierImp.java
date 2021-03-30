package com.app.metier;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.security.authentication.encoding.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.app.dao.UserRepository;
import com.app.dao.UserRoleRepository;
import com.app.entities.User;
import com.app.entities.UserRole;

@Service
public class UserMetierImp implements UserMetier{
    
	@Autowired
	private UserRepository clientrepository;
	@Autowired
	private UserRoleRepository UserRolerep;

	@Override
	public boolean inscrire(User Cl) {	
    User cl = clientrepository.findOne(Cl.getMail());
    if(cl == null) {
		PasswordEncoder encoder = new Md5PasswordEncoder();
		String password = encoder.encodePassword(Cl.getPassword(), null);
		Cl.setPassword(password);
        UserRolerep.save(new UserRole(Cl.getMail(),"USER"));
    	clientrepository.save(Cl);
    	return true;
    }else
    {
	    return false;
    }
	}



	@Override
	public Page<User> listClients(String nom,int page, int size) {
    return clientrepository.listClient(nom,new PageRequest(page, size, new Sort(Direction.ASC, "prenom")));
	}



	@Override
	public int hasRole(String mail) {
		int i = 0;
		List<UserRole> user = UserRolerep.findAll();
		for (UserRole userRole : user) {
			if(userRole.getMail().equals(mail)) {
				i++;
			}
		}
		return i;
	}

}
