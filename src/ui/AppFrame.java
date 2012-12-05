package ui;

import com.jtattoo.plaf.texture.TextureUtils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import javax.swing.border.EmptyBorder;

import custom.ClosableTabbedPane;

public class AppFrame extends JFrame 
{
	private JPanel contentPane;
	private ClosableTabbedPane tabbedPane;
	
	private ABCNNTab abcnnTab;
	private BGRemoverTab bgRemoverTab;
	private ResizerTab resizerTab;
	
	private JMenuBar menuBar;
	private JMenu fileMenu;
	private JMenu optionMenu;
	private JMenu toolMenu;
	
	private JMenuItem openAction;
	private JMenuItem exitAction;
	private JCheckBoxMenuItem bgremoverToolItem;
	private JCheckBoxMenuItem resizerToolItem;
	
	private ArrayList<Component> tabs = new ArrayList<Component>();
	private ArrayList<JCheckBoxMenuItem> toolItems = new ArrayList<JCheckBoxMenuItem>();
	
	/**
	 * Create the frame.
	 */
	public AppFrame() 
	{
		setTitle("Toto-Bee");
		
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		contentPane.putClientProperty("textureType", new Integer(TextureUtils.WINDOW_TEXTURE_TYPE));
		
		abcnnTab = new ABCNNTab(this);
		bgRemoverTab = new BGRemoverTab(this);
		resizerTab = new ResizerTab(this);
		
		tabbedPane = new ClosableTabbedPane(this);
		tabbedPane.setFocusable(false);
		tabbedPane.addTabb("Toto-Bee Main", abcnnTab);
		
		//tabbedPane.addTabb("Extraction", extractionTab);
		
		tabs.add(abcnnTab);
		toolItems.add(null);
		
		tabbedPane.setBounds(4, 4, 745, 683);
		contentPane.add(tabbedPane, 0);
		
		initMenuBar();
		config();
	}
	
	private void initMenuBar() 
	{
		menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		
		fileMenu = new JMenu("File");
		optionMenu = new JMenu("Options");
		toolMenu = new JMenu("Tools");
		menuBar.add(fileMenu);
		menuBar.add(optionMenu);
		menuBar.add(toolMenu);
		
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
		
		bgremoverToolItem  = new JCheckBoxMenuItem("Background Remover");
		bgremoverToolItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent event) {
				JCheckBoxMenuItem s = (JCheckBoxMenuItem) event.getSource();
				if( s.isSelected() )
					addTab("Background Remover", bgRemoverTab, s);
				else 
					removeTab( tabs.indexOf(bgRemoverTab) );
			}
		});
		
		resizerToolItem  = new JCheckBoxMenuItem("Image Resizer");
		resizerToolItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent event) {
				JCheckBoxMenuItem s = (JCheckBoxMenuItem) event.getSource();
				if( s.isSelected() )
					addTab("Image Resizer", resizerTab, s);
				else 
					removeTab( tabs.indexOf(resizerTab) );
			}
		});
		
		toolMenu.add(bgremoverToolItem);
		toolMenu.add(resizerToolItem);	
		
	}
	
	private void addTab(String title, Component component, JCheckBoxMenuItem item)
	{
		tabbedPane.addTabb(title, component);
		tabs.add(component);
		toolItems.add(item);
		tabbedPane.setSelectedIndex(tabs.size()-1);
	}
	
	public void removeTab(int index)
	{	
		if( tabs.size() > 1 ) { // as of now
			tabbedPane.removeTabAt(index);
			tabs.remove(index);
			toolItems.get(index).setSelected(false);
			toolItems.remove(index);
		}
	}

	private void config() 
	{
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(745, 683);
		setLocationRelativeTo(null);		
		setResizable(false);
	}
	
}
