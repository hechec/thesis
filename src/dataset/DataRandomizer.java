package dataset;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

import util2.FileHelper;

public class DataRandomizer
{
	private File dataFile;
	private float percentage = 0.7f;
	
	ArrayList<String> train_paths = new ArrayList<String>();
	ArrayList<String> test_paths = new ArrayList<String>();
	
	public DataRandomizer(File dataFile)
	{
		this.dataFile = dataFile;
	}
	
	public void randomize()
	{
		getAbsolutePaths(dataFile);
	}

	private void getAbsolutePaths(File dataFile) 
	{
		for (File classFile: dataFile.listFiles()) {
	        if (classFile.isDirectory()) { 
	        	File[] data = classFile.listFiles();
	        	int[] trainIndices = randomizeIndices(data.length);
	        	
	        	for( int i = 0; i < data.length; i++ ) {
	        		if(arrayContains(trainIndices, i)) 
	        			train_paths.add(data[i].getAbsolutePath()); // add file to train data
	        		else 
	        			test_paths.add(data[i].getAbsolutePath()); // add file to test data
	        	}
	        	
	        }
	        else {
				// error
			}
		}
		
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
	
	public static void main(String[] args)
	{
		File file = new File("D:/kamatisan/testing_60");
		
		DataRandomizer randomizer = new DataRandomizer(file);
		randomizer.randomize();
	}
	
}