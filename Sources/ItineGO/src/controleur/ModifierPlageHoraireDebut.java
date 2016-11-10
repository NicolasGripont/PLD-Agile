package controleur;

import exceptions.NonRespectPlagesHoraires;
import modeles.Gestionnaire;
import modeles.Livraison;

/**
 * Commande de modification de plage horaire de début.
 */
public class ModifierPlageHoraireDebut extends Commande {

	private final Gestionnaire gestionnaire;
	private final int numLigne;
	private final String plageDebut;
	private final String plageDebutInitiale;

	/**
	 * Constructeur de la commande.
	 * 
	 * @param gestionnaire
	 *            : Gestionnaire de l'application.
	 * @param numLigne
	 *            : Position de la livraison à modifier.
	 * @param plageDebut
	 *            : Nouvelle plage de début.
	 */
	public ModifierPlageHoraireDebut(Gestionnaire gestionnaire, int numLigne, String plageDebut) {
		this.gestionnaire = gestionnaire;
		this.numLigne = numLigne;
		this.plageDebut = plageDebut;
		Livraison livraisonTournee = gestionnaire.getLivraisonTournee(numLigne);
		this.plageDebutInitiale = livraisonTournee.getDebutPlage().toString();
	}

	/**
	 * Appel à la méthode de changement de plage horaire de début du modèle avec la nouvelle plage.
	 */
	@Override
	public void doCode() throws NonRespectPlagesHoraires {
		this.gestionnaire.changerPlageHoraireDebut(this.numLigne, this.plageDebut);
	}

	/**
	 * Appel à la méthode de changement de plage horaire de début du modèle avec l'ancienne plage.
	 */
	@Override
	public void undoCode() throws NonRespectPlagesHoraires {
		this.gestionnaire.changerPlageHoraireDebut(this.numLigne, this.plageDebutInitiale);
	}

}