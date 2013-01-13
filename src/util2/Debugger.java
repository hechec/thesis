package util2;

public class Debugger {

	private static boolean debug = true;
	
	public static void printError(String errorMessage)
	{
		if(debug)
			System.out.println( errorMessage );
	}
	
}
