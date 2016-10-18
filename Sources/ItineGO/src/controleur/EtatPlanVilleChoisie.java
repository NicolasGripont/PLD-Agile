package controleur;

import java.io.File;

import modeles.Gestionnaire;

public class EtatPlanVilleChoisie extends EtatDefault {

	public void clicBoutonValider(Controleur controleur, Gestionnaire gestionnaire, File fichierXML)
	{
		gestionnaire.chargerPlan(fichierXML);
		//TODO : controleur.setetat
	}
}
