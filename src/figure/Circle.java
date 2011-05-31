package figure;

import figure.FigureGraphic;
import figure.Point_2D;

import java.awt.Color;
import java.awt.Graphics;

public class Circle extends FigureGraphic
{
	protected Point_2D center;
	protected int radius;
	
	public Circle(String nom,Color cc,Color cr, int x, int y, int radius) {
		super(nom,cc,cr);
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
		 g.setColor(colorBackground);
		// fill the cercle	
		 g.fillOval(getCenter().x-radius,getCenter().y-radius,radius*2,radius*2);
		 g.setColor(colorStroke);
		// draw stroke of the cercle
		 g.drawOval(getCenter().x-radius,getCenter().y-radius,radius*2,radius*2);
		 g.drawString(name,getCenter().x,getCenter().y);
	}

	public boolean contain(Point_2D p) {
		return (Point_2D.distance(center, p) < radius);	}

	public double getSurface () {
		return Math.PI*radius*radius;
	}
}	