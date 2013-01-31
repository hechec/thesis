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
import javax.swing.filechooser.FileFilter;
import javax.swing.plaf.metal.MetalIconFactory.FileIcon16;

import util.FileTypeFilter;
import util2.ResultWriter;

import custom.MainButton;
import custom.MyTextField;
import dataset.DataRandomizer;
import dataset.DataWriter;

public class RandomizerPane extends JPanel 
{
	private static RandomizerPane instance = null;
	private Frame frame;
	
	private JTextField directoryField;
	
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
	
		JButton backButton = new MainButton("src/images/back.png", "src/images/backHover.png");
		backButton.setBounds(10, 0, 71, 51);
		this.add(backButton, 0);
		backButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				frame.setView(frame.getNextView(0));
			}
		});
		
		JLabel label = new JLabel("Data Set Randomizer");
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
		
		JLabel cLabel = new JLabel("Data Set");
		cLabel.setFont(new Font("Century Gothic", Font.PLAIN, 16));
		cLabel.setForeground(Color.BLACK);
		cLabel.setBounds(0, 0, 75, 30);
		cpanel.add(cLabel);
		
		directoryField = new MyTextField("click to selected directory");
		directoryField.setBounds(330, 117, 260, 30);
		directoryField.setBorder(null);
		directoryField.setFont(new Font("Century Gothic", Font.PLAIN, 16));
		directoryField.setBorder(BorderFactory.createCompoundBorder( directoryField.getBorder(),
					BorderFactory.createEmptyBorder(5, 5, 5, 5)));
		add(directoryField);
		
		JButton tButton = new MainButton("src/images/pencil.png", "src/images/pencil.png");
		tButton.setBounds(595, 117, 35, 33);
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
		
		final JTextField trainingField = new MyTextField("click to selected directory");
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
		
		final JTextField testingField = new MyTextField("click to selected directory");
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
		
		JButton randomize = new JButton("Randomize");
		randomize.setBounds(20, 200, 90, 30);
		add(randomize);
		randomize.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(!directoryField.getText().isEmpty() && !trainingField.getText().isEmpty() && !testingField.getText().isEmpty()) {
					File dataFile = new File(directoryField.getText());
					DataRandomizer dataRandomizer = new DataRandomizer(dataFile);
					boolean success = dataRandomizer.randomize();
					if(success) {
						DataWriter dataWriter = new DataWriter(trainingField.getText());
						boolean b1 = dataWriter.write(dataRandomizer.getTrainSet());
						DataWriter dataWriter2 = new DataWriter(testingField.getText());
						boolean b2 = dataWriter2.write(dataRandomizer.getTestSet());
						if(b1 & b2) {
							JOptionPane.showMessageDialog(null, "Success :)");
						}
						else
							JOptionPane.showMessageDialog(null, "Fail :(");
					}
				}
				else
					JOptionPane.showMessageDialog(null, "Oopppps! :p");
			}
		});
		
		JPanel footerPane = new JPanel() {
			@Override
		    public void paintComponent(Graphics g) {
				 Graphics2D g2 = (Graphics2D) g;
				Image image = null;
				try {                
					image = ImageIO.read(new File("src/images/footer.png"));
		        } catch (IOException ex) {
		        	System.out.println("Check footer image.");
		        }
		        g2.drawImage(image, 0, 0, null);          
		    }
		};
		footerPane.setBackground(Color.DARK_GRAY);
		footerPane.setBounds(0, 425, 700, 50);
		this.add(footerPane);
		footerPane.setLayout(null);
		
	}	
	
	private void selectDirectory()
	{
		JFileChooser chooser = new JFileChooser("D:/");
		chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		if (chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) { 
			directoryField.setText(chooser.getSelectedFile().getAbsolutePath());
		}
	}
	
}
