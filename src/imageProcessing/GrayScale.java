package imageProcessing;

import java.awt.Color;
import java.awt.image.BufferedImage;

import colorspace.RGBManipulator;

public class GrayScale extends RGBManipulator
{

	public static BufferedImage toGray(BufferedImage image) 
	{
		 
	    /*int alpha, red, green, blue;
	    int newPixel;
	 
	    BufferedImage lum = new BufferedImage(original.getWidth(), original.getHeight(), original.getType());
	 
	    for(int i=0; i<original.getWidth(); i++) {
	        for(int j=0; j<original.getHeight(); j++) {
	 
	            // Get pixels by R, G, B
	            alpha = new Color(original.getRGB(i, j)).getAlpha();
	            red = new Color(original.getRGB(i, j)).getRed();
	            green = new Color(original.getRGB(i, j)).getGreen();
	            blue = new Color(original.getRGB(i, j)).getBlue();
	 
	            red = (int) (0.21 * red + 0.71 * green + 0.07 * blue);
	            // Return back to original format
	            newPixel = colorToRGB(alpha, red, red, red);
	 
	            // Write pixels into image
	            lum.setRGB(i, j, newPixel);
	 
	        }
	    }
	 
	    return lum;
	 	*/
	        int red, green, blue;
	        int newPixel;
	        int width = image.getWidth(),
	    		height = image.getHeight();
	        
	        BufferedImage avg_gray = new BufferedImage(width, height, image.getType());
	        int[] avgLUT = new int[766];
	        for(int i=0; i<avgLUT.length; i++) avgLUT[i] = (int) (i / 3);
	 
	        for(int i=0; i<width; i++) {
	            for(int j=0; j<height; j++) {
	                //alpha = new Color(image.getRGB(i, j)).getAlpha();
	                red = new Color(image.getRGB(i, j)).getRed();
	                green = new Color(image.getRGB(i, j)).getGreen();
	                blue = new Color(image.getRGB(i, j)).getBlue();
	 
	                newPixel = red + green + blue;
	                newPixel = avgLUT[newPixel];
	                
	                avg_gray.setRGB(i, j, mixColor(newPixel, newPixel, newPixel));
	            }
	        }
	        return avg_gray;
	}
	
}
