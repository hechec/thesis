package ui;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.Toolkit;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.Color;
import javax.swing.JButton;
import javax.swing.JLabel;

import custom.MainButton;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.io.File;
import java.io.IOException;

public class TotoBeeFrame extends JFrame
{

	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					TotoBeeFrame frame = new TotoBeeFrame();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public TotoBeeFrame() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 700, 500);
		setUndecorated(true);
		setLocationRelativeTo(null);
		contentPane = new JPanel();
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JPanel bgPanel = new JPanel() {
			@Override
		    public void paintComponent(Graphics g) {
		        super.paintComponent(g);
				Image image = null;
				try {                
					image = ImageIO.read(new File("src/images/bg.png"));
		        } catch (IOException ex) {
		        	System.out.println("Check background image.");
		        }
		        g.drawImage(image, 0, 0, null);          
		    }
		};
		bgPanel.setBounds(0, 0, 700, 500);
		contentPane.add(bgPanel);	
		bgPanel.setLayout(null);
		
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
		footerPane.setBounds(0, 450, 700, 50);
		bgPanel.add(footerPane);
		footerPane.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Copyright \u00A9 2013 Harvey Jake G. Opena");
		lblNewLabel.setForeground(Color.WHITE);
		lblNewLabel.setFont(new Font("Century Gothic", Font.BOLD, 13));
		lblNewLabel.setBounds(10, 11, 413, 28);
		footerPane.add(lblNewLabel);
		
		JButton exitButton = new MainButton("src/images/close.png", "src/images/closeHover.png");
		exitButton.setBorderPainted(false);
		exitButton.setBounds(661, 1, 38, 23);
		bgPanel.add(exitButton, 0);
		exitButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				System.exit(JFrame.EXIT_ON_CLOSE);
			}
		});
		
		JButton minimizeButton = new MainButton("src/images/minimize.png", "src/images/minimizeHover.png");
		minimizeButton.setBounds(621, 1, 38, 23);
		bgPanel.add(minimizeButton, 0);
		
		JButton trainButton = new MainButton("src/images/trainButton.png", "src/images/trainHover.png");
		trainButton.setBounds(55, 250, 140, 140);
		bgPanel.add(trainButton, 0);
		
		JButton classifyButton = new MainButton("src/images/classifyButton.png", "src/images/classifyHover.png");
		classifyButton.setBounds(205, 250, 140, 140);
		bgPanel.add(classifyButton, 0);
		
		JButton removeButton = new MainButton("src/images/removeBGButton.png", "src/images/removeBGHover.png");
		removeButton.setBounds(355, 250, 140, 140);
		bgPanel.add(removeButton, 0);
		
		JButton resizeButton = new MainButton("src/images/resizeButton.png", "src/images/resizeHover.png");
		resizeButton.setBounds(505, 250, 140, 140);
		bgPanel.add(resizeButton, 0);
		
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
		title.setBounds(51, 59, 568, 128);
		bgPanel.add(title, 0);
	
		bgPanel.addMouseMotionListener(new MouseMotionListener() {
			boolean start = false;
			Point original = null;
			
			@Override
			public void mouseMoved(MouseEvent e) {
				start = false;
			}
			
			@Override
			public void mouseDragged(MouseEvent e) {
				if(!start) {
					start = true;
					original = e.getPoint();
				}
				if(start) 
					dragFrame(original, e.getPoint());
			}
		});		
	}
	
	private void dragFrame(Point original, Point newPoint) {
		Point p = new Point(newPoint.x - original.x, newPoint.y - original.y);
		this.setLocation(this.getX()+p.x , this.getY() + p.y);
	}

}
