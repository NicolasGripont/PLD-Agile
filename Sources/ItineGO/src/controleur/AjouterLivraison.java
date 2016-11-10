package controleur;

import exceptions.NonRespectPlagesHoraires;
import modeles.Gestionnaire;
import modeles.Livraison;
import modeles.Noeud;

public class AjouterLivraison extends Commande {

	private Livraison livraison;
	private Gestionnaire gestionnaire;
	private Noeud noeudSuivant;
	private int numLigne;
	
	public AjouterLivraison(Gestionnaire gestionnaire, int numLigne) {
		this.gestionnaire = gestionnaire;
		this.numLigne = numLigne;
		livraison = gestionnaire.getLivraisonEnCoursCreation();
		noeudSuivant = gestionnaire.getNoeudTournee(numLigne);
	}
	
	
	@Override
	public void doCode() throws NonRespectPlagesHoraires {
		gestionnaire.setLivraisonEnCourCreation(livraison);
		gestionnaire.setNoeudSuivant(noeudSuivant);
		gestionnaire.setPositionLivraisonEnCours(numLigne);
		gestionnaire.ajouterLivraisonTournee();
	}

	@Override
	public void undoCode() throws NonRespectPlagesHoraires {
		livraison = gestionnaire.getLivraisonTournee(numLigne);
		noeudSuivant = gestionnaire.getNoeudTournee(numLigne+1);
		gestionnaire.supprimerLivraisonTournee(numLigne);
	}
}
