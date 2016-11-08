package modelesTests;

import static org.junit.Assert.*;

import java.io.File;

import org.junit.Test;

import modeles.ParseurPlan;
import modeles.Plan;
import vue.glisserDeposerFichierVue.*;

/**
 * Classe test unitaire de la classe ParseurPlan
 *
 */
public class ParseurPlanTest {

	/**
	 * Test du Parseur sur un fichier Plan correct
	 * 
	 * Resultat: Le fichier est parsé 
	 */
	@Test
	public void testParseurPlan()
	{
		Plan planTest = new Plan();
		String nomFichierTest = "./tests/assetsForTests/plan5x5.xml";
		
		try{
			ParseurPlan.parseurPlanVille(nomFichierTest, planTest);
		}
		catch(Exception e){}
		
		int resNbNoeud= 25;
		int resNbTroncon = 56;
		
		assertEquals(resNbNoeud,planTest.getNoeuds().size());
		assertEquals(resNbTroncon,planTest.getTroncons().size());
	}
	
//	/**
//	 * Test du Parseur sur un fichier Plan au mauvais dormat 
//	 * 
//	 * Resultat: Le fichier n'est pas parsé et renvoie une exception
//	 */
	@Test
	public void testMauvaisFormat()
	{
		Plan planTest = new Plan();
		
		String nomFichierTest = "./tests/assetsForTests/plan5x5-BadFormat.txt";
		File xmlFile = new File(nomFichierTest);
		GlisserDeposerFichierVue testVue = new GlisserDeposerFichierVue();
		testVue.addExtensionAcceptee(".xml");
		assertEquals(false,testVue.estFichierAccepte(xmlFile));
	}
	
	/**
	 * Test du Parseur sur un fichier Plan ayant deux identifiants identiques
	 * 
	 * Resultat: Le fichier n'est pas parsé et renvoie une exception
	 */
	@Test
	public void test2IdentifiantIdentique() 
	{
		Plan planTest = new Plan();
		
		String nomFichierTest = "./tests/assetsForTests/plan5x5-2ID.xml";
		String resTest ="";
		String res1 = "Erreur : Deux identifiants de noeud identiques";
		
		try{
			ParseurPlan.parseurPlanVille(nomFichierTest, planTest);
		}
		catch(Exception e)
		{
			resTest = e.getMessage();
		}
		assertEquals(res1,resTest);
		
		int res2 = 0;
		assertEquals(res2,planTest.getNoeuds().size());
	}
	
	/**
	 * Test du Parseur sur un fichier Plan ayant un troncon dont l'origine est un noeud non connue
	 * 
	 * Resultat: Le fichier n'est pas parsé et renvoie une exception
	 */
	@Test
	public void testOrigineInconnue()
	{
		Plan planTest = new Plan();
		
		String nomFichierTest = "./tests/assetsForTests/plan5x5-UknOrigine.xml";
		String resTest ="";
		String res1 = "Erreur : Fichier plan non valide";
		
		try{
			ParseurPlan.parseurPlanVille(nomFichierTest, planTest);
		}
		catch(Exception e)
		{
			resTest = e.getMessage();
		}
		assertEquals(res1,resTest);
		
		int res2 = 0;
		assertEquals(res2,planTest.getNoeuds().size());
		
		int res3 = 0;
		assertEquals(res3,planTest.getTroncons().size());
	}
	
	/**
	 * Test du Parseur sur un fichier Plan ayant un troncon dont la destination est un noeud non connue
	 * 
	 * Resultat: Le fichier n'est pas parsé et renvoie une exception
	 */
	@Test
	public void testDestinationInconnue()
	{
		Plan planTest = new Plan();
		
		String nomFichierTest = "./tests/assetsForTests/plan5x5-UknDestination.xml";
		String resTest ="";
		String res1 = "Erreur : Fichier plan non valide";
		
		try{
			ParseurPlan.parseurPlanVille(nomFichierTest, planTest);
		}
		catch(Exception e)
		{
			resTest = e.getMessage();
		}
		assertEquals(res1,resTest);
		
		int res2 = 0;
		assertEquals(res2,planTest.getNoeuds().size());
		
		int res3 = 0;
		assertEquals(res3,planTest.getTroncons().size());
	}
	
	/**
	 * Test du Parseur sur un fichier Plan ayant un identifiant de noeud ou de tronçon à -1
	 * 
	 * Resultat: Le fichier n'est pas parsé et renvoie une exception
	 */
	@Test
	public void testIdentifiantMoinsUn()
	{
		Plan planTest = new Plan();
		
		String nomFichierTest = "./tests/assetsForTests/plan5x5-ID-1.xml";
		String resTest ="";
		String res1 = "Erreur : Fichier plan non valide";
		
		try{
			ParseurPlan.parseurPlanVille(nomFichierTest, planTest);
		}
		catch(Exception e)
		{
			resTest = e.getMessage();
		}
		assertEquals(res1,resTest);
		
		int res2 = 0;
		assertEquals(res2,planTest.getNoeuds().size());
		
		int res3 = 0;
		assertEquals(res3,planTest.getTroncons().size());
	}
	
	/**
	 * Test du Parseur sur un fichier Plan ayant un seul noeud
	 * 
	 * Resultat: Le fichier n'est pas parsé et renvoie une exception
	 */
	@Test
	public void testUnNoeud()
	{
		Plan planTest = new Plan();
		
		String nomFichierTest = "./tests/assetsForTests/plan5x5-1Noeud.xml";
		String resTest ="";
		String res1 = "Erreur : Fichier plan non valide";
		try{
			ParseurPlan.parseurPlanVille(nomFichierTest, planTest);
		}
		catch(Exception e)
		{
			resTest = e.getMessage();
		}
		assertEquals(res1,resTest);
	}
}
