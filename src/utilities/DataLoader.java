package utilities;

import imageProcessing.ImageProcessor;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.swing.JOptionPane;

import abcnn.Classifier;
import dialogs.LoadingDialog;

/**
 * class that loads images for training and testing
 * @author Harvey Jake Opena
 *
 */

public class DataLoader {
	
	private double[][] training_input;
	private double[][] training_output;

	private double[][] testing_input;
	private int[] expectedOutput;

	private ArrayList<BufferedImage> training_input_list = new ArrayList<BufferedImage>();
	private ArrayList<Integer> training_output_list = new ArrayList<Integer>();
	
	private ArrayList<BufferedImage> testing_input_list = new ArrayList<BufferedImage>();
	private ArrayList<Integer> testing_output_list = new ArrayList<Integer>();
	
	private static final float TRAINING_PERCENTAGE = 0.7f;
	
	private Random rand = new Random();
	
	private Classifier classifier;
	private LoadingDialog prog;
	private ImageProcessor iProcessor = ImageProcessor.getInstance();
	
	private int counter = 0;
	
	public DataLoader(Classifier classifier, LoadingDialog prog) {
		this.classifier = classifier;
		this.prog = prog;
	}
	
	public void load(final File file) {
		new Thread(new Runnable() {
			@Override
			public void run() {
				int max = countFiles(file);
				prog.start(max*2);
				
				loadAllImages(file);
				
				training_input = iProcessor.createInputVectorArray(training_input_list, prog);
	        	training_output = iProcessor.createOutputVectorArray(training_output_list);
	        	
	        	testing_input = iProcessor.createInputVectorArray(testing_input_list, prog);
	        	convertOutputList();
	        	
	        	classifier.setPrepared(training_input, training_output);
	        	
	        	prog.dispose();
	        	JOptionPane.showMessageDialog(null,  max+" images has been loaded.");
	        	
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
	        	
	        	loadDataSet(fileEntry, indices_training, training_input_list, training_output_list);
	        	loadDataSet(fileEntry, indices_testing, testing_input_list, testing_output_list);
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
				
				counter++;
				prog.setValue(counter);
			} catch (IOException e) {
				e.printStackTrace();
			} catch (NumberFormatException e) {
				System.out.println("Ooooops. Invalid folder name '"+folder.getName()+"'.");
			}
		} 
	}
	
	private void convertOutputList() {
		int size = testing_output_list.size();
		expectedOutput = new int[size];
		for( int i = 0; i < size; i++ )
			expectedOutput[i] = testing_output_list.get(i)-1;
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
		return training_input;
	}
	
	public double[][] getTrainingOutput() {
		return training_output;
	}
	
	public double[][] getTestingInput() {
		return testing_input;
	}
	
	public int[] getTestingOutput() {
		return expectedOutput;
	}

}
