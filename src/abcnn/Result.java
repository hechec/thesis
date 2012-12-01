package abcnn;


public class Result {
	
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

	public int getScore() {
		int score = 0;
		for( int i = 0; i < size(); i++ )
			score += expected[i] == actual[i] ? 1 : 0;
		return score;
	}

}
