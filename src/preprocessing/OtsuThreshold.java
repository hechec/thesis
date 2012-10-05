package preprocessing;

import java.awt.Color;
import java.awt.image.BufferedImage;

public class OtsuThreshold {
	
	 public static BufferedImage binarize(BufferedImage grayImage) {
		 
	        int red;
	        int newPixel;
	 
	        int threshold = otsuTreshold(grayImage);
	 
	        BufferedImage binarized = new BufferedImage(grayImage.getWidth(), grayImage.getHeight(), grayImage.getType());
	 
	        for(int i=0; i<grayImage.getWidth(); i++) {
	            for(int j=0; j<grayImage.getHeight(); j++) {
	 
	                // Get pixels
	                red = new Color(grayImage.getRGB(i, j)).getRed();
	                int alpha = new Color(grayImage.getRGB(i, j)).getAlpha();
	                if(red > threshold) {
	                    newPixel = 0;
	                }
	                else {
	                    newPixel = 255;
	                }
	                newPixel = colorToRGB(alpha, newPixel, newPixel, newPixel);
	                binarized.setRGB(i, j, newPixel); 
	 
	            }
	        }
	        
	        return binarized;
	 }
	 
	 private static int otsuTreshold(BufferedImage grayImage) {
		 
	        int[] histogram = imageHistogram(grayImage);
	        int total = grayImage.getHeight() * grayImage.getWidth();
	 
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
	        }
	 
	        return threshold;
	 
	 }
	 
	 public static int[] imageHistogram(BufferedImage grayImage) {
		 
	        int[] histogram = new int[256];
	 
	        for(int i=0; i<histogram.length; i++) histogram[i] = 0;
	 
	        for(int i=0; i<grayImage.getWidth(); i++) {
	            for(int j=0; j<grayImage.getHeight(); j++) {
	                int red = new Color(grayImage.getRGB (i, j)).getRed();
	                histogram[red]++;
	            }
	        }
	 
	        return histogram;
	 
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
