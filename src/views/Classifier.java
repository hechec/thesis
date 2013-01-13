package views;

import abcnn.MLPNetwork;
import abcnn.Result;

public class Classifier 
{	
	private double[] solution;
	
	public Classifier(double[] solution) 
	{
		this.solution = solution;	
	}
	
	public Result test_batch(double[][] test_input, int[] test_expected)
	{
		int[] actual = new int[test_expected.length];

		for( int i = 0; i < test_input.length; i++ ) 
			actual[i] = classify( test_input[i] );
		
		return new Result(test_expected, actual);
	}
	
	public int classify(double[] input) 
	{
		MLPNetwork classifier = new MLPNetwork(solution);
		double[] output = classifier.test(input);
		
		return normalizeOutput(output);
	}
	
	private int normalizeOutput(double[] output) 
	{
		int maxIndex = 0;	
		for( int i = 1; i < output.length; i++ )
			if( output[i] > output[maxIndex] )
				maxIndex = i;
		return maxIndex;
	}
}
