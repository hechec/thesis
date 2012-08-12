package ui;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

public class MainFrame extends JFrame {
	
	private JPanel contentPane;	
	private JMenuBar menuBar;
	private JMenu fileMenu, actionMenu;
	private JMenuItem openAction;
	private JMenuItem exitAction;
	private JMenuItem enhance;
	private ImagePanel iPanel;
	private JFileChooser fc = new JFileChooser();
	
	public MainFrame() {
		
		initFrame();
		initMenuBar();
		
		iPanel = new ImagePanel();
		contentPane.add(iPanel, BorderLayout.CENTER);		
	}


	private void initMenuBar() {
		
		menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		
		fileMenu = new JMenu("File");
		menuBar.add(fileMenu);
		actionMenu = new JMenu("Action");
		
		openAction =  new JMenuItem("Open");		
		openAction.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				showFileChooser();
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
		
		enhance =  new JMenuItem("enhance image");
		actionMenu.add(enhance);
		enhance.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				
			}
		});
	
	}

	private void showFileChooser() {
		 
		 int retval = fc.showOpenDialog(this);
		 if(retval == JFileChooser.APPROVE_OPTION) {
			    File file = fc.getSelectedFile();
			    iPanel.addImage(file);
		 }
		 
	}


	private void initFrame() {
		
		contentPane = new JPanel();
		setContentPane(contentPane);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout());
		
		setTitle("Segmentation 1");
		setSize(800, 600);
		setVisible(true);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		
	}
	
}
