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
		Livraison livraisonTournee = gestionnaire.getLivraisonTournee(numLigne);
		this.plageDebutInitiale = livraisonTournee.getDebutPlage().toString();
	}
	
	@Override
	public void doCode() throws NonRespectPlagesHoraires {
		gestionnaire.changerPlageHoraireDebut(numLigne, plageDebut);
	}

	@Override
	public void undoCode() throws NonRespectPlagesHoraires {
		gestionnaire.changerPlageHoraireDebut(numLigne, plageDebutInitiale);
	}

}