package com.app.controller;

import java.io.IOException;
import java.security.Principal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.UUID;
import java.util.ArrayList;
import java.util.Date;

import javax.security.auth.message.config.AuthConfig;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.hibernate.context.spi.CurrentSessionContext;
import org.hibernate.sql.Update;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.security.authentication.encoding.PasswordEncoder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.thymeleaf.standard.expression.Token;

import com.app.dao.AnnulationRepository;
import com.app.dao.AppOffreRepository;
import com.app.dao.AttributionRepository;
import com.app.dao.UserRepository;
import com.app.dao.UserRoleRepository;
import com.app.entities.Annulation;
import com.app.entities.ApOffre;
import com.app.entities.Attribution;
import com.app.entities.User;
import com.app.entities.UserRole;
import com.app.metier.AnnonceMetier;
import com.app.metier.UserMetier;

@Controller
public class ComteController {
    
	@Autowired
	private AnnonceMetier appoffremetier;
	@Autowired
	private UserRepository clientr;
	@Autowired
	private UserRoleRepository roleRepository; 
	@Autowired
	private UserMetier clientm;
	@Autowired
	private UserRepository userService;
	@Autowired
	private AnnonceMetier annonceMetier;
	@Autowired
	AppOffreRepository appOffreRepository;
	@Autowired
	AnnulationRepository annulationRepository;
	@Autowired
	private AttributionRepository attributionRepository;
    private User user;
    private UserRole userRole;
    private String testPass = "";
    private int i;
    private Model modelG;
    
   
	
//	private AppOffreRepository appoffrerepository;
	
	@RequestMapping(value="/")
	public String Seconecter() {
		return "redirect:/compte/listeappoffres";
	}
	@RequestMapping("/compte/profil")
	public String ProfilCompte(Model model ,Principal principal) {
		String email = principal.getName();
		user = userService.findOne(email);
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		String date = format.format(user.getDate_naissance());
		i = clientm.hasRole(email);
		model.addAttribute("user", user);
		model.addAttribute("date", date);
		if(testPass == null) {
		model.addAttribute("passError", "ok");	
		}else if(testPass =="Ok")
		{
			model.addAttribute("passSucc", "ok");	
		}
		testPass = "";
		return "PageClient/compte";	
		
		}
	@RequestMapping("/compte/listeappoffres")
	public String appOffre(Model model,@RequestParam(defaultValue="0")int numpage,@RequestParam(defaultValue="",name="rech")String rech
            ,@RequestParam(defaultValue="Equipement industriel, Outillage et Pièce détachée",name="catg")String catg) throws ParseException {

		
		Page<ApOffre> pages = appoffremetier.chercherAppOffre("%"+rech+"%",catg, numpage, 12);		
		String test  = null;
		String test1  = "Ok";
		String test2  = "Ok";
		String totale  = null;
		int	Pag1 = numpage - 1;
		int pag2 = numpage + 1;
		if(Pag1 >= 0) {
			test  = "OK";
		}
		if(pag2 > pages.getTotalPages()-1) {
			test1  = null;
		}
		int page = pages.getTotalPages();
		if(page > 0) {
		totale="ok";
		}
		model.addAttribute("appoffre", pages.getContent());
		model.addAttribute("totale", totale);
		model.addAttribute("numpage", numpage);
		model.addAttribute("pr", Pag1);
		model.addAttribute("sui", pag2);
		model.addAttribute("rech", rech);
		model.addAttribute("catg", catg);
		model.addAttribute("test", test);
		model.addAttribute("test1", test1);
		model.addAttribute("tester", test2);
		return "PageClient/compte";
	}
	@RequestMapping("/compte/listeclient")
	public String client(Model model,@RequestParam(defaultValue="",name="rech")String nom,@RequestParam(defaultValue="0")int numpage) {
		Page<User> cl = clientm.listClients("%"+nom+"%", 0, 20);
		String test  = null;
		String test1  = "Ok";
		String test2  = "Ok";
		String totale  = "ok";
		int	Pag1 = numpage - 1;
		int pag2 = numpage + 1;
		if(Pag1 >= 0) {
			test  = "OK";
		}
		if(pag2 > cl.getTotalPages()-1) {
			test1  = null;
		}
		long page = cl.getTotalElements();
		if(page <= 0) {
		totale=null;
		}
		model.addAttribute("cl", cl.getContent());
		model.addAttribute("test", test);
		model.addAttribute("numpage", numpage);
		model.addAttribute("pr", Pag1);
		model.addAttribute("sui", pag2);
		model.addAttribute("rech", nom);
		model.addAttribute("test", test);
		model.addAttribute("test1", test1);
		model.addAttribute("tester", test2);
		model.addAttribute("totale", totale);
		return "PageClient/compte";
	}
	@RequestMapping("/compte/listeappoffres/attribution")
	public String attribution(Model model,@RequestParam(defaultValue="0")int numpage,@RequestParam(defaultValue="",name="rech")String rech
            ,@RequestParam(defaultValue="Equipement industriel, Outillage et Pièce détachée",name="catg")String catg) {
		Page<Attribution> pages = appoffremetier.chercherAttribution("%"+rech+"%",catg, numpage, 15);
		String test  = null;
		int Pag1 = numpage - 1;
		int pag2 = numpage + 1;
		String totale  = null;
		String test1  = "Ok";
		String test2  = "Ok";
		if(Pag1 >= 0) {
			test  = "OK";
		}
		if(pag2 > pages.getTotalPages()-1) {
			test1  = null;
		}
		int page = pages.getTotalPages();
		if(page > 0) {
		totale="ok";
		}
		System.out.println(page);
		model.addAttribute("totale", totale);
		model.addAttribute("pr", Pag1);
		model.addAttribute("sui", pag2);
		model.addAttribute("attribution", pages.getContent());
		model.addAttribute("numpage", numpage);
		model.addAttribute("rech", rech);
		model.addAttribute("catg", catg);
		model.addAttribute("tester", test2);
		model.addAttribute("test", test);
		model.addAttribute("test1", test1);
		return "PageClient/compte";
	}
	@RequestMapping("/compte/listeappoffres/annulation")
	public String annulation(Model model,@RequestParam(defaultValue="0")int numpage,@RequestParam(defaultValue="",name="rech")String rech
            ,@RequestParam(defaultValue="Equipement industriel, Outillage et Pièce détachée",name="catg")String catg) {
		Page<Annulation> pages = appoffremetier.chercherAnnulation("%"+rech+"%",catg, numpage, 15);
		String test  = null;
		String totale  = null;
		int Pag1 = numpage - 1;
		int pag2 = numpage + 1;
		String test1  = "Ok";
		String test2  = "Ok";
		if(Pag1 >= 0) {
			test  = "OK";
		}
		if(pag2 > pages.getTotalPages()-1) {
			test1  = null;
		}
		int page = pages.getTotalPages();
		if(page > 0) {
		totale="ok";
		}
		model.addAttribute("totale", totale);
		model.addAttribute("pr", Pag1);
		model.addAttribute("sui", pag2);
		model.addAttribute("annulation", pages.getContent());
		model.addAttribute("numpage", numpage);
		model.addAttribute("rech", rech);
		model.addAttribute("catg", catg);
		model.addAttribute("tester", test2);
		model.addAttribute("test", test);
		model.addAttribute("test1", test1);
		return "PageClient/compte";
	}
	@RequestMapping("/supprimer")
	public String Suppression(Model model ,String mail) {
		List <UserRole> userrole = roleRepository.findAll();
		int id=0;
		for (UserRole userRole2 : userrole) {
			if(userRole2.getMail().equals(mail)) {
		     id = userRole2.getId();	
			}
		}
		roleRepository.delete(id);
		clientr.delete(mail);
		String Sup = "ok";
		return "redirect:/compte/listeclient";
	}
	
	@RequestMapping(value="/modifier",method=RequestMethod.PUT)
	public String modifier(String nom,String prenom,String date_naissance,String entreprise,String ctg, String telephone) throws ParseException {
		
		user.setNom(nom);
		user.setPrenom(prenom);
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		Date date = format.parse(date_naissance);
		user.setDate_naissance(date);
		user.setEntreprise(entreprise);
		user.setCtg(ctg);
		user.setTelephone(telephone);
		clientr.save(user);
	    
		return "redirect:/compte/profil";
	}
	@RequestMapping(value="/modifierpass",method=RequestMethod.PUT)
	public String modifierpass(Model model, String password,String new_password) throws InterruptedException {
		
		PasswordEncoder encoder = new Md5PasswordEncoder();
	
		if(user.getPassword().equals(encoder.encodePassword(password, null))) {
		user.setPassword(encoder.encodePassword(new_password, null));
		clientr.save(user);
		testPass = "Ok";
		return "redirect:/compte/profil";
		}
		else {
		testPass = null;
	    return "redirect:/compte/profil";	
		}
	}
//	@RequestMapping("/update")
//    public String update() throws IOException {
//
//		annonceMetier.updateAppOffre();
//    	return "redirect:/compte/listeappoffres";
//    }
	@GetMapping("/findoneApp")
	@ResponseBody
	public ApOffre createfindone1(@RequestParam("id") String id) {
		
		

		ApOffre apOffre = appOffreRepository.findOne(id);	
		return apOffre;
		
	}
	@GetMapping("/findoneAnn")
	@ResponseBody
	public Annulation createfindone2(@RequestParam("id") String id) {
		
		

		Annulation apOffre = annulationRepository.findOne(id);	
		return apOffre;
		
	}
	@GetMapping("/findoneAtt")
	@ResponseBody
	public Attribution createfindone3(@RequestParam("id") String id) {
		
		
        Attribution attribution = attributionRepository.findOne(id);
		return attribution;
		
	}

	
	
	
}
