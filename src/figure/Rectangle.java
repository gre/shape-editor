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
	
	protected Point_2D topleft;
	protected int width,height;
	
	public Rectangle(String name, Color colorStroke, Color colorBackground, int x, int y, int width, int height) {
		super(name,colorStroke,colorBackground);
		this.topleft = new Point_2D(x, y);
		this.width = width;
		this.height = height;
		++nbOfRectangles;
	}
	
	public Point_2D getCenter() {
		return new Point_2D (topleft.getX()+(width)/2,topleft.getY()+(height)/2);
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public void move(int dx, int dy) {
		topleft.move(dx,dy);
	}

	public void draw(Graphics g) {
		g.setColor(getBgForCurrentState());
		g.fillRect(topleft.getX(), topleft.getY(), width, height);
		g.setColor(getStrokeForCurrentState());
		g.drawRect(topleft.getX(), topleft.getY(), width, height);
		afterDraw(g);
	}
	
	public boolean contain(Point_2D p) {
		if ( topleft.getX() <= p.getX() && p.getX() <= topleft.getX()+width
		  && topleft.getY() <= p.getY() && p.getY() <= topleft.getY()+height) {
			return true;
		}
		return false;
	}

	public void setSecondPoint(int x, int y) {
		int xMin = Math.min(x, topleft.getX());
		int yMin = Math.min(y, topleft.getY());
		int xMax = Math.max(x, topleft.getX()+width);
		int yMax = Math.max(y, topleft.getY()+height);
		width = xMax - xMin;
		height = yMax - yMin;
		topleft = new Point_2D(xMin, yMin);
	}

	public static Rectangle createByClick(Env env, int x, int y) {
		String name = "rect_"+(nbOfRectangles+1);
		Rectangle r = new Rectangle(name, env.getStrokeColor(), env.getBackgroundColor(), x, y, 0, 0);
		r.setSelected(true);
		return r;
	}
}	