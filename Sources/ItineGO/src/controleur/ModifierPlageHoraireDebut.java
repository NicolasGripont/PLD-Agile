package controleur;

import exceptions.NonRespectPlagesHoraires;
import modeles.Gestionnaire;
import modeles.Livraison;

public class ModifierPlageHoraireDebut extends Commande {

	private final Gestionnaire gestionnaire;
	private final int numLigne;
	private final String plageDebut;
	private final String plageDebutInitiale;

	public ModifierPlageHoraireDebut(Gestionnaire gestionnaire, int numLigne, String plageDebut) {
		this.gestionnaire = gestionnaire;
		this.numLigne = numLigne;
		this.plageDebut = plageDebut;
		Livraison livraisonTournee = gestionnaire.getLivraisonTournee(numLigne);
		this.plageDebutInitiale = livraisonTournee.getDebutPlage().toString();
	}

	@Override
	public void doCode() throws NonRespectPlagesHoraires {
		this.gestionnaire.changerPlageHoraireDebut(this.numLigne, this.plageDebut);
	}

	@Override
	public void undoCode() throws NonRespectPlagesHoraires {
		this.gestionnaire.changerPlageHoraireDebut(this.numLigne, this.plageDebutInitiale);
	}

}