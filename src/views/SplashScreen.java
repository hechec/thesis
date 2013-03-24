package views;

import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;
import javax.swing.JPanel;
import javax.swing.JWindow;
import javax.swing.UIManager;

import custom.ImagePane;

public class SplashScreen extends JWindow 
{
	private URL bgPath = getClass().getResource("/images/splash.png");
	private URL beePath = getClass().getResource("/images/bee.png");
	private URL tomatoPath = getClass().getResource("/images/tomato.png");
	private URL totobeePath = getClass().getResource("/images/totobee.png");
	private JPanel bee, panel, tomato, totobee;
	
	private int ctr = 270;
	
	public SplashScreen()
	{
		panel = new ImagePane(bgPath);
		panel.setPreferredSize(new Dimension (451,310));
        panel.setBounds( 0, 0, 451, 310 );
		
		setLayout(null);
		add(panel);
		int width = panel.getWidth();
    	int height = panel.getHeight();
    	Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
    	int x = (screen.width - width) / 2;
    	int y = (screen.height - height) / 2;
    	setBounds(x, y, 448, 310);
        setVisible(true);
        
        tomato = new ImagePane(tomatoPath);
        panel.add(tomato);
        tomato.setBounds(320, 200, 100, 92);
        
        totobee = new ImagePane(totobeePath);
        
        bee = new ImagePane(beePath);
        add(bee, 0);
        bee.setBounds(30, 215, 68, 56);
	}
	
	private boolean first = true;
	public void runSplash()
	{
		new Thread(new Runnable() {
			@Override
			public void run() {
				while(ctr > 0) {
					int x = bee.getX()+1;
					if(x > 250) {
						x = 250;
						if(first) {
							bee.setBounds(-100, -100, 0, 0);
							panel.remove(tomato);
							panel.add(totobee, 0);
							totobee.setBounds(320, 150, 100, 140);
							first = false;
							runApp();
						}
					}
					bee.setBounds(x, bee.getY(), 68, 56);
					ctr--;
					try {
						Thread.sleep(15);
					} catch (InterruptedException e) {}
				}
				dispose();
			}
		}).start();
        
	}
	
	private void runApp()
	{
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
					Frame frame = new Frame();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	

}
