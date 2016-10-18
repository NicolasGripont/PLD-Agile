package controleur;

import java.io.File;

import modeles.Gestionnaire;

public class EtatPlanVilleChoisie extends EtatDefaut {

	public void clicBoutonValider(Controleur controleur, Gestionnaire gestionnaire, File fichierXML)
	{
		gestionnaire.chargerPlan(fichierXML);
		//controleur.setEtatCourant(controleur.);
	}
}
