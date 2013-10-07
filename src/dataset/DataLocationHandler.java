package dataset;

import java.io.File;

public class DataLocationHandler 
{
	private static String pathname = "";
	
	public DataLocationHandler(){}
	
	public static void setBaseFolder(String pathname2)
	{
		//System.out.println("set "+pathname2);
		pathname = pathname2;
	}
	
	public static File getBaseFolder()
	{
		//System.out.println("get "+pathname);
		return new File(pathname);
	}
}
