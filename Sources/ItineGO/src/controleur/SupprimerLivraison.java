package controleur;

import exceptions.NonRespectPlagesHoraires;
import modeles.Gestionnaire;
import modeles.Livraison;
import modeles.Noeud;

/**
 * Commande de suppression d'une livraison.
 */
public class SupprimerLivraison extends Commande {

	private Livraison livraison;
	private final Gestionnaire gestionnaire;
	private Noeud noeudSuivant;
	private final int numLigne;

	/**
	 * Constructeur de la commande.
	 * 
	 * @param gestionnaire
	 *            : Gestionnaire de l'application.
	 * @param numLigne
	 *            : Position de la livraison à supprimer.
	 */
	public SupprimerLivraison(Gestionnaire gestionnaire, int numLigne) {
		this.numLigne = numLigne;
		this.gestionnaire = gestionnaire;
	}

	/**
	 * Appel la méthode de suppression du modèle.
	 * On stocke la livraison ainsi que le noeud suivant cette livraison en cas de undo.
	 */
	@Override
	public void doCode() throws NonRespectPlagesHoraires {
		this.livraison = this.gestionnaire.getLivraisonTournee(this.numLigne);
		this.noeudSuivant = this.gestionnaire.getNoeudTournee(this.numLigne + 1);
		this.gestionnaire.supprimerLivraisonTournee(this.numLigne);
	}

	/**
	 * Appel la méthode d'ajout du modèle.
	 * On récupère la livraison stockée avant l'appel.
	 */
	@Override
	public void undoCode() throws NonRespectPlagesHoraires {
		this.gestionnaire.setLivraisonEnCourCreation(this.livraison);
		this.gestionnaire.setNoeudSuivant(this.noeudSuivant);
		this.gestionnaire.setPositionLivraisonEnCours(this.numLigne);
		this.gestionnaire.ajouterLivraisonTournee();
	}

}
