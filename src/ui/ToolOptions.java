package ui;
import java.awt.Color;

import javax.swing.JScrollPane;
import javax.swing.JTextField;


public class ToolOptions extends JScrollPane {
	public ToolOptions(Window window) {
		JTextField c = new JTextField(); c.setBackground(Color.GREEN);// FIXME
		setViewportView(c);
	}
}
