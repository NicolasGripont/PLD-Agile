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

import javafx.application.Platform;
import utility.Pair;

/**
 * Classe modélisant le plan
 */
public class Plan {
	/**
	 * Map représentant l'ensemble des noeuds du plan
	 * Le noeud est accessible par son id
	 */
	private Map<Integer, Noeud> noeuds;
	
	/**
	 * Map représentant l'ensemble des tronçons du plan
	 * Le tronc est accessible par son noeud de départ et d'arrivée
	 */
	private Map<Pair<Noeud, Noeud>, Troncon> troncons;
	
	/**
	 * Map représentant l'ensemble des livraisons de la tournée à effectuer
	 * La livraison est accessible par le noeud modélisant sa position
	 */
	private Map<Integer, Livraison> livraisons;
	
	/**
	 * Entrepot de départ de la tournée à effectuer
	 */
	private Entrepot entrepot;
	
	private int tableauDesId[];
	
	/**
	 * Matrice du graphe, modélisant le coût du troncon entre chaque noeud, un 0 indique qu'il n'y a pas de lien direct
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
	
	/**
	 * Thread utilisé pour calculer la tournée
	 */
	private Thread threadCalcul;
	
	/**
	 * Thread utilisé pour la répresentation graphique de la solution calculée
	 */
	private Thread threadConstructionTournee;
	
	private Gestionnaire gestionnaire;
	
	/**
	 * Temps maximum durant lequel l'application va tourner en millisecondes
	 */
	private int tempsMax = 20000;
	
	/**
	 * Constructeur de classe
	 */
	public Plan() {
		noeuds = new HashMap<Integer, Noeud>();
		troncons = new HashMap<Pair<Noeud, Noeud>, Troncon> ();
		livraisons = new HashMap<Integer, Livraison>();
	}
	
	/**
	 * Constructeur de classe
	 */
	public Plan(Gestionnaire gestionnaire) {
		this.gestionnaire = gestionnaire;
		noeuds = new HashMap<Integer, Noeud>();
		troncons = new HashMap<Pair<Noeud, Noeud>, Troncon> ();
		livraisons = new HashMap<Integer, Livraison>();
	}
	
	/**
	 * Permet de savoir si la solution trouvée est optimale (toutes les possibilités ont été essayées), ou non (fin du temps)
	 */
	public boolean estSolutionOptimale() {
		return !tsp.getTempsLimiteAtteint();
	}
	
	/**
	 * Stop le calcul de la tournée et la construction de la solution
	 */
	public void stopperCalculTournee() {
		if(threadCalcul != null && threadCalcul.isAlive() && threadCalcul.isInterrupted() == false) {
			threadCalcul.interrupt();
			System.out.println("Calcul stoppé");
		}
		if(threadConstructionTournee != null && threadConstructionTournee.isAlive() && threadConstructionTournee.isInterrupted() == false) {
			threadConstructionTournee.interrupt();
			System.out.println("Construction stoppée");
		}
	}
	
	/**
	 * @param id :  id dont on cherche la place dans le table
	 * @return l'indice dans le tableau des id à laquel on trouve l'id mis en paramètre
	 */
	public int numDansTableau(int id)
	{
		for(int i = 0 ; i <tableauDesId.length; i++)
		{
			if(tableauDesId[i]==id)
			{
				return i;
			}
		}
		return -1;
	}
	
	/**
	 * Calcule la tournée
	 */
	public void calculerTournee() {
		int nbDeLivraison = livraisons.size();
    	
    	
		tableauDesId = new int [noeuds.size()];
    	
    	/**
    	 * On remplie le tableau des id 
    	 */
    	remplirTableauDesID(tableauDesId);
    	
    	/**
    	 * numero dans tableau des id des livraisons
    	 */
    	Integer depart[];
    	depart = new Integer[nbDeLivraison+1]; 
		
    	/**
    	 * duree des livraison
    	 */
		int duree[];
		duree = new int[nbDeLivraison+1];
		
		/**
		 *  plage horaire pour chaque livraison
		 */
		int[][] plages_horaire;
		plages_horaire = new int[2][nbDeLivraison+1];
		
		/**
		 * On place les informations de l'entrepot et des livraisons dans les deux tableaux
		*/
		remplirTableauDepEtDur(depart, duree, plages_horaire);
		
		/**
		 * On créé la matrice qui représent le graphe avec les couts des arcs
		 */
    	matriceDuGraphe= new Integer[noeuds.size()][noeuds.size()] ; 
    	
    	/**
    	 * On remplie la matrice qui modelise le graphe
    	 */
    	remplirMatriceDuGraphe();
    	
    	/**
    	 * va contenir les les couts du graphe simplifié qui aura pour sommet les livraisons 
    	 * et pour cout la somme des couts des tronçons qui seront sur les plus courts chemins entre les livraisons
    	 * origine - destination - cout
    	 */
    	HashMap< Integer, HashMap<Integer, Integer>> AllNoires = new HashMap<>(); //Sera Ã©galement placÃ© en paramÃ¨tre
        
    	
    	HashMap< Integer, HashMap<Integer, Integer>> AllPrevious = new HashMap<>(); //Sera Ã©galement placÃ© en paramÃ¨tre
    	
    	
    	/**
    	 * On calcul les plus court chemin entre toute les livraisons
    	 */
    	Dijkstra(depart, AllNoires, AllPrevious);
   
		
		/**
		 * On créé la matrice de cout pour le TSP
		 */
		int cout[][]= new int [depart.length][depart.length];
		
		/**
		 * On remplie la matrice utilisé par la TSP a partir des calculs de Dijkstra
		 */
		constructionMatTsp(cout, depart, AllNoires);
		
		tsp = new TSP4();

		threadCalcul = new Thread() {
			public void run() {
				
				/**
				 * On lance l'algorithme pour rechercher la meilleur tournée
				 */
				tsp.chercheSolution(tempsMax, depart.length , cout, duree, plages_horaire);
				
				/**
				 * Un fois les calcul réalisé, on créé les objets qui vont être utiliseé par la couche supérieur
				 */
				constructionTournee(depart, AllNoires, AllPrevious);
				if(gestionnaire != null) {
					Platform.runLater(() -> gestionnaire.tourneeCalculee());
				}
			}
		};
		threadCalcul.setDaemon(true);
		threadCalcul.start();
		threadConstructionTournee = new Thread() {
			public void run() {
				while(threadCalcul.isInterrupted() == false) {
					try {
						System.out.println("Construction solution");
						///ISOLATION !!!
						Thread.sleep(3000);
						constructionTournee(depart, AllNoires, AllPrevious);
					} catch (InterruptedException e) {
						return;
					}
				}
			}
		};
		threadConstructionTournee.setDaemon(true);
		threadConstructionTournee.start();
    }
	
	/**
	 * Construit l'objet Tournée modélisant la solution pour la tournée à effectuer
	 */
	private void constructionTournee(Integer depart[], HashMap< Integer, HashMap<Integer, Integer>> AllNoires, HashMap< Integer, HashMap<Integer, Integer>> AllPrevious) {
		List<Integer> futurTourne = new ArrayList<Integer>();
		HashMap<Integer, Integer> previous;
		
		Integer noeudCourant = depart[tsp.getMeilleureSolution(0)]; //Comme on travaille avec des arbres de couvrance minimum on fait le chemin Ã  l'envers
		for(int i = depart.length-1 ; i >=0 ; i--) {
			previous = new HashMap<>(AllPrevious.get(depart[tsp.getMeilleureSolution(i)]));
			while(previous.get(noeudCourant)!=noeudCourant) {
				futurTourne.add(noeudCourant);
			    noeudCourant=previous.get(noeudCourant);
			}
		}
		
		LinkedList<Integer> ordreTourneID = new LinkedList<Integer>();
		
		for(int j = 0 ; j< depart.length ; j++ ) {
			ordreTourneID.add(tableauDesId[depart[tsp.getMeilleureSolution(j)]]);
		}
		//on ajoute l'entrepot à la fin de la tournee
		ordreTourneID.add(ordreTourneID.getFirst());
		//on supprime l'entrepot du début de la tournee
		ordreTourneID.removeFirst();

		
		//TODO tableau des idée
		
		
		//LinkedList<Integer> ordreT = new LinkedList<Integer>()

		futurTourne.add(depart[tsp.getMeilleureSolution(0)]);
	      Collections.reverse(futurTourne);
	      List<Integer> FT = new ArrayList<Integer>(futurTourne);
	      futurTourne.clear();
	      ListIterator<Integer> itFT = FT.listIterator();
	      Integer lastAdded=-1;
	      Integer myInt;
	      while(itFT.hasNext()){
	    	  myInt = itFT.next();
		      if(!lastAdded.equals(myInt)) {
		      futurTourne.add(tableauDesId[myInt]);
		      }
		      lastAdded=myInt;
	      } 

	    //Puis retrouver les tronçons en recupérant les id des noeuds dans tableauDesId
	    //Puis on constrit tournee
	     
			// Construction de la Tournee
	      	Set<Livraison> dejaVisites = new HashSet<>();
			List<Trajet> trajetsPrevus = new ArrayList<>();
			List<Troncon> tronconsTrajet = new ArrayList<>();
			for (Integer i = 0; i < futurTourne.size() - 1; i++) {
				tronconsTrajet.add(
						troncons.get(new Pair(noeuds.get(futurTourne.get(i)), noeuds.get(futurTourne.get(i + 1)))));
				//(Si le neoud suivant est une livraison ET si la livraison n'a pas deja etait ajoutée ET si le noeud correspond à la future livraison à faire)  
				//OU
				//(si le noeud suivant est l'entrepot ET que c'est le dernier noeud a visiter)
				if ( (livraisons.get(futurTourne.get(i + 1)) != null 
						&& !dejaVisites.contains(livraisons.get(futurTourne.get(i + 1)))
						&& livraisons.get(futurTourne.get(i + 1)) == livraisons.get(ordreTourneID.getFirst()) )
						|| (entrepot.getNoeud().equals(noeuds.get(futurTourne.get(i + 1)))) && i==futurTourne.size() - 2) {
					ordreTourneID.removeFirst();
					if (!tronconsTrajet.isEmpty()) {
						trajetsPrevus.add(new Trajet(tronconsTrajet.get(0).getOrigine(),
								tronconsTrajet.get(tronconsTrajet.size() - 1).getDestination(), tronconsTrajet));
						tronconsTrajet = new ArrayList<>();
						dejaVisites.add(livraisons.get(futurTourne.get(i + 1)));
					}
				} 
			}
			this.tournee = new Tournee(entrepot,livraisons,trajetsPrevus);
	}
	
	/**
	 * Construit la matrice des coûts pour le TSP
	 * @param cout matrice des cout des plus court chemin entre les livraisons
	 * @param depart vas simuller les noeuds de livraisons 
	 * @param AllNoires vas contenir les noeud noires du gra
	 */
	 private void constructionMatTsp(int[][] cout, Integer[] depart,
			HashMap<Integer, HashMap<Integer, Integer>> AllNoires) {
    	for(int u = 0;u<depart.length; u++)
		{
			for(int v = 0;v<depart.length; v++)
			{
				cout[u][v]=(AllNoires.get(depart[u])).get(depart[v]);
			}
		}
	}

	private void remplirTableauDesID(int[] tableauDesId2) {
    	Set<Entry<Integer , Noeud>> setnoeuds;
		Iterator<Entry<Integer , Noeud>> itnoeuds;
		Entry<Integer , Noeud> enoeuds;
    	int itid=0;
    	setnoeuds = noeuds.entrySet();
    	itnoeuds = setnoeuds.iterator();
		while(itnoeuds.hasNext())
		{
			enoeuds = itnoeuds.next();
			tableauDesId[itid] = (int)enoeuds.getKey();
		    itid++;
		}
	}

	 private void remplirMatriceDuGraphe() {
    	for(Integer i = 0 ; i <matriceDuGraphe.length ;i++)
        {
            for(Integer j = 0 ; j < matriceDuGraphe.length ; j++)
            {
                if(i!=j)
                {
                	Pair<Noeud, Noeud> key = new Pair<>(noeuds.get(tableauDesId[i]),noeuds.get(tableauDesId[j]));
                	if(troncons.get(key) != null)
                	{
                	matriceDuGraphe[i][j]=(int)((troncons.get(key).getLongueur())/(troncons.get(key).getVitesse()));
                	}
                	else
                	{
                		matriceDuGraphe[i][j]=-1; // a remplacer par un max
                	}
                	
                }
                else
                {
                    matriceDuGraphe[i][j]=0;
                }
            }
    
        }		
	}

	 private void remplirTableauDepEtDur(Integer[] depart, int[] duree, int[][] plages_horaire) {
	    	depart[0]=(Integer)(numDansTableau(entrepot.getNoeud().getId())); //le depart 0 sera l'entrepot
			duree[0]=0;
			plages_horaire[0][0]=0;
			plages_horaire[1][0]=Integer.MAX_VALUE;
			
			Set<Entry<Integer, Livraison>> setlivraisons;
			Iterator<Entry<Integer, Livraison>> itlivraisons;
			Entry<Integer, Livraison> elivraisons;
			
			int idep=1;
			setlivraisons = livraisons.entrySet();
			itlivraisons = setlivraisons.iterator();
			/**
			 * Si il n'y a pas de plages horaire indiqué, on prends 0 comme début de plage et Integer.MAX_VALUE
			 */
			while(itlivraisons.hasNext())
			{
				elivraisons = itlivraisons.next();
			    depart[idep]=(Integer)numDansTableau(elivraisons.getKey());
			    duree[idep]=(elivraisons.getValue().getDuree());
			   
			    if(!elivraisons.getValue().getDebutPlage().equals(new Horaire(0,0,0))){
			    	plages_horaire[0][idep]=elivraisons.getValue().getDebutPlage().getHoraireEnMinutes()-entrepot.getHoraireDepart().getHoraireEnMinutes();
			    }
			    else {
			    	plages_horaire[0][idep]=0;
			    }
			    plages_horaire[0][idep]*=60;
			    if(!elivraisons.getValue().getFinPlage().equals(new Horaire(0,0,0))){
			    	plages_horaire[1][idep]=elivraisons.getValue().getFinPlage().getHoraireEnMinutes()-entrepot.getHoraireDepart().getHoraireEnMinutes();
			    	plages_horaire[1][idep]*=60;
			    }
			    else {
			    	plages_horaire[1][idep]=Integer.MAX_VALUE;

			    }
			    

			    idep++;
			}
			
			
		}

	private static void Dijkstra(Integer depart[],HashMap< Integer, HashMap<Integer, Integer>> AllNoires ,HashMap< Integer, HashMap<Integer, Integer>> AllPrevious)
    {
    	
    	for(int itDepart = 0; itDepart<depart.length ; itDepart++)
    	{
    		Integer curentNoeud = depart[itDepart];
        
       
    		HashMap<Integer, Integer> blancs = new HashMap<>();
    		HashMap<Integer, Integer> noires = new HashMap<>();
    		HashMap<Integer, Integer> previous = new HashMap<>();
    		HashMap<Integer, Integer> blancsiteration = new HashMap<>();
    		Set<Entry<Integer, Integer>> setblancs;
    		Iterator<Entry<Integer, Integer>> it;
    		Entry<Integer, Integer> e;
        
	        //Initialisation de la map des blancs
	        for(Integer i = 0; i<matriceDuGraphe[1].length;i++)
	        {
	        	blancs.put(i,Integer.MAX_VALUE/100);
	        }
	        blancs.remove(depart[itDepart]);
	        blancs.put(depart[itDepart], 0);
	
	        
	       
	        //Initialisationde la map des previous
			for(Integer i = 0; i<matriceDuGraphe[1].length;i++)
	        {   
	            previous.put(i,i);   
	        }
	        
			
	        //Début de l'Algorithme connus
	        while( !blancs.isEmpty())
	        {
	        
	            //On cherche le noeud le moins loin
	            setblancs = blancs.entrySet();
	            it = setblancs.iterator();
	            e = it.next();
	            Integer min = e.getValue();
	            Integer noeudMin = e.getKey();
	            while(it.hasNext())
	            {
	                e = it.next();
	                if(e.getValue()<min)
	                {
	                    min = e.getValue();
	                    noeudMin = e.getKey();
	
	                }
	            }
	            
	            //On enlève le noeud le moins loins en noire
	            blancs.remove(noeudMin);
	            noires.put(noeudMin, min);
	            curentNoeud=noeudMin;
	            
	            
	            blancsiteration= new HashMap<Integer, Integer>(blancs);
	            setblancs = blancsiteration.entrySet();
	            it = setblancs.iterator();
	         
	            while(it.hasNext())
	            {
	            	
	                e = it.next();
	                if((e.getValue()> (min + matriceDuGraphe[curentNoeud][e.getKey()]))&& (matriceDuGraphe[curentNoeud][e.getKey()] >0) )
	                {
	                    blancs.put(e.getKey(), (min + matriceDuGraphe[curentNoeud][e.getKey()]));
	                    previous.put(e.getKey(),curentNoeud);
	                }
	            }
	        }
	        
	        blancsiteration= new HashMap<Integer, Integer>(blancs);
	        
	        AllNoires.put(depart[itDepart], new HashMap<Integer, Integer>(noires));
	        AllPrevious.put(depart[itDepart], new HashMap<Integer, Integer>(previous));
	        
			
       }
    }
	
	public void supressionLivraisonTournee(Livraison aSuprimer, Noeud precedent, Noeud suivant){
		
		Noeud arrive = precedent;
		Noeud depart = suivant;
		
		supprimerLivraison(aSuprimer);

		tableauDesId = new int [noeuds.size()];
    	remplirTableauDesID(tableauDesId);
    	
    	Integer idLivraison[];
    	idLivraison = new Integer[2]; 
    	idLivraison[0]=numDansTableau(depart.getId());
		idLivraison[1]=numDansTableau(arrive.getId());
    	
    	HashMap< Integer, HashMap<Integer, Integer>> AllNoires = new HashMap<>(); //Sera Ã©galement placÃ© en paramÃ¨tre
    	HashMap< Integer, HashMap<Integer, Integer>> AllPrevious = new HashMap<>(); //Sera Ã©galement placÃ© en paramÃ¨tre
    	/**
    	 * On calcul les plus court chemin entre toute les livraisons
    	 */
    	Dijkstra(idLivraison, AllNoires, AllPrevious);
    	
    	List<Integer> idTrajetPrevu = ConstructionListdesAdressPourTrajet(idLivraison[0], idLivraison[1], AllPrevious.get(idLivraison[0]));
    	Trajet trajetPrevu = ConstructionTrajet(idTrajetPrevu);
    	
		SuppresionTrajetsARemplacerEtInsertionNouveauTrajetDansTournee(trajetPrevu);
	}
	
	private void SuppresionTrajetsARemplacerEtInsertionNouveauTrajetDansTournee(Trajet trajetPrevu) {
		List<Trajet> listTrajetTourneeCopie= new ArrayList<Trajet>(tournee.getTrajets());
		ListIterator<Trajet> itListTrajetTourneeCopie = listTrajetTourneeCopie.listIterator();
	      
		List<Trajet> futurTrajetTournee= new ArrayList<Trajet>();
		Trajet myTrajet = new Trajet(listTrajetTourneeCopie.get(0).getDepart(), listTrajetTourneeCopie.get(0).getArrive(), listTrajetTourneeCopie.get(0).getTroncons());
	      while(itListTrajetTourneeCopie.hasNext()){
	    	  myTrajet = itListTrajetTourneeCopie.next();
	    	  
	    	  if((myTrajet.getDepart().equals(trajetPrevu.getDepart())  ) ) {
		    	  futurTrajetTournee.add(trajetPrevu);
		      }
	    	  
		      if(!( myTrajet.getDepart().equals(trajetPrevu.getDepart()) || myTrajet.getArrive().equals(trajetPrevu.getArrive()) ) ) {
		    	  futurTrajetTournee.add(myTrajet);
		      } 
	      } 
	      this.tournee = new Tournee(entrepot,livraisons,futurTrajetTournee);
		
	}

	private void supprimerLivraison(Livraison aSuprimer) {
			if(aSuprimer != null) {
				livraisons.remove(aSuprimer.getNoeud());
				tournee.removeLivraisonTournee(aSuprimer.getNoeud().getId());
			}
		
	}

	public void ajouterLivraisonTournee(Livraison aAjouter, Noeud precedent,  Noeud suivant){
		
		ajouterLivraison(aAjouter);
	
		tableauDesId = new int [noeuds.size()];
    	remplirTableauDesID(tableauDesId);
    	
    	Integer depart[];
    	depart = new Integer[3]; 
		remplirTableauDepartPourAjout(depart, precedent, aAjouter, suivant);
    	
    	HashMap< Integer, HashMap<Integer, Integer>> AllNoires = new HashMap<>(); //Sera Ã©galement placÃ© en paramÃ¨tre
    	HashMap< Integer, HashMap<Integer, Integer>> AllPrevious = new HashMap<>(); //Sera Ã©galement placÃ© en paramÃ¨tre
    	/**
    	 * On calcul les plus court chemin entre toute les livraisons
    	 */
    	Dijkstra(depart, AllNoires, AllPrevious);
		
    	List<Integer> idTrajetPrevu1 = ConstructionListdesAdressPourTrajet(depart[0], depart[1], AllPrevious.get(depart[0]));
    	List<Integer> idTrajetPrevu2 = ConstructionListdesAdressPourTrajet(depart[1], depart[2], AllPrevious.get(depart[1]));

    	Trajet trajetPrevu1 = ConstructionTrajet(idTrajetPrevu1);
		Trajet trajetPrevu2 = ConstructionTrajet(idTrajetPrevu2);
		
		SuppresionTrajetARemplacerEtInsertionNouveauxTrajetsDansTournee(trajetPrevu1, trajetPrevu2);
		
//    	InsertionLivraisonDansTournee(depart, AllPrevious);

		
	}
private void SuppresionTrajetARemplacerEtInsertionNouveauxTrajetsDansTournee( Trajet trajet1, Trajet trajet2) {

		List<Trajet> listTrajetTourneeCopie= new ArrayList<Trajet>(tournee.getTrajets());
		ListIterator<Trajet> itListTrajetTourneeCopie = listTrajetTourneeCopie.listIterator();
	      
		List<Trajet> futurTrajetTournee= new ArrayList<Trajet>();
		Trajet myTrajet = new Trajet(listTrajetTourneeCopie.get(0).getDepart(), listTrajetTourneeCopie.get(0).getArrive(), listTrajetTourneeCopie.get(0).getTroncons());
	      while(itListTrajetTourneeCopie.hasNext()){
	    	  myTrajet = itListTrajetTourneeCopie.next();
		      if(!(myTrajet.getDepart().equals(trajet1.getDepart()) || myTrajet.getArrive().equals(trajet2.getArrive()) ) ) {
		    	  futurTrajetTournee.add(myTrajet);
		      } else {
		    	  futurTrajetTournee.add(trajet1);
		    	  futurTrajetTournee.add(trajet2);
		      }
	      } 
	      this.tournee = new Tournee(entrepot,livraisons,futurTrajetTournee);
	}


	private List<Integer> ConstructionListdesAdressPourTrajet(Integer depart, Integer arrivee,
			HashMap<Integer, Integer> previous) {
		List<Integer> listeIdTrajet = new ArrayList<Integer>();
		
		
		Integer noeudCourant = tableauDesId[arrivee]; //Comme on travaille avec des arbres de couvrance minimum on fait le chemin Ã  l'envers
			while(previous.get(noeudCourant)!=noeudCourant) {
				listeIdTrajet.add(tableauDesId[noeudCourant]);
			    noeudCourant=previous.get(noeudCourant);
			}
			
			listeIdTrajet.add(tableauDesId[depart]);
			return listeIdTrajet;
		
	}


	

	private Trajet ConstructionTrajet(List<Integer> idTrajetPrevu) {
		List<Troncon> tronconsTrajet1 = new ArrayList<>();
		for (Integer i = 0; i < idTrajetPrevu.size() - 1; i++) {
			tronconsTrajet1.add(
					troncons.get(new Pair(noeuds.get(tableauDesId[idTrajetPrevu.get(i)]), noeuds.get(tableauDesId[idTrajetPrevu.get(i + 1)]))));
		}
			Trajet trajetPrevu = new Trajet(tronconsTrajet1.get(0).getOrigine(),
							tronconsTrajet1.get(tronconsTrajet1.size() - 1).getDestination(), tronconsTrajet1);
					
		return trajetPrevu;
	}

	
	
	
	private void remplirTableauDepartPourAjout(Integer[] depart, Noeud precedent, Livraison aAjouter, Noeud suivant) {
		depart[0]=numDansTableau(precedent.getId());
		depart[1]=numDansTableau(aAjouter.getNoeud().getId());
		depart[2]=numDansTableau(aAjouter.getNoeud().getId());
	}

	
	
	/**
	 * Ajoute un noeud au plan
	 */
	public void ajouterNoeud(Noeud n) {
		if(n != null) {
			noeuds.put(n.getId(), n);
		}
	}
	
	/**
	 * Ajoute un tronçon au plan
	 */
	public void ajouterTroncon(Troncon t) {
		if(t != null) {
			troncons.put(Pair.create(t.getOrigine(), t.getDestination()) , t);
		}
	}
	
	/**
	 * Ajoute une livraison au plan
	 */
	public void ajouterLivraison(Livraison l) {
		if(l != null) {
			livraisons.put(l.getNoeud().getId(), l);
		}
	}
	
	/**
	 * Ajoute l'entrepot au plan
	 */
	public void ajouterEntrepot(Entrepot e) {
		if(e != null) {
			entrepot = e;
		}
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
	
	public Map<Integer, Livraison> getLivraisons() {
		return livraisons;
	}

	public void setLivraisons(Map<Integer, Livraison> livraisons) {
		this.livraisons = livraisons;
	}
	
	public void resetLivraisons() {
		this.livraisons = new HashMap<Integer, Livraison>();
	}
	
	public Entrepot getEntrepot() {
		return entrepot;
	}
	
	public void setEntrepot(Entrepot entrepot) {
		this.entrepot = entrepot;
	}

	public Tournee getTournee() {
		return tournee;
	}
	
	public void effacerNoeuds()
	{
		noeuds.clear();
	}
	
	public void effacerLivraisons()
	{
		livraisons.clear();
	}
	
	public void effacerTroncons()
	{
		troncons.clear();
	}
	
	public void effacerEntrepot()
	{
		entrepot = null;
	}
		
	public void effacerTournee() {
		this.tournee = null;
	}
	
	public boolean idExiste(int id)
	{
		if(noeuds.containsKey(id)){return true;}
		else{return false;}
	}
	
	public int getTempsMax() {
		return tempsMax;
	}
	
	/**
	 * Génère la feuille de route de la tournée à effectuer
	 * @param link
	 * 		Le nom du fichier qui sera écrit
	 */
	public void genererFeuilleDeRoute(String link) {
		FileWriter fw;
		try {
			fw = new FileWriter(link, false);
			BufferedWriter output = new BufferedWriter(fw);
			output.write("FEUILLE DE ROUTE\n");
			
			//Heure de début
			Horaire heureCourante = entrepot.getHoraireDepart();
			//Liste des trajets de la tournée
			List<Trajet> trajets = tournee.getTrajets();
			for(int i = 0; i < trajets.size(); ++i) {
				if(i == trajets.size()-1) {
					output.write("\n\nRetour à l'entrepot, départ " + heureCourante.getHoraire() +" du noeud " + trajets.get(trajets.size()-1).getArrive().getId());
				}
				else {
					output.write("\n\nDepart "+ heureCourante.getHoraire() +" du noeud " + trajets.get(i).getDepart().getId());
				}
				
				//On parcourt les tronçons
				List<Troncon> troncons = trajets.get(i).getTroncons();
				int longueurRue = 0;
				int j = 0;
				while(j < troncons.size()) {
					Troncon tron = troncons.get(j);
					output.write("\n\tPrendre : " + tron.getNomRue() + " pendant ");
					longueurRue += tron.getLongueur();
					heureCourante.ajouterSeconde(tron.getLongueur()/tron.getVitesse());
					while(j < troncons.size()-1 && tron.getNomRue().equals(troncons.get(j+1).getNomRue())) {
						longueurRue += troncons.get(j+1).getLongueur();
						heureCourante.ajouterSeconde(troncons.get(j+1).getLongueur()/troncons.get(j+1).getVitesse());
						++j;
						continue;
					}
					
					//Affichage pour la longueur du tronçon
					String longueurStr;
					if(longueurRue > 999){
						longueurStr = longueurRue/1000 + "km";
					}
					else {
						longueurStr = longueurRue + "m";
					}
					output.write(longueurStr);

					longueurRue = 0;
					++j;
				}
				
				//La livraison à faire
				Livraison l = livraisons.get(trajets.get(i).getArrive());
				if(i == trajets.size()-1) {
					output.write("\nArrivée à l'entrepot : " + heureCourante.getHoraire());
				}
				else {
					output.write("\nArrivée "+ heureCourante.getHoraire() +" au noeud " + l.getNoeud().getId());
					//Affichage pour la durée estimée de la livraison
					Horaire horaireTemp = new Horaire(0,0,0);
					horaireTemp.ajouterSeconde(l.getDuree());
					output.write("\n\tDurée de livraison estimée : " + horaireTemp.getHoraireEnMinutes() + "min");
					//On incrémente l'heure
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
