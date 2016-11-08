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
		if(position < listeModifications.size()) {
			listeModifications.get(position).undoAll();
			position++;
		}
	}
	
	public void redoModifications() throws NonRespectPlagesHoraires {
		if(position-1 >= 0) {
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
}
