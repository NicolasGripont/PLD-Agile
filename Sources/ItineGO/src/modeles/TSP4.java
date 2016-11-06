package modeles;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * Quatrième version du TSP, implémente l'utilisation des plages horaires
 */
public class TSP4 extends TSP3 {

	
	public void chercheSolution(int tpsLimite, int nbSommets, int[][] cout, int[] duree, int[][] plages_horaire){//@overwrite chercher sol pour int[nbSomet][2] plage
		//System.out.println("TSP4 utilisé");
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
	    	
	    	if (coutVus < getCoutMeilleureSolution()){ // on a trouve une solution meilleure que meilleureSolution
	    		vus.toArray(meilleureSolution);
	    		coutMeilleureSolution=coutVus;
	    		
	    		
	    		coutVus += cout[sommetCrt][0];
		    	int coutVus2=0;
		    	for(int i = 0 ; i< cout[0].length-1 ;i++)
		    	{
		    		coutVus2+=cout[this.getMeilleureSolution(i)][this.getMeilleureSolution(i+1)];
		    		
		    		if(coutVus2 < plage_horaire[0][this.getMeilleureSolution(i+1)])
		    			coutVus2=plage_horaire[0][this.getMeilleureSolution(i+1)];
		    		/*(coutVus2/3600) +":"+ ((coutVus2/60)%60)*/
		    		//System.out.println("A : " + (8+(coutVus2/3600)) +":"+ ((coutVus2/60)%60) + " DEB " + plage_horaire[0][this.getMeilleureSolution(i+1)]+ " FIN " + plage_horaire[1][this.getMeilleureSolution(i+1)]);
		    		coutVus2+=duree[this.getMeilleureSolution(i+1)];
		    	}
		    	coutVus2+=cout[this.getMeilleureSolution(cout[0].length-1)][this.getMeilleureSolution(0)];
	    		//System.out.println("A : "+ (8+(coutVus2/3600)) +":"+ ((coutVus2/60)%60));
		    	//System.out.println("Cout vus : " + coutVus);
	    	}
	    } else{
	    	if ((coutVus + bound(sommetCrt, nonVus, cout, duree) < getCoutMeilleureSolution())&& (coutVus < plage_horaire[1][sommetCrt])){//On v�rifie qu'on arrive pas trop tard
	    
	    	Iterator<Integer> it = iterator(sommetCrt, nonVus, cout, duree);
	        while (it.hasNext()){
	        	Integer prochainSommet = it.next();
	        	vus.add(prochainSommet);
	        	nonVus.remove(prochainSommet);
	        	int futurCoutVus = coutVus + cout[sommetCrt][prochainSommet];
	        	//System.out.println("FC" + futurCoutVus +" : DP "+plage_horaire[0][prochainSommet]);
	        	if(futurCoutVus < plage_horaire[0][prochainSommet]){
	        		//System.out.println("WAIT");
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
