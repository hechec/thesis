package views.dialog;

import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

import custom.MainButton;

public class ResultViewerDialog extends JDialog 
{
	private static ResultViewerDialog instance;
	private JPanel contentPane;
	private JPanel thePanel; 

	private MainPage mainPage;
	
	public static void main(String[] args) {
		try {
			ResultViewerDialog dialog = new ResultViewerDialog();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public ResultViewerDialog() 
	{
		instance = this;
		setSize(550, 450);
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
		
		JPanel topPanel = new JPanel();
		topPanel.setBackground(Color.BLACK);
		topPanel.setBounds(0, 0, 550, 32);
		topPanel.setLayout(null);
		contentPane.add(topPanel);
		//topPanel.setOpaque(false);
		
		JLabel titleLabel = new JLabel("Results Viewer");
		titleLabel.setBounds(224, 4, 111, 23);
		titleLabel.setFont(new Font("Century Gothic", Font.PLAIN, 16));
		titleLabel.setForeground(Color.WHITE);
		topPanel.add(titleLabel);
		
		JButton exitButton = new MainButton("src/images/close.png", "src/images/closeHover.png");
		exitButton.setBorderPainted(false);
		exitButton.setBounds(511, 1, 38, 24);
		topPanel.add(exitButton, 0);
		exitButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				System.exit(1);
			}
		});
		
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
		
		JPanel extraPanel = new JPanel();
		extraPanel.setBounds(0, 31, 550, 3);
		//contentPane.add(extraPanel);
		
		thePanel = new JPanel();
		thePanel.setOpaque(false);
		thePanel.setBounds(0, 50, 550, 400);
		thePanel.setLayout(null);
		contentPane.add(thePanel);
	
		mainPage = MainPage.getInstance();
		mainPage.setBounds(0, 0, 550, 400);
		mainPage.setOpaque(false);
		thePanel.add(mainPage);
		mainPage.showTable();
	}
	
	private void dragFrame(Point original, Point newPoint) 
	{
		Point p = new Point(newPoint.x - original.x, newPoint.y - original.y);
		this.setLocation(this.getX()+p.x , this.getY() + p.y);
	}

	public static ResultViewerDialog getInstance() 
	{
		return instance;
	}
	
}
