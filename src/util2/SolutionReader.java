package util2;

import static abcnn.NNConstants.DIMENSIONS;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import javax.swing.JOptionPane;

public class SolutionReader 
{
	
	public static double[] read(File file) 
	{
		double[] solution = new double[DIMENSIONS];
		
		BufferedReader bufferedReader = null;
		try {
			bufferedReader = new BufferedReader(new FileReader(file));
			String str;	
			int ctr = 0;
			while( (str = bufferedReader.readLine()) != null ) {
				solution[ctr++] = Double.parseDouble(str);
			}
		} catch (FileNotFoundException e) {
			JOptionPane.showMessageDialog(null, "No file found.", "Error Message", JOptionPane.WARNING_MESSAGE);
			return null;
		}catch (NumberFormatException e) {
			JOptionPane.showMessageDialog(null, "Cannot convert to network weights.", "Error Message", JOptionPane.WARNING_MESSAGE);
			return null;
		}
		catch (IOException e) {
			return null;
		}finally {
            try {
                if (bufferedReader != null) {
                	bufferedReader.close();
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
		}
		return solution;
	}
	
}
