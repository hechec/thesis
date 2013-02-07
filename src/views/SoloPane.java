package views;

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

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;

import utilities.FileChooser;
import utilities.FileTypeFilter;
import utilities.SolutionReader;

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
		jpegChooser = new JFileChooser("D:/kamatisan/");
		jpegChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
		jpegChooser.setFileFilter(fileFilter);
		
		FileFilter filter2 = new FileTypeFilter(".ttb", "Text files");
		ttbChooser = new JFileChooser("D:/kamatisan/");
		ttbChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
		ttbChooser.setFileFilter(filter2);
		
		JButton backButton = new MainButton("src/images/back.png", "src/images/backHover.png");
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
		label.setBounds(145, 46, 300, 30);
		add(label);
		
		JPanel line = new JPanel();
		line.setBackground(new Color(255, 204, 51));
		line.setBounds(145, 83, 475, 1);
		add(line);
		
		JLabel dLabel = new JLabel("Data");
		dLabel.setFont(new Font("Century Gothic", Font.PLAIN, 20));
		dLabel.setForeground(Color.WHITE);
		dLabel.setBounds(388, 91, 54, 30);
		add(dLabel);
		
		JPanel cpanel = new JPanel();
		cpanel.setBackground(new Color(255, 204, 51));
		cpanel.setBounds(205, 135, 124, 30);
		add(cpanel);
		
		JLabel cLabel = new JLabel("Classifier");
		cLabel.setFont(new Font("Century Gothic", Font.PLAIN, 16));
		cLabel.setForeground(Color.BLACK);
		cLabel.setBounds(0, 0, 75, 30);
		cpanel.add(cLabel);
		
		JPanel fPanel = new JPanel();
		fPanel.setBackground(Color.LIGHT_GRAY);
		fPanel.setBounds(330, 135, 260, 30);
		fPanel.setLayout(null);
		add(fPanel);
		
		fileLabel = new JLabel("-no classifier selected-");
		fileLabel.setFont(new Font("Century Gothic", Font.PLAIN, 14));
		fileLabel.setForeground(Color.BLACK);
		fileLabel.setBounds(4, 0, 260, 30);
		fPanel.add(fileLabel);
		
		JButton cButton = new MainButton("src/images/pencil.png", "src/images/pencil.png");
		cButton.setBorderPainted(false);
		cButton.setBounds(595, 135, 35, 33);
		add(cButton);
		cButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				selectClassifier();
			}
		});
		
		JPanel ipanel = new JPanel();
		ipanel.setBackground(new Color(255, 204, 51));
		ipanel.setBounds(205, 181, 124, 30);
		add(ipanel);
		
		JLabel iLabel = new JLabel("Input");
		iLabel.setFont(new Font("Century Gothic", Font.PLAIN, 16));
		iLabel.setForeground(Color.BLACK);
		iLabel.setBounds(0, 0, 75, 30);
		ipanel.add(iLabel);
		
		inputLabel = new JLabel();
		inputLabel.setBounds(330, 181, 200, 200);
		inputLabel.setMinimumSize(new Dimension(150, 150));
		inputLabel.setBackground(Color.WHITE);
		inputLabel.setOpaque(true);
		add(inputLabel);
		
		JButton tButton = new MainButton("src/images/pencil.png", "src/images/pencil.png");
		tButton.setBounds(535, 350, 35, 33);
		add(tButton);
		tButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String path = selectInput();
				if(!path.equals("--"))
					showInput(path);
			}
		});
		
		mLabel = new JLabel();
		mLabel.setFont(new Font("Century Gothic", Font.PLAIN, 16));
		mLabel.setBounds(205, 388, 412, 21);
		mLabel.setForeground(Color.WHITE);
		mLabel.setHorizontalAlignment(JLabel.CENTER);
		add(mLabel);
		
		mLabel.setText("D:/kamatisan/testing/1/tomato.jpg");
		
		JPanel line2 = new JPanel();
		line2.setBackground(new Color(255, 204, 51));
		line2.setBounds(145, 421, 475, 1);
		add(line2);
		
		JLabel rLabel = new JLabel("Classification:");
		rLabel.setFont(new Font("Century Gothic", Font.PLAIN, 16));
		rLabel.setBounds(280, 438, 118, 21);
		rLabel.setForeground(Color.WHITE);
		rLabel.setHorizontalAlignment(JLabel.CENTER);
		add(rLabel);
		
		classLabel = new JLabel("--");
		classLabel.setFont(new Font("Century Gothic", Font.BOLD, 20));
		classLabel.setBounds(405, 436, 118, 21);
		classLabel.setForeground(Color.WHITE);
		add(classLabel);
		
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
		footerPane.setBounds(0, 475, 700, 50);
		this.add(footerPane);
		footerPane.setLayout(null);
		
		JButton testButton = new JButton("TEST");
		testButton.setBounds(20, 130, 80, 30);
		add(testButton);
		testButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(solution != null && input != null) {
					test();
				}
				else {
					System.out.println("Oooops!");
				}
			}
		});
		
	}
	
	private void selectClassifier()
	{
		
		if (ttbChooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) { 
			fileLabel.setText(ttbChooser.getSelectedFile()+"");
			solution = SolutionReader.read(ttbChooser.getSelectedFile());
		}
	}

	private String selectInput() 
	{
		String path = "--";
		if (jpegChooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION)  
			path = jpegChooser.getSelectedFile()+"";
		return path;
	}
	
	private void showInput(String path) {
		mLabel.setText(path);
		BufferedImage temp = null;
		try {
			input = ImageIO.read(new File(path));
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		temp = iProcessor.resizeImage(input, 200, 200);
		inputLabel.setIcon(new ImageIcon(temp));
		updateUI();
	}
	
	private void test() {
		classLabel.setText("...");
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
