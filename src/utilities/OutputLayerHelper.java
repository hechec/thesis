package utilities;

public class OutputLayerHelper 
{
	
	/**
	 * normalize expected output array
	 * 
	 * ex
	 * input =  {{1, 0, 0, 0, 0, 0},
	 * 			 {0, 1, 0, 0, 0, 0},
	 * 		     {0, 0, 0, 1, 0, 0},
	 * 			}
	 * output = {0, 1, 3}
	 * 
	 * @param output
	 * @return
	 */
	public static int[] normalize(double[][] output) 
	{
		int[] expected_output = new int[output.length];
		
		for( int i = 0; i < output.length; i++ ) {
			for( int j = 0; j < output[i].length; j++ ) {
				if( output[i][j] == 1.0 ) {
					expected_output[i] = j;
					break;
				}
			}
		}
		return expected_output;
	}
	
}
