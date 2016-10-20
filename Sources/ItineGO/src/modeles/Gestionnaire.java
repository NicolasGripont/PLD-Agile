package modeles;

import java.io.File;

public class Gestionnaire {

	private ParseurLivraison parseurLivraison;
	private ParseurPlan parseurPlan;
	private Plan plan;
	
	public Gestionnaire()
	{
		parseurLivraison =  new ParseurLivraison();
		parseurPlan =  new ParseurPlan();
		plan = new Plan();
	}
	
	public void chargerPlan(File fichierXML)
	{
		ParseurPlan.parseurPlanVille(fichierXML.getAbsolutePath(), plan);
	}
	
	public void chargerLivraisons(File fichierXML)
	{
		ParseurLivraison.parseurLivraisonVille(fichierXML.getAbsolutePath(), plan);
	}
	
	public void calculerTournee()
	{
		plan.calculerTournee();
	}
	
	public void effacerNoeudsEtTroncons()
	{
		plan.effacerTroncons();
		plan.effacerNoeuds();
	}
	
	public void effacerLivraisonsEtEntrepot()
	{
		plan.effacerEntrepot();
		plan.effacerLivraisons();
	}
	
	public void effacerTournee()
	{
		plan.effacerTournee();
	}

	public Plan getPlan() {
		return plan;
	}
}
