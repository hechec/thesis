package views;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.*;
import java.io.File;
import java.text.DecimalFormat;
import javax.swing.*;
import javax.swing.filechooser.FileFilter;

import abcnn.Result;

import util.FileTypeFilter;
import util2.Debugger;
import util2.FileChooser;
import util2.OutputLayerHelper;
import util2.ResultWriter;
import util2.SolutionReader;
import views.dialog.ResultViewerDialog;

import custom.MainButton;
import custom.MyTextField;
import dataset.Data;
import dataset.DataReader;

public class BatchPane extends JPanel
{
	private static BatchPane instance = null;
	private Frame frame;
	
	private Data testData = null;
	private Result result = null;
	
	private double[] solution = null;
	
	private FileChooser chooser = FileChooser.getInstance();
	private JLabel fileLabel, percentLabel, correctLabel, incorrectLabel;
	
	public static BatchPane getInstance() 
	{
		if(instance == null)
			instance = new BatchPane();
		return instance;
	}
	
	public BatchPane()
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
		
		JLabel label = new JLabel("BATCH CLASSIFICATION");
		label.setFont(new Font("Century Gothic", Font.PLAIN, 24));
		label.setForeground(Color.WHITE);
		label.setBounds(145, 46, 300, 30);
		add(label);
		
		JPanel line = new JPanel();
		line.setBackground(new Color(255, 204, 51));
		line.setBounds(145, 83, 475, 1);
		add(line);
		
		JLabel dLabel = new JLabel("Data");
		dLabel.setFont(new Font("Century Gothic", Font.PLAIN, 20));
		dLabel.setForeground(Color.WHITE);
		dLabel.setBounds(388, 91, 54, 30);
		add(dLabel);
		
		JPanel cpanel = new JPanel();
		cpanel.setBackground(new Color(255, 204, 51));
		cpanel.setBounds(205, 135, 124, 30);
		add(cpanel);
		
		JLabel cLabel = new JLabel("Classifier");
		cLabel.setFont(new Font("Century Gothic", Font.PLAIN, 16));
		cLabel.setForeground(Color.BLACK);
		cLabel.setBounds(0, 0, 75, 30);
		cpanel.add(cLabel);
		
		JPanel fPanel = new JPanel();
		fPanel.setBackground(Color.LIGHT_GRAY);
		fPanel.setBounds(330, 135, 260, 30);
		fPanel.setLayout(null);
		add(fPanel);
		
		fileLabel = new JLabel("-no classifier selected-");
		fileLabel.setFont(new Font("Century Gothic", Font.PLAIN, 14));
		fileLabel.setForeground(Color.BLACK);
		fileLabel.setBounds(4, 0, 260, 30);
		fPanel.add(fileLabel);
		
		JButton cButton = new MainButton("src/images/pencil.png", "src/images/pencil.png");
		cButton.setBorderPainted(false);
		cButton.setBounds(595, 135, 35, 33);
		add(cButton);
		cButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				selectClassifier();
			}
		});
	
		JPanel panel = new JPanel();
		panel.setBackground(new Color(255, 204, 51));
		panel.setBounds(205, 195, 124, 30);
		add(panel);
		
		JLabel label2 = new JLabel("Test Data");
		label2.setFont(new Font("Century Gothic", Font.PLAIN, 16));
		label2.setForeground(Color.BLACK);
		label2.setBounds(0, 0, 107, 23);
		panel.add(label2);
		
		final JTextField textField1 = new MyTextField("click to select test data");
		textField1.setBounds(330, 195, 260, 30);
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
		
		JButton tButton = new MainButton("src/images/pencil.png", "src/images/pencil.png");
		tButton.setBounds(595, 195, 35, 33);
		add(tButton);
		tButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				FileFilter filter = new FileTypeFilter(".data", "Text files");
				JFileChooser chooser = new JFileChooser("D:/");
				chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
				chooser.setFileFilter(filter);
				if (chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
					textField1.setText(chooser.getSelectedFile().getAbsolutePath());
				}
				//selectDirectory(textField1);
			}
		});
		
		JPanel line2 = new JPanel();
		line2.setBackground(new Color(255, 204, 51));
		line2.setBounds(205, 260, 415, 1);
		add(line2);
		
		JLabel label3 = new JLabel("Accuracy");
		label3.setFont(new Font("Century Gothic", Font.PLAIN, 20));
		label3.setForeground(Color.WHITE);
		label3.setBounds(364, 268, 107, 29);
		add(label3);
		
		JLabel label4 = new JLabel("no. of CORRECT classification:");
		label4.setFont(new Font("Century Gothic", Font.PLAIN, 16));
		label4.setForeground(Color.WHITE);
		label4.setBounds(208, 300, 230, 24);
		add(label4);
		
		JLabel label5 = new JLabel("no. of INCORRECT classification:");
		label5.setFont(new Font("Century Gothic", Font.PLAIN, 16));
		label5.setForeground(Color.WHITE);
		label5.setBounds(208, 329, 250, 24);
		add(label5);
		
		correctLabel = new JLabel("--");
		correctLabel.setFont(new Font("Century Gothic", Font.PLAIN, 20));
		correctLabel.setForeground(new Color(102, 255, 0));
		correctLabel.setBounds(445, 298, 40, 29);
		add(correctLabel);
		
		incorrectLabel = new JLabel("--");
		incorrectLabel.setFont(new Font("Century Gothic", Font.PLAIN, 20));
		incorrectLabel.setForeground(new Color(255, 51, 51));
		incorrectLabel.setBounds(460, 326, 40, 29);
		add(incorrectLabel);
		
		percentLabel = new JLabel("0 %");
		percentLabel.setFont(new Font("Century Gothic", Font.BOLD, 30));
		percentLabel.setForeground(new Color(102, 255, 0));
		percentLabel.setBounds(515, 305, 110, 40);
		add(percentLabel);
		percentLabel.setVisible(false);
		
		JButton viewButton = new JButton("View Results");
		viewButton.setBounds(208, 370, 124, 30);
		add(viewButton);
		viewButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(testData != null && result != null) {
					ResultViewerDialog rvDialog = new ResultViewerDialog(testData, result);
					rvDialog.setVisible(true);
				}
				else {
					Debugger.printError("Cannot show results.");
				}
			}
		});
		
		JButton saveButton = new JButton("Save Results");
		saveButton.setBounds(350, 370, 124, 30);
		add(saveButton);
		saveButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				FileFilter filter = new FileTypeFilter(".txt", "Text files");
				JFileChooser chooser = new JFileChooser("D:/kamatisan/");
				chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
				chooser.setFileFilter(filter);
				if (chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) { 
					ResultWriter resultWriter = new ResultWriter();
					resultWriter.writeResult(chooser.getSelectedFile(), testData, result);
				}
				
			}
		});
		
		final ProgressPane progressPane = new ProgressPane(); 
		progressPane.setLocation(0, 425);
		this.add(progressPane);
		
		JButton loadButton = new JButton("LOAD");
		loadButton.setBounds(20, 90, 80, 30);
		add(loadButton);
		loadButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {

				final File file = new File(""+textField1.getText());
				
				new Thread(new Runnable() {
					@Override
					public void run() {
						DataReader dl = new DataReader(progressPane, file);
						testData = dl.read();
					}
				}).start();
			}
		});
		
		JButton testButton = new JButton("TEST");
		testButton.setBounds(20, 130, 80, 30);
		add(testButton);
		testButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(solution != null && testData != null) {
					test();
				}
				else {
					System.out.println("Oooops!");
				}
			}
		});
		
		chooser.setIsFiltered(false);
	}
	
	/**
	 *  test set of input images
	 */
	private void test() 
	{
		Classifier classifier = new Classifier(solution);
		result = classifier.test_batch(testData.getInputVector(), OutputLayerHelper.normalize(testData.getOutputVector()));
		
		float acc = result.getAccuracy(); 
		if(acc > 90 )
			percentLabel.setForeground(new Color(102, 255, 0));
		else
			percentLabel.setForeground(new Color(255, 51, 51));
		
		DecimalFormat df = new DecimalFormat("#.##");
		percentLabel.setText( df.format((double)acc)+" %" );
		
		correctLabel.setText( result.getScore() +"" );
		incorrectLabel.setText( result.size() - result.getScore()+"" );
		percentLabel.setVisible(true);
	}
	
	/**
	 * reads a file containing a collection of NN weights
	 * stores these weights to solution
	 */
	private void selectClassifier()
	{
		FileFilter filter = new FileTypeFilter(".ttb", "Text files");
		JFileChooser chooser = new JFileChooser("D:/kamatisan");
		chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
		chooser.setFileFilter(filter);
		if (chooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) { 
			fileLabel.setText(chooser.getSelectedFile()+"");
			solution = SolutionReader.read(chooser.getSelectedFile());
		}
	}
	
	private void selectDirectory(JTextField textField) 
	{
		chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		if (chooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) 
			textField.setText(chooser.getSelectedFile()+"");
	}
	
	
}
