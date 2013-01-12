package views;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Insets;
import java.awt.SystemColor;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingConstants;
import javax.swing.text.Caret;

import sun.security.jca.GetInstance.Instance;

import custom.MainButton;
import custom.MyTextField;
import custom.StopPlayButton;

public class TrainPane extends JPanel 
{
	private static TrainPane instance = null;
	
	private JFileChooser chooser;

	private Classifier classifier;
	private Data trainingData;
	
	private JSpinner runtimeSpinner, cycleSpinner, 
					 employedSpinner, onlookerSpinner;
	private JProgressBar cycleBar, runtimeBar;
	private JLabel mseLabel, timeLabel;	
	
	private Frame frame;
	
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
		
		JButton backButton = new MainButton("src/images/back.png", "src/images/backHover.png");
		backButton.setBounds(10, 0, 71, 51);
		this.add(backButton, 0);
		backButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				frame.setView(frame.getNextView(0));
			}
		});
		
		JPanel panel1 = new JPanel();
		panel1.setBackground(new Color(255, 204, 51));
		panel1.setBounds(205, 38, 124, 30);
		add(panel1);
		
		JLabel label1 = new JLabel("Training Data");
		label1.setFont(new Font("Century Gothic", Font.PLAIN, 15));
		label1.setBounds(0, 0, 107, 23);
		panel1.add(label1);
		
		final JTextField textField1 = new MyTextField("click to selected directory");
		textField1.setBounds(330, 38, 291, 30);
		textField1.setBorder(null);
		textField1.setFont(new Font("Century Gothic", Font.PLAIN, 16));
		add(textField1);
		textField1.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				selectDirectory(textField1);
			}
		});		
		
		JPanel line1 = new JPanel();
		line1.setBackground(new Color(255, 204, 51));
		line1.setBounds(205, 100, 415, 1);
		add(line1);
		
		JLabel label2 = new JLabel("ABC Parameters");
		label2.setFont(new Font("Century Gothic", Font.PLAIN, 20));
		label2.setForeground(Color.WHITE);
		label2.setBounds(205, 119, 194, 25);
		add(label2);
		
		JPanel panel2 = new JPanel();
		panel2.setBackground(new Color(255, 204, 51));
		panel2.setBounds(207, 163, 124, 30);
		add(panel2);
		
		JLabel label3 = new JLabel("Employed Bees");
		label3.setFont(new Font("Century Gothic", Font.PLAIN, 15));
		label3.setBounds(0, 0, 107, 23);
		panel2.add(label3);
		
		employedSpinner = new JSpinner();
		employedSpinner.setModel(new SpinnerNumberModel(new Integer(10), 1, null, new Integer(5)));
		employedSpinner.setBounds(332, 163, 67, 30);
		employedSpinner.setBorder(null);
		employedSpinner.setFont(new Font("Century Gothic", Font.PLAIN, 15));
		add(employedSpinner);
		
		JPanel panel3 = new JPanel();
		panel3.setBackground(new Color(255, 204, 51));
		panel3.setBounds(428, 163, 124, 30);
		add(panel3);
		
		JLabel label4 = new JLabel("Max Cycle");
		label4.setFont(new Font("Century Gothic", Font.PLAIN, 15));
		label4.setBounds(0, 0, 78, 23);
		panel3.add(label4);
		

		cycleSpinner = new JSpinner();
		cycleSpinner.setModel(new SpinnerNumberModel(new Integer(500), 1, null, new Integer(250)));
		cycleSpinner.setBounds(553, 163, 67, 30);
		cycleSpinner.setBorder(null);
		cycleSpinner.setFont(new Font("Century Gothic", Font.PLAIN, 15));
		add(cycleSpinner);
		
		JPanel panel4 = new JPanel();
		panel4.setBackground(new Color(255, 204, 51));
		panel4.setBounds(207, 219, 124, 30);
		add(panel4);
		
		JLabel label5 = new JLabel("Onlooker Bees");
		label5.setFont(new Font("Century Gothic", Font.PLAIN, 15));
		label5.setBounds(0, 0, 78, 23);
		panel4.add(label5);

		onlookerSpinner = new JSpinner();
		onlookerSpinner.setModel(new SpinnerNumberModel(new Integer(10), 1, null, new Integer(5)));
		onlookerSpinner.setBounds(332, 219, 67, 30);
		onlookerSpinner.setBorder(null);
		onlookerSpinner.setFont(new Font("Century Gothic", Font.PLAIN, 15));
		add(onlookerSpinner);

		JPanel panel5 = new JPanel();
		panel5.setBackground(new Color(255, 204, 51));
		panel5.setBounds(428, 219, 124, 30);
		add(panel5);
		
		JLabel label6 = new JLabel("Runtime");
		label6.setFont(new Font("Century Gothic", Font.PLAIN, 15));
		label6.setBounds(0, 0, 60, 23);
		panel5.add(label6);

		runtimeSpinner = new JSpinner();
		runtimeSpinner.setModel(new SpinnerNumberModel(new Integer(1), 1, null, new Integer(1)));
		runtimeSpinner.setBounds(553, 219, 67, 30);
		runtimeSpinner.setBorder(null);
		runtimeSpinner.setFont(new Font("Century Gothic", Font.PLAIN, 15));
		add(runtimeSpinner);
		
		JLabel label7 = new JLabel("Cycle:");
		label7.setFont(new Font("Century Gothic", Font.PLAIN, 16));
		label7.setForeground(Color.WHITE);
		label7.setBounds(261, 275, 50, 23);
		label7.setHorizontalAlignment(SwingConstants.RIGHT);
		add(label7);
		
		JLabel label8 = new JLabel("Run:");
		label8.setFont(new Font("Century Gothic", Font.PLAIN, 16));
		label8.setForeground(Color.WHITE);
		label8.setBounds(261, 313, 50, 23);
		label8.setHorizontalAlignment(SwingConstants.RIGHT);
		add(label8);
		
		cycleBar = new JProgressBar();
		cycleBar.setBounds(332, 275, 288, 26);
		cycleBar.setForeground(Color.LIGHT_GRAY);
		cycleBar.setBorderPainted(false);
		cycleBar.setBackground(Color.WHITE);
		add(cycleBar);
		
		runtimeBar = new JProgressBar();
		runtimeBar.setBounds(332, 313, 288, 26);
		runtimeBar.setForeground(Color.GRAY);
		runtimeBar.setBorderPainted(false);
		runtimeBar.setBackground(Color.WHITE);
		add(runtimeBar);
		
		JPanel line2 = new JPanel();
		line2.setBackground(new Color(255, 204, 51));
		line2.setBounds(205, 359, 415, 1);
		add(line2);
		
		JLabel label9 = new JLabel("Mean Square Error:");
		label9.setFont(new Font("Century Gothic", Font.PLAIN, 16));
		label9.setForeground(Color.WHITE);
		label9.setBounds(202, 370, 151, 23);
		label9.setHorizontalAlignment(SwingConstants.RIGHT);
		add(label9);
		
		JLabel label10 = new JLabel("Training Time (sec):");
		label10.setFont(new Font("Century Gothic", Font.PLAIN, 16));
		label10.setForeground(Color.WHITE);
		label10.setBounds(204, 398, 149, 23);
		label10.setHorizontalAlignment(SwingConstants.RIGHT);
		add(label10);
		
		mseLabel = new JLabel("0.0");
		mseLabel.setFont(new Font("Century Gothic", Font.BOLD, 16));
		mseLabel.setForeground(new Color(255, 204, 51));
		mseLabel.setBounds(361, 370, 257, 23);
		add(mseLabel);
		
		timeLabel = new JLabel("0.0");
		timeLabel.setFont(new Font("Century Gothic", Font.BOLD, 16));
		timeLabel.setForeground(new Color(255, 204, 51));
		timeLabel.setBounds(361, 398, 257, 23);
		add(timeLabel);
		
		final ProgressPane progressPane = new ProgressPane(this); 
		progressPane.setLocation(0, 425);
		this.add(progressPane);
		
		JButton loadButton = new JButton("LD");
		loadButton.setBounds(28, 121, 60, 60);
		this.add(loadButton, 0);
		loadButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if( !textField1.getText().equals("") ) {
					new Thread() {
						public void run() {
							File file = new File(""+textField1.getText());
							ImageLoader dl = new ImageLoader(progressPane, file);
							trainingData = dl.load();
						}
					}.start();
				}
			}
		});
		
		JButton playButton = new StopPlayButton("src/images/start.png", "src/images/startHover.png", "src/images/end.png", "src/images/endHover.png");
		playButton.setBounds(28, 191, 60, 60);
		this.add(playButton, 0);
		playButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				classifier = new Classifier();
				classifier.trainNetwork( trainingData, (int)runtimeSpinner.getValue(), (int)cycleSpinner.getValue(), 
						  (int)employedSpinner.getValue(), (int)onlookerSpinner.getValue());
			}
		});
		//playButton.setEnabled(false);
		

		JButton saveButton = new JButton("SAVE");
		saveButton.setBounds(28, 261, 60, 60);
		this.add(saveButton, 0);
		saveButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				FileSaver fileSaver = new FileSaver();
				fileSaver.saveFile(classifier.getSolution());
			}
		});
		
		chooser = new JFileChooser();
		
	}
	
	private void selectDirectory(JTextField textField) 
	{
		chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		if (chooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) 
			textField.setText(chooser.getSelectedFile()+"");
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

	public void displayResult(double MSE, double elapsedTime) 
	{
		mseLabel.setText(""+MSE);
		timeLabel.setText(""+elapsedTime);
	}
		
}
