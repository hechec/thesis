package views;

import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.net.URL;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import utilities.GlobalVariables;
import custom.ImagePane;
import custom.MainButton;

public class Frame extends JFrame 
{
	private static Frame instance = null;
	
	private JPanel contentPane;
	private JPanel thePanel;
	
	private JPanel[] panels = new JPanel[8];
	
	private URL bgPath = getClass().getResource("/images/bg.png");

	public Frame() 
	{
		instance = this;
		setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/images/icon.png")));
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(700, 550);
		setUndecorated(true);
		setLocationRelativeTo(null);
		contentPane = new ImagePane(bgPath);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		thePanel = new JPanel();
		thePanel.setOpaque(false);
		thePanel.setBounds(0, 26, 700, 525);
		thePanel.setLayout(null);
		contentPane.add(thePanel);
		
		JButton exitButton = new MainButton("/images/close.png", "/images/closeHover.png");
		exitButton.setBorderPainted(false);
		exitButton.setBounds(661, 1, 38, 23);
		contentPane.add(exitButton, 0);
		exitButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				System.exit(JFrame.EXIT_ON_CLOSE);
			}
		});
		JButton minimizeButton = new MainButton("/images/minimize.png", "/images/minimizeHover.png");
		minimizeButton.setBounds(621, 1, 38, 23);
		contentPane.add(minimizeButton, 0);
		minimizeButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				setState(JFrame.ICONIFIED);
			}
		});
		
		panels[0] = HomePane.getInstance();
		panels[0].setBounds(0, 0, 700, 525);
		panels[0].setOpaque(false);
		thePanel.add(panels[0]);	
		
		panels[2] = BatchPane.getInstance();
		panels[2].setBounds(0, 0, 700, 525);
		panels[2].setOpaque(false);
		
		panels[3] = SoloPane.getInstance();
		panels[3].setBounds(0, 0, 700, 525);
		panels[3].setOpaque(false);
		
		panels[4] = BRemoverPane.getInstance(); 
		panels[4].setBounds(0, 0, 700, 525);
		panels[4].setOpaque(false);		
		
		panels[5] = RandomizerPane.getInstance(); 
		panels[5].setBounds(0, 0, 700, 525);
		panels[5].setOpaque(false);
		
		panels[6] = ExperimentPane.getInstance();
		panels[6].setBounds(0, 0, 700, 525);
		panels[6].setOpaque(false);
		
		panels[7] = ResizerPane.getInstance();
		panels[7].setBounds(0, 0, 700, 525);
		panels[7].setOpaque(false);
		
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
	
	private void dragFrame(Point original, Point newPoint) 
	{
		Point p = new Point(newPoint.x - original.x, newPoint.y - original.y);
		this.setLocation(this.getX()+p.x , this.getY() + p.y);
	}
	
	public void setView(JPanel currentPanel)
	{
		thePanel.removeAll();
		thePanel.add(currentPanel);
		thePanel.updateUI();
		if(currentPanel instanceof BatchPane || currentPanel instanceof SoloPane) 
			GlobalVariables.setMode(GlobalVariables.STANDARD_RUN);
	}
	
	public JPanel getNextView(int index)
	{
		return panels[index];
	}

	public static Frame getInstance() {
		return instance;
	}
	
}
