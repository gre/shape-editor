package figure;

import figure.FigureGraphic;
import figure.Point_2D;

import java.awt.Color;
import java.awt.Graphics;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedList;
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

    public Polygon(String name, Color colorStroke, Color colorBackground) {
        super(name,colorStroke,colorBackground);
        ++nbOfPolygons;
    }
    /**
     * Create by first click
     * @param env
     * @param x
     * @param y
     */
    public Polygon(Env env, int x, int y) {
        this("poly_"+(nbOfPolygons+1), env.getStrokeColor(), env.getBackgroundColor());
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
		g.setColor(getBgForCurrentState());
		if(!isBuilding())
		    g.fillPolygon(xAll, yAll, points.size());
		g.setColor(getStrokeForCurrentState());
		if(isBuilding()) {
		    Point_2D last = null, first = null;
            for(Point_2D p : points) {
                if(last!=null)
                    g.drawLine(last.x, last.y, p.x, p.y);
                else
                    first = p;
                last = p;
            }
            if(drawTerminaison() && isBuildingConvenientToBeFinished()) {
                // Dessine une poignee de terminaison
                g.setColor(Color.RED);
                g.fillOval(first.x-FINISH_HANDLE_RADIUS_PX/2, first.y-FINISH_HANDLE_RADIUS_PX/2, FINISH_HANDLE_RADIUS_PX, FINISH_HANDLE_RADIUS_PX);
            }
            
        }
        else g.drawPolygon(xAll, yAll, points.size());
		afterDraw(g);
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
	
	protected boolean drawTerminaison() {
	    return true;
	}
	
    @Override
    public boolean isBuildingConvenientToBeFinished() {
        int size = points.size();
        return size>2 && points.get(0).distance(points.get(size-1))<FINISH_HANDLE_RADIUS_PX;
    }
    public void closePath() {
        points.remove(points.size()-1);
        setBuilding(false);
    }
	
}	