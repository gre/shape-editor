package ui;

import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Rectangle;

import javax.swing.*;


public class ToolBox extends JPanel {
	
	JButton move = new JButton(new ImageIcon("move.png"));
	JButton select = new JButton(new ImageIcon("select.png"));
	JButton newCircle = new JButton("circle");
	JButton newTriangle = new JButton("tri");
	JButton newRectangle = new JButton("rect");
	JButton newPolygon = new JButton("poly");
	
	public ToolBox(Env env) {
		setPreferredSize(new Dimension(200, 150));
		FlowLayout gl = new FlowLayout();
		gl.setAlignment(FlowLayout.LEFT);
		setLayout(gl);
		add(move);
		add(select);
		add(newCircle);
		add(newTriangle);
		add(newRectangle);
		add(newPolygon);
	}
}
