package views;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.SystemColor;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.SwingConstants;

public class ProgressPane extends JPanel
{
	private JProgressBar progressBar;
	private JLabel percentLabel;
	
	public ProgressPane(TrainPane trainPane) 
	{
		setSize(700, 50);
		setLayout(null);
		
		progressBar = new JProgressBar();
		progressBar.setOpaque(false);
		progressBar.setBorderPainted(false);
		progressBar.setBounds(0, 0, this.getWidth(), this.getHeight()-1);
		progressBar.setMinimum(0);
		progressBar.setForeground(Color.BLACK);
		add(progressBar, 0);
	
		percentLabel = new JLabel("0 %");
		percentLabel.setFont(new Font("Century Gothic", Font.PLAIN, 15));
		percentLabel.setForeground(Color.WHITE);
		percentLabel.setBounds(0, 0, 680, 50);
		percentLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		add(percentLabel, 0);

		percentLabel.setVisible(false);
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
		progressBar.setValue(0);
		progressBar.setMaximum(max);
	}

	public void incrementBar() 
	{
		percentLabel.setVisible(true);
		progressBar.setValue(progressBar.getValue()+1);
		percentLabel.setText( (int)(100*((float)progressBar.getValue()/(float)progressBar.getMaximum()))+" %" );
		this.updateUI();
	}
}
