package figure;

import figure.FigureGraphic;
import figure.Point_2D;

import java.awt.Color;
import java.awt.Graphics;
import java.io.Serializable;

public class Circle extends FigureGraphic implements Serializable
{
	protected Point_2D center;
	protected int radius;
	
	public Circle(String name, Color colorStroke, Color colorBackground, int x, int y, int radius) {
		super(name,colorStroke,colorBackground);
		center = new Point_2D(x,y);
		this.radius=radius;
	}
	
	public String toString() {
		return new String(" circle : "+ name +" center : "+ center.toString()+
			" radius : "+ radius+"\n\t\t background color: "+colorBackground+" stroke color "+colorStroke );
	}

	public Point_2D getCenter() {
		return center;
	}

	public void move(int dx, int dy) {
		center.move(dx,dy);	
	}

	public void draw(Graphics g) {
		Point_2D c = getCenter(); 
		g.setColor(colorBackground);
		// fill the cercle	
		 g.fillOval(c.x-radius, c.y-radius, radius*2, radius*2);
		 g.setColor(colorStroke);
		// draw stroke of the cercle
		 g.drawOval(c.x-radius, c.y-radius, radius*2, radius*2);
		 afterDraw(g);
	}

	public boolean contain(Point_2D p) {
		return (Point_2D.distance(center, p) < radius);	}

	public double getSurface () {
		return Math.PI*radius*radius;
	}
}	