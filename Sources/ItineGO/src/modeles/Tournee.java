package modeles;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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
	private Map<Noeud, Livraison> livraisons;
		
	/**
	 * Liste de trajets à faire
	 */
	private List<Trajet> trajets;

	/**
	 * Constructeur de la classe
	 */
	public Tournee(Entrepot entrepot, Map<Noeud, Livraison> livraisons,List<Trajet> trajets) {
		super();
		this.entrepot = entrepot;
		this.livraisons = livraisons;
		this.trajets = trajets;
	}

	public List<Trajet> getTrajets() {
		return trajets;
	}
	
	public Entrepot getEntrepot() {
		return entrepot;
	}

	public Map<Noeud, Livraison> getLivraisons() {
		return livraisons;
	}

	public void ajouterTrajet(Integer index, Trajet traj) {
		trajets.add(index, traj);
	}
	
	public void supprimerTrajet(Integer index) {
		trajets.remove(index);
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
		Horaire horaire = new Horaire(entrepot.getHoraireDepart());
		//Depart de l'entrepot, on ajoute juste l'arrivé de chaque trajet 
		//(qui correspond au départ du suivant). Sauf le dernier, car le 
		//dernier trajet correspond au retour à l'entrepôt ( '< size-1' )
		for(int i = 0; i < trajets.size() - 1; i++){
			horaire.ajouterSeconde(trajets.get(i).getTemps());
			if(!livraisons.get(trajets.get(i).getArrive()).getDebutPlage().equals(new Horaire(0,0,0)) && horaire.getHoraireEnMinutes()<livraisons.get(trajets.get(i).getArrive()).getDebutPlage().getHoraireEnMinutes())
			{
				horaire= new Horaire(livraisons.get(trajets.get(i).getArrive()).getDebutPlage());

			}//Ici on modifie si on est arrivé trop tôt par rapport aux plages horaires

			Horaire horaireDepart = new Horaire(horaire);
			horaireDepart.ajouterSeconde(livraisons.get(trajets.get(i).getArrive()).getDuree());
			livraisons.get(trajets.get(i).getArrive()).setHeureDepart(horaireDepart);
			livraisons.get(trajets.get(i).getArrive()).setHeureArrive(horaire);
			livraisonsOrdonnees.add(livraisons.get(trajets.get(i).getArrive()));
			horaire = new Horaire(horaireDepart);
		}
		return livraisonsOrdonnees;
	}
}
