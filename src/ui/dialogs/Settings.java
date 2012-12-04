package ui.dialogs;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import ui.ABCNNTab;
import javax.swing.border.TitledBorder;
import javax.swing.JRadioButton;

public class Settings extends JDialog 
{
	private JPanel contentPanel = new JPanel();
	
	public static final int USER_DEFINED_DATA = 0;
	public static final int RANDOM_DATA = 1;
	public static final int INDIVIDUAL_TEST = 0;
	public static final int BATCH_TEST = 1;
	
	public static int dataSelection = USER_DEFINED_DATA;
	private int testBy = INDIVIDUAL_TEST;
	
	public Settings(final ABCNNTab abcnnTab) 
	{
		setTitle("Settings");
		setModal(true);
		setBounds(100, 100, 450, 326);
		setLocationRelativeTo(null);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBorder(new TitledBorder(null, "Training", TitledBorder.CENTER, TitledBorder.TOP, null, null));
		panel.setBounds(10, 11, 414, 105);
		contentPanel.add(panel);
		panel.setLayout(null);
		
		JRadioButton userDefinedRadioButton = new JRadioButton("User-defined");
		userDefinedRadioButton.setBounds(19, 39, 109, 23);
		panel.add(userDefinedRadioButton);
		userDefinedRadioButton.setSelected(true);
		userDefinedRadioButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				dataSelection = USER_DEFINED_DATA;
			}
		});		
		
		JRadioButton randomRadioButton = new JRadioButton("Random");
		randomRadioButton.setBounds(19, 65, 109, 23);
		panel.add(randomRadioButton);
		randomRadioButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				dataSelection = RANDOM_DATA;
			}
		});
		
		ButtonGroup group1 = new ButtonGroup();
		group1.add(userDefinedRadioButton);
		group1.add(randomRadioButton);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBorder(new TitledBorder(null, "Testing", TitledBorder.CENTER, TitledBorder.TOP, null, null));
		panel_1.setBounds(10, 127, 414, 105);
		contentPanel.add(panel_1);
		panel_1.setLayout(null);
		
		JRadioButton singleRadioButton = new JRadioButton("Individual Testing");
		singleRadioButton.setBounds(19, 40, 151, 23);
		panel_1.add(singleRadioButton);
		singleRadioButton.setSelected(true);
		singleRadioButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				testBy = INDIVIDUAL_TEST;
			}
		});
		
		JRadioButton batchRadioButton = new JRadioButton("Batch Testing");
		batchRadioButton.setBounds(19, 66, 137, 23);
		panel_1.add(batchRadioButton);
		batchRadioButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				testBy = BATCH_TEST;
			}
		});
		
		ButtonGroup group2 = new ButtonGroup();
		group2.add(singleRadioButton);
		group2.add(batchRadioButton);
		
		JButton btnSave = new JButton("SAVE");
		btnSave.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				abcnnTab.applySettings(dataSelection, testBy);
				setVisible(false);
			}
		});
		btnSave.setBounds(102, 243, 100, 34);
		contentPanel.add(btnSave);
		
		JButton btnCancel = new JButton("CANCEL");
		btnCancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setVisible(false);
			}
		});
		btnCancel.setBounds(226, 243, 100, 34);
		contentPanel.add(btnCancel);
	}
	
}
