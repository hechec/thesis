package algorithm;

import java.util.Random;

import com.sun.org.apache.bcel.internal.generic.NEW;

public class NNTest {
	
	public static void main(String[] args) {
		double c =  1.0 / (1.0 + Math.exp(-(0.80022)));
		System.out.println(c);
		//0.9820137900379085
		//0.9999546021312976
		
		System.out.println( Math.pow(1.2, 2) );
		
		// rand 0-1
		System.out.println( ( (double)Math.random()*32767 / ((double)32767+(double)(1)) ) );
		
		System.out.println( new Random().nextFloat() );
	}
	
}
