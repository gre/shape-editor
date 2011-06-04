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
	
	Env env;
	CanvasArea canvas;
	Point_2D lastPosition;
	
	public CanvasMouseListener(CanvasArea c, Env env) {
		this.canvas = c;
		this.env = env;
	}
	
	public void unselectAll() {
		for(FigureGraphic f : env.getFigures())
			f.setSelected(false);
	}
	
	public FigureGraphic getOneByPosition(Point_2D p) {
		for(FigureGraphic f : env.getFigures())
			if(f.contain(p))
				return f;
		return null;
	}
	public void selectFigure(FigureGraphic figure) {
		unselectAll();
		figure.setSelected(true);
		env.sortFigures();
	}
	
	public FigureGraphic selectOneByPosition(Point_2D p) {
		unselectAll();
		FigureGraphic figure = getOneByPosition(p);
		if(figure!=null) {
			figure.setSelected(true);
			env.sortFigures();
		}
		return figure;
	}
	public void selectPoints(Selection selection) {
		for(FigureGraphic f : env.getFigures())
			f.setSelected(selection.contain(f.getCenter()));
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
		Mode mode = canvas.getMode();
		canvas.setSelection(null);
		FigureGraphic figure = getOneByPosition(new Point_2D(e.getX(), e.getY()));
		if(figure!=null && !figure.isSelected()) selectFigure(figure);
		
		amassMove(e.getX(), e.getY());
		switch(mode) {
		case MOVE:
			if(figure==null) {
				unselectAll();
				env.getToolbox().select.doClick();
			}
			canvas.repaint();
			break;
		}
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
		Mode mode = canvas.getMode();
		canvas.setSelection(null);
		switch(mode) {
		case SELECT:
			if(countSelected()>0)
				env.getToolbox().move.doClick();
			break;
		case MOVE:
			Point_2D move = amassMove(e.getX(), e.getY());
			moveSelected(move.getX(), move.getY());
			break;
		}
		canvas.repaint();
	}

	public void mouseMoved(MouseEvent e) {
	}

}
