package ui;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.ArrayList;
import java.util.List;

import javax.swing.*;

import figure.Circle;
import figure.Figure;
import figure.FigureGraphic;

public class Window extends JFrame {
	
	Env env = new Env();

	public Window() {
		setBounds(100, 100, 800, 600);
		setMinimumSize(new Dimension(400, 300));
		setTitle("Shape Editor");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		Container pane = getContentPane();
		pane.setLayout(new GridBagLayout());
		GridBagConstraints constraints = new GridBagConstraints();

		MenuBar menu = new MenuBar(env);
		ToolBox toolbox = new ToolBox(env);
		ToolOptions tooloptions = new ToolOptions(env);
		CanvasArea canvas = new CanvasArea(env);
		
		canvas.addMouseListener(new CanvasMouseListener(canvas, env));
		
		setMenuBar(menu);
		constraints.insets = new Insets(2, 2, 2, 2);
		constraints.fill = GridBagConstraints.BOTH;
		constraints.gridx = 0;
		constraints.gridy = 0;
		constraints.weightx = 0;
		constraints.weighty = 0;
		constraints.ipadx = 200;
		constraints.ipady = 100;
		pane.add(toolbox, constraints);

		constraints.gridx = 0;
		constraints.gridy = 1;
		constraints.ipadx = 0;
		constraints.ipady = 0;
		pane.add(tooloptions, constraints);

		constraints.gridx = 1;
		constraints.gridy = 0;
		constraints.gridheight = 2;
		constraints.weightx = 1.0;
		constraints.weighty = 1.0;
		pane.add(canvas, constraints);
		
		setVisible(true);
	}
	
	public static void main(String[] args) {
		new Window();
	}
}