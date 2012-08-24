package ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.KeyStroke;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;

import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.swing.JLabel;

public class Frame extends JFrame {

	private JPanel contentPane;
	private JMenuBar menuBar;
	private JMenu fileMenu;
	private JMenuItem openAction;
	private JMenuItem exitAction;
	private JFileChooser fc;
	private JLabel inputLabel;

	private IPanel ipanel;
	private JPanel outputPane;
	private JLabel outputLabel;
	
	//tabs
	private JPanel testingTab;
	private JPanel trainingTab;
	
	private ImageHandler iHandler = new ImageHandler();

	/**
	 * Create the frame.
	 */
	public Frame() {
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(700, 600);
		setLocationRelativeTo(null);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		initMenuBar();
		
		testingTab = new JPanel();
		trainingTab = new JPanel();
		
		JTabbedPane tabbedPane = new JTabbedPane();
		tabbedPane.add("Testing", testingTab);
		tabbedPane.add("Training", trainingTab);
		contentPane.add(tabbedPane, BorderLayout.CENTER);
		
		testingTab.setLayout(new BorderLayout());
		
		JPanel leftPane = new JPanel();
		testingTab.add(leftPane, BorderLayout.CENTER);
		//leftPane.setPreferredSize(new Dimension(200, 500));
		leftPane.setLayout(new BorderLayout(0, 0));
		
		JPanel inputPane = new JPanel();
		inputPane.setBorder(new TitledBorder(null, "Input", TitledBorder.CENTER, TitledBorder.TOP, null, null));
		inputPane.setPreferredSize(new Dimension(200, 208));
		leftPane.add(inputPane, BorderLayout.NORTH);
		
		inputLabel = new JLabel("");
		inputLabel.setPreferredSize(new Dimension(170, 170));
		inputLabel.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1));
		inputPane.add(inputLabel);
		
		outputPane = new JPanel();
		outputPane.setPreferredSize(new Dimension(200, 208));
		outputPane.setBorder(new TitledBorder(null, "Extracted", TitledBorder.CENTER, TitledBorder.TOP, null, null));
		leftPane.add(outputPane, BorderLayout.SOUTH);
		
		outputLabel = new JLabel("");
		outputLabel.setPreferredSize(new Dimension(64, 64));
		outputLabel.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1));
		outputPane.add(outputLabel);
		
		ipanel = new IPanel();
		
		//testingTab.add(ipanel, BorderLayout.EAST);
		FeaturesPane fp = new FeaturesPane();
		testingTab.add(fp, BorderLayout.EAST);
		
	}

	private void initMenuBar() {
		menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		
		fileMenu = new JMenu("File");
		menuBar.add(fileMenu);
		openAction =  new JMenuItem("Open");		
		openAction.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				selectImage();
			}

		});
		
		exitAction =  new JMenuItem("Exit", KeyEvent.VK_T);
		KeyStroke ctrlXKeyStroke = KeyStroke.getKeyStroke("control Q");
		exitAction.setAccelerator(ctrlXKeyStroke);
		exitAction.addActionListener(new ActionListener() {	
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				System.exit(0);				
			}
		});

		fileMenu.add(openAction);
		fileMenu.add(exitAction);	
		
		FileFilter filter = new FileNameExtensionFilter("JPEG file", "jpg", "jpeg", "png", "gif");
		fc = new JFileChooser();
		fc.setFileFilter(filter);
		
	}
	
	private void selectImage() {
		 if(fc.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
			 try {
				 process(ImageIO.read(fc.getSelectedFile()));
				
			} catch (IOException e) {
				e.printStackTrace();
			}
		 }		 
	}
	
	private void process(BufferedImage bi) {
		
		inputLabel.setIcon(new ImageIcon(iHandler.resize(bi, 170, 170)));
		ipanel.process(bi);
		outputLabel.setIcon(new ImageIcon(ipanel.getExtractedImage()));
	
	}
	
}
