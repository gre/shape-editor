package ui;

import java.awt.Color;
import java.beans.EventHandler;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import figure.*;

public class Env {

	protected Data data = new Data();
	
	protected CanvasArea canvas;
	protected ToolBox toolbox;

	protected Color bg = Color.WHITE;
	protected Color stroke = Color.BLACK;

	public Color getBackgroundColor() {
		return bg;
	}
	public Color getStrokeColor() {
		return stroke;
	}
	public void setBackgroundColor(Color c) {
		bg = c;
	}
	public void setStrokeColor(Color c) {
		stroke = c;
	}
	
	/**
	 * Exportable data
	 */
	protected static class Data implements Serializable {
		/**
		 * La liste des figures est trie dans l'ordre de priorite d'affichage
		 */
		List<FigureGraphic> figures = new ArrayList<FigureGraphic>();
	}
	
	public Env(CanvasArea canvas) {
		this.canvas = canvas;
		// TMP
		data.figures.add(new Circle("cercle", Color.BLUE, new Color(70,100,255), 100, 100, 50));
		data.figures.add(new Circle("hello", Color.BLACK, Color.RED, 200, 300, 100));
		data.figures.add(new Rectangle("recta", Color.BLACK, Color.YELLOW, 150, 150, 100,100));
		Polygon p = new Polygon("polyg", Color.BLACK, Color.GREEN);
		p.addPoint(0, 0);
		p.addPoint(100, 0);
		p.addPoint(200, 100);
		p.addPoint(100, 200);
		p.move(200, 200);
		data.figures.add(p);
		data.figures.add(new Triangle("triangle", Color.blue, Color.LIGHT_GRAY, 100, 100, 150, 200, 200, 100));
	}

	public void sortFigures() {
		List<FigureGraphic> newfigures = new ArrayList<FigureGraphic>();
		for(FigureGraphic f : data.figures)
			if(f.isSelected())
				newfigures.add(f);
		for(FigureGraphic f : data.figures)
			if(!f.isSelected())
				newfigures.add(f);
		data.figures = newfigures;
	}
	
	public List<FigureGraphic> getFigures() {
		return data.figures;
	}
	
	public FigureGraphic getFirstFigureByName(String name) {
		for(FigureGraphic f : getFigures())
			if(name.equals(f.getName()))
				return f;
		return null;
	}

	public void setFigures(List<FigureGraphic> figures) {
		data.figures = figures;
	}

	public void setData(Data d) {
		data = d;
		canvas.repaint();
	}

	public void setCanvas(CanvasArea c) {
		canvas = c;
	}
	public CanvasArea getCanvas() {
		return canvas;
	}
	
	public void setToolbox(ToolBox t) {
		toolbox = t;
	}
	public ToolBox getToolbox() {
		return toolbox;
	}
	
	public void saveToFile(File f) {
		try {
			ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(f));
			oos.writeObject(data);
		}
		catch(Exception e) {
			e.printStackTrace();
			System.err.println("TODO - handle exception: "+e);
		}
	}
	
	public void openFromFile(File f) {
		try {
			ObjectInputStream ois = new ObjectInputStream(new FileInputStream(f));
			setData((Data) ois.readObject());
		} catch (Exception e) {
			e.printStackTrace();
			System.err.println("TODO - handle exception: "+e);
		}
	}
	public void addFigure(FigureGraphic buildingFigure) {
		getFigures().add(0, buildingFigure);
	}
}
