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
	
	@Override
	public void mouseClicked(MouseEvent e) {
		for(FigureGraphic f : env.getFigures()) {
			f.setSelected(f.contain(new Point_2D(e.getX(), e.getY())));
		}
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
	/*
	private Point_2D amassMove(e) {
		Point_2D
		if(lastMove==null) lastMove=new Point_2D(0, 0);
	}
	*/
	
	@Override
	public void mousePressed(MouseEvent e) {
		Point_2D p = new Point_2D(e.getX(), e.getY());
		for(FigureGraphic f : env.getFigures()) {
			f.setSelected(f.contain(p));
		}
		canvas.repaint();
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		/*
		Point_2D p = new Point_2D(e.getX(), e.getY());
		int dx = e.getX() - lastMove.getX();
		int dy = e.getY() - lastMove.getY();
		lastMove = null;
		canvas.repaint();
		*/
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		
	}

}
