package modeles;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

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
	
	public boolean chargerPlan(File fichierXML)
	{
		return ParseurPlan.parseurPlanVille(fichierXML.getAbsolutePath(), plan);
	}
	
	public boolean chargerLivraisons(File fichierXML)
	{
		return ParseurLivraison.parseurLivraisonVille(fichierXML.getAbsolutePath(), plan);
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
	
	//Precondition: les trajets de la tournée sont triés par ordre de passage
	public List<Livraison> listeLivraisonsParOrdreDePassage() {
		if(plan.getTournee() == null){
			return null;
		}
		List<Livraison> livraisons = new ArrayList<>();
		//Depart de l'entrepot, on ajoute juste l'arrivé de chaque trajet 
		//(qui correspond au départ du suivant). Sauf le dernier, car le 
		//dernier trajet correspond au retour à l'entrepôt ( '< size-1' )
		for(int i = 0; i < plan.getTournee().getTrajets().size() - 1; i++){
			livraisons.add(plan.getLivraisons().get(plan.getTournee().getTrajets().get(i).getArrive()));
		}
		return livraisons;
	}
}
