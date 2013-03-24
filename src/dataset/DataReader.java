package dataset;

import imageprocessing.ImageProcessor;

import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.IIOException;
import javax.imageio.ImageIO;
import javax.swing.JOptionPane;

import utilities.FileHelper;
import views.ProgressPane;

public class DataReader extends FileHelper
{
	private double[][] inputVector;
	private double[][] outputVector;
	private ArrayList<String> filename = new ArrayList<String>();
	
	private ArrayList<BufferedImage> input_list = new ArrayList<BufferedImage>();
	private ArrayList<Integer> output_list = new ArrayList<Integer>();
	
	private ImageProcessor iProcessor;
	private File dataFile;
	private ProgressPane progressPane;
	private BufferedReader bufferedReader;
	
	public DataReader(ProgressPane progressPane, File dataFile) 
	{
		this.dataFile = dataFile;
		this.progressPane = progressPane;
		iProcessor = new ImageProcessor();
	}

	/**
	 * reads .data file containing absolute paths of images and extracts features from each image
	 * 
	 * @return Data 
	 */
	public Data read() 
	{
		int count = FileHelper.countFiles(dataFile);
		progressPane.reset(count*2);
		String fname = "";
		File file = null;
		try {
			bufferedReader = new BufferedReader(new FileReader(dataFile));
			int classNumber;
        	BufferedImage image = null;
			while( (fname = bufferedReader.readLine()) != null ) 
			{
				file = new File(fname);
        		image = ImageIO.read(file);
        		image = iProcessor.resizeImage(image, ImageProcessor.WIDTH, ImageProcessor.HEIGHT);
        		classNumber =  Integer.parseInt(file.getParentFile().getName());
        		
        		input_list.add(image);
	        	output_list.add(classNumber);
	        	progressPane.incrementBar();
        		filename.add(fname);
			}			
		} catch (IIOException e) {
			//Debugger.printError("Can't read file: "+fname);
		} catch (FileNotFoundException e) {
			//Debugger.printError("File not found: "+fname);
		} catch (IOException e) {			
			//
		} catch (NumberFormatException e) {
			//Debugger.printError("Can't convert to integer: "+file.getParentFile().getName());	
		} 
		inputVector = iProcessor.createInputVectorArray(input_list, progressPane);
    	outputVector = iProcessor.createOutputVector(output_list);

    	//JOptionPane.showMessageDialog(null, "Loaded "+count +" images.");

		return new Data(filename, inputVector, outputVector);
	}
	
}
