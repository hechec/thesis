import java.awt.EventQueue;

import javax.swing.UIManager;

import views.Frame;
import views.SplashScreen;

public class Main 
{
	public static void main(String[] args) 
	{
		
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
					Frame frame = new Frame();

					SplashScreen splash = new SplashScreen(frame);
					splash.runSplash();
					
				} catch (Exception e) {}
			}
		});
		
	}
}
