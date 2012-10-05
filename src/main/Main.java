package main;

import java.awt.EventQueue;
import java.awt.Font;

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
					setUIFont (new javax.swing.plaf.FontUIResource("Calibri", Font.PLAIN, 14));
					UIManager.setLookAndFeel("com.jtattoo.plaf.texture.TextureLookAndFeel");
					AppFrame frame = new AppFrame();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	private static void setUIFont(javax.swing.plaf.FontUIResource f) {
	    java.util.Enumeration keys = UIManager.getDefaults().keys();
	    while (keys.hasMoreElements())
	    {
	        Object key = keys.nextElement();
	        Object value = UIManager.get(key);
	        if (value instanceof javax.swing.plaf.FontUIResource)
	        {
	            UIManager.put(key, f);
	        }
	    }
	}

}
