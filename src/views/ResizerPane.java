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
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import utilities.Debugger;

import custom.MainButton;
import custom.MyTextField;
import dataset.DataResizer;

public class ResizerPane extends JPanel 
{
	private static ResizerPane instance = null;
	private Frame frame;
	
	private ProgressPane progressPane;
	private JTextField sourceField;
	private JTextField destinationField;
	
	private JFileChooser chooser;
	
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
		
		JPanel cpanel = new JPanel();
		cpanel.setBackground(new Color(255, 204, 51));
		cpanel.setBounds(205, 117, 124, 30);
		add(cpanel);

		JLabel cLabel = new JLabel("Source");
		cLabel.setFont(new Font("Century Gothic", Font.PLAIN, 16));
		cLabel.setForeground(Color.BLACK);
		cLabel.setBounds(0, 0, 75, 30);
		cpanel.add(cLabel);
		
		sourceField = new MyTextField("click to select directory");
		sourceField.setBounds(330, 117, 260, 30);
		sourceField.setBorder(null);
		sourceField.setFont(new Font("Century Gothic", Font.PLAIN, 16));
		sourceField.setBorder(BorderFactory.createCompoundBorder(sourceField.getBorder(),
					BorderFactory.createEmptyBorder(5, 5, 5, 5)));
		add(sourceField);

		JButton button1 = new MainButton("src/images/pencil.png", "src/images/pencil.png");
		button1.setBounds(595, 117, 35, 33);
		add(button1);
		button1.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				selectDirectory(sourceField);
			}
		});
		
		JPanel cpanel2 = new JPanel();
		cpanel2.setBackground(new Color(255, 204, 51));
		cpanel2.setBounds(205, 200, 124, 30);
		add(cpanel2);

		JLabel cLabel2 = new JLabel("Destination");
		cLabel2.setFont(new Font("Century Gothic", Font.PLAIN, 16));
		cLabel2.setForeground(Color.BLACK);
		cLabel2.setBounds(0, 0, 75, 30);
		cpanel2.add(cLabel2);
		
		destinationField = new MyTextField("click to select directory");
		destinationField.setBounds(330, 200, 260, 30);
		destinationField.setBorder(null);
		destinationField.setFont(new Font("Century Gothic", Font.PLAIN, 16));
		destinationField.setBorder(BorderFactory.createCompoundBorder(destinationField.getBorder(),
					BorderFactory.createEmptyBorder(5, 5, 5, 5)));
		add(destinationField);
			
		JButton button2 = new MainButton("src/images/pencil.png", "src/images/pencil.png");
		button2.setBounds(595, 200, 35, 33);
		add(button2);
		button2.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				selectDirectory(destinationField);
			}	
		});
		
		progressPane = new ProgressPane(); 
		progressPane.setLocation(0, 425);
		this.add(progressPane);
		
		JButton resizeButton = new JButton("Resize");
		resizeButton.setBounds(20, 150, 70, 60);
		add(resizeButton);
		resizeButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(!sourceField.getText().isEmpty() && !destinationField.getText().isEmpty() ) {   
					resize();
				}
				else 
					Debugger.printError("Ooops! Enter both source and destination directory.");
			}
		});
		
		chooser = new JFileChooser("D:/kamatisan/");
		chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		
	}

	private void selectDirectory(JTextField textField) 
	{
		if (chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) { 
			textField.setText(chooser.getSelectedFile().getAbsolutePath());
		}
	}	
	
	private void resize()
	{
		new Thread(new Runnable() {
			@Override
			public void run() {
				File sourceFile = new File(sourceField.getText());
				File destFile = new File(destinationField.getText());
				DataResizer dataResizer = new DataResizer(sourceFile, destFile);
				boolean flag = dataResizer.resize();
				if(flag) {
					JOptionPane.showMessageDialog(null, "Success :)");
				}
				else {
					JOptionPane.showMessageDialog(null, "Something went wrong.", "Error", JOptionPane.WARNING_MESSAGE);
				}
			}
		}).start();
		
	}
	
	public ProgressPane getProgressPane()
	{
		return progressPane;
	}

}
