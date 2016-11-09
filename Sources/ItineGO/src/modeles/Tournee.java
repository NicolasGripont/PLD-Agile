package modeles;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

/**
 * Classe modélisant la tournée à effectuer, une fois calculée
 */
public class Tournee {

	/**
	 * Entrepot de la tournée
	 */
	private Entrepot entrepot;
	
	/**
	 * Map représentant l'ensemble des livraisons de la tournée à effectuer
	 * La livraison est accessible par le noeud modélisant sa position
	 */
	private Map<Integer, Livraison> livraisons;
	/**
	 * Liste des livraisons ordonées dans l'ordre de passage
	 */
	private LinkedList<Integer> ordreLivraisons;
	/**
	 * Liste de trajets à faire
	 */
	private List<Trajet> trajets;

	/**
	 * Constructeur de la classe
	 */
	public Tournee(Entrepot entrepot, Map<Integer, Livraison> livraisons,List<Trajet> trajets) {
		super();
		this.entrepot = entrepot;
		this.livraisons = livraisons;
		this.trajets = trajets;
		this.ordreLivraisons = new LinkedList<>();
		for(int i = 1; i < trajets.size(); ++i) {
			this.ordreLivraisons.add(trajets.get(i).getDepart().getId());
		}
	}

	public List<Trajet> getTrajets() {
		return trajets;
	}
	
	public Entrepot getEntrepot() {
		return entrepot;
	}

	public Map<Integer, Livraison> getLivraisons() {
		return livraisons;
	}

	public void ajouterTrajet(Integer index, Trajet traj) {
		trajets.add(index, traj);
	}
	
	public void supprimerTrajet(Integer index) {
		trajets.remove(index);
	}
	
	public boolean sontValidesHeuresLivraisons() {
		for(Entry<Integer, Livraison> l: livraisons.entrySet()) {
			if(!l.getValue().sontValidesPlages()) {
				return false;
			}
		}
		return true;
	}
	
	public void removeLivraisonTournee(Integer idLivraison) {
		livraisons.remove(idLivraison);
		ordreLivraisons.remove(idLivraison);
	}
	
	/**
	 * Change le début de la plage horaire de la livraison choisie
	 * Répercute ensuite les changements éventuels au reste de la tournée
	 * @param position
	 * 		Position de la livraison dans la tournée
	 * @param newHoraire
	 * 		Nouvel horaire à mettre au début de la plage horaire
	 */
	public void setDebutPlage(int position, Horaire newHoraire) {
		Livraison liv = livraisons.get(ordreLivraisons.get(position));
		liv.setDebutPlage(newHoraire);
		Horaire horaire = liv.getHeureArrive();
		if(liv.getHeureArrive().getHoraireEnSecondes() < liv.getDebutPlage().getHoraireEnSecondes()) {
			horaire = liv.getDebutPlage();
		}
		for(int i = position+1; i < ordreLivraisons.size(); ++i) {
			liv = livraisons.get(ordreLivraisons.get(i));
			Livraison livPrec = livraisons.get(ordreLivraisons.get(i-1));
			horaire.ajouterSeconde(livPrec.getHeureDepart().getHoraireEnSecondes() + trajets.get(i).getTemps());
			liv.setHeureArrive(horaire);
			if(liv.getHeureArrive().getHoraireEnSecondes() < liv.getDebutPlage().getHoraireEnSecondes()) {
				horaire = liv.getDebutPlage();
			}
			horaire.ajouterSeconde(liv.getDuree());
			liv.setHeureDepart(horaire);
		}
	}
	
	/**
	 * Renvoie la livraison de la position demandée de l'ordre des livraisons
	 */
	public Noeud getNoeudAtPos(int position) {
		if(position == ordreLivraisons.size()) {
			return entrepot.getNoeud();
		}
		return livraisons.get(ordreLivraisons.get(position)).getNoeud();
	}
	
	public Livraison getLivraisonAtPos(int position) {
		return livraisons.get(ordreLivraisons.get(position));
	}
	
	/**
	 * Change la fin de la plage horaire de la livraison choisie
	 * @param position
	 * 		Position de la livraison dans la tournée
	 * @param newHoraire
	 * 		Nouvel horaire à mettre à la fin de la plage horaire
	 */
	public void setFinPlage(int position, Horaire newHoraire) {
		livraisons.get(ordreLivraisons.get(position)).setFinPlage(newHoraire);
	}
	
	@Override
	public String toString() {
		return "Tournee [trajets=" + trajets + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((trajets == null) ? 0 : trajets.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Tournee other = (Tournee) obj;
		if (trajets == null) {
			if (other.trajets != null)
				return false;
		} else if (!trajets.equals(other.trajets))
			return false;
		return true;
	} 
	
	//Precondition: les trajets de la tournée sont triés par ordre de passage
	public List<Livraison> listeLivraisonsParOrdreDePassage() {
		List<Livraison> livraisonsOrdonnees = new ArrayList<>();
		//Depart de l'entrepot, on ajoute juste l'arrivé de chaque trajet 
		//(qui correspond au départ du suivant). Sauf le dernier, car le 
		//dernier trajet correspond au retour à l'entrepôt ( '< size-1' )
		for(int i = 0; i < trajets.size() - 1; i++){
			livraisonsOrdonnees.add(livraisons.get(trajets.get(i).getArrive().getId()));
		}
		return livraisonsOrdonnees;
	}
}
