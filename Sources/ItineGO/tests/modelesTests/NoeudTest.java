package modelesTests;

import static org.junit.Assert.*;

import org.junit.Test;

import modeles.Noeud;

/**
 * Classe test unitaire de la classe Noeud
 *
 */

public class NoeudTest {

	/**
	 * Test du constructeur de la classe Noeud avec trois entiers
	 * 
	 * Resultat: L'affiche du noeud doit correspondre au resultat souhaité
	 */
	@Test
	public void testNoeud() {
		Noeud n1 = new Noeud(12,12,12);
		String res = "Noeud [id=12, x=12, y=12]";
		assertEquals(res,n1.toString());
	}
	
	/**
	 * Test du getter du noeud 
	 * 
	 * Resultat: Doit retourner l'identifiant du noeud
	 */
	@Test
	public void testGetId() {
		Noeud n1 = new Noeud(12,12,12);
		int res = 12;
		assertEquals(res,n1.getId());
	}

	/**
	 * Test de modification de l'identifiant
	 * 
	 * Resultat:  L'affiche du noeud doit correspondre au resultat souhaité
	 */
	@Test
	public void testSetId() {
		Noeud n1 = new Noeud(12,12,12);
		n1.setId(20);
		String res = "Noeud [id=20, x=12, y=12]";
		assertEquals(res,n1.toString());
	}

	/**
	 * Test du getter du point x 
	 * 
	 * Resultat: Doit retourner le point x du noeud
	 */
	@Test
	public void testGetX() {
		Noeud n1 = new Noeud(12,12,12);
		int res = 12;
		assertEquals(res,n1.getX());;
	}

	/**
	 * Test de modification du point x
	 * 
	 * Resultat:  L'affiche du noeud doit correspondre au resultat souhaité
	 */
	@Test
	public void testSetX() {
		Noeud n1 = new Noeud(12,12,12);
		n1.setX(20);
		String res = "Noeud [id=12, x=20, y=12]";
		assertEquals(res,n1.toString());;
	}

	/**
	 * Test du getter du point y 
	 * 
	 * Resultat: Doit retourner le point y du noeud
	 */
	@Test
	public void testGetY() {
		Noeud n1 = new Noeud(12,12,12);
		int res = 12;
		assertEquals(res,n1.getY());
	}

	/**
	 * Test de modification du point y
	 * 
	 * Resultat:  L'affiche du noeud doit correspondre au resultat souhaité
	 */
	@Test
	public void testSetY() {
		Noeud n1 = new Noeud(12,12,12);
		n1.setY(20);
		String res = "Noeud [id=12, x=12, y=20]";
		assertEquals(res,n1.toString());;
	}

	/**
	 * Test de l'afichage texte du noeud
	 * 
	 * Resultat:  L'affiche du noeud doit correspondre au resultat souhaité
	 */
	@Test
	public void testToString() {
		Noeud n1 = new Noeud(12,12,12);
		String res = "Noeud [id=12, x=12, y=12]";
		assertEquals(res,n1.toString());
	}

	/**
	 * Test qui vérifie que 2 objets de type Noeud sont égales et 2 autres sont différents
	 * 
	 * Resultat: Premier est égale, deuxiéme est différent
	 */
	@Test
	public void testEqualsObject() {
		Noeud n1 = new Noeud(12,12,12);
		Noeud n2 = new Noeud(12,12,12);
		Noeud n3 = new Noeud(20,20,20);
		
		assertEquals(n1.toString(),n2.toString());
		assertNotEquals(n1.toString(),n3.toString());
	}

}
