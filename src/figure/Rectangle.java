package figure;

import figure.FigureGraphic;
import figure.Point_2D;

import java.awt.Color;
import java.awt.Graphics;
import java.io.Serializable;

public class Rectangle extends FigureGraphic implements Serializable
{
	protected Point_2D startPoint, endPoint;
	private int xMin, yMin, xMax, yMax;
	
	public Rectangle(String name, Color colorStroke, Color colorBackground, int x1, int y1, int x2, int y2) {
		super(name,colorStroke,colorBackground);
		this.startPoint = new Point_2D(x1,y1);
		this.endPoint = new Point_2D(x2,y2);
	}
	
	public Point_2D getCenter() {
		return new Point_2D( xMin + (Math.abs(startPoint.x - endPoint.x) / 2 ),
							 yMin + (Math.abs(startPoint.y - endPoint.y) / 2 ) );
	}

	public void move(int dx, int dy) {
		startPoint.move(dx,dy);
		endPoint.move(dx,dy);
	}

	public void draw(Graphics g) {
		calcXYMinMax();
		g.setColor(colorBackground);
		// fill the cercle	
		g.fillRect(xMin, yMin, xMax-xMin, yMax-yMin);
		g.setColor(colorStroke);
		// draw stroke of the cercle
		g.drawRect(xMin, yMin, xMax-xMin, yMax-yMin);
		
		 if(isSelected()) {
			 // Display center
			 drawCenter(g);
			 drawName(g);
		 }
	}
	
	public boolean contain(Point_2D p) {
		calcXYMinMax();
		if ( (p.x >= xMin && p.x <= xMax) && (p.y >= yMin && p.y <= yMax ) ) {
			return true;
		}
		return false;
	}
	
	public void calcXYMinMax() {
		xMin = Math.min(startPoint.x, endPoint.x);
		xMax = Math.max(startPoint.x, endPoint.x);
		yMin = Math.min(startPoint.y, endPoint.y);
		yMax = Math.max(startPoint.y, endPoint.y);
	}
}	