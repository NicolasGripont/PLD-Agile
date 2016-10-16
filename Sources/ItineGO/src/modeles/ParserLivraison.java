package modeles;
import java.io.*;
import java.util.List;
import java.util.Iterator;
import org.jdom2.*;
import org.jdom2.filter.*;
import org.jdom2.input.*;


public class ParserLivraison {
	
	 public static void main(String[] args)
	   {
		 Plan plan = new Plan();
		 ParserPlan.parserPlanVille("./xml/plan5x5.xml", plan);
		 parserLivraisonVille("./xml/livraisons5x5-9.xml", plan);
		 System.out.println(plan.getLivraisons().size());
		 System.out.println(plan.getEntrepot().toString());
	   }
	 
	 /**
	 * @param nomFichier
	 */
	public static void parserLivraisonVille(String nomFichier, Plan plan)
	 {
		//Permet de parser le fichier XML
		 SAXBuilder sxb = new SAXBuilder();
		 File xmlFile = new File(nomFichier);
		 List listLivraisonVille; //Liste des Noeuds
		 List listEntrepotVille; //Liste des Troncons
		 try
		 {
			 //Parse le fichier XML
			 Document planVille = (Document) sxb.build(xmlFile);
			 
			 //Attribut l'élément racine du fichier XML
			 Element racine = planVille.getRootElement();
			 listLivraisonVille = racine.getChildren("livraison");
			 listEntrepotVille = racine.getChildren("entrepot");

			 //Parse les noeuds
			 for (int i=0; i < listLivraisonVille.size() ; i++)
			 {
				 Element livraison = (Element) listLivraisonVille.get(i);
				 plan.AjouterLivraison(new Livraison(
						 plan.getNoeud(Integer.parseInt(livraison.getAttributeValue("adresse"))),
						 Integer.parseInt(livraison.getAttributeValue("duree"))
				 ));
			 }
			 
			 Element entrepot = (Element) listEntrepotVille.get(0);
			 plan.AjouterEntrepot(new Entrepot(
					 plan.getNoeud(Integer.parseInt(entrepot.getAttributeValue("adresse"))),
					 entrepot.getAttributeValue("heureDepart")
			 ));
		 }
		 catch(Exception e){e.printStackTrace();}
	 }

}
