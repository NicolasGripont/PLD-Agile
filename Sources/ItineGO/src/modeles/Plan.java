package modeles;

import java.util.HashMap;
import java.util.Map;

import utility.Pair;

public class Plan {
	private Map<Integer, Noeud> noeuds;
	private Map<Pair<Noeud, Noeud>, Troncon> troncons;
	//private List<Livraison> livraisons;
	//private Entrepot entrepot;

	public Plan() {
		noeuds = new HashMap<Integer, Noeud>();
		troncons = new HashMap<Pair<Noeud, Noeud>, Troncon> ();
	}
	
	public void AjouterNoeud(Noeud n) {
		if(n != null) {
			noeuds.put(n.getId(), n);
		}
	}
	
	public void AjouterTroncon(Troncon t) {
		if(t != null) {
			troncons.put(Pair.create(t.getOrigine(), t.getDestination()) , t);
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

	public Map<Pair<Noeud, Noeud>, Troncon> getTroncons() {
		return troncons;
	}

	public void setTroncons(Map<Pair<Noeud, Noeud>, Troncon> troncons) {
		this.troncons = troncons;
	}
}
