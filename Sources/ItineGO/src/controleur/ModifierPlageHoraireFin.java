package controleur;

import exceptions.NonRespectPlagesHoraires;
import modeles.Gestionnaire;
import modeles.Livraison;

/**
 * Commande de modification de plage horaire de fin.
 */
public class ModifierPlageHoraireFin extends Commande {

	private final Gestionnaire gestionnaire;
	private final int numLigne;
	private final String plageFin;
	private final String plageFinInitiale;

	/**
	 * Constructeur de la commande.
	 * 
	 * @param gestionnaire
	 *            : Gestionnaire de l'application.
	 * @param numLigne
	 *            : Position de la livraison à modifier.
	 * @param plageFin
	 *            : Nouvelle plage de fin.
	 */
	public ModifierPlageHoraireFin(Gestionnaire gestionnaire, int numLigne, String plageFin) {
		this.gestionnaire = gestionnaire;
		this.numLigne = numLigne;
		this.plageFin = plageFin;
		Livraison livraisonTournee = gestionnaire.getLivraisonTournee(numLigne);
		this.plageFinInitiale = livraisonTournee.getFinPlage().toString();
	}

	/**
	 * Appel à la méthode de changement de plage horaire de fin du modèle avec la nouvelle plage.
	 */
	@Override
	public void doCode() throws NonRespectPlagesHoraires {
		this.gestionnaire.changerPlageHoraireFin(this.numLigne, this.plageFin);
	}

	/**
	 * Appel à la méthode de changement de plage horaire de fin du modèle avec l'ancienne plage.
	 */
	@Override
	public void undoCode() throws NonRespectPlagesHoraires {
		this.gestionnaire.changerPlageHoraireFin(this.numLigne, this.plageFinInitiale);
	}

}
