package ui;

import javax.swing.JTextArea;

public class GuiController {
	
	private FeaturesPane fPane;
	private JTextArea dArea;
	
	public GuiController(ExtractionPane extractionTab, JTextArea dArea) {
		this.dArea = dArea;
		this.fPane = extractionTab.getFeaturesPane();		
	}

	public void setMessage(String message) {
		dArea.append(message+"\n");
	}

	public void setRedField(double meanRed) {
		fPane.setRedField(meanRed);		
	}

	public void setGreenField(double meanGreen) {
		fPane.setGreenField(meanGreen);		
	}

	public void setRGField(double meanRG) {
		fPane.setRedGreenField(meanRG);
	}

	public void setAField(double meanA) {
		fPane.setAField(meanA);
	}

	public void setHueField(double meanHue) {
		fPane.setHueField(meanHue);
	}

	public void resetExtraction() {
		setMessage("reset.");
		fPane.reset();
	}

}
