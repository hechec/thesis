package dialogs;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JRadioButton;

import org.omg.CORBA.INTERNAL;

import com.sun.xml.internal.ws.Closeable;

import ui.ABCNNTab;
import ui.AppFrame;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class SettingDialog extends JDialog {

	private final JPanel contentPanel = new JPanel();

	private ABCNNTab abcnnPane;
	
	private ButtonGroup group;
	
	public static final int BATCH = 0, INDIVIDUAL = 1;
	private int selected = BATCH;
	
	/**
	 * Create the dialog.
	 * @param abcnnPane 
	 */
	public SettingDialog(ABCNNTab abcnnPane) {
		setTitle("Settings");
		this.abcnnPane = abcnnPane;
		
		setBounds(100, 100, 350, 250);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		
		JLabel lblTestOption = new JLabel("TEST OPTION:");
		lblTestOption.setBounds(27, 29, 147, 31);
		contentPanel.add(lblTestOption);
		
		JRadioButton allRadioButton = new JRadioButton("Batch Testing");
		allRadioButton.setBounds(64, 56, 176, 31);
		contentPanel.add(allRadioButton);
		allRadioButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				selected = BATCH;
			}
		});
		
		JRadioButton oneRadioButton = new JRadioButton("Individual Testing");
		oneRadioButton.setBounds(65, 90, 157, 23);
		contentPanel.add(oneRadioButton);
		oneRadioButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				selected = INDIVIDUAL;
			}
		});
		
		group = new ButtonGroup();
		group.add(allRadioButton);
		group.add(oneRadioButton);
		allRadioButton.setSelected(true);
		
		JPanel buttonPane = new JPanel();
		buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
		getContentPane().add(buttonPane, BorderLayout.SOUTH);
		JButton okButton = new JButton("OK");
		okButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				close();
			}

		});
		okButton.setActionCommand("OK");
		buttonPane.add(okButton);
		getRootPane().setDefaultButton(okButton);
		JButton cancelButton = new JButton("Cancel");
		cancelButton.setActionCommand("Cancel");
		buttonPane.add(cancelButton);
		
		setLocationRelativeTo(null);
		setModal(true);
	}

	private void close() {
		abcnnPane.setTestOption(selected);
		this.dispose();
	}
}
