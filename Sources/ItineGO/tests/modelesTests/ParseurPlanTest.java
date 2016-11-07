package modelesTests;

import static org.junit.Assert.*;

import org.junit.Test;

import modeles.ParseurPlan;
import modeles.Plan;
import vue.choixPlanVilleVue.*;

public class ParseurPlanTest {

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
	
	@Test
	public void testMauvaisFormat()
	{
		Plan planTest = new Plan();
		
		String nomFichierTest = "./tests/assetsForTests/plan5x5-BadFormat.txt";
		String resTest ="";
		String res1 = "Erreur : Fichier XML mal formé";

		try{
			ParseurPlan.parseurPlanVille(nomFichierTest, planTest);
		}
		catch(Exception e)
		{
			resTest = e.getMessage();
			System.err.println(resTest);
		}
		
		assertEquals(res1,resTest);
	}
	
	
//	@Test
//	public void test2IdentifiantIdentique() 
//	{
//		Plan planTest = new Plan();
//		
//		String nomFichierTest = "./tests/assetsForTests/plan5x5-2ID.xml";
//		String resTest ="";
//		String res1 = "Erreur : Deux identifiants de noeud identiques";
//		
//		try{
//			ParseurPlan.parseurPlanVille(nomFichierTest, planTest);
//		}
//		catch(Exception e)
//		{
//			resTest = e.getMessage();
//		}
//		assertEquals(res1,resTest);
//		
//		int res2 = 0;
//		assertEquals(res2,planTest.getNoeuds().size());
//	}
	
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
