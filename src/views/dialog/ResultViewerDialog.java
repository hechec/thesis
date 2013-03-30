package views.dialog;

import java.awt.*;
import java.awt.event.*;
import java.net.URL;
import javax.swing.*;

import views.Frame;
import core.Result;
import custom.ImagePane;
import custom.MainButton;
import dataset.Data;

public class ResultViewerDialog extends JDialog 
{
	private static ResultViewerDialog instance;
	private JPanel thePanel; 

	private MainPage mainPage;
	
	public ResultViewerDialog(Data testData, Result result) 
	{
		instance = this;
		
		final Frame frame = Frame.getInstance();
		setSize(550, 500);
		setUndecorated(true);
		//setLocation(0, 0);
		setLocationRelativeTo(frame);
		getContentPane().setBackground(Color.WHITE);
		getContentPane().setLayout(null);
		
		final URL bgUrl = getClass().getResource("/images/bg.png");
		JPanel panel = new ImagePane(bgUrl);
		panel.setBounds(5, 5, 540, 490);
		panel.setLayout(null);
		getContentPane().add(panel);
		
		
		JPanel topPanel = new JPanel();
		topPanel.setBackground(Color.BLACK);
		topPanel.setBounds(0, 0, 550, 32);
		topPanel.setLayout(null);
		panel.add(topPanel);
		//topPanel.setOpaque(false);
		
		JLabel titleLabel = new JLabel("Results Viewer");
		titleLabel.setBounds(224, 4, 111, 23);
		titleLabel.setFont(new Font("Century Gothic", Font.PLAIN, 16));
		titleLabel.setForeground(Color.WHITE);
		topPanel.add(titleLabel);
		
		JButton exitButton = new MainButton("/images/close.png", "/images/closeHover.png");
		exitButton.setBorderPainted(false);
		exitButton.setBounds(501, 1, 38, 24);
		topPanel.add(exitButton, 0);
		exitButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				close();
			}

		});
		
		panel.addMouseMotionListener(new MouseMotionListener() {
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
		panel.add(thePanel);
	
		mainPage = new MainPage();
		mainPage.setBounds(0, 0, 550, 400);
		mainPage.setOpaque(false);
		thePanel.add(mainPage);
		mainPage.showTable(testData, result);
	
	}
	
	private void dragFrame(Point original, Point newPoint) 
	{
		Point p = new Point(newPoint.x - original.x, newPoint.y - original.y);
		this.setLocation(this.getX()+p.x , this.getY() + p.y);
	}
	
	private void close() 
	{
		this.dispose();
	}

	public static ResultViewerDialog getInstance() 
	{
		return instance;
	}
	
}
