package ui;

import com.jtattoo.plaf.texture.TextureUtils;

import custom.MyTextField;

import javax.swing.*;

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import javax.swing.border.*;
import javax.swing.filechooser.FileFilter;

import dialogs.SettingDialog;
import abcnn.Classifier;
import ui.dialogs.Settings;
import util.DataLoader;
import util.FileTypeFilter;
import util.FixedDataLoader;
import util.NNWeightsLoader;

import static util.NNConstants.*;

public class ABCNNTab extends JPanel 
{
	private JFileChooser chooser;

	private JLabel timeLabel, mseLabel;
	private JButton loadB, prepareB, trainB, resetB;
	private JPanel userdefinedPanel, randomPanel, tPane, leftPane;
	
	private JSpinner employedSpinner, onlookerSpinner, cycleSpinner, runtimeSpinner;
	
	private JProgressBar cycleBar;
	private JProgressBar runtimeBar;
	private JTextField randomTextField;
	
	private double[] weights = new double[DIMENSIONS];
	private NNWeightsLoader fileLoader;
	
	private AppFrame appFrame;
	private Classifier classifier;
	private BottomPane bottomPane;
	private BatchPane batchPane;
	private SoloPane soloPane;
	private Settings settings;
	private JTextField trainDataTextField;
	private JTextField testDataTextField;
	
	public ABCNNTab(AppFrame appFrame) 
	{
		this.appFrame = appFrame;
		this.setSize(740, 600);
		setLayout(null);
		putClientProperty("textureType", new Integer(TextureUtils.WINDOW_TEXTURE_TYPE));
		
		classifier = new Classifier(appFrame, this);
		
		chooser = new JFileChooser();
	    chooser.setAcceptAllFileFilterUsed(false);
		fileLoader = new NNWeightsLoader(appFrame);
		
		initToolbar();
		initLeftPane();
		initRightPane();
		initStatPanel();
		
	}
	
	private void initStatPanel() 
	{
		bottomPane = new BottomPane(this);
		add(bottomPane);
		
		JPanel panel = new JPanel();
		panel.setBounds(363, 84, 33, 467);
		add(panel);
		panel.setLayout(null);
		
		JLabel label = new JLabel(new ImageIcon("src/images/center.png"));
		label.setBounds(0, 0, 33, 467);
		panel.add(label);
		
		userdefinedPanel = new JPanel();
		userdefinedPanel.setBounds(-1, 84, 367, 128);
		add(userdefinedPanel);
		userdefinedPanel.setLayout(null);
		
		trainDataTextField = new MyTextField("Select training data location");
		trainDataTextField.setBounds(20, 45, 242, 27);
		userdefinedPanel.add(trainDataTextField);
		trainDataTextField.setColumns(10);
		
		testDataTextField = new MyTextField("Select testing data location");
		testDataTextField.setColumns(10);
		testDataTextField.setBounds(20, 94, 242, 27);
		userdefinedPanel.add(testDataTextField);
		
		JButton trainDataButton = new JButton("CUSTOM");
		trainDataButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				selectDirectory(trainDataTextField);
			}
		});
		trainDataButton.setBounds(268, 45, 77, 27);
		userdefinedPanel.add(trainDataButton);
		
		JButton testDataButton = new JButton("CUSTOM");
		testDataButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				selectDirectory(testDataTextField);
			}
		});
		testDataButton.setBounds(268, 96, 77, 27);
		userdefinedPanel.add(testDataButton);
		
		randomPanel = new JPanel();
		randomPanel.setBounds(0, 84, 366, 133);
		add(randomPanel);
		randomPanel.setLayout(null);
		randomPanel.setVisible(false);
		
		JLabel lblNewLabel = new JLabel("Training/testing data location:");
		lblNewLabel.setBounds(25, 45, 191, 22);
		randomPanel.add(lblNewLabel);
		
		randomTextField = new MyTextField("");
		randomTextField.setBounds(25, 76, 242, 27);
		randomPanel.add(randomTextField);
		randomTextField.setColumns(10);
		randomTextField.setFocusable(false);
		
		JButton selectButton = new JButton("CUSTOM");
		selectButton.setBounds(279, 76, 77, 27);
		randomPanel.add(selectButton);
		
		selectButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				selectDirectory(randomTextField);
			}
		});
		randomTextField.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				selectDirectory(randomTextField);
			}
		});
		
	}

	private void initLeftPane() 
	{
		leftPane = new JPanel();
		leftPane.setBounds(-1, 212, 367, 339);
		add(leftPane);
		leftPane.setLayout(null);
		
		JPanel paramPanel = new JPanel();
		paramPanel.setBounds(14, 32, 338, 230);
		leftPane.add(paramPanel);
		paramPanel.setBorder(new TitledBorder(null, "ABC Parameters", TitledBorder.CENTER, TitledBorder.TOP, null, null));
		paramPanel.setLayout(null);
		
		JLabel lblEmployedBees = new JLabel("Employed Bees:");
		lblEmployedBees.setHorizontalAlignment(SwingConstants.RIGHT);
		lblEmployedBees.setBounds(0, 30, 111, 20);
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
		employedSpinner.setModel(new SpinnerNumberModel(new Integer(10), 1, null, new Integer(1)));
		employedSpinner.setBounds(121, 30, 50, 24);
		paramPanel.add(employedSpinner);

		JLabel lblOnlookerBees = new JLabel("Onlooker Bees:");
		lblOnlookerBees.setHorizontalAlignment(SwingConstants.RIGHT);
		lblOnlookerBees.setBounds(0, 78, 111, 20);
		paramPanel.add(lblOnlookerBees);
		
		onlookerSpinner = new JSpinner();
		onlookerSpinner.setModel(new SpinnerNumberModel(new Integer(10), 1, null, new Integer(1)));
		onlookerSpinner.setBounds(121, 78, 50, 24);
		paramPanel.add(onlookerSpinner);
		
		JLabel lblMacCycle_1 = new JLabel("Max Cycle:");
		lblMacCycle_1.setHorizontalAlignment(SwingConstants.RIGHT);
		lblMacCycle_1.setBounds(155, 30, 101, 20);
		paramPanel.add(lblMacCycle_1);
		
		JLabel lblRuntime_1 = new JLabel("Runtime:");
		lblRuntime_1.setHorizontalAlignment(SwingConstants.RIGHT);
		lblRuntime_1.setBounds(168, 74, 88, 24);
		paramPanel.add(lblRuntime_1);
		
		cycleSpinner = new JSpinner();
		cycleSpinner.setModel(new SpinnerNumberModel(new Integer(500), 1, null, new Integer(1)));
		cycleSpinner.setBounds(266, 28, 50, 24);
		paramPanel.add(cycleSpinner);
		
		runtimeSpinner = new JSpinner();
		runtimeSpinner.setModel(new SpinnerNumberModel(new Integer(1), 1, null, new Integer(1)));
		runtimeSpinner.setBounds(266, 74, 50, 24);
		paramPanel.add(runtimeSpinner);
		
		cycleBar = new JProgressBar();
		cycleBar.setBounds(70, 141, 229, 20);
		cycleBar.setForeground(SystemColor.textHighlight);
		paramPanel.add(cycleBar);
		
		runtimeBar = new JProgressBar();
		runtimeBar.setBounds(70, 180, 229, 20);
		runtimeBar.setForeground(SystemColor.textHighlight);
		paramPanel.add(runtimeBar);
		
		JLabel lblTrainingTime = new JLabel("Training Time (seconds):");
		lblTrainingTime.setBounds(10, 273, 147, 22);
		leftPane.add(lblTrainingTime);
		lblTrainingTime.setHorizontalAlignment(SwingConstants.RIGHT);
		
		JLabel lblMeanSquereError = new JLabel("Mean Square Error:");
		lblMeanSquereError.setBounds(25, 295, 132, 22);
		leftPane.add(lblMeanSquereError);
		lblMeanSquereError.setHorizontalAlignment(SwingConstants.RIGHT);
		
		timeLabel = new JLabel("0");
		timeLabel.setBounds(167, 273, 169, 22);
		leftPane.add(timeLabel);
		
		mseLabel = new JLabel("0.0");
		mseLabel.setBounds(167, 295, 169, 22);
		leftPane.add(mseLabel);
	}

	private void initRightPane() 
	{
		JPanel rightPane = new JPanel();
		rightPane.setBounds(394, 84, 335, 467);
		add(rightPane);
		rightPane.setLayout(null);
		
		tPane = new JPanel();
		tPane.setBounds(0, 0, 335, 467);
		rightPane.add(tPane);
		tPane.setLayout(null);
		batchPane = new BatchPane(this, chooser, classifier);
		tPane.add(batchPane);
		batchPane.setVisible(false);
		
		soloPane = new SoloPane(this, chooser, classifier);
		tPane.add(soloPane);
	}

	/**
	 * 
	 * init toolbar
	 */
	private void initToolbar() 
	{
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
				bottomPane.reset();
				
				if(Settings.dataSelection == Settings.USER_DEFINED_DATA) {
					if( !trainDataTextField.getText().equals("") && !testDataTextField.getText().equals("") ) {
						new Thread() {
							public void run() {
								bottomPane.setStatus(BottomPane.START_LOADING);
								File file = new File(""+trainDataTextField.getText());
								File file2 = new File(""+testDataTextField.getText());
								FixedDataLoader fdl = new FixedDataLoader(bottomPane, classifier, file, file2);
								fdl.load();
							}
						}.start();
					}
					else
						JOptionPane.showMessageDialog(appFrame, "Oooops. Please select data locations.", "Warning", JOptionPane.WARNING_MESSAGE);
				}
				else {
					bottomPane.setStatus(BottomPane.START_LOADING);
					File file = new File(""+randomTextField.getText());
					DataLoader dataLoader = new DataLoader(bottomPane, classifier, file);
					dataLoader.load(file);
				}
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
		
		//final SettingDialog settingDialog = new SettingDialog(this);
		
		settings = new Settings(this);
		
		JButton settingsButton = new JButton(new ImageIcon("src/images/setting.png"));
		settingsButton.setToolTipText("Setting");
		toolBar.add(settingsButton);
		settingsButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				settings.setVisible(true);
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
				classifier.train( (int)runtimeSpinner.getValue(), (int)cycleSpinner.getValue(), 
								  (int)employedSpinner.getValue(), (int)onlookerSpinner.getValue());
			}
		});
		
		JButton stopButton = new JButton(new ImageIcon("src/images/stop2.png"));
		toolBar2.add(stopButton);
		
	}

	private void updateRightPane(JPanel toRemove, JPanel toAdd) 
	{
		tPane.remove(toRemove);
		tPane.add(toAdd);
		updateUI();
	}
	
	private void selectDirectory(JTextField textField) 
	{
		chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		if (chooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) 
			textField.setText(chooser.getSelectedFile()+"");
	}
	
	private void saveWeights() 
	{
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
	
	public void initComponents() 
	{
		cycleBar.setMinimum(0);
		cycleBar.setMaximum((int)cycleSpinner.getValue());
		runtimeBar.setMinimum(0);
		runtimeBar.setMaximum((int)runtimeSpinner.getValue());	
		cycleBar.setValue(0);
		runtimeBar.setValue(0);
	}

	public void incrementCycle(int percent) 
	{
		cycleBar.setValue(percent);
	}
	
	public void incrementRuntime(int percent) 
	{
		runtimeBar.setValue(percent);
	}
	
	public void displayTrainingResult(double MSE, double elapsedTime) 
	{
		timeLabel.setText( elapsedTime + "" );
		mseLabel.setText( MSE + "" );
		bottomPane.setStatus(BottomPane.END_TRAINING);
	}

	private void loadTrainedData() 
	{
		FileFilter filter = new FileTypeFilter(".txt", "Text files");
		chooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
		chooser.setFileFilter(filter);
		chooser.setAcceptAllFileFilterUsed(false);
		if (chooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
			weights = fileLoader.loadTrainedData(chooser.getSelectedFile());
			if( weights == null )
				return;
			classifier.loadWeights(weights);
			JOptionPane.showMessageDialog(appFrame, "Loaded trained data.", "Message", JOptionPane.PLAIN_MESSAGE);
		}
	}
	
	
	public String getFilePath() 
	{
		return randomTextField.getText();
	}
	
	public BottomPane getBottomPane()
	{
		return bottomPane;
	}

	private void reset() 
	{
		cycleBar.setValue(0);
		runtimeBar.setValue(0);
		timeLabel.setText("0");
		mseLabel.setText("0.0");
		classifier.reset();
	}

	public void setTestOption(int selected) 
	{
		if( selected == SettingDialog.INDIVIDUAL ) 
			updateRightPane(batchPane, soloPane);
		else 
			updateRightPane(soloPane, batchPane);
	}

	public void applySettings(int dataSelection, int testBy) {
		if(dataSelection == Settings.USER_DEFINED_DATA) {
			randomPanel.setVisible(false);
			userdefinedPanel.setVisible(true);
		}
		else {
			userdefinedPanel.setVisible(false);
			randomPanel.setVisible(true);
		}
		if(testBy == Settings.BATCH_TEST) {
			soloPane.setVisible(false);
			batchPane.setVisible(true);
		}
		else {
			batchPane.setVisible(false);
			soloPane.setVisible(true);
		}
		
	}
}
