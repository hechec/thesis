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

import abcnn.Classifier;
import abcnn.Result;

import dialogs.LoadingDialog;

import util.ImageLoader;

import javax.swing.JLabel;
import javax.swing.JComboBox;
import javax.swing.SwingConstants;

public class BatchPane extends JPanel {
	
	private JTextField textField;
	private JTextArea textArea;
	private JFileChooser chooser;
	
	private JButton btnCustom;
	private JLabel result1, result2;
	
	private AppFrame appFrame;
	private ABCNNPane abcnnPane;
	private Classifier classifier;
	
	private ImageLoader iLoader;
		
	private boolean isReady = false;
	
	private boolean testDefault = true;
	
	/**
	 * Create the panel.
	 * @param chooser 
	 * @param classifier 
	 */
	public BatchPane(ABCNNPane abcnnPane, JFileChooser chooser, Classifier classifier) {
		setBounds(0, 0, 290, 442);
		setLayout(null);
		
		JButton btnTest = new JButton("TEST");
		btnTest.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				batchTest();
			}
		});
		btnTest.setBounds(0, 110, 276, 44);
		add(btnTest);
		
		textField = new JTextField();
		textField.setBounds(87, 72, 189, 27);
		add(textField);
		textField.setColumns(10);
		textField.setFocusable(false);
		
		textField.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				selectTestingDir();
			}
		});
		
		btnCustom = new JButton("CUSTOM");
		btnCustom.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				selectTestingDir();
			}
		});
		btnCustom.setBounds(2, 72, 75, 27);
		add(btnCustom);
		
		this.chooser = chooser;
		this.classifier = classifier;
		this.abcnnPane = abcnnPane;

		textArea = new JTextArea();
		textArea.setEnabled(false);
		
		JScrollPane scrollPane = new JScrollPane(textArea);
		scrollPane.setBounds(0, 165, 276, 145);
		add(scrollPane);
		scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		
		JPanel panel = new JPanel();
		panel.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "RESULTS", TitledBorder.CENTER, TitledBorder.TOP, null, null));
		panel.setBounds(0, 321, 279, 110);
		add(panel);
		panel.setLayout(null);
		
		result1 = new JLabel("--");
		result1.setHorizontalAlignment(SwingConstants.CENTER);
		result1.setBounds(10, 31, 259, 25);
		panel.add(result1);
		
		result2 = new JLabel("--");
		result2.setHorizontalAlignment(SwingConstants.CENTER);
		result2.setBounds(10, 55, 259, 33);
		panel.add(result2);
		
		JLabel lblSelectTestingSet = new JLabel("Select Testing Set:");
		lblSelectTestingSet.setBounds(10, 11, 103, 35);
		add(lblSelectTestingSet);
		
		String[] string = {"Default", "Browse"};
		final JComboBox comboBox = new JComboBox(string);
		comboBox.setBounds(123, 14, 153, 28);
		add(comboBox);
		
		comboBox.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				update(comboBox.getSelectedIndex() == 1);
			}
		});
		
		textField.setEnabled(false);
		btnCustom.setEnabled(false);
		
	}
	
	private void update(boolean b) {
		textField.setEnabled(b);
		btnCustom.setEnabled(b);
		testDefault = !b;
	}

	private void selectTestingDir() {
		chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		if (chooser.showOpenDialog(abcnnPane) == JFileChooser.APPROVE_OPTION) {
			textField.setText(chooser.getSelectedFile()+"");
			String path = textField.getText();
			File file = new File(path);
			if(!file.exists())
				JOptionPane.showMessageDialog(appFrame, "The directory does not exist.", "Error", JOptionPane.WARNING_MESSAGE);
			else 
				iLoader = new ImageLoader(abcnnPane, this, path, new LoadingDialog(appFrame));
		}
		
	}

	private void batchTest() {
		if(!classifier.isTrained()) {
			JOptionPane.showMessageDialog(abcnnPane, "Please train the network or load training result.", "Error Message", JOptionPane.WARNING_MESSAGE);
			return;
		}
		//if(!isReady) {
		//	JOptionPane.showMessageDialog(abcnnPane, "Please load testing data.", "Error Message", JOptionPane.WARNING_MESSAGE);
		//	return;
		//}
		//input_data = iLoader.getInputVector();
		//expectedOutput = iLoader.getExpectedOutput();
		Result result = null;
		
		if( testDefault ) {
			//input_data = classifier.getTestingInput();
			//expectedOutput = classifier.getTestingOutput();
			result = classifier.test_batch();
		}
		else {
			input_data = iLoader.getInputVector();
			expectedOutput = iLoader.getExpectedOutput();
		}
		
		for( int i = 0; i < result.size(); i++ ) 
			textArea.append( "test " + (i+1) + ": " + "expected: "+result.getExpected(i)+"\tactual:" +result.getActual(i)+ "\n");
		
		result1.setText((int)result.getScore() +" out of "+result.size()+" are classified correctly \n");
		result2.setText("Accuracy: " + result.getAccuracy() + "%\n");
		
		/*int actualIndex;
		double correct = 0;
		for( int i = 0; i < input_data.length; i++ ) {
			actualIndex = classifier.classify(input_data[i]);
			correct += (actualIndex == expectedOutput[i] ? 1 : 0);
			
		}
		
		double acc = ((correct/input_data.length)*100);
		
		//textArea.append((int)correct +" out of "+input_data.length +" are classified correctly \n");
		//textArea.append("Accuracy: " + acc + "%\n");
		
		result1.setText((int)correct +" out of "+input_data.length +" are classified correctly \n");
		result2.setText("Accuracy: " + acc + "%\n");
		*/
	}
	
	private double[][] input_data;
	private int[] expectedOutput;

	public void setReady(boolean b) {
		isReady = true;
	}
	
	public void reset() {
		result1.setText("--");
		result2.setText("--");
	}
}
