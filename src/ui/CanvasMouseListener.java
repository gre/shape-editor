package ui;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import ui.CanvasArea.Mode;

import figure.FigureGraphic;
import figure.Point_2D;

public class CanvasMouseListener implements MouseListener, MouseMotionListener {
	
	boolean mouseIsDown = false;
	Env env;
	CanvasArea canvas;
	Point_2D lastPosition;
	
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
		figure.setOpaque(value && mouseIsDown);
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
					f.setOpaque(true);
			break;
		}
		canvas.repaint();
	}

	public void mouseDragged(MouseEvent e) {
		Mode mode = canvas.getMode();
		if(mode!=Mode.SELECT) canvas.setSelection(null);
		switch(mode) {
		case SELECT:
			Selection s = new Selection(new Color(120,120,120,150), new Color(120,120,120,50), new Point_2D(e.getX(), e.getY()), lastPosition);
			selectPoints(s);
			canvas.setSelection(s);
			canvas.repaint();
			break;
		case MOVE:
			Point_2D move = amassMove(e.getX(), e.getY());
			moveSelected(move.getX(), move.getY());
			canvas.repaint();
			break;
		}
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
				f.setOpaque(false);
			break;
		case MOVE:
			Point_2D move = amassMove(e.getX(), e.getY());
			moveSelected(move.getX(), move.getY());
			for(FigureGraphic f : env.getFigures())
				f.setOpaque(false);
			break;
		}
		canvas.repaint();
	}

	public void mouseMoved(MouseEvent e) {
	}

}
