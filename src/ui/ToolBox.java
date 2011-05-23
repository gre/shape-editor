package ui;

import java.awt.*;

import javax.swing.JScrollPane;
import javax.swing.JTextField;

public class ToolBox extends JScrollPane {
	public ToolBox(Window window) {
		JTextField c = new JTextField(); c.setBackground(Color.BLUE);// FIXME
		setViewportView(c);
	}
}
