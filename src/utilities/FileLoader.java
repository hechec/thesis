package utilities;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import javax.swing.JOptionPane;

import ui.AppFrame;

public class FileLoader {

	private AppFrame appFrame;	
	
	private double[] weights = new double[NetworkConfiguration.DIMENSIONS];
	
	public FileLoader(AppFrame appFrame) {
		this.appFrame = appFrame;
	}

	public double[] loadTrainedData(File file) {
		BufferedReader bufferedReader = null;
		try {
			bufferedReader = new BufferedReader(new FileReader(file));
			String str;	
			int ctr = 0;
			while( (str = bufferedReader.readLine()) != null ) {
				weights[ctr++] = Double.parseDouble(str);
			}
		} catch (FileNotFoundException e) {
			JOptionPane.showMessageDialog(appFrame, "No file found.", "Error Message", JOptionPane.WARNING_MESSAGE);
			return null;
		}catch (NumberFormatException e) {
			JOptionPane.showMessageDialog(appFrame, "Cannot convert to network weights.", "Error Message", JOptionPane.WARNING_MESSAGE);
			return null;
		}
		catch (IOException e) {
			e.printStackTrace();
		}finally {
            try {
                if (bufferedReader != null) {
                	bufferedReader.close();
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
		}
		return weights;
	}
	

}
