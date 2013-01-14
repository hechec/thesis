package views;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.ScrollPane;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;

import custom.MainButton;

import util2.FileChooser;
import abcnn.Result;

public class BRemoverPane extends JPanel
{
	private static BRemoverPane instance = null;
	private Frame frame;
	
	public static BRemoverPane getInstance() 
	{
		if(instance == null)
			instance = new BRemoverPane();
		return instance;
	}
	
	public BRemoverPane()
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
		
		JPanel line1 = new JPanel();
		line1.setBackground(new Color(255, 204, 51));
		line1.setBounds(218, 28, 250, 1);
		add(line1);
		
		JLabel label1 = new JLabel("Color Features");
		label1.setFont(new Font("Century Gothic", Font.PLAIN, 20));
		label1.setForeground(Color.WHITE);
		label1.setBounds(218, 41, 194, 26);
		add(label1);
		
		JLabel inputLabel = new JLabel();
		inputLabel.setBounds(482, 28, 180, 180);
		inputLabel.setBackground(Color.GRAY);
		inputLabel.setOpaque(true);
		add(inputLabel);
		
		JLabel filenameLabel = new JLabel("tomato.jpg");
		filenameLabel.setBounds(480, 213, 150, 30);
		filenameLabel.setFont(new Font("Century Gothic", Font.PLAIN, 16));
		filenameLabel.setForeground(Color.WHITE);
		//filenameLabel.setBackground(Color.BLACK);
		//filenameLabel.setOpaque(true);
		add(filenameLabel);
		
		JButton tButton = new MainButton("src/images/pencil.png", "src/images/pencil.png");
		tButton.setBounds(640, 215, 35, 33);
		add(tButton);
		tButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
			}
		});
		
		JLabel label2 = new JLabel("Red:");
		label2.setFont(new Font("Century Gothic", Font.PLAIN, 16));
		label2.setForeground(Color.WHITE);
		label2.setBounds(259, 78, 60, 22);
		label2.setHorizontalAlignment(SwingConstants.RIGHT);
		add(label2);
		
		JLabel label3 = new JLabel("Green:");
		label3.setFont(new Font("Century Gothic", Font.PLAIN, 16));
		label3.setForeground(Color.WHITE);
		label3.setBounds(259, 109, 60, 22);
		label3.setHorizontalAlignment(SwingConstants.RIGHT);
		add(label3);
		
		JLabel label4 = new JLabel("R-G:");
		label4.setFont(new Font("Century Gothic", Font.PLAIN, 16));
		label4.setForeground(Color.WHITE);
		label4.setBounds(259, 136, 60, 22);
		label4.setHorizontalAlignment(SwingConstants.RIGHT);
		add(label4);
		
		JLabel label5 = new JLabel("Hue:");
		label5.setFont(new Font("Century Gothic", Font.PLAIN, 16));
		label5.setForeground(Color.WHITE);
		label5.setBounds(259, 163, 60, 22);
		label5.setHorizontalAlignment(SwingConstants.RIGHT);
		add(label5);
		
		JLabel label6 = new JLabel("a*:");
		label6.setFont(new Font("Century Gothic", Font.PLAIN, 16));
		label6.setForeground(Color.WHITE);
		label6.setBounds(259, 193, 60, 22);
		label6.setHorizontalAlignment(SwingConstants.RIGHT);
		add(label6);
		
		JLabel redLabel = new JLabel("--");
		redLabel.setFont(new Font("Century Gothic", Font.PLAIN, 16));
		redLabel.setForeground(Color.WHITE);
		redLabel.setBounds(330, 78, 60, 22);
		add(redLabel);
		
		JLabel greenLabel = new JLabel("--");
		greenLabel.setFont(new Font("Century Gothic", Font.PLAIN, 16));
		greenLabel.setForeground(Color.WHITE);
		greenLabel.setBounds(330, 109, 60, 22);
		add(greenLabel);
		
		JLabel redgreenLabel = new JLabel("--");
		redgreenLabel.setFont(new Font("Century Gothic", Font.PLAIN, 16));
		redgreenLabel.setForeground(Color.WHITE);
		redgreenLabel.setBounds(330, 136, 60, 22);
		add(redgreenLabel);
		
		JLabel hueLabel = new JLabel("--");
		hueLabel.setFont(new Font("Century Gothic", Font.PLAIN, 16));
		hueLabel.setForeground(Color.WHITE);
		hueLabel.setBounds(330, 163, 60, 22);
		add(hueLabel);
		
		JLabel aLabel = new JLabel("--");
		aLabel.setFont(new Font("Century Gothic", Font.PLAIN, 16));
		aLabel.setForeground(Color.WHITE);
		aLabel.setBounds(330, 193, 60, 22);
		add(aLabel);
		
		JPanel processPanel = new JPanel();
		processPanel.setBackground(Color.GRAY);
		
		JScrollPane scrollPane = new JScrollPane(processPanel, 
					JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		scrollPane.setBounds(15, 265, 670, 190);	
		add(scrollPane);
		
		JButton removeButton = new JButton("REMOVE");
		removeButton.setBounds(20, 200, 80, 40);
		add(removeButton);
		
		
	}
	
}
