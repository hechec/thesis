package main;

import java.awt.EventQueue;

import javax.swing.UIManager;

import org.jvnet.substance.SubstanceLookAndFeel;
import org.jvnet.substance.painter.GlassGradientPainter;
import org.jvnet.substance.skin.SubstanceAutumnLookAndFeel;
import org.jvnet.substance.skin.SubstanceBusinessBlackSteelLookAndFeel;
import org.jvnet.substance.skin.SubstanceBusinessBlueSteelLookAndFeel;
import org.jvnet.substance.skin.SubstanceGreenMagicLookAndFeel;
import org.jvnet.substance.skin.SubstanceRavenGraphiteLookAndFeel;
import org.jvnet.substance.title.Glass3DTitlePainter;
import org.jvnet.substance.title.RandomCubesTitlePainter;

import ui.AppFrame;

public class Main {

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					//UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
					UIManager.setLookAndFeel(new SubstanceBusinessBlueSteelLookAndFeel());
                    javax.swing.JDialog.setDefaultLookAndFeelDecorated(true);
                    javax.swing.JFrame.setDefaultLookAndFeelDecorated(true);
                    //SubstanceLookAndFeel.setCurrentTitlePainter(new RandomCubesTitlePainter());
                    SubstanceLookAndFeel.setCurrentGradientPainter(new GlassGradientPainter());
					AppFrame frame = new AppFrame();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

}
