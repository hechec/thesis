package util;

import imageProcessing.ImageProcessor;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;

import abcnn.Classifier;

import ui.ABCNNTab;
import ui.AppFrame;
import ui.BatchPane;

import dialogs.LoadingDialog;

public class ImageLoader{
	
	private File folder;
	
	private ArrayList<BufferedImage> inputList = new ArrayList<BufferedImage>();
	private ArrayList<Integer> outputList = new ArrayList<Integer>();
	
	private ImageProcessor iProcessor = ImageProcessor.getInstance();
	private JTextArea dArea;
	
	private LoadingDialog prog;
	private int counter = 0;
	
	private AppFrame appFrame;
	private BatchPane batchPane;
	private Classifier classifier;

	private double[][] input_data;
	private double[][] output_data;
	private int[] expectedOutput;
	
	private static int loadType;
	private static final int TRAINING = 0;
	private static final int TESTING = 1;
	
	public ImageLoader(Classifier classifier, String path, LoadingDialog prog) {
		this.classifier = classifier;
		this.prog = prog;
		folder = new File(path);
		loadType = TRAINING;
		loadData();
	}
	
	public ImageLoader(ABCNNTab abcnnPane, BatchPane batchPane, String path, LoadingDialog prog) {
		this.batchPane = batchPane;
		this.prog = prog;
		folder = new File(path);
		loadType = TESTING;
		loadData();
	}
	
	private void loadData() {
		new Thread(new Runnable() {
			@Override
			public void run() {
				int max = countFiles(folder);
				prog.start(max*2);
				loadAllImages(folder);
				
				//input_data = iProcessor.createInputVectorArray(inputList, prog);
				output_data = iProcessor.createOutputVectorArray(outputList);
				prog.dispose();
				
				if(loadType == TRAINING)
					classifier.setPrepared(input_data, output_data);
				else if(loadType == TESTING) {
					convertOutputList();
					batchPane.setReady(true);
				}
				JOptionPane.showMessageDialog(appFrame, max+" images has been loaded.");
				
			}
		}).start();
	}
	
	private void loadAllImages(final File folder) {
		for (final File fileEntry : folder.listFiles()) {
	        if (fileEntry.isDirectory()) { 
	        	loadAllImages(fileEntry);
	        }
	        else {
	        	try {
	        		BufferedImage img = ImageIO.read(new File( folder.getAbsoluteFile()+"/"+fileEntry.getName()));
					img = iProcessor.resizeImage(img, ImageProcessor.WIDTH, ImageProcessor.HEIGHT);
					inputList.add(img);
					outputList.add( Integer.parseInt(folder.getName()) );
					
					counter++;
					prog.setValue(counter);
	        	} catch (IOException e) {
					e.printStackTrace();
				}
	        }
	    }
	}
	
	private void convertOutputList() {
		int size = outputList.size();
		expectedOutput = new int[size];
		for( int i = 0; i < size; i++ )
			expectedOutput[i] = outputList.get(i)-1;
	}

	public double[][] getInputVector() {
		return input_data;//training_input;
	}

	public double[][] getOutputVector() {
		return output_data;//training_output;
	}
	
	public int[] getExpectedOutput() {
		return expectedOutput;
	}
	
	private int countFiles(File folder) {
		int ctr = 0;
		for (final File fileEntry : folder.listFiles()) {
	        if (fileEntry.isDirectory())  
	        	ctr += countFiles(fileEntry);
	        else 
	        	ctr++;
		}
		return ctr;
	}

}
