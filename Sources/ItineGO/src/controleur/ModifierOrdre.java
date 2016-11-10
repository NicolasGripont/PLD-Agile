package controleur;

import modeles.Gestionnaire;

public class ModifierOrdre extends Commande {

	private Gestionnaire gestionnaire;
	private int positionInitiale;
	private int positionFinale;
	
	public ModifierOrdre(Gestionnaire gestionnaire, int positionInitiale, int positionFinale) {
		this.gestionnaire = gestionnaire;
		this.positionInitiale = positionInitiale;
		this.positionFinale = positionFinale;
	}
	
	@Override
	public void doCode() {
		gestionnaire.reordonnerLivraisonTournee(positionInitiale, positionFinale);
	}

	@Override
	public void undoCode() {
		gestionnaire.reordonnerLivraisonTournee(positionFinale-1, positionInitiale);
	}

}
