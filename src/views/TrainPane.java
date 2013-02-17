package views;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import javax.swing.plaf.basic.BasicProgressBarUI;

import core.ABC;



import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

import utilities.Debugger;
import utilities.FileTypeFilter;
import utilities.GlobalVariables;
import utilities.SolutionWriter;

import custom.MainButton;
import custom.MyTextField;
import custom.StopPlayButton;
import dataset.Data;
import dataset.DataReader;

public class TrainPane extends JPanel 
{
	private static TrainPane instance = null;
	
	private Data trainData;
	
	private JSpinner runtimeSpinner, cycleSpinner, 
					 employedSpinner, onlookerSpinner;
	private JProgressBar cycleBar, runtimeBar;
	private JLabel mseLabel, timeLabel;	
	
	private boolean hasData = false;
	
	private Frame frame;
	private ABC abc;
	
	public static TrainPane getInstance() 
	{
		if(instance == null)
			instance = new TrainPane();
		return instance;
	}
	
	public TrainPane() 
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
				//frame.setSize(700, 600);
			}
		});
		
		JLabel label = new JLabel("ABC-NN TRAINING");
		label.setFont(new Font("Century Gothic", Font.PLAIN, 24));
		label.setForeground(Color.WHITE);
		label.setBounds(145, 46, 300, 30);
		add(label);
		
		
		
		JPanel panel1 = new JPanel();
		panel1.setBackground(new Color(255, 204, 51));
		panel1.setBounds(205, 118, 124, 30);
		add(panel1);
		
		JLabel label1 = new JLabel("Training Data");
		label1.setFont(new Font("Century Gothic", Font.PLAIN, 15));
		label1.setBounds(0, 0, 107, 23);
		panel1.add(label1);
		
		final JTextField textField1 = new MyTextField("click to select test data");
		textField1.setBounds(330, 118, 261, 30);
		textField1.setBorder(null);
		textField1.setFont(new Font("Century Gothic", Font.PLAIN, 16));
		textField1.setBorder(BorderFactory.createCompoundBorder( textField1.getBorder(),
				BorderFactory.createEmptyBorder(5, 5, 5, 5)));
		add(textField1);
		textField1.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				selectDirectory(textField1);
			}
		});		
		
		JButton cButton = new MainButton("/images/pencil.png", "/images/pencil.png");
		cButton.setBorderPainted(false);
		cButton.setBounds(598, 118, 35, 33);
		add(cButton);
		cButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				selectDirectory(textField1);
			}
		});
		
		JPanel line = new JPanel();
		line.setBackground(new Color(255, 204, 51));
		line.setBounds(145, 83, 475, 1);
		add(line);
		
		JLabel label2 = new JLabel("ABC Parameters");
		label2.setFont(new Font("Century Gothic", Font.PLAIN, 20));
		label2.setForeground(Color.WHITE);
		label2.setBounds(205, 159, 194, 25);
		//add(label2);
		
		JPanel panel2 = new JPanel();
		panel2.setBackground(new Color(255, 204, 51));
		panel2.setBounds(207, 193, 124, 30);
		add(panel2);
		
		JLabel label3 = new JLabel("Employed Bees");
		label3.setFont(new Font("Century Gothic", Font.PLAIN, 15));
		label3.setBounds(0, 0, 107, 23);
		panel2.add(label3);
		
		employedSpinner = new JSpinner();
		employedSpinner.setModel(new SpinnerNumberModel(new Integer(10), 1, null, new Integer(5)));
		employedSpinner.setBounds(332, 193, 67, 30);
		employedSpinner.setBorder(null);
		employedSpinner.setFont(new Font("Century Gothic", Font.PLAIN, 15));
		add(employedSpinner);
		
		
		JPanel panel3 = new JPanel();
		panel3.setBackground(new Color(255, 204, 51));
		panel3.setBounds(428, 193, 124, 30);
		add(panel3);
		
		JLabel label4 = new JLabel("Max Cycle");
		label4.setFont(new Font("Century Gothic", Font.PLAIN, 15));
		label4.setBounds(0, 0, 78, 23);
		panel3.add(label4);

		cycleSpinner = new JSpinner();
		cycleSpinner.setModel(new SpinnerNumberModel(new Integer(750), 1, null, new Integer(250)));
		cycleSpinner.setBounds(553, 193, 67, 30);
		cycleSpinner.setBorder(null);
		cycleSpinner.setFont(new Font("Century Gothic", Font.PLAIN, 15));
		add(cycleSpinner);
		
		JPanel panel4 = new JPanel();
		panel4.setBackground(new Color(255, 204, 51));
		panel4.setBounds(207, 249, 124, 30);
		add(panel4);
		
		JLabel label5 = new JLabel("Onlooker Bees");
		label5.setFont(new Font("Century Gothic", Font.PLAIN, 15));
		label5.setBounds(0, 0, 78, 23);
		panel4.add(label5);

		onlookerSpinner = new JSpinner();
		onlookerSpinner.setModel(new SpinnerNumberModel(new Integer(15), 1, null, new Integer(5)));
		onlookerSpinner.setBounds(332, 249, 67, 30);
		onlookerSpinner.setBorder(null);
		onlookerSpinner.setFont(new Font("Century Gothic", Font.PLAIN, 15));
		add(onlookerSpinner);

		JPanel panel5 = new JPanel();
		panel5.setBackground(new Color(255, 204, 51));
		panel5.setBounds(428, 249, 124, 30);
		add(panel5);
		
		JLabel label6 = new JLabel("Runtime");
		label6.setFont(new Font("Century Gothic", Font.PLAIN, 15));
		label6.setBounds(0, 0, 60, 23);
		panel5.add(label6);

		runtimeSpinner = new JSpinner();
		runtimeSpinner.setModel(new SpinnerNumberModel(new Integer(1), 1, null, new Integer(1)));
		runtimeSpinner.setBounds(553, 249, 67, 30);
		runtimeSpinner.setBorder(null);
		runtimeSpinner.setFont(new Font("Century Gothic", Font.PLAIN, 15));
		add(runtimeSpinner);
		
		JLabel label7 = new JLabel("Cycle:");
		label7.setFont(new Font("Century Gothic", Font.PLAIN, 16));
		label7.setForeground(Color.WHITE);
		label7.setBounds(261, 305, 50, 23);
		label7.setHorizontalAlignment(SwingConstants.RIGHT);
		add(label7);
		
		JLabel label8 = new JLabel("Run:");
		label8.setFont(new Font("Century Gothic", Font.PLAIN, 16));
		label8.setForeground(Color.WHITE);
		label8.setBounds(261, 343, 50, 23);
		label8.setHorizontalAlignment(SwingConstants.RIGHT);
		add(label8);
		
		JPanel cPanel = new JPanel();
		cPanel.setBounds(332, 305, 288, 26);
		cPanel.setBackground(Color.WHITE);
		cPanel.setLayout(null);
		add(cPanel);
		
		cycleBar = new JProgressBar();
		cycleBar.setUI(new BasicProgressBarUI());
		cycleBar.setOpaque(false);
		cycleBar.setBorderPainted(false);
		cycleBar.setBounds(0, 0, 288, 26);
		cycleBar.setStringPainted(true);
		cycleBar.setForeground(Color.GRAY);
		cycleBar.setBackground(Color.WHITE);
		cPanel.add(cycleBar);
		
		runtimeBar = new JProgressBar();
		runtimeBar.setBounds(332, 343, 288, 26);
		runtimeBar.setForeground(Color.GRAY);
		runtimeBar.setBorderPainted(false);
		runtimeBar.setBackground(Color.WHITE);
		add(runtimeBar);
		
		JPanel line2 = new JPanel();
		line2.setBackground(new Color(255, 204, 51));
		line2.setBounds(205, 390, 415, 1);
		add(line2);
		
		JLabel label9 = new JLabel("Mean Square Error:");
		label9.setFont(new Font("Century Gothic", Font.PLAIN, 16));
		label9.setForeground(Color.WHITE);
		label9.setBounds(202, 402, 151, 23);
		label9.setHorizontalAlignment(SwingConstants.RIGHT);
		add(label9);
		
		JLabel label10 = new JLabel("Training Time (sec):");
		label10.setFont(new Font("Century Gothic", Font.PLAIN, 16));
		label10.setForeground(Color.WHITE);
		label10.setBounds(204, 430, 149, 23);
		label10.setHorizontalAlignment(SwingConstants.RIGHT);
		add(label10);
		
		mseLabel = new JLabel("0.0");
		mseLabel.setFont(new Font("Century Gothic", Font.BOLD, 16));
		mseLabel.setForeground(new Color(255, 204, 51));
		mseLabel.setBounds(361, 402, 257, 23);
		add(mseLabel);
		
		timeLabel = new JLabel("0.0");
		timeLabel.setFont(new Font("Century Gothic", Font.BOLD, 16));
		timeLabel.setForeground(new Color(255, 204, 51));
		timeLabel.setBounds(361, 430, 257, 23);
		add(timeLabel);
		
		final ProgressPane progressPane = new ProgressPane(); 
		progressPane.setLocation(0, 475);
		this.add(progressPane);
		
		JButton loadButton = new JButton("LD");
		loadButton.setBounds(28, 121, 60, 60);
		this.add(loadButton, 0);
		loadButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if( !textField1.getText().equals("") ) {
					new Thread( new Runnable() {
						@Override
						public void run() {
							File file = new File(""+textField1.getText());
							DataReader dl = new DataReader(progressPane, file);
							trainData = dl.read();
							hasData = true;
						}
						
					}).start();
				}
			}
		});
		
		JButton playButton = new StopPlayButton("/images/start.png", "/images/startHover.png", "/images/end.png", "/images/endHover.png");
		playButton.setBounds(28, 191, 60, 60);
		this.add(playButton, 0);
		playButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(hasData) {
					trainNetwork( trainData, (int)runtimeSpinner.getValue(), (int)cycleSpinner.getValue(), 
						(int)employedSpinner.getValue(), (int)onlookerSpinner.getValue());
				}
				else
					JOptionPane.showMessageDialog(null, "No Data.");	
			}
		});
		//playButton.setEnabled(false);
		

		JButton saveButton = new JButton("SAVE");
		saveButton.setBounds(28, 261, 60, 60);
		this.add(saveButton, 0);
		saveButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				JFileChooser chooser;
				
				FileFilter filter = new FileTypeFilter(".ttb", "Text files");
				chooser = new JFileChooser("D:/kamatisan/");
				chooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
				chooser.setFileFilter(filter);
				chooser.setAcceptAllFileFilterUsed(false);

				if(chooser.showSaveDialog(null) == JFileChooser.APPROVE_OPTION) {
				    File file = chooser.getSelectedFile();
				    SolutionWriter fileSaver = new SolutionWriter(file);
					fileSaver.saveFile(abc.getSolution());
				}
			}
				
		});
		
	}
	
	private void selectDirectory(JTextField textField) 
	{
		FileFilter filter = new FileTypeFilter(".data", "Text files");
		JFileChooser chooser = new JFileChooser("D:/kamatisan");
		chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
		chooser.setFileFilter(filter);
		if (chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
			textField.setText(chooser.getSelectedFile().getAbsolutePath());
		}
	}
	
	/**
	 * train neural network classifier
	 * 
	 * @param trainingData
	 * @param runtime
	 * @param maxCycle
	 * @param employedBeeSize
	 * @param onlookerBeeSize
	 */
	public void trainNetwork(final Data trainingData, final int runtime, final int maxCycle,
			final int employedBeeSize, final int onlookerBeeSize) 
	{
		initComponents();
		GlobalVariables.setStructure(5, 5);
		new Thread(new Runnable() {
			@Override
			public void run() {
				abc = new ABC(runtime, maxCycle, employedBeeSize, onlookerBeeSize, GlobalVariables.DIMENSIONS); 
				abc.train(trainingData);
			}
		}).start();
	
	}
	
	public void displayResult(double MSE, double[] solution, double elapsedTime, int runTime) 
	{
		//System.out.println("run:"+runTime+"/t MSE: "+MSE+"/t time: "+elapsedTime);
		mseLabel.setText(""+MSE);
		timeLabel.setText(""+elapsedTime);
		
		/*File file = new File("D:/kamatisan/Experiments/Exp1-Params/EmployedBeeExp/solution_"+runTime+".ttb");
		SolutionWriter fileSaver = new SolutionWriter(file);
		fileSaver.saveFile(solution);
		
		File results = new File("D:/kamatisan/Experiments/Exp1-Params/EmployedBeeExp/results.txt");
		if(!results.exists()) {
			try {
				results.createNewFile();
			} catch (IOException e) {
				Debugger.printError("Error creating results.txt");
			}
		}
		try {
		    PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(results, true)));
		    out.print(""+runTime+"\t\t"+elapsedTime+"\t\t"+MSE);
		    out.close();
		} catch (IOException e) {}
		*/
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
		updateUI();
		//System.out.println( cycleBar.getValue() + "/" + cycleBar.getMaximum() );
	}
	
	public void incrementRuntime(int percent) 
	{
		runtimeBar.setValue(percent);
	}

}
