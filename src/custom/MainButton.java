package custom;

import java.awt.Cursor;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;
import javax.swing.JButton;

public class MainButton extends JButton 
{
	private Image normal = null;
	private Image hover = null;
	private boolean mouseEntered = false;
	
	public MainButton(String source, String source2) 
	{
		URL sourceUrl = getClass().getResource(source);
		URL sourceUrl2 = getClass().getResource(source2);
		setBorderPainted(false);		
		try {                
			normal = ImageIO.read(sourceUrl);
			hover = ImageIO.read(sourceUrl2);
        } catch (IOException ex) {
        	//System.out.println("Check main button image source.");
        }
		
		addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				mouseEntered = false;
				repaint();
			}
		});
		
		addMouseListener(new MouseAdapter() {
			@Override
			public void mouseExited(MouseEvent e) {
				mouseEntered = false;
				repaint();
			}
			
			@Override
			public void mouseEntered(MouseEvent e) {
				mouseEntered = true;
				repaint();
			}
		});
	}
	
	@Override
	public void paintComponent(Graphics g) 
	{
		super.paintComponents(g);
		Graphics2D g2 = (Graphics2D) g;
		if(mouseEntered)  {
			setCursor(new Cursor(Cursor.HAND_CURSOR));
			g2.drawImage(hover, 0, 0, null); 
		}
		else 
			g2.drawImage(normal, 0, 0, null); 
	}

	public void updateIcons(String source, String source2) {
		URL sourceUrl = getClass().getResource(source);
		URL sourceUrl2 = getClass().getResource(source2);
		try {                
			normal = ImageIO.read(sourceUrl);
			hover = ImageIO.read(sourceUrl2);
        } catch (IOException ex) {
        	//System.out.println("Check main button image source.");
        }		
		repaint();
	}

}
