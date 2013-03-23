package views.optionpane;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;

import views.ExperimentPane;
import views.Frame;
import custom.MainButton;

public class ConfirmationDialog extends JDialog
{

	public static void main(String[] args) {
		try {
			ConfirmationDialog dialog = new ConfirmationDialog(null, "");
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private Object caller;

	public ConfirmationDialog(Object caller, String message) 
	{
		this.caller = caller;
		final Frame frame = Frame.getInstance();
		
		setSize(300, 180);
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
		panel.setBounds(5, 5, 290, 170);
		panel.setLayout(null);
		getContentPane().add(panel);
		
		JButton exitButton = new MainButton("/images/close.png", "/images/closeHover.png");
		exitButton.setBorderPainted(false);
		exitButton.setBounds(251, 1, 38, 23);
		panel.add(exitButton, 0);
		exitButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		
		JLabel label = new JLabel("<html>"+message+"</html>");
		label.setBounds(45, 50, 200, 50);
		label.setForeground(Color.WHITE);
		label.setFont(new Font("Century Gothic", Font.ITALIC, 14));
	//	label.setOpaque(true);
		panel.add(label);
		
		JButton yesButton = new JButton("Yes");
		yesButton.setBounds(70, 125, 70, 30);
		yesButton.setFont(new Font(null, Font.PLAIN, 12));
		panel.add(yesButton);
		yesButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				yes();
			}
		});
		
		JButton noButton = new JButton("No");
		noButton.setBounds(160, 125, 70, 30);
		noButton.setFont(new Font(null, Font.PLAIN, 12));
		panel.add(noButton);
		noButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				no();
			}
		});
	}
	
	private void yes()
	{
		if(caller instanceof ExperimentPane) {
			ExperimentPane.getInstance().confirmYes();
		}
		this.dispose();
	}
	
	private void no()
	{
		if(caller instanceof ExperimentPane) {
			ExperimentPane.getInstance().confirmNo();
		}
		this.dispose();
	}
	
}
