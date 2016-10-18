package modeles;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import utility.Pair;

public class Plan {
	private Map<Integer, Noeud> noeuds;
	private Map<Pair<Noeud, Noeud>, Troncon> troncons;
	private Map<Noeud, Livraison> livraisons;
	private Entrepot entrepot;
	private int tableauDesId[];
	
	private Tournee tournee;
	
	public Plan() {
		noeuds = new HashMap<Integer, Noeud>();
		troncons = new HashMap<Pair<Noeud, Noeud>, Troncon> ();
		livraisons = new HashMap<Noeud, Livraison>();
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
	
	public void CalculDeTournee() {
    	
    	int nbDeLivraison = livraisons.size();
    	
    	tableauDesId = new int [noeuds.size()];
    	
    	
    	//On remplie le tableau des id 
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
    	
    	
    	Integer depart[]; // numero dans tableau des id des livraisons
		depart = new Integer[nbDeLivraison+1]; // ce sera à placer en paramettre de l'appel de fonction
		
		int duree[];
		duree = new int[nbDeLivraison+1];
		
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
		
		Integer matriceDuGraphe[][];
    	matriceDuGraphe= new Integer[noeuds.size()][noeuds.size()] ; 
    	
    	//On remplie la matrice qui modelise le graphe
    	
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
                		matriceDuGraphe[i][j]=10000000; // a remplacer par un max
                	}
                	
                }
                else
                {
                    matriceDuGraphe[i][j]=0;
                }
            }
    
        }
    	
    	//Imprime la matrice du graphe -> à elever une fois les teste unitaire fait sur données réelles
    
//    	System.out.print(" \n matrice du graph: \n");
    	for(int i = 0; i <matriceDuGraphe.length ;i++)
    	{
    		for(int j = 0 ; j < matriceDuGraphe.length ; j++)
    		{
//    			System.out.print(matriceDuGraphe[i][j] + " ");    
            }
//    		System.out.print("\n");
    	}
    	
    	HashMap< Integer, HashMap<Integer, Integer>> AllNoires = new HashMap<>(); //Sera également placé en paramètre
        HashMap< Integer, HashMap<Integer, Integer>> AllPrevious = new HashMap<>(); //Sera également placé en paramètre
    	
    	Dijkstra(depart, matriceDuGraphe, AllNoires, AllPrevious);
    
    	
    	Set<Entry<Integer, Integer>> setprevious;
		Set<Entry<Integer, Integer>> setnoires;
		Iterator<Entry<Integer, Integer>> it;
		Entry<Integer, Integer> e;
		
    	//Verification de ce qui est sortie
		
		for(int p =0; p< depart.length; p++)
		{	
//			System.out.print("\n depart: " + depart[p] + "\n" );
//			System.out.print( " previous: \n");
			setprevious = AllPrevious.get(depart[p]).entrySet();
			it = setprevious.iterator();
			while(it.hasNext())
			{
			    e = it.next();
//			    System.out.print("<"+e.getKey()+";"+e.getValue()+"> ");
			    
			}
			
//			System.out.print( "\n noires: \n");
			setnoires = AllNoires.get(depart[p]).entrySet();
			it = setnoires.iterator();
			while(it.hasNext())
			{
			    e = it.next();
//			    System.out.print("<"+e.getKey()+";"+e.getValue()+"> ");
			    
			}
//			System.out.print("\n");
		}
		
		//Construction de la matrice de cout pour le TSP
		
		int cout[][]= new int [depart.length][depart.length];
		for(int u = 0;u<depart.length; u++)
		{
			for(int v = 0;v<depart.length; v++)
			{
				cout[u][v]=(AllNoires.get(depart[u])).get(depart[v]);
//				System.out.print(cout[u][v]+" ");
			}
//			System.out.print("\n");
		}
		
		tsp.TSP monTSP = new tsp.TSP1();
		monTSP.chercheSolution(100000, depart.length , cout, duree);  //le 100000 est le temps max toléré 
		
		
		//Il faut maintenant reupéré le chemin optimal via les get
		
		//System.out.print("\n Orbre des noeud : \n");
		
		
		List<Integer> futurTourne = new ArrayList<Integer>();
		HashMap<Integer, Integer> previous;
		Integer noeudCourant = depart[monTSP.getMeilleureSolution(0)]; //Comme on travail avec des arbre de couvrance minimum on fait le chemin à l'envers
//		System.out.print("depart : "+ noeudCourant+ "\n");
		
		for(int i = depart.length-1 ; i >=0 ; i--)
		{
		
			
			
			previous = new HashMap<>(AllPrevious.get(depart[monTSP.getMeilleureSolution(i)]));
			while(previous.get(noeudCourant)!=noeudCourant)
			{
				futurTourne.add(noeudCourant);
			    noeudCourant=previous.get(noeudCourant);
			}
		}
		futurTourne.add(depart[monTSP.getMeilleureSolution(0)]);
	      Collections.reverse(futurTourne);
//	      System.out.print("\n tourne : \n");
//	      System.out.println(futurTourne);

		//Puis retrouver les tronçons en recupérant les id des noeuds dans tableauDesId
	    //Puis on constrit tournee
	     
	    //Checker si la tournee est valide
//	    if(futurTourne.size() >= 3 && futurTourne.get(0) == futurTourne.get(futurTourne.size()-1)) {
			// Construction de la Tournee
			List<Trajet> trajetsPrevus = new ArrayList<>();
			List<Troncon> tronconsTrajet = new ArrayList<>();

			for (Integer i = 0; i < futurTourne.size() - 1; i++) {
				if (livraisons.get(noeuds.get(futurTourne.get(i + 1))) != null
						|| entrepot.getNoeud().equals(noeuds.get(futurTourne.get(i + 1)))) {
					tronconsTrajet.add(troncons
							.get(new Pair(noeuds.get(futurTourne.get(i)), noeuds.get(futurTourne.get(i + 1)))));
					if (!tronconsTrajet.isEmpty()) {
						trajetsPrevus.add(new Trajet(tronconsTrajet.get(0).getOrigine(),
								tronconsTrajet.get(tronconsTrajet.size() - 1).getDestination(), tronconsTrajet));
						tronconsTrajet = new ArrayList<>();
					}
				} else {
					tronconsTrajet.add(
							troncons.get(new Pair(noeuds.get(futurTourne.get(i)), noeuds.get(futurTourne.get(i + 1)))));
				}
			}
			this.tournee = new Tournee(trajetsPrevus);
//	    } else {
//	    	this.tournee = null;
//	    }
    }
    
    private static void Dijkstra(Integer depart[], Integer matriceDuGraphe[][] ,HashMap< Integer, HashMap<Integer, Integer>> AllNoires ,HashMap< Integer, HashMap<Integer, Integer>> AllPrevious)
    {
    	
    	for(int itDepart = 0; itDepart<depart.length ; itDepart++)
    	{
    		Integer curentNoeud = depart[itDepart];
        
    		

    		Integer  distanceADep[] = new Integer[(int) matriceDuGraphe[1].length];
    		for(Integer i = 0 ; i < distanceADep.length;i++)
    		{
    			distanceADep[i]=Integer.MAX_VALUE; //A remplacer par MAX_INT
    		}
       
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
	            if(i!=depart[itDepart])
	            {
	            blancs.put(i,Integer.MAX_VALUE);
	            }
	            else
	            {
	            blancs.put(i,0);
	            }
	        }
	
	        
	       
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
	                if(e.getValue()> (min + matriceDuGraphe[curentNoeud][e.getKey()]))
	                {
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
	
	
	public void AjouterNoeud(Noeud n) {
		if(n != null) {
			noeuds.put(n.getId(), n);
		}
	}
	
	public void AjouterTroncon(Troncon t) {
		if(t != null) {
			troncons.put(Pair.create(t.getOrigine(), t.getDestination()) , t);
		}
	}
	
	public void AjouterLivraison(Livraison l) {
		if(l != null) {
			livraisons.put(l.getNoeud(), l);
		}
	}
	
	public void AjouterEntrepot(Entrepot e) {
		if(e != null) {
			entrepot = e;
		}
	}
	
	public boolean CalculerTournee() {
		return false;
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
	
	public void effacerTournee() {
		this.tournee = null;
	}
	
	public boolean idExiste(int id)
	{
		if(noeuds.containsKey(id)){return true;}
		else{return false;}
	}
}
