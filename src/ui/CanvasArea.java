package ui;
import java.awt.Canvas;
import java.awt.Color;

import javax.swing.JScrollPane;


public class CanvasArea extends JScrollPane {
	Canvas canvas;
	public CanvasArea(Window window) {
		canvas = new Canvas();
		setViewportView(canvas);
		canvas.setBackground(Color.RED);
	}
}
