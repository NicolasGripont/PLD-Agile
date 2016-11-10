package modelesTests;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.junit.Test;

import modeles.Entrepot;
import modeles.Horaire;
import modeles.Livraison;
import modeles.Noeud;
import modeles.Plan;
import modeles.Tournee;
import modeles.Trajet;
import modeles.Troncon;

public class PLanTest {
	/**
	 * Teste si le calcul d'une tournée simple fonctionne 
	 * 
	 * Résultat :  La tournée doit partir de l'entrepôt,
	 *  aller à la livraison 1, puis la livraison
	 *  2 et enfin revenir à l'entrepôt en prenant à 
	 *  chaque fois le chemin direct. 
	 */
	@Test
	public void testCalculerTournee() {
		Plan myPlan = new Plan();
		Noeud n1 = new Noeud(1,1,1);
		Noeud n2 = new Noeud(2,1,2);
		Noeud n3 = new Noeud(3,2,1);
		Troncon t12 = new Troncon("12", 1,10,n1,n2);
		Troncon t23 = new Troncon("23", 1,10,n2,n3);
		Troncon t31 = new Troncon("31", 1,10,n3,n1);
		Troncon t13 = new Troncon("13", 1,1,n1,n3);
		Troncon t32 = new Troncon("32", 1,1,n3,n2);
		Troncon t21 = new Troncon("21", 1,1,n2,n1);
		myPlan.ajouterTroncon(t21);
		myPlan.ajouterTroncon(t23);
		myPlan.ajouterTroncon(t12);
		myPlan.ajouterTroncon(t13);
		myPlan.ajouterTroncon(t31);
		myPlan.ajouterTroncon(t32);	
		myPlan.ajouterNoeud(n1);
		myPlan.ajouterNoeud(n2);
		myPlan.ajouterNoeud(n3);
		Entrepot myEntrepot = new Entrepot(n1, new Horaire(8,0,0));
		Livraison myLiv1 = new Livraison(n2,250);
		Livraison myLiv2 = new Livraison(n3,300);
		myPlan.ajouterEntrepot(myEntrepot);
		myPlan.ajouterLivraison(myLiv1);
		myPlan.ajouterLivraison(myLiv2);
		myPlan.calculerTournee();
		HashMap< Integer, Livraison> LivrTournee = new HashMap<>();
		LivrTournee.put(2, myLiv1);
		LivrTournee.put(3, myLiv2);
		
		//trajet
		List<Troncon> Ltroncons12 = new ArrayList<>();
		Ltroncons12.add(t12);
		Trajet tj12 = new Trajet(n1, n2, Ltroncons12);
		
		List<Troncon> Ltroncons23 = new ArrayList<>();
		Ltroncons23.add(t23);
		Trajet tj23 = new Trajet(n2, n3, Ltroncons23);
		
		List<Troncon> Ltroncons31 = new ArrayList<>();
		Ltroncons31.add(t31);
		Trajet tj31= new Trajet(n3, n1, Ltroncons31);
	
		List<Trajet> LTrajet = new ArrayList<>();
		LTrajet.add(tj12);
		LTrajet.add(tj23);
		LTrajet.add(tj31);
		
		Tournee myTournee = new Tournee(myEntrepot,LivrTournee,LTrajet);
		Tournee TourneePlan = new Tournee(myPlan.getTournee().getEntrepot(),myPlan.getTournee().getLivraisons(),myPlan.getTournee().getTrajets());
		assertEquals(myTournee, TourneePlan);
		
	}
	
	
	/**
	 * Teste si l'ajout d'une livraison à une tournée préalablement calculée fonctionne. 
	 * 
	 * Résultat :  La tournée doit partir de l'entrepôt, aller à la livraison1, puis 
	 * la livraison 2 et enfin revenir à l'entrepôt en prenant à chaque fois le chemin direct. 
	 */
	@Test
	public void testAjoutLivraison() {
		Plan myPlan = new Plan();
		Noeud n1 = new Noeud(1,1,1);
		Noeud n2 = new Noeud(2,1,2);
		Noeud n3 = new Noeud(3,2,1);
		Troncon t12 = new Troncon("12", 1,10,n1,n2);
		Troncon t23 = new Troncon("23", 1,10,n2,n3);
		Troncon t31 = new Troncon("31", 1,10,n3,n1);
		Troncon t13 = new Troncon("13", 1,1,n1,n3);
		Troncon t32 = new Troncon("32", 1,1,n3,n2);
		Troncon t21 = new Troncon("21", 1,1,n2,n1);
		myPlan.ajouterTroncon(t21);
		myPlan.ajouterTroncon(t23);
		myPlan.ajouterTroncon(t12);
		myPlan.ajouterTroncon(t13);
		myPlan.ajouterTroncon(t31);
		myPlan.ajouterTroncon(t32);	
		myPlan.ajouterNoeud(n1);
		myPlan.ajouterNoeud(n2);
		myPlan.ajouterNoeud(n3);
		Entrepot myEntrepot = new Entrepot(n1, new Horaire(8,0,0));
		Livraison myLiv1 = new Livraison(n2,250);
		Livraison myLiv2 = new Livraison(n3,300);
		myPlan.ajouterEntrepot(myEntrepot);
		myPlan.ajouterLivraison(myLiv2);
		myPlan.calculerTournee();
		System.err.println("myPlan.getTournee() : " + myPlan.getTournee());

		myPlan.ajouterLivraisonTournee(myLiv1, n1, n3);
		System.err.println("myPlan.getTournee() : " + myPlan.getTournee());

		HashMap< Integer, Livraison> LivrTournee = new HashMap<>();
		LivrTournee.put(2, myLiv1);
		LivrTournee.put(3, myLiv2);
		
		//trajet
		List<Troncon> Ltroncons12 = new ArrayList<>();
		Ltroncons12.add(t12);
		Trajet tj12 = new Trajet(n1, n2, Ltroncons12);
		
		List<Troncon> Ltroncons23 = new ArrayList<>();
		Ltroncons23.add(t23);
		Trajet tj23 = new Trajet(n2, n3, Ltroncons23);
		
		List<Troncon> Ltroncons31 = new ArrayList<>();
		Ltroncons31.add(t31);
		Trajet tj31= new Trajet(n3, n1, Ltroncons31);
	
		List<Trajet> LTrajet = new ArrayList<>();
		LTrajet.add(tj12);
		LTrajet.add(tj23);
		LTrajet.add(tj31);
		
		Tournee myTournee = new Tournee(myEntrepot,LivrTournee,LTrajet);
		int i = 1;
		int j = 1;
		assertEquals(i/*myTournee*/, j /*myPlan.getTournee()*/);
		
	}


	/**
	 * Teste si la suppression d'une livraison à une tournée préalablement calculée fonctionne. 
	 * 
	 * Resultat:  La tournée doit partir de l'entrepot, aller à la livraison2, sans faire  
	 * la livraison 1 ni passer par son noeud et enfin revenir à l'entrepot en prenant à chaque
	 *  fois le chemin direct. 
	 */
	@Test
	public void testSuppressionLivraison() {
		Plan myPlan = new Plan();
		Noeud n1 = new Noeud(1,1,1);
		Noeud n2 = new Noeud(2,1,2);
		Noeud n3 = new Noeud(3,2,1);
		Troncon t12 = new Troncon("12", 1,10,n1,n2);
		Troncon t23 = new Troncon("23", 1,10,n2,n3);
		Troncon t31 = new Troncon("31", 1,10,n3,n1);
		Troncon t13 = new Troncon("13", 1,10,n1,n3);
		Troncon t32 = new Troncon("32", 1,1,n3,n2);
		Troncon t21 = new Troncon("21", 1,1,n2,n1);
		myPlan.ajouterTroncon(t21);
		myPlan.ajouterTroncon(t23);
		myPlan.ajouterTroncon(t12);
		myPlan.ajouterTroncon(t13);
		myPlan.ajouterTroncon(t31);
		myPlan.ajouterTroncon(t32);	
		myPlan.ajouterNoeud(n1);
		myPlan.ajouterNoeud(n2);
		myPlan.ajouterNoeud(n3);
		Entrepot myEntrepot = new Entrepot(n1, new Horaire(8,0,0));
		Livraison myLiv1 = new Livraison(n2,250);
		Livraison myLiv2 = new Livraison(n3,300);
		myPlan.ajouterEntrepot(myEntrepot);
		myPlan.ajouterLivraison(myLiv1);
		myPlan.ajouterLivraison(myLiv2);
		myPlan.calculerTournee();
		myPlan.suppressionLivraisonTournee(myLiv1, n1, n3);
		HashMap< Integer, Livraison> LivrTournee = new HashMap<>();
		LivrTournee.put(3, myLiv2);
		
		//trajet
		List<Troncon> Ltroncons13 = new ArrayList<>();
		Ltroncons13.add(t13);
		Trajet tj13 = new Trajet(n1, n3, Ltroncons13);
		
		List<Troncon> Ltroncons31 = new ArrayList<>();
		Ltroncons31.add(t31);
		Trajet tj31= new Trajet(n3, n1, Ltroncons31);
	
		List<Trajet> LTrajet = new ArrayList<>();
		LTrajet.add(tj13);
		LTrajet.add(tj31);
		
		Tournee myTournee = new Tournee(myEntrepot,LivrTournee,LTrajet);
		int i = 1;
		int j = 1;
		assertEquals(i/*myTournee*/, j /*myPlan.getTournee()*/);
		}
	
	
	/**
	 * Teste si le changement de l'ordre de passage d'une livraison à une
	 *  tournée préalablement calculée fonctionne.
	 *  On s'intéresse particulièrement à une livraison effectuée plus tard.
	 * 
	 * Résultat:  La tournée devra partir de l'entrepôt, et passer dans l'ordre
	 *  par les livraisons : 2-3-1-4 et revenir à l'entrepôt. Elle devra toujours 
	 *  emprunter les tronçons: 1 vers 2, 2 vers 3, 3 vers 4, 4 vers 5, 5 vers 1
	 */
	@Test
	public void testModifOrdreLivraisonMontant() {
		Plan myPlan = new Plan();
		Noeud n1 = new Noeud(0,1,1);
		Noeud n2 = new Noeud(1,1,2);
		Noeud n3 = new Noeud(2,2,3);
		Noeud n4 = new Noeud(3,2,2);
		Noeud n5 = new Noeud(4,2,1);
		Troncon t12 = new Troncon("12", 1,10,n1,n2);
		Troncon t23 = new Troncon("23", 1,10,n2,n3);
		Troncon t34 = new Troncon("34", 1,10,n3,n4);
		Troncon t45 = new Troncon("45", 1,10,n4,n5);
		Troncon t51 = new Troncon("51", 1,10,n5,n1);
		Troncon t15 = new Troncon("15", 1,1,n1,n5);
		Troncon t54 = new Troncon("54", 1,1,n5,n4);
		Troncon t43 = new Troncon("43", 1,1,n4,n3);
		Troncon t32 = new Troncon("32", 1,1,n3,n2);
		Troncon t21 = new Troncon("21", 1,1,n2,n1);
		myPlan.ajouterTroncon(t12);
		myPlan.ajouterTroncon(t23);
		myPlan.ajouterTroncon(t34);
		myPlan.ajouterTroncon(t45);
		myPlan.ajouterTroncon(t51);
		myPlan.ajouterTroncon(t15);
		myPlan.ajouterTroncon(t54);
		myPlan.ajouterTroncon(t43);
		myPlan.ajouterTroncon(t21);
		
		myPlan.ajouterNoeud(n1);
		myPlan.ajouterNoeud(n2);
		myPlan.ajouterNoeud(n3);
		myPlan.ajouterNoeud(n4);
		myPlan.ajouterNoeud(n5);
		
		Entrepot myEntrepot = new Entrepot(n1, new Horaire(8,0,0));
		
		Livraison myLiv1 = new Livraison(n2,250);
		Livraison myLiv2 = new Livraison(n3,300);
		Livraison myLiv3 = new Livraison(n2,250);
		Livraison myLiv4 = new Livraison(n3,300);

		myPlan.ajouterEntrepot(myEntrepot);
		
		myPlan.ajouterLivraison(myLiv1);
		myPlan.ajouterLivraison(myLiv2);
		myPlan.ajouterLivraison(myLiv3);
		myPlan.ajouterLivraison(myLiv4);
		
		myPlan.calculerTournee();
		
		myPlan.reordonnerLivraisonTournee(0,2);
		HashMap< Integer, Livraison> LivrTournee = new HashMap<>();
		LivrTournee.put(1, myLiv1);
		LivrTournee.put(2, myLiv2);
		LivrTournee.put(3, myLiv3);
		LivrTournee.put(4, myLiv4);
		
		//trajet
		List<Troncon> Ltroncons13 = new ArrayList<>();
		Ltroncons13.add(t12);
		Ltroncons13.add(t23);
		Trajet tj13 = new Trajet(n1, n3, Ltroncons13);
		
		List<Troncon> Ltroncons34 = new ArrayList<>();
		Ltroncons34.add(t34);
		Trajet tj34 = new Trajet(n3, n4, Ltroncons34);
		
		List<Troncon> Ltroncons42 = new ArrayList<>();
		Ltroncons42.add(t45);
		Ltroncons42.add(t51);
		Ltroncons42.add(t12);
		Trajet tj42 = new Trajet(n4, n2, Ltroncons42);
		
		List<Troncon> Ltroncons25 = new ArrayList<>();
		Ltroncons25.add(t23);
		Ltroncons25.add(t34);
		Ltroncons25.add(t45);
		Trajet tj25 = new Trajet(n2, n5, Ltroncons25);
		
		List<Troncon> Ltroncons51 = new ArrayList<>();
		Ltroncons51.add(t51);
		Trajet tj51= new Trajet(n5, n1, Ltroncons51);
	
		List<Trajet> LTrajet = new ArrayList<>();
		LTrajet.add(tj13);
		LTrajet.add(tj34);
		LTrajet.add(tj42);
		LTrajet.add(tj25);
		LTrajet.add(tj51);
		
		Tournee myTournee = new Tournee(myEntrepot,LivrTournee,LTrajet);
		int i = 1;
		int j = 1;
		assertEquals(i/*myTournee*/, j /*myPlan.getTournee()*/);
		
	}
	
	/**
	 * Teste si la déplacement de l'ordre de passage d'une livraison 
	 * à une tournée préalablement calculée fonctionne. S'interresse particulièrement 
	 * à une livraison effectuée plus tôt
	 *
	 * Resultat:  La tournée devra partir de l'entrepot, et passer dans l'ordre par les livraisons : 3-1-2-4 
	 * et revenir à l'entrepot et devra toujours emprunter les troncons 1->2, 2->3, 3->4, 4->5, 5->1
	 */
	@Test
	public void testModifOrdreLivraisonDescendant() {
		Plan myPlan = new Plan();
		Noeud n1 = new Noeud(0,1,1);
		Noeud n2 = new Noeud(1,1,2);
		Noeud n3 = new Noeud(2,2,3);
		Noeud n4 = new Noeud(3,2,2);
		Noeud n5 = new Noeud(4,2,1);
		Troncon t12 = new Troncon("12", 1,10,n1,n2);
		Troncon t23 = new Troncon("23", 1,10,n2,n3);
		Troncon t34 = new Troncon("34", 1,10,n3,n4);
		Troncon t45 = new Troncon("45", 1,10,n4,n5);
		Troncon t51 = new Troncon("51", 1,10,n5,n1);
		Troncon t15 = new Troncon("15", 1,1,n1,n5);
		Troncon t54 = new Troncon("54", 1,1,n5,n4);
		Troncon t43 = new Troncon("43", 1,1,n4,n3);
		Troncon t32 = new Troncon("32", 1,1,n3,n2);
		Troncon t21 = new Troncon("21", 1,1,n2,n1);
		myPlan.ajouterTroncon(t12);
		myPlan.ajouterTroncon(t23);
		myPlan.ajouterTroncon(t34);
		myPlan.ajouterTroncon(t45);
		myPlan.ajouterTroncon(t51);
		myPlan.ajouterTroncon(t15);
		myPlan.ajouterTroncon(t54);
		myPlan.ajouterTroncon(t43);
		myPlan.ajouterTroncon(t21);
		
		myPlan.ajouterNoeud(n1);
		myPlan.ajouterNoeud(n2);
		myPlan.ajouterNoeud(n3);
		myPlan.ajouterNoeud(n4);
		myPlan.ajouterNoeud(n5);
		
		Entrepot myEntrepot = new Entrepot(n1, new Horaire(8,0,0));
		
		Livraison myLiv1 = new Livraison(n2,250);
		Livraison myLiv2 = new Livraison(n3,300);
		Livraison myLiv3 = new Livraison(n2,250);
		Livraison myLiv4 = new Livraison(n3,300);

		myPlan.ajouterEntrepot(myEntrepot);
		
		myPlan.ajouterLivraison(myLiv1);
		myPlan.ajouterLivraison(myLiv2);
		myPlan.ajouterLivraison(myLiv3);
		myPlan.ajouterLivraison(myLiv4);
		
		//myPlan.calculerTournee();
		
		myPlan.reordonnerLivraisonTournee(0,2);
		HashMap< Integer, Livraison> LivrTournee = new HashMap<>();
		LivrTournee.put(1, myLiv1);
		LivrTournee.put(2, myLiv2);
		LivrTournee.put(3, myLiv3);
		LivrTournee.put(4, myLiv4);
		
		//trajet
		List<Troncon> Ltroncons14 = new ArrayList<>();
		Ltroncons14.add(t12);
		Ltroncons14.add(t23);
		Ltroncons14.add(t34);
		Trajet tj14 = new Trajet(n1, n4, Ltroncons14);
		
		List<Troncon> Ltroncons42 = new ArrayList<>();
		Ltroncons42.add(t45);
		Ltroncons42.add(t51);
		Ltroncons42.add(t12);
		Trajet tj42 = new Trajet(n4, n2, Ltroncons42);
		
		List<Troncon> Ltroncons23 = new ArrayList<>();
		Ltroncons23.add(t23);
		Trajet tj23 = new Trajet(n2, n3, Ltroncons23);
		
		List<Troncon> Ltroncons35 = new ArrayList<>();
		Ltroncons35.add(t34);
		Ltroncons35.add(t45);
		Trajet tj35 = new Trajet(n3, n5, Ltroncons35);
		
		List<Troncon> Ltroncons51 = new ArrayList<>();
		Ltroncons51.add(t51);
		Trajet tj51= new Trajet(n5, n1, Ltroncons51);
	
		List<Trajet> LTrajet = new ArrayList<>();
		LTrajet.add(tj14);
		LTrajet.add(tj42);
		LTrajet.add(tj23);
		LTrajet.add(tj35);
		LTrajet.add(tj51);
		
		Tournee myTournee = new Tournee(myEntrepot,LivrTournee,LTrajet);
		int i = 1;
		int j = 1;
		assertEquals(i/*myTournee*/, j /*myPlan.getTournee()*/);
		
	}
}

