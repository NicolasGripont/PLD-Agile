package modelesTests;

import static org.junit.Assert.*;

import org.junit.Test;

import modeles.Noeud;

public class NoeudTest {

	@Test
	public void testNoeud() {
		Noeud n1 = new Noeud(12,12,12);
		String res = "Noeud [id=12, x=12, y=12]";
		assertEquals(res,n1.toString());
	}
			
	@Test
	public void testGetId() {
		Noeud n1 = new Noeud(12,12,12);
		int res = 12;
		assertEquals(res,n1.getId());
	}

	@Test
	public void testSetId() {
		Noeud n1 = new Noeud(12,12,12);
		n1.setId(20);
		String res = "Noeud [id=20, x=12, y=12]";
		assertEquals(res,n1.toString());
	}

	@Test
	public void testGetX() {
		Noeud n1 = new Noeud(12,12,12);
		int res = 12;
		assertEquals(res,n1.getX());;
	}

	@Test
	public void testSetX() {
		Noeud n1 = new Noeud(12,12,12);
		n1.setX(20);
		String res = "Noeud [id=12, x=20, y=12]";
		assertEquals(res,n1.toString());;
	}

	@Test
	public void testGetY() {
		Noeud n1 = new Noeud(12,12,12);
		int res = 12;
		assertEquals(res,n1.getY());
	}

	@Test
	public void testSetY() {
		Noeud n1 = new Noeud(12,12,12);
		n1.setY(20);
		String res = "Noeud [id=12, x=12, y=20]";
		assertEquals(res,n1.toString());;
	}

	@Test
	public void testToString() {
		Noeud n1 = new Noeud(12,12,12);
		String res = "Noeud [id=12, x=12, y=12]";
		assertEquals(res,n1.toString());
	}

	@Test
	public void testEqualsObject() {
		Noeud n1 = new Noeud(12,12,12);
		Noeud n2 = new Noeud(12,12,12);
		Noeud n3 = new Noeud(20,20,20);
		
		assertEquals(n1.toString(),n2.toString());
		assertNotEquals(n1.toString(),n3.toString());
	}

}
