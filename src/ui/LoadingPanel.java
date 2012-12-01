package ui;

import java.awt.Color;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;

public class LoadingPanel extends JPanel 
{	
	private static LoadingPanel instance = null;
	
	private JProgressBar progressBar;
	private JLabel label;
	
	public LoadingPanel() 
	{
		setLayout(null);
		
		progressBar = new JProgressBar();
		progressBar.setBounds(0, 0, 275, 18);
		progressBar.setMinimum(0);
		progressBar.setForeground(Color.GREEN);
		add(progressBar);
		
		label = new JLabel("0%");
		label.setBounds(130, 2, 46, 14);
		add(label, 0);
	}
	
	public static LoadingPanel getInstance()
	{
		if(instance == null)
			instance = new LoadingPanel();
		return instance;
	}
	
	public void setMax(int max) 
	{
		progressBar.setMaximum(max);
	}

	public void increment() 
	{
		progressBar.setValue(progressBar.getValue()+1);
		label.setText( (int)(100*((float)progressBar.getValue()/(float)progressBar.getMaximum()))+"%" );
		this.updateUI();
	}

}
