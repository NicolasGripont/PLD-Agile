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
	public List<LivraisonTournee> listeLivraisonsParOrdreDePassage() {
		if(plan.getTournee() == null){
			return null;
		}
		List<LivraisonTournee> livraisons = new ArrayList<>();
		Horaire horaire = new Horaire(plan.getEntrepot().getHoraireDepart());
		//Depart de l'entrepot, on ajoute juste l'arrivé de chaque trajet 
		//(qui correspond au départ du suivant). Sauf le dernier, car le 
		//dernier trajet correspond au retour à l'entrepôt ( '< size-1' )
		for(int i = 0; i < plan.getTournee().getTrajets().size() - 1; i++){
			horaire.ajouterSeconde(plan.getTournee().getTrajets().get(i).getTemps());
			Horaire horaireDepart = new Horaire(horaire);
			if(plan.getLivraisons().get(plan.getTournee().getTrajets().get(i).getArrive()) != null) {
				horaireDepart.ajouterSeconde(plan.getLivraisons().get(plan.getTournee().getTrajets().get(i).getArrive()).getDuree());
				LivraisonTournee l = new LivraisonTournee(plan.getLivraisons().get(plan.getTournee().getTrajets().get(i).getArrive()), 
						horaire, horaireDepart);
				livraisons.add(l);
			}
			horaire = new Horaire(horaireDepart);
		}
		return livraisons;
	}
	
	public Horaire getHoraireDebutTournee() {
		return plan.getEntrepot().getHoraireDepart();
	}
	
	public Horaire getHoraireFinTournee() {
		if(plan.getTournee() == null){
			return null;
		}
		Horaire horaire = new Horaire(plan.getEntrepot().getHoraireDepart());
		for(int i = 0; i < plan.getTournee().getTrajets().size() - 1; i++){
			horaire.ajouterSeconde(plan.getTournee().getTrajets().get(i).getTemps());
			if(plan.getLivraisons().get(plan.getTournee().getTrajets().get(i).getArrive()) != null)
				horaire.ajouterSeconde(plan.getLivraisons().get(plan.getTournee().getTrajets().get(i).getArrive()).getDuree());
		}
		horaire.ajouterSeconde(plan.getTournee().getTrajets().get(plan.getTournee().getTrajets().size() - 1).getTemps());
		return horaire;
	}
}
