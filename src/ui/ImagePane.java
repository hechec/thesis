package ui;


import imageProcessing.ImageProcessor;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.TitledBorder;

public class ImagePane extends JPanel {
	
	private JPanel inputPane;
	private JLabel inputLabel;
	private JLabel extractedLabel;
	
	private JButton extractButton;
	
	private BufferedImage inputImage;
	
	private ImageProcessor iProcessor = ImageProcessor.getInstance();
	private GuiController guiController;
	
	
	public ImagePane() {
		
		setBounds(0, 0, 700, 231);
		setLayout(null);
		
		inputPane = new JPanel();
		inputPane.setBorder(new TitledBorder(null, "Input", TitledBorder.CENTER, TitledBorder.TOP, null, null));
		inputPane.setBounds(22, 11, 262, 209);
		add(inputPane);
		
		inputLabel = new JLabel("");
		inputPane.add(inputLabel);
		
		extractButton = new JButton("PROCESS");
		extractButton.setEnabled(false);
		extractButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				segment();
				//if( AppFrame.showStepbyProcess )
					//iHandler.showStepByStep();
			}
		});
		extractButton.setBounds(330, 106, 113, 40);
		add(extractButton);
		
		JButton btnNewButton_1 = new JButton("Reset");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				reset();
			}
		});
		btnNewButton_1.setBounds(330, 168, 113, 40);
		add(btnNewButton_1);
		
		JPanel panel = new JPanel();
		panel.setBorder(new TitledBorder(null, "Extracted", TitledBorder.CENTER, TitledBorder.TOP, null, null));
		panel.setBounds(527, 11, 106, 97);
		add(panel);
		
		extractedLabel = new JLabel("");
		extractedLabel.setPreferredSize(new Dimension(64, 64));
		panel.add(extractedLabel);
		
		JButton btnCustom = new JButton("CUSTOM");
		btnCustom.setBounds(330, 41, 113, 40);
		add(btnCustom);
		
	}

	public void setController(GuiController gc) {
		this.guiController = gc;
	}	
	
	public void showInput(BufferedImage original) {
		this.inputImage =  iProcessor.resizeImage(original, ImageProcessor.WIDTH, ImageProcessor.HEIGHT); //iHandler.resize( original, (int) (180/((float)original.getHeight())*original.getWidth()), 180 );
		inputLabel.setIcon(new ImageIcon(this.inputImage));	
		extractedLabel.setIcon(null);
		extractButton.setEnabled(true);
	}
	
	private void segment() {
		BufferedImage extracted = iProcessor.process(inputImage);//iHandler.extract(original);
		extractedLabel.setIcon(new ImageIcon(extracted));
		guiController.setMessage("done extracting.");
		extractFeatures(extracted);
	}
	
	private void extractFeatures(BufferedImage image) {
		double meanRed = iProcessor.computeMeanRed(image);//iHandler.computeMeanRed(image);
		double meanGreen = iProcessor.computeMeanGreen(image);
		double meanRG = iProcessor.computeMeanRG(image);
		double meanHue = iProcessor.computeMeanHue(image);
		double meanA = iProcessor.computeMeanA(image);
		guiController.setRedField(meanRed);
		guiController.setGreenField(meanGreen);
		guiController.setRGField(meanRG);
		guiController.setHueField(meanHue);
		guiController.setAField(meanA);
	}
	
	private void reset() {
		inputLabel.setIcon(null);
		extractedLabel.setIcon(null);
		//updateUI();
		
		guiController.resetExtraction();
		inputImage = null;
		extractButton.setEnabled(false);
	}
}