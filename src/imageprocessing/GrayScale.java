package imageprocessing;

import java.awt.Color;
import java.awt.image.BufferedImage;

import colorspace.RGBManipulator;

public class GrayScale extends RGBManipulator
{

	public static BufferedImage toGray(BufferedImage image) 
	{
		int red, green, blue, newPixel;
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
