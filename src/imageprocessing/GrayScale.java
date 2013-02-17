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
	
	public static void main(String[] args) {
		BufferedImage image = new BufferedImage(100, 100, BufferedImage.TYPE_INT_RGB);
		Color color = new Color(1, 11, 3);
		image.setRGB(99, 99, color.getRGB());
		System.out.println( color.getRed() +" "+color.getGreen()+" "+color.getBlue() );		
		
		image = GrayScale.toGray(image);
		Color gray = new Color(image.getRGB(99, 99));
		System.out.println( gray.getRed() +" "+gray.getGreen()+" "+gray.getBlue() );
	}
	
}
