package modeles;

import java.io.File;
import java.util.List;
import java.util.Map;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.input.SAXBuilder;

import exceptions.BadXmlFile;
import exceptions.BadXmlLivraison;

/**
 * Classe permettant de parser un fichier xml de livraison
 */
public class ParseurLivraison {

	/**
	 * Parse le fichier xml. Cela ajoute au plan passé en paramètre l'entrepot
	 * et les livraisons
	 * 
	 * @param nomFichier
	 *            Nom du fichier xml à utiliser
	 * @param plan
	 *            Le plan de la tournée
	 * @throws BadXmlLivraison
	 *             Si le fichier livraison a des incohérences
	 * @throws BadXmlFile
	 *             Si le fichier est malformé
	 */
	@SuppressWarnings("rawtypes")
	public static void parseurLivraisonVille(String nomFichier, Plan plan) throws BadXmlLivraison, BadXmlFile {
		// Permet de parser le fichier XML
		SAXBuilder sxb = new SAXBuilder();
		File xmlFile = new File(nomFichier);
		List listLivraisonVille; // Liste des Noeuds
		List listEntrepotVille; // Liste des Troncons
		Document planVille;

		try {
			// Parse le fichier XML
			planVille = sxb.build(xmlFile);
		} catch (Exception e) {
			throw new BadXmlFile();
		}

		// Attribut l'element racine du fichier XML
		Element racine = planVille.getRootElement();
		listLivraisonVille = racine.getChildren("livraison");
		listEntrepotVille = racine.getChildren("entrepot");

		if ((listEntrepotVille.size() == 0) || (listLivraisonVille.size() == 0)) {
			throw new BadXmlLivraison();
		}
		// Parse les noeuds
		for (int i = 0; i < listLivraisonVille.size(); i++) {
			Element livraison = (Element) listLivraisonVille.get(i);
			Horaire plageDebut = null;
			Horaire plageFin = null;
			if (livraison.getAttributeValue("debutPlage") != null) {
				plageDebut = new Horaire(livraison.getAttributeValue("debutPlage"));
			}
			if (livraison.getAttributeValue("finPlage") != null) {
				plageFin = new Horaire(livraison.getAttributeValue("finPlage"));
			}
			if (plan.idExiste(Integer.parseInt(livraison.getAttributeValue("adresse"))) == false) {
				plan.effacerEntrepot();
				plan.effacerLivraisons();
				throw new BadXmlLivraison();
			}

			plan.ajouterLivraison(new Livraison(plan.getNoeud(Integer.parseInt(livraison.getAttributeValue("adresse"))),
					Integer.parseInt(livraison.getAttributeValue("duree")), plageDebut, plageFin));
		}

		Element entrepot = (Element) listEntrepotVille.get(0);
		plan.ajouterEntrepot(new Entrepot(plan.getNoeud(Integer.parseInt(entrepot.getAttributeValue("adresse"))),
				entrepot.getAttributeValue("heureDepart")));

		if (plan.getEntrepot().getNoeud().getId() == -1) {
			plan.effacerEntrepot();
			plan.effacerLivraisons();
			throw new BadXmlLivraison();
		}
		for (Map.Entry<Integer, Livraison> l : plan.getLivraisons().entrySet()) {
			if (l.getValue().getNoeud().getId() == -1) {
				plan.effacerEntrepot();
				plan.effacerLivraisons();
				throw new BadXmlLivraison();
			}
		}
	}

}
