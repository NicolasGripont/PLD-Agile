package modeles;

import java.io.*;
import java.util.List;
import org.jdom2.*;
import org.jdom2.filter.*;
import org.jdom2.input.*;
import modeles.Noeud;



public class ParseurPlan{
	


	private static List listNoeudVille; //Liste des Noeuds
//	static List listTronconVille; //Liste des Troncons
	private List<Noeud> listNoeudPlanVille;
	
	 public static void main(String[] args)
	  {
		 parserPlanVille("C:/Users/Pierre/Desktop/INSA/3-4IF/1er Semestre/PLD Agile/PLD-Agile/Archives/xml/plan10x10.xml");
		 System.out.println("Fin Main");
	  }
	 
	 
	 /**
	 * @param nomFichier
	 */
	public static void parserPlanVille(String nomFichier)
	 {
		//Permet de parser le fichier XML
		 SAXBuilder sxb = new SAXBuilder();
		 File xmlFile = new File(nomFichier);
		 
		 
		 try
		 {
			 //Parse le fichier XML
			 Document planVille = (Document) sxb.build(xmlFile);
			 
			 //Attribut l'élément racine du fichier XML
			 Element racine = planVille.getRootElement();
			 
			 listNoeudVille = racine.getChildren("noeud");
//			 listTronconVille = racine.getChildren("troncon");
			 
			 //Afficher les attributs des noeuds
			 for (int i=0; i < listNoeudVille.size() ; i++)
			 {
//				 Element noeud = (Element) listNoeudVille.get(i);
				 
//				 Noeud unNoeud = new Noeud(Integer.parseInt(noeud.getAttributeValue("id")), Integer.parseInt(noeud.getAttributeValue("x")), Integer.parseInt(noeud.getAttributeValue("y")));
				 Noeud n1 = new Noeud(1,20,20);
				 n1.toString();
//				 listNoeudPlanVille.add(unNoeud);
//				 System.out.println("ID : " + Integer.parseInt(noeud.getAttributeValue("id"))+ " X : " + Integer.parseInt(noeud.getAttributeValue("x")) + " Y : " + Integer.parseInt(noeud.getAttributeValue("y")));
			 }
			 System.out.println("Ajout Noeud Fini");
			 
//			 for (int i = 0; i<listNoeudPlanVille.size();i++)
//			 {
//				 Noeud unNoeud = listNoeudPlanVille.get(i);
//				 unNoeud.toString();
//			 }
			 
			//Afficher les attributs des troncons
//			 for (int i=0; i < listTronconVille.size() ; i++)
//			 {
//				 Element noeud = (Element) listTronconVille.get(i);
//				 
//				 System.out.println("destination : " + noeud.getAttributeValue("destination")+ " longueur : " + noeud.getAttributeValue("longueur") + " nomRue : " + noeud.getAttributeValue("nomRue"));
//			 }
			 
		 }
		 catch(Exception e){System.out.println(e);}
	 }

}

