package figure;

import figure.FigureGraphic;
import figure.Point_2D;

import java.awt.Color;
import java.awt.Graphics;
import java.util.LinkedList;
import java.util.List;

public class Polygon extends FigureGraphic
{
	protected List<Point_2D> points;
	protected int[] xAll, yAll;
	
	public Polygon(String name, Color colorStroke, Color colorBackground) {
		super(name,colorStroke,colorBackground);
		points = new LinkedList<Point_2D>();
	}
	
	public void addPoint(int x, int y) {
		points.add(new Point_2D(x,y));
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
		for (Point_2D point : points) {
			point.x += dx;
			point.y += dy;
		}
	}
	
	public void updateXYAll() {
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
		updateXYAll();
		
		g.setColor(colorBackground);
		g.fillPolygon(xAll, yAll, points.size());
		g.setColor(colorStroke);
		g.drawPolygon(xAll, yAll, points.size());
		
		 if(isSelected()) {
			 // Display center
			 drawCenter(g);
			 drawName(g);
		 }
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
	
}	