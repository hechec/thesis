package views;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import custom.MainButton;
import custom.MyTextField;

public class RandomizerPane extends JPanel 
{
	private static RandomizerPane instance = null;
	private Frame frame;
	
	public static RandomizerPane  getInstance() 
	{
		if(instance == null)
			instance = new RandomizerPane();
		return instance;
	}
	
	public RandomizerPane()
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
		
		JLabel label = new JLabel("Data Set Randomizer");
		label.setFont(new Font("Century Gothic", Font.PLAIN, 24));
		label.setForeground(Color.WHITE);
		label.setBounds(145, 46, 300, 30);
		add(label);
		
		JPanel line = new JPanel();
		line.setBackground(new Color(255, 204, 51));
		line.setBounds(145, 83, 475, 1);
		add(line);
		
		JPanel cpanel = new JPanel();
		cpanel.setBackground(new Color(255, 204, 51));
		cpanel.setBounds(205, 117, 124, 30);
		add(cpanel);
		
		JLabel cLabel = new JLabel("Data Set");
		cLabel.setFont(new Font("Century Gothic", Font.PLAIN, 16));
		cLabel.setForeground(Color.BLACK);
		cLabel.setBounds(0, 0, 75, 30);
		cpanel.add(cLabel);
		
		final JTextField textField1 = new MyTextField("click to selected directory");
		textField1.setBounds(330, 117, 260, 30);
		textField1.setBorder(null);
		textField1.setFont(new Font("Century Gothic", Font.PLAIN, 16));
		textField1.setBorder(BorderFactory.createCompoundBorder( textField1.getBorder(),
					BorderFactory.createEmptyBorder(5, 5, 5, 5)));
		add(textField1);
		
		JButton tButton = new MainButton("src/images/pencil.png", "src/images/pencil.png");
		tButton.setBounds(595, 117, 35, 33);
		add(tButton);
		tButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
			}
		});
		
		JPanel panel = new JPanel();
		panel.setBackground(new Color(255, 204, 51));
		panel.setBounds(205, 226, 124, 30);
		add(panel);
		
		JLabel label1 = new JLabel("Training Set");
		label1.setFont(new Font("Century Gothic", Font.PLAIN, 16));
		label1.setForeground(Color.BLACK);
		label1.setBounds(0, 0, 75, 30);
		panel.add(label1);
		
		JTextField trainingField = new MyTextField("click to selected directory");
		trainingField.setBounds(330, 226, 240, 30);
		trainingField.setBorder(null);
		trainingField.setFont(new Font("Century Gothic", Font.PLAIN, 16));
		trainingField.setBorder(BorderFactory.createCompoundBorder( trainingField.getBorder(),
					BorderFactory.createEmptyBorder(5, 5, 5, 5)));
		add(trainingField);
		
		JLabel label2 = new JLabel(".data");
		label2.setFont(new Font("Century Gothic", Font.PLAIN, 16));
		label2.setForeground(Color.WHITE);
		label2.setBounds(572, 232, 50, 21);
		add(label2);
		
		JPanel panel2 = new JPanel();
		panel2.setBackground(new Color(255, 204, 51));
		panel2.setBounds(205, 274, 124, 30);
		add(panel2);
		
		JLabel label3 = new JLabel("Testing Set");
		label3.setFont(new Font("Century Gothic", Font.PLAIN, 16));
		label3.setForeground(Color.BLACK);
		label3.setBounds(0, 0, 75, 30);
		panel2.add(label3);
		
		JTextField testingField = new MyTextField("click to selected directory");
		testingField.setBounds(330, 274, 240, 30);
		testingField.setBorder(null);
		testingField.setFont(new Font("Century Gothic", Font.PLAIN, 16));
		testingField.setBorder(BorderFactory.createCompoundBorder( testingField.getBorder(),
					BorderFactory.createEmptyBorder(5, 5, 5, 5)));
		add(testingField);
		
		JLabel label4 = new JLabel(".data");
		label4.setFont(new Font("Century Gothic", Font.PLAIN, 16));
		label4.setForeground(Color.WHITE);
		label4.setBounds(572, 280, 50, 21);
		add(label4);
		
		JButton randomize = new JButton("Randomize");
		randomize.setBounds(20, 200, 90, 30);
		add(randomize);
		randomize.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(!textField1.getText().isEmpty()) {
					System.out.println("randomize");
				}
			}
		});
		
	}	
}
