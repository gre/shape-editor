package figure;
import java.awt.*;

public abstract class FigureGraphic implements Figure
{
	Color colorStroke, colorBackground;
	String name;
	
	/**
	 * A selected figure should display differently
	 */
	boolean selected = false;
	
	public FigureGraphic (String name, Color strokeColor, Color colorBackground)	{
		this.colorStroke = strokeColor;	
		this.colorBackground = colorBackground;	
		this.name = name;
	}

	public Color getColorStroke()
	{
		return colorStroke;	
	}

	public Color getColorBackground()
	{
		return colorBackground;	
	}

	public static double distance(Figure f1, Figure f2)
	{
		return Point_2D.distance(f1.getCenter(), f2.getCenter());
	}

	protected void drawCenter(Graphics g) {
		Point_2D c = getCenter();
		g.drawLine(c.x-1, c.y, c.x+1, c.y);
		g.drawLine(c.x, c.y-1, c.x, c.y+1);
	}
	
	public abstract void draw(Graphics g);

	public void setSelected(boolean s) {
		selected = s;
	}
	public boolean isSelected() {
		return selected;
	}
}