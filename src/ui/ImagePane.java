package ui;


import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.TitledBorder;
import javax.swing.UIManager;
import javax.swing.border.LineBorder;

import utilities.ImageHandler;


public class ImagePane extends JPanel {
	
	private JPanel inputPane;
	private JLabel inputLabel;
	private JLabel extractedLabel;
	
	private JButton extractButton;
	
	private BufferedImage original;
	
	private ImageHandler iHandler = new ImageHandler();
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
				if( AppFrame.showStepbyProcess )
					iHandler.showStepByStep();
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
		this.original = iHandler.resize( original, (int) (180/((float)original.getHeight())*original.getWidth()), 180 );
		inputLabel.setIcon(new ImageIcon(this.original));	
		extractedLabel.setIcon(null);
		extractButton.setEnabled(true);
	}
	
	private void segment() {
		BufferedImage extracted = iHandler.extract(original);
		extractedLabel.setIcon(new ImageIcon(extracted));
		guiController.setMessage("done extracting.");
		extractFeatures(extracted);
	}
	
	private void extractFeatures(BufferedImage image) {
		double meanRed = iHandler.computeMeanRed(image);
		double meanGreen = iHandler.computeMeanGreen(image);
		double meanRG = iHandler.computeMeanRG(image);
		double meanHue = iHandler.computeMeanHue(image);
		double meanA = iHandler.computeMeanA(image);
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
		original = null;
		extractButton.setEnabled(false);
	}
}