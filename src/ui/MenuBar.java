package ui;

import java.awt.Menu;

public class MenuBar extends java.awt.MenuBar {
	public MenuBar(Window window) {
		Menu file = new Menu("Fichier");
		add(file);
	}
}
