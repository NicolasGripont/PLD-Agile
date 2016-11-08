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

	/**
	 * Plan de l'application
	 */
	private Plan plan;
	/**
	 * Controleur de l'application
	 */
	private Controleur controleur;
	
	private Livraison livraisonEnCourCreation;
	private Livraison livraisonSuivante;
	
	/**
	 * Constructeur de la classe
	 */
	public Gestionnaire(Controleur controleur)
	{
		this.controleur = controleur;
		plan = new Plan(this);
	}
	
	/**
	 * Charge le fichier xml plan de la ville et le parse
	 * @param fichierXML
	 * @throws BadXmlFile
	 * @throws BadXmlPlan
	 */
	public void chargerPlan(File fichierXML) throws BadXmlFile, BadXmlPlan
	{
		ParseurPlan.parseurPlanVille(fichierXML.getAbsolutePath(), plan);
	}
	
	/**
	 * Charge le fichier xml de livraison et le parse
	 * @param fichierXML
	 * @throws BadXmlLivraison
	 * @throws BadXmlFile
	 */
	public void chargerLivraisons(File fichierXML) throws BadXmlLivraison, BadXmlFile
	{
		ParseurLivraison.parseurLivraisonVille(fichierXML.getAbsolutePath(), plan);
	}
	
	/**
	 * Effectue le calcul de la tournée
	 */
	public void calculerTournee()
	{
		plan.calculerTournee();
	}
	
	/**
	 * Affiche la tournée calculée
	 */
	public void tourneeCalculee() {
		controleur.getEtatCourant().afficherTournee(controleur, this, plan.estSolutionOptimale());
	}
	
	/**
	 * Stop le calcul de la tournée
	 */
	public void stopperCalculTournee() {
		plan.stopperCalculTournee();
	}
	
	/**
	 * Renvoie si une solution a été trouvée ou non
	 */
	public boolean solutionTrouvee() {
		return (plan.getTournee() != null) && (plan.getTournee().getTrajets().size() != 0);
	}
	
	/**
	 * Efface tous les noeuds et tronçons de existants du plan
	 */
	public void effacerNoeudsEtTroncons()
	{
		plan.effacerTroncons();
		plan.effacerNoeuds();
	}
	
	/**
	 * Efface toutes les livraisons et l'entrepot du plan
	 */
	public void effacerLivraisonsEtEntrepot()
	{
		plan.effacerEntrepot();
		plan.effacerLivraisons();
	}
	
	/**
	 * Efface la tournée calculée
	 */
	public void effacerTournee()
	{
		plan.effacerTournee();
	}

	/**
	 * Renvoie le plan
	 */
	public Plan getPlan() {
		return plan;
	}
	
	/**
	 * Renvoie le controleur
	 */
	public Controleur getControleur() {
		return controleur;
	}
	
	/**
	 * Renvoie l'horaire du début de la tournée
	 */
	public Horaire getHoraireDebutTournee() {
		return plan.getEntrepot().getHoraireDepart();
	}
	
	/**
	 * Renvoie le temps maximum de calcul
	 */
	public int getTempsMaxDeCalcul() {
		return plan.getTempsMax();
	}
	
	/**
	 * Renvoie l'horaire de la fin de la tournée
	 */
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
	
	/**
	 * Génère la feuille de route dans le fichier spécifié
	 * @param link
	 * 		Fichier dans lequel écrire la feuille de route
	 */
	public void genererFeuilleDeRoute(String link) {
		plan.genererFeuilleDeRoute(link);
	}
	
	public void ajouterLivraisonTournee() {
		//TODO : UTILISER LES ATTRIBUTS : livraisonEnCoursCreation et livraisonSuivante 
		// il faudra rajouter la livraison dans la liste des livraisons du plan et dans la tournée at the good position.
	}

	public Livraison getLivraisonEnCourCreation() {
		return livraisonEnCourCreation;
	}

	public void setLivraisonEnCourCreation(Livraison livraisonEnCourCreation) {
		this.livraisonEnCourCreation = livraisonEnCourCreation;
	}

	public Livraison getLivraisonSuivante() {
		return livraisonSuivante;
	}

	public void setLivraisonSuivante(Livraison livraisonSuivante) {
		this.livraisonSuivante = livraisonSuivante;
	}

	public void supprimerLivraisonTournee(Livraison livraison) {
		//TODO :
		// il faudra supprimer la livraison dans la liste des livraisons du plan et dans la tournée.
		
	}

	public Livraison getLivraisonTournee(int numLigne) {
		// TODO récupérer la livraison à supprimer
		return null;
	}

	public void reordonnerLivraisonTournee(int positionInitiale, int positionFinale) {
		// TODO Changer l'ordre de la livraison à la position initiale vers la position finale.
		
	}

	public void changerPlageHoraireDebut(int numLigne, String plageDebut) {
		// TODO numLigne : Livraison à modifier
		// plageDebut : nouvelle plage de la livraison
		getLivraisonTournee(numLigne).setDebutPlage(new Horaire(plageDebut));
		
		// Checker si ça modifie la tournée au niveau des plages
	}
	
	public void changerPlageHoraireFin(int numLigne, String plageFin) {
		// TODO numLigne : Livraison à modifier
		// plageFin : nouvelle plage de la livraison
		getLivraisonTournee(numLigne).setFinPlage(new Horaire(plageFin));
		
		// Checker si ça modifie la tournée au niveau des plages
	}
}
