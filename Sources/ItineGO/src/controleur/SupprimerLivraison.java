package controleur;

import exceptions.NonRespectPlagesHoraires;
import modeles.Gestionnaire;
import modeles.Livraison;
import modeles.Noeud;

public class SupprimerLivraison extends Commande {

	private Livraison livraison;
	private final Gestionnaire gestionnaire;
	private Noeud noeudSuivant;
	private final int numLigne;

	public SupprimerLivraison(Gestionnaire gestionnaire, int numLigne) {
		this.numLigne = numLigne;
		this.gestionnaire = gestionnaire;
	}

	@Override
	public void doCode() throws NonRespectPlagesHoraires {
		this.livraison = this.gestionnaire.getLivraisonTournee(this.numLigne);
		this.noeudSuivant = this.gestionnaire.getNoeudTournee(this.numLigne + 1);
		this.gestionnaire.supprimerLivraisonTournee(this.numLigne);
	}

	@Override
	public void undoCode() throws NonRespectPlagesHoraires {
		this.gestionnaire.setLivraisonEnCourCreation(this.livraison);
		this.gestionnaire.setNoeudSuivant(this.noeudSuivant);
		this.gestionnaire.setPositionLivraisonEnCours(this.numLigne);
		this.gestionnaire.ajouterLivraisonTournee();
	}

}
