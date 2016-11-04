package modeles;

import java.util.ArrayList;
import java.util.Iterator;

public class TSP4 extends TSP3 {

	
	public void chercheSolution(int tpsLimite, int nbSommets, int[][] cout, int[] duree, int[][] plages_horaire){//@overwrite chercher sol pour int[nbSomet][2] plage
		System.out.println("TSP4 utilisé");
		tempsLimiteAtteint=false;
		coutMeilleureSolution=Integer.MAX_VALUE;
		meilleureSolution=new Integer[nbSommets];
		ArrayList<Integer> nonVus = new ArrayList<Integer>();
		for (int i=1; i<nbSommets; i++) nonVus.add(i);
		ArrayList<Integer> vus = new ArrayList<Integer>(nbSommets);
		vus.add(0); // le premier sommet visite est 0
		branchAndBound(0, nonVus, vus, 0, cout, duree, System.currentTimeMillis(), tpsLimite,  plages_horaire);
	}

	
	void branchAndBound(int sommetCrt, ArrayList<Integer> nonVus, ArrayList<Integer> vus, int coutVus, int[][] cout, int[] duree, long tpsDebut, int tpsLimite, int[][] plage_horaire){ 
		if (System.currentTimeMillis() - tpsDebut > tpsLimite){
			 tempsLimiteAtteint=true;
			 
			 return;
		 }
	    if (nonVus.size() == 0){ // tous les sommets ont ete visites
	    	coutVus += cout[sommetCrt][0];
	    	System.out.println("Cout vus : " + coutVus);
	    	if (coutVus < getCoutMeilleureSolution()){ // on a trouve une solution meilleure que meilleureSolution
	    		vus.toArray(meilleureSolution);
	    		coutMeilleureSolution=coutVus;
	    		
	    	}
	    } else{
	    	if(!(coutVus < plage_horaire[1][sommetCrt])) {
	    		System.out.println("coutVus < plage_horaire[1][sommetCrt]");
	    	}
	    	if ((coutVus + bound(sommetCrt, nonVus, cout, duree) < getCoutMeilleureSolution())&& (coutVus < plage_horaire[1][sommetCrt])){//On v�rifie qu'on arrive pas trop tard
	    
	    	Iterator<Integer> it = iterator(sommetCrt, nonVus, cout, duree);
	        while (it.hasNext()){
	        	Integer prochainSommet = it.next();
	        	vus.add(prochainSommet);
	        	nonVus.remove(prochainSommet);
	        	int futurCoutVus = coutVus + cout[sommetCrt][prochainSommet];
	        	//System.out.println("FC" + futurCoutVus +" : DP "+plage_horaire[0][prochainSommet]);
	        	if(futurCoutVus < plage_horaire[0][prochainSommet]){
	        		//System.out.println("FC" + futurCoutVus +" : DP "+plage_horaire[0][prochainSommet]);
	        		futurCoutVus = plage_horaire[0][prochainSommet];//Si on arrive trop tôt on attend 
	        		//System.out.println("FC" + futurCoutVus +" : DP "+plage_horaire[0][prochainSommet]);

	        	}
	        	branchAndBound(prochainSommet, nonVus, vus, futurCoutVus + duree[prochainSommet], cout, duree, tpsDebut, tpsLimite, plage_horaire );
	        	vus.remove(prochainSommet);
	        	nonVus.add(prochainSommet);
	        	}	    
	    	}
	    }
	}


	
}
