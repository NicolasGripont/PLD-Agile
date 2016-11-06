package controleur;

import java.io.File;

import modeles.Gestionnaire;
import modeles.Livraison;
import modeles.Noeud;

/**
 * Classe abstraite présentant toutes les méthodes que les différents états devront implémenter
 */
public abstract class EtatDefaut {
	
	public void undo() {}
	public void redo() {}
	
	public void clicBoutonParcourir				(Controleur controleur, boolean accepte, File fichier) {}
	public void clicBoutonValider				(Gestionnaire gestionnaire, Controleur controleur, File fichierXML) {}
	public void clicBoutonHome					(Controleur controleur, Gestionnaire gestionnaire) {}
	public void clicBoutonRetour				(Controleur controleur, Gestionnaire gestionnaire) {}
	public void clicBoutonCalculerTournee		(Controleur controleur, Gestionnaire gestionnaire) {}
	public void clicBoutonGenererFeuilleDeRoute (Controleur controleur, Gestionnaire gestionnaire, String link) {}
	public void clicBoutonStopperTournee		(Controleur controleur, Gestionnaire gestionnaire) {}
	public void clicBoutonSauvegarder			(Controleur controleur, Gestionnaire gestionnaire) {}
	public void clicBoutonAnnuler				(Controleur controleur, Gestionnaire gestionnaire) {}
	public void clicBoutonSupprimer				(Controleur controleur, Gestionnaire gestionnaire, int numLigne) {}
	public void clicBoutonAjouter				(Controleur controleur, Gestionnaire gestionnaire) {}
	public void clicPlanNoeud					(Controleur controleur, Gestionnaire gestionnaire, Noeud noeud) {}
	public void clicPlanLivraison				(Controleur controleur, Gestionnaire gestionnaire, Livraison livraison) {}
	
	public void glisserDeposer	(Controleur controleur, boolean accepte, File fichier) {}
	public void redessinerPlan	(Controleur controleur, Gestionnaire gestionnaire) {}
	public void afficherTournee	(Controleur controleur, Gestionnaire gestionnaire, boolean solutionOptimale) {}
	public void modifierLigne	(Controleur controleur, Gestionnaire gestionnaire, int numLigne, int nouveauNumLigne, String plageDebut, String plageFin) {}
	
	public void getEtat()
	{
		System.out.println("etat defaut");
	}
}
