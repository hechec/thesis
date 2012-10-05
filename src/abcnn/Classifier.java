package abcnn;

import java.awt.Color;
import java.io.File;

import javax.swing.JOptionPane;

import dialogs.LoadingDialog;

import ui.ABCNNPane;
import ui.AppFrame;
import utilities.DataLoader;
import utilities.ImageLoader;
import utilities.NetworkConfiguration;

public class Classifier {

	private AppFrame appFrame;	
	private ABCNNPane abcnnPane;
	
	private LoadingDialog dialog;
	private DataLoader dataLoader;	
	
	private ABC abc;
	
	private double[] weights;

	private double[][] training_data;
	private double[][] output_data;
	
	private boolean isPrepared;	
	private boolean isTrained;
	private boolean isNew;
	
	public Classifier(AppFrame appFrame, ABCNNPane abcnnPane) {
		this.appFrame = appFrame;
		this.abcnnPane = abcnnPane;
	}
	
	public void train(int runtime, int maxCycle, int populationSize) {
		if(!isPrepared) 
			JOptionPane.showMessageDialog(appFrame, "No Training Data Loaded.", "Error", JOptionPane.WARNING_MESSAGE);
		else{
			abcnnPane.initComponents();
			training_data = dataLoader.getTrainingInput();
			output_data = dataLoader.getTrainingOutput();
			abc = new ABC( abcnnPane, this, runtime, maxCycle, populationSize, NetworkConfiguration.DIMENSIONS); //126);
			abc.setTrainingData(training_data, output_data);
			abc.start();
		}
	}
	
	public int classify(double[] input) {
		
		MLPNetwork classifier = new MLPNetwork(weights);
		double[] output = classifier.test(input);
		
		int classIndex = normalizeOutput(output);
		
		return classIndex;
	}
	
	private int normalizeOutput(double[] output) {
		int maxIndex = 0;	
		for( int i = 1; i < output.length; i++ )
			if( output[i] > output[maxIndex] )
				maxIndex = i;
		return maxIndex;
	}
	
	public void finishTraining(double MSE, double[] weights, double elapsedTime) {
		this.weights = weights;
		isTrained = true;
		isNew = true;
		abcnnPane.displayTrainingResult(MSE, elapsedTime);
	}

	public void loadWeights(double[] weights) {
		this.weights = weights;
		isTrained = true;
	}
	
	public double[] getWeights() {
		return weights;
	}
	
	public boolean isTrained() {
		return isTrained;
	}

	public void loadImages() {
		String filepath = abcnnPane.getFilePath();
		File file = new File(filepath);
		if( filepath.trim().equals("") ) 
			JOptionPane.showMessageDialog(appFrame, "Please select a directory.", "Error", JOptionPane.WARNING_MESSAGE);
		else if( !file.exists()  )
			JOptionPane.showMessageDialog(appFrame, "The directory does not exist.", "Error", JOptionPane.WARNING_MESSAGE);
		else {
			dialog = new LoadingDialog(appFrame);
			//new ImageLoader(this, filepath, dialog);
			dataLoader = new DataLoader(this, dialog);
			dataLoader.load(file);
		}	
	}

	public void setPrepared(double[][] input_data, double[][] output_data) {
		this.isPrepared = true;
		abcnnPane.setStatus(ABCNNPane.STAT4, Color.BLUE);
	}

	public double[][] getTestingInput() {
		return dataLoader.getTestingInput();
	}
	
	public int[] getTestingOutput() {
		return dataLoader.getTestingOutput();
	}
	
	public void reset() {
		abc = null;
		weights = null;
		isPrepared = false;
		isTrained = false;
		isNew = false;
	}

	public boolean isNewTraining() {
		return isNew;
	}

	
}
