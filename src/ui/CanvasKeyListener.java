package ui;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import figure.FigureGraphic;

public class CanvasKeyListener implements KeyListener {

	public CanvasArea canvas;
	public Env env;
	
	public CanvasKeyListener(CanvasArea canvas, Env env) {
		this.canvas = canvas;
		this.env = env;
	}
	
	public void keyPressed(KeyEvent e) {}

	public void keyReleased(KeyEvent e) {
		FigureGraphic figure;
		CanvasMouseListener cml = env.getCanvasMouseListener();
		switch(e.getKeyCode()) {
		case 10: // ENTER
			figure = cml.getBuildingFigure();
			if(figure!=null && figure.canBeFinishedWithKey()) cml.finishBuildingFigure();
			break;
		case 27: // ESCAPE
			figure = cml.getBuildingFigure();
			if(figure !=null) {
				cml.finishBuildingFigure();
				env.remove(figure);
			}
			break;
		case 127: // DELETE
			env.removeSelected();
			break;
		}
		// System.out.print(e.getKeyCode());
	}

	public void keyTyped(KeyEvent e) {}
	
}
