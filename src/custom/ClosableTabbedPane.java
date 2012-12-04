package custom;

import java.awt.Component;
import java.awt.Cursor;
import java.awt.Graphics;
import javax.swing.*;

import ui.AppFrame;

public class ClosableTabbedPane extends JTabbedPane
{
	private TabCloseUI closeUI = new TabCloseUI(this);
	
	private AppFrame appFrame;
	
	public ClosableTabbedPane(AppFrame appFrame) {
		this.appFrame = appFrame;
	}

	public void paint(Graphics g){
		super.paint(g);
		closeUI.paint(g);
	}
	
	public void addTabb(String title, Component component) {
		super.addTab(title+"     ", null, component);
	}
	
	public String getTabTitleAt(int index) {
		return super.getTitleAt(index).trim();
	}

	public boolean tabAboutToClose(int tabIndex) {
		return true;
	}
	
	public static void main(String[] args)
	{
		
		JFrame frame = new JFrame();
		
		ClosableTabbedPane tabbedPane = new ClosableTabbedPane(null);
		tabbedPane.setSize(200, 200);
		
		tabbedPane.addTabb("Tab 1", new JPanel());
		tabbedPane.addTabb("Tab 2", new JPanel());
		
		frame.getContentPane().add(tabbedPane);
		
		frame.pack();
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		
	}

	public void removeTab(int selectedTab) {
		appFrame.removeTab(selectedTab);
	}
	
}
