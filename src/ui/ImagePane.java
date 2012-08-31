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


public class ImagePane extends JPanel {
	
	private JPanel inputPane;
	private JLabel inputLabel;
	private JLabel extractedLabel;
	
	private JButton extractButton;
	
	private BufferedImage original;
	
	private ImageHandler iHandler = new ImageHandler();
	private GuiController guiController;
	
	
	public ImagePane() {
		
		setBounds(0, 0, 478, 400);
		setLayout(null);
		
		inputPane = new JPanel();
		inputPane.setBorder(new TitledBorder(new LineBorder(new Color(130, 135, 144), 1, true), "Input", TitledBorder.CENTER, TitledBorder.TOP, null, null));
		inputPane.setBounds(105, 11, 286, 234);
		add(inputPane);
		
		inputLabel = new JLabel("");
		inputPane.add(inputLabel);
		
		extractButton = new JButton("Extract");
		extractButton.setEnabled(false);
		extractButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				segment();
			}
		});
		extractButton.setBounds(105, 256, 89, 23);
		add(extractButton);
		
		JButton btnNewButton_1 = new JButton("Reset");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				reset();
			}
		});
		btnNewButton_1.setBounds(302, 256, 89, 23);
		add(btnNewButton_1);
		
		JPanel panel = new JPanel();
		panel.setBorder(new TitledBorder(new LineBorder(new Color(130, 135, 144)), "Extracted", TitledBorder.CENTER, TitledBorder.TOP, null, null));
		panel.setBounds(196, 292, 106, 97);
		add(panel);
		
		extractedLabel = new JLabel("");
		extractedLabel.setPreferredSize(new Dimension(64, 64));
		panel.add(extractedLabel);
		
	}

	public void setController(GuiController gc) {
		this.guiController = gc;
	}	
	
	public void showInput(BufferedImage original) {
		this.original = iHandler.resize( original, (int) (200/((float)original.getHeight())*original.getWidth()), 200 );
		inputLabel.setIcon(new ImageIcon(this.original));	
		extractedLabel.setIcon(null);
		extractButton.setEnabled(true);
	}
	
	private void segment() {
		try{
			//guiController.setMessage("preparing...");
			BufferedImage extracted = iHandler.extract(original);
			extractedLabel.setIcon(new ImageIcon(extracted));
			guiController.setMessage("done extracting.");
			extractFeatures(extracted);
		}
		catch(NullPointerException e) {
			guiController.setMessage("error. no image selected.");
		}
	}
	
	private void extractFeatures(BufferedImage image) {
		int meanRed = iHandler.computeMeanRed(image);
		int meanGreen = iHandler.computerMeanGreen(image);
		int meanRG = iHandler.computeMeanRG(image);
		int meanHue = iHandler.computeMeanHue(image);
		int meanA = iHandler.computeMeanA(image);
		guiController.setRedField(meanRed);
		guiController.setGreenField(meanGreen);
		guiController.setRGField(meanRG);
		guiController.setHueField(meanHue);
		guiController.setAField(meanA);
	}
	
	private void reset() {
		inputLabel.setIcon(null);
		extractedLabel.setIcon(null);
		updateUI();
		guiController.setMessage("reset.");
		original = null;
		extractButton.setEnabled(false);
	}

	
}