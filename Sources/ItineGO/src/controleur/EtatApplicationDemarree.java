package controleur;

import java.io.File;

/**
 * Etat au démarrage de l'application
 */
public class EtatApplicationDemarree extends EtatDefaut {
	
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
