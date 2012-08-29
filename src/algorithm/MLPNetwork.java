package algorithm;

import java.util.ArrayList;

public class MLPNetwork {
	
	private ArrayList<MLPLayer> layers;
	
	public MLPNetwork() {
		layers = new ArrayList<MLPLayer>();
	}
	
	public void addMLPLayer(MLPLayer layer) {
		layers.add(layer);
	}
	
	public void display() {
		for( MLPLayer layer : layers ) 
			layer.display(layers.indexOf(layer)+1);
	}
	
	/**
	 *  main method
	 * @param args
	 */
	public static void main(String[] args) {
		
		MLPNetwork network = new MLPNetwork();
		network.addMLPLayer(new MLPLayer(2, 0));
		network.addMLPLayer(new MLPLayer(2, 2));
		network.addMLPLayer(new MLPLayer(1, 2));
		
		network.display();
		
	}

}
