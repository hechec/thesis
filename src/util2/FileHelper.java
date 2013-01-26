package util2;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class FileHelper 
{
	public static int countFiles(File dataFile) 
	{
		BufferedReader reader;
		int count = 0;
		try {
			reader = new BufferedReader(new FileReader(dataFile));
			while (reader.readLine() != null) count++;
				reader.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return count;
	}

}
