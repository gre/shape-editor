package ui;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.*;

import ui.Mode;

/**
 * Boite à outils qui regroupe les boutons des différents modes de l'application ainsi que les choix de couleurs
 */
@SuppressWarnings("serial")
public class ToolBox extends JPanel {
	
	public JButton select = new JButton(new ImageIcon("select.png"));
	public JButton move = new JButton(new ImageIcon("move.png"));
	public JButton newCircle = new JButton(new ImageIcon("circle.png"));
	public JButton newTriangle = new JButton(new ImageIcon("triangle.png"));
	public JButton newRectangle = new JButton(new ImageIcon("rectangle.png"));
	public JButton newPolygon = new JButton(new ImageIcon("polygon.png"));
	protected List<JButton> buttons = new ArrayList<JButton>();

	protected Canvas bgColor = new Canvas();
	protected Canvas strokeColor = new Canvas();
	protected JLabel bgLabel = new JLabel("fond");
	protected JLabel strokeLabel = new JLabel("contour");
	
	Env env;
	Window window;
	
	public JButton addImageButton(JButton b) {
		Dimension d = new Dimension(36, 36);
		b.setPreferredSize(d);
		b.setMinimumSize(d);
		b.setMaximumSize(d);
		buttons.add(b);
		return b;
	}
	private void select(JButton button) {
		for(JButton b : buttons) {
			b.setSelected(false);
			b.setBackground(null);
		}
		button.setSelected(true);
		button.setBackground(Color.white);
	}
	
	public ToolBox(Window window, Env env) {
		this.env = env;
		this.window = window;
		setBorder(BorderFactory.createEtchedBorder());
		GridBagLayout l = new GridBagLayout();
		GridBagConstraints c = new GridBagConstraints();
		setLayout(l);
		
		c.gridx = 0;
		c.gridy = 0;
		c.anchor = GridBagConstraints.WEST;
		
		JPanel panel = new JPanel();
		
		panel.add(addImageButton(select));
		panel.add(addImageButton(move));
		
		add(panel, c);
		c.gridy++;
		panel = new JPanel();
		panel.add(addImageButton(newCircle));
		panel.add(addImageButton(newTriangle));
		panel.add(addImageButton(newRectangle));
		panel.add(addImageButton(newPolygon));
		add(panel, c);
		
		c.gridy++;
		c.anchor = GridBagConstraints.CENTER;
		
		panel = new JPanel();
		panel.add(strokeColor);
		panel.add(strokeLabel);
		panel.add(bgColor);
		panel.add(bgLabel);
		add(panel, c);
		bgColor.setSize(20, 20);
		strokeColor.setSize(20, 20);
		bgColor.setBackground(env.getBackgroundColor());
		strokeColor.setBackground(env.getStrokeColor());

		final Window w = window;
		final Env e = env;
		MouseListener clickBg = new MouseListener() {
			public void mouseReleased(MouseEvent arg0) {}
			public void mousePressed(MouseEvent arg0) {}
			public void mouseExited(MouseEvent arg0) {}
			public void mouseEntered(MouseEvent arg0) {}
			public void mouseClicked(MouseEvent arg0) {
				Color c = JColorChooser.showDialog(w, "Couleur de fond", e.getBackgroundColor());
				if(c==null) return;
				e.setBackgroundColor(c);
				bgColor.setBackground(c);
			}
		};
		bgLabel.addMouseListener(clickBg);
		bgColor.addMouseListener(clickBg);
		
		MouseListener clickStroke = new MouseListener() {
			public void mouseReleased(MouseEvent arg0) {}
			public void mousePressed(MouseEvent arg0) {}
			public void mouseExited(MouseEvent arg0) {}
			public void mouseEntered(MouseEvent arg0) {}
			public void mouseClicked(MouseEvent arg0) {
				Color c = JColorChooser.showDialog(w, "Couleur de contour", e.getStrokeColor());
				if(c==null) return;
				e.setStrokeColor(c);
				strokeColor.setBackground(c);
			}
		};
		strokeLabel.addMouseListener(clickStroke);
		strokeColor.addMouseListener(clickStroke);
		
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
			env.getCanvasMouseListener().onToolChanged(mode);
		}
	}
}
