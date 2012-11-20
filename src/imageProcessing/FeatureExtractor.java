package imageProcessing;

import java.awt.image.BufferedImage;
import java.awt.image.Raster;

import colorspace.CIELab;
import colorspace.HSI;
import colorspace.RGBChannel;

public class FeatureExtractor 
{
	
	private static FeatureExtractor instance = null;
	
	public FeatureExtractor()
	{
	}
	
	public static FeatureExtractor getInstance()
	{
		if( instance == null )
			instance = new FeatureExtractor();
		return instance;
	}
	
	public double computeMeanRed(BufferedImage image) 
	{
		BufferedImage redImage = RGBChannel.toRGBChannel(image, RGBChannel.RED);
		return getAverage(redImage);
	}

	public double computeMeanGreen(BufferedImage image) 
	{
		BufferedImage greenImage = RGBChannel.toRGBChannel(image, RGBChannel.GREEN);
		return getAverage(greenImage);
	}
	
	public double getAverage(BufferedImage image) 
	{
		BufferedImage grayImage = GrayScale.toGray(image);
		double sum = 0, ctr = 0;
		int width = grayImage.getWidth(),
			height = grayImage.getHeight();
		
		Raster r = grayImage.getData();
		
		for( int i = 0; i < height; i++ ) 
			for( int j = 0; j < width; j++ ) {
				if( r.getSample(j, i, 0) != 0 ) {
					sum += r.getSample(j, i, 0);
					ctr++;
				}
			}
		
		return ctr != 0 ? sum/ctr : 0;
	}

	public double computeMeanRG(BufferedImage image) 
	{
		BufferedImage redGray =  GrayScale.toGray(RGBChannel.toRGBChannel(image, RGBChannel.RED));
		BufferedImage greenGray = GrayScale.toGray(RGBChannel.toRGBChannel(image, RGBChannel.GREEN));
		double sum = 0, ctr = 0;
		int width = image.getWidth(),
			height = image.getHeight();
		
		for( int i = 0; i < height; i++ ) 
			for( int j = 0; j < width; j++ ) {
				if( ((image.getRGB(j, i)) & 0xff) != 0 ) {
					sum += ( (redGray.getRGB(j, i) & 0xff) - (greenGray.getRGB(j, i) & 0xff) );
					ctr++;
				}
			}
		return ctr != 0 ? sum/ctr : 0;
	}

	public double computeMeanA(BufferedImage image) 
	{
		double sum = 0;
		int ctr = 0, rgb, red, green, blue;
		double[] lab = new double[3];
		int width = image.getWidth(),
			height = image.getHeight();
		
		for( int i = 0; i < height; i++ ) 
			for( int j = 0; j < width; j++ ) {
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

	public double computeMeanHue(BufferedImage image) 
	{
		double sum = 0;
		int rgb, red, green, blue, ctr = 0;
		int width = image.getWidth(),
			height = image.getHeight();
		
		for( int i = 0; i < height; i++ ) 
			for( int j = 0; j < width; j++ ) {
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
