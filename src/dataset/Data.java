package dataset;

import java.io.File;
import java.util.ArrayList;

public class Data 
{
	private double[][] inputVector;
	private double[][] outputVector;
	private ArrayList<String> filename;
	
	public Data(ArrayList<String> filename, double[][] inputVector, double[][] outputVector)
	{
		this.filename = filename;
		this.inputVector = inputVector;
		this.outputVector = outputVector;
	}
	
	/**
	 * 
	 * @return M x N array representing M samples and N features for each sample.
	 */
	public double[][] getInputVector()
	{
		return inputVector;
	}
	
	/**
	 * sample row = {0, 0, 0, 0, 0, 1} corresponding to red stage
	 * 
	 * @return M x N array representing M samples and N output vector 
	 */
	public double[][] getOutputVector()
	{
		return outputVector;
	}

	public String getFilename(int index) 
	{
		return filename.get(index);
	}
	
	public int size() 
	{
		return filename.size();
	}

	public String getFilename2(int index) {
		File file = new File(filename.get(index));
		return file.getName();
	}
	
}
