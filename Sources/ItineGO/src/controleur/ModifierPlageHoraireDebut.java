package controleur;

import exceptions.NonRespectPlagesHoraires;
import modeles.Gestionnaire;
import modeles.Livraison;

public class ModifierPlageHoraireDebut extends Commande {

	private Gestionnaire gestionnaire;
	private int numLigne;
	private String plageDebut;
	private String plageDebutInitiale;
	
	public ModifierPlageHoraireDebut(Gestionnaire gestionnaire, int numLigne, String plageDebut) {
		this.gestionnaire = gestionnaire;
		this.numLigne = numLigne;
		this.plageDebut = plageDebut;
	}
	
	@Override
	public void doCode() throws NonRespectPlagesHoraires {
		Livraison livraisonTournee = gestionnaire.getLivraisonTournee(numLigne);
		plageDebutInitiale = livraisonTournee.getDebutPlage().toString();
		gestionnaire.changerPlageHoraireDebut(numLigne, plageDebut);
	}

	@Override
	public void undoCode() throws NonRespectPlagesHoraires {
		gestionnaire.changerPlageHoraireDebut(numLigne, plageDebutInitiale);
	}

}
