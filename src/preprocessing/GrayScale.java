package preprocessing;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.awt.image.BufferedImageOp;
import java.awt.image.ColorConvertOp;

public class GrayScale {
	
	public static BufferedImage grayScale(BufferedImage image) {
		BufferedImage img = new BufferedImage(image.getWidth(), image.getHeight(), BufferedImage.TYPE_BYTE_GRAY);
		//ColorConvertOp colorConvert = new ColorConvertOp(ColorSpace.getInstance(ColorSpace.CS_GRAY), null);
	    //colorConvert.filter(image, img);
	    
	    BufferedImageOp grayscaleConv = 
	    	      new ColorConvertOp(image.getColorModel().getColorSpace(), 
	    	                         img.getColorModel().getColorSpace(), null);
	    	   grayscaleConv.filter(image, img);
	    	   
	    return img;
	}
	
	public static BufferedImage toGray(BufferedImage original) {
		 
	    int alpha, red, green, blue;
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
