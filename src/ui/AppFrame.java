package ui;

import java.awt.BorderLayout;
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
import javax.swing.JTextArea;
import javax.swing.JTabbedPane;


public class AppFrame extends JFrame {

	private JPanel contentPane;
	private JTabbedPane tabbedPane;
	private ExtractionPane extractionTab;
	private ThePane trueTab;
	
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
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout());
		
		bottomPane = new JPanel();
		bottomPane.setBounds(0, 400, 684, 141);
		contentPane.add(bottomPane);
		bottomPane.setLayout(null);
		
		dArea = new JTextArea();
		//dArea.setBounds(0, 0, 684, 141);
		
		JScrollPane spPane = new JScrollPane();
		spPane.setBounds(8, 0, 674, 141);
		spPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		spPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		spPane.setViewportView(dArea);
		bottomPane.add(spPane, BorderLayout.SOUTH);
		
		extractionTab = new ExtractionPane(dArea);
		trueTab = new ThePane(dArea);
		
		tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.add("Extraction", extractionTab);
		tabbedPane.add("True", trueTab);
		contentPane.add(tabbedPane, BorderLayout.CENTER);
		
		gc = new GuiController(extractionTab, dArea);
		extractionTab.setController(gc);
		
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
				extractionTab.selectImage();
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

		fileMenu.add(openAction);
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
		setSize(700, 600);
		setLocationRelativeTo(null);		
		setResizable(false);
	}
}
