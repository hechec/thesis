package views.dialog;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

public class Entry extends JPanel
{
	private JLabel label1, label2, label3;
	
	private Color normalColor, hoverColor;
	
	private JLabel[] components = new JLabel[4];
	
	private String[] stages = {"Green", "Breakers", "Turning", "Pink", "Light Red", "Red"};
	
	public Entry(int number, String absolutePath, String filename, int expected, int actual)
	{
		setPreferredSize(new Dimension(500, 30));
		setOpaque(false);
		setLayout(null);
		setToolTipText(absolutePath);
		
		if(expected == actual) {
			normalColor = new Color(85, 176, 106);
		}
		else {
			normalColor = new Color(255, 51, 51);
		}
		
		JLabel label0 = new JLabel(""+number);
		label0.setFont(new Font("Century Gothic", Font.PLAIN, 14));
		label0.setForeground(Color.WHITE);
		label0.setBounds(0, 0, 80, 30);
		label0.setBackground(normalColor);
		label0.setHorizontalAlignment(SwingConstants.CENTER);
		label0.setOpaque(true);
		add(label0);
		
		label1 = new JLabel(filename);
		label1.setFont(new Font("Century Gothic", Font.PLAIN, 14));
		label1.setForeground(Color.WHITE);
		label1.setBounds(81, 0, 186, 30);
		label1.setBackground(normalColor);
		label1.setHorizontalAlignment(SwingConstants.CENTER);
		label1.setOpaque(true);
		add(label1);
		
		label2 = new JLabel(stages[expected]);
		label2.setFont(new Font("Century Gothic", Font.PLAIN, 14));
		label2.setForeground(Color.WHITE);
		label2.setBounds(268, 0, 95, 30);
		label2.setBackground(normalColor);
		label2.setOpaque(true);
		label2.setHorizontalAlignment(SwingConstants.CENTER);
		add(label2);
		
		label3 = new JLabel(stages[actual]);
		label3.setFont(new Font("Century Gothic", Font.PLAIN, 14));
		label3.setForeground(Color.WHITE);
		label3.setBounds(364, 0, 95, 30);
		label3.setBackground(normalColor);
		label3.setOpaque(true);
		label3.setHorizontalAlignment(SwingConstants.CENTER);
		add(label3);
		
		components[0] = label1;
		components[1] = label2;
		components[2] = label3;
		components[3] = label0;
		
		addMouseListener(new MouseListener() {
			@Override
			public void mouseReleased(MouseEvent e) {
				// TODO Auto-generated method stub
			}
			
			@Override
			public void mousePressed(MouseEvent e) {
				// TODO Auto-generated method stub
			}
			
			@Override
			public void mouseExited(MouseEvent e) {
				updateColor(normalColor);
			}

			@Override
			public void mouseEntered(MouseEvent e) {
				setCursor(new Cursor(Cursor.HAND_CURSOR));
				updateColor(Color.BLACK);
			}
			
			@Override
			public void mouseClicked(MouseEvent e) {
				// TODO Auto-generated method stub
			}
		});
		
	}
	
	private void updateColor(Color color) 
	{
		for( int i = 0; i < components.length; i++ ) 
			components[i].setBackground(color);
		updateUI();
	}
	
}
