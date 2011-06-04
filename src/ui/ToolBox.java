package ui;

import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.*;

import ui.CanvasArea.Mode;

// TODO : une classe ToolBoxButton
public class ToolBox extends JPanel {
	
	public JButton select = new JButton(new ImageIcon("select.png"));
	public JButton move = new JButton(new ImageIcon("move.png"));
	public JButton newCircle = new JButton(new ImageIcon("circle.png"));
	public JButton newTriangle = new JButton(new ImageIcon("triangle.png"));
	public JButton newRectangle = new JButton(new ImageIcon("rectangle.png"));
	public JButton newPolygon = new JButton(new ImageIcon("polygon.png"));
	List<JButton> buttons = new ArrayList<JButton>();
	
	Env env;
	
	public void addImageButton(JButton b) {
		b.setPreferredSize(new Dimension(36, 36));
		buttons.add(b);
		add(b);
	}
	public void select(JButton button) {
		for(JButton b : buttons)
			b.setSelected(false);
		button.setSelected(true);
	}
	
	public ToolBox(Env env) {
		this.env = env;
		setBorder(BorderFactory.createEtchedBorder());
		setPreferredSize(new Dimension(200, 150));
		FlowLayout gl = new FlowLayout();
		gl.setAlignment(FlowLayout.LEFT);
		setLayout(gl);
		addImageButton(select);
		addImageButton(move);
		addImageButton(newCircle);
		addImageButton(newTriangle);
		addImageButton(newRectangle);
		addImageButton(newPolygon);
		select.addActionListener(new SelectListener());
		move.addActionListener(new MoveListener());
		newCircle.addActionListener(new NewCircleListener());
		newTriangle.addActionListener(new NewTriangleListener());
		newRectangle.addActionListener(new NewRectangleListener());
		newPolygon.addActionListener(new NewPolygonListener());
		select(move);
	}

	class SelectListener extends ButtonListener {
		public SelectListener() {
			super(Mode.SELECT);
		}
		public void actionPerformed(ActionEvent e) {
			super.actionPerformed(e);
		}
	}
	class MoveListener extends ButtonListener {
		public MoveListener() {
			super(Mode.MOVE);
		}
		public void actionPerformed(ActionEvent e) {
			super.actionPerformed(e);
		}
	}
	class NewCircleListener extends ButtonListener {
		public NewCircleListener() {
			super(Mode.DRAW_CIRCLE);
		}
		public void actionPerformed(ActionEvent e) {
			super.actionPerformed(e);
		}
	}
	class NewTriangleListener extends ButtonListener {
		public NewTriangleListener() {
			super(Mode.DRAW_TRIANGLE);
		}
		public void actionPerformed(ActionEvent e) {
			super.actionPerformed(e);
		}
	}
	class NewRectangleListener extends ButtonListener {
		public NewRectangleListener() {
			super(Mode.DRAW_RECTANGLE);
		}
		public void actionPerformed(ActionEvent e) {
			super.actionPerformed(e);
		}
	}
	class NewPolygonListener extends ButtonListener {
		public NewPolygonListener() {
			super(Mode.DRAW_POLYGON);
		}
		public void actionPerformed(ActionEvent e) {
			super.actionPerformed(e);
		}
	}
	
	class ButtonListener implements ActionListener {
		Mode mode;
		public ButtonListener(Mode mode) {
			this.mode = mode;
		}
		public void actionPerformed(ActionEvent e) {
			if(e.getSource() instanceof JButton) 
				select((JButton)e.getSource());
			env.getCanvas().setMode(mode);
		}
	}
}
