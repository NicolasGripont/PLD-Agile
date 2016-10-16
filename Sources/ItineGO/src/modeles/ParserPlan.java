package modeles;
import java.io.*;
import java.util.List;
import java.util.Iterator;
import org.jdom2.*;
import org.jdom2.filter.*;
import org.jdom2.input.*;


public class ParserPlan {
	
	 public static void main(String[] args)
	   {
		 Plan plan = new Plan();
		 parserPlanVille("./xml/plan10x10.xml", plan);
		 System.out.println(plan.getNoeuds().size());
		 System.out.println(plan.getTroncons().size());
		 System.out.println(plan.getNoeud(1).toString());
	   }
	 
	 /**
	 * @param nomFichier
	 */
	public static void parserPlanVille(String nomFichier, Plan plan)
	 {
		//Permet de parser le fichier XML
		 SAXBuilder sxb = new SAXBuilder();
		 File xmlFile = new File(nomFichier);
		 List listNoeudVille; //Liste des Noeuds
		 List listTronconVille; //Liste des Troncons
		 try
		 {
			 //Parse le fichier XML
			 Document planVille = (Document) sxb.build(xmlFile);
			 
			 //Attribut l'élément racine du fichier XML
			 Element racine = planVille.getRootElement();
			 listNoeudVille = racine.getChildren("noeud");
			 listTronconVille = racine.getChildren("troncon");
			 
			 //Parse les noeuds
			 for (int i=0; i < listNoeudVille.size() ; i++)
			 {
				 Element noeud = (Element) listNoeudVille.get(i);
				 plan.AjouterNoeud(new Noeud(Integer.parseInt(noeud.getAttributeValue("id")), Integer.parseInt(noeud.getAttributeValue("x")), Integer.parseInt(noeud.getAttributeValue("y"))));
				 //System.out.println("ID : " + noeud.getAttributeValue("id")+ " X : " + noeud.getAttributeValue("x") + " Y : " + noeud.getAttributeValue("y"));
			 }
			 
			//Parse les troncons
			 for (int i=0; i < listTronconVille.size() ; i++)
			 {
				 Element troncon = (Element) listTronconVille.get(i);
				 if(plan.getNoeud(Integer.parseInt(troncon.getAttributeValue("origine"))) != null && plan.getNoeud(Integer.parseInt(troncon.getAttributeValue("destination"))) != null) {
					 plan.AjouterTroncon(
							 new Troncon(
									 troncon.getAttributeValue("nomRue"),
									 Integer.parseInt(troncon.getAttributeValue("longueur")),
									 Integer.parseInt(troncon.getAttributeValue("vitesse")),
									 plan.getNoeud(Integer.parseInt(troncon.getAttributeValue("origine"))),
									 plan.getNoeud(Integer.parseInt(troncon.getAttributeValue("destination")))
							 )
					);
					//System.out.println("destination : " + troncon.getAttributeValue("destination")+ " longueur : " + troncon.getAttributeValue("longueur") + " nomRue : " + troncon.getAttributeValue("nomRue"));
				 }
				 else {
					 /*
					  * MESSAGE ERREUR A CREER
					  */
				 }
			 }
		 }
		 catch(Exception e){System.out.println(e);}
	 }

}
