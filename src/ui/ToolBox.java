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

import javax.swing.*;

// TODO : une classe ToolBoxButton
public class ToolBox extends JPanel {
	
	JButton select = new JButton(new ImageIcon("select.png"));
	JButton move = new JButton(new ImageIcon("move.png"));
	JButton newCircle = new JButton(new ImageIcon("circle.png"));
	JButton newTriangle = new JButton(new ImageIcon("triangle.png"));
	JButton newRectangle = new JButton(new ImageIcon("rectangle.png"));
	JButton newPolygon = new JButton(new ImageIcon("polygon.png"));
	
	public void addImageButton(JButton b) {
		b.setPreferredSize(new Dimension(32, 32));
		add(b);
	}
	
	public void unselectButtons() {
		select.setBackground(null);
		move.setBackground(null);
		newCircle.setBackground(null);
		newTriangle.setBackground(null);
		newRectangle.setBackground(null);
		newPolygon.setBackground(null);
	}
	public void select(JButton b) {
		unselectButtons();
		b.setBackground(Color.WHITE);
	}
	
	public ToolBox(Env env) {
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
		move.addActionListener(new ButtonListener());
		newCircle.addActionListener(new ButtonListener());
		newTriangle.addActionListener(new ButtonListener());
		newRectangle.addActionListener(new ButtonListener());
		newPolygon.addActionListener(new ButtonListener());
		select(move);
	}
	
	class SelectListener extends ButtonListener {
	}
	
	class ButtonListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			if(e.getSource() instanceof JButton) 
				select((JButton)e.getSource());
		}
	}
}
