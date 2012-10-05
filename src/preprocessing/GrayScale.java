package preprocessing;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.awt.image.BufferedImageOp;
import java.awt.image.ColorConvertOp;
import java.awt.image.Raster;
import java.awt.image.WritableRaster;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class GrayScale {

	public static BufferedImage toGray(BufferedImage image) {
		 
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
	        int alpha, red, green, blue;
	        int newPixel;
	 
	        BufferedImage avg_gray = new BufferedImage(image.getWidth(), image.getHeight(), image.getType());
	        int[] avgLUT = new int[766];
	        for(int i=0; i<avgLUT.length; i++) avgLUT[i] = (int) (i / 3);
	 
	        for(int i=0; i<image.getWidth(); i++) {
	            for(int j=0; j<image.getHeight(); j++) {
	 
	                // Get pixels by R, G, B
	                alpha = new Color(image.getRGB(i, j)).getAlpha();
	                red = new Color(image.getRGB(i, j)).getRed();
	                green = new Color(image.getRGB(i, j)).getGreen();
	                blue = new Color(image.getRGB(i, j)).getBlue();
	 
	                newPixel = red + green + blue;
	                newPixel = avgLUT[newPixel];
	                // Return back to original format
	                newPixel = colorToRGB(alpha, newPixel, newPixel, newPixel);
	 
	                // Write pixels into image
	                avg_gray.setRGB(i, j, newPixel);
	 
	            }
	        }
	        return avg_gray;
	}
	
	// Convert R, G, B, Alpha to standard 8 bit
    private static int colorToRGB(int alpha, int red, int green, int blue) {
 
        int newPixel = 0;
        newPixel += alpha;
        newPixel = newPixel << 8;
        newPixel += red; newPixel = newPixel << 8;
        newPixel += green; newPixel = newPixel << 8;
        newPixel += blue;
 
        return newPixel;
 
    }
    
}
