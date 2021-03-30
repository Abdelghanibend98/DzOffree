package com.app.metier;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.app.entities.User;

@Service
public class Mailservice {

	
	private JavaMailSender javaMailSender;

	
	@Autowired
	public Mailservice(JavaMailSender javaMailSender) {
		this.javaMailSender = javaMailSender;
	}
	
	public void sendEmail(String maill , String message) throws MailException {



		SimpleMailMessage mail = new SimpleMailMessage();
		mail.setTo(maill);
		mail.setSubject("Code de Confirmation");
		mail.setText("Recevez vous le code de confirmation de votre compte DzOffre : "+message);

		/*
		 * This send() contains an Object of SimpleMailMessage as an Parameter
		 */
		javaMailSender.send(mail);
	}
}
