package com.app.sec;

import java.io.IOException;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.mail.MessagingException;
import javax.mail.SendFailedException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.datetime.DateFormatter;
import org.springframework.mail.MailException;
import org.springframework.mail.MailSendException;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.security.authentication.encoding.PasswordEncoder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.app.dao.UserRepository;
import com.app.entities.User;
import com.app.metier.Mailservice;
import com.app.metier.UserMetier;

@Controller
public class SecuritéController {
	
	@Autowired
	private UserRepository userrep;
	@Autowired
	private UserMetier userMetier; 
	@Autowired
	private Mailservice notificationService;
	private User userIns;
	private int uuid;
	private String EmailPasse;
    
	
	
	@RequestMapping(value="/home")
	public String home(Model model) {
		String insc = "Ok";
		model.addAttribute("home", insc);
		return "PagesHome/index";
	}
	
	@RequestMapping(value="/espaceclient")
	public String PageInscription(Model model) {
		String insc = "Ok";
		model.addAttribute("insc", insc);
		return "PagesHome/index";
	}
	
	
	
	@RequestMapping(value="/registration", method=RequestMethod.POST)
	public String Inscrire(@ModelAttribute("user") User user,Model model) {
	   	
		userIns = user;
		userIns.setMail(user.getMail());
		userIns.setNom(user.getNom());
		userIns.setPrenom(user.getPrenom());
		userIns.setDate_naissance(user.getDate_naissance());
		userIns.setPassword(user.getPassword());
		userIns.setCtg(user.getCtg());
		userIns.setEntreprise(user.getEntreprise());
		userIns.setTelephone(user.getTelephone());
        User UserT = userrep.findOne(userIns.getMail());
        if(UserT != null) {
         String t= "Ok";
         model.addAttribute("testeinsc",t);
         String insc = "Ok";
         model.addAttribute("insc", insc);
         return "PagesHome/index";
        }
        String insc = "Ok";
        model.addAttribute("mailConf", "Ok");
		model.addAttribute("insc", insc);
        return "PagesHome/index";
		
	    
	}
	
	
	@RequestMapping(value="/TermineInsc")
	public String TerminerInsc(Model model) {
		boolean test = userMetier.inscrire(userIns);
		if(test == false) {
	    model.addAttribute("testeinsc", test);
	    String insc = "Ok";
		model.addAttribute("insc", insc);
		return "PagesHome/index";
		}
	    String insc = "Ok";
		model.addAttribute("succ", insc);
	    return "PagesHome/index";    
	}
	

	
	@RequestMapping(value="/CodeConfirme")
	public String SetConfirmer(Model model){
	   	
        uuid = UUID.randomUUID().hashCode(); 
        try {
    	    notificationService.sendEmail(userIns.getMail(),String.valueOf(uuid));
    		String c = "Ok";
    		String insc = "Ok";
    		model.addAttribute("insc", insc);
    		model.addAttribute("Code", c);
    		return "PagesHome/index";
	
		} catch (MailSendException ex) {
            ex.printStackTrace();
			String c = "Ok";
			String insc = "Ok";
			model.addAttribute("insc", insc);
			model.addAttribute("MailError", c);
			return "PagesHome/index";
	
		}
        
	}
	
	
	
	
	@RequestMapping(value="/TermineConf", method=RequestMethod.POST)
	public String send(Model model, @RequestParam("CodeConf") String CodeConf) throws MailException{

		
		User user = userIns;
        if(CodeConf.equals(String.valueOf(uuid))) {
    		boolean test = userMetier.inscrire(user);
    		if(test == false) {
    	    model.addAttribute("testeinsc", test);
    	    String insc = "Ok";
    		model.addAttribute("insc", insc);
    		return "PagesHome/index";
    		}
    	    String insc = "Ok";
    		model.addAttribute("succ", insc);
    	    return "PagesHome/index";
        	
        }else {
            uuid = UUID.randomUUID().hashCode();      
    	    notificationService.sendEmail(userIns.getMail(),String.valueOf(uuid));        	
    		String erreur = "Err";
    		model.addAttribute("CodeErreur", erreur);
    		String c = "Ok";
    		String insc = "Ok";
    		model.addAttribute("insc", insc);
    		model.addAttribute("Code", c);
    		return "PagesHome/index";	
        }
        	
	}	
	
	@RequestMapping(value="/EnvoyerMail",method = RequestMethod.POST)
	public String PassOublie(Model model,@RequestParam("mail") String mail) {
		
		User Test_User = userrep.findOne(mail);
		if(Test_User == null) {
			String Code="ok";
			String insc = "Ok";
			model.addAttribute("MailError", Code);
			model.addAttribute("home", insc);
			return "PagesHome/index";	
		}
		EmailPasse = mail;
		uuid = UUID.randomUUID().hashCode();
	    notificationService.sendEmail(mail,String.valueOf(uuid));
        String Code="ok";
		String insc = "Ok";
		model.addAttribute("home", insc);
		model.addAttribute("MailCode", Code);
		return "PagesHome/index";
	}

	@RequestMapping(value="/ReinitialisePasse",method = RequestMethod.POST)
	public String PassTeinitialse(Model model,@RequestParam("MailCode") String code,@RequestParam("password")String passe) {

		PasswordEncoder encoder = new Md5PasswordEncoder();
		User user = userrep.findOne(EmailPasse);
         if(code.equals(String.valueOf(uuid))) {
    		user.setPassword(encoder.encodePassword(passe, null));
    		userrep.save(user);
    		String insc = "Ok";
    		model.addAttribute("succPasse", insc);
    	    return "PagesHome/index";
        	
        }else {
        	String erreur = "Err";
    		model.addAttribute("CodeErreurPasse", erreur);
    		String insc = "Ok";
    		model.addAttribute("home", insc);
    		return "PagesHome/index";	
        }

	}
	@RequestMapping(value="/logout", method = RequestMethod.GET)
	public String logoutPage (HttpServletRequest request, HttpServletResponse response) {
	    Authentication auth = SecurityContextHolder.getContext().getAuthentication();
	    if (auth != null){    
	        new SecurityContextLogoutHandler().logout(request, response, auth);
	    }
	    return "redirect:/home?logout";
	}
	
	
	
}
