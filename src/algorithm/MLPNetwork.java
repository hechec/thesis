package algorithm;

import java.util.ArrayList;

import com.sun.corba.se.spi.orbutil.fsm.Input;

public class MLPNetwork {
	
	private ArrayList<MLPLayer> layers;
	private MLPLayer outputLayer;
	private MLPLayer inputLayer;
	private int NUMBER_OF_LAYERS = 3;
	private int[] NODES_PER_LAYER = {2, 2, 1};
	
	private double[][] input_data = { {0, 0}, {0, 1}, {1, 0}, {1, 1} };
	private double[][] output_data = { {0}, {1}, {1}, {0} };
	
	/**
	 * MLPNetwork constructor
	 */
	public MLPNetwork() {
		layers = new ArrayList<MLPLayer>();
		createLayers();
	}
	
	public MLPNetwork(double[] weights) {
		layers = new ArrayList<MLPLayer>();
		createLayers();
		initWeights(weights);
	}
	
	private void createLayers() {
		for( int i = 0; i < NUMBER_OF_LAYERS; i++ ) 
			layers.add( i == 0 ? new MLPLayer(NODES_PER_LAYER[i]) : new MLPLayer( NODES_PER_LAYER[i], layers.get(i-1) ));
		layers.get(0).addBias();
		layers.get(1).addBias();
		inputLayer = layers.get(0);
		outputLayer = layers.get(NUMBER_OF_LAYERS-1);
	}
	
	/**
	 * init/set network weights
	 * @param foods
	 */
	public void initWeights(double[] foods) {
		int index = 0;
		for( int i = 1; i < layers.size(); i++ )
			index = layers.get(i).setLayerWeights(index, foods);
		//display();
	}
	
	/**
	 * computer network error (MSE)
	 * @return
	 */
	public double computeError() {
		double error = 0;
		int data_size = input_data.length;
		
		for( int i = 0; i < data_size; i++ ) {
			feedforward(input_data[i]);
			error += feedforwardError(input_data[i], output_data[i], i);
		}
		//System.out.println("network error: "+error/data_size);
		return error/data_size;
	}
	
	/**
	 * calculate error for each training data
	 * activate output
	 * MSE
	 * 
	 * @param input_data
	 * @param output_data
	 * @param index
	 * @return
	 */
	private void feedforward(double[] input_data) {
		inputLayer.setInputs(input_data); 			// plug inputs
		for( int i = 1; i < NUMBER_OF_LAYERS-1; i++ ) 		// activate hidden layer/s
			layers.get(i).activateHidden(layers.get(i-1));
		outputLayer.activateOutput(layers.get(NUMBER_OF_LAYERS-2));  // activate output
		//outputLayer.displayValue(1);
	}
	
	private double feedforwardError(double[] input_data, double[] output_data, int index) {
		ArrayList<MLPNeuron> output = outputLayer.getNodes();
		double error = 0;
		int output_size = output.size();
		for( int j = 0; j < output_size; j++ ) {
			error +=  Math.pow(output_data[j] - output.get(j).getValue(), 2) ; // (target-actual)^2
			//System.out.println( output_data[j] +" "+ output.get(j).getValue() +" "+error );
		}
			//System.out.println( input_data[0] +" - "+input_data[1] +" ==> "+output_data[0] );
		//outputLayer.displayValue(3);
		//System.out.println("error for data "+(index+1)+": "+ error/output_size );
		return error/output_size;
	}

	public void initTrainingData() {
		// sample data
		int number_of_data = 4;
		int number_of_input = NODES_PER_LAYER[0];
		int number_of_output = NODES_PER_LAYER[NODES_PER_LAYER.length-1];
		//input_data = new double[number_of_data][number_of_input];
		//input_data[0][0] = 1;
		//input_data[0][1] = 0;
		output_data = new double[number_of_data][number_of_output];
	}
	
	/**
	 *  main method
	 * @param args
	 */
	public static void main(String[] args) {
		MLPNetwork network = new MLPNetwork();
		double[] foods = {0.431, -0.921, 0.5332, 0.91232, -0.1121, 0.63412, 0.82932, -0.99323, -0.5645, .9, .9, .8};
		network.initWeights(foods);
		System.out.println( network.computeError() );
		//network.feedforward();
		//network.display();
		//network.displayVaue();
	}
	
	// xor test
	public double[] test(double[] input) {
		feedforward(input);
		double[] output = new double[outputLayer.size()];
		ArrayList<MLPNeuron> outs = outputLayer.getNodes();
		for( int i = 0; i < outs.size(); i++ )
			output[i] = outs.get(i).getValue();
		return output;
	}
	
	private void displayVaue() {
		for( MLPLayer layer : layers ) 
			layer.displayValue( layers.indexOf(layer)+1);		
	}

	public void addMLPLayer(MLPLayer layer) {
		layers.add(layer);
	}
	
	public void display() {
		for( MLPLayer layer : layers ) 
			layer.display( layers.indexOf(layer)+1);
	}

	
}
