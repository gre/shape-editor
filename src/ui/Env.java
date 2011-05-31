package ui;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import figure.Circle;
import figure.FigureGraphic;

public class Env {

	private List<FigureGraphic> figures = new ArrayList<FigureGraphic>();
	
	public Env() {
		// TMP
		figures.add(new Circle("cercle", Color.RED, Color.BLUE, 100, 100, 50));
		figures.add(new Circle("hello", Color.blue, Color.LIGHT_GRAY, 200, 300, 100));
	}
	
	public List<FigureGraphic> getFigures() {
		return figures;
	}

	public void setFigures(List<FigureGraphic> figures) {
		this.figures = figures;
	}
}
