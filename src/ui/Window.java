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

// TODO : StatusBar
public class Window extends JFrame {
	
	private CanvasArea canvas;
	private MenuBar menu;
	public ToolBox toolbox;
	public ToolOptions tooloptions;
	
	private Env env = new Env(canvas);
	
	public Window() {
		setBounds(100, 100, 800, 600);
		setMinimumSize(new Dimension(400, 300));
		setTitle("Shape Editor");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		Container pane = getContentPane();
		pane.setLayout(new GridBagLayout());
		GridBagConstraints constraints = new GridBagConstraints();

		menu = new MenuBar(this, env);
		toolbox = new ToolBox(env);
		tooloptions = new ToolOptions(env);
		canvas = new CanvasArea(env);
		
		env.setToolbox(toolbox);
		env.setCanvas(canvas);
		
		CanvasMouseListener cml = new CanvasMouseListener(canvas, env);
		canvas.addMouseListener(cml);
		canvas.addMouseMotionListener(cml);
		
		setJMenuBar(menu);
		constraints.insets = new Insets(2, 2, 2, 2);
		constraints.fill = GridBagConstraints.BOTH;
		constraints.gridx = 0;
		constraints.gridy = 0;
		constraints.weightx = 0;
		constraints.weighty = 0;
		pane.add(toolbox, constraints);

		constraints.gridx = 0;
		constraints.gridy = 1;
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
