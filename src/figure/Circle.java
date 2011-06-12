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
	
	public Circle(String name) {
		super(name);
		++nbOfCircles;
	}
	
	public Circle() {
		this("circle_"+(nbOfCircles+1));
	}
	
    public Circle(String name, Color stroke, Color bg, int x, int y, int radius) {
		super(name);
		setColors(stroke, bg);
		setRadius(radius);
		setCenter(x, y);
    }

	/**
     * Create by first click
     * @param env
     * @param x
     * @param y
     */
    public void init(Env env, int x, int y) {
        setColors(env);
        setCenter(x, y);
        setSelected(true);
        setBuilding(true);
    }

	public Point_2D getCenter() {
		return center;
	}
	public void setCenter(int x, int y) {
		center = new Point_2D(x, y);
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

    private boolean canBeFinished() {
        return 2*radius > THRESHOLD_BUILDING_PX;
    }
    public boolean canBeFinishedWithKey() {
    	return canBeFinished();
    }
    public boolean canBeFinishedWithMouse() {
    	return canBeFinished();
    }

    @Override
    public void onFigureFinish() {
    	
    }
    
	@Override
	public void onPressPoint(int x, int y) {
		setCenter(x, y);
	}

	@Override
	public void onReleasePoint(int x, int y) {
		fitRadiusWithPoint(x, y);
	}

	@Override
	public void onMovePoint(int x, int y) {
		fitRadiusWithPoint(x, y);
	}

	@Override
	public String getShapeName() {
		return "Cercle";
	}
}	