package colorspace;

public class HSI 
{
	public static double getHue(int R, int G, int B)  
	{
		double r = getNormalizedRed(R, G, B);
		double g = getNormalizedGreen(R, G, B);
		double b = getNormalizedBlue(R, G, B);
		double hue = 0.0;
		double numerator;
		double denominator;
		
		if ((r==g)&&(r==b))
			hue = 255;
		else {
			numerator = (r-g) + (r-b);
			denominator = 2*Math.sqrt((((r-g)*(r-g))+((r-b)*(g-b))));
			hue = Math.acos(numerator/denominator);
			
			if (hue<=0)
				hue = 2*Math.PI + hue;
			
			if (b>g) 
				hue = 2*Math.PI - hue;
			
			hue = hue * (180/Math.PI);
				
		}
		
		return hue;
	}
	
	private static double getNormalizedRed(int r, int g, int b) 
	{
		return (double)r / (double)(r+g+b);
	}
	
	private static double getNormalizedGreen(int r, int g, int b) 
	{
		return (double)g / (double)(r+g+b);
	}
	
	private static double getNormalizedBlue(int r, int g, int b) 
	{
		return (double)b / (double)(r+g+b);
	}

}
