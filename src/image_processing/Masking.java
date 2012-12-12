package image_processing;

import java.awt.image.BufferedImage;

import colorspace.RGBManipulator;

public class Masking extends RGBManipulator 
{
	
	public static BufferedImage extract(BufferedImage original, BufferedImage mask) 
	{
		int width = original.getWidth(),
			height = original.getHeight();	
		BufferedImage img = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		int pixelValue;
		int bgValue = mixColor(0, 0, 0);
		
		for( int i = 0; i < height; i++ )
			for( int j = 0; j < width; j++ ) {
				pixelValue = mask.getRGB(j, i) & 0x00ffffff;
				if( pixelValue == bgValue )
					img.setRGB(j, i, mixColor(0, 0, 0));
				else{
					img.setRGB(j, i, original.getRGB(j, i) );
				}
			}
		return img;
		
	}
	
}
