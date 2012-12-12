package ui;

import javax.swing.*;
import java.awt.event.*;
import javax.swing.border.TitledBorder;
import javax.swing.filechooser.*;
import java.awt.Dimension;
import java.io.IOException;
import javax.imageio.ImageIO;

import ui.dialogs.Histogram;

import java.awt.image.BufferedImage;

import image_processing.BilinearInterpolation;
import image_processing.FeatureExtractor;
import image_processing.ImageProcessor;
import image_processing.OtsuThreshold;

public class BGRemoverTab extends JPanel 
{
	private JFileChooser fc;
	
	private JPanel processPanel;
	private JPanel featuresPanel = null;
	private JLabel inputLabel;
	private JLabel outputLabel;
	private JLabel meanRLabel, meanGLabel, meanRGLabel, meanHLabel, meanALabel;
	private JCheckBox chckbxShowHistogram;
	
	private BufferedImage inputImage;
	private ImageProcessor iProcessor = ImageProcessor.getInstance();
	
	private Histogram histogramDialog;
	
	public BGRemoverTab(AppFrame appFrame) {
		this.setLayout(null);
		this.setSize(740, 600);
		
		JPanel inputPane = new JPanel();
		inputPane.setBorder(new TitledBorder(null, "Input", TitledBorder.CENTER, TitledBorder.TOP, null, null));
		inputPane.setBounds(44, 41, 262, 241);
		add(inputPane);
		
		inputLabel = new JLabel();
		inputPane.add(inputLabel);
		
		JButton btnNewButton = new JButton("CUSTOM");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				selectImage();
			}
		});
		btnNewButton.setBounds(369, 67, 114, 37);
		add(btnNewButton);
		
		JButton btnProcess = new JButton("PROCESS");
		btnProcess.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				process();
			}
		});
		btnProcess.setBounds(369, 128, 114, 37);
		add(btnProcess);
		
		JButton btnReset = new JButton("RESET");
		btnReset.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				reset();
			}
		});
		btnReset.setBounds(369, 188, 114, 37);
		add(btnReset);
		
		JPanel panel = new JPanel();
		panel.setBorder(new TitledBorder(null, "Extracted", TitledBorder.CENTER, TitledBorder.TOP, null, null));
		panel.setBounds(559, 41, 106, 97);
		add(panel);
		
		outputLabel = new JLabel();
		outputLabel.setPreferredSize(new Dimension(64, 64));
		panel.add(outputLabel);

		processPanel = new JPanel();
		
		JScrollPane scrollPane = new JScrollPane(processPanel);
		scrollPane.setBounds(44, 317, 639, 229);
		scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		add(scrollPane);
		
		featuresPanel = new JPanel();
		/*{
			public void paintComponent(Graphics g) {
				 Graphics2D g2d = (Graphics2D) g;
				 g2d.setRenderingHint(
			            RenderingHints.KEY_ANTIALIASING,
			            RenderingHints.VALUE_ANTIALIAS_ON);
			        g2d.setComposite(AlphaComposite.getInstance(
			            AlphaComposite.SRC_OVER, 0.1f));
			        g2d.setColor(Color.GRAY);
			        g2d.fillRect(0, 0, 199, 253);
			}
		};*/
		featuresPanel.setBounds(547, 149, 163, 157);
		add(featuresPanel);
		featuresPanel.setLayout(null);
		
		JLabel lblMeanR = new JLabel("Mean R:");
		lblMeanR.setHorizontalAlignment(SwingConstants.RIGHT);
		lblMeanR.setBounds(10, 5, 66, 30);
		featuresPanel.add(lblMeanR);
		
		JLabel lblMeanG = new JLabel("Mean G:");
		lblMeanG.setHorizontalAlignment(SwingConstants.RIGHT);
		lblMeanG.setBounds(10, 34, 66, 30);
		featuresPanel.add(lblMeanG);
		
		JLabel lblMeanRg = new JLabel("Mean R-G:");
		lblMeanRg.setHorizontalAlignment(SwingConstants.RIGHT);
		lblMeanRg.setBounds(10, 61, 66, 30);
		featuresPanel.add(lblMeanRg);
		
		JLabel lblMeanH = new JLabel("Mean H:");
		lblMeanH.setHorizontalAlignment(SwingConstants.RIGHT);
		lblMeanH.setBounds(10, 90, 66, 30);
		featuresPanel.add(lblMeanH);
		
		JLabel lblMeanA = new JLabel("Mean a*:");
		lblMeanA.setHorizontalAlignment(SwingConstants.RIGHT);
		lblMeanA.setBounds(10, 115, 66, 30);
		featuresPanel.add(lblMeanA);
		
		meanRLabel = new JLabel("0.0");
		meanRLabel.setHorizontalAlignment(SwingConstants.LEFT);
		meanRLabel.setBounds(87, 5, 66, 30);
		featuresPanel.add(meanRLabel);
		
		meanGLabel = new JLabel("0.0");
		meanGLabel.setHorizontalAlignment(SwingConstants.LEFT);
		meanGLabel.setBounds(86, 34, 66, 30);
		featuresPanel.add(meanGLabel);
		
		meanRGLabel = new JLabel("0.0");
		meanRGLabel.setHorizontalAlignment(SwingConstants.LEFT);
		meanRGLabel.setBounds(86, 61, 66, 30);
		featuresPanel.add(meanRGLabel);
		
		meanHLabel = new JLabel("0.0");
		meanHLabel.setHorizontalAlignment(SwingConstants.LEFT);
		meanHLabel.setBounds(86, 90, 66, 30);
		featuresPanel.add(meanHLabel);
		
		meanALabel = new JLabel("0.0");
		meanALabel.setHorizontalAlignment(SwingConstants.LEFT);
		meanALabel.setBounds(86, 115, 66, 30);
		featuresPanel.add(meanALabel);
		
		chckbxShowHistogram = new JCheckBox(" Show Histogram");
		chckbxShowHistogram.setBounds(39, 553, 163, 23);
		add(chckbxShowHistogram);
		
		FileFilter filter = new FileNameExtensionFilter("JPEG file", "jpg", "jpeg", "png", "gif");
		fc = new JFileChooser();
		fc.setFileFilter(filter);
		
		histogramDialog = new Histogram();
		
	}
	
	private void selectImage() {
		
		 if(fc.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
			 try {
				 inputImage = ImageIO.read(fc.getSelectedFile());
				 
				 //inputImage = iProcessor.resizeImage( inputImage, ImageProcessor.WIDTH, ImageProcessor.HEIGHT );
				 inputImage = BilinearInterpolation.resize(inputImage, 200, 200);
				 
				 //inputImage = iProcessor.resizeImage( inputImage, (int) (180/((float)inputImage.getHeight())*inputImage.getWidth()), 180 );
				 
				 inputLabel.setIcon(new ImageIcon(inputImage));
			} catch (IOException e) {
				e.printStackTrace();
			}
		 }
		
	}
	
	BufferedImage processed;
	private void process() {
		if(inputImage != null) {
			processed = iProcessor.process(inputImage);
			outputLabel.setIcon(new ImageIcon(processed));
			showFeatures(processed);
			showProcess();
			
			if( chckbxShowHistogram.isSelected() ) {
				histogramDialog.create(OtsuThreshold.hist, OtsuThreshold.thresh);
			}
		}
	}
	
	private void showProcess() {
		processPanel.removeAll();
		processPanel.add(new JLabel(new ImageIcon(inputImage)));
		processPanel.add(new JLabel(new ImageIcon(iProcessor.getBlueChannel())));
		processPanel.add(new JLabel(new ImageIcon(iProcessor.getFilteredBlue())));
		processPanel.add(new JLabel(new ImageIcon(iProcessor.getGrayscale())));
		processPanel.add(new JLabel(new ImageIcon(iProcessor.getBinaryMask())));
		processPanel.add(new JLabel(new ImageIcon(iProcessor.getSegmented())));
		processPanel.add(new JLabel(new ImageIcon(iProcessor.getCropped())));
		processPanel.add(new JLabel(new ImageIcon(processed)));
		updateUI();
	
	}

	private void showFeatures(BufferedImage image) {
		meanRLabel.setText(""+FeatureExtractor.computeMeanRed(image));
		meanGLabel.setText(""+FeatureExtractor.computeMeanGreen(image));
		meanRGLabel.setText(""+FeatureExtractor.computeMeanRG(image));
		meanHLabel.setText(""+FeatureExtractor.computeMeanHue(image));
		meanALabel.setText(""+FeatureExtractor.computeMeanA(image));
	}

	private void reset() {
		inputImage = null;
		processPanel.removeAll();
		inputLabel.setIcon(null);
		outputLabel.setIcon(null);
		meanRLabel.setText("0.0");
		meanGLabel.setText("0.0");
		meanRGLabel.setText("0.0");
		meanHLabel.setText("0.0");
		meanALabel.setText("0.0");
		updateUI();
	}
}
