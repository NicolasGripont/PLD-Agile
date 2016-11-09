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
		System.out.println("UNDO taille liste="+listeModifications.size()+" pos="+position);
	}
	
	public void redoModifications() throws NonRespectPlagesHoraires {
		if(isRedoPossible()) {
			position--;
			listeModifications.get(position).redoAll();
		}
		System.out.println("REDO taille liste="+listeModifications.size()+" pos="+position);
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
		System.out.println("CREER taille liste="+listeModifications.size()+" pos="+position);
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
		System.out.println("ANNULER taille liste="+listeModifications.size()+" pos="+position);
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
	}
}
