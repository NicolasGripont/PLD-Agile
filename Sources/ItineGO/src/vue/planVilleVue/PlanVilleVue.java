package vue.planVilleVue;

import modeles.Livraison;
import modeles.Noeud;
import modeles.Plan;
import modeles.Trajet;
import modeles.Troncon;
import utility.Pair;
import java.util.Map;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class PlanVilleVue extends Canvas {
	private int RAYON_LIVRAISON = 5;
	private int RAYON_NOEUD = 0;
	private int LARGEUR_TRONCON = 3;
	private double zoom = 1;
	private Plan plan;
	
	public PlanVilleVue(double width, double height) {
		super(width, height);
		this.setStyle("-fx-background-color: rgb(240,237,230);");
	}
	
	public void dessinerPlan(Plan plan) {
		dessinerFond();
		if(plan != null) {
			this.plan = plan;
			calculerZoom();
			if(plan.getNoeuds() != null) {
				dessinerNoeud();
			}
			if(plan.getTroncons() != null) {
				dessinerTroncon();
			}
			if(plan.getTournee() != null) {
				dessinerTournee();
			}
			if(plan.getEntrepot() != null) {
				dessinerEntrepot();
			}
			if(plan.getLivraisons() != null) {
				dessinerLivraison();
			}
		} else {
			System.err.println("plan is null");
		}
	}
	

	private void dessinerFond() {
		GraphicsContext gc = this.getGraphicsContext2D();
		gc.setFill(new Color(0.9375,0.9258,0.8945,1));
        gc.setStroke(new Color(0.9375,0.9258,0.8945,1));
		gc.fillRect(0, 0, getWidth(), getHeight());
	}

	private void dessinerNoeud() {
		GraphicsContext gc = this.getGraphicsContext2D();
		gc.setFill(new Color(0.859,0.839,0.808,1));
        gc.setStroke(Color.WHITE);
		for(Map.Entry<Integer, Noeud> n : this.plan.getNoeuds().entrySet()) {
			if(n != null) {
				gc.fillOval(n.getValue().getX() * zoom, n.getValue().getY() * zoom, RAYON_NOEUD, RAYON_NOEUD);
			}
		}
	}
	
	private void dessinerTroncon() {
		GraphicsContext gc = this.getGraphicsContext2D();
		gc.setFill(new Color(0.859,0.839,0.808,1));
        gc.setStroke(Color.WHITE);
		gc.setLineWidth(LARGEUR_TRONCON);
		for(Map.Entry<Pair<Noeud, Noeud>, Troncon> t : this.plan.getTroncons().entrySet()) {
			if(t != null && t.getKey().first != null && t.getKey().second != null) {
				gc.strokeLine(t.getKey().first.getX() * zoom, t.getKey().first.getY() * zoom,
						t.getKey().second.getX() * zoom, t.getKey().second.getY() * zoom);
			}
		}
	}
	
	public void dessinerLivraison() {
		GraphicsContext gc = this.getGraphicsContext2D();
		gc.setFill(Color.GREEN);
        gc.setStroke(Color.GREEN);
        for(Map.Entry<Noeud, Livraison> l : this.plan.getLivraisons().entrySet()) {
			if(l != null && l.getKey() != null) {
				gc.fillOval(l.getKey().getX() * zoom, l.getKey().getY() * zoom, RAYON_LIVRAISON, RAYON_LIVRAISON);
			}
		}
	}
	
	public void dessinerEntrepot() {
		GraphicsContext gc = this.getGraphicsContext2D();
		gc.setFill(Color.RED);
        gc.setStroke(Color.RED);
        if(plan.getEntrepot() != null && plan.getEntrepot().getNoeud() != null) {
        	gc.fillOval(plan.getEntrepot().getNoeud().getX() * zoom, plan.getEntrepot().getNoeud().getY() * zoom, RAYON_LIVRAISON, RAYON_LIVRAISON);
        }
	}
	
	private void dessinerTournee() {
		GraphicsContext gc = this.getGraphicsContext2D();
		gc.setFill(new Color(0,0.709,0.968,1));
        gc.setStroke(new Color(0,0.709,0.968,1));
		gc.setLineWidth(LARGEUR_TRONCON);
		for(Trajet trajet : plan.getTournee().getTrajets()){
			for(Troncon t : trajet.getTroncons()) {
				if(t != null && t.getOrigine() != null && t.getDestination() != null) {
					gc.strokeLine(t.getOrigine().getX() * zoom, t.getOrigine().getY() * zoom,
							t.getDestination().getX() * zoom, t.getDestination().getY() * zoom);
				}
			}
		}
	}
	
	private void calculerZoom() {
		if(plan.getNoeuds() != null) {
			int minX = Integer.MAX_VALUE;
			int maxX = Integer.MIN_VALUE;
			for(Map.Entry<Integer, Noeud> n : plan.getNoeuds().entrySet()) {
				if(n != null) {
					if(n.getValue().getX() <= minX) {
						minX = n.getValue().getX();
					} else if(n.getValue().getX() >= maxX) {
						maxX = n.getValue().getX();
					}
				}
			}
			double widthMap = maxX+minX;
			double width = this.getWidth();
			this.zoom = width/(widthMap) ;
		} else {
			this.zoom = 1;
		}
	}
	
	public void resize(double width, double height){
        setWidth(width);
		setHeight(height);
	}
	
}
