package controleur;

import modeles.Gestionnaire;
/**
 * Commande correspondant à la modification de l'ordre de la livraison.
 */
public class ModifierOrdre extends Commande {

	private final Gestionnaire gestionnaire;
	private final int positionInitiale;
	private final int positionFinale;

	/**
	 * Constructeur de ModifierOrdre.
	 * 
	 * @param gestionnaire
	 *            : Gestionnaire de l'application.
	 * @param positionInitiale
	 *            : Position initiale dans le tableau.
	 * @param positionFinale
	 *            : Nouvelle position voulue.
	 */
	public ModifierOrdre(Gestionnaire gestionnaire, int positionInitiale, int positionFinale) {
		this.gestionnaire = gestionnaire;
		this.positionInitiale = positionInitiale;
		this.positionFinale = positionFinale;
	}

	/**
	 * On va réordonner la tournée en donnant les bonnes positions.
	 * Si on change l'ordre de la dernière livraison on modifie la positionInitiale, 
	 * car la vue renvoie un index qui ne correspond pas à l'index dans la liste.
	 */
	@Override
	public void doCode() {
		if (this.positionInitiale == this.gestionnaire.getPlan().getLivraisons().size()) {
			this.gestionnaire.reordonnerLivraisonTournee(this.positionInitiale - 1, this.positionFinale);
		} else {
			this.gestionnaire.reordonnerLivraisonTournee(this.positionInitiale, this.positionFinale);
		}
	}

	/**
	 * On va réordonner la tournée en changeant la livraison en la mettant à sa place initiale.
	 */
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
