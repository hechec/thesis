package preprocessing;

import java.awt.image.BufferedImage;

public class ImageSegmentation {
	
	public static BufferedImage extract(BufferedImage original, BufferedImage mask) 
	{
		BufferedImage img = new BufferedImage(original.getWidth(), original.getHeight(), BufferedImage.TYPE_INT_RGB);
		int pixelValue;
		int bgValue = mixColor(0, 0, 0);
		for( int i = 0; i < original.getHeight(); i++ )
			for( int j = 0; j < original.getWidth(); j++ ) {
				pixelValue = mask.getRGB(j, i) & 0x00ffffff;
				if( pixelValue == bgValue )
					img.setRGB(j, i, mixColor(0, 0, 0));
				else{
					img.setRGB(j, i, original.getRGB(j, i) );
				}
			}
		return img;
		
	}
	
	private static int mixColor(int red, int green, int blue) 
	{
		return red<<16|green<<8|blue;
	}
	
}
