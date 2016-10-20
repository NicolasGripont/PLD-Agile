package modelesTests;

import static org.junit.Assert.*;

import org.junit.Test;
import modeles.Horaire;
import modeles.Livraison;
import modeles.Noeud;

public class LivraisonTest {

	@Test
	public void testLivraisonNoeudIntIntIntIntIntIntInt() {
		Noeud noeud = new Noeud(12,12,12);
		Livraison livraison = new Livraison(noeud, 4, 10, 20, 0, 10, 25, 0);
		String res1 = "Livraison [noeud=Noeud [id=12, x=12, y=12], duree=4, debutPlage=10:20:0, finPlage=10:25:0]";
		assertEquals(res1,livraison.toString());
	}

	@Test
	public void testLivraisonHorairesImpossible() {
		Noeud noeud = new Noeud(12,12,12);
		Livraison livraison = new Livraison(noeud, 4, 25, 78, 1000, 1000, -90, 689);
		String res1 = "Livraison [noeud=Noeud [id=12, x=12, y=12], duree=4, debutPlage=0:0:0, finPlage=0:0:0]";
		assertEquals(res1,livraison.toString());
	}
	
	@Test
	public void testLivraisonNoeudIntHoraireHoraire() {
		Noeud noeud = new Noeud(12,12,12);
		Horaire h1 = new Horaire(10, 20, 0);
		Horaire h2 = new Horaire(10, 25, 0);
		Livraison livraison = new Livraison(noeud, 4, h1, h2);
		String res1 = "Livraison [noeud=Noeud [id=12, x=12, y=12], duree=4, debutPlage=10:20:0, finPlage=10:25:0]";
		assertEquals(res1,livraison.toString());
	}

	@Test
	public void testLivraisonNoeudIntHoraireHoraireAvecNull() {
		Noeud noeud = new Noeud(12,12,12);
		Livraison livraison = new Livraison(noeud, 4, new Horaire(10, 20, 0), null);
		String res1 = "Livraison [noeud=Noeud [id=12, x=12, y=12], duree=4, debutPlage=10:20:0, finPlage=0:0:0]";
		assertEquals(res1,livraison.toString());
	}
	
	@Test
	public void testLivraisonNoeudIntStringString() {
		Noeud noeud = new Noeud(12,12,12);
		Livraison livraison = new Livraison(noeud, 4, "10:20:0", "10:25:0");
		String res1 = "Livraison [noeud=Noeud [id=12, x=12, y=12], duree=4, debutPlage=10:20:0, finPlage=10:25:0]";
		assertEquals(res1,livraison.toString());
	}
	
	@Test
	public void testLivraisonNoeudIntStringStringMalForme() {
		Noeud noeud = new Noeud(12,12,12);
		Livraison livraison = new Livraison(noeud, 4, "10:20:0", "dzdzedzzduhiuddez:dznded");
		String res1 = "Livraison [noeud=Noeud [id=12, x=12, y=12], duree=4, debutPlage=10:20:0, finPlage=0:0:0]";
		assertEquals(res1,livraison.toString());
	}

	@Test
	public void testEqualsObject() {
		Noeud noeud = new Noeud(12,12,12);
		Livraison livraison = new Livraison(noeud, 4, "10:20:0", "10:25:0");
		Livraison livraison2 = new Livraison(noeud, 4, "10:20:0", "10:25:0");
		Noeud noeud2 = new Noeud(13,42,12);
		Livraison livraison3 = new Livraison(noeud2, 4, "10:50:0", "10:70:0");
		assertEquals(livraison, livraison2);
		assertNotEquals(livraison, livraison3);
	}

	@Test
	public void testSetNoeud() {
		Noeud noeud = new Noeud(12,12,12);
		Livraison livraison = new Livraison(noeud, 4, "10:20:0", "10:25:0");
		livraison.setNoeud(null);
		String res1 = "Livraison [noeud=Noeud [id=-1, x=0, y=0], duree=4, debutPlage=10:20:0, finPlage=10:25:0]";
		assertEquals(res1,livraison.toString());
	}

	@Test
	public void testSetDebutPlage() {
		Noeud noeud = new Noeud(12,12,12);
		Livraison livraison = new Livraison(noeud, 4, "10:20:0", "10:25:0");
		livraison.setDebutPlage(null);
		String res1 = "Livraison [noeud=Noeud [id=12, x=12, y=12], duree=4, debutPlage=0:0:0, finPlage=10:25:0]";
		assertEquals(res1,livraison.toString());
	}

	@Test
	public void testSetFinPlage() {
		Noeud noeud = new Noeud(12,12,12);
		Livraison livraison = new Livraison(noeud, 4, "10:20:0", "10:25:0");
		livraison.setFinPlage(null);
		String res1 = "Livraison [noeud=Noeud [id=12, x=12, y=12], duree=4, debutPlage=10:20:0, finPlage=0:0:0]";
		assertEquals(res1,livraison.toString());
	}

	@Test
	public void testSetDuree() {
		Noeud noeud = new Noeud(12,12,12);
		Livraison livraison = new Livraison(noeud, 4, "10:20:0", "10:25:0");
		livraison.setDuree(-20);
		String res1 = "Livraison [noeud=Noeud [id=12, x=12, y=12], duree=0, debutPlage=10:20:0, finPlage=10:25:0]";
		assertEquals(res1,livraison.toString());
		livraison.setDuree(20);
		String res2 = "Livraison [noeud=Noeud [id=12, x=12, y=12], duree=20, debutPlage=10:20:0, finPlage=10:25:0]";
		assertEquals(res2,livraison.toString());
	}

}
