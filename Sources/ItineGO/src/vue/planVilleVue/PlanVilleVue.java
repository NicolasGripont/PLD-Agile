package vue.planVilleVue;

import modeles.Entrepot;
import modeles.Livraison;
import modeles.Noeud;
import modeles.Plan;
import modeles.Trajet;
import modeles.Troncon;
import utility.Pair;
import vue.gestionVue.GestionVue;
import java.util.Map;

import javafx.event.EventHandler;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.TableView;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;

/**
 * Classe permettant d'afficher le plan de la ville et toutes les informations qu'il contient
 * 		Noeuds, tronçons, livraisons, entrepôt, tournée calculée...
 */
public class PlanVilleVue extends Canvas {
	private int RAYON_LIVRAISON = 15;
	private int RAYON_NOEUD = 10;
	private int LARGEUR_TRONCON = 3;
	private double zoom = 1;
	private double offsetX = 0;
	private double offsetY = 0;
	private double pointerMargin = 10;
	private Noeud noeudSelectionned = null;
	private Troncon tronconSelectionned = null;
	private Plan plan;
	private boolean modeAjouterLivraison = false;
	private GestionVue vue;
	
	public PlanVilleVue(double width, double height) {
		super(width, height);
		this.addEventFilter(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
		    @Override
		    public void handle(MouseEvent mouseEvent) {
		    	evenementSourisClick(mouseEvent.getX(), mouseEvent.getY());
		    }
		});
		this.addEventFilter(MouseEvent.MOUSE_MOVED, new EventHandler<MouseEvent>() {
		    @Override
		    public void handle(MouseEvent mouseEvent) {
		    	evenementSourisMove(mouseEvent.getX(), mouseEvent.getY());
		    }
		});
	}
	
	public PlanVilleVue(double width, double height, GestionVue vue) {
		super(width, height);
		this.setVue(vue);
		this.addEventFilter(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
		    @Override
		    public void handle(MouseEvent mouseEvent) {
		    	evenementSourisClick(mouseEvent.getX(), mouseEvent.getY());
		    }
		});
		this.addEventFilter(MouseEvent.MOUSE_MOVED, new EventHandler<MouseEvent>() {
		    @Override
		    public void handle(MouseEvent mouseEvent) {
		    	evenementSourisMove(mouseEvent.getX(), mouseEvent.getY());
		    }
		});
		this.addEventFilter(MouseEvent.MOUSE_EXITED, new EventHandler<MouseEvent>() {
		    @Override
		    public void handle(MouseEvent mouseEvent) {
		    	noeudSelectionned = null;
				tronconSelectionned = null;
				dessinerPlan(plan);
		    }
		});
	}
	

	public void livraisonSelected(Livraison livraison) {
		noeudSelectionned = null;
		tronconSelectionned = null;
		dessinerPlan(plan);
		livraisonIsClicked(livraison);
	}
	
	public void evenementSourisClick(double x, double y) {
		noeudSelectionned = null;
		tronconSelectionned = null;
		if(plan != null) {
			dessinerPlan(plan);
			//Recherche d'un noeud, livraison ou entrepot
			if(plan.getNoeuds() != null) {
				for(Map.Entry<Integer, Noeud> n : this.plan.getNoeuds().entrySet()) {
					if(n != null) {
						double NoeudX = n.getValue().getX() * zoom + offsetX - RAYON_NOEUD /2;
						double NoeudY = n.getValue().getY() * zoom + offsetY - RAYON_NOEUD /2;
						double XCenter = x - pointerMargin /2;
						double YCenter = y - pointerMargin /2;
						if(Math.abs(NoeudX - XCenter) <= pointerMargin && Math.abs(NoeudY - YCenter) <= pointerMargin) {
							if(plan.getEntrepot() != null && plan.getEntrepot().getNoeud().equals(n.getValue())) {
								entrepotIsClicked(plan.getEntrepot());
							} else {
								for(Map.Entry<Noeud, Livraison> l : this.plan.getLivraisons().entrySet()) {
									if(l.getKey().equals(n.getValue())) {
										livraisonIsClicked(l.getValue());
										vue.selectionneNoeud(l.getValue().getNoeud());
										return;
									}
								}
								noeudIsClicked(n.getValue());
								vue.selectionneNoeud(n.getValue());
							}
							return;
						}
					}
				}
			}
			//Recherche d'un troncon
			if(plan.getTroncons() != null) {
				for(Map.Entry<Pair<Noeud, Noeud>, Troncon> troncon : this.plan.getTroncons().entrySet()) {
					if(troncon != null) {
						double XCenter = x - pointerMargin /2;
						double YCenter = y - pointerMargin /2;
						double Ax = troncon.getKey().first.getX()  * zoom + offsetX - RAYON_NOEUD /2;
						double Ay = troncon.getKey().first.getY()  * zoom + offsetY - RAYON_NOEUD /2;
						double Bx = troncon.getKey().second.getX()  * zoom + offsetX - RAYON_NOEUD /2;
						double By = troncon.getKey().second.getY() * zoom + offsetY - RAYON_NOEUD /2;
						double Ux = Bx - Ax;
						double Uy = By - Ay;
						double ACx = XCenter - Ax;
						double ACy = YCenter - Ay;
						double numerateur = Ux*ACy - Uy*ACx;
						if (numerateur <0)
							numerateur = -numerateur ;
						double denominateur = Math.sqrt(Ux*Ux + Uy*Uy);
						double CI = numerateur / denominateur;
						if (CI<=pointerMargin) {
							double ABx = Bx - Ax;
							double ABy = By - Ay;
							double BCx = XCenter - Bx;
							double BCy = YCenter - By;
							double pscal1 = ABx*ACx + ABy*ACy;
							double pscal2 = (-ABx)*BCx + (-ABy)*BCy;
							if(pscal1>=0 && pscal2>=0) {
								tronconIsClicked(troncon.getValue());
								return;
							}
						}
					}
				}
			}
		}
	}
	
	public void evenementSourisMove(double x, double y) {
		if(plan != null) {
			dessinerPlan(plan);
			//Recherche d'un noeud, livraison ou entrepot
			if(plan.getNoeuds() != null) {
				for(Map.Entry<Integer, Noeud> n : this.plan.getNoeuds().entrySet()) {
					if(n != null) {
						double NoeudX = n.getValue().getX() * zoom + offsetX - RAYON_NOEUD /2;
						double NoeudY = n.getValue().getY() * zoom + offsetY - RAYON_NOEUD /2;
						double XCenter = x - pointerMargin /2;
						double YCenter = y - pointerMargin /2;
						if(Math.abs(NoeudX - XCenter) <= pointerMargin && Math.abs(NoeudY - YCenter) <= pointerMargin) {
							if(plan.getEntrepot() != null && plan.getEntrepot().getNoeud().equals(n.getValue())) {
								entrepotIsFocused(plan.getEntrepot());
							} else {
								for(Map.Entry<Noeud, Livraison> l : this.plan.getLivraisons().entrySet()) {
									if(l.getKey().equals(n.getValue())) {
										livraisonIsFocused(l.getValue());
										return;
									}
								}
								noeudIsFocused(n.getValue());
							}
							return;
						}
					}
				}
			}
			//Recherche d'un troncon
			if(plan.getTroncons() != null) {
				for(Map.Entry<Pair<Noeud, Noeud>, Troncon> troncon : this.plan.getTroncons().entrySet()) {
					if(troncon != null) {
						double XCenter = x - pointerMargin /2;
						double YCenter = y - pointerMargin /2;
						double Ax = troncon.getKey().first.getX()  * zoom + offsetX - RAYON_NOEUD /2;
						double Ay = troncon.getKey().first.getY()  * zoom + offsetY - RAYON_NOEUD /2;
						double Bx = troncon.getKey().second.getX()  * zoom + offsetX - RAYON_NOEUD /2;
						double By = troncon.getKey().second.getY() * zoom + offsetY - RAYON_NOEUD /2;
						double Ux = Bx - Ax;
						double Uy = By - Ay;
						double ACx = XCenter - Ax;
						double ACy = YCenter - Ay;
						double numerateur = Ux*ACy - Uy*ACx;
						if (numerateur <0)
							numerateur = -numerateur ;
						double denominateur = Math.sqrt(Ux*Ux + Uy*Uy);
						double CI = numerateur / denominateur;
						if (CI<=pointerMargin) {
							double ABx = Bx - Ax;
							double ABy = By - Ay;
							double BCx = XCenter - Bx;
							double BCy = YCenter - By;
							double pscal1 = ABx*ACx + ABy*ACy;
							double pscal2 = (-ABx)*BCx + (-ABy)*BCy;
							if(pscal1>=0 && pscal2>=0) {
								tronconIsFocused(troncon.getValue());
								return;
							}
						}
					}
				}
			}
		}
	}
	
	private void tronconIsClicked(Troncon troncon) {
		tronconSelectionned = troncon;
		GraphicsContext gc = this.getGraphicsContext2D();
		double x = troncon.getOrigine().getX() * zoom + offsetX - RAYON_NOEUD /2;
		double y = troncon.getOrigine().getY() * zoom + offsetY - RAYON_NOEUD /2;
		//Affichage du troncon
		gc.setStroke(new Color(0,0.3984,0,1));
		gc.strokeLine(troncon.getOrigine().getX() * zoom + offsetX, troncon.getOrigine().getY() * zoom + offsetY,
				troncon.getDestination().getX() * zoom + offsetX, troncon.getDestination().getY() * zoom + offsetY);
		//Affichage zone de texte
		int l = troncon.getNomRue().length();
		gc.setFill(new Color(0,0,0,0.5));
		gc.fillRect(x + 15, y - 15, 10 + l*7, 20);
		//Affichage texte
		gc.setFill(new Color(1,1,1,1));
		gc.fillText(troncon.getNomRue(), x + 15 + 5, y);
	}
	
	private void tronconIsFocused(Troncon troncon) {
		if(troncon != tronconSelectionned) {
			GraphicsContext gc = this.getGraphicsContext2D();
			double x = troncon.getOrigine().getX() * zoom + offsetX - RAYON_NOEUD /2;
			double y = troncon.getOrigine().getY() * zoom + offsetY - RAYON_NOEUD /2;
			//Affichage du troncon
			gc.setStroke(new Color(0,0.5976,0,1));
			gc.strokeLine(troncon.getOrigine().getX() * zoom + offsetX, troncon.getOrigine().getY() * zoom + offsetY,
					troncon.getDestination().getX() * zoom + offsetX, troncon.getDestination().getY() * zoom + offsetY);
			//Affichage zone de texte
			int l = troncon.getNomRue().length();
			gc.setFill(new Color(0,0,0,0.5));
			gc.fillRect(x + 15, y - 15, 10 + l*7, 20);
			//Affichage texte
			gc.setFill(new Color(1,1,1,1));
			gc.fillText(troncon.getNomRue(), x + 15 + 5, y);
		}
	}
	
	private void livraisonIsClicked(Livraison livraison) {
		GraphicsContext gc = this.getGraphicsContext2D();
		noeudIsClickedEffect(livraison.getNoeud());
		double x = livraison.getNoeud().getX() * zoom + offsetX - RAYON_NOEUD /2;
		double y = livraison.getNoeud().getY() * zoom + offsetY - RAYON_NOEUD /2;
		//Affichage zone de texte
		int l = String.valueOf(livraison.getNoeud().getId()).length();
		gc.setFill(new Color(0,0,0,0.5));
		gc.fillRect(x + 15, y - 15, 10 + l*7, 20);
		//Affichage texte
		gc.setFill(new Color(1,1,1,1));
		gc.fillText(String.valueOf(livraison.getNoeud().getId()), x + 15 + 5, y);
	}
	
	private void entrepotIsClicked(Entrepot entrepot) {
		GraphicsContext gc = this.getGraphicsContext2D();
		noeudIsClickedEffect(entrepot.getNoeud());
		double x = entrepot.getNoeud().getX() * zoom + offsetX - RAYON_NOEUD /2;
		double y = entrepot.getNoeud().getY() * zoom + offsetY - RAYON_NOEUD /2;
		//Affichage zone de texte
		int l = String.valueOf(entrepot.getNoeud().getId()).length();
		gc.setFill(new Color(0,0,0,0.5));
		gc.fillRect(x + 15, y - 15, 10 + l*7, 20);
		//Affichage texte
		gc.setFill(new Color(1,1,1,1));
		gc.fillText(String.valueOf(entrepot.getNoeud().getId()), x + 15 + 5, y);
	}
	
	private void noeudIsClicked(Noeud noeud) {
		GraphicsContext gc = this.getGraphicsContext2D();
		noeudIsClickedEffect(noeud);
		double x = noeud.getX() * zoom + offsetX - RAYON_NOEUD /2;
		double y = noeud.getY() * zoom + offsetY - RAYON_NOEUD /2;
		//Affichage zone de texte
		int l = String.valueOf(noeud.getId()).length();
		gc.setFill(new Color(0,0,0,0.5));
		gc.fillRect(x + 15, y - 15, 10 + l*7, 20);
		//Affichage texte
		gc.setFill(new Color(1,1,1,1));
		gc.fillText(String.valueOf(noeud.getId()), x + 15 + 5, y);
	}
	
	private void noeudIsClickedEffect(Noeud noeud) {
		noeudSelectionned = noeud;
		double x = noeud.getX() * zoom + offsetX - RAYON_NOEUD /2;
		double y = noeud.getY() * zoom + offsetY - RAYON_NOEUD /2;
		GraphicsContext gc = this.getGraphicsContext2D();
		//Affichage du noeud exterieur
		gc.setFill(new Color(0.859,0.839,0.808,1));
		gc.fillOval(x,y,RAYON_NOEUD, RAYON_NOEUD);
		//Affichage du noeud interieur
		gc.setFill(new Color(0,0.4921,0.9609,1));
		gc.fillOval(noeud.getX() * zoom + offsetX - (RAYON_NOEUD-4)/2, noeud.getY() * zoom + offsetY - (RAYON_NOEUD - 4) /2
				, RAYON_NOEUD - 4, RAYON_NOEUD - 4);
	}
	
	private void livraisonIsFocused(Livraison livraison) {
		if(livraison.getNoeud() != noeudSelectionned) {
			double x = livraison.getNoeud().getX() * zoom + offsetX - RAYON_NOEUD /2;
			double y = livraison.getNoeud().getY() * zoom + offsetY - RAYON_NOEUD /2;
			GraphicsContext gc = this.getGraphicsContext2D();
			noeudIsFocusedEffect(livraison.getNoeud());
			//Affichage zone de texte
			int l = String.valueOf(livraison.getNoeud().getId()).length();
			gc.setFill(new Color(0,0,0,0.8));
			gc.fillRect(x + 15, y - 15, 10 + l*7, 20);
			//Affichage texte
			gc.setFill(new Color(1,1,1,1));
			gc.fillText(String.valueOf(livraison.getNoeud().getId()), x + 15 + 5, y);
		}
	}
	
	private void entrepotIsFocused(Entrepot entrepot) {
		if(entrepot.getNoeud() != noeudSelectionned) {
			double x = entrepot.getNoeud().getX() * zoom + offsetX - RAYON_NOEUD /2;
			double y = entrepot.getNoeud().getY() * zoom + offsetY - RAYON_NOEUD /2;
			GraphicsContext gc = this.getGraphicsContext2D();
			noeudIsFocusedEffect(entrepot.getNoeud());
			//Affichage zone de texte
			int l = String.valueOf(entrepot.getNoeud().getId()).length();
			gc.setFill(new Color(0,0,0,0.8));
			gc.fillRect(x + 15, y - 15, 10 + l*7, 20);
			//Affichage texte
			gc.setFill(new Color(1,1,1,1));
			gc.fillText(String.valueOf(entrepot.getNoeud().getId()), x + 15 + 5, y);
		}
	}
	
	private void noeudIsFocused(Noeud noeud) {
		if(noeud != noeudSelectionned) {
			double x = noeud.getX() * zoom + offsetX - RAYON_NOEUD /2;
			double y = noeud.getY() * zoom + offsetY - RAYON_NOEUD /2;
			GraphicsContext gc = this.getGraphicsContext2D();
			noeudIsFocusedEffect(noeud);
			//Affichage zone de texte
			int l = String.valueOf(noeud.getId()).length();
			gc.setFill(new Color(0,0,0,0.8));
			gc.fillRect(x + 15, y - 15, 10 + l*7, 20);
			//Affichage texte
			gc.setFill(new Color(1,1,1,1));
			gc.fillText(String.valueOf(noeud.getId()), x + 15 + 5, y);
		}
	}
	
	private void noeudIsFocusedEffect(Noeud noeud) {
		double x = noeud.getX() * zoom + offsetX - RAYON_NOEUD /2;
		double y = noeud.getY() * zoom + offsetY - RAYON_NOEUD /2;
		GraphicsContext gc = this.getGraphicsContext2D();
		//Affichage du noeud exterieur
		gc.setFill(new Color(0,0.4921,0.9609,0.7));
		gc.fillOval(x,y,RAYON_NOEUD, RAYON_NOEUD);
	}
	
	public void modeAjouterLivraison(boolean mode) {
		modeAjouterLivraison = mode;
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
			if(noeudSelectionned != null) {
				noeudIsClicked(noeudSelectionned);
			}
			if(tronconSelectionned != null) {
				tronconIsClicked(tronconSelectionned);
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
		gc.setFill(new Color(0.250,0.250,0.250,0.8));
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
        for(Map.Entry<Noeud, Livraison> l : this.plan.getLivraisons().entrySet()) {
			if(l != null && l.getKey() != null) {
				gc.setFill(new Color(0.250,0.250,0.250,1));
				gc.fillOval(l.getKey().getX() * zoom + offsetX - RAYON_LIVRAISON/2, l.getKey().getY() * zoom + offsetY - RAYON_LIVRAISON/2
						, RAYON_LIVRAISON, RAYON_LIVRAISON);
				gc.setFill(Color.LIGHTGREEN);
				gc.fillOval(l.getKey().getX() * zoom + offsetX - (RAYON_LIVRAISON-4)/2, l.getKey().getY() * zoom + offsetY - (RAYON_LIVRAISON-4)/2
						, RAYON_LIVRAISON - 4, RAYON_LIVRAISON - 4);
			}
		}
	}
	
	public void dessinerEntrepot() {
		GraphicsContext gc = this.getGraphicsContext2D();
        if(plan.getEntrepot() != null && plan.getEntrepot().getNoeud() != null) {
        	gc.setFill(new Color(0.250,0.250,0.250,1));
			gc.fillOval(plan.getEntrepot().getNoeud().getX() * zoom + offsetX - RAYON_LIVRAISON/2, plan.getEntrepot().getNoeud().getY() * zoom + offsetY - RAYON_LIVRAISON/2
					, RAYON_LIVRAISON, RAYON_LIVRAISON);
			gc.setFill(Color.RED);
			gc.fillOval(plan.getEntrepot().getNoeud().getX() * zoom + offsetX - (RAYON_LIVRAISON-4)/2, plan.getEntrepot().getNoeud().getY() * zoom + offsetY - (RAYON_LIVRAISON-4)/2
					, RAYON_LIVRAISON - 4, RAYON_LIVRAISON - 4);
        	gc.setFill(Color.RED);
        	//gc.drawImage(img, x, y);
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

	public GestionVue getVue() {
		return vue;
	}

	public void setVue(GestionVue vue) {
		this.vue = vue;
	}
	
	public boolean getModeAjouterLivraison() {
		return modeAjouterLivraison;
	}
}
