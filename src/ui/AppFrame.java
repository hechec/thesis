package ui;

import java.awt.AlphaComposite;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import javax.swing.ImageIcon;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.KeyStroke;
import javax.swing.border.EmptyBorder;
import javax.swing.text.DefaultCaret;
import javax.swing.JTextArea;
import javax.swing.JTabbedPane;

import com.jtattoo.plaf.texture.TextureUtils;


public class AppFrame extends JFrame {

	private JPanel contentPane;
	private JTabbedPane tabbedPane;
	private ExtractionPane extractionTab;
	private ABCNNPane abcnnTab;
	
	private JMenuBar menuBar;
	private JMenu fileMenu;
	private JMenu optionMenu;
	
	private JMenuItem openAction;
	private JMenuItem exitAction;
	private JCheckBoxMenuItem showStepbyStep;
	
	private JPanel bottomPane;
	
	BufferedImage original;
	
	private GuiController gc;
	private JTextArea dArea;
	
	public static boolean showStepbyProcess = false;

	/**
	 * Create the frame.
	 */
	public AppFrame() {
		instance = this;
		setTitle("Toto-Bee");
		
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		contentPane.putClientProperty("textureType", new Integer(TextureUtils.WINDOW_TEXTURE_TYPE));
		
		dArea = new JTextArea();
		dArea.setEditable(false);
		dArea.setFocusable(false);
		//dArea.setBounds(0, 0, 684, 141);
		DefaultCaret caret = (DefaultCaret)dArea.getCaret();
		caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);
		
		
		JScrollPane spPane = new JScrollPane();
		spPane.setBounds(8, 0, 674, 141);
		spPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		spPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		spPane.setViewportView(dArea);
		//bottomPane.add(spPane, BorderLayout.SOUTH);
		
		extractionTab = new ExtractionPane(dArea);
		abcnnTab = new ABCNNPane(this);
		
		tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.add("ABC+NN", abcnnTab);
		tabbedPane.add("Extraction", extractionTab);
		
		tabbedPane.setBounds(4, 4, 745, 683);
		//tabbedPane.putClientProperty("textureType", new Integer(TextureUtils.WINDOW_TEXTURE_TYPE));
		
		//gc = new GuiController(extractionTab, dArea);
		//extractionTab.setController(gc);
		
		JPanel glass = new JPanel() {
			@Override
			public void paintComponent(Graphics g) {
				 Graphics2D g2d = (Graphics2D) g;
				 g2d.setRenderingHint(
			            RenderingHints.KEY_ANTIALIASING,
			            RenderingHints.VALUE_ANTIALIAS_ON);
			        g2d.setComposite(AlphaComposite.getInstance(
			            AlphaComposite.SRC_OVER, 0.1f));
			        g2d.setColor(Color.GRAY);
			        g2d.fillRect(0, 0, 745, 683);
			}
		};
		
		glass.setBounds(0, 0, 745, 683);
		
		contentPane.add(tabbedPane, 0);
		//contentPane.add(glass, 0);
		
		initMenuBar();
		config();
	}
	
	private void initMenuBar() {
		menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		
		fileMenu = new JMenu("File");
		optionMenu = new JMenu("Options");
		menuBar.add(fileMenu);
		menuBar.add(optionMenu);
		
		openAction =  new JMenuItem("Open", KeyEvent.VK_T);
		openAction.setIcon(new ImageIcon("src/images/open.png"));
		KeyStroke ctrlOKeyStroke = KeyStroke.getKeyStroke("control O");
		openAction.setAccelerator(ctrlOKeyStroke);
		openAction.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				//extractionTab.selectImage();
			}

		});
		
		exitAction =  new JMenuItem("Exit", KeyEvent.VK_T);
		KeyStroke ctrlQKeyStroke = KeyStroke.getKeyStroke("control E");
		exitAction.setAccelerator(ctrlQKeyStroke);
		exitAction.addActionListener(new ActionListener() {	
			@Override
			public void actionPerformed(ActionEvent arg0) {
				System.exit(0);				
			}
		});

		//fileMenu.add(openAction);
		fileMenu.add(exitAction);	
		
		showStepbyStep = new JCheckBoxMenuItem("show step by step");
		showStepbyStep.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent event) {
				JCheckBoxMenuItem s = (JCheckBoxMenuItem) event.getSource();
				showStepbyProcess = s.isSelected();
			}
		});
		
		optionMenu.add(showStepbyStep);
		
	}

	private void config() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(745, 683);
		setLocationRelativeTo(null);		
		setResizable(false);
	}
	
	public static Frame getInstance() {
		return instance;
	}
	
	private static AppFrame instance = null;
}
