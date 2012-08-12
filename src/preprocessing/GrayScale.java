package preprocessing;

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
	
}
