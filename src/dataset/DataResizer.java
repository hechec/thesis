package dataset;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import util2.Debugger;
import util2.FileHelper;
import views.ImageProcessor;
import views.ProgressPane;
import views.ResizerPane;

public class DataResizer 
{
	private File sourceFile;
	private File destinationFile;
	private ImageProcessor iProcessor;
	private ProgressPane progressPane;	
	
	public DataResizer(File sourceFile, File destinationFile) 
	{
		this.sourceFile = sourceFile;
		this.destinationFile = destinationFile;
		iProcessor = new ImageProcessor();
		progressPane = ResizerPane.getInstance().getProgressPane();
		int count = FileHelper.countAllFiles(sourceFile);
		progressPane.reset(count);
	}
	
	public void resize()
	{		
		for (File classFile: sourceFile.listFiles()) {
	        if (classFile.isDirectory()) { 	        	
	        	// creates a folder in destination file
	        	File newFile = new File(destinationFile.getAbsoluteFile()+"/"+classFile.getName());		
	        	newFile.mkdir();	
	        	
	        	File[] data = classFile.listFiles();
	        	for( int i = 0; i < data.length; i++ ) {
	        		try {
						BufferedImage image = iProcessor.resizeImage(ImageIO.read(data[i]), 200, 200);
						ImageIO.write(image, "JPG", new File(newFile.getAbsoluteFile() + "/"+data[i].getName()));
						progressPane.incrementBar();
					} catch (IOException e) {
						Debugger.printError("Cannot read image: \""+data[i].getAbsolutePath()+"\"");
					}
	        	}
	        	
	        }
		}
	}
	
	public static void main(String[] args)
	{
		File destFile = new File("C:/Users/hechec/Desktop/Jake");
		File sourceFile = new File("D:/kamatisan/KAMATISAN");
		DataResizer dataResizer = new DataResizer(sourceFile, destFile);
		dataResizer.resize();
		
	}

}
