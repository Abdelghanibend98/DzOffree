package com.app.metier;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.app.dao.AppOffreRepository;
import com.app.entities.ApOffre;

@Service
public class AsyncService {

	
	@Autowired
	private AnnonceImp results;
	@Autowired
	private AppOffreRepository  appOffrerepo;
	 
	@Async
    synchronized public void Update() throws InterruptedException, IOException {
        
		SimpleDateFormat format = new SimpleDateFormat("hh:mm:ss");
		Date date =  new Date();
        Date dateCourant = new Date();
		date.setHours(00);
		date.setMinutes(00);
		date.setSeconds(00);
		long d;
		int compare = date.compareTo(dateCourant);
		if(compare > 0) {
		d = date.getTime() - dateCourant.getTime() ; 	
		}else {
		d = dateCourant.getTime() - date.getTime() ;	
		}
		Thread.sleep(d);
		results.updateAppOffre("http://www.algeriatenders.com/old.php");
		results.updateAppOffre("http://www.algeriatenders.com/annulations.php");
		results.updateAppOffre("http://www.algeriatenders.com/attributions.php");
	
}
	@Async
    synchronized public void Traiter() throws InterruptedException, IOException, ParseException {
        
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Date date_courante = new Date();
        List <ApOffre> liste_App_Offre = appOffrerepo.findAll();
        for (ApOffre apOffre : liste_App_Offre) {
			Date Echeance = format.parse(apOffre.getEcheance());
			int Compare = date_courante.compareTo(Echeance);
			if(Compare > 0) {
			apOffre.setEtt(false);
			appOffrerepo.save(apOffre);
			}
        	
		}
}
	
	}
