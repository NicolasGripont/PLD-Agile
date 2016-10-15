package modeles;

import java.util.ArrayList;
import java.util.List;

public class Plan {
	private List<Noeud> noeuds;
	private List<Troncon> troncons;
	//private List<Livraison> livraisons;
	//private Entrepot entrepot;
	
	public Plan() {
		noeuds = new ArrayList<Noeud>();
		troncons = new ArrayList<Troncon>();
	}
	
	public void AjouterNoeud(Noeud n) {
		if(n != null) {
			noeuds.add(n);
		}
	}
	
	public void AjouterTroncon(Troncon t) {
		if(t != null) {
			troncons.add(t);
		}
	}
	
	/*public void AjouterLivraison(Livraison l) {
		if(l != null) {
			livraisons.add(l);
		}
	}*/
	
	/*public void AjouterEntrepot(Entrepot e) {
		if(e != null) {
			entrepot = e;
		}
	}*/
	
	public boolean CalculerTournee() {
		return false;
	}
}
