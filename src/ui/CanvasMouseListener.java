package ui;

import java.awt.Color;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import ui.CanvasArea.Mode;

import figure.*;

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
	
	public FigureGraphic getBuildingFigure() {
		return buildingFigure;
	}
	
	public void finishBuildingFigure() {
		buildingFigure.setBuilding(false);
		buildingFigure.onFigureFinish();
		buildingFigure = null;
	}
	
	public void onToolChanged(Mode mode) {
		if(buildingFigure==null) return;
		env.getFigures().remove(buildingFigure);
		finishBuildingFigure();
		canvas.repaint();
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
			env.selectOneByPosition(new Point_2D(e.getX(), e.getY()));
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
			figure = env.getOneByPosition(new Point_2D(e.getX(), e.getY()));
			if(figure!=null && !figure.isSelected()) env.selectFigure(figure);
		}
		amassMove(e.getX(), e.getY());
		switch(mode) {
		case MOVE:
			if(figure==null) {
				env.unselectAll();
				env.getToolbox().select.doClick();
			}
			for(FigureGraphic f : env.getFigures())
				if(f.isSelected())
					f.setTransparent(true);
			break;
		case DRAW_CIRCLE:
		case DRAW_RECTANGLE:
		case DRAW_TRIANGLE:
		case DRAW_POLYGON:
			if(buildingFigure!=null) {
				if(buildingFigure.canBeFinishedWithMouse()) {
					finishBuildingFigure();
				}
				else {
					buildingFigure.onPressPoint(e.getX(), e.getY());
				}
			}
			else {
				try {
					buildingFigure = mode.getDrawClass().newInstance();
					buildingFigure.init(env, e.getX(), e.getY());
					env.addFigure(buildingFigure);
				}
				catch (Exception exception) {
					exception.printStackTrace();
				}
			}
			break;
		}
		canvas.repaint();
	}

	public void mouseReleased(MouseEvent e) {
		mouseIsDown = false;
		Mode mode = canvas.getMode();
		canvas.setSelection(null);
		switch(mode) {
		case SELECT:
			if(env.countSelected()>0)
				env.getToolbox().move.doClick();
			for(FigureGraphic f : env.getFigures())
				f.setTransparent(false);
			break;
		case MOVE:
			Point_2D move = amassMove(e.getX(), e.getY());
			env.moveSelected(move.getX(), move.getY());
			for(FigureGraphic f : env.getFigures())
				f.setTransparent(false);
			break;
		case DRAW_CIRCLE:
		case DRAW_TRIANGLE:
		case DRAW_POLYGON:
		case DRAW_RECTANGLE:
			if(buildingFigure!=null) {
				boolean canBeFinished = buildingFigure.canBeFinishedWithMouse();
				buildingFigure.onReleasePoint(e.getX(), e.getY());
				if(canBeFinished) finishBuildingFigure();
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
			env.selectPoints(s);
			canvas.setSelection(s);
			break;
		case MOVE:
			Point_2D move = amassMove(e.getX(), e.getY());
			env.moveSelected(move.getX(), move.getY());
			break;
		case DRAW_CIRCLE:
		case DRAW_RECTANGLE:
		case DRAW_TRIANGLE:
		case DRAW_POLYGON:
			if(buildingFigure!=null) buildingFigure.onMovePoint(e.getX(), e.getY());
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
			case DRAW_RECTANGLE:
			case DRAW_TRIANGLE:
			case DRAW_POLYGON:
				if(buildingFigure!=null) buildingFigure.onMovePoint(e.getX(), e.getY());
				else mustRepaint = false;
				break;
			default:
				mustRepaint = false;
			}
		}
		if(mustRepaint) canvas.repaint();
	}

}
