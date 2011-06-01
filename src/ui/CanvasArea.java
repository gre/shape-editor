package ui;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.swing.JScrollPane;

import figure.Circle;
import figure.FigureGraphic;


public class CanvasArea extends Canvas {
	Env env;
	
	public CanvasArea(Env env) {
		this.env = env;
		setBackground(Color.white);
	}
	
	@Override
	public void paint(Graphics g) {
		List<FigureGraphic> figures = new ArrayList<FigureGraphic>(env.getFigures());
		Collections.reverse(figures);
		for(FigureGraphic f : figures)
			f.draw(g);
	}
	
	/**
	 * Double buffering implementation
	 */
	public void update(Graphics g) {
		Graphics offgc;
		Image offscreen = null;
		Dimension d = size();
		// create the offscreen buffer and associated Graphics
		offscreen = createImage(d.width, d.height);
		offgc = offscreen.getGraphics();
		// clear the exposed area
		offgc.setColor(getBackground());
		offgc.fillRect(0, 0, d.width, d.height);
		offgc.setColor(getForeground());
		// do normal redraw
		paint(offgc);
		// transfer offscreen to window
		g.drawImage(offscreen, 0, 0, this);
	}
}
