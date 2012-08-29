package colorspace;

import java.awt.Color;
import java.awt.image.BufferedImage;

public class RGBChannel {
	public static final int RED = 1, GREEN = 2, BLUE = 3;
	
	/**
	 * Extract R/G/B Channel
	 * @param image
	 * @param channel
	 * @return specified RGB Channel
	 */
	public static BufferedImage toRGBChannel(BufferedImage image, int channel) {
		
		BufferedImage convertedImage = new BufferedImage(image.getWidth(), image.getHeight(), BufferedImage.TYPE_INT_RGB);
		int rgb;
		for( int i = 0; i < image.getHeight(); i++ ) 
			for( int j = 0; j < image.getWidth(); j++ ) {
				rgb = image.getRGB(j, i);
				rgb &= getChannelMask(channel);
				convertedImage.setRGB(j, i, rgb);
			}
		return convertedImage;
		
	}
	
	//A is 0xFF000000
    //R is 0x00FF0000
    //G is 0x0000FF00
    //B is 0x000000FF        
	private static int getChannelMask(int channel) 
	{
		if( channel == RED )
			return 0x00FF0000;
		else if( channel == BLUE )
			return 0x000000FF;
		//green
		return 0x0000FF00; 
	}
	
	public static void main(String[] args) {
		
		BufferedImage img = new BufferedImage(10, 10, BufferedImage.TYPE_INT_RGB);
		img.setRGB(0, 0, 12321);
		//System.out.print( getChannelColor("REDGREEN", img.getRGB(0, 0)) );
		Color c = new Color(img.getRGB(0, 0));
		int red = c.getRed();
		int green = c.getGreen();
		System.out.println(red|green);
		
		
	}

}
