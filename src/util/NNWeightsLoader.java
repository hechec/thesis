package util;

import java.io.*;
import javax.swing.JOptionPane;

import ui.AppFrame;
import static abcnn.NNConstants.*;

/**
 * class that loads trained data to be used for classification
 * 
 * @author Harvey Jake Opena
 *
 */

public class NNWeightsLoader {

	private AppFrame appFrame;	
	
	private double[] weights = new double[DIMENSIONS];
	
	public NNWeightsLoader(AppFrame appFrame) {
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
		return weights;
	}
	
}
