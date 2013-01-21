package util2;

import java.io.File;

public class FileHelper 
{
	public static int countFiles(File folder) 
	{
		int count = 0;
		for (final File fileEntry : folder.listFiles()) {
	        if (fileEntry.isDirectory())  
	        	count += countFiles(fileEntry);
	        else 
	        	count++;
		}
		return count;
	}

}
