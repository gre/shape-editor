package ui;

import figure.*;


public enum Mode {
	MOVE, SELECT, DRAW_CIRCLE, DRAW_TRIANGLE, DRAW_RECTANGLE, DRAW_POLYGON;
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