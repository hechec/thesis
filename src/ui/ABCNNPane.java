package ui;

import handlers.FileHandler;
import handlers.ImageHandler;

import javax.imageio.ImageIO;
import javax.swing.JPanel;
import javax.swing.JTextArea;

import org.omg.CORBA.PRIVATE_MEMBER;

import dialogs.PreparingDialog;

import abcnn.ABC;

import javax.swing.border.TitledBorder;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import javax.swing.JProgressBar;
import javax.swing.JButton;
import javax.swing.SwingUtilities;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.JTextField;
import java.awt.Font;

public class ABCNNPane extends JPanel {

	private JTextArea dArea;
	
	private JFileChooser trainingChooser;
	private JFileChooser testingChooser;
	
	private JLabel inputLabel;
	
	private JSpinner employedSpinner;
	private JSpinner onlookerSpinner;
	private JSpinner cycleSpinner;
	private JSpinner runtimeSpinner;
	
	private JProgressBar cycleBar;
	private JProgressBar runtimeBar;
	private JTextField directoryField;
	
	private ArrayList<BufferedImage> training_input = new ArrayList<BufferedImage>();
	private ArrayList<Integer> training_output = new ArrayList<Integer>();
	private double[][] input_data;
	private double[][] output_data;
	
	public ABCNNPane(JTextArea dArea) {
		this.dArea = dArea;
		this.setSize(670, 400);
		setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBorder(new TitledBorder(null, "ABC Parameters", TitledBorder.CENTER, TitledBorder.TOP, null, null));
		panel.setBounds(26, 159, 326, 189);
		add(panel);
		panel.setLayout(null);
		
		JLabel lblEmployedBees = new JLabel("Employed Bees:");
		lblEmployedBees.setHorizontalAlignment(SwingConstants.RIGHT);
		lblEmployedBees.setBounds(10, 30, 88, 20);
		panel.add(lblEmployedBees);
		
		JLabel lblOnlookerBees = new JLabel("Onlooker Bees:");
		lblOnlookerBees.setHorizontalAlignment(SwingConstants.RIGHT);
		lblOnlookerBees.setBounds(10, 61, 88, 24);
		panel.add(lblOnlookerBees);
		
		JLabel lblMacCycle = new JLabel("cycle:");
		lblMacCycle.setHorizontalAlignment(SwingConstants.RIGHT);
		lblMacCycle.setBounds(10, 118, 50, 20);
		panel.add(lblMacCycle);
		
		JLabel lblRuntime = new JLabel("run:");
		lblRuntime.setHorizontalAlignment(SwingConstants.RIGHT);
		lblRuntime.setBounds(10, 150, 50, 20);
		panel.add(lblRuntime);
		
		employedSpinner = new JSpinner();
		employedSpinner.setModel(new SpinnerNumberModel(new Integer(20), null, null, new Integer(1)));
		employedSpinner.setBounds(108, 30, 50, 24);
		panel.add(employedSpinner);
		
		onlookerSpinner = new JSpinner();
		onlookerSpinner.setModel(new SpinnerNumberModel(new Integer(20), null, null, new Integer(1)));
		onlookerSpinner.setBounds(108, 61, 50, 24);
		panel.add(onlookerSpinner);
		
		JLabel lblMacCycle_1 = new JLabel("Mac Cycle:");
		lblMacCycle_1.setHorizontalAlignment(SwingConstants.RIGHT);
		lblMacCycle_1.setBounds(151, 30, 88, 20);
		panel.add(lblMacCycle_1);
		
		JLabel lblRuntime_1 = new JLabel("Runtime:");
		lblRuntime_1.setHorizontalAlignment(SwingConstants.RIGHT);
		lblRuntime_1.setBounds(151, 61, 88, 24);
		panel.add(lblRuntime_1);
		
		cycleSpinner = new JSpinner();
		cycleSpinner.setModel(new SpinnerNumberModel(new Integer(2500), null, null, new Integer(1)));
		cycleSpinner.setBounds(249, 30, 50, 24);
		panel.add(cycleSpinner);
		
		runtimeSpinner = new JSpinner();
		runtimeSpinner.setModel(new SpinnerNumberModel(new Integer(30), null, null, new Integer(1)));
		runtimeSpinner.setBounds(249, 61, 50, 24);
		panel.add(runtimeSpinner);
		
		cycleBar = new JProgressBar();
		cycleBar.setBounds(70, 118, 229, 20);
		panel.add(cycleBar);
		
		runtimeBar = new JProgressBar();
		runtimeBar.setBounds(70, 150, 229, 20);
		panel.add(runtimeBar);
		
		JButton trainButton = new JButton("Train");
		trainButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				trainNetwork();
			}
		});
		trainButton.setBounds(26, 129, 89, 25);
		add(trainButton);
		
		directoryField = new JTextField();
		directoryField.setBounds(26, 63, 242, 27);
		add(directoryField);
		directoryField.setColumns(10);
		
		JButton selectButton = new JButton("select");
		selectButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				selectDirectory();
			}
		});
		selectButton.setBounds(275, 63, 77, 27);
		add(selectButton);
		
		JLabel lblNewLabel = new JLabel("Images Directory:");
		lblNewLabel.setBounds(26, 38, 106, 25);
		add(lblNewLabel);
		
		JButton btnPrepare = new JButton("Prepare");
		btnPrepare.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				prepareTrainingData();
			}
		});
		btnPrepare.setBounds(26, 101, 89, 23);
		add(btnPrepare);
		
		JLabel lblTraining = new JLabel("Training");
		lblTraining.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblTraining.setBounds(137, 11, 77, 18);
		add(lblTraining);
		
		JLabel lblTesting = new JLabel("Testing");
		lblTesting.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblTesting.setBounds(498, 15, 70, 18);
		add(lblTesting);
		
		JPanel inputPanel = new JPanel();
		inputPanel.setBorder(new TitledBorder(null, "Input", TitledBorder.CENTER, TitledBorder.TOP, null, null));
		inputPanel.setBounds(400, 63, 260, 222);
		add(inputPanel);
		
		inputLabel = new JLabel();
		inputPanel.add(inputLabel);
		
		JButton btnBrowse = new JButton("browse");
		btnBrowse.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				browseInput();
			}
		});
		btnBrowse.setBounds(400, 296, 89, 23);
		add(btnBrowse);
		
		JButton classifyButton = new JButton("classify");
		classifyButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				classify();
			}
		});
		classifyButton.setBounds(571, 296, 89, 23);
		add(classifyButton);
		
		   
	    trainingChooser = new JFileChooser(); 
	    trainingChooser.setCurrentDirectory(null);
	    trainingChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
	    //
	    // disable the "All files" option.
	    //
	    trainingChooser.setAcceptAllFileFilterUsed(false);
	    
	    FileFilter filter = new FileNameExtensionFilter("JPEG file", "jpg", "jpeg", "png", "gif");
	    testingChooser = new JFileChooser();
	    testingChooser.setFileFilter(filter);
	    testingChooser.setAcceptAllFileFilterUsed(false);
		
	}
	
	private void selectDirectory() {
		if (trainingChooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) 
			directoryField.setText(trainingChooser.getSelectedFile()+"");
	}
	
	private void prepareTrainingData() {
		if( directoryField.getText().equals("") ) {
			JOptionPane.showMessageDialog(this, "No directory selected.");
			return;
		}
		PreparingDialog dialog = new PreparingDialog();
		dialog.setVisible(true);
		fHandler = new FileHandler(directoryField.getText(), dArea, dialog);
		Thread thread = new Thread(fHandler);
		thread.start();
		
	}
	
	
	private ImageHandler iHandler = new ImageHandler();
	private FileHandler fHandler;
	
	ABC abc;
	private void trainNetwork() {
		setComponents();
		abc = new ABC( this, (int)runtimeSpinner.getValue(), (int)cycleSpinner.getValue(), (int)employedSpinner.getValue(), 66);
		abc.setTrainingData(input_data, output_data);
		Thread thread = new Thread(abc);
		thread.start();
	}
	
	/**
	 *  set gui when training
	 */
	private void setComponents() {
		runtimeBar.setMinimum(0);
		runtimeBar.setMaximum((int)runtimeSpinner.getValue());	
		training_input = fHandler.getTrainingInput();
		training_output = fHandler.getTrainingOutput();
		input_data = iHandler.createInputVectorArray(training_input);
		output_data = iHandler.createOutputVectorArray(training_output);
	}

	public void incrementCycle(int percent) {
		cycleBar.setValue(percent);
	}
	
	public void incrementRuntime(int percent) {
		runtimeBar.setValue(percent);
	}
	
	
	private BufferedImage forTesting;
	private void browseInput() {
		 if(testingChooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
			 try {
				 forTesting = ImageIO.read(new File(testingChooser.getSelectedFile()+""));
				 forTesting = iHandler.resize(forTesting, (int) (180/((float)forTesting.getHeight())*forTesting.getWidth()), 180);
				 inputLabel.setIcon(new ImageIcon(forTesting));
			} catch (IOException e) {
				e.printStackTrace();
			}
		 }
	}
	
	private void classify() {
		forTesting = iHandler.extract(forTesting);
		double[] features = iHandler.getFeatures(forTesting);
		
		double[] output = abc.test(features);
		
		dArea.append("****************RESULT******************\n");
		
		for( int i = 0; i < output.length; i++ ) 
			dArea.append(  output[i] +"\t ==> "+ Math.round(output[i]) +"\n");
		
		dArea.append("**************END RESULT****************\n");
	}
	
	public void print(String message) {
		dArea.append(message);
	}
	
}
