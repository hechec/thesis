package ui;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.color.ColorSpace;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.KeyStroke;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;

import javax.swing.JTextArea;


public class MyFrame extends JFrame {

	private JPanel contentPane;
	
	private JMenuBar menuBar;
	private JMenu fileMenu;
	private JMenuItem openAction;
	private JMenuItem exitAction;
	private JFileChooser fc;
	private JPanel bottomPane;
	
	BufferedImage original;
	
	private GuiController gc;
	private ImagePane iPane;
	private FeaturesPane fPane;
	private JTextArea dArea;
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					
					UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
					MyFrame frame = new MyFrame();
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
	public MyFrame() {
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		
		iPane = new ImagePane();
		contentPane.add(iPane);
		
		JPanel rightPane = new JPanel();
		rightPane.setBounds(475, 0, 209, 400);
		contentPane.add(rightPane);
		rightPane.setLayout(null);
		
		fPane = new FeaturesPane();
		fPane.setBounds(10, 11, 199, 378);
		rightPane.add(fPane);
		
		bottomPane = new JPanel();
		bottomPane.setBounds(0, 400, 684, 141);
		contentPane.add(bottomPane);
		bottomPane.setLayout(null);
		
		dArea = new JTextArea();
		dArea.setBounds(0, 0, 684, 141);
		
		JScrollPane spPane = new JScrollPane();
		spPane.setBounds(8, 0, 674, 141);
		spPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		spPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		spPane.setViewportView(dArea);
		bottomPane.add(spPane);
		
		gc = new GuiController(iPane, fPane, dArea);
		iPane.setController(gc);
		
		initMenuBar();
		config();
	}
	
	private void initMenuBar() {
		menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		
		fileMenu = new JMenu("File");
		menuBar.add(fileMenu);
		openAction =  new JMenuItem("Open", KeyEvent.VK_T);
		KeyStroke ctrlOKeyStroke = KeyStroke.getKeyStroke("control O");
		openAction.setAccelerator(ctrlOKeyStroke);
		openAction.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				selectImage();
			}

		});
		
		exitAction =  new JMenuItem("Exit", KeyEvent.VK_T);
		KeyStroke ctrlQKeyStroke = KeyStroke.getKeyStroke("control Q");
		exitAction.setAccelerator(ctrlQKeyStroke);
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
		 dArea.append("opening...\n");
		 if(fc.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
			 try {
				 
				 iPane.showInput(ImageIO.read(fc.getSelectedFile()));
				 dArea.append("file opened: "+fc.getSelectedFile()+"\n");
			} catch (IOException e) {
				e.printStackTrace();
			}
		 }
		 else
			 dArea.append("aborted.\n");
	}


	private void config() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(700, 600);
		setLocationRelativeTo(null);		
		setResizable(false);
	}
}
