package utilities;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;

public class SolutionWriter 
{
	
	private File file;
	
	public SolutionWriter(File file)
	{
		this.file = file;
	}
	
	public void saveFile(double[] solution)
	{
		BufferedWriter bufferedWriter = null;
	    FileWriter fileWriter;
		try {
			fileWriter = new FileWriter(file);
			bufferedWriter = new BufferedWriter(fileWriter);
			for( int i = 0; i < solution.length; i++ ) {
				bufferedWriter.write(solution[i]+"");
				bufferedWriter.newLine();
			}
		} catch (FileNotFoundException ex) {
           // ex.printStackTrace();
        } catch (IOException e) {
			//e.printStackTrace();
		}finally {
            try {
                if (bufferedWriter != null) {
                    bufferedWriter.flush();
                    bufferedWriter.close();
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
	}
	
}
