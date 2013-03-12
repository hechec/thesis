package views.dialog;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import views.ExperimentPane;
import views.Frame;
import views.RandomizerPane;
import custom.MainButton;

public class ResultLocationChooser extends JDialog 
{
	public static final int EXPERIMENATION = 0;
	public static final int RANDOMIZE = 1;
	
	public ResultLocationChooser(final int MODE) 
	{
		final Frame frame = Frame.getInstance();
		
		setSize(350, 200);
		setModal(true);
		setLocationRelativeTo(frame);
		setUndecorated(true);
		getContentPane().setBackground(Color.WHITE);
		getContentPane().setLayout(null);
		
		final URL bgUrl = getClass().getResource("/images/bg.png");
		JPanel panel = new JPanel() {
			@Override
		    public void paintComponent(Graphics g) {
		        super.paintComponent(g);
				Image image = null;
				try {                
					image = ImageIO.read(bgUrl);
		        } catch (IOException ex) {
		        	//System.out.println("Check background image.");
		        }
		        g.drawImage(image, 0, 0, null);          
		    }
		};
		panel.setBounds(5, 5, 340, 190);
		panel.setLayout(null);
		getContentPane().add(panel);
		
		JButton exitButton = new MainButton("/images/close.png", "/images/closeHover.png");
		exitButton.setBorderPainted(false);
		exitButton.setBounds(301, 1, 38, 23);
		panel.add(exitButton, 0);
		exitButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		
		JLabel label = new JLabel("Choose where to save the results:");
		label.setFont(new Font("Century Gothic", Font.ITALIC, 13));
		label.setForeground(Color.WHITE);
		label.setBounds(50, 50, 220, 30);
		panel.add(label);
		
		final JTextField textField = new JTextField();
		textField.setBounds(50, 80, 220, 30);
		textField.setBorder(null);
		textField.setFont(new Font("Century Gothic", Font.PLAIN, 12));
		textField.setBorder(BorderFactory.createCompoundBorder( textField.getBorder(),
					BorderFactory.createEmptyBorder(5, 5, 5, 5)));
		panel.add(textField);
		
		final JFileChooser fileChooser = new JFileChooser();
		fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		
		JButton browseButton = new JButton("..");
		browseButton.setBounds(275, 80, 30, 30);
		panel.add(browseButton);
		browseButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (fileChooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
					textField.setText(fileChooser.getSelectedFile().getAbsolutePath());
				}
			}
		});
		
		JButton optimizeButton = new JButton(MODE == EXPERIMENATION ? "OPTIMIZE" : "RANDOMIZE");
		optimizeButton.setBounds(120, 136, 100, 35);
		panel.add(optimizeButton);
		optimizeButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				File file = new File(textField.getText());
				if(file.exists()) {
					if(MODE == EXPERIMENATION)
						ExperimentPane.getInstance().train(file);
					else
						RandomizerPane.getInstance().randomize(file);
				} 
				dispose();
			}
		});
		
	}
	
	public static void main(String[] a) {
		//new ResultLocationChooser().setVisible(true);
	}

}
