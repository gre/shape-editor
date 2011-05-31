package figure;

import figure.Point_2D;

import java.awt.Graphics;

public interface Figure
{
	public abstract double getSurface();

	public abstract Point_2D getCenter();

	public abstract void move(int dx, int dy);

	public abstract boolean contain(Point_2D p);

	public abstract String toString();

	public abstract void draw(Graphics gx);
}