package controleur;

import java.util.LinkedList;

public class ListeModifications {

	protected LinkedList<ListeCommandes> listeModifications;
	private int position;
	
	public ListeModifications() {
		listeModifications = new LinkedList<>();
		position = 0;
	}
	
	public void undoModifications() {
		if(position < listeModifications.size()) {
			listeModifications.get(position).undoAll();
			position++;
		}
	}
	
	public void redoModifications() {
		if(position-1 >= 0) {
			position--;
			listeModifications.get(position).redoAll();
		}
	}
	
	public void ajouterCommande(Commande commande) {
		listeModifications.getFirst().ajouterCommande(commande);
	}
	
	public void creerModification() {
		listeModifications.addFirst(new ListeCommandes());
	}
	
	public void annulerModification() {
		undoModifications();
		listeModifications.removeFirst();
	}
}
