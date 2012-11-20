package ui;


import javax.imageio.ImageIO;
import javax.swing.JPanel;

import utilities.DataLoader;
import utilities.FileLoader;
import utilities.FileTypeFilter;
import utilities.ImageLoader;
import utilities.NetworkConfiguration;

import dialogs.LoadingDialog;
import dialogs.SettingDialog;

import abcnn.ABC;
import abcnn.Classifier;
import abcnn.MLPNetwork;

import javax.swing.border.TitledBorder;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.BorderFactory;
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
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
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

	private JFileChooser chooser;

	private JPanel tPane;
	private BatchPane batchPane;
	private SoloPane soloPane;
	private JPanel leftPane;
	
	private JLabel timeLabel, mseLabel, statusLabel;

	private JButton loadB, prepareB, trainB, resetB;
	
	private JSpinner employedSpinner;
	private JSpinner cycleSpinner;
	private JSpinner runtimeSpinner;
	
	private JProgressBar cycleBar;
	private JProgressBar runtimeBar;
	private JTextField directoryField;
	
	private String[] testingTypes = {"Batch", "One by One"};
	private static String STAT1 = "Load trained data or train network.";
	private static String STAT2 = "Loaded trained data. Ready for testing.";
	private static String STAT3 = "Network trained. Ready for testing.";
	public static String STAT4 = "Training data loaded. Ready for training.";
	
	private double[] weights = new double[NetworkConfiguration.DIMENSIONS];
	private FileLoader fileLoader;
	
	private AppFrame appFrame;

	private Classifier classifier;
	
	public ABCNNPane(AppFrame appFrame) {
		this.appFrame = appFrame;
		this.setSize(740, 600);
		setLayout(null);
		putClientProperty("textureType", new Integer(TextureUtils.WINDOW_TEXTURE_TYPE));
		
		classifier = new Classifier(appFrame, this);
		
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
		statPanel.setBounds(-1, 562, 730, 27);
		add(statPanel);
		statPanel.setLayout(null);
		
		JLabel lblStatus = new JLabel("Status:");
		lblStatus.setForeground(Color.BLACK);
		lblStatus.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblStatus.setHorizontalAlignment(SwingConstants.RIGHT);
		lblStatus.setBounds(0, 4, 43, 20);
		statPanel.add(lblStatus);
		
		statusLabel = new JLabel(STAT1);
		statusLabel.setForeground(new Color(153, 0, 0));
		statusLabel.setBounds(48, 4, 243, 20);
		statPanel.add(statusLabel);
		
		JPanel panel = new JPanel();
		panel.setBounds(363, 84, 33, 467);
		add(panel);
		panel.setLayout(null);
		
		JLabel label = new JLabel(new ImageIcon("src/images/center.png"));
		label.setBounds(0, 0, 33, 467);
		panel.add(label);
		
	}

	private void initLeftPane() {
		leftPane = new JPanel();
		leftPane.setBounds(-1, 84, 373, 467);
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
		
		JLabel lblEmployedBees = new JLabel("Population Size:");
		lblEmployedBees.setHorizontalAlignment(SwingConstants.RIGHT);
		lblEmployedBees.setBounds(11, 30, 111, 20);
		paramPanel.add(lblEmployedBees);
		
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
		employedSpinner.setBounds(132, 28, 50, 24);
		paramPanel.add(employedSpinner);
		
		JLabel lblMacCycle_1 = new JLabel("Mac Cycle:");
		lblMacCycle_1.setHorizontalAlignment(SwingConstants.RIGHT);
		lblMacCycle_1.setBounds(21, 76, 101, 20);
		paramPanel.add(lblMacCycle_1);
		
		JLabel lblRuntime_1 = new JLabel("Runtime:");
		lblRuntime_1.setHorizontalAlignment(SwingConstants.RIGHT);
		lblRuntime_1.setBounds(169, 28, 88, 24);
		paramPanel.add(lblRuntime_1);
		
		cycleSpinner = new JSpinner();
		cycleSpinner.setModel(new SpinnerNumberModel(new Integer(2500), null, null, new Integer(1)));
		cycleSpinner.setBounds(132, 74, 50, 24);
		paramPanel.add(cycleSpinner);
		
		runtimeSpinner = new JSpinner();
		runtimeSpinner.setModel(new SpinnerNumberModel(new Integer(1), null, null, new Integer(1)));
		runtimeSpinner.setBounds(267, 28, 50, 24);
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
		
		JLabel lblMeanSquereError = new JLabel("Mean Square Error:");
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
		rightPane.setBounds(382, 84, 347, 467);
		add(rightPane);
		rightPane.setLayout(null);
		
		tPane = new JPanel();
		tPane.setBounds(27, 11, 298, 436);
		rightPane.add(tPane);
		tPane.setLayout(null);
		batchPane = new BatchPane(this, chooser, classifier);
		tPane.add(batchPane);
		
		soloPane = new SoloPane(this, chooser, classifier);
	}

	/**
	 * 
	 * init toolbar
	 */
	private void initToolbar() {
		JToolBar toolBar = new JToolBar();
		toolBar.setFloatable(false);
		toolBar.setBounds(-1, 0, 731, 40);
		add(toolBar);
		//toolBar.putClientProperty("textureType", new Integer(TextureUtils.WINDOW_TEXTURE_TYPE));
		
		loadB = new JButton(new ImageIcon("src/images/f.png"));
		loadB.setToolTipText("Load data");
		toolBar.add(loadB);
		loadB.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				loadTrainedData();
			}
		});
		

		prepareB = new JButton(new ImageIcon("src/images/prepare.png"));
		prepareB.setToolTipText("Prepare");
		toolBar.add(prepareB);
		prepareB.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				//prepareTrainingData();
				classifier.loadImages();
			}
		});
		
		
		JButton saveButton = new JButton(new ImageIcon("src/images/save.png"));
		saveButton.setToolTipText("Save");
		toolBar.add(saveButton);
		saveButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				saveWeights();
			}
		});
		
		final SettingDialog settingDialog = new SettingDialog(this);
		
		JButton settingsButton = new JButton(new ImageIcon("src/images/setting.png"));
		settingsButton.setToolTipText("Setting");
		toolBar.add(settingsButton);
		settingsButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				settingDialog.setVisible(true);
			}
		});		
		
		for( int i = 0; i<52; i++ )
			toolBar.addSeparator();

		resetB = new JButton(new ImageIcon("src/images/reset2.png"));
		resetB.setToolTipText("Reset");
		resetB.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				reset();
				soloPane.reset();
				batchPane.reset();
			}
		});
		toolBar.add(resetB);
		
		JToolBar toolBar2 = new JToolBar();
		toolBar2.setFloatable(false);
		toolBar2.setBounds(-1, 41, 731, 32);
		add(toolBar2);
		toolBar2.setBorder(new LineBorder(SystemColor.activeCaptionBorder));
		
		toolBar2.addSeparator();
		
		trainB = new JButton(new ImageIcon("src/images/play2.png"));
		trainB.setToolTipText("Train NN");
		toolBar2.add(trainB);
		trainB.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				classifier.train((int)runtimeSpinner.getValue(), (int)cycleSpinner.getValue(), (int)employedSpinner.getValue());
			}
		});
		

		JButton stopButton = new JButton(new ImageIcon("src/images/stop2.png"));
		toolBar2.add(stopButton);
		
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
	
	private void saveWeights() {
		if(!classifier.isNewTraining()) 
			JOptionPane.showMessageDialog(appFrame, "No training has be done recently.", "Error", JOptionPane.WARNING_MESSAGE);
		else {
			FileFilter filter = new FileTypeFilter(".txt", "Text files");
			chooser = new JFileChooser();
			chooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
			chooser.setFileFilter(filter);
			chooser.setAcceptAllFileFilterUsed(false);
			
			BufferedWriter bufferedWriter = null;
			if(chooser.showSaveDialog(appFrame) == JFileChooser.APPROVE_OPTION) {
			    File file = chooser.getSelectedFile();
			    FileWriter fileWriter;
				try {
					fileWriter = new FileWriter(file);
					bufferedWriter = new BufferedWriter(fileWriter);
					weights = classifier.getWeights();
					for( int i = 0; i < weights.length; i++ ) {
						bufferedWriter.write(weights[i]+"");
						bufferedWriter.newLine();
					}
				} catch (FileNotFoundException ex) {
		            ex.printStackTrace();
		        } catch (IOException e) {
					e.printStackTrace();
				}finally {
		            try {
		                if (bufferedWriter != null) {
		                    bufferedWriter.flush();
		                    bufferedWriter.close();
		                }
		            } catch (IOException ex) {
		                ex.printStackTrace();
		            }
		        }
				
			}
		}
	}
	
	public void initComponents() {
		cycleBar.setMinimum(0);
		cycleBar.setMaximum((int)cycleSpinner.getValue());
		runtimeBar.setMinimum(0);
		runtimeBar.setMaximum((int)runtimeSpinner.getValue());	
		cycleBar.setValue(0);
		runtimeBar.setValue(0);
	}

	public void incrementCycle(int percent) {
		cycleBar.setValue(percent);
	}
	
	public void incrementRuntime(int percent) {
		runtimeBar.setValue(percent);
	}
	
	public void displayTrainingResult(double MSE, double elapsedTime) {
		timeLabel.setText( elapsedTime + "" );
		mseLabel.setText( MSE + "" );
		setStatus(STAT3, Color.BLUE);
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
			classifier.loadWeights(weights);
			setStatus(STAT2, Color.BLUE);
			JOptionPane.showMessageDialog(appFrame, "Loaded trained data.", "Message", JOptionPane.PLAIN_MESSAGE);
		}
	}
	
	public void setStatus(String status, Color color) {
		statusLabel.setText(status);
		statusLabel.setForeground(color);
	}
	
	public String getFilePath() {
		return directoryField.getText();
	}
	
	private void reset() {
		setStatus(STAT1,new Color(153, 0, 0));
		cycleBar.setValue(0);
		runtimeBar.setValue(0);
		timeLabel.setText("0");
		mseLabel.setText("0.0");
		classifier.reset();
	}

	public void setTestOption(int selected) {
		if( selected == SettingDialog.INDIVIDUAL ) {
			updateRightPane(batchPane, soloPane);
		}
		else {
			updateRightPane(soloPane, batchPane);
		}
	}
}
