package utilities;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import views.optionpane.MessageDialog;

public class SolutionReader 
{
	
	public static double[] read(File file) 
	{
		double[] solution = new double[GlobalVariables.DIMENSIONS];
		
		BufferedReader bufferedReader = null;
		try {
			bufferedReader = new BufferedReader(new FileReader(file));
			String str;	
			int ctr = 0;
			while( (str = bufferedReader.readLine()) != null ) {
				solution[ctr++] = Double.parseDouble(str);
			}
		} catch (FileNotFoundException e) {
			new MessageDialog("No file found.").setVisible(true);
			return null;
		}catch (NumberFormatException e) {
			new MessageDialog("Cannot convert to network weights.").setVisible(true);
			return null;
		} catch (ArrayIndexOutOfBoundsException e) {
			new MessageDialog("Please check the dimension of the solution.").setVisible(true);
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
               return null;
            	// ex.printStackTrace();
            }
		}
		return solution;
	}
	
}
