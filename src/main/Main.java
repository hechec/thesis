package main;

import java.awt.EventQueue;

import javax.swing.UIManager;

import org.jvnet.substance.SubstanceLookAndFeel;
import org.jvnet.substance.painter.GlassGradientPainter;
import org.jvnet.substance.plugin.SubstanceWatermarkPlugin;
import org.jvnet.substance.skin.SubstanceAutumnLookAndFeel;
import org.jvnet.substance.skin.SubstanceBusinessBlackSteelLookAndFeel;
import org.jvnet.substance.skin.SubstanceBusinessBlueSteelLookAndFeel;
import org.jvnet.substance.skin.SubstanceGreenMagicLookAndFeel;
import org.jvnet.substance.skin.SubstanceRavenGraphiteGlassLookAndFeel;
import org.jvnet.substance.skin.SubstanceRavenGraphiteLookAndFeel;
import org.jvnet.substance.title.ArcHeaderPainter;
import org.jvnet.substance.title.Glass3DTitlePainter;
import org.jvnet.substance.title.RandomCubesTitlePainter;
import org.jvnet.substance.watermark.SubstanceKatakanaWatermark;
import org.jvnet.substance.watermark.SubstanceWatermark;

import com.jtattoo.plaf.JTattooUtilities;

import ui.AppFrame;

public class Main {

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					/*UIManager.setLookAndFeel(new SubstanceGreenMagicLookAndFeel());
                    javax.swing.JDialog.setDefaultLookAndFeelDecorated(true);
                    javax.swing.JFrame.setDefaultLookAndFeelDecorated(true);
                    //SubstanceLookAndFeel.setCurrentTitlePainter(new RandomCubesTitlePainter());
                    SubstanceLookAndFeel.setCurrentGradientPainter(new GlassGradientPainter());
                    SubstanceLookAndFeel.setCurrentTitlePainter(new ArcHeaderPainter());
                    SubstanceLookAndFeel.setCurrentWatermark(new SubstanceKatakanaWatermark());
					*/
					UIManager.setLookAndFeel("com.jtattoo.plaf.texture.TextureLookAndFeel");
					AppFrame frame = new AppFrame();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

}
