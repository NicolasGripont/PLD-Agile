package modeles;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import tsp.TSP;
import utility.Pair;

public class Plan {
	private Map<Integer, Noeud> noeuds;
	private Map<Pair<Noeud, Noeud>, Troncon> troncons;
	private Map<Noeud, Livraison> livraisons;
	private Entrepot entrepot;
	private int tableauDesId[];
	private TSP tsp;
	private Tournee tournee;
	
	public Plan() {
		noeuds = new HashMap<Integer, Noeud>();
		troncons = new HashMap<Pair<Noeud, Noeud>, Troncon> ();
		livraisons = new HashMap<Noeud, Livraison>();
	}
	
	public boolean estSolutionOptimale() {
		return !tsp.getTempsLimiteAtteint();
	}
	
	public void stopperCalculTournee() {
		
	}
	
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
	
	public boolean calculerTournee() {
    	
    	int nbDeLivraison = livraisons.size();
    	
    	tableauDesId = new int [noeuds.size()];
    	
    	
    	//On remplie le tableau des id 
    	remplirTableauDesID(tableauDesId);
    	
    	
    	Integer depart[]; // numero dans tableau des id des livraisons
    	depart = new Integer[nbDeLivraison+1]; 
		
		int duree[];
		duree = new int[nbDeLivraison+1];
		
		//On place les informations de l'entrepot et des livraisons dans les deux tableaux
		remplirTableauDepEtDur(depart, duree);
		
		
		Integer matriceDuGraphe[][];
    	matriceDuGraphe= new Integer[noeuds.size()][noeuds.size()] ; 
    	
    	//On remplie la matrice qui modelise le graphe
    	remplirMatriceDuGraphe(matriceDuGraphe);
    	
    	HashMap< Integer, HashMap<Integer, Integer>> AllNoires = new HashMap<>(); //Sera également placé en paramètre
        HashMap< Integer, HashMap<Integer, Integer>> AllPrevious = new HashMap<>(); //Sera également placé en paramètre
    	
    	Dijkstra(depart, matriceDuGraphe, AllNoires, AllPrevious);
   
		
		//Construction de la matrice de cout pour le TSP
		
		int cout[][]= new int [depart.length][depart.length];
		
		//On construti la matrice utilisé par la TSP a partir des Calcul de Dijkstra
		constructionMatTsp(cout, depart, AllNoires);
		
		tsp = new tsp.TSP1();
		tsp.chercheSolution(20000, depart.length , cout, duree);  //le 100000 est le temps max toléré
		if(tsp.getTempsLimiteAtteint())
			return false;
		//Il faut maintenant reupéré le chemin optimal via les get
		
		
		
		List<Integer> futurTourne = new ArrayList<Integer>();
		HashMap<Integer, Integer> previous;
		
		Integer noeudCourant = depart[tsp.getMeilleureSolution(0)]; //Comme on travail avec des arbre de couvrance minimum on fait le chemin Ã  l'envers
		boolean first;
		for(int i = depart.length-1 ; i >=0 ; i--)
		{
			previous = new HashMap<>(AllPrevious.get(depart[tsp.getMeilleureSolution(i)]));
			while(previous.get(noeudCourant)!=noeudCourant)
			{
				futurTourne.add(noeudCourant);
			    noeudCourant=previous.get(noeudCourant);
			}
		}
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
		      futurTourne.add(myInt);
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
				//(Si le neoud suivant est une livraison ET si la livraison n'a pas deja etait ajoutée)  
				//OU si le noeud suivant est l'entrepot
				if ((livraisons.get(noeuds.get(futurTourne.get(i + 1))) != null 
						&& !dejaVisites.contains(livraisons.get(noeuds.get(futurTourne.get(i + 1))))
						|| entrepot.getNoeud().equals(noeuds.get(futurTourne.get(i + 1))))) {
					tronconsTrajet.add(troncons
							.get(new Pair(noeuds.get(futurTourne.get(i)), noeuds.get(futurTourne.get(i + 1)))));
					if (!tronconsTrajet.isEmpty()) {
						trajetsPrevus.add(new Trajet(tronconsTrajet.get(0).getOrigine(),
								tronconsTrajet.get(tronconsTrajet.size() - 1).getDestination(), tronconsTrajet));
						tronconsTrajet = new ArrayList<>();
						dejaVisites.add(livraisons.get(noeuds.get(futurTourne.get(i + 1))));
					}
				} else {
					tronconsTrajet.add(
							troncons.get(new Pair(noeuds.get(futurTourne.get(i)), noeuds.get(futurTourne.get(i + 1)))));
				}
			}
			this.tournee = new Tournee(trajetsPrevus);
			
			return true;
    }
    
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

		private void remplirMatriceDuGraphe(Integer[][] matriceDuGraphe) {
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

		private void remplirTableauDepEtDur(Integer[] depart, int[] duree) {
	    	depart[0]=(Integer)(numDansTableau(entrepot.getNoeud().getId())); //le depart 0 sera l'entrepot
			duree[0]=0;
			
			Set<Entry<Noeud, Livraison>> setlivraisons;
			Iterator<Entry<Noeud, Livraison>> itlivraisons;
			Entry<Noeud, Livraison> elivraisons;
			
			int idep=1;
			setlivraisons = livraisons.entrySet();
			itlivraisons = setlivraisons.iterator();
			while(itlivraisons.hasNext())
			{
				elivraisons = itlivraisons.next();
			    depart[idep]=(Integer)numDansTableau(elivraisons.getKey().getId());
			    duree[idep]=(elivraisons.getValue().getDuree());
			    idep++;
			}
		}

		private static void Dijkstra(Integer depart[], Integer matriceDuGraphe[][] ,HashMap< Integer, HashMap<Integer, Integer>> AllNoires ,HashMap< Integer, HashMap<Integer, Integer>> AllPrevious)
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
	                	//TODO supprimer appel a remove (inutile) car put remplace la vielle valeur
	                	blancs.remove(e.getKey());
	                    blancs.put(e.getKey(), (min + matriceDuGraphe[curentNoeud][e.getKey()]));
	                    previous.remove(e.getKey());
	                    previous.put(e.getKey(),curentNoeud);
	                }
	            }
	        }
	        
	        blancsiteration= new HashMap<Integer, Integer>(blancs);
	        
	        AllNoires.put(depart[itDepart], new HashMap<Integer, Integer>(noires));
	        AllPrevious.put(depart[itDepart], new HashMap<Integer, Integer>(previous));
	        
			
       }
    }
	
	
	public void ajouterNoeud(Noeud n) {
		if(n != null) {
			noeuds.put(n.getId(), n);
		}
	}
	
	public void ajouterTroncon(Troncon t) {
		if(t != null) {
			troncons.put(Pair.create(t.getOrigine(), t.getDestination()) , t);
		}
	}
	
	public void ajouterLivraison(Livraison l) {
		if(l != null) {
			livraisons.put(l.getNoeud(), l);
		}
	}
	
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
	
	public Map<Noeud, Livraison> getLivraisons() {
		return livraisons;
	}

	public void setLivraisons(Map<Noeud, Livraison> livraisons) {
		this.livraisons = livraisons;
	}
	
	public void resetLivraisons() {
		this.livraisons = new HashMap<Noeud, Livraison>();
	}
	
	public Entrepot getEntrepot() {
		return entrepot;
	}
	
	public void setEntrepot(Entrepot entrepot) {
		this.entrepot = entrepot;
	}

	public Tournee getTournee() {
		// TODO Auto-generated method stub
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
			//On parcourt les trajets, on s'arrête à size()-1 pour gérer le trajet jusqu'à l'entrepôt manuellement			
			for(int i = 0; i < trajets.size()-1; ++i) {
				output.write("\nDepart "+ heureCourante.getHoraire() +" du noeud " + trajets.get(i).getDepart().getId());
				//On parcourt les tronçons
				for(Troncon tron : trajets.get(i).getTroncons()) {
					//Affichage pour la longueur du tronçon
					String longueurStr;
					if(tron.getLongueur() > 999){
						longueurStr = tron.getLongueur()/1000 + "km";
					}
					else {
						longueurStr = tron.getLongueur() + "m";
					}
					output.write("\n\tPrendre : " + tron.getNomRue() + " pendant " + longueurStr);
					//On incrémente l'heure
					heureCourante.ajouterSeconde(tron.getLongueur()/tron.getVitesse());
				}
				//La livraison à faire
				Livraison l = livraisons.get(trajets.get(i).getArrive());
				output.write("\nArrivée "+ heureCourante.getHoraire() +" au noeud " + l.getNoeud().getId());
				//Affichage pour la durée estimée de la livraison
				Horaire horaireTemp = new Horaire(0,0,0);
				horaireTemp.ajouterSeconde(l.getDuree());
				output.write("\n\tDurée de livraison estimée : " + horaireTemp.toString());
				//On incrémente l'heure
				heureCourante.ajouterSeconde(l.getDuree());
				
				output.flush();
			}
			
			//Trajet de la dernière livraison à l'entrepot
			output.write("\nRetour à l'entrepot, départ " + heureCourante.getHoraire() +" du noeud " + trajets.get(trajets.size()-1).getArrive().getId());
			for(Troncon tron : trajets.get(trajets.size()-1).getTroncons()) {
				String longueurStr;
				if(tron.getLongueur() > 999){
					longueurStr = tron.getLongueur()/1000 + "km";
				}
				else {
					longueurStr = tron.getLongueur() + "m";
				}
				output.write("\n\tPrendre : " + tron.getNomRue() + " pendant " + longueurStr);
				heureCourante.ajouterSeconde(tron.getLongueur()/tron.getVitesse());
			}
			output.write("\nArrivée à l'entrepot : " + heureCourante.getHoraire());
			
			output.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
