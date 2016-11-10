package modeles;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * Quatrième version du TSP, implémente l'utilisation des plages horaires
 */
public class TSP4 extends TSP3 {

	public void chercheSolution(int tpsLimite, int nbSommets, int[][] cout, int[] duree, int[][] plages_horaire) {// @overwrite
																													// chercher
																													// sol
																													// pour
																													// int[nbSomet][2]
																													// plage
		this.tempsLimiteAtteint = false;
		this.coutMeilleureSolution = Integer.MAX_VALUE;
		this.meilleureSolution = new Integer[nbSommets];
		ArrayList<Integer> nonVus = new ArrayList<>();
		for (int i = 1; i < nbSommets; i++) {
			nonVus.add(i);
		}
		ArrayList<Integer> vus = new ArrayList<>(nbSommets);
		vus.add(0); // le premier sommet visite est 0: l'entrepot
		this.branchAndBound(0, nonVus, vus, 0, cout, duree, System.currentTimeMillis(), tpsLimite, plages_horaire);
	}

	void branchAndBound(int sommetCrt, ArrayList<Integer> nonVus, ArrayList<Integer> vus, int coutVus, int[][] cout,
			int[] duree, long tpsDebut, int tpsLimite, int[][] plage_horaire) {
		if ((System.currentTimeMillis() - tpsDebut) > tpsLimite) {
			this.tempsLimiteAtteint = true;

			return;
		}
		if (nonVus.size() == 0) { // tous les sommets ont été visités

			coutVus += cout[sommetCrt][0]; // on rajoute le cout pour retourné à
											// l'entrepot

			if (coutVus < this.getCoutMeilleureSolution()) { // on a trouve une
																// solution
																// meilleure que
																// meilleureSolution
				// On sauvgarde la denière meilleur solution trouvé
				vus.toArray(this.meilleureSolution);
				this.coutMeilleureSolution = coutVus;

			}
		} else {

			if (((coutVus + this.bound(sommetCrt, nonVus, cout, duree)) < this.getCoutMeilleureSolution())) {
				Iterator<Integer> it = this.iterator(sommetCrt, nonVus, cout, duree);

				while (it.hasNext()) {// On parcour les noeud restants
					Integer prochainSommet = it.next();
					vus.add(prochainSommet);
					nonVus.remove(prochainSommet);
					// On calcul l'heure à la quel on arrivera à la prochaine
					// livraison
					int futurCoutVus = coutVus + cout[sommetCrt][prochainSommet];
					if (futurCoutVus < plage_horaire[0][prochainSommet]) {
						futurCoutVus = plage_horaire[0][prochainSommet];// Si on
																		// arrive
																		// trop
																		// tôt
																		// on
																		// attend
					}

					// Si on arrive trop tard à la prochaine livraison on coupe
					// la branche
					if (futurCoutVus < plage_horaire[1][prochainSommet]) {
						this.branchAndBound(prochainSommet, nonVus, vus, futurCoutVus + duree[prochainSommet], cout,
								duree, tpsDebut, tpsLimite, plage_horaire);
					}
					vus.remove(prochainSommet);// On indique qu'on a déjà vu le
												// sommet
					nonVus.add(prochainSommet);

				}

			}
		}
	}

}
