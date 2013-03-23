package views;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
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
import javax.swing.SwingConstants;

import views.optionpane.MessageDialog;

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
	private JButton resizeButton;
	
	private JFileChooser chooser;
	private File sourceFile = null, destinationFile = null;
	
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
	
		JButton backButton = new MainButton("/images/back.png", "/images/backHover.png");
		backButton.setBounds(10, 0, 71, 51);
		this.add(backButton, 0);
		backButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				frame.setView(frame.getNextView(0));
			}
		});
		
		JLabel label = new JLabel("DATASET RESIZER");
		label.setFont(new Font("Century Gothic", Font.PLAIN, 24));
		label.setForeground(Color.WHITE);
		label.setBounds(145, 46, 475, 30);
		label.setHorizontalAlignment(SwingConstants.RIGHT);
		add(label);
		
		JPanel line = new JPanel();
		line.setBackground(new Color(255, 204, 51));
		line.setBounds(145, 83, 475, 1);
		add(line);
		
		JPanel cpanel = new JPanel();
		cpanel.setBackground(new Color(255, 204, 51));
		cpanel.setBounds(145, 130, 124, 30);
		add(cpanel);

		JLabel cLabel = new JLabel("Source");
		cLabel.setFont(new Font("Century Gothic", Font.PLAIN, 16));
		cLabel.setForeground(Color.BLACK);
		cLabel.setBounds(0, 0, 75, 30);
		cpanel.add(cLabel);
		
		sourceField = new MyTextField("click to select directory");
		sourceField.setBounds(270, 130, 320, 30);
		sourceField.setBorder(null);
		sourceField.setFont(new Font("Century Gothic", Font.PLAIN, 16));
		sourceField.setBorder(BorderFactory.createCompoundBorder(sourceField.getBorder(),
					BorderFactory.createEmptyBorder(5, 5, 5, 5)));
		add(sourceField);
		sourceField.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				selectSourceFile();
			}
		});

		JButton button1 = new MainButton("/images/pencil.png", "/images/pencil.png");
		button1.setBounds(595, 130, 35, 33);
		add(button1);
		button1.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				selectSourceFile();
			}
		});
		
		JLabel label2 = new JLabel("The directory where your dataset is located");
		label2.setBounds(270, 158, 260, 30);
		label2.setForeground(Color.WHITE);
		label2.setFont(new Font(null, Font.ITALIC, 12));
		add(label2);
		
		JPanel cpanel2 = new JPanel();
		cpanel2.setBackground(new Color(255, 204, 51));
		cpanel2.setBounds(145, 200, 124, 30);
		add(cpanel2);

		JLabel cLabel2 = new JLabel("Destination");
		cLabel2.setFont(new Font("Century Gothic", Font.PLAIN, 16));
		cLabel2.setForeground(Color.BLACK);
		cLabel2.setBounds(0, 0, 75, 30);
		cpanel2.add(cLabel2);
		
		destinationField = new MyTextField("click to select directory");
		destinationField.setBounds(270, 200, 320, 30);
		destinationField.setBorder(null);
		destinationField.setFont(new Font("Century Gothic", Font.PLAIN, 16));
		destinationField.setBorder(BorderFactory.createCompoundBorder(destinationField.getBorder(),
					BorderFactory.createEmptyBorder(5, 5, 5, 5)));
		add(destinationField);
		destinationField.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				selectDestinationFile();
			}
		});
		
		JButton button2 = new MainButton("/images/pencil.png", "/images/pencil.png");
		button2.setBounds(595, 200, 35, 33);
		add(button2);
		button2.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				selectDestinationFile();
			}	
		});
		
		JLabel label3 = new JLabel("The directory where you want to store the resized dataset");
		label3.setBounds(270, 228, 320, 30);
		label3.setForeground(Color.WHITE);
		label3.setFont(new Font(null, Font.ITALIC, 12));
		add(label3);
		
		progressPane = new ProgressPane(); 
		progressPane.setLocation(0, 475);
		this.add(progressPane);
		
		resizeButton = new JButton("RESIZE");
		resizeButton.setBounds(305, 290, 124, 50);
		resizeButton.setFont(new Font("Century Gothic", Font.PLAIN, 18));
		add(resizeButton);
		resizeButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				sourceFile = new File(sourceField.getText().trim());
				destinationFile = new File(destinationField.getText().trim());
				if(sourceFile.exists() && destinationFile.exists()) {   
					resize(sourceFile, destinationFile);
				} else {
					new MessageDialog("Ooops. Please enter both source and destination folder.").setVisible(true);
				}
			}
		});
		
		JPanel line2 = new JPanel();
		line2.setBackground(new Color(255, 204, 51));
		line2.setBounds(145, 360, 475, 1);
		add(line2);
		
		chooser = new JFileChooser("");
		chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		
	}

	private String selectDirectory() 
	{
		String path = "";
		if (chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) 
			path += chooser.getSelectedFile().getAbsolutePath();
		return path;
	}	
	
	private void resize(final File sourceFile, final File destFile)
	{
		resizeButton.setEnabled(false);
		new Thread(new Runnable() {
			@Override
			public void run() {
				DataResizer dataResizer = new DataResizer(sourceFile, destFile);
				boolean flag = dataResizer.resize();
				if(flag) {
					new MessageDialog("You have successfully resized your dataset.").setVisible(true);
				}
				else {
					new MessageDialog("Something went wrong :(").setVisible(true);
				}
				resizeButton.setEnabled(true);
			}
		}).start();
		
	}
	

	private void selectSourceFile() 
	{
		String path = selectDirectory();
		if(new File(path).exists())
			sourceField.setText(path);
	}		
	
	private void selectDestinationFile() 
	{
		String path = selectDirectory();
		if(new File(path).exists())
			destinationField.setText(path);
	}		
	
	public ProgressPane getProgressPane()
	{
		return progressPane;
	}

}
