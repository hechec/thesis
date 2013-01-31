package util2;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class FileHelper 
{
	/**
	 * counts the number of files (absolute paths) which is 
	 * just equal to the number of line in the .data file
	 * @param dataFile
	 * @return 
	 */
	public static int countFiles(File dataFile) 
	{
		BufferedReader reader;
		int count = 0;
		try {
			reader = new BufferedReader(new FileReader(dataFile));
			while (reader.readLine() != null) count++;
				reader.close();
		} catch (FileNotFoundException e) {
			Debugger.printError("File not found: \""+dataFile.getAbsolutePath()+"\"");
		} catch (IOException e) {
			e.printStackTrace();
		}
		return count;
	}
	
	/**
	 * couns the number of files (images) inside a directory
	 * Given the dir struct
	 * 		Main_Dir
	 * 			1
	 * 				tomato1.jpg
	 * 			2
	 * 				tomato2.jpg
	 *			3
	 *			4
	 *			5
	 *			6
	 *				tomato6.jpg
	 * 
	 * @param file
	 * @return
	 */
	public static int countAllFiles(File file)
	{
		int count = 0;
		
		for (File classFile: file.listFiles()) {
	        if (classFile.isDirectory()) { 
	        	count += classFile.list().length;
	        }
		}
		return count;
	}

}
