package util;

import imageProcessing.ImageProcessor;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.JOptionPane;

import ui.BottomPane;
import abcnn.Classifier;

public class FixedDataLoader
{
	private Classifier classifier;
	
	private double[][] train_input;
	private double[][] train_output;
	private double[][] test_input;
	private int[] test_output;

	private ArrayList<BufferedImage> train_input_list = new ArrayList<BufferedImage>();
	private ArrayList<Integer> train_output_list = new ArrayList<Integer>();
	
	private ArrayList<BufferedImage> test_input_list = new ArrayList<BufferedImage>();
	private ArrayList<Integer> test_output_list = new ArrayList<Integer>();
	
	private ImageProcessor iProcessor;
	private BottomPane bottomPane;
	
	private int count = 0;
	private File trainFile, testFile;
	
	public FixedDataLoader(BottomPane bottomPane, Classifier classifier, File trainFile, File testFile)
	{
		this.classifier = classifier;
		this.trainFile = trainFile;
		this.testFile = testFile;
		iProcessor = new ImageProcessor();
		this.bottomPane = bottomPane;
		
		count += countFiles(trainFile);
		count += countFiles(testFile);
		bottomPane.setProgressBarMax(count*2);
	}
	
	private void loadAllImages(final File folder, ArrayList<BufferedImage> train_input_list, ArrayList<Integer> train_output_list) 
	{
		for (final File fileEntry : folder.listFiles()) {
	        if (fileEntry.isDirectory()) 
	        	loadAllImages(fileEntry, train_input_list, train_output_list);
	        else {
	        	File file = null;
	        	BufferedImage image = null;
	        	int classNumber = -1;
	        	try {
	        		file = new File(fileEntry.getAbsolutePath());
	        		image = ImageIO.read(file);
	        		image = iProcessor.resizeImage(image, ImageProcessor.WIDTH, ImageProcessor.HEIGHT);
					classNumber =  Integer.parseInt(fileEntry.getParentFile().getName());
				} catch (IOException e) {
				} catch (NumberFormatException e) {}
	        	
	        	train_input_list.add(image);
	        	train_output_list.add(classNumber);
	        	bottomPane.incrementBar();
	        	//System.out.println( fileEntry.getParentFile().getName()+" :"+fileEntry.getAbsolutePath() );
	        }
	        	
		}
	}
	
	private int countFiles(File folder) 
	{
		int ctr = 0;
		for (final File fileEntry : folder.listFiles()) {
	        if (fileEntry.isDirectory())  
	        	ctr += countFiles(fileEntry);
	        else 
	        	ctr++;
		}
		return ctr;
	}
	
	
	private int[] convertOutputList(ArrayList<Integer> test_output_list) {
		int size = test_output_list.size();
		int[] expectedOutput = new int[size];
		for( int i = 0; i < size; i++ )
			expectedOutput[i] = test_output_list.get(i)-1;
		return expectedOutput;
	}

	public void load() {
		loadAllImages(trainFile, train_input_list, train_output_list);
		loadAllImages(testFile, test_input_list, test_output_list);
		
		train_input = iProcessor.createInputVectorArray(train_input_list, bottomPane);
    	train_output = iProcessor.createOutputVectorArray(train_output_list);
    	
    	test_input = iProcessor.createInputVectorArray(test_input_list, bottomPane);
    	test_output = convertOutputList(test_output_list);
    	
    	classifier.setTrainData(train_input, train_output);
    	classifier.setTestData(test_input, test_output);
    	
    	bottomPane.setStatus(count+" "+BottomPane.END_LOADING);
    	JOptionPane.showMessageDialog(null, "Loaded "+count +" images.");
    	
	}
	
}