package controleur;

import java.util.LinkedList;

import exceptions.NonRespectPlagesHoraires;

public class ListeModifications {

	protected LinkedList<ListeCommandes> listeModifications;
	private int position;
	
	public ListeModifications() {
		listeModifications = new LinkedList<>();
		position = 0;
	}
	
	public void undoModifications() throws NonRespectPlagesHoraires {
		if(isUndoPossible()) {
			listeModifications.get(position).undoAll();
			position++;
		}
	}
	
	public void redoModifications() throws NonRespectPlagesHoraires {
		if(isRedoPossible()) {
			position--;
			listeModifications.get(position).redoAll();
		}
	}
	
	public void ajouterCommande(Commande commande) throws NonRespectPlagesHoraires {
		listeModifications.getFirst().ajouterCommande(commande);
	}
	
	public void creerModification() {
		for(int i = 0; i < position; i++) {
			listeModifications.removeFirst();
		}
		listeModifications.addFirst(new ListeCommandes());
		position = 0;
	}
	
	public void annulerModification() throws NonRespectPlagesHoraires {
		undoModifications();
		listeModifications.removeFirst();
	}
	
	public void finModification() {
		if(listeModifications.getFirst().estVide()) {
			listeModifications.removeFirst();
		}
	}
	
	public boolean isUndoPossible() {
		return position < listeModifications.size(); 
	}
	
	public boolean isRedoPossible() {
		return position-1 >= 0 && listeModifications.size() >= 1;
	}
	
	public LinkedList<ListeCommandes> getListeModifications() {
		return listeModifications;
	}

	public void viderListeModifications() {
		for (int i = 0; i < listeModifications.size(); i++) {
			listeModifications.remove();
		}
	}
}
