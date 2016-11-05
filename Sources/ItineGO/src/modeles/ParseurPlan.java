package modeles;
import java.io.*;
import java.util.List;
import java.util.Map;
import java.util.Iterator;
import org.jdom2.*;
import org.jdom2.filter.*;
import org.jdom2.input.*;

import exceptions.BadXmlFile;
import exceptions.BadXmlPlan;


public class ParseurPlan {
	
	 public static void main(String[] args)
	   {
		 Plan plan = new Plan();
		 try {
			 parseurPlanVille("./tests/assetsForTests/plan5x5.xml", plan);
		 }
		 catch(Exception e){
			 System.err.println(e);
		 }
//		 System.out.println(plan.getNoeuds().size());
//		 System.out.println(plan.getTroncons().size());
//		 System.out.println(plan.getNoeud(1).toString());
	   }
	 
	 /**
	 * @param nomFichier
	 * @throws BadXmlFile 
	 * @throws BadXmlPlan 
	 */
	public static void parseurPlanVille(String nomFichier, Plan plan) throws BadXmlFile, BadXmlPlan
	 {
		//Permet de parser le fichier XML
		 SAXBuilder sxb = new SAXBuilder();
		 File xmlFile = new File(nomFichier);
		 List listNoeudVille; //Liste des Noeuds
		 List listTronconVille; //Liste des Troncons
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
			 listNoeudVille = racine.getChildren("noeud");
			 listTronconVille = racine.getChildren("troncon");
			 
			 if(listNoeudVille.size() == 0 || listTronconVille.size() == 0) {
				 throw new BadXmlPlan();
		 	 }
			 //Parse les noeuds
			 for (int i=0; i < listNoeudVille.size() ; i++)
			 {
				 Element noeud = (Element) listNoeudVille.get(i);
				 if(plan.idExiste(Integer.parseInt(noeud.getAttributeValue("id")))==false)
				 {
					 plan.ajouterNoeud(new Noeud(Integer.parseInt(noeud.getAttributeValue("id")), Integer.parseInt(noeud.getAttributeValue("x")), Integer.parseInt(noeud.getAttributeValue("y"))));
			 	 }
				 else
				 {
					 plan.effacerNoeuds();
					 throw new BadXmlPlan("Erreur : Deux identifiants de noeud identiques");
				 }
			 }
			//Parse les troncons
			 for (int i=0; i < listTronconVille.size() ; i++)
			 {
				 Element troncon = (Element) listTronconVille.get(i);
				 if(plan.getNoeud(Integer.parseInt(troncon.getAttributeValue("origine"))) != null && plan.getNoeud(Integer.parseInt(troncon.getAttributeValue("destination"))) != null) {
					 plan.ajouterTroncon(
							 new Troncon(
									 troncon.getAttributeValue("nomRue"),
									 Integer.parseInt(troncon.getAttributeValue("longueur")),
									 Integer.parseInt(troncon.getAttributeValue("vitesse")),
									 plan.getNoeud(Integer.parseInt(troncon.getAttributeValue("origine"))),
									 plan.getNoeud(Integer.parseInt(troncon.getAttributeValue("destination")))
							 )
					);
				 }
				 else {
					 plan.effacerNoeuds();
					 plan.effacerTroncons();
					 throw new BadXmlPlan();
				 }
			 }
		 
		 for(Map.Entry<Integer, Noeud> n : plan.getNoeuds().entrySet()) {
			if(n.getValue().getId() == -1) {
				plan.effacerNoeuds();
				plan.effacerTroncons();
				throw new BadXmlPlan();
			}
		}
	 }

}
