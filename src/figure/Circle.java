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
    
    /**
     * Create by first click
     * @param env
     * @param x
     * @param y
     */
    public Circle(Env env, int x, int y) {
        this("circle_"+(nbOfCircles+1), env.getStrokeColor(), env.getBackgroundColor(), x, y, 0);
        setSelected(true);
        setBuilding(true);
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

    @Override
    public boolean isBuildingConvenientToBeFinished() {
        return 2*radius > THRESHOLD_BUILDING_PX;
    }
}	