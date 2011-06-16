package ui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.*;
import javax.swing.filechooser.FileFilter;

/**
 * Barre de menu
 */
@SuppressWarnings("serial")
public class MenuBar extends JMenuBar {

	protected File openedFile = null;
	protected Window parent;
	protected Env env;
	
	public MenuBar(final Window parent, final Env env) {
		this.parent = parent;
		this.env = env;
		JMenu file = new JMenu("Fichier");
		JMenuItem nouveau = new JMenuItem("Nouveau");
		JMenuItem open = new JMenuItem("Ouvrir");
		JMenuItem save = new JMenuItem("Sauvegarder");
		JMenuItem saveAs = new JMenuItem("Sauvegarder sous");
		nouveau.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(!parent.confirm("Abandonner le travail actuel ?") )
					return;
				env.empty();
				env.canvas.repaint();
			}
		});
		open.addActionListener(new OpenFileListener(this));
		save.addActionListener(new SaveFileListener(this, false));
		saveAs.addActionListener(new SaveFileListener(this, true));
		file.add(nouveau);
		file.add(open);
		file.add(save);
		file.add(saveAs);
		add(file);
	}
	
	protected void open(File f) {
		if(parent.confirm("Abandonner le travail actuel ?") )
			openedFile = env.openFromFile(f) ? f : null;
	}

	protected void save(File f) {
		openedFile = env.saveToFile(f) ? f : null;
	}
	
	protected class ShapeEditorFileFilter extends FileFilter {
		public String getDescription() {
			return "Shape Editor File (.sef)";
		}
		public boolean accept(File f) {
			return f.isDirectory() || f.getName().endsWith(".sef");
		}
	}
	
	protected class OpenFileListener implements ActionListener {
		
		MenuBar menu;
		public OpenFileListener(MenuBar menu) {
			this.menu = menu;
		}
		
		public void actionPerformed(ActionEvent e) {
			final JFileChooser fc = new JFileChooser();
			fc.setDialogTitle("Ouvrir");
			fc.setFileFilter(new ShapeEditorFileFilter());
			fc.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					if(e.getActionCommand().equals("ApproveSelection")) {
						menu.open(fc.getSelectedFile());
					}
				}
			});
			fc.showOpenDialog(menu.parent);
		}
	}
	
	protected class SaveFileListener implements ActionListener {

		MenuBar menu;
		boolean saveAs;
		public SaveFileListener(MenuBar menu, boolean saveAs) {
			this.menu = menu;
			this.saveAs = saveAs;
		}
		public void actionPerformed(ActionEvent e) {
			if(!saveAs && menu.openedFile!=null) {
				menu.save(menu.openedFile);
				return;
			}
			
			final JFileChooser fc = new JFileChooser();
			fc.setApproveButtonText("Enregistrer");
			fc.setDialogTitle("Enregistrer");
			fc.setFileFilter(new ShapeEditorFileFilter());
			fc.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					if(e.getActionCommand().equals("ApproveSelection")) {
						File selected = fc.getSelectedFile();
						if(!selected.getName().endsWith(".sef"))
							selected = new File(selected.getAbsolutePath()+".sef");
						menu.save(selected);
					}
				}
			});
			fc.showSaveDialog(menu.parent);
		}
	}
}
