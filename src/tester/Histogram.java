package tester;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;


import javax.swing.JFrame;
import javax.swing.JPanel;


public class Histogram extends JFrame
{
	private int[] hist = null;
	private final int originX = 50;
	private final int originY = 300;
	
	private int startX = 0;
	private int startY = 300;
	JPanel panel;
	
	public Histogram(final int[] hist, final int threshold)	
	{
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
		
		getContentPane().setLayout(null);
		
		panel.setBounds(50, 50, 300, 350);
		getContentPane().setBackground(Color.WHITE);
		getContentPane().add(panel);
		setSize(400, 450);
		setVisible(true);
		//setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
}
