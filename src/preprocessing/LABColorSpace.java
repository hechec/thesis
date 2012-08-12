package preprocessing;

import java.awt.image.BufferedImage;

public class LABColorSpace {
	
	public static double[][] M   = {{0.4124, 0.3576,  0.1805},
            {0.2126, 0.7152,  0.0722},
            {0.0193, 0.1192,  0.9505}};
	
	public static double[] D50 = {96.4212, 100.0, 82.5188};
    public static double[] D55 = {95.6797, 100.0, 92.1481};
    public static double[] D65 = {95.0429, 100.0, 108.8900};
    public static double[] D75 = {94.9722, 100.0, 122.6394};
    
    private static double[] whitePoint = D65;
    

	public static BufferedImage convert(BufferedImage original) {
		BufferedImage converted = new BufferedImage(original.getWidth(), original.getHeight(), BufferedImage.TYPE_INT_RGB);
		int red = 0, green = 0, blue = 0, rgb = 0;
		int[] lab = new int[3];
		double[] lab2 = new double[3];
		
		for( int i = 0; i < original.getHeight(); i++ ) 
			for( int j = 0; j < original.getWidth(); j++ ){
				rgb = original.getRGB(j, i);
				red =  (rgb >> 16) & 0xFF;
				green = (rgb >> 8) & 0xFF;
				blue = rgb & 0xFF;
				lab = rgb2lab(red, green, blue);
				converted.setRGB(j, i, lab[0]);
				//lab2 = RGBtoLAB(red, green, blue);
				//converted.setRGB(j, i, (int)lab2[0]);
			}
		return converted;
	}
	
	public static BufferedImage convertToLab(BufferedImage original) {
		BufferedImage converted = new BufferedImage(original.getWidth(), original.getHeight(), BufferedImage.TYPE_INT_RGB);
		int red = 0, green = 0, blue = 0, rgb = 0;
		int[] lab = new int[3];
		
		int pixelValue;
		int bgValue = mixColor(0, 0, 0);
		
		for( int i = 0; i < original.getHeight(); i++ ) 
			for( int j = 0; j < original.getWidth(); j++ ){
				rgb = original.getRGB(j, i);
				pixelValue = rgb & 0x00ffffff;
				System.out.println(pixelValue);
				if( pixelValue == 0 )
					converted.setRGB(j, i, mixColor(0, 0, 0));
				else{
					red =  (rgb >> 16) & 0xFF;
					green = (rgb >> 8) & 0xFF;
					blue = rgb & 0xFF;
					lab = rgb2lab(red, green, blue);
					converted.setRGB(j, i, lab[0]);
				}
				//lab2 = RGBtoLAB(red, green, blue);
				//converted.setRGB(j, i, (int)lab2[0]);
			}
		return converted;
	}
    
    /**
     * 
     * @param R
     * @param G
     * @param B
     * @return
     */
	private static int[] rgb2lab(int R, int G, int B) {
		//http://www.brucelindbloom.com
		  
		float r, g, b, X, Y, Z, fx, fy, fz, xr, yr, zr;
		float Ls, as, bs;
		float eps = 216.f/24389.f;
		float k = 24389.f/27.f;
		   
		float Xr = 0.964221f;  // reference white D50
		float Yr = 1.0f;
		float Zr = 0.825211f;
		
		// RGB to XYZ
		r = R/255.f; //R 0..1
		g = G/255.f; //G 0..1
		b = B/255.f; //B 0..1
		
		// assuming sRGB (D65)
		if (r <= 0.04045)
			r = r/12;
		else
			r = (float) Math.pow((r+0.055)/1.055,2.4);
		
		if (g <= 0.04045)
			g = g/12;
		else
			g = (float) Math.pow((g+0.055)/1.055,2.4);
		
		if (b <= 0.04045)
			b = b/12;
		else
			b = (float) Math.pow((b+0.055)/1.055,2.4);
		
		
		X =  0.436052025f*r     + 0.385081593f*g + 0.143087414f *b;
		Y =  0.222491598f*r     + 0.71688606f *g + 0.060621486f *b;
		Z =  0.013929122f*r     + 0.097097002f*g + 0.71418547f  *b;
		
		// XYZ to Lab
		xr = X/Xr;
		yr = Y/Yr;
		zr = Z/Zr;
				
		if ( xr > eps )
			fx =  (float) Math.pow(xr, 1/3.);
		else
			fx = (float) ((k * xr + 16.) / 116.);
		 
		if ( yr > eps )
			fy =  (float) Math.pow(yr, 1/3.);
		else
		fy = (float) ((k * yr + 16.) / 116.);
		
		if ( zr > eps )
			fz =  (float) Math.pow(zr, 1/3.);
		else
			fz = (float) ((k * zr + 16.) / 116);
		
		Ls = ( 116 * fy ) - 16;
		as = 500*(fx-fy);
		bs = 200*(fy-fz);
		
		int[] lab = new int[3];
		
		lab[0] = (int) (2.55*Ls + .5);
		lab[1] = (int) (as + .5); 
		lab[2] = (int) (bs + .5);
		return lab;
	}
	
	 public static double[] RGBtoLAB(int R, int G, int B) {
	      return XYZtoLAB(RGBtoXYZ(R, G, B));
	 }
	 
	 public static double[] RGBtoXYZ(int R, int G, int B) {
	      double[] result = new double[3];

	      // convert 0..255 into 0..1
	      double r = R / 255.0;
	      double g = G / 255.0;
	      double b = B / 255.0;

	      // assume sRGB
	      if (r <= 0.04045) {
	        r = r / 12.92;
	      }
	      else {
	        r = Math.pow(((r + 0.055) / 1.055), 2.4);
	      }
	      if (g <= 0.04045) {
	        g = g / 12.92;
	      }
	      else {
	        g = Math.pow(((g + 0.055) / 1.055), 2.4);
	      }
	      if (b <= 0.04045) {
	        b = b / 12.92;
	      }
	      else {
	        b = Math.pow(((b + 0.055) / 1.055), 2.4);
	      }

	      r *= 100.0;
	      g *= 100.0;
	      b *= 100.0;

	      // [X Y Z] = [r g b][M]
	      result[0] = (r * M[0][0]) + (g * M[0][1]) + (b * M[0][2]);
	      result[1] = (r * M[1][0]) + (g * M[1][1]) + (b * M[1][2]);
	      result[2] = (r * M[2][0]) + (g * M[2][1]) + (b * M[2][2]);

	      return result;
	 }
	 
	 public static double[] XYZtoLAB(double[] XYZ) {
	      return XYZtoLAB(XYZ[0], XYZ[1], XYZ[2]);
	 }
	 
	 public static double[] XYZtoLAB(double X, double Y, double Z) {

	      double x = X / whitePoint[0];
	      double y = Y / whitePoint[1];
	      double z = Z / whitePoint[2];

	      if (x > 0.008856) {
	        x = Math.pow(x, 1.0 / 3.0);
	      }
	      else {
	        x = (7.787 * x) + (16.0 / 116.0);
	      }
	      if (y > 0.008856) {
	        y = Math.pow(y, 1.0 / 3.0);
	      }
	      else {
	        y = (7.787 * y) + (16.0 / 116.0);
	      }
	      if (z > 0.008856) {
	        z = Math.pow(z, 1.0 / 3.0);
	      }
	      else {
	        z = (7.787 * z) + (16.0 / 116.0);
	      }

	      double[] result = new double[3];

	      result[0] = (116.0 * y) - 16.0;
	      result[1] = 500.0 * (x - y);
	      result[2] = 200.0 * (y - z);

	      return result;
	      
	   }

	public static int getMeanA(BufferedImage image) {
		int sum = 0, ctr = 0, pixelValue;
		for( int i = 0; i < image.getHeight(); i++ ) 
			for( int j = 0; j < image.getWidth(); j++ ){
				pixelValue = image.getRGB(j, i) & 0x00ffffff;
				if( pixelValue != 0 ) {
					sum += pixelValue;
					ctr++;
					System.out.println(pixelValue+" "+ctr);
				}
			
			}
		
		return sum/ctr;
	}
	
	private static int mixColor(int red, int green, int blue) 
	{
		return red<<16|green<<8|blue;
	}

}
