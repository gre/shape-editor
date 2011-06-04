package ui;

import java.awt.Color;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import ui.CanvasArea.Mode;

import figure.Circle;
import figure.FigureGraphic;
import figure.Point_2D;

public class CanvasMouseListener implements MouseListener, MouseMotionListener {
	
	boolean mouseIsDown = false;
	Env env;
	CanvasArea canvas;
	Point_2D lastPosition;
	
	FigureGraphic buildingFigure = null;
	
	public CanvasMouseListener(CanvasArea c, Env env) {
		this.canvas = c;
		this.env = env;
	}
	
	// Some of this functions will probably move in Env
	public void unselectAll() {
		for(FigureGraphic f : env.getFigures())
			setSelected(f, false);
	}
	
	public FigureGraphic getOneByPosition(Point_2D p) {
		for(FigureGraphic f : env.getFigures())
			if(f.contain(p))
				return f;
		return null;
	}
	public void setSelected(FigureGraphic figure, boolean value) {
		figure.setSelected(value);
		figure.setTransparent(value && mouseIsDown);
	}
	
	public void selectFigure(FigureGraphic figure) {
		unselectAll();
		setSelected(figure, true);
		env.sortFigures();
	}
	
	public FigureGraphic selectOneByPosition(Point_2D p) {
		unselectAll();
		FigureGraphic figure = getOneByPosition(p);
		if(figure!=null) {
			setSelected(figure, true);
			env.sortFigures();
		}
		return figure;
	}
	public void selectPoints(Selection selection) {
		for(FigureGraphic f : env.getFigures())
			setSelected(f, selection.contain(f.getCenter()));
		env.sortFigures();
	}
	
	public int countSelected() {
		int nb = 0;
		for(FigureGraphic f : env.getFigures())
			if(f.isSelected())
				nb ++;
		return nb;
	}
	
	public void moveSelected(int dx, int dy) {
		for(FigureGraphic f : env.getFigures())
			if(f.isSelected())
				f.move(dx, dy);
	}
	
	/**
	 * Enregistre une position comme derniere position
	 * et retourne le deplacement associe au dernier enregistrement
	 * @return
	 */
	private Point_2D amassMove(int x, int y) {
		Point_2D move = new Point_2D(0, 0);
		if(lastPosition!=null) {
			move.setX(x - lastPosition.getX());
			move.setY(y - lastPosition.getY());
		}
		lastPosition = new Point_2D(x, y);
		return move;
	}
	
	protected void emptyBuildingFigureIfNotInstanceOf(Class<? extends FigureGraphic> c) {
		if(buildingFigure == null) return;
		if(!c.isInstance(buildingFigure)) {
			env.getFigures().remove(buildingFigure);
			buildingFigure = null;
		}
	}
	
	public void mouseClicked(MouseEvent e) {
		Mode mode = canvas.getMode();
		switch(mode) {
		case MOVE:
		case SELECT:
			selectOneByPosition(new Point_2D(e.getX(), e.getY()));
			canvas.repaint();
		}
	}
	public void mouseEntered(MouseEvent e) {}
	public void mouseExited(MouseEvent e) {}
	
	public void mousePressed(MouseEvent e) {
		mouseIsDown = true;
		Mode mode = canvas.getMode();
		canvas.setSelection(null);
		FigureGraphic figure = null;
		if(mode==Mode.MOVE || mode==Mode.SELECT) {
			figure = getOneByPosition(new Point_2D(e.getX(), e.getY()));
			if(figure!=null && !figure.isSelected()) selectFigure(figure);
		}
		amassMove(e.getX(), e.getY());
		switch(mode) {
		case MOVE:
			if(figure==null) {
				unselectAll();
				env.getToolbox().select.doClick();
			}
			for(FigureGraphic f : env.getFigures())
				if(f.isSelected())
					f.setTransparent(true);
			break;
		case DRAW_CIRCLE:
			emptyBuildingFigureIfNotInstanceOf(Circle.class);
			Circle circle = (Circle)buildingFigure;
			if(buildingFigure!=null) {
				circle.fitRadiusWithPoint(e.getX(), e.getY());
				buildingFigure = null;
			}
			else {
				buildingFigure = Circle.createByClick(env, e.getX(), e.getY());
				env.addFigure(buildingFigure);
			}
		}
		canvas.repaint();
	}

	public void mouseReleased(MouseEvent e) {
		mouseIsDown = false;
		Mode mode = canvas.getMode();
		canvas.setSelection(null);
		switch(mode) {
		case SELECT:
			if(countSelected()>0)
				env.getToolbox().move.doClick();
			for(FigureGraphic f : env.getFigures())
				f.setTransparent(false);
			break;
		case MOVE:
			Point_2D move = amassMove(e.getX(), e.getY());
			moveSelected(move.getX(), move.getY());
			for(FigureGraphic f : env.getFigures())
				f.setTransparent(false);
			break;
		case DRAW_CIRCLE:
			emptyBuildingFigureIfNotInstanceOf(Circle.class);
			Circle circle = (Circle)buildingFigure;
			if(circle!=null && circle.getRadius()>0) {
				circle.fitRadiusWithPoint(e.getX(), e.getY());
				buildingFigure = null;
			}
			break;
		}
		canvas.repaint();
	}

	public void mouseDragged(MouseEvent e) {
		Mode mode = canvas.getMode();
		boolean mustRepaint = true;
		if(mode!=Mode.SELECT) canvas.setSelection(null);
		switch(mode) {
		case SELECT:
			Selection s = new Selection(new Color(120,120,120,150), new Color(120,120,120,50), new Point_2D(e.getX(), e.getY()), lastPosition);
			selectPoints(s);
			canvas.setSelection(s);
			break;
		case MOVE:
			Point_2D move = amassMove(e.getX(), e.getY());
			moveSelected(move.getX(), move.getY());
			break;
		case DRAW_CIRCLE:
			emptyBuildingFigureIfNotInstanceOf(Circle.class);
			Circle circle = (Circle)buildingFigure;
			if(buildingFigure!=null) circle.fitRadiusWithPoint(e.getX(), e.getY());
			else mustRepaint = false;
			break;
		default: 
			mustRepaint = false;
		}
		if(mustRepaint) canvas.repaint();
	}

	public void mouseMoved(MouseEvent e) {
		Mode mode = canvas.getMode();
		boolean mustRepaint = true;
		if (buildingFigure != null) {
			switch (mode) {
			case DRAW_CIRCLE:
				emptyBuildingFigureIfNotInstanceOf(Circle.class);
				if(buildingFigure!=null) 
					((Circle) buildingFigure).fitRadiusWithPoint(e.getX(), e.getY());
				else
					mustRepaint = false;
				break;
			default:
				mustRepaint = false;
			}
		}
		if(mustRepaint) canvas.repaint();
	}

}
