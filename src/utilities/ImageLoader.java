package utilities;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;

import ui.AppFrame;

import dialogs.PreparingDialog;

public class ImageLoader extends Thread{
	
	private File folder;
	
	private ArrayList<BufferedImage> training_input = new ArrayList<BufferedImage>();
	private ArrayList<Integer> training_output = new ArrayList<Integer>();
	
	private ImageHandler iHandler = new ImageHandler();
	private JTextArea dArea;
	
	private PreparingDialog prog;
	private int counter = 0;
	
	private AppFrame appFrame;
	
	public ImageLoader(AppFrame appFrame, String path, PreparingDialog prog) {
		this.appFrame = appFrame;
		this.prog = prog;
		folder = new File(path);
	}
	
	private void loadAllImages(final File folder) {
		for (final File fileEntry : folder.listFiles()) {
	        if (fileEntry.isDirectory()) { 
	        	loadAllImages(fileEntry);
	        }
	        else {
	        	try {
	        		BufferedImage img = ImageIO.read(new File( folder.getAbsoluteFile()+"/"+fileEntry.getName()));
					img = iHandler.resize(img, 256, 256);
					training_input.add(img);
					training_output.add( Integer.parseInt(folder.getName()) );
					
					counter++;
					prog.setValue(counter);
	        	} catch (IOException e) {
					e.printStackTrace();
				}
	        }
	    }
	}

	public double[][] getTrainingInput() {
		return input_data;//training_input;
	}

	public double[][] getTrainingOutput() {
		return output_data;//training_output;
	}
	
	private double[][] input_data;
	private double[][] output_data;
	
	@Override
	public void run() {
		int max = initProgressBar(folder);
		//prog.setMax(max*2);
		prog.start(max*2);
		loadAllImages(folder);
		
		input_data = iHandler.createInputVectorArray(training_input, prog);
		output_data = iHandler.createOutputVectorArray(training_output);
		prog.setVisible(false);
		JOptionPane.showMessageDialog(appFrame, max+" images has been loaded.");
		
		//dArea.append("\n"+max+" images has been loaded.\n");
		//dArea.append("\n****************LOADING TRAINING IMAGE END**************\n");
	}
	
	private int initProgressBar(File folder) {
		int ctr = 0;
		for (final File fileEntry : folder.listFiles()) {
	        if (fileEntry.isDirectory())  
	        	ctr += initProgressBar(fileEntry);
	        else 
	        	ctr++;
		}
		return ctr;
	}

}
