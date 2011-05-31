package ui;

import java.awt.Color;
import java.beans.EventHandler;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import figure.*;

public class Env implements Serializable {

	private List<FigureGraphic> figures = new ArrayList<FigureGraphic>();
	
	public Env() {
		// TMP
		figures.add(new Circle("cercle", Color.RED, Color.BLUE, 100, 100, 50));
		figures.add(new Circle("hello", Color.blue, Color.LIGHT_GRAY, 200, 300, 100));
		figures.add(new Rectangle("recta", Color.blue, Color.LIGHT_GRAY, 150, 150, 250,250));
		Polygon p;
		p = new Polygon("polyg", Color.blue, Color.LIGHT_GRAY);
		p.addPoint(0, 0);
		p.addPoint(100, 100);
		p.addPoint(100, 300);
		
		figures.add(p);
		
		p = new Triangle("triangle", Color.blue, Color.LIGHT_GRAY, 100, 100, 100, 200, 200, 300);

		figures.add(p);
		
		

	}
	
	public List<FigureGraphic> getFigures() {
		return figures;
	}

	public void setFigures(List<FigureGraphic> figures) {
		this.figures = figures;
	}

	public void set(Env newEnv) {
		figures = new ArrayList<FigureGraphic>(newEnv.figures);
		Window.getCurrent().triggerChange();
	}
}
