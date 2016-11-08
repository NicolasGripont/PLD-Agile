package controleur;

import exceptions.NonRespectPlagesHoraires;
import modeles.Gestionnaire;
import modeles.Livraison;
import modeles.Noeud;

public class SupprimerLivraison extends Commande {

	private Livraison livraison;
	private Gestionnaire gestionnaire;
	private Noeud noeudSuivant;
	private int numLigne;
	
	public SupprimerLivraison(Gestionnaire gestionnaire, int numLigne) {
		this.numLigne = numLigne;
		this.gestionnaire = gestionnaire;
	}
	
	@Override
	public void doCode() throws NonRespectPlagesHoraires {
		livraison = gestionnaire.getLivraisonTournee(numLigne);
		noeudSuivant = gestionnaire.getNoeudTournee(numLigne+1);
		gestionnaire.supprimerLivraisonTournee(livraison);
	}

	@Override
	public void undoCode() throws NonRespectPlagesHoraires {
		gestionnaire.setLivraisonEnCourCreation(livraison);
		gestionnaire.setNoeudSuivant(noeudSuivant);
		gestionnaire.ajouterLivraisonTournee();
	}

}
