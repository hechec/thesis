package image_processing;

import java.awt.Color;
import java.awt.image.BufferedImage;

import colorspace.RGBManipulator;

public class OtsuThreshold extends RGBManipulator
{
	
	private static int width = 0, height = 0;
	
	public static BufferedImage binarize(BufferedImage grayImage) 
	{
		int red, newPixel;
		width = grayImage.getWidth();
		height = grayImage.getHeight();
	    int threshold = otsuTreshold(grayImage);
	     
	    BufferedImage binarized = new BufferedImage(width, height, grayImage.getType());
 
	    for( int i = 0; i < width; i++ ) {
	    	for( int j = 0; j < height; j++ ) {
                Color color = new Color(grayImage.getRGB(i, j));
	    		red = color.getRed();
                //alpha = color.getAlpha();
                if(red > threshold) {
                    newPixel = 0;
                }
                else {
                    newPixel = 255;
                }
                binarized.setRGB(i, j, mixColor(newPixel, newPixel, newPixel)); 
 
            }
        }
	    return binarized;
	}
	 
	 private static int otsuTreshold(BufferedImage grayImage) {
		 
	        int[] histogram = imageHistogram(grayImage);
	        int total = width * height;
	 
	        float sum = 0;
	        for(int i=0; i<256; i++) sum += i * histogram[i];
	 
	        float sumB = 0;
	        int wB = 0;
	        int wF = 0;
	 
	        float varMax = 0;
	        int threshold = 0;
	 
	        for(int i=0 ; i<256 ; i++) {
	            wB += histogram[i];
	            if(wB == 0) continue;
	            wF = total - wB;
	 
	            if(wF == 0) break;
	 
	            sumB += (float) (i * histogram[i]);
	            float mB = sumB / wB;
	            float mF = (sum - sumB) / wF;
	 
	            float varBetween = (float) wB * (float) wF * (mB - mF) * (mB - mF);
	 
	            if(varBetween > varMax) {
	                varMax = varBetween;
	                threshold = i;
	            }
	            //if( i == 36 )
	            //	System.out.println( wF+" "+mF +" "+varBetween);
	        }
	        thresh = threshold;
	        return threshold;
	 }
	 
	 public static int[] hist = null;
	 public static int thresh = 0;
	 
	 public static int[] imageHistogram(BufferedImage grayImage) 
	 {
	        int[] histogram = new int[256];
	 
	        for( int i = 0; i < histogram.length; i++ ) histogram[i] = 0;
	 
	        for( int i = 0; i < width; i++ ) {
	            for( int j = 0; j < height; j++ ) {
	                int red = new Color(grayImage.getRGB (i, j)).getRed(); //doesn't matter if red, blue, or green; red=blue=green in grayscale
	                histogram[red]++;
	            }
	        }
	        hist = histogram;
	        return histogram;
	}
	
}