package imageprocessing;

import java.awt.Color;
import java.awt.image.BufferedImage;

import colorspace.CIELab;
import colorspace.HSI;
import colorspace.RGBChannel;

/**
 * Class that extracts color features from the processed image
 * @author Harvey Jake G. Opena
 *
 */
public class FeatureExtractor 
{
	/**
	 * Extracts the average RED component of the RGB image.
	 * @param image
	 * @return Average red
	 */
	public static double computeMeanRed(BufferedImage image) 
	{
		//BufferedImage redImage = RGBChannel.toRGBChannel(image, RGBChannel.RED);
		return getAverage(image, RGBChannel.RED);
	}
	
	/**
	 * Extracts the average GREEN component of the RGB image.
	 * @param image
	 * @return Average green
	 */
	public static double computeMeanGreen(BufferedImage image) 
	{
		//BufferedImage greenImage = RGBChannel.toRGBChannel(image, RGBChannel.GREEN);		
		return getAverage(image, RGBChannel.GREEN);
	}
	
	/**
	 * Extracts the average BLUE component of the RGB image.
	 * @param image
	 * @return Average blue
	 */
	public static double computeMeanBlue(BufferedImage image) 
	{
		//BufferedImage blueImage = RGBChannel.toRGBChannel(image, RGBChannel.BLUE);		
		return getAverage(image, RGBChannel.BLUE);
	}
	
	
	/**
	 * Extracts the average RED-GREEN DIFFERENCE of the RGB image.
	 * @param image
	 * @return Average red-green
	 */
	public static double computeMeanRG(BufferedImage image) 
	{
		//BufferedImage redGray =  GrayScale.toGray(RGBChannel.toRGBChannel(image, RGBChannel.RED));
		//BufferedImage greenGray = GrayScale.toGray(RGBChannel.toRGBChannel(image, RGBChannel.GREEN));
		//BufferedImage redImage =  RGBChannel.toRGBChannel(image, RGBChannel.RED);
		//BufferedImage greenImage = RGBChannel.toRGBChannel(image, RGBChannel.GREEN);
		
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
			}

		return ctr != 0 ? sum/ctr : 0;
	}
	
	/**
	 * Extracts the average a* (from L*a*b*) component of the image
	 * @param image
	 * @return Average a*
	 */
	public static double computeMeanA(BufferedImage image) 
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

	/**
	 * Extracts the average HUE (from HSI) component of the image.
	 * @param image
	 * @return Average hue 
	 */	
	public static double computeMeanHue(BufferedImage image) 
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
	
	private static double getAverage(BufferedImage grayImage, int color) 
	{
		//BufferedImage grayImage = GrayScale.toGray(image);
		double sum = 0, ctr = 0;
		int width = grayImage.getWidth(),
			height = grayImage.getHeight();
		
		int value = 0;
		
		for( int i = 0; i < height; i++ ) 
			for( int j = 0; j < width; j++ ) {
				Color c = new Color(grayImage.getRGB(j, i));
				if( color == RGBChannel.RED )
					value = c.getRed();
				else if( color == RGBChannel.GREEN )
					value = c.getGreen();
				else if( color == RGBChannel.BLUE ) 
					value = c.getBlue();
				if( value != 0 ) {
					sum += value;
					ctr++;
				}
					
			}
		
		return ctr != 0 ? sum/ctr : 0;
	}
	
}
