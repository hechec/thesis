package utilities;

public class GlobalVariables {
	
	public static int DIMENSIONS = 0;
	
	public static int NUMBER_OF_INPUT = 0;
	
	public static int NODES_PER_HIDDEN = 0;
	
	public static int MODE;
	
	public static final int NUMBER_OF_OUTPUT = 6;
	
	public static final int NUMBER_OF_HIDDEN_LAYER = 1;
	
	public static final int EXPERIMENTATION_3 = -1;
	
	public static final int STANDARD_RUN = 1;
	
	public static final int STANDARD_INPUT_NODES = 5;
	
	public static final int STANDARD_HIDDEN_NODES = 5;
	
	public static void setStructure(int numberOfInput, int numberOfHidden) 
	{
		NUMBER_OF_INPUT = numberOfInput;
		NODES_PER_HIDDEN = numberOfHidden;
			
		// +1 because of bias node
		DIMENSIONS = (numberOfInput+1)*numberOfHidden + (numberOfHidden+1)*NUMBER_OF_OUTPUT;
		
		//System.out.println( NUMBER_OF_INPUT+" "+NODES_PER_HIDDEN+" "+DIMENSIONS );
	}
	
	public static void setMode(int mode)
	{
		MODE = mode;
		if(mode == STANDARD_RUN) 
			setStructure(STANDARD_INPUT_NODES, STANDARD_HIDDEN_NODES);
			
	}
	
}
