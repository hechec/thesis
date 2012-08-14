package preprocessing;

import java.awt.AlphaComposite;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;

public class ImageManager 
{
	private BufferedImage original;
	private BufferedImage resized;
	
	private static final int scaledWidth = 100, scaledHeight = 100;
	
	/**
	 *	constructor 
	 */
	public ImageManager(BufferedImage original) 
	{
		this.original = original;
	}
	
	public BufferedImage getResizedImage()
	{
		return resizeImage();
	}
	
	/**
	 * resize an image from AxB to NxN
	 * 
	 * @return resized BufferedImage
	 */
	private BufferedImage resizeImage()
	{
		BufferedImage resizedImage = new BufferedImage(scaledWidth, scaledHeight, original.getType());
		Graphics2D g = resizedImage.createGraphics();
		g.drawImage(original, 0, 0, scaledWidth, scaledHeight, null);
		g.setComposite(AlphaComposite.Src);
		g.dispose();	
		
		return resizedImage;
	}
	
}
