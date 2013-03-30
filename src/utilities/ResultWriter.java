package utilities;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;

import core.Result;
import dataset.Data;

public class ResultWriter 
{
	public ResultWriter() 
	{}

	public void writeResult(File file, Data testData, Result result) 
	{
		BufferedWriter bufferedWriter = null;
		FileWriter fileWriter;
		try {
			fileWriter = new FileWriter(file);
			bufferedWriter = new BufferedWriter(fileWriter);
			for( int i = 0; i < result.size(); i++ ) {
				bufferedWriter.write(testData.getFilename(i) + "\t" + result.getExpected(i) + "\t" + result.getActual(i) );
				bufferedWriter.newLine();
			}
		} catch (FileNotFoundException ex) {
            //ex.printStackTrace();
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
