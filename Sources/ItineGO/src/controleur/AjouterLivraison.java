package controleur;

import exceptions.NonRespectPlagesHoraires;
import modeles.Gestionnaire;

public class AjouterLivraison extends Commande {

	private Gestionnaire gestionnaire;
	private int numLigne;
	
	public AjouterLivraison(Gestionnaire gestionnaire, int numLigne) {
		this.gestionnaire = gestionnaire;
		this.numLigne = numLigne;
	}
	
	
	@Override
	public void doCode() throws NonRespectPlagesHoraires {
		gestionnaire.ajouterLivraisonTournee();
	}

	@Override
	public void undoCode() throws NonRespectPlagesHoraires {
		gestionnaire.supprimerLivraisonTournee(numLigne);
	}

}
