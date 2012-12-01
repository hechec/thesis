package imageProcessing;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

import colorspace.RGBChannel;

public class BilinearInterpolation {
	
	public static BufferedImage resize(BufferedImage image, int w, int h)
	{
		BufferedImage resized = null;
		
		int[] pixels = toArray(image);
		//int[] newPixels = resizeNearestNeighbor(pixels, image.getWidth(), image.getHeight(), w, h);
		int[] newPixels = resizeBilinear(pixels, image.getWidth(), image.getHeight(), w, h);
		
		resized = toImage(newPixels, w, h);
		
		return resized;
	}
	
	private static int[] resizeBilinear(int[] pixels, int w, int h, int w2, int h2) {
	    int[] temp = new int[w2*h2] ;
	    int a, b, c, d, x, y, index ;
	    float x_ratio = ((float)(w-1))/w2 ;
	    float y_ratio = ((float)(h-1))/h2 ;
	    float x_diff, y_diff, blue, red, green ;
	    int offset = 0 ;
	    for (int i=0;i<h2;i++) {
	        for (int j=0;j<w2;j++) {
	            x = (int)(x_ratio * j) ;
	            y = (int)(y_ratio * i) ;
	            x_diff = (x_ratio * j) - x ;
	            y_diff = (y_ratio * i) - y ;
	            index = (y*w+x) ;                
	            a = pixels[index] ;
	            b = pixels[index+1] ;
	            c = pixels[index+w] ;
	            d = pixels[index+w+1] ;
	            
	            //System.out.println( i + " "+j+" :"+x+" "+y+" "+index);

	            // blue element
	            // Yb = Ab(1-w)(1-h) + Bb(w)(1-h) + Cb(h)(1-w) + Db(wh)
	            blue = (a&0xff)*(1-x_diff)*(1-y_diff) + (b&0xff)*(x_diff)*(1-y_diff) +
	                   (c&0xff)*(y_diff)*(1-x_diff)   + (d&0xff)*(x_diff*y_diff);

	            // green element
	            // Yg = Ag(1-w)(1-h) + Bg(w)(1-h) + Cg(h)(1-w) + Dg(wh)
	            green = ((a>>8)&0xff)*(1-x_diff)*(1-y_diff) + ((b>>8)&0xff)*(x_diff)*(1-y_diff) +
	                    ((c>>8)&0xff)*(y_diff)*(1-x_diff)   + ((d>>8)&0xff)*(x_diff*y_diff);

	            // red element
	            // Yr = Ar(1-w)(1-h) + Br(w)(1-h) + Cr(h)(1-w) + Dr(wh)
	            red = ((a>>16)&0xff)*(1-x_diff)*(1-y_diff) + ((b>>16)&0xff)*(x_diff)*(1-y_diff) +
	                  ((c>>16)&0xff)*(y_diff)*(1-x_diff)   + ((d>>16)&0xff)*(x_diff*y_diff);

	            temp[offset++] = 
	                    0xff000000 | // hardcode alpha
	                    ((((int)red)<<16)&0xff0000) |
	                    ((((int)green)<<8)&0xff00) |
	                    ((int)blue) ;
	        }
	    }
	    return temp ;
	}
	
	private static int[] resizeNearestNeighbor(int[] pixels,int w1,int h1,int w2,int h2) {
	    int[] temp = new int[w2*h2] ;
	    // EDIT: added +1 to account for an early rounding problem
	    int x_ratio = (int)((w1<<16)/w2) +1;
	    int y_ratio = (int)((h1<<16)/h2) +1;
	    //int x_ratio = (int)((w1<<16)/w2) ;
	    //int y_ratio = (int)((h1<<16)/h2) ;
	    int x2, y2 ;
	    for (int i=0;i<h2;i++) {
	        for (int j=0;j<w2;j++) {
	            x2 = ((j*x_ratio)>>16) ;
	            y2 = ((i*y_ratio)>>16) ;
	            temp[(i*w2)+j] = pixels[(y2*w1)+x2] ;
	        }                
	    }                
	    return temp ;
	}
	
	public static int[] toArray(BufferedImage image)
	{
		int[] pixels = new int[image.getWidth()*image.getHeight()];
		int ctr = 0;
		for( int i = 0; i < image.getHeight(); i++  )
			for( int j = 0; j < image.getWidth(); j++ ){
				//System.out.println( i+" "+j );
				pixels[ctr++] = image.getRGB(j, i);
			}
		return pixels;
	}
	
	public static BufferedImage toImage(int[] pixels, int w, int h)
	{
		BufferedImage image = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);
		
		for( int ctr = 0, j = 0, i = 0 ; ctr < pixels.length;j++, ctr++ ) {
			if( ctr > 0 && (ctr) % w == 0 ) { // w or h ?
 				j = 0;
				i++;
			}		
			//System.out.println(ctr+" "+i+" "+j);
			image.setRGB(j, i, pixels[ctr]);
		}
			
		
		return image;
	}
	
	public static void main(String[] args) {
		BufferedImage image = null;
		File file = new File("C:/Users/hechec/Desktop/saved.jpeg");
		/*try {
			image  = ImageIO.read(file);
		} catch (IOException e) {
			e.printStackTrace();
		}*/
		
		image = new BufferedImage(5, 5, BufferedImage.TYPE_INT_RGB);
		
		for( int y = 0; y < image.getHeight(); y++ )
			for( int x = 0; x < image.getWidth(); x++ ){
				int rgb = (100+x+y)<<16|(100+x+y)<<8|(100+x+y);
				image.setRGB(x, y, rgb);	
			}
				
		
		
		//System.out.println( image.getWidth() +" "+image.getHeight() );
		
		int[] pixels = toArray(image);
		
		int[] newPixels = resizeBilinear(pixels, image.getWidth(), image.getHeight(), 2, 2);
		
		//System.out.println( newPixels.length );
		
		//System.out.println( newPixels[39999] );
		
		BufferedImage resized = toImage(newPixels, 2, 2);
		
		JFrame frame = new JFrame();
		frame.getContentPane().setLayout(new FlowLayout());
		frame.getContentPane().add(new JLabel(new ImageIcon(image)));
		frame.getContentPane().add(new JLabel(new ImageIcon(resized)));
		frame.pack();
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

}
