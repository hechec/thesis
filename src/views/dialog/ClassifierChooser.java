package views.dialog;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import custom.MainButton;

import views.Frame;

public class ClassifierChooser extends JDialog 
{
	
	
	private final JPanel contentPanel = new JPanel();
	
	public ClassifierChooser() 
	{
		final Frame frame = Frame.getInstance();
		
		setSize(300, 180);
		setModal(true);
		setLocationRelativeTo(frame);
		setUndecorated(true);
		getContentPane().setBackground(Color.WHITE);
		getContentPane().setLayout(null);
		
		final URL bgUrl = getClass().getResource("/images/bg.png");
		JPanel panel = new JPanel() {
			@Override
		    public void paintComponent(Graphics g) {
		        super.paintComponent(g);
				Image image = null;
				try {                
					image = ImageIO.read(bgUrl);
		        } catch (IOException ex) {
		        	//System.out.println("Check background image.");
		        }
		        g.drawImage(image, 0, 0, null);          
		    }
		};
		panel.setBounds(5, 5, 290, 170);
		panel.setLayout(null);
		getContentPane().add(panel);
		
		JButton exitButton = new MainButton("/images/close.png", "/images/closeHover.png");
		exitButton.setBorderPainted(false);
		exitButton.setBounds(251, 1, 38, 23);
		panel.add(exitButton, 0);
		exitButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
			
		JLabel label = new JLabel("SELECT CLASSIFICATION MODE:");
		label.setBounds(20, 20, 220, 30);
		label.setForeground(Color.WHITE);
		label.setFont(new Font("Century Gothic", Font.PLAIN, 14));
		panel.add(label);
		
		JButton solo = new JButton("SOLO");
		solo.setFont(new Font(null, Font.PLAIN, 14));
		solo.setBounds(80, 60, 130, 40);
		panel.add(solo);
		
		JButton batch = new JButton("BATCH");
		batch.setBounds(80, 110, 130, 40);
		batch.setFont(new Font(null, Font.PLAIN, 14));
		panel.add(batch);
		
		solo.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				frame.setView(frame.getNextView(3));
				dispose();
			}
		});
		
		batch.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				frame.setView(frame.getNextView(2));
				dispose();
			}
		});
		
	}
}
