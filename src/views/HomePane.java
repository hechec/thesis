package views;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import custom.MainButton;

public class HomePane extends JPanel
{
	private static HomePane instance = null;
	private Frame frame;
	
	public static HomePane getInstance() 
	{
		if(instance == null)
			instance = new HomePane();
		return instance;
	}
	
	public HomePane() 
	{
		frame = Frame.getInstance();
		setLayout(null);
		
		JButton trainButton = new MainButton("src/images/trainButton.png", "src/images/trainHover.png");
		trainButton.setBounds(130, 117, 140, 140);
		this.add(trainButton, 0);
		trainButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				frame.setView(frame.getNextView(1));
			}
		});
		
		JButton soloButton = new MainButton("src/images/classifyButton.png", "src/images/classifyHover.png");
		soloButton.setBounds(285, 117, 140, 140);
		this.add(soloButton, 0);
		soloButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				frame.setView(frame.getNextView(3));
			}
		});
		
		JButton batchButton = new MainButton("src/images/batchButton.png", "src/images/batchHover.png");
		batchButton.setBounds(440, 117, 140, 140);
		this.add(batchButton, 0);
		batchButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				frame.setView(frame.getNextView(2));
			}
		});
		
		JButton removeButton = new MainButton("src/images/removeBGButton.png", "src/images/removeBGHover.png");
		removeButton.setBounds(285, 272, 140, 140);
		this.add(removeButton, 0);
		removeButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				frame.setView(frame.getNextView(4));
			}
		});
		
		JButton resizeButton = new MainButton("src/images/resizeButton.png", "src/images/resizeHover.png");
		resizeButton.setBounds(130, 272, 140, 140);
		this.add(resizeButton, 0);
		resizeButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
			}
		});
		
		JButton randomizeButton = new MainButton("src/images/randomizeButton.png", "src/images/randomizeHover.png");
		randomizeButton.setBounds(440, 272, 140, 140);
		this.add(randomizeButton, 0);
		randomizeButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				//frame.setView(frame.getNextView(3));
			}
		});
		
		
		JPanel title = new JPanel() {
			@Override
		    public void paintComponent(Graphics g) {
		        Graphics2D g2 = (Graphics2D) g;
				Image image = null;
				try {                
					image = ImageIO.read(new File("src/images/title_logo.png"));
		        } catch (IOException ex) {
		        	System.out.println("Check logo image.");
		        }
		        g2.drawImage(image, 0, 0, null);          
		    }
		};
		title.setBounds(61, 7, 488, 90);
		this.add(title, 0);
		
		JPanel footerPane = new JPanel() {
			@Override
		    public void paintComponent(Graphics g) {
				 Graphics2D g2 = (Graphics2D) g;
				Image image = null;
				try {                
					image = ImageIO.read(new File("src/images/footer.png"));
		        } catch (IOException ex) {
		        	System.out.println("Check footer image.");
		        }
		        g2.drawImage(image, 0, 0, null);          
		    }
		};
		footerPane.setBackground(Color.DARK_GRAY);
		footerPane.setBounds(0, 425, 700, 50);
		this.add(footerPane);
		footerPane.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Copyright \u00A9 2013 Harvey Jake G. Opena");
		lblNewLabel.setForeground(Color.WHITE);
		lblNewLabel.setFont(new Font("Century Gothic", Font.BOLD, 13));
		lblNewLabel.setBounds(10, 11, 413, 28);
		footerPane.add(lblNewLabel);
		

	}
	
}
