package utilities;

public class Debugger {

	private static boolean debug = true;
	
	public static void printError(String errorMessage)
	{
		if(debug)
			System.err.println( errorMessage );
	}
	
}
