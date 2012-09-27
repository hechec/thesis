package ui;


import javax.imageio.ImageIO;
import javax.swing.JPanel;

import utilities.ImageLoader;
import utilities.ImageHandler;

import dialogs.PreparingDialog;

import abcnn.ABC;
import abcnn.MLPNetwork;

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

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.JTextField;
import java.awt.Font;
import javax.swing.UIManager;

public class ABCNNPane extends JPanel {

	private PreparingDialog dialog;
	
	private JFileChooser chooser;
	private FileFilter fileFilter;
	
	private JLabel inputLabel;
	private JLabel timeLabel, mseLabel, classLabel;
	
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
	
	private String[] classes = {"Green","Breaker","Turning","Pink","Light Red","Red"};
	
	private AppFrame appFrame;
	
	public ABCNNPane(AppFrame appFrame) {
		this.appFrame = appFrame;
		this.setSize(670, 570);
		setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBorder(new TitledBorder(null, "ABC Parameters", TitledBorder.CENTER, TitledBorder.TOP, null, null));
		panel.setBounds(26, 253, 326, 230);
		add(panel);
		panel.setLayout(null);
		
		JLabel lblEmployedBees = new JLabel("Employed Bees:");
		lblEmployedBees.setHorizontalAlignment(SwingConstants.RIGHT);
		lblEmployedBees.setBounds(10, 30, 88, 20);
		panel.add(lblEmployedBees);
		
		JLabel lblOnlookerBees = new JLabel("Onlooker Bees:");
		lblOnlookerBees.setHorizontalAlignment(SwingConstants.RIGHT);
		lblOnlookerBees.setBounds(10, 74, 88, 24);
		panel.add(lblOnlookerBees);
		
		JLabel lblMacCycle = new JLabel("cycle:");
		lblMacCycle.setHorizontalAlignment(SwingConstants.RIGHT);
		lblMacCycle.setBounds(10, 141, 50, 20);
		panel.add(lblMacCycle);
		
		JLabel lblRuntime = new JLabel("run:");
		lblRuntime.setHorizontalAlignment(SwingConstants.RIGHT);
		lblRuntime.setBounds(10, 180, 50, 20);
		panel.add(lblRuntime);
		
		employedSpinner = new JSpinner();
		employedSpinner.setModel(new SpinnerNumberModel(new Integer(50), null, null, new Integer(1)));
		employedSpinner.setBounds(108, 30, 50, 24);
		panel.add(employedSpinner);
		
		onlookerSpinner = new JSpinner();
		onlookerSpinner.setModel(new SpinnerNumberModel(new Integer(50), null, null, new Integer(1)));
		onlookerSpinner.setBounds(108, 74, 50, 24);
		panel.add(onlookerSpinner);
		
		JLabel lblMacCycle_1 = new JLabel("Mac Cycle:");
		lblMacCycle_1.setHorizontalAlignment(SwingConstants.RIGHT);
		lblMacCycle_1.setBounds(151, 30, 88, 20);
		panel.add(lblMacCycle_1);
		
		JLabel lblRuntime_1 = new JLabel("Runtime:");
		lblRuntime_1.setHorizontalAlignment(SwingConstants.RIGHT);
		lblRuntime_1.setBounds(151, 74, 88, 24);
		panel.add(lblRuntime_1);
		
		cycleSpinner = new JSpinner();
		cycleSpinner.setModel(new SpinnerNumberModel(new Integer(2500), null, null, new Integer(1)));
		cycleSpinner.setBounds(249, 30, 50, 24);
		panel.add(cycleSpinner);
		
		runtimeSpinner = new JSpinner();
		runtimeSpinner.setModel(new SpinnerNumberModel(new Integer(5), null, null, new Integer(1)));
		runtimeSpinner.setBounds(249, 74, 50, 24);
		panel.add(runtimeSpinner);
		
		cycleBar = new JProgressBar();
		cycleBar.setBounds(70, 141, 229, 20);
		panel.add(cycleBar);
		
		runtimeBar = new JProgressBar();
		runtimeBar.setBounds(70, 180, 229, 20);
		panel.add(runtimeBar);
		
		JButton trainButton = new JButton("TRAIN");
		trainButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				trainNetwork();
			}
		});
		trainButton.setBounds(26, 215, 89, 27);
		add(trainButton);
		
		directoryField = new JTextField();
		directoryField.setBounds(26, 63, 242, 27);
		add(directoryField);
		directoryField.setColumns(10);
		directoryField.setFocusable(false);
		
		directoryField.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				selectDirectory();
			}
		});
		
		JButton selectButton = new JButton("CUSTOM");
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
		
		JButton btnPrepare = new JButton("PREPARE");
		btnPrepare.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				prepareTrainingData();
			}
		});
		btnPrepare.setBounds(26, 101, 89, 27);
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
		inputPanel.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "INPUT IMAGE", TitledBorder.CENTER, TitledBorder.TOP, null, null));
		inputPanel.setBounds(400, 43, 260, 222);
		add(inputPanel);
		
		inputLabel = new JLabel();
		inputPanel.add(inputLabel);
		
		JButton btnBrowse = new JButton("CUSTOM");
		btnBrowse.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				browseInput();
			}
		});
		btnBrowse.setBounds(400, 276, 89, 27);
		add(btnBrowse);
		
		JButton classifyButton = new JButton("CLASSIFY");
		classifyButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				classify();
			}
		});
		classifyButton.setBounds(571, 276, 89, 27);
		add(classifyButton);
		
		JPanel resultsPane = new JPanel();
		resultsPane.setBorder(new TitledBorder(null, "RESULTS", TitledBorder.CENTER, TitledBorder.TOP, null, null));
		resultsPane.setBounds(400, 330, 260, 153);
		add(resultsPane);
		resultsPane.setLayout(null);
		
		JLabel lblTrainingTime = new JLabel("Training Time (seconds):");
		lblTrainingTime.setHorizontalAlignment(SwingConstants.RIGHT);
		lblTrainingTime.setBounds(10, 28, 139, 22);
		resultsPane.add(lblTrainingTime);
		
		JLabel lblMeanSquereError = new JLabel("Mean Squere Error:");
		lblMeanSquereError.setHorizontalAlignment(SwingConstants.RIGHT);
		lblMeanSquereError.setBounds(25, 61, 124, 22);
		resultsPane.add(lblMeanSquereError);
		
		JLabel lblClassification = new JLabel("CLASSIFICATION:");
		lblClassification.setHorizontalAlignment(SwingConstants.RIGHT);
		lblClassification.setBounds(25, 108, 124, 22);
		resultsPane.add(lblClassification);
		
		timeLabel = new JLabel("0");
		timeLabel.setBounds(169, 28, 64, 22);
		resultsPane.add(timeLabel);
		
		mseLabel = new JLabel("0.0");
		mseLabel.setBounds(169, 61, 64, 22);
		resultsPane.add(mseLabel);
		
		classLabel = new JLabel("--");
		classLabel.setBounds(169, 108, 64, 22);
		resultsPane.add(classLabel);
		
		   
	   //trainingChooser = new JFileChooser(); 
	    //trainingChooser.setCurrentDirectory(null);
	    //trainingChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
	    //
	    // disable the "All files" option.
	    //
	    
	    
	    fileFilter = new FileNameExtensionFilter("JPEG file", "jpg", "jpeg", "png", "gif");
	    
	    chooser = new JFileChooser();
	    chooser.setAcceptAllFileFilterUsed(false);
	}
	
	private void selectDirectory() {
		chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		if (chooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) 
			directoryField.setText(chooser.getSelectedFile()+"");
	}
	
	private void prepareTrainingData() {
		if( directoryField.getText().equals("") ) {
			JOptionPane.showMessageDialog(this, "No directory selected.");
			return;
		}
		dialog = new PreparingDialog(appFrame);
		iLoader = new ImageLoader(appFrame, directoryField.getText(), dialog);
		Thread thread = new Thread(iLoader);
		thread.start();
		
	}
	
	
	private ImageHandler iHandler = new ImageHandler();
	private ImageLoader iLoader;
	
	ABC abc;
	private void trainNetwork() {
		setComponents();
		abc = new ABC( this, (int)runtimeSpinner.getValue(), (int)cycleSpinner.getValue(), (int)employedSpinner.getValue(), 106); //126);
		abc.setTrainingData(input_data, output_data);
		abc.start();
	}
	
	/**
	 *  set gui when training
	 */
	private void setComponents() {
		cycleBar.setMinimum(0);
		cycleBar.setMaximum((int)cycleSpinner.getValue());
		runtimeBar.setMinimum(0);
		runtimeBar.setMaximum((int)runtimeSpinner.getValue());	
		//training_input = fHandler.getTrainingInput();
		//training_output = fHandler.getTrainingOutput();
		input_data = iLoader.getTrainingInput();//iHandler.createInputVectorArray(training_input);
		output_data = iLoader.getTrainingOutput();//iHandler.createOutputVectorArray(training_output);
	}

	public void incrementCycle(int percent) {
		cycleBar.setValue(percent);
	}
	
	public void incrementRuntime(int percent) {
		runtimeBar.setValue(percent);
	}
	
	
	private BufferedImage forTesting;
	private void browseInput() {
		 chooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
		 chooser.setFileFilter(fileFilter);
		 chooser.setAcceptAllFileFilterUsed(false);
		 if(chooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
			 try {
				 forTesting = ImageIO.read(new File(chooser.getSelectedFile()+""));
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
		
		MLPNetwork classifier = abc.getBestBee();
		double[] output = classifier.test(features);
		
		int classIndex = normalizeOutput(output);
		
		classLabel.setText( classes[classIndex] );
		
	}

	private int normalizeOutput(double[] output) {
		int maxIndex = 0;	
		for( int i = 1; i < output.length; i++ )
			if( output[i] > output[maxIndex] )
				maxIndex = i;
		return maxIndex;
	}

	public void showResult(double bestMin, double elapsedTime) {
		timeLabel.setText( elapsedTime + "" );
		mseLabel.setText( bestMin + "" );
	}
}
