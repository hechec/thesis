package util;

import imageProcessing.ImageProcessor;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.CountDownLatch;

import javax.imageio.ImageIO;
import javax.swing.JOptionPane;

import ui.ABCNNTab;
import ui.BottomPane;

import abcnn.Classifier;
import dialogs.LoadingDialog;

/**
 * class that loads images for training and testing
 * @author Harvey Jake Opena
 *
 */

public class DataLoader {
	
	/*private double[][] training_input;
	private double[][] training_output;

	private double[][] testing_input;
	private int[] expectedOutput;

	private ArrayList<BufferedImage> training_input_list = new ArrayList<BufferedImage>();
	private ArrayList<Integer> training_output_list = new ArrayList<Integer>();
	
	private ArrayList<BufferedImage> testing_input_list = new ArrayList<BufferedImage>();
	private ArrayList<Integer> testing_output_list = new ArrayList<Integer>();
	*/
	private double[][] train_input;
	private double[][] train_output;
	private double[][] test_input;
	private int[] test_output;

	private ArrayList<BufferedImage> train_input_list = new ArrayList<BufferedImage>();
	private ArrayList<Integer> train_output_list = new ArrayList<Integer>();
	
	private ArrayList<BufferedImage> test_input_list = new ArrayList<BufferedImage>();
	private ArrayList<Integer> test_output_list = new ArrayList<Integer>();
	
	private static final float TRAINING_PERCENTAGE = 0.7f;
	
	private Random rand = new Random();
	
	private Classifier classifier;
	private LoadingDialog prog;
	private ImageProcessor iProcessor = ImageProcessor.getInstance();
	private BottomPane bottomPane;
	private File file;
	
	private int counter = 0;
	private int count = 0;
	
	public DataLoader(Classifier classifier, LoadingDialog prog) {
		this.classifier = classifier;
		this.prog = prog;
	}
	
	public DataLoader(BottomPane bottomPane, Classifier classifier, File file) {
		this.classifier = classifier;
		this.bottomPane = bottomPane;
		this.file = file;
		
		count = countFiles(file);
		bottomPane.setProgressBarMax(count*2);
	}

	public void load(final File file) {
		//final int max = countFiles(file);
		//loadingPanel.setMax(max*2);
		new Thread(new Runnable() {
			@Override
			public void run() {
				
				//prog.start(max*2);
				
				loadAllImages(file);
				
				train_input = iProcessor.createInputVectorArray(train_input_list, bottomPane);
		    	train_output = iProcessor.createOutputVectorArray(train_output_list);

		    	test_input = iProcessor.createInputVectorArray(test_input_list, bottomPane);
		    	test_output = convertOutputList(test_output_list);
		    	
		    	classifier.setTrainData(train_input, train_output);
		    	classifier.setTestData(test_input, test_output);
				//training_input = iProcessor.createInputVectorArray(training_input_list, loadingPanel);
	        	//training_output = iProcessor.createOutputVectorArray(training_output_list);
	        	
	        	//testing_input = iProcessor.createInputVectorArray(testing_input_list, loadingPanel);
	        	//convertOutputList();
	        	
	        	//classifier.setPrepared(training_input, training_output);
	        	
	        	//prog.dispose();
		    	bottomPane.setStatus(count+" "+BottomPane.END_LOADING);
	        	JOptionPane.showMessageDialog(null,  count+" images has been loaded.");
	        	
			}
		}).start();
	}
	
	private void loadAllImages(final File folder) {
		for (final File fileEntry : folder.listFiles()) 
	        if (fileEntry.isDirectory()) { 
	        	File[] fileList = fileEntry.listFiles();
	        	int total = fileList.length;
	        	
	        	int[] indices_training = randomizeIndices(total);
	        	int[] indices_testing = getOtherIndices(indices_training, total);
	        	
	        	loadDataSet(fileEntry, indices_training, train_input_list, train_output_list);
	        	loadDataSet(fileEntry, indices_testing, test_input_list, test_output_list);
	        }
	}
	
	private void loadDataSet(File folder, int[] indices, ArrayList<BufferedImage> input_list, ArrayList<Integer> output_list) {

		File[] files = folder.listFiles();
		int len = indices.length;
		
		for( int i = 0; i < len; i++ ) {
			try {
				BufferedImage image = ImageIO.read(files[indices[i]].getAbsoluteFile());
				image = iProcessor.resizeImage(image, ImageProcessor.WIDTH, ImageProcessor.HEIGHT);
				
				input_list.add(image);
				output_list.add( Integer.parseInt(folder.getName()) );
				
				bottomPane.incrementBar();
				//loadingPanel.increment();
				//counter++;
				//prog.setValue(counter);
			} catch (IOException e) {
				e.printStackTrace();
			} catch (NumberFormatException e) {
				System.out.println("Ooooops. Invalid folder name '"+folder.getName()+"'.");
			}
		} 
	}
	
	private int[] convertOutputList(ArrayList<Integer> test_output_list) {
		int size = test_output_list.size();
		int[] expectedOutput = new int[size];
		for( int i = 0; i < size; i++ )
			expectedOutput[i] = test_output_list.get(i)-1;
		return expectedOutput;
	}
	
	private void convertOutputList() {
		//int size = testing_output_list.size();
		//expectedOutput = new int[size];
		//for( int i = 0; i < size; i++ )
		//	expectedOutput[i] = testing_output_list.get(i)-1;
	}

	private int[] randomizeIndices(int total) {
		int count = (int)(total*TRAINING_PERCENTAGE);
		int[] indices = new int[count];
		
		int index = 0;
		for( int ctr = 0; ctr < count; ctr++ ) {
			do{
				index = rand.nextInt(total);
			}while( hasBeenTaken(indices, index, ctr) );
			indices[ctr] = index;
		}
		
		return indices;
	}
	
	private int[] getOtherIndices(int[] indices_testing, int total) {
		int[] otherIndices = new int[total - indices_testing.length];
		for( int i = 0, index = 0; i < total; i++ ){
			if( !hasBeenTaken(indices_testing, i, indices_testing.length) )
				otherIndices[index++] = i;
		}
		return otherIndices;
	}

	private boolean hasBeenTaken(int[] indices, int index, int ctr) {
		for( int i = 0; i < ctr; i++ )
			if( indices[i] == index )
				return true;
		return false;
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
	
	public double[][] getTrainingInput() {
		return null;//training_input;
	}
	
	public double[][] getTrainingOutput() {
		return null;//training_output;
	}
	
	public double[][] getTestingInput() {
		return null;//testing_input;
	}
	
	public int[] getTestingOutput() {
		return null;//expectedOutput;
	}

}
