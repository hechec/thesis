package views;

import java.awt.EventQueue;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import custom.MainButton;

public class Frame extends JFrame 
{
	private static Frame instance = null;
	
	private JPanel contentPane;
	private JPanel thePanel;
	
	private JPanel[] panels = new JPanel[3];
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Frame frame = new Frame();
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
	public Frame() 
	{
		instance = this;
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(700, 500);
		setUndecorated(true);
		setLocationRelativeTo(null);
		contentPane = new JPanel() {
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
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		thePanel = new JPanel();
		thePanel.setOpaque(false);
		thePanel.setBounds(0, 26, 700, 475);
		thePanel.setLayout(null);
		contentPane.add(thePanel);
		
		JButton exitButton = new MainButton("src/images/close.png", "src/images/closeHover.png");
		exitButton.setBorderPainted(false);
		exitButton.setBounds(661, 1, 38, 23);
		contentPane.add(exitButton, 0);
		exitButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				System.exit(JFrame.EXIT_ON_CLOSE);
			}
		});
		JButton minimizeButton = new MainButton("src/images/minimize.png", "src/images/minimizeHover.png");
		minimizeButton.setBounds(621, 1, 38, 23);
		contentPane.add(minimizeButton, 0);
		minimizeButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				setState(JFrame.ICONIFIED);
			}
		});
		
		panels[0] = HomePane.getInstance();
		panels[0].setBounds(0, 0, 700, 475);
		panels[0].setOpaque(false);
		thePanel.add(panels[0]);	
		
		panels[1] = TrainPane.getInstance();
		panels[1].setBounds(0, 0, 700, 475);
		panels[1].setOpaque(false);
		
		panels[2] = ClassificationPane.getInstance();
		panels[2].setBounds(0, 0, 700, 475);
		panels[2].setOpaque(false);
		
		contentPane.addMouseMotionListener(new MouseMotionListener() {
			boolean startDrag = false;
			Point original = null;
			
			@Override
			public void mouseMoved(MouseEvent e) {
				startDrag = false;
			}
			
			@Override
			public void mouseDragged(MouseEvent e) {
				if(!startDrag) {
					startDrag = true;
					original = e.getPoint();
				}
				if(startDrag) 
					dragFrame(original, e.getPoint());
			}
		});		
	}
	
	private void dragFrame(Point original, Point newPoint) {
		Point p = new Point(newPoint.x - original.x, newPoint.y - original.y);
		this.setLocation(this.getX()+p.x , this.getY() + p.y);
	}
	
	public void setView(JPanel currentPanel)
	{
		thePanel.removeAll();
		thePanel.add(currentPanel);
		thePanel.updateUI();
	}
	
	public JPanel getNextView(int index)
	{
		return panels[index];
	}

	public static Frame getInstance() {
		return instance;
	}
	
}