package views;

import image_processing.OtsuThreshold;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.ScrollPane;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;

import custom.MainButton;

import util.FileTypeFilter;
import util2.Debugger;
import util2.FileChooser;
import abcnn.Result;

public class BRemoverPane extends JPanel
{
	private static BRemoverPane instance = null;
	
	private Frame frame;
	private ImageProcessor iProcessor = new ImageProcessor();
	
	private JFileChooser chooser = new JFileChooser("D:/");
	private FileNameExtensionFilter fileFilter = new FileNameExtensionFilter("", "jpg", "jpeg");
	
	private JLabel filenameLabel, inputLabel;
	private JPanel processPanel;
	private BufferedImage input, temp, processed;
	
	public static BRemoverPane getInstance() 
	{
		if(instance == null)
			instance = new BRemoverPane();
		return instance;
	}
	
	public BRemoverPane()
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
		
		JPanel line1 = new JPanel();
		line1.setBackground(new Color(255, 204, 51));
		line1.setBounds(218, 28, 250, 1);
		add(line1);
		
		JLabel label1 = new JLabel("Color Features");
		label1.setFont(new Font("Century Gothic", Font.PLAIN, 20));
		label1.setForeground(Color.WHITE);
		label1.setBounds(218, 41, 194, 26);
		add(label1);
		
		inputLabel = new JLabel();
		inputLabel.setBounds(492, 28, 170, 170);
		inputLabel.setBackground(Color.GRAY);
		inputLabel.setOpaque(true);
		add(inputLabel);
		
		filenameLabel = new JLabel("tomato.jpg");
		filenameLabel.setBounds(480, 213, 150, 30);
		filenameLabel.setFont(new Font("Century Gothic", Font.PLAIN, 16));
		filenameLabel.setForeground(Color.WHITE);
		//filenameLabel.setBackground(Color.BLACK);
		//filenameLabel.setOpaque(true);
		add(filenameLabel);
		
		JButton tButton = new MainButton("src/images/pencil.png", "src/images/pencil.png");
		tButton.setBounds(640, 215, 35, 33);
		add(tButton);
		tButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				File inputFile = selectInput();
				if(inputFile != null) {
					showInput(inputFile);
				}
				else {
					System.out.println("wew");
				}
			}
		});
		
		JLabel label2 = new JLabel("Red:");
		label2.setFont(new Font("Century Gothic", Font.PLAIN, 16));
		label2.setForeground(Color.WHITE);
		label2.setBounds(259, 78, 60, 22);
		label2.setHorizontalAlignment(SwingConstants.RIGHT);
		add(label2);
		
		JLabel label3 = new JLabel("Green:");
		label3.setFont(new Font("Century Gothic", Font.PLAIN, 16));
		label3.setForeground(Color.WHITE);
		label3.setBounds(259, 109, 60, 22);
		label3.setHorizontalAlignment(SwingConstants.RIGHT);
		add(label3);
		
		JLabel label4 = new JLabel("R-G:");
		label4.setFont(new Font("Century Gothic", Font.PLAIN, 16));
		label4.setForeground(Color.WHITE);
		label4.setBounds(259, 136, 60, 22);
		label4.setHorizontalAlignment(SwingConstants.RIGHT);
		add(label4);
		
		JLabel label5 = new JLabel("Hue:");
		label5.setFont(new Font("Century Gothic", Font.PLAIN, 16));
		label5.setForeground(Color.WHITE);
		label5.setBounds(259, 163, 60, 22);
		label5.setHorizontalAlignment(SwingConstants.RIGHT);
		add(label5);
		
		JLabel label6 = new JLabel("a*:");
		label6.setFont(new Font("Century Gothic", Font.PLAIN, 16));
		label6.setForeground(Color.WHITE);
		label6.setBounds(259, 193, 60, 22);
		label6.setHorizontalAlignment(SwingConstants.RIGHT);
		add(label6);
		
		JLabel redLabel = new JLabel("--");
		redLabel.setFont(new Font("Century Gothic", Font.PLAIN, 16));
		redLabel.setForeground(Color.WHITE);
		redLabel.setBounds(330, 78, 60, 22);
		add(redLabel);
		
		JLabel greenLabel = new JLabel("--");
		greenLabel.setFont(new Font("Century Gothic", Font.PLAIN, 16));
		greenLabel.setForeground(Color.WHITE);
		greenLabel.setBounds(330, 109, 60, 22);
		add(greenLabel);
		
		JLabel redgreenLabel = new JLabel("--");
		redgreenLabel.setFont(new Font("Century Gothic", Font.PLAIN, 16));
		redgreenLabel.setForeground(Color.WHITE);
		redgreenLabel.setBounds(330, 136, 60, 22);
		add(redgreenLabel);
		
		JLabel hueLabel = new JLabel("--");
		hueLabel.setFont(new Font("Century Gothic", Font.PLAIN, 16));
		hueLabel.setForeground(Color.WHITE);
		hueLabel.setBounds(330, 163, 60, 22);
		add(hueLabel);
		
		JLabel aLabel = new JLabel("--");
		aLabel.setFont(new Font("Century Gothic", Font.PLAIN, 16));
		aLabel.setForeground(Color.WHITE);
		aLabel.setBounds(330, 193, 60, 22);
		add(aLabel);
		
		processPanel = new JPanel();
		processPanel.setBackground(Color.GRAY);
		
		JScrollPane scrollPane = new JScrollPane(processPanel, 
					JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		scrollPane.setBounds(15, 255, 670, 200);	
		add(scrollPane);
		
		JButton removeButton = new JButton("REMOVE");
		removeButton.setBounds(20, 200, 80, 40);
		add(removeButton);
		removeButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				process();
			}
		});
		
		chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
		chooser.setFileFilter(fileFilter);
	}
	
	private File selectInput() 
	{
		File file = null;
		chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
		if (chooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION)  
			file = chooser.getSelectedFile();
		return file;
	}	
	
	private void showInput(File inputFile) 
	{
		filenameLabel.setText(inputFile.getName());
		try {
			input = ImageIO.read(inputFile);
		} catch (IOException e1) {
			Debugger.printError("IO error in "+this.getClass().getName());
		}
		temp = iProcessor.resizeImage(input, 170, 170);
		inputLabel.setIcon(new ImageIcon(temp));
		updateUI();
	}
	
	private void process() 
	{
		if(temp != null) {
			processed = iProcessor.process(temp, 170, 170);
			//outputLabel.setIcon(new ImageIcon(processed));
			//showFeatures(processed);
			showProcess();
			
			//if( chckbxShowHistogram.isSelected() ) {
			//	histogramDialog.create(OtsuThreshold.hist, OtsuThreshold.thresh);
			//}
		}
	}
	
	private void showProcess() 
	{
		processPanel.removeAll();
		processPanel.add(new JLabel(new ImageIcon(temp)));
		processPanel.add(new JLabel(new ImageIcon(iProcessor.getBlueChannel())));
		processPanel.add(new JLabel(new ImageIcon(iProcessor.getFilteredBlue())));
		processPanel.add(new JLabel(new ImageIcon(iProcessor.getGrayscale())));
		processPanel.add(new JLabel(new ImageIcon(iProcessor.getBinaryMask())));
		processPanel.add(new JLabel(new ImageIcon(iProcessor.getSegmented())));
		processPanel.add(new JLabel(new ImageIcon(iProcessor.getCropped())));
		processPanel.add(new JLabel(new ImageIcon(processed)));
		updateUI();
	}
		
}
