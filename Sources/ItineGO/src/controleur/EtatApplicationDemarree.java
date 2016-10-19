package controleur;

import java.io.File;

public class EtatApplicationDemarree extends EtatDefaut {
	
	public void glisserDeposer(Controleur controleur, boolean accepte, File fichier)
	{
		if(accepte) {
			controleur.choixPlanVilleVue.fichierAccepte(fichier);
			//changement d'�tat
			controleur.setEtatCourant(controleur.etatPlanVilleChoisie);
		} else {
			controleur.choixPlanVilleVue.fichierRefuse();
		}
	}
	
	public void clicBoutonParcourir(Controleur controleur, boolean accepte, File fichier)
	{
		if(accepte) {
			controleur.choixPlanVilleVue.fichierAccepte(fichier);
			//changement d'�tat
			controleur.setEtatCourant(controleur.etatPlanVilleChoisie);
		} else {
			controleur.choixPlanVilleVue.fichierRefuse();
		}
	}

}
