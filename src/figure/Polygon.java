package figure;

import figure.FigureGraphic;
import figure.Point_2D;

import java.awt.Color;
import java.awt.Graphics;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import ui.Env;

@SuppressWarnings("serial")
public class Polygon extends FigureGraphic implements Serializable
{
	// Taille de la poignee de terminaison
	protected static final int FINISH_HANDLE_RADIUS_PX = 15;
	
	private static long nbOfPolygons = 0;
	
	protected List<Point_2D> points = new ArrayList<Point_2D>();
	protected int[] xAll, yAll;

	public Polygon(String name) {
		super(name);
		++nbOfPolygons;
	}
	public Polygon() {
		this("poly_"+(nbOfPolygons+1));
	}
	
	public Polygon(String name, Color stroke, Color bg) {
		this(name);
		setColors(stroke, bg);
	}
	/**
	 * Create by first click
	 * @param env
	 * @param x
	 * @param y
	 */
	public void init(Env env, int x, int y) {
		setColors(env);
		addPoint(x, y);
		addPoint(x, y);
		setSelected(true);
		setBuilding(true);
	}
	
	public Point_2D getCenter() {
		int xSum=0, ySum=0;
		for (Point_2D point : points) {
			xSum += point.x;
			ySum += point.y;
		}
		return new Point_2D( xSum / points.size(), ySum / points.size());
	}
	
	public void move(int dx, int dy) {
		for (Point_2D point : points)
			point.move(dx,dy);
		updateXYAll();
	}
	public void addPoint(int x, int y) {
		points.add(new Point_2D(x,y));
		updateXYAll();
	}
	public void editLastPoint(Point_2D p) {
		int size = points.size();
		if(size==0) {
			points.add(p);
		}
		else {
			points.set(points.size()-1, p);
		}
		updateXYAll();
	}
	public void editLastPoint(int x, int y) {
		editLastPoint(new Point_2D(x, y));
	}
	
	protected void updateXYAll() {
		xAll = new int[points.size()];
		yAll = new int[points.size()];
		
		int i=0;
		for (Point_2D point : points) {
			xAll[i] = point.x;
			yAll[i] = point.y;
			i++;
		}
	}
	
	public void draw(Graphics g) {
		if(isBuilding()) {
			g.setColor(!isConvex() ? Color.red : Color.black);
			Point_2D last = null, first = null;
			for(Point_2D p : points) {
				if(last!=null)
					g.drawLine(last.x, last.y, p.x, p.y);
				else
					first = p;
				last = p;
			}
			if(drawTerminaisonEnabled() && canBeFinished()) {
				// Dessine une poignee de terminaison
				if(canBeFinishedWithMouse()) // mouseover
					g.setColor(new Color(255, 100, 100));
				else
					g.setColor(Color.white);
				g.fillOval(first.x-FINISH_HANDLE_RADIUS_PX/2, first.y-FINISH_HANDLE_RADIUS_PX/2, FINISH_HANDLE_RADIUS_PX, FINISH_HANDLE_RADIUS_PX);
				g.setColor(new Color(0, 0, 0));
				g.drawOval(first.x-FINISH_HANDLE_RADIUS_PX/2, first.y-FINISH_HANDLE_RADIUS_PX/2, FINISH_HANDLE_RADIUS_PX, FINISH_HANDLE_RADIUS_PX);
			}
			
		}
		else {
			g.setColor(getBgForCurrentState());
			g.fillPolygon(xAll, yAll, points.size());
			g.setColor(getStrokeForCurrentState());
			g.drawPolygon(xAll, yAll, points.size());
		}
		afterDraw(g);
	}
	
	public Double angle(Point_2D a, Point_2D b, Point_2D c) {
		Double t1 = Math.atan2(a.getY()-c.getY(), a.getX()-c.getX());
		Double t2 = Math.atan2(b.getY()-c.getY(), b.getX()-c.getX());
		return t2 - t1;
		
	}
	
	/**
	 * utilise l'algorithme "la marche de Jarvis"
	 * @return les points de l'enveloppe convexe du polygone
	 */
	public List<Point_2D> enveloppeConvexe() {
		if(points.size()<=3) return new ArrayList<Point_2D>(points);
		List<Point_2D> list = new ArrayList<Point_2D>();
		Double a, bestA;
		Point_2D endpoint = null, firstpoint = null, pivot = null;
		Integer minX = null;
		for(Point_2D p : points) {
			if(minX==null || p.getX()<minX) {
				minX = p.getX();
				pivot = p;
			}
		}
		firstpoint = pivot;
		int i = 0;
		do {
			list.add(pivot);
			endpoint = points.get(0);
			for(int j=1; j<points.size(); j++) {
				a = angle(list.get(i), points.get(j), endpoint);
				if(endpoint == pivot || 0<a) {
					endpoint = points.get(j);
					bestA = a;
				}
			}
			++ i;
			pivot = endpoint;
			
		} while(endpoint == firstpoint);
		return list;
	}
	
	public boolean isConvex() {
		if(points.size()<=3) return true;
		/*Point_2D a = points.get(0), b = points.get(1), c;
		System.out.println("---");
		for(int i = 2; i<points.size(); ++i) {
			c = points.get(i);
			Double angle = angle(a, c, b);
			System.out.println(""+angle);
			a = b; b = c;
		}*/
		System.out.println(enveloppeConvexe().size()+" "+points.size());
		return enveloppeConvexe().size() == points.size();
	}

	public boolean contain(Point_2D p) {
		int nvert = points.size();
		int[] verty = yAll; 
		int[] vertx = xAll;
		int testx = p.x;
		int testy = p.y;

		int i, j;
		boolean c = false;
		for (i = 0, j = nvert-1; i < nvert; j = i++) {
		  if ( ((verty[i]>testy) != (verty[j]>testy)) &&
				  (testx < (vertx[j]-vertx[i]) * (testy-verty[i]) / (verty[j]-verty[i]) + vertx[i]) )
			   c = !c;
		}
		
		return c;
	}
	
	protected boolean drawTerminaisonEnabled() {
		return true;
	}
	
	private boolean canBeFinished() {
		return points.size() > 3;
	}
    public boolean canBeFinishedWithKey() {
    	return canBeFinished();
    }
    public boolean canBeFinishedWithMouse() {
    	return canBeFinished() && points.get(0).distance(points.get(points.size()-1))<FINISH_HANDLE_RADIUS_PX/2;
    }

	public void closePath() {
		points.remove(points.size()-1);
	}
	
	@Override
	public void onFigureFinish() {
		closePath();
	}
	
	@Override
	public void onPressPoint(int x, int y) {
		addPoint(x, y);
	}
	@Override
	public void onReleasePoint(int x, int y) {
		
	}
	
	@Override
	public void onMovePoint(int x, int y) {
		editLastPoint(x, y);
	}
	@Override
	public String getShapeName() {
		return "Polygone";
	}
}	