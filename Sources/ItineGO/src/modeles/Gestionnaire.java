package modeles;

import java.io.File;
import controleur.Controleur;
import exceptions.BadXmlFile;
import exceptions.BadXmlLivraison;
import exceptions.BadXmlPlan;
import exceptions.NonRespectPlagesHoraires;

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
	
	private Livraison livraisonEnCoursCreation;
	private Noeud noeudSuivant;
	private int positionLivraisonEnCours;
	
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
	 * Renvoie si une solution est optimale ou non
	 */
	public boolean estSolutionOptimale() {
		return plan.estSolutionOptimale();
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
		Horaire horaire = new Horaire(plan.getEntrepot().getHoraireArrive());
		/*for(int i = 0; i < plan.getTournee().getTrajets().size() - 1; i++){
			horaire.ajouterSeconde(plan.getTournee().getTrajets().get(i).getTemps());//Modifier ici pour si on arrive trop tot
			if(!plan.getLivraisons().get(plan.getTournee().getTrajets().get(i).getArrive().getId()).getDebutPlage().equals(null))
			{
				//System.out.println(" AVANT : H1 "+ horaire.getHoraireEnMinutes()+ "-- DP "+ plan.getLivraisons().get(plan.getTournee().getTrajets().get(i).getArrive()).getDebutPlage().getHoraireEnMinutes());
				horaire= new Horaire(plan.getLivraisons().get(plan.getTournee().getTrajets().get(i).getArrive().getId()).getDebutPlage());
				//System.out.println(" ARPRES : H1 "+ horaire.getHoraireEnMinutes()+ "-- DP "+ plan.getLivraisons().get(plan.getTournee().getTrajets().get(i).getArrive()).getDebutPlage().getHoraireEnMinutes());

			}//Ici on modifie si on est arrivé trop tôt par rapport aux plages horaires
			horaire.ajouterSeconde(plan.getLivraisons().get(plan.getTournee().getTrajets().get(i).getArrive().getId()).getDuree());
		}
		horaire.ajouterSeconde(plan.getTournee().getTrajets().get(plan.getTournee().getTrajets().size() - 1).getTemps());*/
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
	
	public void ajouterLivraisonTournee() throws NonRespectPlagesHoraires {
		Noeud n = null;
		if(positionLivraisonEnCours-1 < 0) {
			n = plan.getEntrepot().getNoeud();
		} else {
			n = getNoeudTournee(positionLivraisonEnCours-1);
		}

		System.out.println("livraisonEnCoursCreation : " + livraisonEnCoursCreation);
		System.out.println("n : " + n);
		System.out.println("getNoeudTournee(getPositionLivraisonEnCours()) : " + noeudSuivant);
		plan.ajouterLivraisonTournee(livraisonEnCoursCreation, n, noeudSuivant);
		if(!plan.getTournee().sontValidesHeuresLivraisons()) {
			throw new NonRespectPlagesHoraires();
		}
	}

	public Livraison getLivraisonEnCoursCreation() {
		return livraisonEnCoursCreation;
	}

	public void setLivraisonEnCourCreation(Livraison livraisonEnCourCreation) {
		this.livraisonEnCoursCreation = livraisonEnCourCreation;
	}

	public Noeud getNoeudSuivant() {
		return noeudSuivant;
	}

	public void setNoeudSuivant(Noeud noeudSuivant) {
		this.noeudSuivant = noeudSuivant;
	}

	public void supprimerLivraisonTournee(int position) throws NonRespectPlagesHoraires {
		plan.suppressionLivraisonTournee(getLivraisonTournee(position), (position-1 < 0 ? plan.getEntrepot().getNoeud() : getNoeudTournee(position-1)), getNoeudTournee(position+1));
		if(!plan.getTournee().sontValidesHeuresLivraisons()) {
			throw new NonRespectPlagesHoraires();
		}
	}

	public Noeud getNoeudTournee(int position) {
		return plan.getTournee().getNoeudAtPos(position);
	}
	
	public Livraison getLivraisonTournee(int position) {
		return plan.getTournee().getLivraisonAtPos(position);
	}

	public void reordonnerLivraisonTournee(int positionInitiale, int positionFinale) {
		// TODO Changer l'ordre de la livraison à la position initiale vers la position finale.
		plan.reordonnerLivraisonTournee(positionInitiale, positionFinale);
	}

	/**
	 * Change le début de la plage horaire de la livraison choisie
	 * @throws NonRespectPlagesHoraires
	 * 		Si les plages horaires ne sont pas respectées
	 */
	public void changerPlageHoraireDebut(int position, String plageDebut) throws NonRespectPlagesHoraires {
		System.err.println("CHANGER PLAGE");
		System.err.println(position);
		plan.getTournee().setDebutPlage(position, new Horaire(plageDebut));
		if(!plan.getTournee().sontValidesHeuresLivraisons()) {
			throw new NonRespectPlagesHoraires();
		}
	}
	
	public void changerPlageHoraireFin(int position, String plageFin) throws NonRespectPlagesHoraires {
		getLivraisonTournee(position).setFinPlage(new Horaire(plageFin));
		if(!plan.getTournee().sontValidesHeuresLivraisons()) {
			throw new NonRespectPlagesHoraires();
		}
	}

	public boolean isNoeudLivraison(Noeud noeud) {
		return plan.getLivraisons().containsKey(noeud.getId());
	}

	public boolean isNoeudEntrepot(Noeud noeud) {
		return plan.getEntrepot().getNoeud().equals(noeud);
	}

	public int getPositionLivraisonEnCours() {
		return positionLivraisonEnCours;
	}

	public void setPositionLivraisonEnCours(int positionLivraisonEnCours) {
		this.positionLivraisonEnCours = positionLivraisonEnCours;
	}
}
