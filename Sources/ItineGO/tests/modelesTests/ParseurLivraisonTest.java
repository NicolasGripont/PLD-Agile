package modelesTests;

import static org.junit.Assert.*;

import org.junit.Test;
import modeles.ParseurPlan;
import modeles.ParseurLivraison;
import modeles.Plan;

public class ParseurLivraisonTest {

	@Test
	public void testParseurLivraison() {
		Plan planTest = new Plan();
		String nomFichierPlanTest= "./tests/assetsForTests/plan5x5.xml";
		
		try{
			ParseurPlan.parseurPlanVille(nomFichierPlanTest, planTest);
			String nomFichierLivraisonTest = "./tests/assetsForTests/livraison5x5-4.xml";
			ParseurLivraison.parseurLivraisonVille(nomFichierLivraisonTest, planTest);
		}
		catch(Exception e){}
		
		int resNbLivraison = 4;
		System.out.println(planTest.getLivraisons().size());
		assertEquals(resNbLivraison,planTest.getLivraisons().size());
		
		int resId = 21;
		assertEquals(resId,planTest.getEntrepot().getNoeud().getId());
	}
	
	@Test
	public void testEntrepotInconnue()
	{
		fail("Not yet implemented");
	}
	
	@Test
	public void testPasDeLivraison()
	{
		fail("Not yet implemented");
	}
	

}
