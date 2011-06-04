package ui;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import figure.FigureGraphic;

@SuppressWarnings("serial")
public class CanvasArea extends Canvas {
	Env env;
	Mode mode = Mode.MOVE;
	Selection selection = null;
	
	public enum Mode {
		MOVE, SELECT, DRAW_CIRCLE, DRAW_TRIANGLE, DRAW_RECTANGLE, DRAW_POLYGON;
	}
	
	public CanvasArea(Env env) {
		this.env = env;
		setBackground(Color.white);
	}
	
	@Override
	public void paint(Graphics g) {
		Graphics2D g2d = (Graphics2D) g;
	    g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		List<FigureGraphic> figures = new ArrayList<FigureGraphic>(env.getFigures());
		Collections.reverse(figures);
		for(FigureGraphic f : figures)
			f.draw(g);
		if(selection!=null)
			selection.draw(g);
	}
	
	public void setMode(Mode m) {
		this.mode = m;
	}
	public Mode getMode() {
		return this.mode;
	}

	public void setSelection(Selection s) {
		selection = s;
	}
	
	/**
	 * Double buffering implementation
	 */
	public void update(Graphics g) {
		Graphics offgc;
		Image offscreen = null;
		@SuppressWarnings("deprecation")
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
