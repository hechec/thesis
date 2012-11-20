package imageProcessing;

import java.awt.image.BufferedImage;

public class Cropper 
{

	public static BufferedImage crop(BufferedImage image) 
	{
		int width = image.getWidth(),
			height = image.getHeight(),
			minX = width, 
			maxX = 0, 
			minY = height, 
			maxY = 0;
		
		for( int x = 0; x < width; x++) 
			for( int y = 0; y < height; y++ ) {
				if( (image.getRGB(x, y) & 0x00FFFFFF) != 0  ) {
					if( x < minX  )
						minX = x;
					else if( x > maxX )
						maxX = x;
					if( y < minY )
						minY = y;
					else if( y > maxY)
						maxY = y;
				}
			}

		int w = maxX - minX,
			h = maxY - minY;
		
		BufferedImage cropped = new BufferedImage(w, h, image.getType());
		for( int i = 0; i < h; i++ ) 
			for( int j = 0; j < w; j++ ) {
				cropped.setRGB(j, i, image.getRGB(minX+j, minY+i));
			}
		return cropped;
	}
	
}
