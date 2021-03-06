package modeles;

import java.io.File;
import java.util.List;
import java.util.Map;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.input.SAXBuilder;

import exceptions.BadXmlFile;
import exceptions.BadXmlPlan;

public class ParseurPlan {

	/**
	 * Parse le fichier xml. Cela ajoute au plan passé en paramètre les noeuds
	 * et tronçons
	 * 
	 * @param nomFichier
	 *            Nom du fichier xml à utiliser
	 * @param plan
	 *            Le plan de la tournée
	 * @throws BadXmlPlan
	 *             Si le fichier plan a des incohérences
	 * @throws BadXmlFile
	 *             Si le fichier est malformé
	 */
	@SuppressWarnings("rawtypes")
	public static void parseurPlanVille(String nomFichier, Plan plan) throws BadXmlFile, BadXmlPlan {
		// Permet de parser le fichier XML
		SAXBuilder sxb = new SAXBuilder();
		File xmlFile = new File(nomFichier);
		List listNoeudVille; // Liste des Noeuds
		List listTronconVille; // Liste des Troncons
		Document planVille;

		try {
			// Parse le fichier XML
			planVille = sxb.build(xmlFile);

		} catch (Exception e) {
			throw new BadXmlFile();
		}
		// Attribut l'element racine du fichier XML
		Element racine = planVille.getRootElement();
		listNoeudVille = racine.getChildren("noeud");
		listTronconVille = racine.getChildren("troncon");

		if ((listNoeudVille.size() == 0) || (listTronconVille.size() == 0)) {
			throw new BadXmlPlan();
		}
		// Parse les noeuds
		for (int i = 0; i < listNoeudVille.size(); i++) {
			Element noeud = (Element) listNoeudVille.get(i);
			if (plan.idExiste(Integer.parseInt(noeud.getAttributeValue("id"))) == true) {
				plan.effacerNoeuds();
				throw new BadXmlPlan("Erreur : Deux identifiants de noeud identiques");
			} else if ((Integer.parseInt(noeud.getAttributeValue("x")) < 0)
					|| (Integer.parseInt(noeud.getAttributeValue("y")) < 0)) {
				plan.effacerNoeuds();
				throw new BadXmlPlan();
			} else {
				plan.ajouterNoeud(new Noeud(Integer.parseInt(noeud.getAttributeValue("id")),
						Integer.parseInt(noeud.getAttributeValue("x")),
						Integer.parseInt(noeud.getAttributeValue("y"))));
			}
		}
		// Parse les troncons
		for (int i = 0; i < listTronconVille.size(); i++) {
			Element troncon = (Element) listTronconVille.get(i);
			if ((plan.getNoeud(Integer.parseInt(troncon.getAttributeValue("origine"))) != null)
					&& (plan.getNoeud(Integer.parseInt(troncon.getAttributeValue("destination"))) != null)
					&& (Integer.parseInt(troncon.getAttributeValue("longueur")) >= 0)
					&& (Integer.parseInt(troncon.getAttributeValue("vitesse")) >= 0)) {
				plan.ajouterTroncon(new Troncon(troncon.getAttributeValue("nomRue"),
						Integer.parseInt(troncon.getAttributeValue("longueur")),
						Integer.parseInt(troncon.getAttributeValue("vitesse")),
						plan.getNoeud(Integer.parseInt(troncon.getAttributeValue("origine"))),
						plan.getNoeud(Integer.parseInt(troncon.getAttributeValue("destination")))));
			} else {
				plan.effacerNoeuds();
				plan.effacerTroncons();
				throw new BadXmlPlan();
			}
		}

		for (Map.Entry<Integer, Noeud> n : plan.getNoeuds().entrySet()) {
			if (n.getValue().getId() < 0) {
				plan.effacerNoeuds();
				plan.effacerTroncons();
				throw new BadXmlPlan();
			}
		}
	}

}
