package ui;

import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.*;
import javax.imageio.*;
import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;

import imageProcessing.ImageProcessor;
import abcnn.Classifier;

public class SoloPane extends JPanel {
	
	private JLabel classLabel, inputLabel;
	
	private JFileChooser chooser;
	private FileFilter fileFilter;
	
	private String[] classes = {"Green","Breaker","Turning","Pink","Light Red","Red"};
	private boolean hasInput = false;
	
	private ABCNNTab abcnnPane;
	private Classifier classifier;
	
	private BufferedImage forTesting;
	ImageProcessor iProcessor = ImageProcessor.getInstance();
	
	public SoloPane(final ABCNNTab abcnnPane, JFileChooser chooser, Classifier classifier) {
		setBounds(15, 0, 300, 442);
		setLayout(null);
		
		JPanel resultsPane = new JPanel();
		resultsPane.setBounds(0, 310, 300, 110);
		add(resultsPane);
		resultsPane.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "RESULT", TitledBorder.CENTER, TitledBorder.TOP, null, null));
		resultsPane.setLayout(null);
		
		JPanel inputPanel = new JPanel();
		inputPanel.setBounds(0, 39, 300, 210);
		add(inputPanel);
		inputPanel.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "INPUT IMAGE", TitledBorder.CENTER, TitledBorder.TOP, null, null));
		
		inputLabel = new JLabel();
		inputPanel.add(inputLabel);
		
		JButton btnBrowse = new JButton("CUSTOM");
		btnBrowse.setBounds(0, 260, 89, 27);
		add(btnBrowse);
		
		JButton classifyButton = new JButton("CLASSIFY");
		classifyButton.setBounds(211, 260, 89, 27);
		add(classifyButton);
		classifyButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				classify();
			}
		});
		btnBrowse.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				browseInput();
			}
		});
		
		JLabel lblClassification = new JLabel("CLASSIFICATION:");
		lblClassification.setHorizontalAlignment(SwingConstants.RIGHT);
		lblClassification.setBounds(21, 48, 124, 22);
		resultsPane.add(lblClassification);
		
		classLabel = new JLabel("--");
		classLabel.setBounds(166, 48, 64, 22);
		resultsPane.add(classLabel);
		
		this.abcnnPane = abcnnPane;
		this.classifier = classifier;
		this.chooser = chooser;
		fileFilter = new FileNameExtensionFilter("JPEG file", "jpg", "jpeg", "png", "gif");
	}
	
	public void browseInput() {
		chooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
		chooser.setFileFilter(fileFilter);
		chooser.setAcceptAllFileFilterUsed(false);
		if(chooser.showOpenDialog(abcnnPane) == JFileChooser.APPROVE_OPTION) {
			try {
				forTesting = ImageIO.read(new File(chooser.getSelectedFile()+""));
				forTesting = iProcessor.resizeImage(forTesting, ImageProcessor.WIDTH, ImageProcessor.HEIGHT);//iHandler.resize(forTesting, (int) (180/((float)forTesting.getHeight())*forTesting.getWidth()), 180);
				inputLabel.setIcon(new ImageIcon(forTesting));
				hasInput = true;
			} catch (IIOException e) {
				JOptionPane.showMessageDialog(abcnnPane, "Cannot read file.", "Error Message", JOptionPane.WARNING_MESSAGE);
			} catch (NullPointerException e) {
				JOptionPane.showMessageDialog(abcnnPane, "Cannot read file.", "Error Message", JOptionPane.WARNING_MESSAGE);
			} catch (IOException e) {
				e.printStackTrace();
		    }
		}
	}
	
	private void classify() {
		if(!classifier.isTrained()) {
			JOptionPane.showMessageDialog(abcnnPane, "Please train the network or load training result.", "Error Message", JOptionPane.WARNING_MESSAGE);
			return;
		}		
		if(!hasInput) {
			JOptionPane.showMessageDialog(abcnnPane, "Please input a tomato image.", "Error Message", JOptionPane.WARNING_MESSAGE);
			return;
		}
		
		BufferedImage temp = forTesting;
		temp = iProcessor.process(temp);//iHandler.extract(temp);
		double[] features = iProcessor.getFeatures(temp);//iHandler.getFeatures(temp);
		
		int classIndex = classifier.classify(features);//abcnnPane.classify(features);
		setResult(classes[classIndex]);
		
	}
	
	public void setResult(String result) {
		classLabel.setText(result);	
	}

	public void reset() {
		inputLabel.setIcon(null);
		classLabel.setText("--");
		hasInput = false;
	}

}
