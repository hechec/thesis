package views;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.net.URL;

import javax.swing.JPanel;
import javax.swing.JWindow;

import custom.ImagePane;

public class SplashScreen extends JWindow 
{
	private URL bgPath = getClass().getResource("/images/splash.png");
	private URL beePath = getClass().getResource("/images/bee.png");
	private URL tomatoPath = getClass().getResource("/images/tomato.png");
	private URL totobeePath = getClass().getResource("/images/totobee.png");
	private JPanel bee, panel, tomato, totobee;
	
	private int ctr = 320;
	private Frame frame;
	
	public SplashScreen(Frame frame)
	{
		this.frame = frame;
		setLayout(null);
		
		panel = new ImagePane(bgPath);
		panel.setPreferredSize(new Dimension (451,310));
        panel.setBounds( 0, 0, 451, 310 );
        add(panel);
        
        int width = panel.getWidth();
    	int height = panel.getHeight();
    	Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
    	int x = (screen.width - width) / 2;
    	int y = (screen.height - height) / 2;
    	setBounds(x, y, 448, 310);
        
        totobee = new ImagePane(totobeePath);
        bee = new ImagePane(beePath);
        bee.setBounds(30, 250, 68, 56);
        add(bee, 0);

        tomato = new ImagePane(tomatoPath);
        tomato.setBounds(360, 249, 100, 92);
        add(tomato, 0);
       
        setVisible(true);
	}
	
	private boolean first = true;
	public void runSplash()
	{
		new Thread(new Runnable() {
			@Override
			public void run() {
				while(ctr > 0) {
					int x = bee.getX()+1;
					if(x > 280) {
						x = 280;
						if(first) {
							bee.setBounds(-100, -100, 0, 0);
							remove(tomato);
							add(totobee, 0);
							//panel.updateUI();
							totobee.setBounds(360, 220, 100, 140);
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
		frame.setVisible(true);
	}
	

}
