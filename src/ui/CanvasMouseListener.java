package ui;

import java.awt.Color;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import com.sun.management.jmx.Trace;

import ui.CanvasArea.Mode;

import figure.*;

// TODO : gros refactoring a faire : deplacer certaines fonctions + factoriser  
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
	
	public void finishBuildingFigure() {
	    buildingFigure.setBuilding(false);
	    buildingFigure = null;
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
			finishBuildingFigure();
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
			    if(buildingFigure.isBuildingConvenientToBeFinished()) {
    				circle.fitRadiusWithPoint(e.getX(), e.getY());
    				finishBuildingFigure();
			    }
			}
			else {
				buildingFigure = new Circle(env, e.getX(), e.getY());
				env.addFigure(buildingFigure);
			}
			break;
        case DRAW_RECTANGLE:
            emptyBuildingFigureIfNotInstanceOf(Rectangle.class);
            Rectangle rectangle = (Rectangle) buildingFigure;
            if(buildingFigure!=null) {
                if(buildingFigure.isBuildingConvenientToBeFinished()) {
                    rectangle.setSecondPoint(e.getX(), e.getY());
                    finishBuildingFigure();
                }
            }
            else {
                buildingFigure = new Rectangle(env, e.getX(), e.getY());
                env.addFigure(buildingFigure);
            }
            break;
        case DRAW_TRIANGLE:
            emptyBuildingFigureIfNotInstanceOf(Triangle.class);
            if(buildingFigure!=null) {
                Triangle tri = ((Triangle)buildingFigure);
                if(tri.isBuildingConvenientToBeFinished()) {
                    tri.closePath();
                    finishBuildingFigure();
                }
                else {
                    ((Triangle)buildingFigure).addPoint(e.getX(), e.getY());
                }
            }
            else {
                buildingFigure = new Triangle(env, e.getX(), e.getY());
                env.addFigure(buildingFigure);
            }
            break;
        case DRAW_POLYGON:
            emptyBuildingFigureIfNotInstanceOf(Polygon.class);
            if(buildingFigure!=null) {
                Polygon poly = ((Polygon)buildingFigure);
                if(poly.isBuildingConvenientToBeFinished()) {
                    poly.closePath();
                    finishBuildingFigure();
                }
                else {
                    ((Polygon)buildingFigure).addPoint(e.getX(), e.getY());
                }
            }
            else {
                buildingFigure = new Polygon(env, e.getX(), e.getY());
                env.addFigure(buildingFigure);
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
			if(circle!=null && circle.isBuildingConvenientToBeFinished()) {
				circle.fitRadiusWithPoint(e.getX(), e.getY());
				finishBuildingFigure();
			}
			break;
        case DRAW_RECTANGLE:
            emptyBuildingFigureIfNotInstanceOf(Rectangle.class);
            Rectangle rectangle = (Rectangle) buildingFigure;
            if(rectangle!=null && rectangle.isBuildingConvenientToBeFinished()) {
                rectangle.setSecondPoint(e.getX(), e.getY());
                finishBuildingFigure();
            }
            break;
        case DRAW_TRIANGLE:
            emptyBuildingFigureIfNotInstanceOf(Triangle.class);
            if(buildingFigure!=null && buildingFigure.isBuildingConvenientToBeFinished()) {
                ((Triangle) buildingFigure).editLastPoint(e.getX(), e.getY());
            }
            break;
        case DRAW_POLYGON:
            emptyBuildingFigureIfNotInstanceOf(Polygon.class);
            if(buildingFigure!=null && buildingFigure.isBuildingConvenientToBeFinished()) {
                ((Polygon) buildingFigure).editLastPoint(e.getX(), e.getY());
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
        case DRAW_RECTANGLE:
            emptyBuildingFigureIfNotInstanceOf(Rectangle.class);
            Rectangle rectangle = (Rectangle) buildingFigure;
            if(buildingFigure!=null) rectangle.setSecondPoint(e.getX(), e.getY());
            else mustRepaint = false;
            break;
        case DRAW_TRIANGLE:
            emptyBuildingFigureIfNotInstanceOf(Triangle.class);
            if(buildingFigure!=null) ((Triangle) buildingFigure).editLastPoint(e.getX(), e.getY());
            else mustRepaint = false;
            break;
        case DRAW_POLYGON:
            emptyBuildingFigureIfNotInstanceOf(Polygon.class);
            if(buildingFigure!=null) ((Polygon) buildingFigure).editLastPoint(e.getX(), e.getY());
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
				if(buildingFigure!=null) ((Circle) buildingFigure).fitRadiusWithPoint(e.getX(), e.getY());
				else mustRepaint = false;
				break;
            case DRAW_RECTANGLE:
                emptyBuildingFigureIfNotInstanceOf(Rectangle.class);
                if(buildingFigure!=null) ((Rectangle) buildingFigure).setSecondPoint(e.getX(), e.getY());
                else mustRepaint = false;
                break;
            case DRAW_TRIANGLE:
                emptyBuildingFigureIfNotInstanceOf(Triangle.class);
                if(buildingFigure!=null) ((Triangle) buildingFigure).editLastPoint(e.getX(), e.getY());
                else mustRepaint = false;
                break;
            case DRAW_POLYGON:
                emptyBuildingFigureIfNotInstanceOf(Polygon.class);
                if(buildingFigure!=null) ((Polygon) buildingFigure).editLastPoint(e.getX(), e.getY());
                else mustRepaint = false;
                break;
			default:
				mustRepaint = false;
			}
		}
		if(mustRepaint) canvas.repaint();
	}

}
