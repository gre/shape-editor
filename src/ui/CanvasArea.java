package ui;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JScrollPane;

import figure.Circle;
import figure.FigureGraphic;


public class CanvasArea extends Canvas {
	Env env;
	
	public CanvasArea(Env env) {
		this.env = env;
	}
	
	@Override
	public void paint(Graphics g) {
		for(FigureGraphic f : env.getFigures())
			f.draw(g);
	}
}
