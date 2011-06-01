package ui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import javax.swing.*;
import javax.swing.filechooser.FileFilter;

public class MenuBar extends JMenuBar {

	public File openedFile = null;
	public Window parent;
	public Env env;
	
	public void open(File f) {
		openedFile = f;
		try {
			ObjectInputStream ois = new ObjectInputStream(new FileInputStream(f));
			env.set((Env) ois.readObject());
		} catch (Exception e) {
			e.printStackTrace();
			System.err.println("TODO - handle exception: "+e);
		}
	}
	
	public void save(File f) {
		openedFile = f;
		save();
	}
	
	public void save() {
		try {
			ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(openedFile));
			oos.writeObject(env);
		}
		catch(Exception e) {
			e.printStackTrace();
			System.err.println("TODO - handle exception: "+e);
		}
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
			fc.showOpenDialog(Window.getCurrent());
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
			fc.showOpenDialog(menu.parent);
		}
	}
	
	public MenuBar(Window window, Env env) {
		this.parent = window;
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
