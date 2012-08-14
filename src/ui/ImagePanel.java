package ui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

import preprocessing.GrayScale;
import preprocessing.ImageManager;
import preprocessing.ImageSegmentation;
import preprocessing.MeanFilter;
import preprocessing.OtsuThreshold;
import preprocessing.RGBChannel;
import preprocessing.LABColorSpace;

public class ImagePanel extends JPanel {
	
	private JLabel imageLabel = new JLabel(), imageLabel2 = new JLabel(), 
			imageLabel3 = new JLabel(), imageLabel4 = new JLabel(), imageLabel5 = new JLabel(), imageLabel6 = new JLabel(),
					 imageLabel7 = new JLabel(),  imageLabel8 = new JLabel();
	
	private BufferedImage image, blueChannel, blueFiltered, blueGrayScale, blueBinary, redChannel, redGrayScale, redBinary, segmentedBlue,
						sobelEdge, labImage, lImage, redgreenChannel, redgreenGray, redgreenBinary, greenChannel, ORed;
	
	private BufferedImage resizedImage;
	
	public ImagePanel() {
		this.add(imageLabel);	
		this.add(imageLabel2);
		this.add(imageLabel3);
		this.add(imageLabel4);
		this.add(imageLabel5);
		this.add(imageLabel6);
		this.add(imageLabel7);
		this.add(imageLabel8);
		initPanel();
	}
	
	private void initPanel() {
		//setLayout(new GridLayout(3, 2));
		setBorder(BorderFactory.createLineBorder(Color.BLACK));
		//imageLabel.setMinimumSize(new Dimension(300, 300));
		//imageLabel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		//imageLabel2.setBorder(BorderFactory.createLineBorder(Color.BLACK));
	}
	
	public void addImage(File file) {
		try {
			image = ImageIO.read(file);
			imageLabel.setIcon(new ImageIcon(file.getAbsolutePath()));	
		} catch (IOException e) {}
		
		ImageManager im = new ImageManager(image);
		
		resizedImage = im.getResizedImage();
		
		blueChannel = RGBChannel.toRGBChannel(resizedImage, RGBChannel.BLUE);
		blueFiltered = MeanFilter.filter(blueChannel);
		blueGrayScale = GrayScale.grayScale(blueFiltered);
		blueBinary = OtsuThreshold.binarize(blueGrayScale);
		segmentedBlue = ImageSegmentation.extract(resizedImage, blueBinary);

		imageLabel2.setIcon(new ImageIcon(blueChannel));
		//imageLabel3.setIcon(new ImageIcon(blueFiltered));
		//imageLabel4.setIcon(new ImageIcon(blueGrayScale));	
		imageLabel5.setIcon(new ImageIcon(blueBinary));	
		imageLabel6.setIcon(new ImageIcon(segmentedBlue));	
		imageLabel3.setIcon(new ImageIcon(resizedImage));	
		
		redgreenChannel = RGBChannel.toRGChannel(segmentedBlue, "REDGREEN");
		//imageLabel7.setIcon(new ImageIcon(redgreenChannel));	
	    //redChannel = RGBChannel.toRGBChannel(image, "RED");
	    //redGrayScale = GrayScale.grayScale(redChannel);
	    //redBinary = OtsuThreshold.binarize(redGrayScale);
	    //imageLabel6.setIcon(new ImageIcon(redChannel));
	   
	    
	    //ORed = RGBChannel.ORredblue( blueBinary, redBinary );
	    //imageLabel7.setIcon(new ImageIcon(ORed));
		//greenChannel = GrayScale.grayScale(segmentedBlue);
		//System.out.println( RGBChannel.getRminusGavg(segmentedBlue) );
		//System.out.println( RGBChannel.getRminusGavg(greenChannel) );
		
		//labImage = LABColorSpace.convertToLab(segmentedBlue);
		//imageLabel6.setIcon(new ImageIcon(labImage));
		//imageLabel7.setIcon(new ImageIcon(segmentedBlue));
		
		//System.out.println( LABColorSpace.getMeanA(segmentedBlue) );
		
		//imageLabel5.setIcon(new ImageIcon(redChannel));	
		//imageLabel6.setIcon(new ImageIcon(redGrayScale));	
		//imageLabel7.setIcon(new ImageIcon(redBinary));	
	}
	
	
	public BufferedImage getOriginalImage() {
		return image;
	}
	
}