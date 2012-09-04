package ui;

import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JFileChooser;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;

public class ExtractionPane extends JPanel {
	
	private ImagePane iPane;
	private FeaturesPane fPane;

	private JTextArea dArea;
	private JFileChooser fc;
	
	private GuiController gc;
	
	public ExtractionPane(JTextArea dArea) {
		this.setLayout(null);
		
		this.dArea = dArea;
		
		iPane = new ImagePane();
		add(iPane);
		
		JPanel rightPane = new JPanel();
		rightPane.setBounds(450, 0, 200, 350);
		add(rightPane);
		rightPane.setLayout(null);
		
		fPane = new FeaturesPane();
		fPane.setBounds(0, 11, 199, 330);
		rightPane.add(fPane);
		
		FileFilter filter = new FileNameExtensionFilter("JPEG file", "jpg", "jpeg", "png", "gif");
		fc = new JFileChooser();
		fc.setFileFilter(filter);
	}
	
	public void setController(GuiController gc) {
		this.gc = gc;
		iPane.setController(gc);
	}

	public void selectImage() {
		 dArea.append("opening...\n");
		 if(fc.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
			 try {
				 
				 iPane.showInput(ImageIO.read(fc.getSelectedFile()));
				 dArea.append("file opened: "+fc.getSelectedFile()+"\n");
			} catch (IOException e) {
				e.printStackTrace();
			}
		 }
		 else
			 dArea.append("aborted.\n");
		
	}

	public FeaturesPane getFeaturesPane() {
		return fPane;
	}

}
