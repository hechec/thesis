package views;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import custom.MainButton;
import dataset.DataResizer;

public class ResizerPane extends JPanel 
{
	private static ResizerPane instance = null;
	private Frame frame;
	
	private ProgressPane progressPane;
	private JTextField directoryField;
	
	public static ResizerPane  getInstance() 
	{
		if(instance == null)
			instance = new ResizerPane();
		return instance;
	}
	
	public ResizerPane()
	{
		frame = Frame.getInstance();
		setLayout(null);	
	
		JButton backButton = new MainButton("src/images/back.png", "src/images/backHover.png");
		backButton.setBounds(10, 0, 71, 51);
		this.add(backButton, 0);
		backButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				frame.setView(frame.getNextView(0));
			}
		});
		
		JLabel label = new JLabel("Image Resizer");
		label.setFont(new Font("Century Gothic", Font.PLAIN, 24));
		label.setForeground(Color.WHITE);
		label.setBounds(145, 46, 300, 30);
		add(label);
		
		JPanel line = new JPanel();
		line.setBackground(new Color(255, 204, 51));
		line.setBounds(145, 83, 475, 1);
		add(line);
		
		progressPane = new ProgressPane(); 
		progressPane.setLocation(0, 425);
		this.add(progressPane);
		
		JButton resizeButton = new JButton("Resize");
		resizeButton.setBounds(20, 150, 70, 60);
		add(resizeButton);
		resizeButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				resize();
			}
		});
		
	}
	
	private void resize()
	{
		new Thread(new Runnable() {
			@Override
			public void run() {
				File destFile = new File("C:/Users/hechec/Desktop/Jake");
				File sourceFile = new File("D:/kamatisan/KAMATISAN");
				DataResizer dataResizer = new DataResizer(sourceFile, destFile);
				dataResizer.resize();
			}
		}).start();
		
	}
	
	public ProgressPane getProgressPane()
	{
		return progressPane;
	}

}
