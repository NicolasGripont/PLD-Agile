package modeles;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import utility.Pair;

public class Plan {
	private Map<Integer, Noeud> noeuds;
	private Map<Pair<Integer, Integer>, Troncon> troncons;
	//private List<Livraison> livraisons;
	//private Entrepot entrepot;

	public Plan() {
		noeuds = new HashMap<Integer, Noeud>();
		troncons = new HashMap<Pair<Integer, Integer>, Troncon> ();
	}
	
	public void AjouterNoeud(Noeud n) {
		if(n != null) {
			noeuds.put(n.getId(), n);
		}
	}
	
	public void AjouterTroncon(Troncon t) {
		if(t != null) {
			troncons.put(Pair.create(t.getOrigine().getId(), t.getDestination().getId()) , t);
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
	
	public Noeud getNoeud(Integer idNoeud) {
		return noeuds.get(idNoeud);
	}
	
	public Troncon getTroncon(Integer idOrigine, Integer idDestination) {
		return troncons.get(Pair.create(idOrigine, idDestination));
	}

	public Map<Integer, Noeud> getNoeuds() {
		return noeuds;
	}

	public void setNoeuds(Map<Integer, Noeud> noeuds) {
		this.noeuds = noeuds;
	}

	public Map<Pair<Integer, Integer>, Troncon> getTroncons() {
		return troncons;
	}

	public void setTroncons(Map<Pair<Integer, Integer>, Troncon> troncons) {
		this.troncons = troncons;
	}
}
