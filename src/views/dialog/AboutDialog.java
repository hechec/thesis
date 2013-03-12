package views.dialog;

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

import views.Frame;
import custom.MainButton;

public class AboutDialog extends JDialog 
{
	private final JPanel contentPanel = new JPanel();
	
	public AboutDialog()
	{
		final Frame frame = Frame.getInstance();
		
		setSize(410, 310);
		setModal(true);
		setLocationRelativeTo(frame);
		setUndecorated(true);
		getContentPane().setBackground(Color.WHITE);
		getContentPane().setLayout(null);
		
		final URL bgUrl = getClass().getResource("/images/about.png");
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
		panel.setBounds(5, 5, 400, 300);
		panel.setLayout(null);
		getContentPane().add(panel);
		
		JButton exitButton = new MainButton("/images/close.png", "/images/closeHover.png");
		exitButton.setBorderPainted(false);
		exitButton.setBounds(361, 1, 38, 23);
		panel.add(exitButton, 0);
		exitButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		
	}
	
	public static void main(String[] args) {
		try {
			AboutDialog dialog = new AboutDialog();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}
