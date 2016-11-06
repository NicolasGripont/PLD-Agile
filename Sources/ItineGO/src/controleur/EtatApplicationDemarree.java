package controleur;

import java.io.File;

/**
 * Etat au démarrage de l'application
 */
public class EtatApplicationDemarree extends EtatDefaut {

	
	/**
	 * Permet de faire un glisser deposer d'un fichier, si le fichier est accepté on passe a l'état EtatFichierPlanVilleChoisi sinon on reste en l'état. 
	 * On va indiquer à la vue si le fichier est accepté ou non et celle-ci affichera un message correspondant.
	 * 
	 * @param controleur : Controleur de l'application.
	 * @param accepte : Indique si le fichier a été accepté ou non.
	 * @param fichier : Fichier pris en compte.
	 */
	public void glisserDeposer(Controleur controleur, boolean accepte, File fichier)
	{
		if(accepte) {
			controleur.choixPlanVilleVue.fichierAccepte(fichier);
			//changement d'état
			controleur.setEtatCourant(controleur.etatPlanVilleChoisie);
		} else {
			controleur.choixPlanVilleVue.fichierRefuse();
		}
	}
	
	/**
	 * Permet choisir un fichier, si le fichier est accepté on passe a l'état EtatFichierPlanVilleChoisi sinon on reste en l'état. 
	 * On va indiquer à la vue si le fichier est accepté ou non et celle-ci affichera un message correspondant.
	 * 
	 * @param controleur : Controleur de l'application.
	 * @param accepte : Indique si le fichier a été accepté ou non.
	 * @param fichier : Fichier pris en compte.
	 */
	public void clicBoutonParcourir(Controleur controleur, boolean accepte, File fichier)
	{
		if(accepte) {
			controleur.choixPlanVilleVue.fichierAccepte(fichier);
			//changement d'état
			controleur.setEtatCourant(controleur.etatPlanVilleChoisie);
		} else {
			controleur.choixPlanVilleVue.fichierRefuse();
		}
	}
	
	public void getEtat()
	{
		System.out.println("etat Application démarrée");
	}

}
