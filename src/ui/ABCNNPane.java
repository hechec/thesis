package ui;


import javax.imageio.ImageIO;
import javax.swing.JPanel;

import utilities.FileLoader;
import utilities.FileTypeFilter;
import utilities.ImageLoader;
import utilities.ImageHandler;
import utilities.NetworkConfiguration;

import dialogs.LoadingDialog;

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
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.JTextField;
import java.awt.Font;
import javax.swing.UIManager;
import javax.swing.JComboBox;
import javax.swing.border.LineBorder;
import java.awt.Color;
import java.awt.SystemColor;
import javax.swing.JToolBar;

import org.jvnet.substance.SubstanceLookAndFeel;

import com.jtattoo.plaf.texture.TextureUtils;

public class ABCNNPane extends JPanel {

	private LoadingDialog dialog;
	
	private JFileChooser chooser;

	private JPanel tPane;
	private BatchPane batchPane;
	private SoloPane soloPane;
	private JPanel leftPane;
	
	private JLabel timeLabel, mseLabel, statusLabel;

	private JButton loadB, prepareB, trainB, resetB;
	
	private JSpinner employedSpinner;
	private JSpinner onlookerSpinner;
	private JSpinner cycleSpinner;
	private JSpinner runtimeSpinner;
	
	private JProgressBar cycleBar;
	private JProgressBar runtimeBar;
	private JTextField directoryField;
	
	private double[][] input_data;
	private double[][] output_data;
	
	private String[] testingTypes = {"Batch", "One by One"};
	private String status1 = "Load trained data or train network.";
	private String status2 = "Loaded trained data. Ready for testing.";
	private String status3 = "Network trained. Ready for testing.";
	private String status4 = "Training data loaded. Ready for training.";
	
	private double[] weights = new double[NetworkConfiguration.DIMENSIONS];
	private FileLoader fileLoader;
	
	private AppFrame appFrame;

	private ImageHandler iHandler = new ImageHandler();
	private ImageLoader iLoader;
	
	private ABC abc;
	
	public boolean isTrained = false;
	private boolean isPrepared = false;
	
	
	public ABCNNPane(AppFrame appFrame) {
		this.appFrame = appFrame;
		this.setSize(740, 570);
		setLayout(null);
		putClientProperty("textureType", new Integer(TextureUtils.WINDOW_TEXTURE_TYPE));
		
		chooser = new JFileChooser();
	    chooser.setAcceptAllFileFilterUsed(false);
		fileLoader = new FileLoader(appFrame);

		initToolbar();
		initLeftPane();
		initRightPane();
		initStatPanel();
	}
	
	private void initStatPanel() {
		JPanel statPanel = new JPanel();
		statPanel.setBorder(new LineBorder(SystemColor.activeCaptionBorder));
		statPanel.setBounds(0, 522, 730, 27);
		add(statPanel);
		statPanel.setLayout(null);
		
		JLabel lblStatus = new JLabel("Status:");
		lblStatus.setForeground(Color.BLACK);
		lblStatus.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblStatus.setHorizontalAlignment(SwingConstants.RIGHT);
		lblStatus.setBounds(0, 4, 43, 20);
		statPanel.add(lblStatus);
		
		statusLabel = new JLabel(status1);
		statusLabel.setFont(new Font("Tahoma", Font.PLAIN, 12));
		statusLabel.setForeground(new Color(153, 0, 0));
		statusLabel.setBounds(48, 4, 243, 20);
		statPanel.add(statusLabel);
		
	}

	private void initLeftPane() {
		leftPane = new JPanel();
		leftPane.setBounds(10, 50, 373, 461);
		add(leftPane);
		leftPane.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Image Directory:");
		lblNewLabel.setBounds(20, 45, 118, 22);
		leftPane.add(lblNewLabel);
		
		directoryField = new JTextField();
		directoryField.setBounds(20, 72, 242, 27);
		leftPane.add(directoryField);
		directoryField.setColumns(10);
		directoryField.setFocusable(false);
		directoryField.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				selectDirectory();
			}
		});
		
		JButton selectButton = new JButton("CUSTOM");
		selectButton.setBounds(268, 72, 77, 27);
		leftPane.add(selectButton);
		
		selectButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				selectDirectory();
			}
		});
		
		JPanel paramPanel = new JPanel();
		paramPanel.setBounds(19, 150, 329, 230);
		leftPane.add(paramPanel);
		paramPanel.setBorder(new TitledBorder(null, "ABC Parameters", TitledBorder.CENTER, TitledBorder.TOP, null, null));
		paramPanel.setLayout(null);
		
		JLabel lblEmployedBees = new JLabel("Employed Bees:");
		lblEmployedBees.setHorizontalAlignment(SwingConstants.RIGHT);
		lblEmployedBees.setBounds(11, 30, 98, 20);
		paramPanel.add(lblEmployedBees);
		
		JLabel lblOnlookerBees = new JLabel("Onlooker Bees:");
		lblOnlookerBees.setHorizontalAlignment(SwingConstants.RIGHT);
		lblOnlookerBees.setBounds(11, 74, 98, 24);
		paramPanel.add(lblOnlookerBees);
		
		JLabel lblMacCycle = new JLabel("cycle:");
		lblMacCycle.setHorizontalAlignment(SwingConstants.RIGHT);
		lblMacCycle.setBounds(10, 141, 50, 20);
		paramPanel.add(lblMacCycle);
		
		JLabel lblRuntime = new JLabel("run:");
		lblRuntime.setHorizontalAlignment(SwingConstants.RIGHT);
		lblRuntime.setBounds(10, 180, 50, 20);
		paramPanel.add(lblRuntime);
		
		employedSpinner = new JSpinner();
		employedSpinner.setModel(new SpinnerNumberModel(new Integer(50), null, null, new Integer(1)));
		employedSpinner.setBounds(119, 28, 50, 24);
		paramPanel.add(employedSpinner);
		
		onlookerSpinner = new JSpinner();
		onlookerSpinner.setModel(new SpinnerNumberModel(new Integer(50), null, null, new Integer(1)));
		onlookerSpinner.setBounds(119, 74, 50, 24);
		paramPanel.add(onlookerSpinner);
		
		JLabel lblMacCycle_1 = new JLabel("Mac Cycle:");
		lblMacCycle_1.setHorizontalAlignment(SwingConstants.RIGHT);
		lblMacCycle_1.setBounds(168, 30, 88, 20);
		paramPanel.add(lblMacCycle_1);
		
		JLabel lblRuntime_1 = new JLabel("Runtime:");
		lblRuntime_1.setHorizontalAlignment(SwingConstants.RIGHT);
		lblRuntime_1.setBounds(168, 74, 88, 24);
		paramPanel.add(lblRuntime_1);
		
		cycleSpinner = new JSpinner();
		cycleSpinner.setModel(new SpinnerNumberModel(new Integer(2500), null, null, new Integer(1)));
		cycleSpinner.setBounds(266, 28, 50, 24);
		paramPanel.add(cycleSpinner);
		
		runtimeSpinner = new JSpinner();
		runtimeSpinner.setModel(new SpinnerNumberModel(new Integer(5), null, null, new Integer(1)));
		runtimeSpinner.setBounds(266, 74, 50, 24);
		paramPanel.add(runtimeSpinner);
		
		cycleBar = new JProgressBar();
		cycleBar.setBounds(70, 141, 229, 20);
		paramPanel.add(cycleBar);
		
		runtimeBar = new JProgressBar();
		runtimeBar.setBounds(70, 180, 229, 20);
		paramPanel.add(runtimeBar);
		
		JLabel lblTrainingTime = new JLabel("Training Time (seconds):");
		lblTrainingTime.setBounds(14, 391, 147, 22);
		leftPane.add(lblTrainingTime);
		lblTrainingTime.setHorizontalAlignment(SwingConstants.RIGHT);
		
		JLabel lblMeanSquereError = new JLabel("Mean Squere Error:");
		lblMeanSquereError.setBounds(29, 413, 132, 22);
		leftPane.add(lblMeanSquereError);
		lblMeanSquereError.setHorizontalAlignment(SwingConstants.RIGHT);
		
		timeLabel = new JLabel("0");
		timeLabel.setBounds(171, 391, 169, 22);
		leftPane.add(timeLabel);
		
		mseLabel = new JLabel("0.0");
		mseLabel.setBounds(171, 413, 169, 22);
		leftPane.add(mseLabel);
	}

	private void initRightPane() {
		JPanel rightPane = new JPanel();
		rightPane.setBounds(393, 50, 325, 461);
		add(rightPane);
		rightPane.setLayout(null);
		//add(btnPrepare);
		
		JLabel lblTestBy = new JLabel("Test by:");
		lblTestBy.setBounds(27, 22, 77, 18);
		rightPane.add(lblTestBy);
		
		JComboBox comboBox_1 = new JComboBox(testingTypes);
		comboBox_1.setBounds(100, 20, 197, 23);
		rightPane.add(comboBox_1);
		
		tPane = new JPanel();
		tPane.setBounds(27, 51, 292, 396);
		rightPane.add(tPane);
		tPane.setLayout(null);
		batchPane = new BatchPane(appFrame, this, chooser);
		tPane.add(batchPane);
		
		soloPane = new SoloPane(this, chooser);
		
		comboBox_1.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent event) {
				JComboBox comboBox = (JComboBox) event.getSource();
				if( comboBox.getSelectedIndex() == 1 ) 
					updateRightPane(batchPane, soloPane);
				else 
					updateRightPane(soloPane, batchPane);
			}
		});
	}

	/**
	 * 
	 * init toolbar
	 */
	private void initToolbar() {
		JToolBar toolBar = new JToolBar();
		toolBar.setFloatable(false);
		toolBar.setBounds(0, 0, 740, 40);
		add(toolBar);
		//toolBar.putClientProperty("textureType", new Integer(TextureUtils.WINDOW_TEXTURE_TYPE));
		
		loadB = new JButton(new ImageIcon("src/images/load.png"));
		loadB.setToolTipText("Load Trained Data");
		toolBar.add(loadB);
		loadB.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				loadTrainedData();
			}
		});
		
		toolBar.addSeparator();
		toolBar.addSeparator();
		
		prepareB = new JButton("Prepare");
		toolBar.add(prepareB);
		prepareB.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				prepareTrainingData();
			}
		});
		
		trainB = new JButton(new ImageIcon("src/images/play.png"));
		trainB.setToolTipText("Train NN");
		toolBar.add(trainB);
		trainB.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				trainNetwork();
			}
		});
		
		resetB = new JButton(new ImageIcon("src/images/reset.png"));
		resetB.setToolTipText("Reset");
		resetB.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				reset();
				soloPane.reset();
			}
		});
		toolBar.add(resetB);
	}

	private void updateRightPane(JPanel toRemove, JPanel toAdd) {
		tPane.remove(toRemove);
		tPane.add(toAdd);
		updateUI();
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
		dialog = new LoadingDialog(appFrame);
		iLoader = new ImageLoader(this, directoryField.getText(), dialog);
	}
	
	private void trainNetwork() {
		if(!isPrepared) {
			JOptionPane.showMessageDialog(appFrame, "No Training Data Loaded.", "Error", JOptionPane.WARNING_MESSAGE);
			return;
		}
		setComponents();
		abc = new ABC( this, (int)runtimeSpinner.getValue(), (int)cycleSpinner.getValue(), (int)employedSpinner.getValue(), NetworkConfiguration.DIMENSIONS); //126);
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
		input_data = iLoader.getInputVector();//iHandler.createInputVectorArray(training_input);
		output_data = iLoader.getOutputVector();//iHandler.createOutputVectorArray(training_output);
	}

	public void incrementCycle(int percent) {
		cycleBar.setValue(percent);
	}
	
	public void incrementRuntime(int percent) {
		runtimeBar.setValue(percent);
	}

	public void returnResult(double MSE, double[] weights, double elapsedTime) {
		timeLabel.setText( elapsedTime + "" );
		mseLabel.setText( MSE + "" );
		statusLabel.setText(status3);
		statusLabel.setForeground(Color.GREEN);
		isTrained = true;
	}
	
	private void loadTrainedData() {
		FileFilter filter = new FileTypeFilter(".txt", "Text files");
		chooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
		chooser.setFileFilter(filter);
		chooser.setAcceptAllFileFilterUsed(false);
		if (chooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
			weights = fileLoader.loadTrainedData(chooser.getSelectedFile());
			if( weights == null )
				return;
			isTrained = true;
			statusLabel.setText(status2);
			statusLabel.setForeground(Color.BLUE);
			JOptionPane.showMessageDialog(appFrame, "Loaded trained data.", "Message", JOptionPane.PLAIN_MESSAGE);
		}
	}

	public void setPrepared(boolean b) {
		isPrepared = b;
		statusLabel.setText(status4);
		statusLabel.setForeground(Color.GREEN);
	}
	
	public int classify(double[] input) {
		
		MLPNetwork classifier = new MLPNetwork(weights);
		double[] output = classifier.test(input);
		
		int classIndex = normalizeOutput(output);
		
		return classIndex;
	}
	
	private int normalizeOutput(double[] output) {
		int maxIndex = 0;	
		for( int i = 1; i < output.length; i++ )
			if( output[i] > output[maxIndex] )
				maxIndex = i;
		return maxIndex;
	}

	private void reset() {
		isTrained = false;
		isPrepared = false;
		statusLabel.setText(status1);
		statusLabel.setForeground(new Color(153, 0, 0));
		cycleBar.setValue(0);
		runtimeBar.setValue(0);
		timeLabel.setText("0");
		mseLabel.setText("0.0");
	}

	public double[] getOptimalWeights() {
		return weights;
	}
}
