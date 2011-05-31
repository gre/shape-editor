package figure;

import figure.Point_2D;

import java.awt.Color;
import java.io.Serializable;

public class Triangle extends Polygon implements Serializable
{
	public Triangle(String name, Color colorStroke, Color colorBackground, int x1, int y1, int x2, int y2, int x3, int y3) {
		super(name,colorStroke,colorBackground);
		addPoint(x1,y1);
		addPoint(x2,y2);
		addPoint(x3,y3);
	}
}