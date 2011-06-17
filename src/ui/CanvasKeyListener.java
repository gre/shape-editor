package ui;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import figure.FigureGraphic;

/**
 * Ecoute les evenements du clavier sur la zone de dessin
 */
public class CanvasKeyListener extends KeyAdapter {

	public CanvasArea canvas;
	public Env env;
	
	public CanvasKeyListener(CanvasArea canvas, Env env) {
		this.canvas = canvas;
		this.env = env;
	}
	
	public void keyReleased(KeyEvent e) {
		FigureGraphic figure;
		CanvasMouseListener cml = env.getCanvasMouseListener();
		switch(e.getKeyCode()) {
		case 10: // ENTER
			figure = cml.getBuildingFigure();
			if(figure!=null && figure.canBeFinishedWithKey()) cml.finishBuildingFigure();
			canvas.repaint();
			return;
		case 27: // ESCAPE
			figure = cml.getBuildingFigure();
			if(figure !=null) {
				cml.finishBuildingFigure();
				env.remove(figure);
			}
			canvas.repaint();
			return;
		case 127: // DELETE
			env.removeSelected();
			return;
		}
		switch(e.getKeyChar()) {
		case 'a':
			if(env.getFigures().size() == env.getSelected().size())
				env.unselectAll();
			else
				env.selectAll();
			return;
		}
	}
	
}
