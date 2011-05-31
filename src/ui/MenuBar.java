package ui;

import java.awt.Menu;

public class MenuBar extends java.awt.MenuBar {
	public MenuBar(Env env) {
		Menu file = new Menu("Fichier");
		add(file);
	}
}
