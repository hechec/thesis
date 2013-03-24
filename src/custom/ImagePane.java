package custom;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

public class ImagePane extends JPanel 
{
	private URL url;
	
	public ImagePane(URL url) 
	{
		this.url = url;
	}
	
	@Override
    public void paintComponent(Graphics g) {
		Graphics2D g2 = (Graphics2D) g;
		Image image = null;
		try {                
			image = ImageIO.read(url);
        } catch (IOException ex) {
        }
        g2.drawImage(image, 0, 0, null);   
    }
	
}
