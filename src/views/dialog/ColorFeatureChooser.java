package views.dialog;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;

import custom.MainButton;

import views.ExperimentPane;
import views.Frame;

public class ColorFeatureChooser extends JDialog 
{
	private String[] features = {"Red", "Green", "R-G", "Blue", "Hue", "a*"};
	
	private static ColorFeatureChooser instance = null;
	
	public static ColorFeatureChooser getInstance()
	{
		if(instance == null)
			instance = new ColorFeatureChooser();
		return instance;
	}
	
	public ColorFeatureChooser() 
	{
		final Frame frame = Frame.getInstance();
		
		setSize(350, 300);
		setModal(true);
		setLocationRelativeTo(frame);
		setUndecorated(true);
		getContentPane().setBackground(Color.WHITE);
		getContentPane().setLayout(null);
		
		final URL bgUrl = getClass().getResource("/images/bg.png");
		JPanel panel = new JPanel() {
			@Override
		    public void paintComponent(Graphics g) {
		        super.paintComponent(g);
				Image image = null;
				try {                
					image = ImageIO.read(bgUrl);
		        } catch (IOException ex) { }
		        g.drawImage(image, 0, 0, null);          
		    }
		};
		panel.setBounds(5, 5, 340, 290);
		panel.setLayout(null);
		getContentPane().add(panel);
		
		JButton exitButton = new MainButton("/images/close.png", "/images/closeHover.png");
		exitButton.setBorderPainted(false);
		exitButton.setBounds(301, 1, 38, 23);
		panel.add(exitButton, 0);
		exitButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		
		JLabel label = new JLabel("FEATURES");
		label.setBounds(45, 50, 100, 30);
		label.setForeground(Color.WHITE);
		label.setFont(new Font(null, Font.PLAIN, 14));
		panel.add(label);
		
		JLabel label2 = new JLabel("SELECTED");
		label2.setBounds(220, 50, 100, 30);
		label2.setForeground(Color.WHITE);
		label2.setFont(new Font(null, Font.PLAIN, 14));
		panel.add(label2);
		
		final DefaultListModel<String> model = new DefaultListModel<String>();
		final JList list = new JList<String>(model);
		for(int i = 0; i < features.length; i++)
			model.add(i, features[i]);
		list.setBounds(30, 80, 100, 150);	
		list.setFixedCellHeight(25);
		panel.add(list);
		list.setSelectedIndex(0);
		
		final DefaultListModel<String> model2 = new DefaultListModel<String>();
		final JList list2 = new JList<String>(model2);
		list2.setBounds(210, 80, 100, 150);	
		list2.setFixedCellHeight(25);
		panel.add(list2);
		list2.setSelectedIndex(0);	
		
		JButton addButton = new JButton(">>");
		addButton.setBounds(145, 100, 50, 40);
		panel.add(addButton);
		addButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				int index = list.getSelectedIndex();
				if(index != -1) {
					model2.addElement(model.getElementAt(index));
					model.remove(index);
					list.setSelectedIndex(index);
				}
			}
		});
		
		JButton removeButton = new JButton("<<");
		removeButton.setBounds(145, 170, 50, 40);
		panel.add(removeButton);
		removeButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				int index = list2.getSelectedIndex();
				if(index != -1) {
					model.addElement(model2.getElementAt(index));
					model2.remove(index);
					list2.setSelectedIndex(index);
				}
			}
		});
		
		JButton saveButton = new JButton("SAVE");
		saveButton.setBounds(130, 245, 80, 35);
		panel.add(saveButton);
		saveButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				ExperimentPane.getInstance().setColorFeatures(model2);
				dispose();
			}
		});
		
	}

}
