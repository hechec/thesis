package dataset;

import java.io.File;
import java.util.ArrayList;
import java.util.Random;

/** 
 * Randomizes the  dataset; 70% for training and 30% for testing
 * @author hechec
 *
 */

public class DataRandomizer
{
	private File dataFile;
	private float percentage = 0.7f;
	
	ArrayList<String> train_set = new ArrayList<String>();
	ArrayList<String> test_set = new ArrayList<String>();
	
	public DataRandomizer(File dataFile)
	{
		this.dataFile = dataFile;
	}
	
	public ArrayList<String> getTrainSet()
	{
		return train_set;
	}
	
	public ArrayList<String> getTestSet()
	{
		return test_set;
	}
	
	public boolean randomize()
	{
		boolean success = getAbsolutePaths(dataFile);
		return success;
	}

	private boolean getAbsolutePaths(File dataFile) 
	{
		for (File classFile: dataFile.listFiles()) {
	        if (classFile.isDirectory()) { 
	        	File[] data = classFile.listFiles();
	        	int[] trainIndices = randomizeIndices(data.length);
	        	
	        	for( int i = 0; i < data.length; i++ ) {
	        		if(arrayContains(trainIndices, i)) 
	        			train_set.add(data[i].getParentFile().getName()+File.separator+data[i].getName()); // add file to train data
	        		else 
	        			test_set.add(data[i].getParentFile().getName()+File.separator+data[i].getName()); // add file to test data
	        	}
	        	
	        }
	        else 
				return false;
		}
		return true;
	}
	
	private boolean arrayContains(int[] array, int number)
	{
		for( int i = 0; i < array.length; i++ )
			if( array[i] == number )
				return true;
		return false;
	}
	
	private int[] randomizeIndices(int total) 
	{
		int count = (int)(total*percentage);
		int[] indices = new int[count];
		Random rand = new Random();
		int index = 0;
	
		for( int ctr = 0; ctr < count; ctr++ ) {
			do {
				index = rand.nextInt(total);
			} while( hasBeenTaken(indices, index, ctr) );
			indices[ctr] = index;
		}
		
		return indices;
	}
	
	private boolean hasBeenTaken(int[] indices, int index, int ctr) 
	{
		for( int i = 0; i < ctr; i++ )
			if( indices[i] == index )
				return true;
		return false;
	
	}
	
}