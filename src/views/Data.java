package views;

public class Data 
{
	private double[][] input;
	private double[][] output;
	
	public Data(double[][] input, double[][] output)
	{
		this.input = input;
		this.output = output;
	}
	
	/**
	 * 
	 * @return M x N array representing M samples and N features for each sample.
	 */
	public double[][] getInput()
	{
		return input;
	}
	
	/**
	 * sample row = {0, 0, 0, 0, 0, 1} corresponding to red stage
	 * 
	 * @return M x N array representing M samples and N output vector 
	 */
	public double[][] getOutput()
	{
		return output;
	}
	
}
