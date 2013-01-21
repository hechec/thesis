package dataset;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.JOptionPane;

import com.sun.xml.internal.bind.v2.schemagen.xmlschema.List;

import util2.FileHelper;
import views.ImageProcessor;
import views.ProgressPane;

public class DataLoader extends FileHelper
{
	private double[][] inputVector;
	private double[][] outputVector;
	private ArrayList<String> filename = new ArrayList<String>();
	
	private ArrayList<BufferedImage> input_list = new ArrayList<BufferedImage>();
	private ArrayList<Integer> output_list = new ArrayList<Integer>();
	
	private ImageProcessor iProcessor;
	private int count = 0;
	private File file;
	private ProgressPane progressPane;

	public DataLoader(ProgressPane progressPane, File file) 
	{
		this.file = file;
		this.progressPane = progressPane;
		iProcessor = new ImageProcessor();
		count += FileHelper.countFiles(file);
		progressPane.reset(count*2);
	}
	
	/**
	 * Loads images and extracts features of each image
	 * 
	 * @return Data 
	 */
	public Data load() 
	{
		loadAllImages(file, input_list, output_list);
		
		inputVector = iProcessor.createInputVectorArray(input_list, progressPane);
    	outputVector = iProcessor.createOutputVector(output_list);
    	
    	JOptionPane.showMessageDialog(null, "Loaded "+count +" images.");
    	
    	return new Data(filename, inputVector, outputVector);
	}
	
	private void loadAllImages(final File folder, ArrayList<BufferedImage> input_list, ArrayList<Integer> output_list) 
	{
		for (final File fileEntry : folder.listFiles()) {
	        if (fileEntry.isDirectory()) 
	        	loadAllImages(fileEntry, input_list, output_list);
	        else {
	        	File file = null;
	        	BufferedImage image = null;
	        	int classNumber = -1;
	        	try {
	        		file = new File(fileEntry.getAbsolutePath());
	        		image = ImageIO.read(file);
	        		image = iProcessor.resizeImage(image, ImageProcessor.WIDTH, ImageProcessor.HEIGHT);
	        		classNumber =  Integer.parseInt(fileEntry.getParentFile().getName());
	        		
	        		filename.add(fileEntry.getName());
	        		input_list.add(image);
		        	output_list.add(classNumber);
		        	progressPane.incrementBar();
	        	} catch (IOException e) {
	        	} catch (NumberFormatException e) {}
	        }
	        	
		}
	}
	
}
