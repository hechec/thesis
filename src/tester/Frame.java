package tester;

import image_processing.GrayScale;
import image_processing.ImageProcessor;
import image_processing.OtsuThreshold;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.text.Bidi;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

public class Frame extends JFrame {

	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Frame frame = new Frame();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public Frame() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 900, 500);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new FlowLayout());
		
	
		BufferedImage original = null;
		try {
			original = ImageIO.read(new File("C:/Users/hechec/Desktop/try.jpg"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		ImageProcessor iProcessor = ImageProcessor.getInstance();
		
		original = iProcessor.resizeImage(original, 200, 200);
		BufferedImage img = new BufferedImage(200, 200, BufferedImage.TYPE_INT_RGB);
		for( int i = 0; i < original.getHeight(); i++ ) 
			for( int j = 0; j < original.getWidth(); j++ ) {
				img.setRGB(i, j, original.getRGB(i, j));
			}		
		
		int x = 100, y = 100;
		
		
		img = iProcessor.resizeImage(img, 200, 200);
		contentPane.add( new JLabel(new ImageIcon(img)) );
		
		img = iProcessor.process(img);
		contentPane.add( new JLabel(new ImageIcon(img)) );
		
		int[] hist = OtsuThreshold.hist;
		
		double w0 = 0, w1 = 0, u0 = 0, u1 = 0;
		
		for(int i = 0; i <= 36; i++) {
			w0 += hist[i];
			u0 += (double)i*hist[i];
		}
		
		u0 /= w0;
		
		for(int i = 37; i <= 255; i++) {
			w1 += hist[i];
			u1 += (double)i*hist[i];
		}
		
		u1 /= w1;
		
		System.out.println( u0+" "+u1 );
		
		System.out.println( w0*w1*(u0-u1)*(u0-u1) );
		
		new Histogram(hist, 36);
		
	}
	

}
