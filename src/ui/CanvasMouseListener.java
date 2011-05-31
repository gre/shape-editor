package ui;

import java.awt.Canvas;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import figure.FigureGraphic;
import figure.Point_2D;

public class CanvasMouseListener implements MouseListener, MouseMotionListener {
	
	Env env;
	Canvas canvas;
	Point_2D lastPosition;
	
	public CanvasMouseListener(Canvas c, Env env) {
		this.canvas = c;
		this.env = env;
	}
	
	public void unselectAll() {
		for(FigureGraphic f : env.getFigures())
			f.setSelected(false);
	}
	
	public FigureGraphic selectOneByPosition(Point_2D p) {
		unselectAll();
		for(FigureGraphic f : env.getFigures()) {
			if(f.contain(p)) {
				f.setSelected(true);
				env.sortFigures();
				return f;
			}
		}
		return null;
	}
	
	public void moveSelected(int dx, int dy) {
		for(FigureGraphic f : env.getFigures())
			if(f.isSelected())
				f.move(dx, dy);
	}
	
	@Override
	public void mouseClicked(MouseEvent e) {
		selectOneByPosition(new Point_2D(e.getX(), e.getY()));
		canvas.repaint();
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub

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
	
	@Override
	public void mousePressed(MouseEvent e) {
		amassMove(e.getX(), e.getY());
		Point_2D p = new Point_2D(e.getX(), e.getY());
		selectOneByPosition(p);
		canvas.repaint();
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		Point_2D move = amassMove(e.getX(), e.getY());
		moveSelected(move.getX(), move.getY());
		canvas.repaint();
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		Point_2D move = amassMove(e.getX(), e.getY());
		moveSelected(move.getX(), move.getY());
		canvas.repaint();
	}

	@Override
	public void mouseMoved(MouseEvent e) {
	}

}
