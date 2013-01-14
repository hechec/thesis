package views.dialog;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.util.Random;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;

import views.BatchPane;
import views.SoloPane;

public class MainPage extends JPanel
{
	private static MainPage instance = null;
	
	private ResultViewerDialog parent;
	
	public static MainPage getInstance() 
	{
		if(instance == null)
			instance = new MainPage();
		return instance;
	}
	
	public MainPage()
	{
		parent = ResultViewerDialog.getInstance();
		setLayout(null);	
		
		JPanel panel1 = new JPanel();
		panel1.setBounds(46, 22, 186, 30);
		panel1.setBackground(Color.BLACK);
		add(panel1);
		
		JLabel label1 = new JLabel("Input");
		label1.setFont(new Font("Century Gothic", Font.PLAIN, 16));
		label1.setForeground(Color.WHITE);
		panel1.add(label1);
		
		JPanel panel2 = new JPanel();
		panel2.setBounds(233, 22, 95, 30);
		panel2.setBackground(Color.BLACK);
		add(panel2);
		
		JLabel label2 = new JLabel("Expected");
		label2.setFont(new Font("Century Gothic", Font.PLAIN, 16));
		label2.setForeground(Color.WHITE);
		panel2.add(label2);
		
		JPanel panel3 = new JPanel();
		panel3.setBounds(329, 22, 95, 30);
		panel3.setBackground(Color.BLACK);
		add(panel3);
		
		JLabel label3 = new JLabel("Actual");
		label3.setFont(new Font("Century Gothic", Font.PLAIN, 16));
		label3.setForeground(Color.WHITE);
		panel3.add(label3);
		
		JPanel linePanel = new JPanel();
		linePanel.setBounds(46, 52, 400, 2);
		add(linePanel);
		
	}
	
	public void showTable() 
	{
		
		JPanel panel = new JPanel();
		panel.setOpaque(false);
		panel.setLayout(null);
		panel.setBounds(46, 55, 400, 310);
		add(panel);
		
		Random random = new Random();
		for( int i = 0, y = 0; i < 10; i++, y += 31 ) {
			boolean b = random.nextBoolean();
			Entry e1 = new Entry("", "tomato1.jpg", "TURNING", b?"TURNING":"BREAKER");
			e1.setBounds(0, y, 400, 30);
			//e1.setLocation(0, y);
			
			//JLabel e1 = new JLabel("Harvey Jake Opena");
			//e1.setSize(400, 30);
			panel.add(e1);
		}
		
		
		
	}
	
}
