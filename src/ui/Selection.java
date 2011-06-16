package ui;

import figure.Point_2D;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

/**
 * Représente une zone de sélection rectangulaire sur le canvas.
 */
public class Selection
{
	final static float dash1[] = {4.0f};
	final static BasicStroke dashed = new BasicStroke(1.0f, BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER, 4.0f, dash1, 0.0f);
	
	protected Color bg, stroke;
	protected Point_2D topleft;
	protected int width,height;

	public Selection(Color colorStroke, Color colorBackground, int x, int y, int width, int height) {
		this.bg = colorBackground;
		this.stroke = colorStroke;
		this.topleft = new Point_2D(x, y);
		this.width = width;
		this.height = height;
	}
	
	public Selection(Color colorStroke, Color colorBackground, Point_2D a, Point_2D b) {
		this(colorStroke, colorBackground, Math.min(a.getX(), b.getX()), Math.min(a.getY(), b.getY()), Math.abs(a.getX()-b.getX()), Math.abs(a.getY()-b.getY()));
	}
	
	public void draw(Graphics g) {
		Graphics2D g2d = (Graphics2D) g;
		g.setColor(bg);
		g.fillRect(topleft.getX(), topleft.getY(), width, height);
		g.setColor(stroke);
		g2d.setStroke(dashed);
		g.drawRect(topleft.getX(), topleft.getY(), width, height);
	}
	
	public boolean contain(Point_2D p) {
		if ( topleft.getX() <= p.getX() && p.getX() <= topleft.getX()+width
		  && topleft.getY() <= p.getY() && p.getY() <= topleft.getY()+height) {
			return true;
		}
		return false;
	}
}	