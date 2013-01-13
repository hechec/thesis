package util2;

import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

public class FileChooser extends JFileChooser 
{
	private static FileChooser instance = null;
	private FileNameExtensionFilter fileFilter = new FileNameExtensionFilter("", "jpg", "jpeg");
	
	public static FileChooser getInstance()
	{
		if(instance == null)
			instance = new FileChooser();
		return instance;
	}
	
	public FileChooser()
	{
		setCurrentDirectory(new File("D:/"));
	}
	
	public void setIsFiltered(boolean b)
	{
		if(b) {
			setFileFilter(fileFilter);
			setAcceptAllFileFilterUsed(false);
		}
		else {
			removeChoosableFileFilter(getFileFilter());
		}
	}
	
}
