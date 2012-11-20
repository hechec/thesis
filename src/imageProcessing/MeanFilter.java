package imageProcessing;

import java.awt.image.BufferedImage;

public class MeanFilter 
{
	
	private static final int KERNEL_SIZE = 3;
	
	public static BufferedImage filter(BufferedImage blueImage) 
	{
		BufferedImage filtered = new BufferedImage(blueImage.getWidth(), blueImage.getHeight(), BufferedImage.TYPE_INT_RGB);
		int mean;
		int[][] inputArray = toArray(blueImage, blueImage.getWidth(), blueImage.getHeight());
		int w = blueImage.getWidth(); // makes the program faster; instead of repeatedly calling inside the loop
		int h = blueImage.getHeight();
		for( int i = 0; i < h; i++ ) 
			for( int j = 0; j < w; j++ ){
				mean = meanArithmetic(inputArray, KERNEL_SIZE, h, w, i, j);
				filtered.setRGB(j, i, mean);
			}
		return filtered;
	}
	
	private static int[][] toArray(BufferedImage blueImage, int width, int height) 
	{
		int[][] arr = new int[height][width];
		
		for( int i = 0; i < height; i++ )
			for( int j = 0; j < width; j++ ) 
				arr[i][j] = blueImage.getRGB(j, i) & 0x000000FF;
		return arr;
	}
	
	/**
    * Calculates the arithmetic mean of a kxk pixel neighbourhood (including centre pixel).
    *
    * @param input The input image 2D array
    * @param k Dimension of the kernel
    * @param w The image width
    * @param h The image height
    * @param x The x coordinate of the centre pixel of the array
    * @param y The y coordinate of the centre pixel of the array
    * @return The arithmetic mean of the kxk pixels
    */ 
    private static int meanArithmetic(int[][] input, int k,int w, int h, int x, int y) 
    {
        int sum = 0;
        int number = 0;
        for(int j=0;j<k;++j)
        {
            for(int i=0;i<k;++i)
            {
	            if(((x-1+i)>=0) && ((y-1+j)>=0) && ((x-1+i)<w) && ((y-1+j)<h))
                {
	                sum = sum + input[x-1+i][y-1+j];
	                ++number;
	            }
            }
        }
        if(number==0) 
            return 0;
        return (sum/number);
    }

}