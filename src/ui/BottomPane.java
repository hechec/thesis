package ui;

import java.awt.Color;
import java.awt.Font;
import java.awt.SystemColor;

import javax.swing.*;
import javax.swing.border.LineBorder;

public class BottomPane extends JPanel 
{
	private ABCNNTab parent;
	
	private JProgressBar progressBar;
	private JLabel lblPercent, lblStatus;
	
	public static final String START_LOADING = "Loading images...";
	public static final String END_LOADING = "images loaded";
	public static final String START_TRAINING = "Training...";
	public static final String END_TRAINING = "Done training";
	
	public BottomPane(ABCNNTab parent) 
	{
		this.parent = parent;
		
		setBorder(new LineBorder(SystemColor.activeCaptionBorder));
		setBounds(-1, 562, 730, 27);
		setLayout(null);
		
		lblStatus = new JLabel("--");
		lblStatus.setForeground(Color.BLACK);
		lblStatus.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblStatus.setHorizontalAlignment(SwingConstants.LEFT);
		lblStatus.setBounds(20, 4, 100, 20);
		add(lblStatus);
		
		progressBar = new JProgressBar();
		progressBar.setBounds(411, 4, 298, 18);
		progressBar.setMinimum(0);
		//progressBar.setBorder(null);
		//progressBar.setBackground(null);
		//progressBar.setForeground(Color.GREEN);
		progressBar.setForeground(SystemColor.textHighlight);
		add(progressBar);
		
		lblPercent = new JLabel("0%");
		lblPercent.setBounds(550, 6, 46, 14);
		add(lblPercent, 0);
		
	}
	
	public void setProgressBarMax(int max) 
	{
		progressBar.setMaximum(max);
	}

	public void incrementBar() 
	{
		progressBar.setValue(progressBar.getValue()+1);
		lblPercent.setText( (int)(100*((float)progressBar.getValue()/(float)progressBar.getMaximum()))+"%" );
		this.updateUI();
	}
	
	public void reset() {
		lblStatus.setText("--");
		progressBar.setValue(0);
		this.updateUI();
	}
	
	public void setStatus(String status)
	{
		lblStatus.setText(status);
		this.updateUI();
	}

}
