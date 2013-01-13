package views;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.plaf.basic.BasicProgressBarUI;

public class ProgressPane extends JPanel
{
	private JProgressBar progressBar;
	
	public ProgressPane() 
	{
		setSize(700, 50);
		setLayout(null);
		
		progressBar = new JProgressBar();
		progressBar.setUI(new BasicProgressBarUI());
		progressBar.setOpaque(false);
		progressBar.setBorderPainted(false);
		progressBar.setBounds(0, 0, this.getWidth(), this.getHeight()-1);
		progressBar.setMinimum(0);
		progressBar.setFont(new Font("Century Gothic", Font.PLAIN, 15));
		progressBar.setForeground(Color.BLACK);
		add(progressBar, 0);
	}
	
	@Override
    public void paintComponent(Graphics g) {
		Graphics2D g2 = (Graphics2D) g;
		Image image = null;
		try {                
			image = ImageIO.read(new File("src/images/footer.png"));
        } catch (IOException ex) {
        	System.out.println("Check footer image.");
        }
        g2.drawImage(image, 0, 0, null);          
    }
	
	public void reset(int max) 
	{
		progressBar.setStringPainted(true);
		progressBar.setValue(0);
		progressBar.setMaximum(max);
	}

	public void incrementBar() 
	{
		progressBar.setValue(progressBar.getValue()+1);
		this.updateUI();
	}
}
