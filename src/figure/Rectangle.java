package figure;

import figure.FigureGraphic;
import figure.Point_2D;

import java.awt.Color;
import java.awt.Graphics;
import java.io.Serializable;

@SuppressWarnings("serial")
public class Rectangle extends FigureGraphic implements Serializable
{
	protected Point_2D startPoint;
	protected int width,height;
	
	public Rectangle(String name, Color colorStroke, Color colorBackground, int x1, int y1, int width, int height) {
		super(name,colorStroke,colorBackground);
		this.startPoint = new Point_2D(x1,y1);
		this.width = width;
		this.height = height;
	}
	
	public Point_2D getCenter() {
		return new Point_2D (startPoint.getX()+(width)/2,startPoint.getY()+(height)/2);
	}

	public void move(int dx, int dy) {
		startPoint.move(dx,dy);
	}

	public void draw(Graphics g) {
		g.setColor(colorBackground);
		g.fillRect(startPoint.x,startPoint.y,width,height); 
		g.setColor(colorStroke);
		g.drawRect(startPoint.x,startPoint.y,width,height) ;
		afterDraw(g);
	}
	
	public boolean contain(Point_2D p) {
		return ((startPoint.getX()< p.getX())&& ( p.getX()< startPoint.getX()+ width)&&
				(startPoint.getY()< p.getY())&& ( p.getY()< startPoint.getY()+ height));
	}
}	