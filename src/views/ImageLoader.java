package views;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.JOptionPane;

public class ImageLoader 
{
	private double[][] input;
	private double[][] output;

	private ArrayList<BufferedImage> input_list = new ArrayList<BufferedImage>();
	private ArrayList<Integer> output_list = new ArrayList<Integer>();
	
	private ImageProcessor iProcessor;
	private int count = 0;
	private File trainFile;
	private ProgressPane progressPane;

	public ImageLoader(ProgressPane progressPane, File trainFile) 
	{
		this.trainFile = trainFile;
		this.progressPane = progressPane;
		iProcessor = new ImageProcessor();
		count += countFiles(trainFile);
		progressPane.reset(count*2);
	}
	
	/**
	 * Loads training images and extracts features of each image
	 * 
	 * @return Data - training data
	 */
	public Data load() 
	{
		loadAllImages(trainFile, input_list, output_list);
		
		input = iProcessor.createInputVectorArray(input_list, progressPane);
    	output = iProcessor.createOutputVector(output_list);
    	
    	JOptionPane.showMessageDialog(null, "Loaded "+count +" images.");
    	
    	return new Data(input, output);
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
	        	progressPane.incrementBar();
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
	
}
