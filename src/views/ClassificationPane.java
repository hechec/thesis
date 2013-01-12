package views;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;

import custom.MainButton;

public class ClassificationPane extends JPanel
{
	private static ClassificationPane instance = null;
	
	private Frame frame;
	
	public static ClassificationPane getInstance() 
	{
		if(instance == null)
			instance = new ClassificationPane();
		return instance;
	}
	
	public ClassificationPane()
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
		
	}
	
	
}
