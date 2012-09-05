package abcnn;

import java.util.ArrayList;
import java.util.Random;

public class MLPNeuron {
	
	private MLPLayer prev_layer;
	private double[] weights;
	private double value;
	private int number_of_weights;
	
	/**
	 * MLPNeuron constructor for hidden/output neuron
	 * @param prev_layer
	 * @param bias
	 */
	public MLPNeuron(MLPLayer prev_layer) {
		this.prev_layer = prev_layer;
	}
	
	/**
	 * constructor for input neuron
	 */
	public MLPNeuron() {
	}

	/**
	 * set all weights going to this node
	 * @param index
	 * @param foods
	 * @return
	 */
	public int setWeights(int index, double[] foods) {
		if( number_of_weights == 0 ) {
			weights = new double[prev_layer.size()];
			number_of_weights = prev_layer.size();
		}
		
		for( int i = 0; i < number_of_weights; i++, index++ )
			weights[i] = foods[index];
		// return current index
		//initWeights();
		return index;
	}
	
	/**
	 * sets the value of this node
	 * @param value
	 */
	public void setValue(double val) {
		value = val;		
	}
	/**
	 * get the output value of this node
	 * @return node output value
	 */
	public double getValue() {
		return value;
	}
	
	// sigmoid function
	private double sigmoid(double x) {
		return 1.0 / (1.0 + Math.exp(-x));
	}
	
	/**
	 *  activate hidden neuron
	 * @param inputs
	 * @param bias 
	 */
	public void activateHidden(ArrayList<MLPNeuron> inputs, boolean hasBias) {
		double sum = getWeightedSum(inputs, hasBias);
		value = sigmoid(sum);
	}
	
	/**
	 * 
	 * @param inputs
	 * @param hasBias
	 */
	public void activateOutput(ArrayList<MLPNeuron> inputs, boolean hasBias) {
		//double sum = getWeightedSum(inputs, hasBias);
		//value = sigmoid(sum);
		value = getWeightedSum(inputs, hasBias);
	}
	
	/**
	 * calculate weighted sum
	 */
	private double getWeightedSum(ArrayList<MLPNeuron> inputs, boolean hasBias) {
		double sum = 0;		
		int index = 0, len = inputs.size();
		if(hasBias)
			sum += 1 * weights[index++];
		for( int i = 0; i < len; i++, index++ ) {
			sum += inputs.get(i).getValue() * weights[index];
			//System.out.println( inputs.get(i).getValue() +" :: "+ weights[index] );
		}
		return sum;
	}

	// sample 
	public void initWeights() {
		Random random = new Random();
		int len = weights.length;
		for( int i = 0; i < len; i++ ) {
			weights[i] = random.nextFloat() - 0.5f;
		}
	}
	//print
	public void display() {
		System.out.println("number of weights: "+number_of_weights);
		for( int i = 0; i < number_of_weights; i++ )
			System.out.print( "["+weights[i] +"] ");
		//System.out.println(value);
		System.out.println();
	}

	public void displayValue() {
		System.out.println(value);		
	}
	
}
