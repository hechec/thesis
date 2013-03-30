package views;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Desktop;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import views.dialog.AboutDialog;
import views.dialog.ClassifierChooser;
import views.dialog.DatasetLocationChooser;
import views.optionpane.MessageDialog;

import custom.ImagePane;
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
		
		JButton trainButton = new MainButton("/images/trainButton.png", "/images/trainHover.png");
		trainButton.setBounds(130, 135, 140, 140);
		this.add(trainButton, 0);
		trainButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				frame.setView(frame.getNextView(6));
			}
		});
		
		JButton soloButton = new MainButton("/images/classifyButton.png", "/images/classifyHover.png");
		soloButton.setBounds(285, 135, 140, 140);
		this.add(soloButton, 0);
		soloButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				new ClassifierChooser().setVisible(true);	
				//frame.setView(frame.getNextView(3));
			}
		});
		
		JButton helpButton = new MainButton("/images/helpButton.png", "/images/helpHover.png");
		helpButton.setBounds(440, 290, 140, 140);
		this.add(helpButton, 0);
		helpButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (Desktop.isDesktopSupported()) {
				    try {
				    	File f = new java.io.File(Frame.class.getProtectionDomain().getCodeSource().getLocation().toURI());
						f = new java.io.File(f.getParent()+"/User's Manual.pdf");
						if(f.exists())
							Desktop.getDesktop().open(f);
						else 
							new MessageDialog("Ooops. The user's manual does not exist.").setVisible(true);
				    } catch (URISyntaxException e1) {} 
				      catch (IOException e1) {}
				}
			}
		});
		
		JButton removeButton = new MainButton("/images/removeBGButton.png", "/images/removeBGHover.png");
		//removeButton.setBounds(285, 272, 140, 140);
		removeButton.setBounds(440, 135, 140, 140);
		this.add(removeButton, 0);
		removeButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				frame.setView(frame.getNextView(4));
			}
		});
		
		JButton resizeButton = new MainButton("/images/resizeButton.png", "/images/resizeHover.png");
		resizeButton.setBounds(130, 290, 140, 140);
		this.add(resizeButton, 0);
		resizeButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				frame.setView(frame.getNextView(7));
			}
		});
		
		JButton randomizeButton = new MainButton("/images/randomizeButton.png", "/images/randomizeHover.png");
		//randomizeButton.setBounds(440, 272, 140, 140);
		randomizeButton.setBounds(285, 290, 140, 140);
		this.add(randomizeButton, 0);
		randomizeButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				frame.setView(frame.getNextView(5));
			}
		});
		
		final URL logoUrl = getClass().getResource("/images/title_logo.png");
		
		JPanel title = new ImagePane(logoUrl);
		title.setBounds(80, 10, 600, 150);
		this.add(title, 0);
		
		final URL footerUrl = getClass().getResource("/images/footer.png");
		
		JPanel footerPane = new ImagePane(footerUrl);
		footerPane.setBackground(Color.DARK_GRAY);
		footerPane.setBounds(0, 475, 700, 50);
		this.add(footerPane);
		footerPane.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Copyright \u00A9 2013 Harvey Jake G. Opena");
		lblNewLabel.setForeground(Color.WHITE);
		lblNewLabel.setFont(new Font("Century Gothic", Font.BOLD, 13));
		lblNewLabel.setBounds(10, 11, 413, 28);
		//footerPane.add(lblNewLabel);
		
		JButton aboutButton = new JButton("About");
		aboutButton.setForeground(Color.WHITE);
		aboutButton.setFont(new Font("Century Gothic", Font.BOLD, 13));
		aboutButton.setBounds(10, 11, 70, 30);
		aboutButton.setBorderPainted(false);
		aboutButton.setBorder(null);
		aboutButton.setContentAreaFilled(false);
		footerPane.add(aboutButton);
		aboutButton.addMouseListener(new MouseAdapter() {
			public void mouseExited(MouseEvent e) {
				setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
			}
			@Override
			public void mouseEntered(MouseEvent e) {
				setCursor(new Cursor(Cursor.HAND_CURSOR));
			}
		});
		aboutButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				new AboutDialog().setVisible(true);
			}
		});
	
		JButton datasetButton = new JButton("Dataset");
		datasetButton.setForeground(Color.WHITE);
		datasetButton.setFont(new Font("Century Gothic", Font.BOLD, 13));
		datasetButton.setBounds(80, 11, 70, 30);
		datasetButton.setBorderPainted(false);
		datasetButton.setBorder(null);
		datasetButton.setContentAreaFilled(false);
		footerPane.add(datasetButton);
		datasetButton.addMouseListener(new MouseAdapter() {
			public void mouseExited(MouseEvent e) {
				setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
			}
			@Override
			public void mouseEntered(MouseEvent e) {
				setCursor(new Cursor(Cursor.HAND_CURSOR));
			}
		});
		datasetButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				new DatasetLocationChooser("").setVisible(true);
			}
		});
	}
	
}
