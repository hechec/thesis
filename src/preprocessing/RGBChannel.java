package preprocessing;

import java.awt.Color;
import java.awt.image.BufferedImage;

public class RGBChannel {
	
	public static BufferedImage toRGBChannel(BufferedImage image, String color) {
		
		BufferedImage convertedImage = new BufferedImage(image.getWidth(), image.getHeight(), BufferedImage.TYPE_INT_RGB);
		int rgb, colorValue;
		for( int i = 0; i < image.getHeight(); i++ ) 
			for( int j = 0; j < image.getWidth(); j++ ) {
				rgb = image.getRGB(j, i);
				 //A is 0xFF000000
		        //R is 0x00FF0000
		        //G is 0x0000FF00
		        //B is 0x000000FF        
				//colorValue = getChannelColor(color, rgb);
				rgb &= 0xFF0000FF;
				convertedImage.setRGB(j, i, rgb);
			}
		return convertedImage;
		
	}
	
	public static BufferedImage toRGChannel(BufferedImage image, String color) {
		
		BufferedImage convertedImage = new BufferedImage(image.getWidth(), image.getHeight(), BufferedImage.TYPE_INT_RGB);
		int rgb, colorValue;
		for( int i = 0; i < image.getHeight(); i++ ) 
			for( int j = 0; j < image.getWidth(); j++ ) {
				rgb = image.getRGB(j, i);
				if( (rgb & 0x00ffffff) != 0 ){
					colorValue = getChannelColor("RED", rgb) - getChannelColor("GREEN", rgb);
					convertedImage.setRGB(j, i, colorValue);
				}
				else {
					convertedImage.setRGB(j, i, rgb & 0x00ffffff);
				}
			}
		return convertedImage;
		
	}
	
	private static int getChannelColor(String color, int rgb) {
		Color c = new Color(rgb);
		
		if( color == "BLUE" )
			return c.getBlue();//return rgb & 0xFF;
		else if( color == "REDGREEN" )
			return ((rgb >> 16) & 0xFF) | ((rgb >> 8) & 0xFF);
		else if( color == "GREEN" )
			return c.getGreen();//return (rgb >> 8) & 0xFF;
		// red
		return (rgb >> 16) & 0x000000FF;//return (rgb >> 16) & 0xFF;
	}
	
	public static void main(String[] args) {
		
		BufferedImage img = new BufferedImage(10, 10, BufferedImage.TYPE_INT_RGB);
		img.setRGB(0, 0, 12321);
		System.out.print( getChannelColor("REDGREEN", img.getRGB(0, 0)) );
		Color c = new Color(img.getRGB(0, 0));
		int red = c.getRed();
		int green = c.getGreen();
		System.out.println(red|green);
		
		
	}

	public static int getRminusGavg(BufferedImage image) {
		int sum = 0, ctr = 0, rgb, red, green;
		for( int i = 0; i < image.getHeight(); i++ ) 
			for( int j = 0; j < image.getWidth(); j++ ) {
				rgb = image.getRGB(j, i);
				red =  (rgb >> 16) & 0xFF;
				green = (rgb >> 8) & 0xFF;
				if( (rgb & 0x00ffffff) != 0 ) {
					sum += ( red - green );
					ctr++;
				}
			}
		if( ctr == 0 )
			return 0;
		return sum/ctr;
	}

	public static BufferedImage ORredblue(BufferedImage blueBinary, BufferedImage redBinary) {
		BufferedImage ORed = new BufferedImage(blueBinary.getWidth(), blueBinary.getHeight(), BufferedImage.TYPE_INT_RGB);
		int ored;
		for( int i = 0; i < blueBinary.getHeight(); i++ ) 
			for( int j = 0; j < blueBinary.getWidth(); j++ ) {
				ored = blueBinary.getRGB(j, i) | redBinary.getRGB(j, i);
				ORed.setRGB(j, i, ored);
			}
		return ORed;
	}
	
}
