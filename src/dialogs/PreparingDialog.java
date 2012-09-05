package dialogs;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JProgressBar;

import ui.AppFrame;
import ui.ABCNNPane;

public class PreparingDialog extends JDialog {

	private final JPanel contentPanel = new JPanel();

	private JProgressBar progressBar;
	
	/**
	 * Create the dialog.
	 * @param thePane 
	 */
	public PreparingDialog() {
		setTitle("Preparing...");
		setAlwaysOnTop(true);
	
		setBounds(100, 100, 311, 57);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		
		progressBar = new JProgressBar();
		progressBar.setBounds(0, 0, 295, 19);
		contentPanel.add(progressBar);
		
		progressBar.setMinimum(0);
		

		setLocationRelativeTo(AppFrame.getInstance());
	}
	
	public void setValue(int percent) {
		progressBar.setValue(percent);
	}

	public void setMax(int max) {
		progressBar.setMaximum(max);
	}
	
}
