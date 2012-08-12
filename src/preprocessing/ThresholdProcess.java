package preprocessing;

import java.awt.image.BufferedImage;

public class ThresholdProcess {
	
	public static BufferedImage Threshold(BufferedImage image) {
		
		int requiredThresholdValue = getThresholdValue(image);
		int height = image.getHeight();
		int width = image.getWidth();
		BufferedImage finalThresholdImage = new BufferedImage(width,height,BufferedImage.TYPE_INT_RGB) ;
		
		int red = 0;
		int green = 0;
		int blue = 0;
		
		for (int x = 0; x < width; x++) {
			try {

				for (int y = 0; y < height; y++) {
					int rgb = image.getRGB(x, y);
					
					red =  (rgb >> 16) & 0xFF;
					green = (rgb >> 8) & 0xFF;
					blue = rgb & 0xFF;
					//System.out.println("Threshold : " + requiredThresholdValue +"   val: "+red+" "+green+" "+green);
						if((red+green+green)/3 < (int) (requiredThresholdValue)) {
							finalThresholdImage.setRGB(x,y,mixColor(0, 0,0));
						}
						else {
							finalThresholdImage.setRGB(x,y, mixColor(255, 255,255));
						}
					
				}
			} catch (Exception e) {
				 e.getMessage();
			}
		}
		return finalThresholdImage;
	}
	
	private static int getThresholdValue(BufferedImage image) {
		int rgb = 0, ctr = 0, sum = 0, red, green;
		for (int x = 0; x < image.getWidth(); x++) {
			for (int y = 0; y < image.getHeight(); y++) {
				rgb = image.getRGB(x, y);
				red =  (rgb >> 16) & 0xFF;
				green = (rgb >> 8) & 0xFF;
				sum += (red+green+green)/3;
				ctr++;
			}
		}
		return sum/ctr;
	}

	private static int mixColor(int red, int green, int blue) {
		return red<<16|green<<8|blue;
	}
	
}
