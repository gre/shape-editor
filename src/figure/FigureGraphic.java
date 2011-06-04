package figure;
import java.awt.*;
import java.awt.color.ColorSpace;
import java.io.Serializable;

@SuppressWarnings("serial")
public abstract class FigureGraphic implements Figure, Serializable
{
	Color colorStroke, colorBackground;
	String name;
	
	/**
	 * A selected figure should display differently
	 */
	boolean selected = false;

	public FigureGraphic (String name, Color colorStroke, Color colorBackground)	{
		this.colorStroke = colorStroke;	
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
	
	protected void drawName(Graphics g) {
		Point_2D c = getCenter();
		g.drawString(name, c.x+2, c.y+12);
	}
	
	public abstract void draw(Graphics g);

	public void afterDraw(Graphics g) {
		if(isSelected()) {
			drawCenter(g);
			drawName(g);
		}
	}

	public Color getStrokeForCurrentState() {
		Color c = colorStroke;
		if(selected) return new Color(c.getRed(), c.getGreen(), c.getBlue(), 200);
		return c;
	}
	public Color getBgForCurrentState() {
		Color c = colorBackground;
		if(selected) return new Color(c.getRed(), c.getGreen(), c.getBlue(), 200);
		return c;
	}
	
	public void setSelected(boolean s) {
		selected = s;
	}
	public boolean isSelected() {
		return selected;
	}
}