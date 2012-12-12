package image_processing;

import java.awt.image.BufferedImage;

public class ImageUtility {

	public static int[] to1DArray(BufferedImage image)
	{
		int[] pixels = new int[image.getWidth()*image.getHeight()];
		int ctr = 0;
		
		for( int i = 0; i < image.getHeight(); i++  )
			for( int j = 0; j < image.getWidth(); j++ )
				pixels[ctr++] = image.getRGB(j, i);
		
		return pixels;
	}
	
	public static int[][] to2DArray(BufferedImage blueImage, int width, int height) 
	{
		int[][] arr = new int[height][width];
		
		for( int i = 0; i < height; i++ )
			for( int j = 0; j < width; j++ ) 
				arr[i][j] = blueImage.getRGB(j, i) & 0x000000FF;
		return arr;
	}
	
	public static BufferedImage toImage(int[] pixels, int w, int h)
	{
		BufferedImage image = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);
		
		for( int ctr = 0, j = 0, i = 0 ; ctr < pixels.length;j++, ctr++ ) {
			if( ctr > 0 && (ctr) % w == 0 ) { // w or h ?
 				j = 0;
				i++;
			}		
			image.setRGB(j, i, pixels[ctr]);
		}
			
		return image;
	}	
	
}
