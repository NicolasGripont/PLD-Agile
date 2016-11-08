package controleur;

import modeles.Gestionnaire;
import modeles.Livraison;

public class ModifierPlageHoraireFin extends Commande {

	private Gestionnaire gestionnaire;
	private int numLigne;
	private String plageFin;
	private String plageFinInitiale;
	
	public ModifierPlageHoraireFin(Gestionnaire gestionnaire, int numLigne, String plageFin) {
		this.gestionnaire = gestionnaire;
		this.numLigne = numLigne;
		this.plageFin = plageFin;
	}
	
	@Override
	public void doCode() {
		Livraison livraisonTournee = gestionnaire.getLivraisonTournee(numLigne);
		plageFinInitiale = livraisonTournee.getFinPlage().toString();
		gestionnaire.changerPlageHoraireFin(numLigne, plageFin);
	}

	@Override
	public void undoCode() {
		gestionnaire.changerPlageHoraireFin(numLigne, plageFinInitiale);
	}

}
