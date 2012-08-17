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
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;

import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
	
	private ImageHandler iHandler = new ImageHandler();
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
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
	public Frame() {
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(700, 600);
		setLocationRelativeTo(null);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		initMenuBar();

		JPanel leftPane = new JPanel();
		contentPane.add(leftPane, BorderLayout.WEST);
		leftPane.setPreferredSize(new Dimension(200, 600));
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
		outputLabel.setPreferredSize(new Dimension(100, 100));
		outputLabel.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1));
		outputPane.add(outputLabel);
		
		ipanel = new IPanel();
		
		//JPanel rightPane = new JPanel();
		contentPane.add(ipanel, BorderLayout.CENTER);
		
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
		
		exitAction =  new JMenuItem("Exit");
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
