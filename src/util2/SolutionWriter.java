package util2;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileFilter;

import util.FileTypeFilter;

public class SolutionWriter 
{
	
	public SolutionWriter()
	{
	}
	
	public void saveFile(double[] solution)
	{
		JFileChooser chooser;
		
		FileFilter filter = new FileTypeFilter(".txt", "Text files");
		chooser = new JFileChooser();
		chooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
		chooser.setFileFilter(filter);
		chooser.setAcceptAllFileFilterUsed(false);
		
		BufferedWriter bufferedWriter = null;
		if(chooser.showSaveDialog(null) == JFileChooser.APPROVE_OPTION) {
		    File file = chooser.getSelectedFile();
		    FileWriter fileWriter;
			try {
				fileWriter = new FileWriter(file);
				bufferedWriter = new BufferedWriter(fileWriter);
				for( int i = 0; i < solution.length; i++ ) {
					bufferedWriter.write(solution[i]+"");
					bufferedWriter.newLine();
				}
			} catch (FileNotFoundException ex) {
	            ex.printStackTrace();
	        } catch (IOException e) {
				e.printStackTrace();
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
	
}
