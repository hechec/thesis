package views.dialog;

import imageprocessing.ImageProcessor;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JLabel;

public class ImageTooltip extends JDialog 
{  
	  
	private BufferedImage image;
	
	public ImageTooltip(String pathname, int x,int y)  
	{  
		setUndecorated(true);
		setLayout(null);
		
		try {
			image = ImageIO.read(new File(pathname));
		} catch (IOException e) {
			//e.printStackTrace();
		}
		image = ImageProcessor.getInstance().resizeImage(image, 200, 200);
			
		JLabel label = new JLabel(new ImageIcon(image));
		label.setBounds(0, 0, 200, 200);
		add(label);
		
		setLocation(x, y);  
		setSize(200, 200);  
		
		setAlwaysOnTop(true);
	}  

}
