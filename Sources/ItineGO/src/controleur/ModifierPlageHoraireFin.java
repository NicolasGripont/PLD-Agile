package controleur;

import exceptions.NonRespectPlagesHoraires;
import modeles.Gestionnaire;
import modeles.Livraison;

public class ModifierPlageHoraireFin extends Commande {

	private final Gestionnaire gestionnaire;
	private final int numLigne;
	private final String plageFin;
	private final String plageFinInitiale;

	public ModifierPlageHoraireFin(Gestionnaire gestionnaire, int numLigne, String plageFin) {
		this.gestionnaire = gestionnaire;
		this.numLigne = numLigne;
		this.plageFin = plageFin;
		Livraison livraisonTournee = gestionnaire.getLivraisonTournee(numLigne);
		this.plageFinInitiale = livraisonTournee.getFinPlage().toString();
	}

	@Override
	public void doCode() throws NonRespectPlagesHoraires {
		this.gestionnaire.changerPlageHoraireFin(this.numLigne, this.plageFin);
	}

	@Override
	public void undoCode() throws NonRespectPlagesHoraires {
		this.gestionnaire.changerPlageHoraireFin(this.numLigne, this.plageFinInitiale);
	}

}
