package Main;
import java.io.*;
import java.util.List;
import java.util.Iterator;
import org.jdom2.*;
import org.jdom2.filter.*;
import org.jdom2.input.*;


public class Parser {
	
	static List listNoeudVille; //Liste des Noeuds
	static List listTronconVille; //Liste des Troncons
	
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
			 listTronconVille = racine.getChildren("troncon");
			 
			 //Afficher les attributs des noeuds
			 for (int i=0; i < listNoeudVille.size() ; i++)
			 {
				 Element noeud = (Element) listNoeudVille.get(i);
				 
				 System.out.println("ID : " + noeud.getAttributeValue("id")+ " X : " + noeud.getAttributeValue("x") + " Y : " + noeud.getAttributeValue("y"));
			 }
			 
			//Afficher les attributs des troncons
			 for (int i=0; i < listTronconVille.size() ; i++)
			 {
				 Element noeud = (Element) listTronconVille.get(i);
				 
				 System.out.println("destination : " + noeud.getAttributeValue("destination")+ " longueur : " + noeud.getAttributeValue("longueur") + " nomRue : " + noeud.getAttributeValue("nomRue"));
			 }
			 
		 }
		 catch(Exception e){System.out.println(e);}
	 }

}
