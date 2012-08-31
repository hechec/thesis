package ui;

import javax.swing.JTextArea;

public class GuiController {
	
	private ImagePane iPane;
	private FeaturesPane fPane;
	private JTextArea dArea;
	
	public GuiController(ImagePane iPane, FeaturesPane fPane, JTextArea dArea) {
		this.iPane = iPane;
		this.fPane = fPane;
		this.dArea = dArea;
	}
	
	public void setMessage(String message) {
		dArea.append(message+"\n");
	}

	public void setRedField(int meanRed) {
		fPane.setRedField(meanRed);		
	}

	public void setGreenField(int meanGreen) {
		fPane.setGreenField(meanGreen);		
	}

	public void setRGField(int meanRG) {
		fPane.setRedGreenField(meanRG);
	}

	public void setAField(int meanA) {
		fPane.setAField(meanA);
	}

	public void setHueField(int meanHue) {
		fPane.setHueField(meanHue);
	}

}
