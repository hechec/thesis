package colorspace;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class RGBChannel 
{
	public static final int RED = 1, GREEN = 2, BLUE = 3;
	
	/**
	 * Extract R/G/B Channel
	 * @param image BufferedImage
	 * @param channel int
	 * @return BufferedImage
	 */
	public static BufferedImage toRGBChannel(BufferedImage image, int channel) 
	{
		int rgb, 
			width = image.getWidth(),
			height = image.getHeight();
		BufferedImage convertedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		
		for( int i = 0; i < height; i++ ) 
			for( int j = 0; j < width; j++ ) {
				rgb = image.getRGB(j, i);
				
				rgb &= getChannelMask(channel);
				
				convertedImage.setRGB(j, i, rgb);
			}
		return convertedImage;
		
	}
	    
	private static int getChannelMask(int channel) 
	{
		if( channel == RED )
			return 0x00FF0000;
		else if( channel == BLUE )
			return 0x000000FF;
		return 0x0000FF00;  //green
	}

	public static void main(String[] args) {
		
		BufferedImage img = null;
		try {
			img = ImageIO.read(new File("C:/Users/hechec/Desktop/try.jpg"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
