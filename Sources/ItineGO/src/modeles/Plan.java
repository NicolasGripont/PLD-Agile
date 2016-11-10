package modeles;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import utility.Pair;

/**
 * Classe modélisant le plan
 */
public class Plan {
	/**
	 * Map représentant l'ensemble des noeuds du plan Le noeud est accessible
	 * par son id
	 */
	private Map<Integer, Noeud> noeuds;

	/**
	 * Map représentant l'ensemble des tronçons du plan Le tronc est accessible
	 * par son noeud de départ et d'arrivée
	 */
	private Map<Pair<Noeud, Noeud>, Troncon> troncons;

	/**
	 * Map représentant l'ensemble des livraisons de la tournée à effectuer La
	 * livraison est accessible par le noeud modélisant sa position
	 */
	private Map<Integer, Livraison> livraisons;

	/**
	 * Entrepot de départ de la tournée à effectuer
	 */
	private Entrepot entrepot;

	private int tableauDesId[];

	/**
	 * Matrice du graphe, modélisant le coût du troncon entre chaque noeud, un 0
	 * indique qu'il n'y a pas de lien direct
	 */
	static Integer matriceDuGraphe[][];

	/**
	 * TSP utilisé dans l'application
	 */
	private TSP4 tsp;

	/**
	 * Modélise la tournée effectuée
	 */
	private Tournee tournee;

	private Gestionnaire gestionnaire;

	/**
	 * Temps maximum durant lequel l'application va tourner en millisecondes
	 */
	private final int tempsMax = 20000;

	/**
	 * 
	 */
	private HashMap<Integer, HashMap<Integer, Integer>> AllNoires = new HashMap<>();

	/**
	 * 
	 */
	private HashMap<Integer, HashMap<Integer, Integer>> AllPrevious = new HashMap<>();

	/**
	 * 
	 */
	private Integer depart[];

	/**
	 * Constructeur de classe
	 */
	public Plan() {
		this.noeuds = new HashMap<>();
		this.troncons = new HashMap<>();
		this.livraisons = new HashMap<>();
	}

	/**
	 * Constructeur de classe
	 */
	public Plan(Gestionnaire gestionnaire) {
		this.gestionnaire = gestionnaire;
		this.noeuds = new HashMap<>();
		this.troncons = new HashMap<>();
		this.livraisons = new HashMap<>();
	}

	/**
	 * Permet de savoir si la solution trouvée est optimale (toutes les
	 * possibilités ont été essayées), ou non (fin du temps)
	 */
	public boolean estSolutionOptimale() {
		return !this.tsp.getTempsLimiteAtteint();
	}

	/**
	 * @param id
	 *            : id dont on cherche la place dans le table
	 * @return l'indice dans le tableau des id à laquel on trouve l'id mis en
	 *         paramètre
	 */
	public int numDansTableau(int id) {
		for (int i = 0; i < this.tableauDesId.length; i++) {
			if (this.tableauDesId[i] == id) {
				return i;
			}
		}
		return -1;
	}

	/**
	 * Calcule la tournée
	 */
	public void calculerTournee() {

		int nbDeLivraison = this.livraisons.size();

		this.tableauDesId = new int[this.noeuds.size()];

		/**
		 * On remplie le tableau des id
		 */
		this.remplirTableauDesID(this.tableauDesId);

		/**
		 * numero dans tableau des id des livraisons
		 */
		this.depart = new Integer[nbDeLivraison + 1];

		/**
		 * duree des livraison
		 */
		int duree[];
		duree = new int[nbDeLivraison + 1];

		/**
		 * plage horaire pour chaque livraison
		 */
		int[][] plages_horaire;
		plages_horaire = new int[2][nbDeLivraison + 1];

		/**
		 * On place les informations de l'entrepot et des livraisons dans les
		 * deux tableaux
		 */
		this.remplirTableauDepEtDur(this.depart, duree, plages_horaire);

		/**
		 * On créé la matrice qui représent le graphe avec les couts des arcs
		 */
		matriceDuGraphe = new Integer[this.noeuds.size()][this.noeuds.size()];

		/**
		 * On remplie la matrice qui modelise le graphe
		 */
		this.remplirMatriceDuGraphe();

		/**
		 * va contenir les les couts du graphe simplifié qui aura pour sommet
		 * les livraisons et pour cout la somme des couts des tronçons qui
		 * seront sur les plus courts chemins entre les livraisons origine -
		 * destination - cout
		 */
		this.AllNoires = new HashMap<>(); // Sera Ã©galement placÃ© en
											// paramÃ¨tre

		this.AllPrevious = new HashMap<>(); // Sera Ã©galement placÃ© en
											// paramÃ¨tre

		/**
		 * On calcul les plus court chemin entre toute les livraisons
		 */
		Dijkstra(this.depart, this.AllNoires, this.AllPrevious);

		/**
		 * On créé la matrice de cout pour le TSP
		 */
		int cout[][] = new int[this.depart.length][this.depart.length];

		/**
		 * On remplie la matrice utilisé par la TSP a partir des calculs de
		 * Dijkstra
		 */
		this.constructionMatTsp(cout, this.depart, this.AllNoires);

		this.tsp = new TSP4();

		/**
		 * On lance l'algorithme pour rechercher la meilleur tournée
		 */
		this.tsp.chercheSolution(this.tempsMax, this.depart.length, cout, duree, plages_horaire);

		/**
		 * Un fois les calcul réalisé, on créé les objets qui vont être utiliseé
		 * par la couche supérieur
		 */
		if (this.tsp.getCoutMeilleureSolution() != Integer.MAX_VALUE) {
			this.constructionTournee(this.depart, this.AllNoires, this.AllPrevious);
		}
	}

	/**
	 * Construit la tournée pendant le calcul de la tournee
	 */
	public void constructionTourneePendantCalculDeTournee() {
		if (this.tsp.getCoutMeilleureSolution() != Integer.MAX_VALUE) {
			this.constructionTournee(this.depart, this.AllNoires, this.AllPrevious);
		}
	}

	/**
	 * Construit l'objet Tournée modélisant la solution pour la tournée à
	 * effectuer
	 */
	private void constructionTournee(Integer depart[], HashMap<Integer, HashMap<Integer, Integer>> AllNoires,
			HashMap<Integer, HashMap<Integer, Integer>> AllPrevious) {
		List<Integer> futurTourne = new ArrayList<>();
		HashMap<Integer, Integer> previous;

		Integer noeudCourant = depart[this.tsp.getMeilleureSolution(0)]; // Comme
																			// on
																			// travaille
																			// avec
																			// des
																			// arbres
																			// de
																			// couvrance
																			// minimum
																			// on
																			// fait
																			// le
																			// chemin
																			// Ã 
																			// l'envers
		for (int i = depart.length - 1; i >= 0; i--) {
			previous = new HashMap<>(AllPrevious.get(depart[this.tsp.getMeilleureSolution(i)]));
			while (previous.get(noeudCourant) != noeudCourant) {
				futurTourne.add(noeudCourant);
				noeudCourant = previous.get(noeudCourant);
			}
		}

		LinkedList<Integer> ordreTourneID = new LinkedList<>();

		for (int j = 0; j < depart.length; j++) {
			ordreTourneID.add(this.tableauDesId[depart[this.tsp.getMeilleureSolution(j)]]);
		}
		// on ajoute l'entrepot à la fin de la tournee
		ordreTourneID.add(ordreTourneID.getFirst());
		// on supprime l'entrepot du début de la tournee
		ordreTourneID.removeFirst();

		// TODO tableau des idée

		// LinkedList<Integer> ordreT = new LinkedList<Integer>()

		futurTourne.add(depart[this.tsp.getMeilleureSolution(0)]);
		Collections.reverse(futurTourne);
		List<Integer> FT = new ArrayList<>(futurTourne);
		futurTourne.clear();
		ListIterator<Integer> itFT = FT.listIterator();
		Integer lastAdded = -1;
		Integer myInt;
		while (itFT.hasNext()) {
			myInt = itFT.next();
			if (!lastAdded.equals(myInt)) {
				futurTourne.add(this.tableauDesId[myInt]);
			}
			lastAdded = myInt;
		}

		// Puis retrouver les tronçons en recupérant les id des noeuds dans
		// tableauDesId
		// Puis on constrit tournee

		// Construction de la Tournee
		Set<Livraison> dejaVisites = new HashSet<>();
		List<Trajet> trajetsPrevus = new ArrayList<>();
		List<Troncon> tronconsTrajet = new ArrayList<>();
		for (Integer i = 0; i < (futurTourne.size() - 1); i++) {
			tronconsTrajet.add(this.troncons
					.get(new Pair<>(this.noeuds.get(futurTourne.get(i)), this.noeuds.get(futurTourne.get(i + 1)))));
			// (Si le neoud suivant est une livraison ET si la livraison n'a pas
			// deja etait ajoutée ET si le noeud correspond à la future
			// livraison à faire)
			// OU
			// (si le noeud suivant est l'entrepot ET que c'est le dernier noeud
			// a visiter)
			if (((this.livraisons.get(futurTourne.get(i + 1)) != null)
					&& !dejaVisites.contains(this.livraisons.get(futurTourne.get(i + 1)))
					&& (this.livraisons.get(futurTourne.get(i + 1)) == this.livraisons.get(ordreTourneID.getFirst())))
					|| ((this.entrepot.getNoeud().equals(this.noeuds.get(futurTourne.get(i + 1))))
							&& (i == (futurTourne.size() - 2)))) {
				ordreTourneID.removeFirst();
				if (!tronconsTrajet.isEmpty()) {
					trajetsPrevus.add(new Trajet(tronconsTrajet.get(0).getOrigine(),
							tronconsTrajet.get(tronconsTrajet.size() - 1).getDestination(), tronconsTrajet));
					tronconsTrajet = new ArrayList<>();
					dejaVisites.add(this.livraisons.get(futurTourne.get(i + 1)));
				}
			}
		}
		this.miseAJourHeureDePassageLivraison(trajetsPrevus);
		this.tournee = new Tournee(this.entrepot, this.livraisons, trajetsPrevus);
	}

	/**
	 * Construit la matrice des coûts pour le TSP
	 * 
	 * @param cout
	 *            matrice des cout des plus court chemin entre les livraisons
	 * @param depart
	 *            vas simuller les noeuds de livraisons
	 * @param AllNoires
	 *            vas contenir les noeud noires du gra
	 */
	private void constructionMatTsp(int[][] cout, Integer[] depart,
			HashMap<Integer, HashMap<Integer, Integer>> AllNoires) {
		for (int u = 0; u < depart.length; u++) {
			for (int v = 0; v < depart.length; v++) {
				cout[u][v] = (AllNoires.get(depart[u])).get(depart[v]);
			}
		}
	}

	private void remplirTableauDesID(int[] tableauDesId2) {
		Set<Entry<Integer, Noeud>> setnoeuds;
		Iterator<Entry<Integer, Noeud>> itnoeuds;
		Entry<Integer, Noeud> enoeuds;
		int itid = 0;
		setnoeuds = this.noeuds.entrySet();
		itnoeuds = setnoeuds.iterator();
		while (itnoeuds.hasNext()) {
			enoeuds = itnoeuds.next();
			this.tableauDesId[itid] = enoeuds.getKey();
			itid++;
		}
	}

	private void remplirMatriceDuGraphe() {
		for (Integer i = 0; i < matriceDuGraphe.length; i++) {
			for (Integer j = 0; j < matriceDuGraphe.length; j++) {
				if (i != j) {
					Pair<Noeud, Noeud> key = new Pair<>(this.noeuds.get(this.tableauDesId[i]),
							this.noeuds.get(this.tableauDesId[j]));
					if (this.troncons.get(key) != null) {
						matriceDuGraphe[i][j] = (int) ((this.troncons.get(key).getLongueur())
								/ (this.troncons.get(key).getVitesse()));
					} else {
						matriceDuGraphe[i][j] = -1; // a remplacer par un max
					}

				} else {
					matriceDuGraphe[i][j] = 0;
				}
			}

		}
	}

	private void remplirTableauDepEtDur(Integer[] depart, int[] duree, int[][] plages_horaire) {
		depart[0] = (this.numDansTableau(this.entrepot.getNoeud().getId())); // le
																				// depart
																				// 0
																				// sera
																				// l'entrepot
		duree[0] = 0;
		plages_horaire[0][0] = 0;
		plages_horaire[1][0] = Integer.MAX_VALUE;

		Set<Entry<Integer, Livraison>> setlivraisons;
		Iterator<Entry<Integer, Livraison>> itlivraisons;
		Entry<Integer, Livraison> elivraisons;

		int idep = 1;
		setlivraisons = this.livraisons.entrySet();
		itlivraisons = setlivraisons.iterator();
		/**
		 * Si il n'y a pas de plages horaire indiqué, on prends 0 comme début de
		 * plage et Integer.MAX_VALUE
		 */
		while (itlivraisons.hasNext()) {
			elivraisons = itlivraisons.next();
			depart[idep] = this.numDansTableau(elivraisons.getKey());
			duree[idep] = (elivraisons.getValue().getDuree());

			if (!elivraisons.getValue().getDebutPlage().equals(new Horaire(0, 0, 0))) {
				plages_horaire[0][idep] = elivraisons.getValue().getDebutPlage().getHoraireEnMinutes()
						- this.entrepot.getHoraireDepart().getHoraireEnMinutes();
			} else {
				plages_horaire[0][idep] = 0;
			}
			plages_horaire[0][idep] *= 60;
			if (!elivraisons.getValue().getFinPlage().equals(new Horaire(0, 0, 0))) {
				plages_horaire[1][idep] = elivraisons.getValue().getFinPlage().getHoraireEnMinutes()
						- this.entrepot.getHoraireDepart().getHoraireEnMinutes();
				plages_horaire[1][idep] *= 60;
			} else {
				plages_horaire[1][idep] = Integer.MAX_VALUE;

			}

			idep++;
		}

	}

	private static void Dijkstra(Integer depart[], HashMap<Integer, HashMap<Integer, Integer>> AllNoires,
			HashMap<Integer, HashMap<Integer, Integer>> AllPrevious) {

		for (int itDepart = 0; itDepart < depart.length; itDepart++) {
			Integer curentNoeud = depart[itDepart];

			HashMap<Integer, Integer> blancs = new HashMap<>();
			HashMap<Integer, Integer> noires = new HashMap<>();
			HashMap<Integer, Integer> previous = new HashMap<>();
			HashMap<Integer, Integer> blancsiteration = new HashMap<>();
			Set<Entry<Integer, Integer>> setblancs;
			Iterator<Entry<Integer, Integer>> it;
			Entry<Integer, Integer> e;

			// Initialisation de la map des blancs
			for (Integer i = 0; i < matriceDuGraphe[1].length; i++) {
				blancs.put(i, Integer.MAX_VALUE / 100);
			}
			blancs.remove(depart[itDepart]);
			blancs.put(depart[itDepart], 0);

			// Initialisationde la map des previous
			for (Integer i = 0; i < matriceDuGraphe[1].length; i++) {
				previous.put(i, i);
			}

			// Début de l'Algorithme connus
			while (!blancs.isEmpty()) {

				// On cherche le noeud le moins loin
				setblancs = blancs.entrySet();
				it = setblancs.iterator();
				e = it.next();
				Integer min = e.getValue();
				Integer noeudMin = e.getKey();
				while (it.hasNext()) {
					e = it.next();
					if (e.getValue() < min) {
						min = e.getValue();
						noeudMin = e.getKey();

					}
				}

				// On enlève le noeud le moins loins en noire
				blancs.remove(noeudMin);
				noires.put(noeudMin, min);
				curentNoeud = noeudMin;

				blancsiteration = new HashMap<>(blancs);
				setblancs = blancsiteration.entrySet();
				it = setblancs.iterator();

				while (it.hasNext()) {

					e = it.next();
					if ((e.getValue() > (min + matriceDuGraphe[curentNoeud][e.getKey()]))
							&& (matriceDuGraphe[curentNoeud][e.getKey()] > 0)) {
						blancs.put(e.getKey(), (min + matriceDuGraphe[curentNoeud][e.getKey()]));
						previous.put(e.getKey(), curentNoeud);
					}
				}
			}

			blancsiteration = new HashMap<>(blancs);

			AllNoires.put(depart[itDepart], new HashMap<>(noires));
			AllPrevious.put(depart[itDepart], new HashMap<>(previous));

		}
	}

	public void modificationOrdreTournee(int place1, int place2) {
		this.retrouverLivraisonDansTournee(place1);
		this.retrouverLivraisonDansTournee(place2);
	}

	private Livraison retrouverLivraisonDansTournee(int place1) {

		return this.livraisons.get(this.tournee.getTrajets().get((place1 + 1)).getArrive());

	}

	/**
	 * 
	 * @param place1
	 * @param place2
	 */
	public void reordonnerLivraisonTournee(int place1, int place2) {
		if ((place1 + 1) != place2) {
			Livraison livraison1 = new Livraison(this.gestionnaire.getLivraisonTournee(place1));
			Livraison livraison2 = null;
			if (place2 <= (this.livraisons.size() - 1)) {
				livraison2 = new Livraison(this.gestionnaire.getLivraisonTournee(place2));
			}
			Noeud suivant1 = null;
			if (place1 == (this.livraisons.size() - 1)) {
				suivant1 = this.entrepot.getNoeud();
			} else {
				suivant1 = this.gestionnaire.getLivraisonTournee(place1 + 1).getNoeud();
			}
			Noeud precedent1 = this.entrepot.getNoeud();
			if (place1 != 0) {
				precedent1 = this.gestionnaire.getLivraisonTournee(place1 - 1).getNoeud();
			}
			Noeud precedent2 = this.entrepot.getNoeud();
			if (place2 != 0) {
				precedent2 = this.gestionnaire.getLivraisonTournee(place2 - 1).getNoeud();
			}
			this.suppressionLivraisonTournee(livraison1, precedent1, suivant1);
			if (place2 > this.livraisons.size()) {
				this.ajouterLivraisonTournee(livraison1, precedent2, this.entrepot.getNoeud());
			} else {
				this.ajouterLivraisonTournee(livraison1, precedent2, livraison2.getNoeud());
			}
		}
	}

	public void suppressionLivraisonTournee(Livraison aSuprimer, Noeud precedent, Noeud suivant) {
		Noeud arrive = precedent;
		Noeud depart = suivant;

		this.supprimerLivraison(aSuprimer);
		this.tableauDesId = new int[this.noeuds.size()];
		this.remplirTableauDesID(this.tableauDesId);

		Integer idLivraison[];
		idLivraison = new Integer[2];
		idLivraison[1] = this.numDansTableau(depart.getId());
		idLivraison[0] = this.numDansTableau(arrive.getId());

		HashMap<Integer, HashMap<Integer, Integer>> AllNoires = new HashMap<>();
		HashMap<Integer, HashMap<Integer, Integer>> AllPrevious = new HashMap<>();
		/**
		 * On calcul les plus court chemin entre toute les livraisons
		 */
		Dijkstra(idLivraison, AllNoires, AllPrevious);

		List<Integer> idTrajetPrevu = this.ConstructionListdesAdressPourTrajet(idLivraison[0], idLivraison[1],
				AllPrevious.get(idLivraison[0]));
		Trajet trajetPrevu = this.ConstructionTrajet(idTrajetPrevu);

		this.SuppresionTrajetsARemplacerEtInsertionNouveauTrajetDansTournee(trajetPrevu);
	}

	private void SuppresionTrajetsARemplacerEtInsertionNouveauTrajetDansTournee(Trajet trajetPrevu) {
		List<Trajet> listTrajetTourneeCopie = new ArrayList<>(this.tournee.getTrajets());
		ListIterator<Trajet> itListTrajetTourneeCopie = listTrajetTourneeCopie.listIterator();

		List<Trajet> futurTrajetTournee = new ArrayList<>();
		Trajet myTrajet = new Trajet(listTrajetTourneeCopie.get(0).getDepart(),
				listTrajetTourneeCopie.get(0).getArrive(), listTrajetTourneeCopie.get(0).getTroncons());
		while (itListTrajetTourneeCopie.hasNext()) {

			myTrajet = itListTrajetTourneeCopie.next();
			if ((myTrajet.getDepart().equals(trajetPrevu.getDepart()))) {
				futurTrajetTournee.add(trajetPrevu);
			} else if (!(myTrajet.getDepart().equals(trajetPrevu.getDepart())
					|| myTrajet.getArrive().equals(trajetPrevu.getArrive()))) {
				futurTrajetTournee.add(myTrajet);
			}
		}
		this.miseAJourHeureDePassageLivraison(futurTrajetTournee);
		this.tournee = new Tournee(this.entrepot, this.livraisons, futurTrajetTournee);
	}

	private void supprimerLivraison(Livraison aSuprimer) {
		if (aSuprimer != null) {
			this.livraisons.remove(aSuprimer.getNoeud());
			this.tournee.removeLivraisonTournee(aSuprimer.getNoeud().getId());
		}
	}

	public void ajouterLivraisonTournee(Livraison aAjouter, Noeud precedent, Noeud suivant) {
		this.ajouterLivraison(aAjouter);
		this.tableauDesId = new int[this.noeuds.size()];
		this.remplirTableauDesID(this.tableauDesId);

		Integer depart[];
		depart = new Integer[3];
		this.remplirTableauDepartPourAjout(depart, precedent, aAjouter, suivant);

		HashMap<Integer, HashMap<Integer, Integer>> AllNoires = new HashMap<>();
		HashMap<Integer, HashMap<Integer, Integer>> AllPrevious = new HashMap<>();
		/**
		 * On calcul les plus court chemin entre toute les livraisons
		 */
		Dijkstra(depart, AllNoires, AllPrevious);
		// ATTENTION IL FAUT PARTIR DE LA FIN CHAQUE FOIS
		List<Integer> idTrajetPrevu1 = this.ConstructionListdesAdressPourTrajet(depart[0], depart[1],
				AllPrevious.get(depart[0]));
		List<Integer> idTrajetPrevu2 = this.ConstructionListdesAdressPourTrajet(depart[1], depart[2],
				AllPrevious.get(depart[1]));

		Trajet trajetPrevu1 = this.ConstructionTrajet(idTrajetPrevu1);
		Trajet trajetPrevu2 = this.ConstructionTrajet(idTrajetPrevu2);

		this.suppressionTrajetARemplacerEtInsertionNouveauxTrajetsDansTournee(trajetPrevu1, trajetPrevu2);
	}

	private void suppressionTrajetARemplacerEtInsertionNouveauxTrajetsDansTournee(Trajet trajet1, Trajet trajet2) {
		List<Trajet> listTrajetTourneeCopie = new ArrayList<>(this.tournee.getTrajets());
		ListIterator<Trajet> itListTrajetTourneeCopie = listTrajetTourneeCopie.listIterator();
		List<Trajet> futurTrajetTournee = new ArrayList<>();
		Trajet myTrajet = new Trajet(listTrajetTourneeCopie.get(0).getDepart(),
				listTrajetTourneeCopie.get(0).getArrive(), listTrajetTourneeCopie.get(0).getTroncons());
		while (itListTrajetTourneeCopie.hasNext()) {
			myTrajet = itListTrajetTourneeCopie.next();
			if (myTrajet.getDepart().getId() == trajet1.getDepart().getId()) {
				futurTrajetTournee.add(trajet1);
				futurTrajetTournee.add(trajet2);
			} else {
				futurTrajetTournee.add(myTrajet);
			}
		}
		this.miseAJourHeureDePassageLivraison(futurTrajetTournee);

		this.tournee = new Tournee(this.entrepot, this.livraisons, futurTrajetTournee);
	}

	private void miseAJourHeureDePassageLivraison(List<Trajet> futurTrajetTournee) {
		int coutVus = this.entrepot.getHoraireDepart().getHoraireEnSecondes();

		for (Trajet trajet : futurTrajetTournee) {
			coutVus += trajet.getTemps();
			if (!trajet.getArrive().equals(this.entrepot.getNoeud())) {
				this.livraisons.get(trajet.getArrive().getId()).setHeureArrive(new Horaire(coutVus));
				if (coutVus < this.livraisons.get(trajet.getArrive().getId()).getDebutPlage().getHoraireEnSecondes()) {
					coutVus = this.livraisons.get(trajet.getArrive().getId()).getDebutPlage().getHoraireEnSecondes();
				}
				coutVus += this.livraisons.get(trajet.getArrive().getId()).getDuree();
				this.livraisons.get(trajet.getArrive().getId()).setHeureDepart(new Horaire(coutVus));

			}
		}
		this.entrepot.setHoraireArrive(new Horaire(coutVus));
	}

	private List<Integer> ConstructionListdesAdressPourTrajet(Integer depart, Integer arrivee,
			HashMap<Integer, Integer> previous) {
		List<Integer> listeIdTrajet = new ArrayList<>();

		Integer noeudCourant = this.tableauDesId[arrivee]; // Comme on travaille
															// avec des arbres
															// de couvrance
															// minimum on fait
															// le chemin Ã 
															// l'envers
		while (!previous.get(noeudCourant).equals(noeudCourant)) {
			listeIdTrajet.add(this.tableauDesId[noeudCourant]);
			noeudCourant = previous.get(noeudCourant);

		}

		listeIdTrajet.add(this.tableauDesId[depart]);
		Collections.reverse(listeIdTrajet);
		return listeIdTrajet;
	}

	private Trajet ConstructionTrajet(List<Integer> idTrajetPrevu) {
		List<Troncon> tronconsTrajet1 = new ArrayList<>();
		for (Integer i = 0; i < (idTrajetPrevu.size() - 1); i++) {
			tronconsTrajet1.add(this.troncons.get(new Pair<>(this.noeuds.get(this.tableauDesId[idTrajetPrevu.get(i)]),
					this.noeuds.get(this.tableauDesId[idTrajetPrevu.get(i + 1)]))));
		}
		Trajet trajetPrevu = new Trajet(tronconsTrajet1.get(0).getOrigine(),
				tronconsTrajet1.get(tronconsTrajet1.size() - 1).getDestination(), tronconsTrajet1);
		return trajetPrevu;
	}

	private void remplirTableauDepartPourAjout(Integer[] depart, Noeud precedent, Livraison aAjouter, Noeud suivant) {
		depart[0] = this.numDansTableau(precedent.getId());
		depart[1] = this.numDansTableau(aAjouter.getNoeud().getId());
		depart[2] = this.numDansTableau(suivant.getId());
	}

	/**
	 * Ajoute un noeud au plan
	 */
	public void ajouterNoeud(Noeud n) {
		if (n != null) {
			this.noeuds.put(n.getId(), n);
		}
	}

	/**
	 * Ajoute un tronçon au plan
	 */
	public void ajouterTroncon(Troncon t) {
		if (t != null) {
			this.troncons.put(Pair.create(t.getOrigine(), t.getDestination()), t);
		}
	}

	/**
	 * Ajoute une livraison au plan
	 */
	public void ajouterLivraison(Livraison l) {
		if (l != null) {
			this.livraisons.put(l.getNoeud().getId(), l);
		}
	}

	/**
	 * Ajoute l'entrepot au plan
	 */
	public void ajouterEntrepot(Entrepot e) {
		if (e != null) {
			this.entrepot = e;
		}
	}

	public Noeud getNoeud(Integer idNoeud) {
		return this.noeuds.get(idNoeud);
	}

	public Troncon getTroncon(Integer idOrigine, Integer idDestination) {
		return this.troncons.get(Pair.create(idOrigine, idDestination));
	}

	public Map<Integer, Noeud> getNoeuds() {
		return this.noeuds;
	}

	public void setNoeuds(Map<Integer, Noeud> noeuds) {
		this.noeuds = noeuds;
	}

	public Map<Pair<Noeud, Noeud>, Troncon> getTroncons() {
		return this.troncons;
	}

	public void setTroncons(Map<Pair<Noeud, Noeud>, Troncon> troncons) {
		this.troncons = troncons;
	}

	public Map<Integer, Livraison> getLivraisons() {
		return this.livraisons;
	}

	public void setLivraisons(Map<Integer, Livraison> livraisons) {
		this.livraisons = livraisons;
	}

	public void resetLivraisons() {
		this.livraisons = new HashMap<>();
	}

	public Entrepot getEntrepot() {
		return this.entrepot;
	}

	public void setEntrepot(Entrepot entrepot) {
		this.entrepot = entrepot;
	}

	public Tournee getTournee() {
		return this.tournee;
	}

	public void effacerNoeuds() {
		this.noeuds.clear();
	}

	public void effacerLivraisons() {
		this.livraisons.clear();
	}

	public void effacerTroncons() {
		this.troncons.clear();
	}

	public void effacerEntrepot() {
		this.entrepot = null;
	}

	public void effacerTournee() {
		this.tournee = null;
	}

	public boolean idExiste(int id) {
		if (this.noeuds.containsKey(id)) {
			return true;
		} else {
			return false;
		}
	}

	public int getTempsMax() {
		return this.tempsMax;
	}

	/**
	 * Génère la feuille de route de la tournée à effectuer
	 * 
	 * @param nomFichier
	 *            Le nom du fichier qui sera écrit
	 */
	public void genererFeuilleDeRoute(String nomFichier) {
		FileWriter fw;
		try {
			fw = new FileWriter(nomFichier, false);
			BufferedWriter output = new BufferedWriter(fw);
			output.write("FEUILLE DE ROUTE\n");

			// Heure de début
			Horaire heureCourante = this.entrepot.getHoraireDepart();
			// Liste des trajets de la tournée
			List<Trajet> trajets = this.tournee.getTrajets();
			for (int i = 0; i < trajets.size(); ++i) {
				if (i == (trajets.size() - 1)) {
					output.write("\n\nRetour à l'entrepot, départ " + heureCourante.getHoraire() + " du noeud "
							+ trajets.get(trajets.size() - 1).getArrive().getId());
				} else {
					output.write("\n\nDepart " + heureCourante.getHoraire() + " du noeud "
							+ trajets.get(i).getDepart().getId());
				}

				// On parcourt les tronçons
				List<Troncon> troncons = trajets.get(i).getTroncons();
				int longueurRue = 0;
				Horaire tempsParcours = new Horaire(0, 0, 0);
				int j = 0;

				while (j < troncons.size()) {
					Troncon tron = troncons.get(j);
					output.write("\n\tPrendre : " + tron.getNomRue() + " pendant ");
					longueurRue += tron.getLongueur();
					tempsParcours.ajouterSeconde(tron.getLongueur() / tron.getVitesse());
					heureCourante.ajouterSeconde(tron.getLongueur() / tron.getVitesse());
					while ((j < (troncons.size() - 1)) && tron.getNomRue().equals(troncons.get(j + 1).getNomRue())) {
						longueurRue += troncons.get(j + 1).getLongueur();
						tempsParcours
								.ajouterSeconde(troncons.get(j + 1).getLongueur() / troncons.get(j + 1).getVitesse());
						heureCourante
								.ajouterSeconde(troncons.get(j + 1).getLongueur() / troncons.get(j + 1).getVitesse());
						++j;
						continue;
					}

					// Affichage pour la longueur du tronçon
					String longueurStr;
					String tempsParcourStr;
					longueurStr = (longueurRue / 10) + "m";
					if (tempsParcours.getHoraireEnMinutes() > 1) {
						tempsParcourStr = "  (" + tempsParcours.getHoraireEnMinutes() + "min)";
					} else {
						tempsParcourStr = "  (>1min)";
					}
					output.write(longueurStr + tempsParcourStr);

					longueurRue = 0;
					tempsParcours = new Horaire(0, 0, 0);
					++j;
				}

				// La livraison à faire
				Livraison l = this.livraisons.get(trajets.get(i).getArrive().getId());
				if (i == (trajets.size() - 1)) {
					output.write("\nArrivée à l'entrepot : " + heureCourante.getHoraire());
				} else {
					if (l.getDebutPlage().getHoraireEnMinutes() > heureCourante.getHoraireEnMinutes()) {
						output.write("\n\tAttente de "
								+ (l.getDebutPlage().getHoraireEnMinutes() - heureCourante.getHoraireEnMinutes())
								+ "min avant livraison");
						heureCourante = new Horaire(l.getDebutPlage());
					}
					output.write("\nArrivée " + heureCourante.getHoraire() + " au noeud " + l.getNoeud().getId());
					// Affichage pour la durée estimée de la livraison
					Horaire horaireTemp = new Horaire(0, 0, 0);
					horaireTemp.ajouterSeconde(l.getDuree());
					output.write("\n\tDurée de livraison estimée : " + horaireTemp.getHoraireEnMinutes() + "min");
					// On incrémente l'heure
					heureCourante.ajouterSeconde(l.getDuree());
				}

				output.flush();
			}

			output.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
