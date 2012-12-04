package imageProcessing;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

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
		return getAverage(redImage, RGBChannel.RED);
	}

	public double computeMeanGreen(BufferedImage image) 
	{
		BufferedImage greenImage = RGBChannel.toRGBChannel(image, RGBChannel.GREEN);		
		return getAverage(greenImage, RGBChannel.GREEN);
	}
	
	public double getAverage(BufferedImage grayImage, int color) 
	{
		//BufferedImage grayImage = GrayScale.toGray(image);
		double sum = 0, ctr = 0;
		int width = grayImage.getWidth(),
			height = grayImage.getHeight();
		
		//Raster r = grayImage.getData();
		
		int value = 0;
		
		for( int i = 0; i < height; i++ ) 
			for( int j = 0; j < width; j++ ) {
				Color c = new Color(grayImage.getRGB(j, i));
				if( color == RGBChannel.RED )
					value = c.getRed();
				else if( color == RGBChannel.GREEN )
					value = c.getGreen();
				if( value != 0 ) {
					sum += value;
					ctr++;
				}
					
			}
		
		return ctr != 0 ? sum/ctr : 0;
	}

	public double computeMeanRG(BufferedImage image) 
	{
		//BufferedImage redGray =  GrayScale.toGray(RGBChannel.toRGBChannel(image, RGBChannel.RED));
		//BufferedImage greenGray = GrayScale.toGray(RGBChannel.toRGBChannel(image, RGBChannel.GREEN));
		BufferedImage redImage =  RGBChannel.toRGBChannel(image, RGBChannel.RED);
		BufferedImage greenImage = RGBChannel.toRGBChannel(image, RGBChannel.GREEN);
		
		double sum = 0, ctr = 0;
		int width = image.getWidth(),
			height = image.getHeight();
		
		
		
		for( int i = 0; i < height; i++ ) 
			for( int j = 0; j < width; j++ ) {
				Color color = new Color(image.getRGB(j, i));
				if( color.getRed() != 0 ) {
					sum += ( color.getRed() - color.getGreen() );
					ctr++;
				}
				//if( ((image.getRGB(j, i)) & 0xff) != 0 ) {
				//	sum += ( (redGray.getRGB(j, i) & 0xff) - (greenGray.getRGB(j, i) & 0xff) );
				//	ctr++;
				//}
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
	
	public static void main(String[] args)
	{
		BufferedImage image = null;
		File file = new File("C:/Users/hechec/Desktop/saved.jpeg");
		try {
			image  = ImageIO.read(file);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		FeatureExtractor fe = new FeatureExtractor();
		double red = fe.computeMeanRed(image);
		double green = fe.computeMeanGreen(image);
		double redgreen = fe.computeMeanRG(image);
		
		System.out.println( "red: "+red );
		System.out.println( "green: "+green );
		System.out.println( "redgreen: "+redgreen );
		
	}
	
}
