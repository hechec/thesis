package ui;

import java.awt.AlphaComposite;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import preprocessing.GrayScale;
import preprocessing.ImageSegmentation;
import preprocessing.LABColorSpace;
import preprocessing.MeanFilter;
import preprocessing.OtsuThreshold;
import preprocessing.RGBChannel;

public class ImageHandler {
	
	public ImageHandler() {
	}
	
	/**
	 * 
	 * @param BufferedImage
	 * @param WIDTH
	 * @param HEIGHT
	 * @return BufferedImage
	 */
	public BufferedImage resize(BufferedImage original, int scaledWidth, int scaledHeight) {
		
		BufferedImage resizedImage = new BufferedImage(scaledWidth, scaledHeight, original.getType());
		Graphics2D g = resizedImage.createGraphics();
		g.drawImage(original, 0, 0, scaledWidth, scaledHeight, null);
		g.setComposite(AlphaComposite.Src);
		g.dispose();	
		return resizedImage;
	
	} 
	
	public BufferedImage toBlue(BufferedImage original) {
		
		return RGBChannel.toRGBChannel(original, RGBChannel.BLUE);
		
	}
	
	public BufferedImage filter(BufferedImage original) {

		return MeanFilter.filter(original);
	
	}
	
	public BufferedImage toGray(BufferedImage original) {
		
		return GrayScale.toGray(original);
		
	}
	
	public BufferedImage binarize(BufferedImage original) {
		
		return OtsuThreshold.binarize(original);

	}
	
	public BufferedImage segment(BufferedImage original, BufferedImage mask) {
		
		return ImageSegmentation.extract(original, mask);
		
	}

	public BufferedImage toRed(BufferedImage original) {
		
		return RGBChannel.toRGBChannel(original, RGBChannel.RED);
	
	}

	public BufferedImage toGreen(BufferedImage original) {

		return RGBChannel.toRGBChannel(original, RGBChannel.GREEN);
		
	}

	public BufferedImage convertToLab(BufferedImage original) {
	
		return LABColorSpace.convertToLab(original);
		
	}
	
}
