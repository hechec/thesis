package ui;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JTextField;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;

import javax.swing.JTextArea;
import javax.swing.JScrollPane;
import javax.swing.border.TitledBorder;
import javax.swing.UIManager;

import dialogs.LoadingDialog;

import utilities.ImageLoader;

public class BatchPane extends JPanel {
	
	private JTextField textField;
	private JTextArea textArea;
	private JFileChooser chooser;
	
	private AppFrame appFrame;
	private ABCNNPane abcnnPane;
	
	private ImageLoader iLoader;
		
	private boolean isReady = false;
	
	/**
	 * Create the panel.
	 * @param abcnnPane 
	 * @param chooser 
	 */
	public BatchPane(AppFrame appFrame, ABCNNPane abcnnPane, JFileChooser chooser) {
		setBounds(0, 0, 290, 442);
		setLayout(null);
		
		JButton btnTest = new JButton("TEST");
		btnTest.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				batchTest();
			}
		});
		btnTest.setBounds(0, 49, 276, 44);
		add(btnTest);
		
		textField = new JTextField();
		textField.setBounds(0, 11, 189, 27);
		add(textField);
		textField.setColumns(10);
		textField.setFocusable(false);
		
		textField.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				selectTestingDir();
			}
		});
		
		JButton btnCustom = new JButton("CUSTOM");
		btnCustom.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				selectTestingDir();
			}
		});
		btnCustom.setBounds(201, 11, 75, 27);
		add(btnCustom);
		
		this.chooser = chooser;
		this.abcnnPane = abcnnPane;

		textArea = new JTextArea();
		textArea.setEnabled(false);
		
		JScrollPane scrollPane = new JScrollPane(textArea);
		scrollPane.setBounds(0, 123, 276, 145);
		add(scrollPane);
		scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		
		JPanel panel = new JPanel();
		panel.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "RESULTS", TitledBorder.CENTER, TitledBorder.TOP, null, null));
		panel.setBounds(0, 281, 279, 110);
		add(panel);
		panel.setLayout(null);
		
		this.appFrame = appFrame;
	}

	private void selectTestingDir() {
		chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		if (chooser.showOpenDialog(abcnnPane) == JFileChooser.APPROVE_OPTION) 
			textField.setText(chooser.getSelectedFile()+"");
		
		String path = textField.getText();
		
		iLoader = new ImageLoader(abcnnPane, this, path, new LoadingDialog(appFrame));
		
	}

	private void batchTest() {
		if(!abcnnPane.isTrained) {
			JOptionPane.showMessageDialog(abcnnPane, "Please train the network or load training result.", "Error Message", JOptionPane.WARNING_MESSAGE);
			return;
		}
		if(!isReady) {
			JOptionPane.showMessageDialog(abcnnPane, "Please load testing data.", "Error Message", JOptionPane.WARNING_MESSAGE);
			return;
		}
		
		input_data = iLoader.getInputVector();
		expectedOutput = iLoader.getExpectedOutput();
		
		int actualIndex;
		double correct = 0;
		for( int i = 0; i < input_data.length; i++ ) {
			actualIndex = abcnnPane.classify(input_data[i]);
			correct += (actualIndex == expectedOutput[i] ? 1 : 0);
			textArea.append( "test " + (i+1) + ": " + "expected: "+expectedOutput[i]+"\tactual:" +actualIndex + "\n");
		}
		
		double acc = ((correct/input_data.length)*100);
		
		textArea.append("Accuracy(%):"+ acc  +"\n");
	}
	
	private double[][] input_data;
	private int[] expectedOutput;

	public void setReady(boolean b) {
		isReady = true;
	}
	
}
