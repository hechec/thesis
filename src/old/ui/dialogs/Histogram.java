package old.ui.dialogs;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import javax.swing.JDialog;
import javax.swing.JPanel;

public class Histogram extends JDialog {

	private final int originX = 50;
	private final int originY = 300;
	
	private JPanel panel;
	
	private JPanel contentPane;
	
	public Histogram()
	{
		contentPane = new JPanel();
		setContentPane(contentPane);
		contentPane.setLayout(null);
		contentPane.setBackground(Color.WHITE);
		setSize(400, 450);
	}
	
	public void create(final int[] hist, final int threshold)	
	{
		contentPane.removeAll();
		contentPane.updateUI();
		
		panel = new JPanel() {
			public void paintComponent(Graphics g)
			{
				Graphics2D g2 = (Graphics2D)g;
				g2.setColor(Color.BLACK);
				g2.drawLine(originX, 0, originX, originY);
				g2.drawLine(originX, originY, 250, originY);
				
				g2.setStroke(new BasicStroke(2));
				g2.setColor(Color.BLUE);
				for( int i = 0; i < hist.length; i++ ) {
					if( hist[i] == 0 )
						continue;
					g2.drawLine(originX+(i+1)*2, originY-1, originX+(i+1)*2, originY-(hist[i]/10)-1);
					//System.out.println(i);
				}
				g2.setColor(Color.RED);
				g2.setStroke(new BasicStroke(2));
				g2.drawLine(originX+threshold*2, 0, originX+threshold*2, originY);
				g2.drawString("T = "+threshold, threshold*2, originY/2);
				
				g2.setStroke(new BasicStroke(1));
				g2.setColor(Color.BLACK);
				for( int i = 20; i < 200; i+=20 )
				{				
					g2.drawLine(originX+i, originY-3, originX+i, originY+3);
					g2.drawString(i/2+"", originX+i-7, originY+15);
				}
				
				for( int i = 400; i <= 3000; i += 400 ) {
					g2.drawLine(originX-3, originY-i/10, originX+3, originY-i/10);
					g2.drawString(i+"", originX-(i < 1000? 27:35), originY-i/10+3);
				}
			}
		};
		
		panel.setBounds(50, 50, 300, 350);
		contentPane.add(panel);
		setVisible(true);
		//setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

}
