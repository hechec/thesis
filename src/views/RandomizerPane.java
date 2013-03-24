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
import java.net.URL;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import views.dialog.ResultLocationChooser;
import views.optionpane.MessageDialog;
import custom.ImagePane;
import custom.MainButton;
import custom.MyTextField;
import dataset.DataRandomizer;
import dataset.DataWriter;

public class RandomizerPane extends JPanel 
{
	private static RandomizerPane instance = null;
	private Frame frame;
	
	private JTextField directoryField, testingField, trainingField;
	private JFileChooser chooser;
	
	private File dataFile = null;
	
	public static RandomizerPane  getInstance() 
	{
		if(instance == null)
			instance = new RandomizerPane();
		return instance;
	}
	
	public RandomizerPane()
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
		
		JLabel label = new JLabel("DATASET RANDOMIZER");
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
		cpanel.setBounds(205, 130, 124, 30);
		add(cpanel);
		
		JLabel cLabel = new JLabel("Data Set");
		cLabel.setFont(new Font("Century Gothic", Font.PLAIN, 16));
		cLabel.setForeground(Color.BLACK);
		cLabel.setBounds(0, 0, 75, 30);
		cpanel.add(cLabel);
		
		directoryField = new MyTextField("click to select directory");
		directoryField.setBounds(330, 130, 260, 30);
		directoryField.setBorder(null);
		directoryField.setFont(new Font("Century Gothic", Font.PLAIN, 16));
		directoryField.setBorder(BorderFactory.createCompoundBorder( directoryField.getBorder(),
					BorderFactory.createEmptyBorder(5, 5, 5, 5)));
		add(directoryField);
		directoryField.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				selectDirectory();
			}
		});

		
		JButton tButton = new MainButton("/images/pencil.png", "/images/pencil.png");
		tButton.setBounds(595, 130, 35, 33);
		add(tButton);
		tButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				selectDirectory();
			}	
		});
		
		JPanel panel = new JPanel();
		panel.setBackground(new Color(255, 204, 51));
		panel.setBounds(205, 226, 124, 30);
		add(panel);
		
		JLabel label1 = new JLabel("Training Set");
		label1.setFont(new Font("Century Gothic", Font.PLAIN, 16));
		label1.setForeground(Color.BLACK);
		label1.setBounds(0, 0, 75, 30);
		panel.add(label1);
		
		trainingField = new MyTextField("Enter train data filename");
		trainingField.setBounds(330, 226, 240, 30);
		trainingField.setBorder(null);
		trainingField.setFont(new Font("Century Gothic", Font.PLAIN, 16));
		trainingField.setBorder(BorderFactory.createCompoundBorder( trainingField.getBorder(),
					BorderFactory.createEmptyBorder(5, 5, 5, 5)));
		add(trainingField);
		
		JLabel label2 = new JLabel(".data");
		label2.setFont(new Font("Century Gothic", Font.PLAIN, 16));
		label2.setForeground(Color.WHITE);
		label2.setBounds(572, 232, 50, 21);
		add(label2);
		
		JPanel panel2 = new JPanel();
		panel2.setBackground(new Color(255, 204, 51));
		panel2.setBounds(205, 274, 124, 30);
		add(panel2);
		
		JLabel label3 = new JLabel("Testing Set");
		label3.setFont(new Font("Century Gothic", Font.PLAIN, 16));
		label3.setForeground(Color.BLACK);
		label3.setBounds(0, 0, 75, 30);
		panel2.add(label3);
		
		testingField = new MyTextField("Enter test data filename");
		testingField.setBounds(330, 274, 240, 30);
		testingField.setBorder(null);
		testingField.setFont(new Font("Century Gothic", Font.PLAIN, 16));
		testingField.setBorder(BorderFactory.createCompoundBorder( testingField.getBorder(),
					BorderFactory.createEmptyBorder(5, 5, 5, 5)));
		add(testingField);
		
		JLabel label4 = new JLabel(".data");
		label4.setFont(new Font("Century Gothic", Font.PLAIN, 16));
		label4.setForeground(Color.WHITE);
		label4.setBounds(572, 280, 50, 21);
		add(label4);
		
		JPanel line2 = new JPanel();
		line2.setBackground(new Color(255, 204, 51));
		line2.setBounds(145, 400, 475, 1);
		add(line2);
		
		
		JButton randomize = new JButton("RANDOMIZE");
		randomize.setBounds(315, 330, 124, 50);
		randomize.setFont(new Font("Century Gothic", Font.PLAIN, 15));
		add(randomize);
		randomize.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				dataFile = new File(directoryField.getText());
				if(!dataFile.exists())
					new MessageDialog("Ooops. Please enter the data set.").setVisible(true);
				else if(trainingField.getText().isEmpty() || testingField.getText().isEmpty())
					new MessageDialog("Ooops. Please enter training and test data filenames.").setVisible(true);
				else
					new ResultLocationChooser(ResultLocationChooser.RANDOMIZE).setVisible(true);
				
			}
		});
		
		final URL footerUrl = getClass().getResource("/images/footer.png");
		JPanel footerPane = new ImagePane(footerUrl);
		
		footerPane.setBackground(Color.DARK_GRAY);
		footerPane.setBounds(0, 475, 700, 50);
		this.add(footerPane);
		footerPane.setLayout(null);
		
		chooser = new JFileChooser();
		chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
	}	
	
	private void selectDirectory()
	{
		String path = "";
		if (chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION)  
			path = chooser.getSelectedFile().getAbsolutePath();
		if(new File(path).exists())
			directoryField.setText(path);		
	}
	
	public void randomize(File destfile) 
	{
		DataRandomizer dataRandomizer = new DataRandomizer(dataFile);
		boolean success = dataRandomizer.randomize();
		if(success) {
			DataWriter dataWriter = new DataWriter(new File(destfile.getAbsolutePath()+"/"+trainingField.getText()+".data"));
			boolean b1 = dataWriter.write(dataRandomizer.getTrainSet());
			DataWriter dataWriter2 = new DataWriter(new File(destfile.getAbsolutePath()+"/"+testingField.getText()+".data"));
			boolean b2 = dataWriter2.write(dataRandomizer.getTestSet());
			if(b1 & b2) {
				new MessageDialog("You have successfully created a random dataset.").setVisible(true);
			}
			else
				new MessageDialog("Error. Something went wrong while randomizing.").setVisible(true);
		}
	}
	
}
