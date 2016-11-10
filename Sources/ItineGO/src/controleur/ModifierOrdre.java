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
		if(positionInitiale == gestionnaire.getPlan().getLivraisons().size()) {
			gestionnaire.reordonnerLivraisonTournee(positionInitiale-1, positionFinale);
		} else {
			gestionnaire.reordonnerLivraisonTournee(positionInitiale, positionFinale);
		}
	}

	@Override
	public void undoCode() {
		if(positionInitiale == gestionnaire.getPlan().getLivraisons().size()-1) {
			if(positionFinale == 0) {
				gestionnaire.reordonnerLivraisonTournee(0, positionInitiale+1);
			} else {
				gestionnaire.reordonnerLivraisonTournee(positionFinale, positionInitiale+1);
			}
		}
		else { 
			if(positionFinale == 0) {
				gestionnaire.reordonnerLivraisonTournee(0, positionInitiale+1);
			} else {
				if(positionInitiale>positionFinale) {
					gestionnaire.reordonnerLivraisonTournee(positionFinale, positionInitiale+1);
				}
				else {
					gestionnaire.reordonnerLivraisonTournee(positionFinale-1, positionInitiale);
				}
			}
		}
	}

}
