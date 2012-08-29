package algorithm;

import java.util.ArrayList;

public class MLPLayer {
	
	private ArrayList<MLPNeuron> nodes;
		
	public MLPLayer(int n_nodes, int n_prev_nodes) {
		nodes = new ArrayList<MLPNeuron>();
		createNodes(n_nodes, n_prev_nodes);
	}

	private void createNodes(int n_nodes, int n_prev_nodes) {
		for( int i = 0; i < n_nodes; i++ )
			nodes.add(new MLPNeuron(n_prev_nodes, false));
	}

	public void display(int n) {
		System.out.println( "--- LAYER "+n+" ---" );
		for( MLPNeuron node: nodes )
			node.display();
	}

}
