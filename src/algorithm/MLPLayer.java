package algorithm;

import java.util.ArrayList;

public class MLPLayer {
	
	private ArrayList<MLPNeuron> nodes;
	private MLPLayer prev_layer = null;
	private int size;
	private int prev_size;
	private boolean hasBias = false;
	
	/**
	 * MLPLayer constructor hidden/output layers
	 * @param n_nodes
	 * @param mlpLayer.getS*ize()
	 */
	public MLPLayer(int n_nodes, MLPLayer prev_layer) {
		nodes = new ArrayList<MLPNeuron>();
		size = n_nodes;
		prev_size = prev_layer.size();
		this.prev_layer = prev_layer;
		createNodes();
	}
	
	/**
	 * constructor for input layer
	 * @param n_nodes
	 */
	public MLPLayer(int n_nodes) {
		nodes = new ArrayList<MLPNeuron>();
		size = n_nodes;
		createNodes();
	}
	
	/**
	 *  create nodes for this layer
	 */
	private void createNodes() {
		for( int i = 0; i < size; i++ )
			nodes.add( prev_layer == null? new MLPNeuron() : new MLPNeuron(prev_layer));
	}
	
	public void addBias() {
		hasBias = true;
	}
	
	public boolean hasBias() {
		return hasBias;
	}
	
	public int size() {
		return nodes.size() + (hasBias? 1 : 0);
	}
	
	/**
	 * get all nodes in this layer
	 * @return nodes
	 */
	public ArrayList<MLPNeuron> getNodes() {
		return nodes;
	}
	
	/**
	 * set all weights going to this layer
	 * @param index
	 * @param foods
	 * @return
	 */
	public int setLayerWeights(int index, double[] foods ) {
		for( int i = 0; i < size; i++ ) 
			index = nodes.get(i).setWeights(index, foods);
		// return current index;	
		return index;
	}
	
	/**
	 * set inputs for input layer
	 * @param data
	 */
	public void setInputs(double[] data) {
		for( int i = 0; i < size; i++ )
			nodes.get(i).setValue(data[i]);
	}
	
	/**
	 * activate hidden
	 * @param prev_layer
	 */
	public void activateHidden(MLPLayer prev_layer) {
		ArrayList<MLPNeuron> inputs = prev_layer.getNodes();
		for( MLPNeuron node : nodes )
			node.activateHidden(inputs, prev_layer.hasBias());
	}
	
	/**
	 *  activate output layer
	 * @param prev_layer
	 */
	public void activateOutput(MLPLayer prev_layer) {
		ArrayList<MLPNeuron> inputs = prev_layer.getNodes();
		for( MLPNeuron node : nodes )
			node.activateOutput(inputs, prev_layer.hasBias());
	}
	

	// print
	public void display(int n) {
		System.out.println( "--- LAYER "+n+" has "+size+" nodes and bias:"+hasBias );
		for( MLPNeuron node: nodes )
			node.display();
	}

	public void displayValue(int n) {
		//System.out.println( "--- LAYER "+n+" has "+size+" nodes and bias:"+hasBias );
		for( MLPNeuron node: nodes )
			node.displayValue();
		
	}

}
