package main;

import java.awt.EventQueue;

import javax.swing.UIManager;

import ui.AppFrame;

public class Main {

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					
					UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
					AppFrame frame = new AppFrame();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

}
