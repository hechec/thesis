package ui;

import javax.swing.JTextArea;

public class DisplayArea extends JTextArea {
	
	private GuiController guiController;
	
	public void setController(GuiController gc) {
		this.guiController = gc;
	}
	
	
}
