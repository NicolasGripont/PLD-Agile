package vue.PlanVilleVue;

import modeles.Noeud;
import modeles.Plan;
import modeles.Troncon;
import java.util.List;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class PlanVilleVue extends Canvas {
	private int RAYON_LIVRAISON = 5;
	private int RAYON_NOEUD = 1;
	private int LARGEUR_TRONCON = 1;
	
	public PlanVilleVue(double width, double height) {
		super(width, height);
	}
	
	public void dessinePlan(Plan plan) {
		if(plan != null) {
			if(plan.getNoeuds() != null) {
				dessineNoeud(plan.getNoeuds());
			}
			if(plan.getTroncons() != null) {
				dessineTroncon(plan.getTroncons());
			}
		} else {
			System.err.println("plan is null");
		}
	}
	
	public void dessineNoeud(List<Noeud> noeuds) {
		GraphicsContext gc = this.getGraphicsContext2D();
		gc.setFill(Color.BLACK);
        gc.setStroke(Color.BLACK);
		for(Noeud n : noeuds) {
			if(n != null) {
				gc.fillOval(n.getX(), n.getY(), RAYON_NOEUD, RAYON_NOEUD);
			}
		}
	}
	
	public void dessineTroncon(List<Troncon> troncons) {
		GraphicsContext gc = this.getGraphicsContext2D();
		gc.setFill(Color.BLACK);
        gc.setStroke(Color.BLACK);
		gc.setLineWidth(LARGEUR_TRONCON);
		for(Troncon t : troncons) {
			if(t != null && t.getOrigine() != null && t.getDestination() != null) {
				gc.strokeLine(t.getOrigine().getX(), t.getOrigine().getY(), t.getDestination().getX(), t.getDestination().getY());
			}
		}
	}
	
	public void dessineLivraison() {
		
	}
	
	public void dessineEntrepot() {
		
	}
	
	public void dessineChemin() {
		
	}
}
