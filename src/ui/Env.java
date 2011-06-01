package ui;

import java.awt.Color;
import java.beans.EventHandler;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import figure.*;

public class Env implements Serializable {

	/**
	 * La lste des figures est trie dans l'ordre de priorite d'affichage
	 */
	private List<FigureGraphic> figures = new ArrayList<FigureGraphic>();
	
	public Env() {
		// TMP
		figures.add(new Circle("cercle", Color.RED, Color.BLUE, 100, 100, 50));
		figures.add(new Circle("hello", Color.blue, Color.LIGHT_GRAY, 200, 300, 100));
		figures.add(new Rectangle("recta", Color.blue, Color.LIGHT_GRAY, 150, 150, 100,100));
		Polygon p = new Polygon("polyg", Color.blue, Color.LIGHT_GRAY);
		p.addPoint(0, 0);
		p.addPoint(100, 100);
		p.addPoint(100, 300);
		figures.add(p);
		figures.add(new Triangle("triangle", Color.blue, Color.LIGHT_GRAY, 100, 100, 100, 200, 200, 300));
	}
	
	public void sortFigures() {
		List<FigureGraphic> newfigures = new ArrayList<FigureGraphic>();
		for(FigureGraphic f : figures)
			if(f.isSelected())
				newfigures.add(f);
		for(FigureGraphic f : figures)
			if(!f.isSelected())
				newfigures.add(f);
		figures = newfigures;
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
