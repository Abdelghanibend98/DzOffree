package com.app;



import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.Executor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import com.app.dao.UserRepository;
import com.app.entities.Annulation;
import com.app.entities.ApOffre;
import com.app.entities.Attribution;
import com.app.entities.User;
import com.app.metier.AnnonceMetier;
import com.app.metier.AsyncService;
import com.app.metier.UserMetier;

@SpringBootApplication
@EnableAsync
public class DzOffreeApplication implements CommandLineRunner{
	
	@Autowired
    private AsyncService asyncService;
	public static void main(String[] args) throws IOException {
		ApplicationContext ctx = SpringApplication.run(DzOffreeApplication.class, args);
		
	
	    
//		UserMetier userMetier = ctx.getBean(UserMetier.class); 
//		userMetier.inscrire(new User("abgh.bend98@gmail.com", "ghani1998", "benyamina","abdelghani", new Date(), "0656205685", "vffvr", "svefvr"));
//		userMetier.inscrire(new User("bens.moh@gmail.com", "moh1997", "bensouna","mohamed", new Date(), "0656205685", "vffvr", "svefvr"));
     
//		Page<Client> pg = clientMetier.listClients(0, 5);
//		for (Client client : pg.getContent()) {
//			System.out.println(client.getPrenom());
//		}
//		AnnonceMetier annonceMetier = ctx.getBean(AnnonceMetier.class);
//	    Page<Attribution> pg= annonceMetier.chercherAttribution("%r%", "Bâtiment et Génie civil",0, 4);
//	    for (Attribution apOffre : pg.getContent()) {
//	    	
//	    	System.out.println(apOffre.getCtg());
//			System.out.println(apOffre.getParution());
//			System.out.println(apOffre.getObjet());
//			System.out.println(apOffre.getType());
//			System.out.println(apOffre.getRef());
//			System.out.println("_______________");
//		}
//		
	 
//	  annonceMetier.updateAppOffre("http://www.algeriatenders.com/annulations.php");
	}
	@Bean
    public Executor taskExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(2);
        executor.setMaxPoolSize(2);
        executor.setQueueCapacity(500);
        executor.setThreadNamePrefix("Update-");
        executor.initialize();
        return executor;
    }
	@Override
    public void run(String... args) throws Exception {
	
		
		asyncService.Traiter();
		asyncService.Update();
		
      
        
	}
	
}
