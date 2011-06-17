package ui;

import figure.*;

/**
 * Represente le mode de dessin actuel
 */
public enum Mode {
	/**
	 * mode Deplacement
	 */
	MOVE, 
	/**
	 * mode Selection
	 */
	SELECT, 
	/**
	 * mode Cercle
	 */
	DRAW_CIRCLE, 
	/**
	 * mode Triangle
	 */
	DRAW_TRIANGLE, 
	/**
	 * mode Rectangle
	 */
	DRAW_RECTANGLE, 
	/**
	 * mode Polygon
	 */
	DRAW_POLYGON;
	
	public Class<? extends FigureGraphic> getDrawClass() {
		switch(this) {
		case DRAW_CIRCLE: return Circle.class;
		case DRAW_TRIANGLE: return Triangle.class;
		case DRAW_RECTANGLE: return Rectangle.class;
		case DRAW_POLYGON: return Polygon.class;
		}
		return null;
	}
}