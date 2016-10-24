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
	private int RAYON_LIVRAISON = 10;
	private int RAYON_NOEUD = 10;
	private int LARGEUR_TRONCON = 3;
	private double zoom = 1;
	private double offsetX = 0;
	private double offsetY = 0;
	private Plan plan;
	
	public PlanVilleVue(double width, double height) {
		super(width, height);
		this.setStyle("-fx-background-color: rgb(240,237,230);");
	}
	
	public void dessinerPlan(Plan plan) {
		effacer();
		if(plan != null) {
			this.plan = plan;
			dessinerFond();
			calculerZoom();
			recentrerPlan();			
			if(plan.getTroncons() != null) {
				dessinerTroncon();
			}
			if(plan.getNoeuds() != null) {
				dessinerNoeud();
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
	
	private void effacer(){
		GraphicsContext gc = this.getGraphicsContext2D();
		gc.clearRect(0, 0, getWidth(), getHeight());
	}
	
	private void dessinerFond() {
		GraphicsContext gc = this.getGraphicsContext2D();
		gc.setFill(new Color(0.250,0.250,0.250,1));
		gc.fillRect(0, 0, getWidth(), getHeight());
	}

	private void dessinerNoeud() {
		GraphicsContext gc = this.getGraphicsContext2D();
		for(Map.Entry<Integer, Noeud> n : this.plan.getNoeuds().entrySet()) {
			if(n != null) {
				gc.setFill(new Color(0.859,0.839,0.808,1));
				gc.fillOval(n.getValue().getX() * zoom + offsetX - RAYON_NOEUD /2, n.getValue().getY() * zoom + offsetY - RAYON_NOEUD /2,
						RAYON_NOEUD, RAYON_NOEUD);
				gc.setFill(new Color(0.250,0.250,0.250,1));
				gc.fillOval(n.getValue().getX() * zoom + offsetX - (RAYON_NOEUD-4)/2, n.getValue().getY() * zoom + offsetY - (RAYON_NOEUD - 4) /2
						, RAYON_NOEUD - 4, RAYON_NOEUD - 4);
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
				gc.strokeLine(t.getKey().first.getX() * zoom + offsetX, t.getKey().first.getY() * zoom + offsetY,
						t.getKey().second.getX() * zoom + offsetX, t.getKey().second.getY() * zoom + offsetY);
			}
		}
	}
	
	public void dessinerLivraison() {
		GraphicsContext gc = this.getGraphicsContext2D();
		gc.setFill(Color.GREEN);
        gc.setStroke(Color.GREEN);
        for(Map.Entry<Noeud, Livraison> l : this.plan.getLivraisons().entrySet()) {
			if(l != null && l.getKey() != null) {
				gc.fillOval(l.getKey().getX() * zoom + offsetX - RAYON_LIVRAISON/2, l.getKey().getY() * zoom + offsetY - RAYON_LIVRAISON/2
						, RAYON_LIVRAISON, RAYON_LIVRAISON);
			}
		}
	}
	
	public void dessinerEntrepot() {
		GraphicsContext gc = this.getGraphicsContext2D();
		gc.setFill(Color.RED);
        gc.setStroke(Color.RED);
        if(plan.getEntrepot() != null && plan.getEntrepot().getNoeud() != null) {
        	gc.fillOval(plan.getEntrepot().getNoeud().getX() * zoom + offsetX - RAYON_LIVRAISON/2,
        			plan.getEntrepot().getNoeud().getY() * zoom + offsetY - RAYON_LIVRAISON/2, RAYON_LIVRAISON, RAYON_LIVRAISON);
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
					gc.strokeLine(t.getOrigine().getX() * zoom + offsetX, t.getOrigine().getY() * zoom + offsetY,
							t.getDestination().getX() * zoom + offsetX, t.getDestination().getY() * zoom + offsetY);
				}
			}
		}
	}
	
	private void calculerZoom() {
		if(plan.getNoeuds() != null) {
			double width = this.getWidth();
			double height = this.getHeight();
			if(width == Math.min(width, height)) {
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
				this.zoom = width/(widthMap) ;
			} else {
				int minY = Integer.MAX_VALUE;
				int maxY = Integer.MIN_VALUE;
				for(Map.Entry<Integer, Noeud> n : plan.getNoeuds().entrySet()) {
					if(n != null) {
						if(n.getValue().getY() <= minY) {
							minY = n.getValue().getY();
						} else if(n.getValue().getY() >= maxY) {
							maxY = n.getValue().getY();
						}
					}
				}
				double heightMap = maxY+minY;
				this.zoom = height/(heightMap) ;
			}
			
		} else {
			this.zoom = 1;
		}
	}
	
	private void recentrerPlan() {
		offsetY = 0;
		offsetX = 0;
		double width = this.getWidth();
		double height = this.getHeight();
		if(width == Math.min(width, height)) {
			offsetY = (height - width) / 2;
		} else {
			offsetX = (width - height) / 2;
		}
	}
	
	public void resize(double width, double height){
        setWidth(width);
		setHeight(height);
	}
	
}
