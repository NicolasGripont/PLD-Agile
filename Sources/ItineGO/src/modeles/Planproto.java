/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modeles;


import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Map.Entry;
import java.util.Set;
import java.util.HashMap;



/**
 *
 * @author jfolleas1
 */


public class Planproto {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
    	
    	Integer depart[]; // deprt deviendrai livraison
		depart = new Integer[3]; // ce sera à placer en paramettre de l'appel de fonction
		depart[0]=1;
		depart[1]=2;
		depart[2]=3;
		
		int duree[];
		duree = new int[3]; // ce sera à placer en paramettre de l'appel de fonction
		duree[0]=1;
		duree[1]=1;
		duree[2]=1;
		
		Integer matriceDuGraphe[][];
    	matriceDuGraphe= new Integer[10][10] ; //Sera également placé en paramètre
    	
    	for(Integer i = 0 ; i <matriceDuGraphe.length ;i++)
        {
            for(Integer j = 0 ; j < matriceDuGraphe.length ; j++)
            {
                if(i!=j)
                {
                    
                    matriceDuGraphe[i][j]=(int)( Math.random()*(9) + 1);

                }
                else
                {
                    matriceDuGraphe[i][j]=0;
                }
            }
    
        }
    	
    	//Imprime la matrice du graphe 
    		System.out.print(" \n matrice du graph: \n");
    		for(int i = 0; i <matriceDuGraphe.length ;i++)
         	{
         		for(int j = 0 ; j < matriceDuGraphe.length ; j++)
         		{
                	System.out.print(matriceDuGraphe[i][j] + " ");    
            	}
            	System.out.print("\n");
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
			System.out.print("\n depart: " + depart[p] + "\n" );
			System.out.print( " previous: \n");
			setprevious = AllPrevious.get(depart[p]).entrySet();
			it = setprevious.iterator();
			while(it.hasNext())
			{
			    e = it.next();
			    System.out.print("<"+e.getKey()+";"+e.getValue()+"> ");
			    
			}
			
			System.out.print( "\n noires: \n");
			setnoires = AllNoires.get(depart[p]).entrySet();
			it = setnoires.iterator();
			while(it.hasNext())
			{
			    e = it.next();
			    System.out.print("<"+e.getKey()+";"+e.getValue()+"> ");
			    
			}
			System.out.print("\n");
		}
		
		//Construction de la matrice de cout pour le TSP
		
		int cout[][]= new int [depart.length][depart.length];
		for(int u = 0;u<depart.length; u++)
		{
			for(int v = 0;v<depart.length; v++)
			{
				cout[u][v]=(AllNoires.get(depart[u])).get(depart[v]);
				System.out.print(cout[u][v]+" ");
			}
			System.out.print("\n");
		}
		
		tsp.TSP monTSP = new tsp.TSP1();
		monTSP.chercheSolution(100000, depart.length , cout, duree);  //le 100000 est le temps max toléré 
		
		
		//Il faut maintenant reupéré le chemin optimal via les get
		
		//System.out.print("\n Orbre des noeud : \n");
		
		
		List<Integer> futurTourne = new ArrayList<Integer>();
		HashMap<Integer, Integer> previous;
		Integer noeudCourant = depart[monTSP.getMeilleureSolution(0)]; //Comme on travail avec des arbre de couvrance minimum on fait le chemin à l'envers
		System.out.print("depart : "+ noeudCourant+ "\n");
		
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
	      System.out.print("\n tourne : \n");
	      System.out.println(futurTourne);

		//Puis retrouver les tronçons grace à la map previous
		
    }
    
    public static void Dijkstra(Integer depart[], Integer matriceDuGraphe[][] ,HashMap< Integer, HashMap<Integer, Integer>> AllNoires ,HashMap< Integer, HashMap<Integer, Integer>> AllPrevious)
    {
    	
    	for(int itDepart = 0; itDepart<depart.length ; itDepart++)
    	{
    		Integer curentNoeud = depart[itDepart];
        
    		

    		Integer  distanceADep[] = new Integer[(int) matriceDuGraphe[1].length];
    		for(Integer i = 0 ; i < distanceADep.length;i++)
    		{
    			distanceADep[i]=10000; //A remplacer par MAX_INT
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
	            blancs.put(i,10000);//MAXINT
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
    
}
      
      
    
            
