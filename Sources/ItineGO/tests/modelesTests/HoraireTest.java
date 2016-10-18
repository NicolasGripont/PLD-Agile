package modelesTests;

import static org.junit.Assert.*;

import org.junit.Test;

import modeles.Horaire;

public class HoraireTest {

	@Test
	public void testHoraireIntIntInt() {
		Horaire horaire = new Horaire(10,20,29);
		String res1 = "10:20:29";
		assertEquals(res1, horaire.toString());
	}
	
	@Test
	public void testHoraireIntIntIntMauvaiseHeure() {
		Horaire horaire = new Horaire(30,20,29);
		String res1 = "0:20:29";
		assertEquals(res1, horaire.toString());
	}
	
	@Test
	public void testHoraireIntIntIntMauvaiseMinute() {
		Horaire horaire = new Horaire(10,70,29);
		String res1 = "10:0:29";
		assertEquals(res1, horaire.toString());
	}
	
	@Test
	public void testHoraireIntIntIntMauvaiseSeconde() {
		Horaire horaire = new Horaire(10,20,129);
		String res1 = "10:20:0";
		assertEquals(res1, horaire.toString());
	}

	@Test
	public void testHoraireString() {
		Horaire horaire = new Horaire("10:20:29");
		String res1 = "10:20:29";
		assertEquals(res1, horaire.toString());
	}
	
	@Test
	public void testHoraireMalForme1() {
		Horaire horaire = new Horaire("helloworld");
		String res1 = "0:0:0";
		assertEquals(res1, horaire.toString());
	}
	
	@Test
	public void testHoraireMalForme2() {
		Horaire horaire = new Horaire("fskjfqsnfqsjkn:qsfnkqflnkfq:qfsqsfqsfoif");
		String res1 = "0:0:0";
		assertEquals(res1, horaire.toString());
	}
	
	@Test
	public void testHoraireMalForme3() {
		Horaire horaire = new Horaire("-10:29293:02023");
		String res1 = "0:0:0";
		assertEquals(res1, horaire.toString());
	}

	@Test
	public void testAjouterHeure() {
		Horaire horaire = new Horaire("10:20:29");
		horaire.ajouterHeure(2);
		assertEquals("12:20:29", horaire.toString());
		horaire.ajouterHeure(-2);
		assertEquals("10:20:29", horaire.toString());
		horaire.ajouterHeure(26);
		assertEquals("12:20:29", horaire.toString());
		horaire.ajouterHeure(-24);
		assertEquals("12:20:29", horaire.toString());
	}

	@Test
	public void testAjouterMinute() {
		Horaire horaire = new Horaire("10:20:29");
		horaire.ajouterMinute(20);
		assertEquals("10:40:29", horaire.toString());
		horaire.ajouterMinute(-20);
		assertEquals("10:20:29", horaire.toString());
		horaire.ajouterMinute(121);
		assertEquals("12:21:29", horaire.toString());
		horaire.ajouterMinute(-119);
		assertEquals("11:38:29", horaire.toString());
	}

	@Test
	public void testAjouterSeconde() {
		Horaire horaire = new Horaire("10:20:29");
		horaire.ajouterSeconde(20);
		System.out.println(horaire);
		assertEquals("10:20:49", horaire.toString());
		horaire.ajouterSeconde(-20);
		System.out.println(horaire);
		assertEquals("10:20:29", horaire.toString());
		horaire.ajouterSeconde(121);
		System.out.println(horaire);
		assertEquals("10:22:30", horaire.toString());
		horaire.ajouterSeconde(-119);
		System.out.println(horaire);
		assertEquals("10:21:29", horaire.toString());
	}

	@Test
	public void testSetHeure() {
		Horaire horaire = new Horaire("10:20:29");
		horaire.setHeure(20);
		String res1 = "20:20:29";
		assertEquals(res1, horaire.toString());
	}
	
	@Test
	public void testSetHeureMauvais() {
		Horaire horaire = new Horaire("10:20:29");
		horaire.setHeure(28);
		String res1 = "0:20:29";
		assertEquals(res1, horaire.toString());
	}

	@Test
	public void testSetMinute() {
		Horaire horaire = new Horaire("10:20:29");
		horaire.setMinute(40);
		String res1 = "10:40:29";
		assertEquals(res1, horaire.toString());
	}
	
	@Test
	public void testSetMinuteMauvais() {
		Horaire horaire = new Horaire("10:20:29");
		horaire.setMinute(80);
		String res1 = "10:0:29";
		assertEquals(res1, horaire.toString());
	}

	@Test
	public void testSetSeconde() {
		Horaire horaire = new Horaire("10:20:29");
		horaire.setSeconde(30);
		String res1 = "10:20:30";
		assertEquals(res1, horaire.toString());
	}
	
	@Test
	public void testSetSecondeMauvais() {
		Horaire horaire = new Horaire("10:20:29");
		horaire.setSeconde(70);
		String res1 = "10:20:0";
		assertEquals(res1, horaire.toString());
	}

	@Test
	public void testEqualsObject() {
		Horaire horaire1 = new Horaire("10:20:29");
		Horaire horaire2 = new Horaire(10,20,29);
		Horaire horaire3 = new Horaire("10:20:59");
		assertEquals(horaire1, horaire2);
		assertNotEquals(horaire1,horaire3);
	}
}
