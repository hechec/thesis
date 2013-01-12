package views;

import static abcnn.NNConstants.DIMENSIONS;

public class Classifier 
{
	private TrainPane trainPane;
	
	private double[] solution;
	
	public Classifier() 
	{
		trainPane = TrainPane.getInstance();
	}
	
	/**
	 * train neural network classifier
	 * 
	 * @param trainingData
	 * @param runtime
	 * @param maxCycle
	 * @param employedBeeSize
	 * @param onlookerBeeSize
	 */
	public void trainNetwork(Data trainingData, int runtime, int maxCycle,
			int employedBeeSize, int onlookerBeeSize) 
	{
		trainPane.initComponents();
		ABC abc = new ABC(trainPane, this, trainingData, runtime, maxCycle, employedBeeSize, onlookerBeeSize, DIMENSIONS); 
		abc.start();
	}
	
	/**
	 * signals end of training process
	 * displays results
	 * 
	 * @param MSE
	 * @param solution
	 * @param elapsedTime
	 */
	public void finishTraining(double MSE, double[] solution, double elapsedTime) 
	{
		this.solution = solution;
		trainPane.displayResult(MSE, elapsedTime);
	}
	
	/**
	 * @return optimal weights
	 */
	public double[] getSolution() 
	{
		return solution;
	}

}
