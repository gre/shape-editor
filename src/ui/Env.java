package ui;

import java.awt.Color;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import figure.*;

/**
 * Variable d'environnement qui contient tous le contexte du dessin actuel
 */
public class Env {
	
	protected Data data = new Data();
	
	protected Window window;
	
	protected CanvasArea canvas;
	protected CanvasMouseListener canvasMouseListener;
	
	protected ToolBox toolbox;
	protected SelectionPanel selectionPanel;

	protected Color bg = new Color(150, 150, 250);
	protected Color stroke = Color.BLACK;

	/**
	 * Exportable data
	 */
	@SuppressWarnings("serial")
	protected static class Data implements Serializable {
		/**
		 * La liste des figures est trie dans l'ordre de priorite d'affichage
		 */
		List<FigureGraphic> figures = new ArrayList<FigureGraphic>();
	}
	
	public Env(Window window) {
		this.window = window;
	}

	public CanvasMouseListener getCanvasMouseListener() {
		return canvasMouseListener;
	}
	public void setCanvasMouseListener(CanvasMouseListener canvasMouseListener) {
		this.canvasMouseListener = canvasMouseListener;
	}
	
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
	public void setSelectionPanel(SelectionPanel t) {
		selectionPanel = t;
	}
	public SelectionPanel getSelectionPanel() {
		return selectionPanel;
	}

	public List<FigureGraphic> getFigures() {
		return data.figures;
	}
	public void setFigures(List<FigureGraphic> figures) {
		data.figures = figures;
	}
	
	/**
	 * Trie les figures de telle sorte que les figures s�lectionn�es apparaissent en premier. Outre la s�lection, l'ordre est conserv�.
	 */
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
	
	/**
	 * Sauvegarde le dessin actuel dans un fichier
	 * @param f le fichier
	 * @return true si sauvegard� avec succ�s, false sinon
	 */
	public boolean saveToFile(File f) {
		try {
			ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(f));
			oos.writeObject(data);
			return true;
		}
		catch(Exception e) {
			e.printStackTrace();
			window.error("Impossible de sauvegarder le dessin dans le fichier choisi");
		}
		return false;
	}
	
	/**
	 * Ouvrir un dessin depuis un fichier
	 * @param f le fichier � ouvrir
	 * @return true si ouvert avec succ�s, false si l'ouverture du dessin a �chou� (fichier incompatible, erreur de fichier)
	 */
	public boolean openFromFile(File f) {
		try {
			ObjectInputStream ois = new ObjectInputStream(new FileInputStream(f));
			setData((Data) ois.readObject());
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			window.error("Impossible d'ouvrir le fichier");
		}
		return false;
	}
	
	/**
	 * Ajoute une figure au dessin
	 */
	public void addFigure(FigureGraphic f) {
		getFigures().add(0, f);
	}
	
	/**
	 * R�initialise le dessin (supprime toutes les figures)
	 */
	public void empty() {
		data = new Data();
	}
	
	/**
	 * compare deux listes
	 * @param f
	 * @param g
	 * @return true si les deux listes sont �gales
	 */
	public boolean listsAreSame(List<FigureGraphic> f, List<FigureGraphic> g) {
		return f.size() == g.size() && f.containsAll(g);
	}
	
	private List<FigureGraphic> lastSelection = new ArrayList<FigureGraphic>();
	/**
	 * Fonction appel� quand la s�lection a chang�
	 */
	public void onSelectionChanged() {
		List<FigureGraphic> s = new ArrayList<FigureGraphic>( getSelected() );
		if(!listsAreSame(s, lastSelection)) {
			canvas.repaint();
			selectionPanel.onSelectionChanged();
		}
		lastSelection = s;
	}
	
	// Selection

	/**
	 * Annule la s�lection actuelle
	 */
	private void emptySelection() {
		for(FigureGraphic f : getFigures())
			setSelected(f, false);
	}
	
	/**
	 * D�selectionne toutes les figures
	 */
	public void unselectAll() {
		emptySelection();
		onSelectionChanged();
	}
	
	/**
	 * Recherche la premi�re figure qui contient le point p
	 * @param p
	 * @return la premi�re figure trouv�e sous le point p , null si aucune figure trouv�e
	 */
	public FigureGraphic getOneByPosition(Point_2D p) {
		for(FigureGraphic f : getFigures())
			if(f.contain(p))
				return f;
		return null;
	}
	
	private void setSelected(FigureGraphic figure, boolean value) {
		figure.setSelected(value);
		figure.setTransparent(value && canvasMouseListener.mouseIsDown);
	}
	
	/**
	 * R�cup�re les figures s�lectionn�es
	 * @return une liste de toutes les figures s�lectionn�es
	 */
	public List<FigureGraphic> getSelected() {
		List<FigureGraphic> figures = new ArrayList<FigureGraphic>();
		for(FigureGraphic f : getFigures())
			if(f.isSelected())
				figures.add(f);
		return figures;
	}
	
	/**
	 * S�lectionne une et une seule figure
	 * @param figure : la figure � s�lectionner
	 */
	public void selectFigure(FigureGraphic figure) {
		emptySelection();
		setSelected(figure, true);
		sortFigures();
		onSelectionChanged();
	}
	
	/**
	 * Selectionne la premi�re figure qui contient p
	 * @param p : le point
	 * @return la figure s�lectionn� ou null
	 */
	public FigureGraphic selectOneByPosition(Point_2D p) {
		emptySelection();
		FigureGraphic figure = getOneByPosition(p);
		if(figure!=null) {
			setSelected(figure, true);
			sortFigures();
		}
		onSelectionChanged();
		return figure;
	}
	
	/**
	 * S�lectionne un ensemble de figures gr�ce � une s�lection.
	 * Le centre des figures est d�terminant pour savoir si elles appartiennent � cette s�lection.
	 * @param selection
	 */
	public void selectPoints(Selection selection) {
		for(FigureGraphic f : getFigures())
			setSelected(f, selection.contain(f.getCenter()));
		sortFigures();
		onSelectionChanged();
	}
	
	/**
	 * S�lectionne toutes les figures.
	 */
	public void selectAll() {
		for(FigureGraphic f : getFigures())
			f.setSelected(true);
		onSelectionChanged();
	}
	
	/**
	 * @return le nombre de figures s�lectionn�es
	 */
	public int countSelected() {
		return getSelected().size();
	}
	
	/**
	 * D�place toutes les figures s�lectionn�es
	 * @param dx : d�placement en x
	 * @param dy : d�placement en y
	 */
	public void moveSelected(int dx, int dy) {
		for(FigureGraphic f : getSelected())
			f.move(dx, dy);
	}
	
	/**
	 * Supprime une figure du dessin
	 * @param figure
	 */
	public void remove(Figure figure) {
		getFigures().remove(figure);
		onSelectionChanged();
	}
	
	/**
	 * Supprime les figures selectionn�es du dessin
	 */
	public void removeSelected() {
		List<FigureGraphic> figures = getFigures();
		for(FigureGraphic f : getSelected())
			figures.remove(f);
		onSelectionChanged();
	}
	
}
