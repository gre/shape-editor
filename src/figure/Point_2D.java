package figure;

import java.io.Serializable;

public class Point_2D implements Serializable
{
	protected int x;
	protected int y;

	public Point_2D ()
	{
		x = 0;
		y = 0;
	}

	public Point_2D ( int x, int y)
	{
		this.x = x;
		this.y = y;
	}

	public Point_2D (Point_2D p)
	{
		x = p.x;
		y = p.y;
	}

	/**
	 * Accesseurs
	 */
	public int getX () { return x; }
	public int getY () { return y; }
	public void setX (int val) { x = val; }
	public void setY (int val) { y = val; }

	/**
	 * Affichage contenu
	 */

	public String toString()
	{
		return new String (" x = " + getX() + " y = " + getY());
	}

	public void move(int dx, int dy)
	{
		this.x += dx;
		this.y += dy;
	}

	public static double distance(Point_2D p1, Point_2D p2)
	{
		int dx = p1.x-p2.x;
		int dy = p1.y-p2.y;

		return Math.sqrt((dx*dx)+(dy*dy));
	}

	public double distance(Point_2D p)
	{
		int dx = x-p.x;
		int dy = y-p.y;

		return ((int)Math.sqrt((dx*dx)+(dy*dy)));
	}

	public static boolean equal (Point_2D p1, Point_2D p2)
	{
		return (Point_2D.distance(p1,p2) < 1);
	}


}