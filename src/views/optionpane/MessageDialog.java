package views.optionpane;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URL;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;

import views.Frame;
import custom.ImagePane;
import custom.MainButton;

public class MessageDialog extends JDialog {

	public static void main(String[] args) {
		try {
			MessageDialog dialog = new MessageDialog("");
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public MessageDialog(String message) 
	{
		final Frame frame = Frame.getInstance();
		
		setSize(300, 180);
		setModal(true);
		setLocationRelativeTo(frame);
		setUndecorated(true);
		getContentPane().setBackground(Color.WHITE);
		getContentPane().setLayout(null);
		
		final URL bgUrl = getClass().getResource("/images/bg.png");
		JPanel panel = new ImagePane(bgUrl);
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
		
		JButton button = new JButton("CLOSE");
		button.setBounds(110, 125, 75, 30);
		button.setFont(new Font(null, Font.PLAIN, 12));
		panel.add(button);
		button.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		
	}

}
