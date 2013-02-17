package utilities;

import java.io.File;

public class Renamer 
{
	private int count = 1;
	
	public boolean rename(File folder)
	{
		boolean success = true;
		System.out.println( folder.getAbsolutePath() ); 
		for (File file: folder.listFiles()) {
	        if (file.isDirectory())   {
	        	success = rename(file);
	        	if(!success)
	        		return false;
	        }
	        else {
				//System.out.println( (count++) + " "+file.getName() );
			    File file2 = new File(file.getParentFile().getAbsoluteFile()+"/tomato_"+(count++)+".JPG");

			    // Rename file (or directory)
			    success = file.renameTo(file2);
			    if(!success)
			    	return false;
	        }
		}
		return true;
	}
	
	/*
	public static void main(String[] args) {
		Renamer renamer = new Renamer();
		File file = new File("D:/kamatisan/RESIZED/");
		System.out.println(renamer.rename(file));
	}
	*/
}
