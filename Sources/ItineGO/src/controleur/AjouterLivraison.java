package controleur;

import exceptions.NonRespectPlagesHoraires;
import modeles.Gestionnaire;
import modeles.Livraison;
import modeles.Noeud;

public class AjouterLivraison extends Commande {

	private Livraison livraison;
	private final Gestionnaire gestionnaire;
	private Noeud noeudSuivant;
	private final int numLigne;

	public AjouterLivraison(Gestionnaire gestionnaire, int numLigne) {
		this.gestionnaire = gestionnaire;
		this.numLigne = numLigne;
		this.livraison = gestionnaire.getLivraisonEnCoursCreation();
		this.noeudSuivant = gestionnaire.getNoeudTournee(numLigne);
	}

	@Override
	public void doCode() throws NonRespectPlagesHoraires {
		this.gestionnaire.setLivraisonEnCourCreation(this.livraison);
		this.gestionnaire.setNoeudSuivant(this.noeudSuivant);
		this.gestionnaire.setPositionLivraisonEnCours(this.numLigne);
		this.gestionnaire.ajouterLivraisonTournee();
	}

	@Override
	public void undoCode() throws NonRespectPlagesHoraires {
		this.livraison = this.gestionnaire.getLivraisonTournee(this.numLigne);
		this.noeudSuivant = this.gestionnaire.getNoeudTournee(this.numLigne + 1);
		this.gestionnaire.supprimerLivraisonTournee(this.numLigne);
	}
}
