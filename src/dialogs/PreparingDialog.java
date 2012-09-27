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
	 * @param appFrame 
	 * @param thePane 
	 */
	public PreparingDialog(AppFrame appFrame) {
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
		
		setLocationRelativeTo(appFrame);
	}
	
	public void start(int max) {
		progressBar.setMaximum(max);
		setVisible(true);
		new Thread() {
			public void run() {
				int ctr = 1;
				while( isShowing() ) {
					if( ctr == 5 )
						ctr -= 4;
					updateTitle(ctr);
					try {
						ctr++;
						Thread.sleep(400);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					
				}				
			}
		}.start();
	}
	
	private void updateTitle(int ctr) {
		String dot = "";
		for( int i = 0; i < ctr; i++ )
			dot += ".";
		setTitle("Preparing"+dot);
	}
	
	public void setValue(int percent) {
		progressBar.setValue(percent);
	}

	public void setMax(int max) {
		progressBar.setMaximum(max);
	}
	
}
