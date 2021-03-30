package com.app.metier;


import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.app.dao.AnnulationRepository;
import com.app.dao.AppOffreRepository;
import com.app.dao.AttributionRepository;
import com.app.entities.Annulation;
import com.app.entities.ApOffre;
import com.app.entities.Attribution;

@Service
public class AnnonceImp implements AnnonceMetier{
    
	@Autowired
	private AppOffreRepository appOffrerepository;
	@Autowired
	private AnnulationRepository annulationrepository;
	@Autowired
	private AttributionRepository attributionrepository;

	@Override
	public ArrayList<String> getAppOffre(String lns) throws IOException {
	
		
		Document doc = Jsoup.connect(lns).get();
		String liens = null , liens2 = null ;
		ArrayList<String> A = new ArrayList<String>(); 
		
		// extraire les catégories.
		for (Element e : doc.select("body > table > tbody > tr:nth-child(2) > td:nth-child(1) > table > tbody > tr:nth-child(2) > td:nth-child(2) > table tr")) {
			
			liens = "http://www.algeriatenders.com/"+e.select("td:nth-of-type(1) a").attr("href");
			Document doc1 = Jsoup.connect(liens).get();
			for (Element e1 : doc1.select("form[target='print'] p a")) {
		    liens2 = "http://www.algeriatenders.com/"+ e1.attr("href");
		    A.add(liens2);
			}
			
			liens= "http://www.algeriatenders.com/"+e.select("td:nth-of-type(2) a").attr("href");
			doc1 = Jsoup.connect(liens).get();
			for (Element e1 : doc1.select("form[target='print'] p a")) {
				liens2 = "http://www.algeriatenders.com/"+e1.attr("href");
			    A.add(liens2);
				}
		   
		}
        System.out.println("hello");
		return A;
		
		
		
	
	}

	@Override
	public void updateAppOffre(String page) throws IOException {
	  ApOffre apOffre = new ApOffre();
	  Annulation annulation = new Annulation();
	  Attribution attribution = new Attribution(); 
	  ArrayList<String> App = this.getAppOffre(page);
//	  ArrayList<String> Attr = this.getAppOffre("http://www.algeriatenders.com/attributions.php");
//	  ArrayList<String> Annl = this.getAppOffre("http://www.algeriatenders.com/annulations.php");
  	  if(page.equals("http://www.algeriatenders.com/old.php")) {
  		  for (String B : App) {
  			Document doc2 = Jsoup.connect(B).get();
  			for (Element s : doc2.select("form[target='print'] table tr")) {
  				apOffre.setDate_ajout(s.select("td:nth-of-type(1)").text());
  				apOffre.setCtg(s.select("td:nth-of-type(2)").text());
  				apOffre.setRef(s.select("td:nth-of-type(3)").text());
  				apOffre.setType(s.select("td:nth-of-type(4)").text());
  				apOffre.setDescription(s.select("td:nth-of-type(5)").text());
  				apOffre.setEcheance(s.select("td:nth-of-type(6)").text());
  				if(apOffre.getDate_ajout().length() >= 11 || apOffre.getCtg().equals("Catégorie") || 
  						apOffre.getCtg().equals("") || apOffre.getRef().equals("Réf") 
  						|| apOffre.getType().equals("Type") || apOffre.getDescrition().equals("Description")
  						|| apOffre.getEcheance().equals("Echéance"))
  				{
  					continue;
  				}
  				else
  				{  
  	
  	appOffrerepository.save(new ApOffre(apOffre.getDate_ajout(),apOffre.getCtg() , apOffre.getRef(), apOffre.getType(), apOffre.getDescrition(), apOffre.getEcheance(), true));
  				}
  				
  				
  			}
  			
      }

  	  }
  	  else if(page.equals("http://www.algeriatenders.com/annulations.php")) {
  	  	  for (String B : App) {
  			Document doc2 = Jsoup.connect(B).get();
  			for (Element s : doc2.select("form[target='print'] table tr")) {
  			    annulation.setPrt(s.select("td:nth-of-type(1)").text());
  			    annulation.setCtg(s.select("td:nth-of-type(2)").text());
  			    annulation.setRef(s.select("td:nth-of-type(3)").text());
  			    annulation.setType(s.select("td:nth-of-type(4)").text());
  			    annulation.setObj(s.select("td:nth-of-type(5)").text());
  				if(annulation.getPrt().length() >= 11 || annulation.getCtg().equals("Catégorie") || 
  						annulation.getCtg().equals("") || annulation.getRef().equals("Réf") 
  						|| annulation.getType().equals("Type") || annulation.getObj().equals("Objet"))
  				{
  					continue;
  				}
  				else
  				{  
  	annulationrepository.save(new Annulation(annulation.getPrt(), annulation.getCtg(), annulation.getRef(),annulation.getType(), annulation.getObj()));				
  	
  				}
  				
  				
  			}
  			
      }
  		  
  	  }
  	  else {
  	  	  for (String B : App) {
  			Document doc2 = Jsoup.connect(B).get();
  			for (Element s : doc2.select("form[target='print'] table tr")) {
  			    attribution.setPrt(s.select("td:nth-of-type(1)").text());;
  			    attribution.setCtg(s.select("td:nth-of-type(2)").text());
  			    attribution.setRef(s.select("td:nth-of-type(3)").text());
  			    attribution.setType(s.select("td:nth-of-type(4)").text());
  			    attribution.setObj(s.select("td:nth-of-type(5)").text());;
  				if(attribution.getPrt().length() >= 11 || attribution.getCtg().equals("Catégorie") || 
  						attribution.getCtg().equals("") || attribution.getRef().equals("Réf") 
  						|| attribution.getType().equals("Type") || attribution.getObj().equals("Objet"))
  				{
  					continue;
  				}
  				else
  				{  
  	attributionrepository.save(new Attribution(attribution.getPrt(), attribution.getCtg(), attribution.getRef(),attribution.getType(), attribution.getObj()));				
  	
  				}
  				
  				
  			}
  			
      }
  		  
  	  }

		
	
		
		
	}

	@Override
	public List<ApOffre> findByCatégorie(String catégorie) {
		return null;
	}

	@Override
	public Page<ApOffre> findByCatégorie(String catégorie, Pageable pageable) {
		
		return appOffrerepository.findByCatégorie(catégorie, pageable);
	}

	@Override
	public Page<ApOffre> chercherAppOffre(String mc, String catégorie, int page,int size) {
		
		return appOffrerepository.chercherAppOffre(mc, catégorie, new PageRequest(page, size, new Sort(Direction.DESC, "ett")));
	}

	@Override
	public Page<Attribution> chercherAttribution(String mc, String catégorie, int page, int size) {
		
		return attributionrepository.chercherAttribution(mc, catégorie, new PageRequest(page, size));
	}

	@Override
	public Page<Annulation> chercherAnnulation(String mc, String catégorie, int page, int size) {
		
		return annulationrepository.chercherAnnulation(mc, catégorie, new PageRequest(page, size));
	}

	

}
