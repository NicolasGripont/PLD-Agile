package controleur;

import java.io.File;

import modeles.Gestionnaire;
import modeles.Noeud;

/**
 * Classe abstraite présentant toutes les méthodes que les différents états devront implémenter
 */
public abstract class EtatDefaut {
	
	public void undo(Controleur controleur, Gestionnaire gestionnaire) {}
	public void redo(Controleur controleur, Gestionnaire gestionnaire) {}
	
	public void clicBoutonParcourir				(Controleur controleur, boolean accepte, File fichier) {}
	public void clicBoutonValider				(Gestionnaire gestionnaire, Controleur controleur, File fichierXML) {}
	public void clicBoutonHome					(Controleur controleur, Gestionnaire gestionnaire) {}
	public void clicBoutonRetour				(Controleur controleur, Gestionnaire gestionnaire) {}
	public void clicBoutonCalculerTournee		(Controleur controleur, Gestionnaire gestionnaire) {}
	public void clicBoutonGenererFeuilleDeRoute (Controleur controleur, Gestionnaire gestionnaire, String link) {}
	public void clicBoutonStopperTournee		(Controleur controleur, Gestionnaire gestionnaire) {}
	public void clicBoutonModifier				(Controleur controleur) {}
	public void clicBoutonSauvegarder			(Controleur controleur) {}
	public void clicBoutonAnnuler				(Controleur controleur, Gestionnaire gestionnaire) {}
	public void clicBoutonSupprimer				(Controleur controleur, Gestionnaire gestionnaire, int numLigne) {}
	public void clicBoutonAjouter				(Controleur controleur) {}
	public void clicPlanNoeud					(Controleur controleur, Gestionnaire gestionnaire, Noeud noeud) {}
	public void clicPlanLivraison				(Controleur controleur, Gestionnaire gestionnaire, Noeud noeudLivraison) {}
	
	public void glisserDeposer	   (Controleur controleur, boolean accepte, File fichier) {}
	public void redessinerPlan	   (Controleur controleur, Gestionnaire gestionnaire) {}
	public void afficherTournee	   (Controleur controleur, Gestionnaire gestionnaire, boolean solutionOptimale) {}
	public void modifierOrdre	   (Controleur controleur, Gestionnaire gestionnaire, int numLigne, int nouveauNumLigne) {}
	public void modifierPlageDebut (Controleur controleur, Gestionnaire gestionnaire, int numLigne, String plageDebut) {}
	public void modifierPlageFin   (Controleur controleur, Gestionnaire gestionnaire, int numLigne, String plageFin) {}
	public void entrerDuree		   (Controleur controleur, Gestionnaire gestionnaire, int duree) {}
	
	public void getEtat()
	{
		System.out.println("etat defaut");
	}
	
}
