package controleur;

public class EtatApplicationDemarree extends EtatDefaut {
	
	public void glisserDeposer(Controleur controleur)
	{
		//changement d'état
		controleur.setEtatCourant(controleur.etatPlanVilleChoisie);
	}

}
