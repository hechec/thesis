package ui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.image.BufferedImage;
import java.awt.image.PixelGrabber;
import java.awt.image.Raster;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.border.TitledBorder;
import javax.swing.UIManager;

public class IPanel extends JPanel {
	
	private JPanel processPane;
	private JPanel featuresPane;
	private ImageHandler iHandler = new ImageHandler();
	
	private BufferedImage original, cropped, blueChannel, blueFiltered, grayscale, blueMask, segmented, normalized;
	private BufferedImage meanRed, meanGreen, meanBlue;
	private BufferedImage lab;
	
	private JScrollPane scroll1;
	private JScrollPane scroll2;
	
	/**
	 * Create the panel.
	 */
	public IPanel() {
		setLayout(new GridLayout(2, 0, 0, 0));	
		
		scroll1 = new JScrollPane();
		processPane = new JPanel();
		scroll1.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Background Removal", TitledBorder.CENTER, TitledBorder.TOP, null, null));
		scroll1.getViewport().add(processPane);
		add(scroll1);
		
		scroll2 = new JScrollPane();
		featuresPane = new JPanel();
		scroll2.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Features", TitledBorder.CENTER, TitledBorder.TOP, null, null));
		scroll2.getViewport().add(featuresPane);
		add(scroll2);
		
	}
	
	public void process(BufferedImage original ) {
		removeBackground(original);
		extractFeatures(segmented);
	}
	
	private void removeBackground(BufferedImage original) {
		
		processPane.removeAll();
		processPane.updateUI();
		
		JPanel originalPanel = new JPanel();
		originalPanel.setBorder(new TitledBorder(null, "original", TitledBorder.CENTER, TitledBorder.TOP, null, null));
		original = iHandler.resize(original, (int) (200/((float)original.getHeight())*original.getWidth()), 200 );
		originalPanel.add(new JLabel(new ImageIcon(original)));
		processPane.add(originalPanel);	
	
		
		JPanel bPanel = new JPanel();
		bPanel.setBorder(new TitledBorder(null, "blue channel", TitledBorder.CENTER, TitledBorder.TOP, null, null));
		blueChannel = iHandler.toBlue(original);	
		bPanel.add(new JLabel(new ImageIcon(blueChannel)));
		processPane.add(bPanel);	
		
		JPanel bfPanel = new JPanel();
		bfPanel.setBorder(new TitledBorder(null, "filtered", TitledBorder.CENTER, TitledBorder.TOP, null, null));
		blueFiltered = iHandler.filter(blueChannel);
		bfPanel.add(new JLabel(new ImageIcon(blueFiltered)));
		processPane.add(bfPanel);
		
		JPanel gPanel = new JPanel();
		gPanel.setBorder(new TitledBorder(null, "grayscale", TitledBorder.CENTER, TitledBorder.TOP, null, null));
		grayscale = iHandler.toGray(blueFiltered);
		gPanel.add(new JLabel(new ImageIcon(grayscale)));
		processPane.add(gPanel);
		
		JPanel mPanel = new JPanel();
		mPanel.setBorder(new TitledBorder(null, "mask", TitledBorder.CENTER, TitledBorder.TOP, null, null));
		blueMask = iHandler.binarize(grayscale);
		mPanel.add(new JLabel(new ImageIcon(blueMask)));
		processPane.add(mPanel);
		
		JPanel sPanel = new JPanel();
		sPanel.setBorder(new TitledBorder(null, "segmented", TitledBorder.CENTER, TitledBorder.TOP, null, null));
		segmented = iHandler.segment(original, blueMask);
		sPanel.add(new JLabel(new ImageIcon(segmented)));
		processPane.add(sPanel);
		
		
		
		cropped = iHandler.normalize(segmented);
		processPane.add(new JLabel(new ImageIcon(cropped)));
		
		normalized = iHandler.resize(cropped, 64, 64);
		processPane.add(new JLabel(new ImageIcon(normalized)));
		
		System.out.println("normalized size: "+normalized.getWidth()+" "+normalized.getHeight());
		
	}
	
	private void extractFeatures(BufferedImage segmented) {
		
		featuresPane.removeAll();
		featuresPane.updateUI();
		
		meanRed = iHandler.toRed(segmented);
		//meanRed = iHandler.toGray(meanRed);
		
		JPanel redPanelanel = new JPanel();
		redPanelanel.setBorder(new TitledBorder(null, "red", TitledBorder.CENTER, TitledBorder.TOP, null, null));
		featuresPane.add(redPanelanel);
		redPanelanel.setLayout(new BorderLayout(0, 0));
		
		JLabel redImage = new JLabel(new ImageIcon(meanRed));
		redPanelanel.add(redImage, BorderLayout.CENTER);
		
		JLabel redValue = new JLabel("New label");
		redPanelanel.add(redValue, BorderLayout.SOUTH);
		
		meanGreen = iHandler.toGreen(segmented);
		//meanGreen= iHandler.toGray(meanGreen);
		
		JPanel greenPanel = new JPanel();
		greenPanel.setBorder(new TitledBorder(null, "green", TitledBorder.CENTER, TitledBorder.TOP, null, null));
		featuresPane.add(greenPanel);
		greenPanel.setLayout(new BorderLayout(0, 0));
		
		JLabel greenImage = new JLabel(new ImageIcon(meanGreen));
		greenPanel.add(greenImage, BorderLayout.CENTER);
		
		JLabel greenValue = new JLabel("New label");
		greenPanel.add(greenValue, BorderLayout.SOUTH);
		
		
		meanBlue = iHandler.toBlue(segmented);
		//meanBlue = iHandler.toGray(meanBlue);
		
		JPanel bluePanel = new JPanel();
		bluePanel.setBorder(new TitledBorder(null, "blue", TitledBorder.CENTER, TitledBorder.TOP, null, null));
		featuresPane.add(bluePanel);
		bluePanel.setLayout(new BorderLayout(0, 0));
		
		JLabel blueImage = new JLabel(new ImageIcon(meanBlue));
		bluePanel.add(blueImage, BorderLayout.CENTER);
		
		JLabel blueValue = new JLabel("New label");
		bluePanel.add(blueValue, BorderLayout.SOUTH);
		
		
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
		return normalized;
	}
	
}
