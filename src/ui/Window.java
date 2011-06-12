package ui;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.*;

@SuppressWarnings("serial")
public class Window extends JFrame {
	
	private CanvasArea canvas;
	private MenuBar menu;
	public ToolBox toolbox;
	public ToolOptions tooloptions;
	
	private Env env = new Env(this);
	
	public Window() {
		setBounds(0, 0, 800, 600);
		setMinimumSize(new Dimension(400, 300));
		setLocationRelativeTo(null);
		setTitle("Shape Editor");
		setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		addWindowListener(new WindowAdapter() {
		    public void windowClosing(WindowEvent e) {
		        if(confirm("Quitter et abandonner ce dessin ?"))
		            System.exit(0);
		    }
        });
		
		Container pane = getContentPane();
		pane.setLayout(new GridBagLayout());
		GridBagConstraints constraints = new GridBagConstraints();

		menu = new MenuBar(this, env);
		toolbox = new ToolBox(this, env);
		tooloptions = new ToolOptions(env);
		canvas = new CanvasArea(env);
		CanvasMouseListener cml = new CanvasMouseListener(canvas, env);
		CanvasKeyListener ckl = new CanvasKeyListener(canvas, env);
		
		env.setToolbox(toolbox);
		env.setCanvas(canvas);
		env.setCanvasMouseListener(cml);
		
		canvas.addMouseListener(cml);
		canvas.addMouseMotionListener(cml);
		canvas.addKeyListener(ckl);
		
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
	
	/**
	 * Pose une question à l'utilisateur de type Oui/Non à travers une popup 
	 * @param message : la question
	 * @return la réponse booléenne
	 */
    public boolean confirm(String message, String title) {
        return JOptionPane.OK_OPTION == JOptionPane.showConfirmDialog(this, message, title, JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE);
    }
    public boolean confirm(String message) {
        return confirm(message, null);
    }

	public void error(String message) {
		JOptionPane.showConfirmDialog(this, message, null, JOptionPane.CLOSED_OPTION, JOptionPane.ERROR_MESSAGE);
	}
	
	public static void main(String[] args) {
		new Window();
	}
}
