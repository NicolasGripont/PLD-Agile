package controleur;

import modeles.Gestionnaire;

public class ModifierOrdre extends Commande {

	private final Gestionnaire gestionnaire;
	private final int positionInitiale;
	private final int positionFinale;

	public ModifierOrdre(Gestionnaire gestionnaire, int positionInitiale, int positionFinale) {
		this.gestionnaire = gestionnaire;
		this.positionInitiale = positionInitiale;
		this.positionFinale = positionFinale;
	}

	@Override
	public void doCode() {
		if (this.positionInitiale == this.gestionnaire.getPlan().getLivraisons().size()) {
			this.gestionnaire.reordonnerLivraisonTournee(this.positionInitiale - 1, this.positionFinale);
		} else {
			this.gestionnaire.reordonnerLivraisonTournee(this.positionInitiale, this.positionFinale);
		}
	}

	@Override
	public void undoCode() {
		if (this.positionInitiale == (this.gestionnaire.getPlan().getLivraisons().size() - 1)) {
			if (this.positionFinale == 0) {
				this.gestionnaire.reordonnerLivraisonTournee(0, this.positionInitiale + 1);
			} else {
				this.gestionnaire.reordonnerLivraisonTournee(this.positionFinale, this.positionInitiale + 1);
			}
		} else {
			if (this.positionFinale == 0) {
				this.gestionnaire.reordonnerLivraisonTournee(0, this.positionInitiale + 1);
			} else {
				if (this.positionInitiale > this.positionFinale) {
					this.gestionnaire.reordonnerLivraisonTournee(this.positionFinale, this.positionInitiale + 1);
				} else {
					this.gestionnaire.reordonnerLivraisonTournee(this.positionFinale - 1, this.positionInitiale);
				}
			}
		}
	}

}
