package ui;

import java.awt.AlphaComposite;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.awt.image.Raster;

import colorspace.CIELab;
import colorspace.HSI;
import colorspace.RGBChannel;

import preprocessing.GrayScale;
import preprocessing.ImageSegmentation;
import preprocessing.MeanFilter;
import preprocessing.OtsuThreshold;

public class ImageHandler {
	
	MyDialog dialog;
	
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
	
		return CIELab.convertToLab(original);
		
	}

	public BufferedImage normalize(BufferedImage segmented) {
		int minX = segmented.getWidth(), maxX = 0, minY = segmented.getHeight(), maxY = 0;
		
		for( int x = 0; x < segmented.getWidth(); x++) 
			for( int y = 0; y < segmented.getHeight(); y++ ) {
				if( (segmented.getRGB(x, y) & 0x00FFFFFF) != 0  ) {
					if( x < minX  )
						minX = x;
					else if( x > maxX )
						maxX = x;
					if( y < minY )
						minY = y;
					else if( y > maxY)
						maxY = y;
				}
			}

		int w = maxX - minX;
		int h = maxY - minY;
		BufferedImage normalized = new BufferedImage(w, h, segmented.getType());
		for( int i = 0; i < h; i++ ) 
			for( int j = 0; j < w; j++ ) {
				normalized.setRGB(j, i, segmented.getRGB(minX+j, minY+i));
			}
		return normalized;
	}

	public BufferedImage extract(BufferedImage original) {
		dialog = new MyDialog();
		dialog.addImage(original);
		BufferedImage result = toBlue(original);
		dialog.addImage(result);
		result = filter(result);
		dialog.addImage(result);
		result = toGray(result);
		dialog.addImage(result);
		result = binarize(result);
		dialog.addImage(result);
		result = segment(original, result);
		dialog.addImage(result);
		result = normalize(result);
		dialog.addImage(result);
		result = resize(result, 64, 64);
		dialog.addImage(result);
		dialog.setVisible(true);
		return resize(normalize(segment(original, binarize(toGray(filter(toBlue(original)))))), 64, 64);
	}

	public int computeMeanRed(BufferedImage image) {
		BufferedImage redImage = RGBChannel.toRGBChannel(image, RGBChannel.RED);
		return getAverage(redImage);
	}

	public int computerMeanGreen(BufferedImage image) {
		BufferedImage greenImage = RGBChannel.toRGBChannel(image, RGBChannel.GREEN);
		return getAverage(greenImage);
	}
	
	public int getAverage(BufferedImage image) {
		BufferedImage grayImage = toGray(image);
		int sum = 0, ctr = 0;
		
		Raster r = grayImage.getData();
		
		for( int i = 0; i < grayImage.getHeight(); i++ ) 
			for( int j = 0; j < grayImage.getWidth(); j++ ) {
				
				//System.out.println( r.getSample(j, i, 0) +" - "+((grayImage.getRGB(j, i)) & 0xff) );
				
				if( r.getSample(j, i, 0) != 0 ) {
					sum += r.getSample(j, i, 0);
					//System.out.println((grayRed.getRGB(j, i)) & 0xff);
					ctr++;
				}
			}
		
		return ctr != 0 ? sum/ctr : 0;
	}

	public int computeMeanRG(BufferedImage image) {
		BufferedImage redGray =  toGray(RGBChannel.toRGBChannel(image, RGBChannel.RED));
		BufferedImage greenGray = toGray(RGBChannel.toRGBChannel(image, RGBChannel.GREEN));
		int sum = 0, ctr = 0;
		
		for( int i = 0; i < image.getHeight(); i++ ) 
			for( int j = 0; j < image.getWidth(); j++ ) {
				if( ((image.getRGB(j, i)) & 0xff) != 0 ) {
					sum += ( (redGray.getRGB(j, i) & 0xff) - (greenGray.getRGB(j, i) & 0xff) );
					//System.out.println((grayRed.getRGB(j, i)) & 0xff);
					ctr++;
				}
			}
		//System.out.println("ctr "+ctr);
		return ctr != 0 ? sum/ctr : 0;
	}

	public int computeMeanA(BufferedImage image) {
		int sum = 0, ctr = 0, rgb, red, green, blue;
		double[] lab = new double[3];
		
		for( int i = 0; i < image.getHeight(); i++ ) 
			for( int j = 0; j < image.getWidth(); j++ ) {
				if( ((image.getRGB(j, i)) & 0xff) != 0 ) {
					rgb = image.getRGB(j, i);
					red =  (rgb >> 16) & 0xFF;
					green = (rgb >> 8) & 0xFF;
					blue = rgb & 0xFF;
					lab = CIELab.RGBtoLAB(red, green, blue);
					sum += lab[1];
					ctr++;
				}
			}
		
		return ctr != 0 ? sum/ctr : 0;
	}

	public int computeMeanHue(BufferedImage image) {
		int rgb, red, green, blue, sum = 0, ctr = 0;
		
		for( int i = 0; i < image.getHeight(); i++ ) 
			for( int j = 0; j < image.getWidth(); j++ ) {
				if( ((image.getRGB(j, i)) & 0xff) != 0 ) {
					rgb = image.getRGB(j, i);
					red =  (rgb >> 16) & 0xFF;
					green = (rgb >> 8) & 0xFF;
					blue = rgb & 0xFF;
					sum += HSI.getHue(red, green, blue);
					ctr++;
				}
			}
		return ctr != 0 ? sum/ctr : 0;
	}
	
}
