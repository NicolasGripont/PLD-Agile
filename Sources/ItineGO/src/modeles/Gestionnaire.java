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
	private final Plan plan;
	/**
	 * Controleur de l'application
	 */
	private final Controleur controleur;

	private Livraison livraisonEnCoursCreation;
	private Noeud noeudSuivant;
	private int positionLivraisonEnCours;

	/**
	 * Constructeur de la classe
	 */
	public Gestionnaire(Controleur controleur) {
		this.controleur = controleur;
		this.plan = new Plan(this);
	}

	/**
	 * Charge le fichier xml plan de la ville et le parse
	 * 
	 * @param fichierXML
	 * @throws BadXmlFile
	 * @throws BadXmlPlan
	 */
	public void chargerPlan(File fichierXML) throws BadXmlFile, BadXmlPlan {
		ParseurPlan.parseurPlanVille(fichierXML.getAbsolutePath(), this.plan);
	}

	/**
	 * Charge le fichier xml de livraison et le parse
	 * 
	 * @param fichierXML
	 * @throws BadXmlLivraison
	 * @throws BadXmlFile
	 */
	public void chargerLivraisons(File fichierXML) throws BadXmlLivraison, BadXmlFile {
		ParseurLivraison.parseurLivraisonVille(fichierXML.getAbsolutePath(), this.plan);
	}

	/**
	 * Effectue le calcul de la tournée
	 */
	public void calculerTournee() {
		this.plan.calculerTournee();
	}

	/**
	 * Affiche la tournée calculée
	 */
	public void tourneeCalculee() {
		this.controleur.getEtatCourant().afficherTournee(this.controleur, this, this.plan.estSolutionOptimale());
	}

	/**
	 * Renvoie si une solution est optimale ou non
	 */
	public boolean estSolutionOptimale() {
		return this.plan.estSolutionOptimale();
	}

	/**
	 * Renvoie si une solution a été trouvée ou non
	 */
	public boolean solutionTrouvee() {
		return (this.plan.getTournee() != null) && (this.plan.getTournee().getTrajets().size() != 0);
	}

	/**
	 * Efface tous les noeuds et tronçons de existants du plan
	 */
	public void effacerNoeudsEtTroncons() {
		this.plan.effacerTroncons();
		this.plan.effacerNoeuds();
	}

	/**
	 * Efface toutes les livraisons et l'entrepot du plan
	 */
	public void effacerLivraisonsEtEntrepot() {
		this.plan.effacerEntrepot();
		this.plan.effacerLivraisons();
	}

	/**
	 * Efface la tournée calculée
	 */
	public void effacerTournee() {
		this.plan.effacerTournee();
	}

	/**
	 * Renvoie le plan
	 */
	public Plan getPlan() {
		return this.plan;
	}

	/**
	 * Renvoie le controleur
	 */
	public Controleur getControleur() {
		return this.controleur;
	}

	/**
	 * Renvoie l'horaire du début de la tournée
	 */
	public Horaire getHoraireDebutTournee() {
		return this.plan.getEntrepot().getHoraireDepart();
	}

	/**
	 * Renvoie le temps maximum de calcul
	 */
	public int getTempsMaxDeCalcul() {
		return this.plan.getTempsMax();
	}

	/**
	 * Renvoie l'horaire de la fin de la tournée
	 */
	public Horaire getHoraireFinTournee() {
		if (this.plan.getTournee() == null) {
			return null;
		}
		Horaire horaire = new Horaire(this.plan.getEntrepot().getHoraireArrive());
		return horaire;
	}

	/**
	 * Génère la feuille de route dans le fichier spécifié
	 * 
	 * @param link
	 *            Fichier dans lequel écrire la feuille de route
	 */
	public void genererFeuilleDeRoute(String link) {
		this.plan.genererFeuilleDeRoute(link);
	}

	public void ajouterLivraisonTournee() throws NonRespectPlagesHoraires {
		Noeud n = null;
		if ((this.positionLivraisonEnCours - 1) < 0) {
			n = this.plan.getEntrepot().getNoeud();
		} else {
			n = this.getNoeudTournee(this.positionLivraisonEnCours - 1);
		}

		this.plan.ajouterLivraisonTournee(this.livraisonEnCoursCreation, n, this.noeudSuivant);
		if (!this.plan.getTournee().sontValidesHeuresLivraisons()) {
			throw new NonRespectPlagesHoraires();
		}
	}

	public Livraison getLivraisonEnCoursCreation() {
		return this.livraisonEnCoursCreation;
	}

	public void setLivraisonEnCourCreation(Livraison livraisonEnCourCreation) {
		this.livraisonEnCoursCreation = livraisonEnCourCreation;
	}

	public Noeud getNoeudSuivant() {
		return this.noeudSuivant;
	}

	public void setNoeudSuivant(Noeud noeudSuivant) {
		this.noeudSuivant = noeudSuivant;
	}

	public void supprimerLivraisonTournee(int position) throws NonRespectPlagesHoraires {
		this.plan.suppressionLivraisonTournee(this.getLivraisonTournee(position),
				((position - 1) < 0 ? this.plan.getEntrepot().getNoeud() : this.getNoeudTournee(position - 1)),
				this.getNoeudTournee(position + 1));
		if (!this.plan.getTournee().sontValidesHeuresLivraisons()) {
			throw new NonRespectPlagesHoraires();
		}
	}

	public Noeud getNoeudTournee(int position) {
		return this.plan.getTournee().getNoeudAtPos(position);
	}

	public Livraison getLivraisonTournee(int position) {
		return this.plan.getTournee().getLivraisonAtPos(position);
	}

	public void reordonnerLivraisonTournee(int positionInitiale, int positionFinale) {
		this.plan.reordonnerLivraisonTournee(positionInitiale, positionFinale);
	}

	/**
	 * Change le début de la plage horaire de la livraison choisie
	 * 
	 * @throws NonRespectPlagesHoraires
	 *             Si les plages horaires ne sont pas respectées
	 */
	public void changerPlageHoraireDebut(int position, String plageDebut) throws NonRespectPlagesHoraires {
		this.plan.getTournee().setDebutPlage(position, new Horaire(plageDebut));
		if (!this.plan.getTournee().sontValidesHeuresLivraisons()) {
			throw new NonRespectPlagesHoraires();
		}
	}

	public void changerPlageHoraireFin(int position, String plageFin) throws NonRespectPlagesHoraires {
		this.getLivraisonTournee(position).setFinPlage(new Horaire(plageFin));
		if (!this.plan.getTournee().sontValidesHeuresLivraisons()) {
			throw new NonRespectPlagesHoraires();
		}
	}

	public boolean isNoeudLivraison(Noeud noeud) {
		return this.plan.getLivraisons().containsKey(noeud.getId());
	}

	public boolean isNoeudEntrepot(Noeud noeud) {
		return this.plan.getEntrepot().getNoeud().equals(noeud);
	}

	public int getPositionLivraisonEnCours() {
		return this.positionLivraisonEnCours;
	}

	public void setPositionLivraisonEnCours(int positionLivraisonEnCours) {
		this.positionLivraisonEnCours = positionLivraisonEnCours;
	}
}
