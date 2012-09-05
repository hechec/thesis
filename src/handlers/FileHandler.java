package handlers;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.JTextArea;

import dialogs.PreparingDialog;

public class FileHandler extends Thread{
	
	private File folder;
	
	private ArrayList<BufferedImage> training_input = new ArrayList<BufferedImage>();
	private ArrayList<Integer> training_output = new ArrayList<Integer>();
	
	private ImageHandler iHandler = new ImageHandler();
	private JTextArea dArea;
	
	private PreparingDialog prog;
	private int counter = 0;
	
	public FileHandler(String path, JTextArea dArea, PreparingDialog prog) {
		this.dArea = dArea;
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
					img = iHandler.resize(img, 256,256);
					training_input.add(img);
					training_output.add( Integer.parseInt(folder.getName()) );
					
					counter++;
					dArea.append("Loaded "+fileEntry.getAbsolutePath()+".\n");
					prog.setValue(counter);
	        	} catch (IOException e) {
					e.printStackTrace();
				}
	        }
	    }
	}

	public ArrayList<BufferedImage> getTrainingInput() {
		return training_input;
	}

	public ArrayList<Integer> getTrainingOutput() {
		return training_output;
	}

	@Override
	public void run() {
		dArea.append("***************LOADING TRAINING IMAGE START*************\n\n");
		int max = initProgressBar(folder);
		prog.setMax(max);
		loadAllImages(folder);
		prog.hide();
		dArea.append("\n"+max+" images has been loaded.\n");
		dArea.append("\n****************LOADING TRAINING IMAGE END**************\n");
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
