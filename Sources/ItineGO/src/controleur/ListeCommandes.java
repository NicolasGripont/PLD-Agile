package controleur;

import java.util.LinkedList;

public class ListeCommandes {

	private LinkedList<Commande> listeCommandes;
	private int position;
	
	public ListeCommandes() {
		listeCommandes = new LinkedList<>();
		position = 0;
	}
	
	public void ajouterCommande(Commande commande) {
		listeCommandes.addFirst(commande);
		commande.doCode();
		for(int i = 0; i < position; i++) {
			listeCommandes.removeFirst();
		}
		position = 0;
	}
	
	public void undoAll() {
		for(int i = position; i < listeCommandes.size(); i++) {
			undo();
		}
	}
	
	public void redoAll() {
		for(int i = position; i >=0; i--) {
			redo();
		}
	}
	
	public void undo() {
		if(position < listeCommandes.size()) {
			listeCommandes.get(position).undoCode();
			position++;
		}
	}
	
	public void redo() {
		if(position-1 >= 0) {
			position--;
			listeCommandes.get(position).doCode();
		}
	}
}
