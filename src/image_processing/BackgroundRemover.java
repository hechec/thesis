package image_processing;

import java.awt.image.BufferedImage;

import colorspace.RGBChannel;

public class BackgroundRemover 
{
	
	private static BackgroundRemover instance = null;
	
	private BufferedImage blueChannel; 	// blue channel
	private BufferedImage filteredBlue; // blue filtered
	private BufferedImage grayscale; 	// grayscale
	private BufferedImage binaryMask;	// mask
	private BufferedImage segmented;	// output
	
	
	public BackgroundRemover() 
	{
	}

	public static BackgroundRemover getInstance() 
	{
		if( instance == null )
			instance = new BackgroundRemover();
		return instance;
	}
	
	public BufferedImage removeBackground(BufferedImage image) 
	{	
		blueChannel = RGBChannel.toRGBChannel(image, RGBChannel.BLUE);
		//filteredBlue = MeanFilter.filter(blueChannel);
		grayscale = GrayScale.toGray(blueChannel);
		binaryMask = OtsuThreshold.binarize(grayscale);
		segmented = Masking.extract(image, binaryMask);
		return segmented;
	}

	public BufferedImage getBlueChannel() 
	{
		return blueChannel;
	}
	
	public BufferedImage getFilteredBlue() 
	{
		return filteredBlue;
	}
	
	public BufferedImage getGrayscale() 
	{
		return grayscale;
	}
	
	public BufferedImage getBinaryMask() 
	{
		return binaryMask;
	}
	
	public BufferedImage getSegmented()
	{
		return segmented;
	}

}
