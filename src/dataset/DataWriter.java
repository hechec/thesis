package dataset;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class DataWriter 
{
	private File dataFile;
	
	public DataWriter(String filename) 
	{
		dataFile = new File("D:/kamatisan/Data/"+filename+".data");
	}
	
	public boolean write(ArrayList<String> files) 
	{
		FileWriter fileWriter;
		BufferedWriter bufferedWriter = null;
		try {
			fileWriter = new FileWriter(dataFile);
			bufferedWriter = new BufferedWriter(fileWriter);

			for( String filename: files ) {
				bufferedWriter.write(filename);
				bufferedWriter.newLine();
			}
			
		} catch (FileNotFoundException ex) {
	        ex.printStackTrace();
	        return false;
	    } catch (IOException e) {
			e.printStackTrace();
			return false;
		}finally {
	        try {
	            if (bufferedWriter != null) {
	                bufferedWriter.flush();
	                bufferedWriter.close();
	            }
	        } catch (IOException ex) {
	            ex.printStackTrace();
	            return false;
	        }
	    }
		return true;
	}
	
}
