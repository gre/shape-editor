package figure;

import figure.FigureGraphic;
import figure.Point_2D;

import java.awt.Color;
import java.awt.Graphics;
import java.io.Serializable;

import ui.Env;

@SuppressWarnings("serial")
public class Circle extends FigureGraphic implements Serializable
{
	private static long nbOfCircles = 0;
	
	protected Point_2D center;
	protected int radius;
	
	public Circle(String name, Color colorStroke, Color colorBackground, int x, int y, int radius) {
		super(name,colorStroke,colorBackground);
		center = new Point_2D(x,y);
		this.radius=radius;
		++nbOfCircles;
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
	
	public void setRadius(int rad) {
		radius = rad;
	}
	public int getRadius() {
		return radius;
	}
	
	public void fitRadiusWithPoint(int x, int y) {
		x -= center.getX();
		y -= center.getY();
		int radius = (int)Math.sqrt(x*x+y*y);
		setRadius(radius);
	}

	public void draw(Graphics g) {
		Point_2D c = getCenter(); 
		g.setColor(getBgForCurrentState());
		g.fillOval(c.x-radius, c.y-radius, radius*2, radius*2);
		g.setColor(getStrokeForCurrentState());
		g.drawOval(c.x-radius, c.y-radius, radius*2, radius*2);
		afterDraw(g);
	}

	public boolean contain(Point_2D p) {
		return (Point_2D.distance(center, p) < radius);	}

	public double getSurface () {
		return Math.PI*radius*radius;
	}

	public static Circle createByClick(Env env, int x, int y) {
		String name = "circle_"+(nbOfCircles+1);
		Circle c = new Circle(name, env.getStrokeColor(), env.getBackgroundColor(), x, y, 0);
		c.setSelected(true);
		return c;
	}
}	