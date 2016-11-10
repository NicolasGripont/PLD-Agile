package controleur;

import exceptions.NonRespectPlagesHoraires;
import modeles.Gestionnaire;
import modeles.Livraison;
import modeles.Noeud;

/**
 * Commande qui permet l'ajout d'une livraison
 *
 */
public class AjouterLivraison extends Commande {

	private Livraison livraison;
	private Gestionnaire gestionnaire;
	private Noeud noeudSuivant;
	private int numLigne;
	
	/**
	 * Constructeur de la la commande. On va récupérer la livraison que l'on souhaite ajouter, 
	 * et le noeud suivant cette livraison pour pouvoir insérer cette livraison au bon endroit
	 * 
	 * @param gestionnaire 
	 *            : Gestionnaire de l'application pour pouvoir appeler les méthodes modifiant le modèle.
	 * @param numLigne 
	 *            : numéro de la ligne où l'on souhaite ajouter la livraison
	 */
	public AjouterLivraison(Gestionnaire gestionnaire, int numLigne) {
		this.gestionnaire = gestionnaire;
		this.numLigne = numLigne;
		livraison = gestionnaire.getLivraisonEnCoursCreation();
		noeudSuivant = gestionnaire.getNoeudTournee(numLigne);
	}
	
	/**
	 * On va faire appel à la méthode ajouter du modèle pour ajouter la livraison, 
	 * ainsi que stocker la livraison en cours et le noeud suivant cette livraison dans le gestionnaire.
	 * On va renvoyer une exception dans le cas où les plages ne sont pas respecter lors de l'ajout d'une livraison ayant des plages horaires.
	 */
	@Override
	public void doCode() throws NonRespectPlagesHoraires {
		gestionnaire.setLivraisonEnCourCreation(livraison);
		gestionnaire.setNoeudSuivant(noeudSuivant);
		gestionnaire.setPositionLivraisonEnCours(numLigne);
		gestionnaire.ajouterLivraisonTournee();
	}

	/**
	 * On récupère la livraison que l'on vient d'ajouter ainsi que le noeud suivant qui seront utilisés si jamais on décide de faire un redo.
	 * On va appeler la méthode supprimer du modèle en lui donnant le numéro de la livraison à supprimer. 
	 */
	@Override
	public void undoCode() throws NonRespectPlagesHoraires {
		livraison = gestionnaire.getLivraisonTournee(numLigne);
		noeudSuivant = gestionnaire.getNoeudTournee(numLigne+1);
		gestionnaire.supprimerLivraisonTournee(numLigne);
	}
}
