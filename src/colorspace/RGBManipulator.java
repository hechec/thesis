package colorspace;

public abstract class RGBManipulator 
{
	protected static int mixColor(int red, int green, int blue) 
	{
		return red<<16|green<<8|blue;
	}
	
	// Convert R, G, B, Alpha to standard 8 bit
    /*private static int colorToRGB(int alpha, int red, int green, int blue) {
 
        int newPixel = 0;
        newPixel += alpha;
        newPixel = newPixel << 8;
        newPixel += red; newPixel = newPixel << 8;
        newPixel += green; newPixel = newPixel << 8;
        newPixel += blue;
 
        return newPixel;
 
    }*/
	
}
