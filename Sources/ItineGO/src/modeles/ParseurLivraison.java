package modeles;
import java.io.*;
import java.util.List;
import java.util.Map;
import java.util.Iterator;
import org.jdom2.*;
import org.jdom2.filter.*;
import org.jdom2.input.*;

import exceptions.BadXmlFile;
import exceptions.BadXmlLivraison;
import exceptions.BadXmlPlan;


public class ParseurLivraison {
	
	 public static void main(String[] args)
	   {
		 Plan plan = new Plan();
		 try {
			ParseurPlan.parseurPlanVille("./xml/plan5x5.xml", plan);
			parseurLivraisonVille("./xml/livraisons5x5-9.xml", plan);
		} catch (Exception e) {
			e.printStackTrace();
		}
		 System.out.println(plan.getLivraisons().size());
		 System.out.println(plan.getEntrepot().toString());
	   }
	 
	 /**
	 * @param nomFichier
	 * @throws BadXmlLivraison 
	 * @throws BadXmlFile 
	 */
	public static void parseurLivraisonVille(String nomFichier, Plan plan) throws BadXmlLivraison, BadXmlFile
	 {
		//Permet de parser le fichier XML
		 SAXBuilder sxb = new SAXBuilder();
		 File xmlFile = new File(nomFichier);
		 List listLivraisonVille; //Liste des Noeuds
		 List listEntrepotVille; //Liste des Troncons
		 Document planVille;
		 
		 try
		 {
			 //Parse le fichier XML
			 planVille = (Document) sxb.build(xmlFile);
		 }
		 catch(Exception e){
			 throw new BadXmlFile();
		 }
			 
			
			 //Attribut l'�l�ment racine du fichier XML
			 Element racine = planVille.getRootElement();
			 listLivraisonVille = racine.getChildren("livraison");
			 listEntrepotVille = racine.getChildren("entrepot");

			 if(listEntrepotVille.size() == 0 || listLivraisonVille.size() == 0) {
				 throw new BadXmlLivraison();
		 	 }
			 //Parse les noeuds
			 for (int i=0; i < listLivraisonVille.size() ; i++)
			 {
				 Element livraison = (Element) listLivraisonVille.get(i);
				 Horaire plageDebut = null;
				 Horaire plageFin = null;
				 if(livraison.getAttributeValue("debutPlage") != null) {
					 plageDebut = new Horaire(livraison.getAttributeValue("debutPlage"));
				 }
				 if(livraison.getAttributeValue("finPlage") != null) {
					 plageFin = new Horaire(livraison.getAttributeValue("finPlage"));
				 }
				 System.err.println(plageDebut==null?"NULL":plageDebut.toString());
				 plan.ajouterLivraison(new Livraison(
						 plan.getNoeud(Integer.parseInt(livraison.getAttributeValue("adresse"))),
						 Integer.parseInt(livraison.getAttributeValue("duree")),
						 plageDebut,
						 plageFin
				 ));
			 }
			 
			 Element entrepot = (Element) listEntrepotVille.get(0);
			 plan.ajouterEntrepot(new Entrepot(
					 plan.getNoeud(Integer.parseInt(entrepot.getAttributeValue("adresse"))),
					 entrepot.getAttributeValue("heureDepart")
			 ));
		 
		 if(plan.getEntrepot().getNoeud().getId() == -1) {
				plan.effacerEntrepot();
				plan.effacerLivraisons();
				throw new BadXmlLivraison();
		 }
		 for(Map.Entry<Noeud, Livraison> l : plan.getLivraisons().entrySet()) {
				if(l.getValue().getNoeud().getId() == -1) {
					plan.effacerEntrepot();
					plan.effacerLivraisons();
					throw new BadXmlLivraison();
			}
		}
	 }

}
