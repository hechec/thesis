package views;

import imageprocessing.ImageProcessor;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;

import core.Classifier;

import utilities.FileTypeFilter;
import utilities.GlobalVariables;
import utilities.SolutionReader;
import views.optionpane.MessageDialog;

import custom.ImagePane;
import custom.MainButton;

public class SoloPane extends JPanel 
{
	private static SoloPane instance = null;
	private Frame frame;
	
	private Classifier classifier;
	private ImageProcessor iProcessor = new ImageProcessor();
	
	private double[] solution = null;
	private BufferedImage input = null;

	private JFileChooser ttbChooser, jpegChooser;
	private JLabel mLabel, fileLabel, inputLabel, classLabel;
	
	private String[] classifications = { "GREEN", "BREAKER", "TURNING", "PINK", "LIGHT RED", "RED" };
	private Color[] classColors = { new Color(44, 151, 5), new Color(176, 206, 25), new Color(227, 253, 96), 
									new Color(209, 125, 40), new Color(223, 94, 33), new Color(224, 40, 40),};
	
	/**
	 * 
	 * @return instance of this class
	 */
	public static SoloPane getInstance() 
	{
		if(instance == null)
			instance = new SoloPane();
		return instance;
	}
	
	public SoloPane()
	{
		frame = Frame.getInstance();
		setLayout(null);		
		
		FileNameExtensionFilter fileFilter = new FileNameExtensionFilter("", "jpg", "jpeg");
		jpegChooser = new JFileChooser();
		jpegChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
		jpegChooser.setFileFilter(fileFilter);
		
		FileFilter filter2 = new FileTypeFilter(".ttb", "Text files");
		ttbChooser = new JFileChooser();
		ttbChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
		ttbChooser.setFileFilter(filter2);
		
		JButton backButton = new MainButton("/images/back.png", "/images/backHover.png");
		backButton.setBounds(10, 0, 71, 51);
		this.add(backButton, 0);
		backButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				frame.setView(frame.getNextView(0));
			}
		});
		
		JLabel label = new JLabel("SOLO CLASSIFICATION");
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
		cpanel.setBounds(205, 120, 124, 30);
		add(cpanel);
		
		JLabel cLabel = new JLabel("Classifier");
		cLabel.setFont(new Font("Century Gothic", Font.PLAIN, 16));
		cLabel.setForeground(Color.BLACK);
		cLabel.setBounds(0, 0, 75, 30);
		cpanel.add(cLabel);
		
		JPanel fPanel = new JPanel();
		fPanel.setBackground(Color.LIGHT_GRAY);
		fPanel.setBounds(330, 120, 260, 30);
		fPanel.setLayout(null);
		add(fPanel);
		
		fileLabel = new JLabel("-no classifier selected-");
		fileLabel.setFont(new Font("Century Gothic", Font.PLAIN, 14));
		fileLabel.setForeground(Color.BLACK);
		fileLabel.setBounds(4, 0, 260, 30);
		fPanel.add(fileLabel);
		
		JButton cButton = new MainButton("/images/pencil.png", "/images/pencil.png");
		cButton.setBorderPainted(false);
		cButton.setBounds(595, 120, 35, 33);
		add(cButton);
		cButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				selectClassifier();
			}
		});
		
		JPanel ipanel = new JPanel();
		ipanel.setBackground(new Color(255, 204, 51));
		ipanel.setBounds(205, 165, 124, 30);
		add(ipanel);
		
		JLabel iLabel = new JLabel("Input");
		iLabel.setFont(new Font("Century Gothic", Font.PLAIN, 16));
		iLabel.setForeground(Color.BLACK);
		iLabel.setBounds(0, 0, 75, 30);
		ipanel.add(iLabel);
		
		inputLabel = new JLabel();
		inputLabel.setBounds(330, 165, 200, 200);
		inputLabel.setMinimumSize(new Dimension(150, 150));
		inputLabel.setBackground(Color.WHITE);
		inputLabel.setOpaque(true);
		add(inputLabel);
		
		JButton tButton = new MainButton("/images/pencil.png", "/images/pencil.png");
		tButton.setBounds(535, 335, 35, 33);
		add(tButton);
		tButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				File file = selectInput();
				if(file.exists())
					showInput(file);
			}
		});
		
		mLabel = new JLabel();
		mLabel.setFont(new Font("Century Gothic", Font.PLAIN, 16));
		mLabel.setBounds(205, 373, 412, 21);
		mLabel.setForeground(Color.WHITE);
		mLabel.setHorizontalAlignment(JLabel.CENTER);
		add(mLabel);
		
		mLabel.setText("tomato.jpg");
		
		JPanel line2 = new JPanel();
		line2.setBackground(new Color(255, 204, 51));
		line2.setBounds(145, 405, 475, 1);
		add(line2);
		
		JLabel rLabel = new JLabel("Classification:");
		rLabel.setFont(new Font("Century Gothic", Font.PLAIN, 16));
		rLabel.setBounds(150, 430, 118, 21);
		rLabel.setForeground(Color.WHITE);
		rLabel.setHorizontalAlignment(JLabel.CENTER);
		add(rLabel);
		
		classLabel = new JLabel("--");
		classLabel.setFont(new Font("Century Gothic", Font.BOLD, 20));
		classLabel.setBounds(275, 428, 118, 21);
		classLabel.setForeground(Color.WHITE);
		add(classLabel);
		
		final URL footerUrl = getClass().getResource("/images/footer.png");
		JPanel footerPane = new ImagePane(footerUrl);
		footerPane.setBackground(Color.DARK_GRAY);
		footerPane.setBounds(0, 475, 700, 50);
		this.add(footerPane);
		footerPane.setLayout(null);
		
		JButton testButton = new JButton("CLASSIFY");
		testButton.setBounds(520, 420, 100, 40);
		testButton.setFont(new Font("Century Gothic", Font.PLAIN, 15));
		add(testButton);
		testButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(solution != null && input != null) {
					test();
				} else {
					new MessageDialog("Ooops. Please select a classifier and input image.").setVisible(true);
				}
			}
		});
		
	}
	
	private void selectClassifier()
	{
		
		if (ttbChooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) { 
			fileLabel.setText(ttbChooser.getSelectedFile().getName()+"");
			solution = SolutionReader.read(ttbChooser.getSelectedFile());
		}
	}

	private File selectInput() 
	{
		String path = "";
		if (jpegChooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION)  
			path = jpegChooser.getSelectedFile()+"";
		return new File(path);
	}
	
	private void showInput(File inputFile) {
		mLabel.setText(inputFile.getName());
		BufferedImage temp = null;
		try {
			input = ImageIO.read(inputFile);
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		temp = iProcessor.resizeImage(input, 200, 200);
		inputLabel.setIcon(new ImageIcon(temp));
		updateUI();
	}
	
	private void test() {
		classLabel.setText("...");
		GlobalVariables.setMode(GlobalVariables.STANDARD_RUN);
		new Thread(new Runnable() {
			@Override
			public void run() {
				classifier = new Classifier(solution);
				BufferedImage temp = input;
				temp = iProcessor.process(input, 200, 200);
				double[] features = iProcessor.getFeatures(temp);
				int classIndex = classifier.classify(features);
				classLabel.setForeground(classColors[classIndex]);
				classLabel.setText(classifications[classIndex]+"");
			}
		}).start();
	}
	
}
