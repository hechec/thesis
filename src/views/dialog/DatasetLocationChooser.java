package views.dialog;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.net.URL;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import views.Frame;
import custom.ImagePane;
import custom.MainButton;
import dataset.DataLocationHandler;

public class DatasetLocationChooser extends JDialog
{
	
	private String pathname = DataLocationHandler.getBaseFolder().getAbsolutePath();
	
	public DatasetLocationChooser(String message) 
	{
		final Frame frame = Frame.getInstance();
		
		setSize(450, 280);
		setModal(true);
		setLocationRelativeTo(frame);
		setUndecorated(true);
		getContentPane().setBackground(Color.WHITE);
		getContentPane().setLayout(null);
		
		final URL bgUrl = getClass().getResource("/images/dataset.png");
		JPanel panel = new ImagePane(bgUrl);
		panel.setBounds(5, 5, 440, 270);
		panel.setLayout(null);
		getContentPane().add(panel);
		
		JButton exitButton = new MainButton("/images/close.png", "/images/closeHover.png");
		exitButton.setBorderPainted(false);
		exitButton.setBounds(401, 1, 38, 23);
		panel.add(exitButton, 0);
		exitButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		
		final JLabel label = new JLabel("<html>"+DataLocationHandler.getBaseFolder().getAbsolutePath()+"</html>");
		label.setFont(new Font(null, Font.ITALIC, 12));
		label.setHorizontalAlignment(SwingConstants.CENTER);
		label.setBounds(50, 70, 340, 60);
		label.setForeground(Color.WHITE);
		//label.setBackground(Color.WHITE);
		//label.setOpaque(true);
		panel.add(label);
		
		JButton browseButton = new JButton("BROWSE");
		browseButton.setBounds(120, 210, 85, 35);
		browseButton.setFont(new Font(null, Font.PLAIN, 12));
		panel.add(browseButton);
		browseButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				pathname = selectFolder();
				if(new File(pathname).exists()){
					label.setText("<html>"+pathname+"</html>");
				} else 
					pathname = "";
			}
		});

		JButton saveButton = new JButton("SAVE");
		saveButton.setBounds(240, 210, 85, 35);
		saveButton.setFont(new Font(null, Font.PLAIN, 12));
		panel.add(saveButton);
		saveButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				DataLocationHandler.setBaseFolder(pathname);
				dispose();
			}
		});
	}	
	
	private String selectFolder()
	{
		String path = "";
		JFileChooser chooser = new JFileChooser(DataLocationHandler.getBaseFolder().getAbsolutePath());
		chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		if(chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) 
			path += chooser.getSelectedFile().getAbsolutePath(); 
		return path;
	}
	
}
