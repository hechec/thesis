package ui;

import javax.swing.JPanel;
import javax.swing.border.TitledBorder;
import javax.swing.UIManager;

import java.awt.Dimension;
import java.awt.GridLayout;
import javax.swing.JLabel;
import javax.swing.JTextField;
import java.awt.FlowLayout;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;
import java.awt.Color;

public class FeaturesPane extends JPanel {
	private JTextField redTextField;
	private JTextField greenTextField;
	private JTextField rgTextField;
	private JTextField hueTextField;
	private JTextField aTextField;

	/**
	 * Create the panel.
	 */
	public FeaturesPane() {
		setBorder(new TitledBorder(new LineBorder(new Color(130, 135, 144)), "Features", TitledBorder.CENTER, TitledBorder.TOP, null, null));
		setBounds(10, 11, 189, 378);
		setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Mean Red:");
		lblNewLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		lblNewLabel.setBounds(21, 40, 73, 33);
		add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("Mean Green:");
		lblNewLabel_1.setHorizontalAlignment(SwingConstants.RIGHT);
		lblNewLabel_1.setBounds(21, 84, 73, 33);
		add(lblNewLabel_1);
		
		JLabel lblMeanRg = new JLabel("Mean R-G:");
		lblMeanRg.setHorizontalAlignment(SwingConstants.RIGHT);
		lblMeanRg.setBounds(21, 128, 73, 33);
		add(lblMeanRg);
		
		redTextField = new JTextField();
		redTextField.setHorizontalAlignment(SwingConstants.CENTER);
		redTextField.setEditable(false);
		redTextField.setBounds(104, 43, 59, 26);
		add(redTextField);
		redTextField.setColumns(10);
		
		greenTextField = new JTextField();
		greenTextField.setHorizontalAlignment(SwingConstants.CENTER);
		greenTextField.setEditable(false);
		greenTextField.setColumns(10);
		greenTextField.setBounds(104, 87, 59, 26);
		add(greenTextField);
		
		rgTextField = new JTextField();
		rgTextField.setHorizontalAlignment(SwingConstants.CENTER);
		rgTextField.setEditable(false);
		rgTextField.setColumns(10);
		rgTextField.setBounds(104, 131, 59, 26);
		add(rgTextField);
		
		JLabel lblMeanH = new JLabel("Mean Hue:");
		lblMeanH.setHorizontalAlignment(SwingConstants.RIGHT);
		lblMeanH.setBounds(21, 172, 73, 33);
		add(lblMeanH);
		
		JLabel lblMeanA = new JLabel("Mean a*:");
		lblMeanA.setHorizontalAlignment(SwingConstants.RIGHT);
		lblMeanA.setBounds(21, 216, 73, 33);
		add(lblMeanA);
		
		hueTextField = new JTextField();
		hueTextField.setHorizontalAlignment(SwingConstants.CENTER);
		hueTextField.setEditable(false);
		hueTextField.setColumns(10);
		hueTextField.setBounds(104, 175, 59, 26);
		add(hueTextField);
		
		aTextField = new JTextField();
		aTextField.setHorizontalAlignment(SwingConstants.CENTER);
		aTextField.setEditable(false);
		aTextField.setColumns(10);
		aTextField.setBounds(104, 216, 59, 26);
		add(aTextField);
		
		setRedField(0);
		setGreenField(0);
		setRedGreenField(0);
		setHueField(0);
		setAField(0);
	}
	
	public void setRedField(int value) {
		redTextField.setText(""+value);
	}
	
	public void setGreenField(int value) {
		greenTextField.setText(""+value);
	}
	
	public void setRedGreenField(int value) {
		rgTextField.setText(""+value);
	}
	
	public void setHueField(int value) {
		hueTextField.setText(""+value);
	}
	
	public void setAField(int value) {
		aTextField.setText(""+value);
	}
	
}
