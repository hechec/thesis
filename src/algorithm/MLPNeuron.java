package algorithm;

import java.util.ArrayList;
import java.util.Random;

public class MLPNeuron {
	
	private float[] weights;
	private float value;
	
	public MLPNeuron(int n_prev_nodes, boolean bias) {
		weights = new float[n_prev_nodes];
		initWeights();
	}
	
	// sample 
	public void initWeights() {
		Random random = new Random();
		int len = weights.length;
		for( int i = 0; i < len; i++ ) {
			weights[i] = random.nextFloat() - 0.5f;
		}
	}
	
	public float activate(ArrayList<MLPNeuron> nodes) {
		float sum = 0f;
		int len = nodes.size();
		for( int i = 0; i < len; i++ )
			sum += nodes.get(i).getValue() + weights[i];
		return sigmoid(sum);
	}

	private float sigmoid(float x) {
		 return 1.0f / (1.0f + (float) Math.exp(-x));
	}
	
	private float getValue() {
		return value;
	}
	
	public void display() {
		int len = weights.length;
		for( int i = 0; i < len; i++ )
			System.out.print( "["+weights[i] +"] ");
		System.out.println();
	}

}
