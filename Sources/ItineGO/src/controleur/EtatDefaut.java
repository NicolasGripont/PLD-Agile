package controleur;

import java.io.File;

import modeles.Gestionnaire;

public abstract class EtatDefaut {
	
	public void undo()
	{
		
	}
	
	public void redo()
	{
		
	}
	
	public void clicBoutonParcourir(Controleur controleur, boolean accepte, File fichier)
	{
		
	}
	
	public void glisserDeposer(Controleur controleur, boolean accepte, File fichier)
	{
		
	}
	
	public void clicBoutonValider(Gestionnaire gestionnaire, Controleur controleur, File fichierXML)
	{
		
	}
	
	public void clicBoutonHome(Controleur controleur, Gestionnaire gestionnaire)
	{
		
	}
	
	public void clicBoutonRetour(Controleur controleur, Gestionnaire gestionnaire)
	{
		
	}
	
	public void clicBoutonCalculerTournee(Controleur controleur, Gestionnaire gestionnaire)
	{
		
	}
	
	public void redessinerPlan(Controleur controleur, Gestionnaire gestionnaire)
	{
		
	}
	
	public void clicBoutonGenererFeuilleDeRoute(Controleur controleur, Gestionnaire gestionnaire, String link) 
	{
		
	}
	
	public void afficherTournee(Controleur controleur, Gestionnaire gestionnaire, boolean solutionOptimale) 
	{
		
	}
	
	public void clicBoutonStopperTournee(Controleur controleur, Gestionnaire gestionnaire)
	{
		
	}
	
	public void getEtat()
	{
		System.out.println("etat defaut");
	}
}
