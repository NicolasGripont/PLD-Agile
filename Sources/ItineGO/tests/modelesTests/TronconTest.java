package modelesTests;

import static org.junit.Assert.*;

import org.junit.Test;

import modeles.Troncon;
import modeles.Noeud;


/**
 * Classe test unitaire de la classe Troncon
 *
 */
public class TronconTest {

	/**
	 * Test du constructeur de la classe Troncon avec un nom, une longeur, une vitesse et un noeud de départ et d'arrivé
	 * 
	 * Resultat: L'affiche du troncon doit correspondre au resultat souhaité
	 */
	@Test
	public void testTroncon() {
		Noeud origine = new Noeud(20,20,20);
		Noeud destination = new Noeud(30,30,30);
		Troncon t1 = new Troncon("p1",12,12,origine,destination);
		String res = "Troncon [nomRue=p1, longueur=12, vitesse=12, origine=Noeud [id=20, x=20, y=20], destination=Noeud [id=30, x=30, y=30]]";
		assertEquals(res,t1.toString());
	}

	/**
	 * Test retournant le nom du troncon
	 * 
	 * Resultat: L'affiche du troncon doit correspondre au resultat souhaité
	 */
	@Test
	public void testGetNomRue() {
		Noeud origine = new Noeud(20,20,20);
		Noeud destination = new Noeud(30,30,30);
		Troncon t1 = new Troncon("p1",12,12,origine,destination);
		String res = "p1";
		assertEquals(res,t1.getNomRue());
	}

	/**
	 * Test modifiant le nom du troncon
	 * 
	 * Resultat: L'affiche du troncon doit correspondre au resultat souhaité
	 */
	@Test
	public void testSetNomRue() {
		Noeud origine = new Noeud(20,20,20);
		Noeud destination = new Noeud(30,30,30);
		Troncon t1 = new Troncon("p1",12,12,origine,destination);
		t1.setNomRue("q1");
		String res = "Troncon [nomRue=q1, longueur=12, vitesse=12, origine=Noeud [id=20, x=20, y=20], destination=Noeud [id=30, x=30, y=30]]";
		assertEquals(res,t1.toString());
	}

	/**
	 * Test retournant la longeur du troncon
	 * 
	 * Resultat: L'affiche du troncon doit correspondre au resultat souhaité
	 */
	@Test
	public void testGetLongueur() {
		Noeud origine = new Noeud(20,20,20);
		Noeud destination = new Noeud(30,30,30);
		Troncon t1 = new Troncon("p1",12,12,origine,destination);
		int res = 12;
		assertEquals(res,t1.getLongueur());
	}

	/**
	 * Test modifiant la longeur du troncon
	 * 
	 * Resultat: L'affiche du troncon doit correspondre au resultat souhaité
	 */
	@Test
	public void testSetLongueur() {
		Noeud origine = new Noeud(20,20,20);
		Noeud destination = new Noeud(30,30,30);
		Troncon t1 = new Troncon("p1",12,12,origine,destination);
		t1.setLongueur(50);
		String res = "Troncon [nomRue=p1, longueur=50, vitesse=12, origine=Noeud [id=20, x=20, y=20], destination=Noeud [id=30, x=30, y=30]]";
		assertEquals(res,t1.toString());
	}

	/**
	 * Test retournant la vitesse du troncon
	 * 
	 * Resultat: L'affiche du troncon doit correspondre au resultat souhaité
	 */
	@Test
	public void testGetVitesse() {
		Noeud origine = new Noeud(20,20,20);
		Noeud destination = new Noeud(30,30,30);
		Troncon t1 = new Troncon("p1",12,12,origine,destination);
		int res = 12;
		assertEquals(res,t1.getVitesse());
	}

	/**
	 * Test modifiant la vitesse du troncon
	 * 
	 * Resultat: L'affiche du troncon doit correspondre au resultat souhaité
	 */
	@Test
	public void testSetVitesse() {
		Noeud origine = new Noeud(20,20,20);
		Noeud destination = new Noeud(30,30,30);
		Troncon t1 = new Troncon("p1",12,12,origine,destination);
		t1.setVitesse(50);
		String res = "Troncon [nomRue=p1, longueur=12, vitesse=50, origine=Noeud [id=20, x=20, y=20], destination=Noeud [id=30, x=30, y=30]]";
		assertEquals(res,t1.toString());
	}

	/**
	 * Test retournant l'origine du troncon
	 * 
	 * Resultat: L'affiche du troncon doit correspondre au resultat souhaité
	 */
	@Test
	public void testGetOrigine() {
		Noeud origine = new Noeud(20,20,20);
		Noeud destination = new Noeud(30,30,30);
		Troncon t1 = new Troncon("p1",12,12,origine,destination);
		String res = "Noeud [id=20, x=20, y=20]";
		assertEquals(res,t1.getOrigine().toString());
	}

	/**
	 * Test modifiant l'origine du troncon
	 * 
	 * Resultat: L'affiche du troncon doit correspondre au resultat souhaité
	 */
	@Test
	public void testSetOrigine() {
		Noeud origine = new Noeud(20,20,20);
		Noeud destination = new Noeud(30,30,30);
		Troncon t1 = new Troncon("p1",12,12,origine,destination);
		Noeud n1 = new Noeud(10,10,10);
		t1.setOrigine(n1);
		String res = "Troncon [nomRue=p1, longueur=12, vitesse=12, origine=Noeud [id=10, x=10, y=10], destination=Noeud [id=30, x=30, y=30]]";
		assertEquals(res,t1.toString());
	}

	/**
	 * Test retournant la destination du troncon
	 * 
	 * Resultat: L'affiche du troncon doit correspondre au resultat souhaité
	 */
	@Test
	public void testGetDestination() {
		Noeud origine = new Noeud(20,20,20);
		Noeud destination = new Noeud(30,30,30);
		Troncon t1 = new Troncon("p1",12,12,origine,destination);
		String res = "Noeud [id=30, x=30, y=30]";
		assertEquals(res,t1.getDestination().toString());
	}

	/**
	 * Test modifiant la destination du troncon
	 * 
	 * Resultat: L'affiche du troncon doit correspondre au resultat souhaité
	 */
	@Test
	public void testSetDestination() {
		Noeud origine = new Noeud(20,20,20);
		Noeud destination = new Noeud(30,30,30);
		Troncon t1 = new Troncon("p1",12,12,origine,destination);
		Noeud n1 = new Noeud(15,15,15);
		t1.setDestination(n1);
		String res = "Troncon [nomRue=p1, longueur=12, vitesse=12, origine=Noeud [id=20, x=20, y=20], destination=Noeud [id=15, x=15, y=15]]";
		assertEquals(res,t1.toString());;
	}

	/**
	 * Test de l'affichage texte de la classe Troncon
	 * 
	 * Resultat: L'affiche du troncon doit correspondre au resultat souhaité 
	 */
	@Test
	public void testToString() {
		Noeud origine = new Noeud(20,20,20);
		Noeud destination = new Noeud(30,30,30);
		Troncon t1 = new Troncon("p1",12,12,origine,destination);
		String res = "Troncon [nomRue=p1, longueur=12, vitesse=12, origine=Noeud [id=20, x=20, y=20], destination=Noeud [id=30, x=30, y=30]]";
		assertEquals(res,t1.toString());
	}

	/**
	 * Test qui vérifie que 2 objets de type troncon sont égales et 2 autres sont différents
	 * 
	 * Resultat: Premier est égale, deuxiéme est différent
	 */
	@Test
	public void testEqualsObject() {
		Noeud origine1 = new Noeud(20,20,20);
		Noeud destination1 = new Noeud(30,30,30);
		Troncon t1 = new Troncon("p1",12,12,origine1,destination1);
		Troncon t2 = new Troncon("p1",12,12,origine1,destination1);
		
		Noeud origine2 = new Noeud(15,15,15);
		Noeud destination2 = new Noeud(40,40,40);
		Troncon t3 = new Troncon("p1",12,12,origine2,destination2);
		
		assertEquals(t1.toString(),t2.toString());
		assertNotEquals(t1.toString(),t3.toString());
	}

}
