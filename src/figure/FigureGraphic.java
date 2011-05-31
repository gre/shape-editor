package figure;
import java.awt.*;

public abstract class FigureGraphic implements Figure
{
	Color colorStroke, colorBackground;
	String name;
	
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

	public abstract void draw(Graphics g);
}