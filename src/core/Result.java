package core;

public class Result 
{
	private int[] expected;
	private int[] actual;
	
	public Result(int[] expected, int[] actual)
	{
		this.expected = expected;
		this.actual = actual;
	}
	
	public int size()
	{
		return expected.length;
	}
	
	public float getAccuracy()
	{
		return 100*(float)getScore()/(float)size();
	}
	
	public int getExpected(int index)
	{
		return expected[index];
	}
	
	public int getActual(int index)
	{
		return actual[index];
	}

	public int getScore() 
	{
		int score = 0;
		for( int i = 0; i < size(); i++ ) {
			if(  expected[i] == actual[i] )
				score++;
			else {
				addError(expected[i]);
			}
		}
			//score += expected[i] == actual[i] ? 1 : 0;
		return score;
	}
	
	private void addError(int expected)
	{
		if(expected == 0)
			green++;
		else if(expected == 1)
			breakers++;
		else if(expected == 2)
			turning++;
		else if(expected == 3)
			pink++;
		else if(expected == 4)
			lightred++;
		else if(expected == 5)
			red++;
	}
	
	public void printErrors()
	{
		System.out.println(green+"\t"+breakers+"\t"+turning+"\t"+pink+"\t"+lightred+"\t"+red);
	}
	
	private int green = 0, breakers = 0, turning = 0, pink = 0, lightred = 0, red = 0;

}
