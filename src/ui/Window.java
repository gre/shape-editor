package ui;
import java.awt.*;
import javax.swing.*;

public class Window extends JFrame {
	
	public Window() {
		setBounds(100, 100, 800, 600);
		setMinimumSize(new Dimension(400, 300));
		setTitle("Shape Editor");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		Container pane = getContentPane();
		pane.setLayout(new GridBagLayout());
		GridBagConstraints constraints = new GridBagConstraints();

		MenuBar menu = new MenuBar(this);
		ToolBox toolbox = new ToolBox(this);
		ToolOptions tooloptions = new ToolOptions(this);
		CanvasArea canvas = new CanvasArea(this);
		
		setMenuBar(menu);
		constraints.insets = new Insets(5, 5, 5, 5);
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
