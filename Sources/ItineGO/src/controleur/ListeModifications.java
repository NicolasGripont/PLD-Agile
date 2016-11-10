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
		NonRespectPlagesHoraires exec =  null;
		if(isUndoPossible()) {
			try {
				listeModifications.get(position).undoAll();
			}
			catch(NonRespectPlagesHoraires e) {
				exec = e;
			}
			position++;
		}
		if(exec != null)
		{
			throw exec;
		}
	}
	
	public void undoAllModifications() {
		for(int i = position; i < listeModifications.size(); i++) {
			try {
				undoModifications();
			}catch(NonRespectPlagesHoraires e) {
				//
			}
		}
	}
	
	public void redoModifications() throws NonRespectPlagesHoraires {
		NonRespectPlagesHoraires exec =  null;
		if(isRedoPossible()) {
			position--;
			try {
				listeModifications.get(position).redoAll();
			}
			catch(NonRespectPlagesHoraires e) {
				exec = e;
			}
		}
		if(exec != null)
		{
			throw exec;
		}
	}
	
	public void redoAllModifications() {
		for(int i = position; i < listeModifications.size(); i++) {
			try {
				redoModifications();
			}catch(NonRespectPlagesHoraires e) {
				//
			}
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
		NonRespectPlagesHoraires exec =  null;
		try {
			undoModifications();
		}
		catch(NonRespectPlagesHoraires e) {
			exec = e;
		}
		listeModifications.removeFirst();
		position = 0;
		if(exec != null)
		{
			throw exec;
		}
	}
	
	public void finModification() {
		if(listeModifications.getFirst().estVide() && !listeModifications.isEmpty()) {
			listeModifications.removeFirst();
		}
	}
	
	public boolean isUndoPossible() {
		return position < listeModifications.size(); 
	}
	
	public boolean isRedoPossible() {
		return position-1 >= 0 && !listeModifications.isEmpty();
	}

	public void viderListeModifications() {
		listeModifications.clear();
		position = 0;
	}
}
