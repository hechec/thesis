package dialogs;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.image.BufferedImage;

import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

public class MyDialog extends JDialog {

	private final JPanel contentPanel = new JPanel();

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			MyDialog dialog = new MyDialog();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public MyDialog() {
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setSize(900, 600);
		getContentPane().setLayout(new BorderLayout());
		
		//JScrollPane scrollPane = new JScrollPane(contentPanel);
		//getContentPane().add(scrollPane, BorderLayout.CENTER);
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		
		contentPanel.setLayout(new FlowLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		//getContentPane().add(contentPanel, BorderLayout.CENTER);
	}
	
	public void addImage(BufferedImage image) {
		contentPanel.add(new JLabel(new ImageIcon(image)));
	}
	
}
