package ui;

import figure.*;

/**
 * Représente le mode de dessin actuel
 */
public enum Mode {
	/**
	 * mode Déplacement
	 */
	MOVE, 
	/**
	 * mode Sélection
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