package preprocessing;

import java.awt.image.BufferedImage;

public class MeanFilter {
	
	private static final int KERNEL_SIZE = 3;
	
	
	
	/*
	 *  filter 2
	 * 
	 */
	public static BufferedImage filter(BufferedImage original) {
		BufferedImage filtered = new BufferedImage(original.getWidth(), original.getHeight(), BufferedImage.TYPE_INT_RGB);
		int mean;
		int[][] inputArray = toArray(original, original.getWidth(), original.getHeight());
		int w = original.getWidth();
		int h = original.getHeight();
		for( int i = 0; i < original.getHeight(); i++ ) 
			for( int j = 0; j < original.getWidth(); j++ ){
				mean = meanArithmetic(inputArray, KERNEL_SIZE, h, w, i, j);
				filtered.setRGB(j, i, mean);
			}
		return filtered;
	}
	
	private static int[][] toArray(BufferedImage image, int width, int height) 
	{
		int[][] arr = new int[height][width];
		
		for( int i = 0; i < height; i++ )
			for( int j = 0; j < width; j++ ) 
				arr[i][j] = image.getRGB(j, i);
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
    private static int meanArithmetic(int[][] input, int k,int w, int h, int x, int y) {
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
    
    /*
	 *  filter 1
	 * 
	 */
	public static BufferedImage convert(BufferedImage labImage) {
		BufferedImage img = new BufferedImage(labImage.getWidth(), labImage.getHeight(), BufferedImage.TYPE_INT_RGB);
		int mean;
		for( int i = 0; i < labImage.getHeight(); i++ ) 
			for( int j = 0; j < labImage.getWidth(); j++ ){
				mean = getMean(labImage, 0, j, i);
				img.setRGB(j, i, mean);
			}
		return img;
	}
	
	private static int getMean(BufferedImage img, int kernekSize, int x, int y) 
	{
		int sum = 0;
		
		for( int i = -1; i < 2; i++ )
			for( int j = -1; j < 2; j++ ) {
				try{
					sum += img.getRGB(x+j, y+i) & 0x00ffffff;
				}	
				catch(IndexOutOfBoundsException e){}
			}
		return sum/9;
	}

	public static void main(String[] args) {
		BufferedImage img = new BufferedImage(3, 3, BufferedImage.TYPE_INT_RGB);
		img.setRGB(0, 0, 1);
		img.setRGB(1, 0, 2);
		img.setRGB(2, 0, 3);
		img.setRGB(0, 1, 4);
		img.setRGB(1, 1, 5);
		img.setRGB(2, 1, 6);
		img.setRGB(0, 2, 7);
		img.setRGB(1, 2, 8);
		img.setRGB(2, 2, 9);
		
		int[][] inputArray = toArray(img, img.getWidth(), img.getHeight());
		System.out.println(meanArithmetic(inputArray, KERNEL_SIZE, img.getHeight(), img.getWidth(), 0, 2) & 0x00ffffff);
		
		System.out.println( getMean(img, 0, 1, 1) );
		System.exit(1);
		
	//	System.out.println( (int)img.getRGB(0, 0)  & 0x00ffffff );
	}

}
