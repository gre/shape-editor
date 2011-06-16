package figure;

import figure.Point_2D;

import java.awt.Graphics;

/**
* An interface implemented by other figures.
* By implementing this iterface figures can be used for various operations like drawing, drag&drop, selecting, etc.
*/
public interface Figure
{
	/**
	* Center of the figure
	* @return Point_2D
	*/
	public abstract Point_2D getCenter();

	/**
	* Move the figure by dx and dy
	* @param int dx delta x
	* @Param int dy delta y
	* @return void
	*/
	public abstract void move(int dx, int dy);

	/**
	* Tell if the figure is in that point p
	* @param Point_2D p point
	* @return boolean
	*/
	public abstract boolean contain(Point_2D p);
	
	/**
	* Get the name of the figure
	*/
	public abstract String toString();

	/**
	* Drag the figure on the screen
	*/
	public abstract void draw(Graphics gx);
}