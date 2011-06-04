package ui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.*;
import javax.swing.filechooser.FileFilter;

// TODO : Fichier -> Nouveau
@SuppressWarnings("serial")
public class MenuBar extends JMenuBar {

	public File openedFile = null;
	public JFrame parent;
	public Env env;
	
	public void open(File f) {
		openedFile = f;
		env.openFromFile(f);
	}
	
	public void save(File f) {
		openedFile = f;
		save();
	}
	
	public void save() {
		env.saveToFile(openedFile);
	}
	
	class ShapeEditorFileFilter extends FileFilter {
		public String getDescription() {
			return "Shape Editor File (.sef)";
		}
		public boolean accept(File f) {
			return f.isDirectory() || f.getName().endsWith(".sef");
		}
	}
	
	class OpenFileListener implements ActionListener {
		
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
	
	class SaveFileListener implements ActionListener {

		MenuBar menu;
		boolean saveAs;
		public SaveFileListener(MenuBar menu, boolean saveAs) {
			this.menu = menu;
			this.saveAs = saveAs;
		}
		public void actionPerformed(ActionEvent e) {
			if(!saveAs && menu.openedFile!=null) {
				menu.save();
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
	
	public MenuBar(JFrame parent, Env env) {
		this.parent = parent;
		this.env = env;
		JMenu file = new JMenu("Fichier");
		JMenuItem open = new JMenuItem("Ouvrir");
		JMenuItem save = new JMenuItem("Sauvegarder");
		JMenuItem saveAs = new JMenuItem("Sauvegarder sous");
		open.addActionListener(new OpenFileListener(this));
		save.addActionListener(new SaveFileListener(this, false));
		saveAs.addActionListener(new SaveFileListener(this, true));
		file.add(open);
		file.add(save);
		file.add(saveAs);
		add(file);
	}
}
