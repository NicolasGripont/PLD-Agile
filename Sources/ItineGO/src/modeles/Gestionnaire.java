package modeles;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import controleur.Controleur;
import exceptions.BadXmlFile;
import exceptions.BadXmlLivraison;
import exceptions.BadXmlPlan;

/**
 * Classe servant de lien entre le modèle et le contrôleur
 */
public class Gestionnaire {

	private Plan plan;
	private Controleur controleur;
	
	public Gestionnaire(Controleur controleur)
	{
		this.controleur = controleur;
		plan = new Plan(this);
	}
	
	public void chargerPlan(File fichierXML) throws BadXmlFile, BadXmlPlan
	{
		ParseurPlan.parseurPlanVille(fichierXML.getAbsolutePath(), plan);
	}
	
	public void chargerLivraisons(File fichierXML) throws BadXmlLivraison, BadXmlFile
	{
		ParseurLivraison.parseurLivraisonVille(fichierXML.getAbsolutePath(), plan);
	}
	
	public void calculerTournee()
	{
		plan.calculerTournee();
	}
	
	public void tourneeCalculer() {
		controleur.getEtatCourant().afficherTournee(controleur, this, plan.estSolutionOptimale());
	}
	
	public void stopperCalculTournee() {
		plan.stopperCalculTournee();
	}
	
	public boolean solutionTrouvee() {
		return (plan.getTournee() != null) && (plan.getTournee().getTrajets().size() != 0);
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
	
	public Controleur getControleur() {
		return controleur;
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
			if(!plan.getLivraisons().get(plan.getTournee().getTrajets().get(i).getArrive()).getDebutPlage().equals(new Horaire(0,0,0)) && horaire.getHoraireEnMinutes()<plan.getLivraisons().get(plan.getTournee().getTrajets().get(i).getArrive()).getDebutPlage().getHoraireEnMinutes())
			{
				horaire= new Horaire(plan.getLivraisons().get(plan.getTournee().getTrajets().get(i).getArrive()).getDebutPlage());

			}//Ici on modifie si on est arrivé trop tôt par rapport aux plages horaires

			Horaire horaireDepart = new Horaire(horaire);
			horaireDepart.ajouterSeconde(plan.getLivraisons().get(plan.getTournee().getTrajets().get(i).getArrive()).getDuree());
			LivraisonTournee l = new LivraisonTournee(plan.getLivraisons().get(plan.getTournee().getTrajets().get(i).getArrive()), 
					horaire, horaireDepart);
			livraisons.add(l);
			horaire = new Horaire(horaireDepart);
		}
		return livraisons;
	}
	
	public Horaire getHoraireDebutTournee() {
		return plan.getEntrepot().getHoraireDepart();
	}
	
	public int getTempsMaxDeCalcul() {
		return plan.getTempsMax();
	}
	
	public Horaire getHoraireFinTournee() {
		if(plan.getTournee() == null){
			return null;
		}
		Horaire horaire = new Horaire(plan.getEntrepot().getHoraireDepart());
		for(int i = 0; i < plan.getTournee().getTrajets().size() - 1; i++){
			horaire.ajouterSeconde(plan.getTournee().getTrajets().get(i).getTemps());//Modifier ici pour si on arrive trop tot
			if(!plan.getLivraisons().get(plan.getTournee().getTrajets().get(i).getArrive()).getDebutPlage().equals(null) && horaire.getHoraireEnMinutes()<plan.getLivraisons().get(plan.getTournee().getTrajets().get(i).getArrive()).getDebutPlage().getHoraireEnMinutes())
			{
				//System.out.println(" AVANT : H1 "+ horaire.getHoraireEnMinutes()+ "-- DP "+ plan.getLivraisons().get(plan.getTournee().getTrajets().get(i).getArrive()).getDebutPlage().getHoraireEnMinutes());
				horaire= new Horaire(plan.getLivraisons().get(plan.getTournee().getTrajets().get(i).getArrive()).getDebutPlage());
				//System.out.println(" ARPRES : H1 "+ horaire.getHoraireEnMinutes()+ "-- DP "+ plan.getLivraisons().get(plan.getTournee().getTrajets().get(i).getArrive()).getDebutPlage().getHoraireEnMinutes());

			}//Ici on modifie si on est arrivé trop tôt par rapport aux plages horaires
			horaire.ajouterSeconde(plan.getLivraisons().get(plan.getTournee().getTrajets().get(i).getArrive()).getDuree());
		}
		horaire.ajouterSeconde(plan.getTournee().getTrajets().get(plan.getTournee().getTrajets().size() - 1).getTemps());
		return horaire;
	}
	
	public void genererFeuilleDeRoute(String link) {
		plan.genererFeuilleDeRoute(link);
	}
}
