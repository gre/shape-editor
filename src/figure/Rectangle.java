package figure;

import figure.FigureGraphic;
import figure.Point_2D;

import java.awt.Color;
import java.awt.Graphics;
import java.io.Serializable;

import ui.Env;

@SuppressWarnings("serial")
public class Rectangle extends FigureGraphic implements Serializable
{
	private static long nbOfRectangles = 0;
	
	/*
	protected Point_2D topleft;
	protected int width,height;
	*/
	
	protected Point_2D a, b;
	
	public Rectangle(String name) {
		super(name);
		++nbOfRectangles;
	}
	
	public Rectangle() {
		this("rect_"+(nbOfRectangles+1));
	}

    public Rectangle(String name, Color stroke, Color bg, int x, int y, int w, int h) {
		this(name);
		setColors(stroke, bg);
		setFirstPoint(x, y);
		setSecondPoint(x+w, y+h);
	}

	/**
     * Create by first click
     * @param env
     * @param x
     * @param y
     */
    public void init(Env env, int x, int y) {
    	setColors(env);
    	a = new Point_2D(x, y);
    	b = new Point_2D(x, y);
        setSelected(true);
        setBuilding(true);
    }
	
	public Point_2D getCenter() {
		return new Point_2D ((a.getX()+b.getX())/2, (a.getY()+b.getY())/2);
	}

	public int getWidth() {
		return Math.abs(a.getX() - b.getX());
	}

	public int getHeight() {
		return Math.abs(a.getY() - b.getY());
	}
	
	public Point_2D getTopLeft() {
		return new Point_2D(Math.min(a.getX(), b.getX()), Math.min(a.getY(), b.getY()));
	}

	public void move(int dx, int dy) {
		a.move(dx, dy);
		b.move(dx, dy);
	}

	public void draw(Graphics g) {
		int width = getWidth();
		int height = getHeight();
		Point_2D topleft = getTopLeft();
		g.setColor(getBgForCurrentState());
		g.fillRect(topleft.getX(), topleft.getY(), width, height);
		g.setColor(getStrokeForCurrentState());
		g.drawRect(topleft.getX(), topleft.getY(), width, height);
		afterDraw(g);
	}
	
	public boolean contain(Point_2D p) {
		int width = getWidth();
		int height = getHeight();
		Point_2D topleft = getTopLeft();
		if ( topleft.getX() <= p.getX() && p.getX() <= topleft.getX()+width
		  && topleft.getY() <= p.getY() && p.getY() <= topleft.getY()+height) {
			return true;
		}
		return false;
	}

	public void setFirstPoint(int x, int y) {
	    a = new Point_2D(x, y);
	}
	public void setSecondPoint(int x, int y) {
	    b = new Point_2D(x, y);
	}

    @Override
    public boolean canBeFinished() {
        return getWidth()>THRESHOLD_BUILDING_PX && getHeight()>THRESHOLD_BUILDING_PX;
    }

	@Override
	public void onPressPoint(int x, int y) {
		if(canBeFinished()) setSecondPoint(x, y);
	}

	@Override
	public void onReleasePoint(int x, int y) {
		setSecondPoint(x, y);
	}

	@Override
	public void onMovePoint(int x, int y) {
		setSecondPoint(x, y);
	}

}	