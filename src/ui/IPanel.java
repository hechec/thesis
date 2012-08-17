package ui;

import java.awt.GridLayout;
import java.awt.image.BufferedImage;
import java.awt.image.PixelGrabber;
import java.awt.image.Raster;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.TitledBorder;
import javax.swing.UIManager;

public class IPanel extends JPanel {
	
	private JPanel processPane;
	private JPanel featuresPane;
	private ImageHandler iHandler = new ImageHandler();
	
	private BufferedImage resized, blueChannel, blueFiltered, grayscale, blueMask, segmented;
	private BufferedImage meanRed, meanGreen, meanBlue;
	private BufferedImage lab;
	
	/**
	 * Create the panel.
	 */
	public IPanel() {
		setLayout(new GridLayout(2, 0, 0, 0));		
		
		processPane = new JPanel();
		processPane.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Background Removal", TitledBorder.CENTER, TitledBorder.TOP, null, null));
		add(processPane);
		
		featuresPane = new JPanel();
		featuresPane.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Features", TitledBorder.CENTER, TitledBorder.TOP, null, null));
		add(featuresPane);
		
	}
	
	public void process(BufferedImage original ) {
		removeBackground(original);
		extractFeatures(segmented);
	}
	
	private void removeBackground(BufferedImage original) {
		
		processPane.removeAll();
		processPane.updateUI();
		
		resized = iHandler.resize(original, 100, 100);
		processPane.add(new JLabel(new ImageIcon(resized)));	
	
		blueChannel = iHandler.toBlue(resized);	
		processPane.add(new JLabel(new ImageIcon(blueChannel)));
		
		blueFiltered = iHandler.filter(blueChannel);
		processPane.add(new JLabel(new ImageIcon(blueFiltered)));
		
		grayscale = iHandler.toGray(blueFiltered);
		processPane.add(new JLabel(new ImageIcon(grayscale)));
		
		blueMask = iHandler.binarize(grayscale);
		processPane.add(new JLabel(new ImageIcon(blueMask)));
		
		segmented = iHandler.segment(resized, blueMask);
		processPane.add(new JLabel(new ImageIcon(segmented)));
		
		
	}
	
	private void extractFeatures(BufferedImage segmented) {
		
		featuresPane.removeAll();
		featuresPane.updateUI();
		
		meanRed = iHandler.toRed(segmented);
		//meanRed = iHandler.toGray(meanRed);
		featuresPane.add(new JLabel(new ImageIcon(meanRed)));
		
		meanGreen = iHandler.toGreen(segmented);
		//meanGreen= iHandler.toGray(meanGreen);
		featuresPane.add(new JLabel(new ImageIcon(meanGreen)));

		meanBlue = iHandler.toBlue(segmented);
		//meanBlue = iHandler.toGray(meanBlue);
		featuresPane.add(new JLabel(new ImageIcon(meanBlue)));
		
		lab = iHandler.convertToLab(segmented);
		featuresPane.add(new JLabel(new ImageIcon(lab)));
		
		System.out.println( "red at (50, 50): "+((segmented.getRGB(51, 51) >> 16) & 0xff) +" "+ ((meanRed.getRGB(51, 51) >> 16) & 0xff ) );
		System.out.println( "green at (50, 50): "+((segmented.getRGB(51, 51) >> 8) & 0xff) +" "+ ((meanGreen.getRGB(51, 51) >> 8) & 0xFF ) );
		System.out.println( "blue at (50, 50): "+(segmented.getRGB(51, 51) & 0xff) +" "+ (meanBlue.getRGB(51, 51) & 0xFF ) );
		printPixelARGB(segmented.getRGB(51, 51));
		
	/*	for( int i = 50; i <= 60; i++ ) {
			for( int j = 50; j <= 60; j++ )
				System.out.print( raster.getSample(i, j, 0) +" " );
			System.out.println();
		}System.out.println();
	*/	
	}
	
	public void printPixelARGB(int pixel) {
	    int alpha = (pixel >> 24) & 0xff;
	    int red = (pixel >> 16) & 0xff;
	    int green = (pixel >> 8) & 0xff;
	    int blue = (pixel) & 0xff;
	    System.out.println("argb: " + alpha + ", " + red + ", " + green + ", " + blue);
	  }
	
	public BufferedImage getExtractedImage() {
		return segmented;
	}
	
}
