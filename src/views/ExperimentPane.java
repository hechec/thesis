package views;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JRadioButton;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingConstants;
import javax.swing.filechooser.FileFilter;
import javax.swing.plaf.basic.BasicProgressBarUI;

import core.ABC;
import core.Classifier;
import core.Result;

import utilities.FileTypeFilter;
import utilities.GlobalVariables;
import utilities.OutputLayerHelper;
import utilities.SolutionWriter;
import views.dialog.ColorFeatureChooser;
import views.dialog.ResultLocationChooser;
import views.optionpane.ConfirmationDialog;
import views.optionpane.MessageDialog;

import custom.MainButton;
import custom.MyTextField;
import dataset.Data;
import dataset.DataReader;

public class ExperimentPane extends JPanel
{
	private static ExperimentPane instance = null;
	private Frame frame;
	
	private JSpinner runtimeSpinner, cycleSpinner, 
					 employedSpinner, onlookerSpinner,
					 hiddenNodesSpinner;
	private JProgressBar cycleBar, runtimeBar;
	
	private JButton selectFeaturesButton;
	private JLabel featuresLabel;
	private JButton stopButton, optimizeButton, prepareButton;
	
	private boolean hasData = false;
	
	private Data trainData, testData;
	private ABC abc;
	
	public static final int EXP_1 = 1, EXP_2 = 2, EXP_3 = 3;
	public static int EXPERIMENT = EXP_1;
	
	private File masterFile = null;

	private ArrayList<String> features = new ArrayList<String>();

	private FileFilter filter = new FileTypeFilter(".data", "Text files");
	private JFileChooser chooser = new JFileChooser();
	private File trainFile = new File(""), testFile = new File("");
	
	public static ExperimentPane getInstance() 
	{
		if(instance == null)
			instance = new ExperimentPane();
		return instance;
	}
	
	public ExperimentPane()
	{
		frame = Frame.getInstance();
		setLayout(null);		
		
		JButton backButton = new MainButton("/images/back.png", "/images/backHover.png");
		backButton.setBounds(10, 0, 71, 51);
		this.add(backButton, 0);
		backButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				frame.setView(frame.getNextView(0));
			}
		});
		
		JLabel label = new JLabel("EXPERIMENTATION");
		label.setFont(new Font("Century Gothic", Font.PLAIN, 24));
		label.setForeground(Color.WHITE);
		label.setBounds(445, 46, 300, 30);
		add(label);
		
		JPanel line = new JPanel();
		line.setBackground(new Color(255, 204, 51));
		line.setBounds(46, 83, 610, 1);
		add(line);
		
		/**  right pane **/
		JLabel dLabel = new JLabel("Data Set");
		dLabel.setFont(new Font("Century Gothic", Font.PLAIN, 20));
		dLabel.setForeground(Color.WHITE);
		dLabel.setBounds(445, 100, 120, 30);
		add(dLabel);
		
		JPanel cpanel = new JPanel();
		cpanel.setBackground(new Color(255, 204, 51));
		cpanel.setBounds(320, 133, 107, 27);
		add(cpanel);
		
		JLabel cLabel = new JLabel("TRAINING");
		cLabel.setFont(new Font("Century Gothic", Font.PLAIN, 12));
		cLabel.setForeground(Color.BLACK);
		cLabel.setBounds(0, 0, 75, 30);
		cpanel.add(cLabel);
		
		final JTextField textField1 = new MyTextField("click to select train data");
		textField1.setBounds(428, 133, 228, 27);
		textField1.setBorder(null);
		textField1.setFont(new Font("Century Gothic", Font.PLAIN, 12));
		textField1.setBorder(BorderFactory.createCompoundBorder( textField1.getBorder(),
					BorderFactory.createEmptyBorder(5, 5, 5, 5)));
		add(textField1);
		textField1.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				String path = selectDirectory();
				if(new File(path).exists())
					textField1.setText(path);
			}
		});	
				
		JPanel panel2 = new JPanel();
		panel2.setBackground(new Color(255, 204, 51));
		panel2.setBounds(320, 177, 107, 27);
		add(panel2);
		
		JLabel label2 = new JLabel("TESTING");
		label2.setFont(new Font("Century Gothic", Font.PLAIN, 12));
		label2.setForeground(Color.BLACK);
		label2.setBounds(0, 0, 75, 30);
		panel2.add(label2);
		
		final JTextField textField2 = new MyTextField("click to select test data");
		textField2.setBounds(428, 177, 228, 27);
		textField2.setBorder(null);
		textField2.setFont(new Font("Century Gothic", Font.PLAIN, 12));
		textField2.setBorder(BorderFactory.createCompoundBorder( textField2.getBorder(),
					BorderFactory.createEmptyBorder(5, 5, 5, 5)));
		add(textField2);
		textField2.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				String path = selectDirectory();
				if(new File(path).exists())
					textField2.setText(path);
			}
		});	
		
		JPanel line2 = new JPanel();
		line2.setBackground(new Color(255, 204, 51));
		line2.setBounds(320, 227, 335, 1);
		add(line2);
		
		JLabel paramLabel = new JLabel("ABC Parameters");
		paramLabel.setFont(new Font("Century Gothic", Font.PLAIN, 20));
		paramLabel.setForeground(Color.WHITE);
		paramLabel.setBounds(409, 235, 194, 30);
		add(paramLabel);
		
		JPanel panel3 = new JPanel();
		panel3.setBackground(new Color(255, 204, 51));
		panel3.setBounds(320, 270, 107, 27);
		add(panel3);
		
		JLabel label3 = new JLabel("Employed Bees");
		label3.setFont(new Font("Century Gothic", Font.PLAIN, 12));
		label3.setForeground(Color.BLACK);
		label3.setBounds(0, 0, 75, 30);
		panel3.add(label3);
		
		employedSpinner = new JSpinner();
		employedSpinner.setModel(new SpinnerNumberModel(new Integer(15), 1, null, new Integer(5)));
		employedSpinner.setBounds(428, 270, 52, 27);
		employedSpinner.setBorder(null);
		employedSpinner.setFont(new Font("Century Gothic", Font.PLAIN, 12));
		add(employedSpinner);
		
		JPanel panel4 = new JPanel();
		panel4.setBackground(new Color(255, 204, 51));
		panel4.setBounds(320, 311, 107, 27);
		add(panel4);
		
		JLabel label4 = new JLabel("Onlooker Bees");
		label4.setFont(new Font("Century Gothic", Font.PLAIN, 12));
		label4.setForeground(Color.BLACK);
		label4.setBounds(0, 0, 75, 30);
		panel4.add(label4);
		
		onlookerSpinner = new JSpinner();
		onlookerSpinner.setModel(new SpinnerNumberModel(new Integer(15), 1, null, new Integer(5)));
		onlookerSpinner.setBounds(428, 311, 52, 27);
		onlookerSpinner.setBorder(null);
		onlookerSpinner.setFont(new Font("Century Gothic", Font.PLAIN, 12));
		add(onlookerSpinner);
		
		JPanel panel5 = new JPanel();
		panel5.setBackground(new Color(255, 204, 51));
		panel5.setBounds(495, 270, 107, 27);
		add(panel5);
		
		JLabel label5 = new JLabel("Max Cycle");
		label5.setFont(new Font("Century Gothic", Font.PLAIN, 12));
		label5.setForeground(Color.BLACK);
		label5.setBounds(0, 0, 75, 30);
		panel5.add(label5);
		
		cycleSpinner = new JSpinner();
		cycleSpinner.setModel(new SpinnerNumberModel(new Integer(750), 1, null, new Integer(250)));
		cycleSpinner.setBounds(603, 270, 52, 27);
		cycleSpinner.setBorder(null);
		cycleSpinner.setFont(new Font("Century Gothic", Font.PLAIN, 12));
		add(cycleSpinner);
		
		JPanel panel6 = new JPanel();
		panel6.setBackground(new Color(255, 204, 51));
		panel6.setBounds(495, 311, 107, 27);
		add(panel6);
		
		JLabel label6 = new JLabel("Runtime");
		label6.setFont(new Font("Century Gothic", Font.PLAIN, 12));
		label6.setForeground(Color.BLACK);
		label6.setBounds(0, 0, 75, 30);
		panel6.add(label6);
		
		runtimeSpinner = new JSpinner();
		runtimeSpinner.setModel(new SpinnerNumberModel(new Integer(1), 1, null, new Integer(1)));
		runtimeSpinner.setBounds(603, 311, 52, 27);
		runtimeSpinner.setBorder(null);
		runtimeSpinner.setFont(new Font("Century Gothic", Font.PLAIN, 12));
		add(runtimeSpinner);
		
		JLabel label7 = new JLabel("Cycle:");
		label7.setFont(new Font("Century Gothic", Font.PLAIN, 16));
		label7.setForeground(Color.WHITE);
		label7.setBounds(320, 352, 50, 23);
		label7.setHorizontalAlignment(SwingConstants.RIGHT);
		add(label7);
		
		JLabel label8 = new JLabel("Run:");
		label8.setFont(new Font("Century Gothic", Font.PLAIN, 16));
		label8.setForeground(Color.WHITE);
		label8.setBounds(320, 390, 50, 23);
		label8.setHorizontalAlignment(SwingConstants.RIGHT);
		add(label8);
		
		JPanel cPanel = new JPanel();
		cPanel.setBounds(384, 352, 271, 26);
		cPanel.setBackground(Color.WHITE);
		cPanel.setLayout(null);
		add(cPanel);
		
		cycleBar = new JProgressBar();
		cycleBar.setUI(new BasicProgressBarUI());
		cycleBar.setOpaque(false);
		cycleBar.setBorderPainted(false);
		cycleBar.setBounds(0, 0, 271, 26);
		cycleBar.setStringPainted(true);
		cycleBar.setForeground(Color.GRAY);
		cycleBar.setBackground(Color.WHITE);
		cPanel.add(cycleBar);
		
		JPanel rPanel = new JPanel();
		rPanel.setBounds(384, 390, 271, 26);
		rPanel.setBackground(Color.WHITE);
		rPanel.setLayout(null);
		add(rPanel);
		
		runtimeBar = new JProgressBar();
		runtimeBar.setBounds(0, 0, 271, 26);
		runtimeBar.setUI(new BasicProgressBarUI());
		runtimeBar.setOpaque(false);
		runtimeBar.setBorderPainted(false);
		runtimeBar.setStringPainted(true);
		runtimeBar.setForeground(Color.DARK_GRAY);
		runtimeBar.setBackground(Color.WHITE);
		rPanel.add(runtimeBar);
		
		optimizeButton = new JButton("OPTIMIZE");
		optimizeButton.setBounds(550, 430, 100, 35);
		add(optimizeButton);
		optimizeButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(hasData) {
					new ResultLocationChooser(ResultLocationChooser.EXPERIMENATION).setVisible(true);
				} else {
					new MessageDialog("Oooops. Click prepare before optimizing.").setVisible(true);
				}
			}
		});
		
		stopButton = new JButton("STOP");
		stopButton.setBounds(550, 430, 100, 35);
		stopButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				abc.pause();
				new ConfirmationDialog(getInstance(), "Are you sure you want to stop optimizing?").setVisible(true);				
			}
		});
		//add(stopButton);
		
		final ProgressPane progressPane = new ProgressPane(); 
		progressPane.setLocation(0, 475);
		this.add(progressPane);
		
		prepareButton = new JButton("PREPARE");
		prepareButton.setBounds(385, 430, 100, 35);
		add(prepareButton);
		prepareButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				trainFile = new File(textField1.getText().trim());
				testFile = new File(textField2.getText().trim());
				if( trainFile.exists() && testFile.exists() ) {	
					if(EXPERIMENT == EXP_3 && features.size() == 0) {
						new MessageDialog("Oooops. Please select at least one color feature").setVisible(true);
						return;
					}
					setComponents(false);
					initNetwork();
					new Thread( new Runnable() {
						@Override
						public void run() {
							DataReader dl = new DataReader(progressPane, trainFile);
							trainData = dl.read();
							
							DataReader dl2 = new DataReader(progressPane, testFile);
							testData = dl2.read();	
							
							hasData = true;
							new MessageDialog("Done preparing.").setVisible(true);
							setComponents(true);
						}
						
					}).start();
				} else {
					new MessageDialog("Oooops. Please enter both train and test data.").setVisible(true);
				}
			}
		});
		
		JPanel expPanel = new JPanel();
		expPanel.setBackground(new Color(255, 204, 51, 60));
		expPanel.setBounds(46, 133, 235, 275);
		expPanel.setLayout(null);
		add(expPanel);
		
		JLabel label9 = new JLabel("SELECT EXPERIMENT");
		label9.setBounds(10, 5, 162, 22);
		label9.setFont(new Font(null, Font.PLAIN, 15));
		label9.setForeground(Color.WHITE);
		expPanel.add(label9);
		
		JRadioButton exp1 = new JRadioButton("ABC EXPERIMENT");
		exp1.setBounds(30, 40, 175, 30);		
		exp1.setFont(new Font(null, Font.PLAIN, 14));
		exp1.setBackground(new Color(255, 204, 51));
		expPanel.add(exp1);
		exp1.setSelected(true);
		exp1.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				EXPERIMENT = EXP_1;
				disableExp3();
				disableExp2();
				enableExp1();
			}
		});
		
		JRadioButton exp2 = new JRadioButton("NN EXPERIMENT");
		exp2.setBounds(30, 80, 175, 30);		
		exp2.setFont(new Font(null, Font.PLAIN, 14));
		exp2.setBackground(new Color(255, 204, 51));
		expPanel.add(exp2);
		exp2.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				EXPERIMENT = EXP_2;
				disableExp1();
				disableExp3();
				enableExp2();
			}
		});
		
		JRadioButton exp3 = new JRadioButton("INPUT FEATURES");
		exp3.setBounds(30, 160, 175, 30);		
		exp3.setFont(new Font(null, Font.PLAIN, 14));
		exp3.setBackground(new Color(255, 204, 51));
		expPanel.add(exp3);
		exp3.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				EXPERIMENT = EXP_3;
				disableExp1();
				disableExp2();
				enableExp3();
			}
		});
		
		ButtonGroup group = new ButtonGroup();
		group.add(exp1);
		group.add(exp2);
		group.add(exp3);
		
		JLabel label10 = new JLabel("Hidden Nodes:");
		label10.setBounds(65, 118, 100, 30);
		label10.setFont(new Font(null, Font.PLAIN, 14));
		label10.setForeground(Color.WHITE);
		expPanel.add(label10);
		
		hiddenNodesSpinner = new JSpinner();
		hiddenNodesSpinner.setModel(new SpinnerNumberModel(new Integer(5), 1, null, new Integer(1)));
		hiddenNodesSpinner.setBounds(165, 120, 40, 28);
		hiddenNodesSpinner.setBorder(null);
		hiddenNodesSpinner.setFont(new Font("Century Gothic", Font.PLAIN, 15));
		expPanel.add(hiddenNodesSpinner);
		hiddenNodesSpinner.setEnabled(false);
		
		featuresLabel = new JLabel("--");
		featuresLabel.setBounds(40, 190, 200, 30);
		featuresLabel.setFont(new Font(null, Font.ITALIC, 12));
		featuresLabel.setForeground(Color.WHITE);
		expPanel.add(featuresLabel);
		
		selectFeaturesButton = new JButton("CHANGE FEATURES");
		selectFeaturesButton.setBounds(55, 225, 130, 30);
		expPanel.add(selectFeaturesButton);
		selectFeaturesButton.setEnabled(false);
		selectFeaturesButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				ColorFeatureChooser.getInstance().setVisible(true);
			}
		});
		
		JLabel notes = new JLabel("Note:");
		notes.setBounds(50, 400, 30, 50);
		notes.setForeground(Color.WHITE);
		notes.setFont(new Font(null, Font.ITALIC, 12));
		add(notes);
		
		JLabel message = new JLabel("<html>When shifting to another experiment, click again the prepare button before optimizing.</html>");
		message.setBounds(90, 415, 200, 50);
		message.setForeground(Color.WHITE);
		message.setFont(new Font(null, Font.ITALIC, 12));
		add(message);
		
		chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
		chooser.setFileFilter(filter);
	}
	
	public void train(File file)
	{
		this.masterFile = file;
		remove(optimizeButton);
		add(stopButton);
		prepareButton.setEnabled(false);
		resetComponents();
		new Thread(new Runnable() {
			@Override
			public void run() {
				abc = new ABC((int)runtimeSpinner.getValue(), (int)cycleSpinner.getValue(), (int)employedSpinner.getValue(), 
						(int)onlookerSpinner.getValue(), GlobalVariables.DIMENSIONS); 
				abc.train(trainData);
			}
		}).start();
	
	}
	
	private void initNetwork() 
	{
		int numberOfInput = 5;
		int numberOfHidden = 5;
		GlobalVariables.setMode(GlobalVariables.STANDARD_RUN);
		if(EXPERIMENT == EXP_2) 
			numberOfHidden = (int)hiddenNodesSpinner.getValue();
		else if(EXPERIMENT == EXP_3) {
			numberOfInput = features.size();
			GlobalVariables.setMode(GlobalVariables.EXPERIMENTATION_3);
		}
		GlobalVariables.setStructure(numberOfInput, numberOfHidden);
	}

	public void returnResult(double MSE, double[] solution, double elapsedTime, int runTime) 
	{
		
		File file = new File(masterFile.getAbsoluteFile()+"/solution_"+runTime+".ttb");
		SolutionWriter fileSaver = new SolutionWriter(file);
		fileSaver.saveFile(solution);
		
		File resultFile = new File(masterFile.getAbsoluteFile()+"/results.txt");
		if(!resultFile.exists()) {
			try {
				resultFile.createNewFile();
			} catch (IOException e) {
				//Debugger.printError("Error creating results.txt");
			}
		}
		
		Classifier classifier = new Classifier(solution);
		Result result = classifier.test_batch(testData.getInputVector(), OutputLayerHelper.normalize(testData.getOutputVector()));
		
		//System.out.println("run:"+ctr+"\t MSE: "+MSE+"\t time: "+elapsedTime
		//		+"\t correct:"+result.getScore()+"\t incorrect:"+(result.size() - result.getScore())
		//		+"\t accuracy:"+result.getAccuracy());
		
		try {
		    PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(resultFile, true)));
		    out.println(""+runTime+"\t\t"+elapsedTime+"\t\t"+MSE+"\t\t"+result.getScore()+"\t\t"+result.getAccuracy());
		    out.close();
		} catch (IOException e) {}
	
	}
	

	private String selectDirectory() 
	{
		String path = "";
		if (chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) 
			path += chooser.getSelectedFile().getAbsolutePath();
		return path;
	}
	
	public void resetComponents() 
	{
		cycleBar.setMinimum(0);
		cycleBar.setMaximum((int)cycleSpinner.getValue());
		runtimeBar.setMinimum(0);
		runtimeBar.setMaximum((int)runtimeSpinner.getValue());	
		cycleBar.setValue(0);
		runtimeBar.setValue(0);
		updateUI();
	}
	
	public void incrementCycle(int percent) 
	{
		cycleBar.setValue(percent);
	}
	
	public void incrementRuntime(int percent) 
	{
		runtimeBar.setValue(percent);
	}
	
	private void enableExp1()
	{
		employedSpinner.setEnabled(true);
		onlookerSpinner.setEnabled(true);
	}
	
	private void disableExp1()
	{
		employedSpinner.setValue(15);
		employedSpinner.setEnabled(false);
		onlookerSpinner.setValue(15);
		onlookerSpinner.setEnabled(false);
	}
	
	private void enableExp2()
	{
		hiddenNodesSpinner.setEnabled(true);
	}
	
	private void disableExp2()
	{
		hiddenNodesSpinner.setEnabled(false);
	}
	
	private void enableExp3()
	{
		selectFeaturesButton.setEnabled(true);
	}
	
	private void disableExp3()
	{
		selectFeaturesButton.setEnabled(false);
	}

	public void setColorFeatures(DefaultListModel<String> listModel) 
	{
		String string = "";
		features.clear();
		for(int i = 0; i < listModel.getSize(); i++) {
			features.add(listModel.getElementAt(i));
			string += listModel.getElementAt(i)+", ";
		}
		string = (String) string.subSequence(0, string.length()-2);
		featuresLabel.setText(string);		
		updateUI();
	}
	
	public ArrayList<String> getFeatures()
	{
		return features;
	}
	
	private void setComponents(boolean b) 
	{
		optimizeButton.setEnabled(b);
		prepareButton.setEnabled(b);
	}
	
	public void confirmNo() 
	{
		abc.resume();		
	}

	public void confirmYes() 
	{
		resetButtons();
		resetComponents();
		abc.terminate();
	}
	
	public void resetButtons()
	{
		remove(stopButton);
		add(optimizeButton);
		setComponents(true);
		updateUI();
	}

}
