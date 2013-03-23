package core;

import java.util.Random;
import views.ExperimentPane;
import views.optionpane.MessageDialog;

import dataset.Data;

public class ABC
{	
	private int runtime 	 	= 0,
				maxCycle 	 	= 0,
				dimension 		 = 0,
				employedBeeSize = 0,
				onlookerBeeSize = 0,
				scoutBeeSize	= 0,
				param2change 	= 0,
				neighbour 	 	= 0,
				bestIndex	 	= 0;
	
	private double GlobalMin 	= 0,
			       lb 		 	= -1.0,
			       ub 		    = 1.0,
			       ObjValSol	= 0,            
				   FitnessSol	= 0,
				   meanRun		= 0,
				   bestMin		= 1000;
	
	private double[] MSE,
					 GlobalParams,
					 GlobalMins,
					 solution,
					 fitness,
					 prob,
					 trial;
	
	private double[][] Foods,
					   Params;	
	
	private MLPNetwork[] networks;
	private Random rand;
		
	private double[][] input_data;
	private double[][] output_data;
	
	private boolean pause = false, terminate = false; 
	
	public ABC(int runtime, int maxCycle, int employedBeeSize, 
			int onlookerBeeSize, int dimension) 
	{
		this.runtime = runtime;
		this.maxCycle = maxCycle;
		this.dimension = dimension;
		this.employedBeeSize = employedBeeSize;
		this.onlookerBeeSize = onlookerBeeSize;
		scoutBeeSize = (int) Math.ceil(employedBeeSize*.10);
		
		MSE = new double[employedBeeSize];
		solution = new double[dimension];
		GlobalParams = new double[dimension];
		GlobalMins = new double[runtime];
		fitness = new double[employedBeeSize];
		prob = new double[employedBeeSize];
		trial = new double[employedBeeSize];    
		
		Foods = new double[employedBeeSize][dimension];
		Params = new double[runtime][dimension];
		
		networks = new MLPNetwork[employedBeeSize];
		
		rand = new Random();
	}
	
	/**
	 * trains the neural netwok
	 * 
	 * @param trainingData
	 */
    public void train(Data trainingData) 
	{
    	input_data = trainingData.getInputVector();
		output_data = trainingData.getOutputVector();
		
		for( int run = 0; run < runtime; run++ ) {
			if(pause) {
				continue;
			}
			if(terminate) {
				break;
			}			
			double start = System.currentTimeMillis();
			initializePopulation();
			memorizeBestSource();
			
			for( int cycle = 0; cycle < maxCycle; ) {
				if(pause) {
					continue;
				}
				if(terminate) {
					break;
				}
				sendEmployedBees();
				calculateProbabilities();
				sendOnlookerBees();
				memorizeBestSource();
				sendScoutBees();
				ExperimentPane.getInstance().incrementCycle(cycle+1);
				cycle++;
			}
			ExperimentPane.getInstance().incrementRuntime(run+1);
			GlobalMins[run] = GlobalMin;
			meanRun += GlobalMin;
			
			Params[run] = GlobalParams;
			if( GlobalMin <= bestMin ){
				bestMin = GlobalMin;
				bestIndex = run;
			}
			double elapsedTime = (System.currentTimeMillis() - start) / 1000;
			
			ExperimentPane.getInstance().returnResult(/*bestMin*/GlobalMin, Params[bestIndex], elapsedTime, run+1);
		}
		ExperimentPane.getInstance().resetButtons();
		if(terminate)
			new MessageDialog("The optimization has been stopped.").setVisible(true);
		else
			new MessageDialog("Done optimizing.").setVisible(true);
	}
	
	/**
	 *  initialize all food sources / networks
	 */
	private void initializePopulation() 
	{
		for( int i = 0; i < employedBeeSize; i++ )
			initializeEachFood(i);
		GlobalMin = MSE[0];
		for( int j = 0; j < dimension; j++ )
			GlobalParams[j] = Foods[0][j];
	}
	
	private void initializeEachFood(int index) 
	{
		double r;
		for( int j = 0; j < dimension; j++ ) {
			r = ( (double)Math.random()*32767 / ((double)32767+(double)(1)) );
			Foods[index][j] = r * ( ub - lb ) + lb;
			solution[j] = Foods[index][j];
			//System.out.println( r+" :"+ Foods[index][j]);
		}
		networks[index] = new MLPNetwork(solution, input_data, output_data);
		MSE[index] = calculateObjectiveFunction(networks[index]);
		fitness[index] = calculateFitness(MSE[index]);
		trial[index] = 0;
		
	}

	private void memorizeBestSource() 
	{
		for( int i = 0; i < employedBeeSize; i++ ) 
			if( MSE[i] < GlobalMin ) {
				GlobalMin = MSE[i];
				for( int j = 0;j < dimension; j++)
					GlobalParams[j] = Foods[i][j];
	        }		
	}
	
	private void sendEmployedBees() 
	{
		for( int i = 0; i < employedBeeSize; i++ ) {
			neighborhoodSearch(i);
			evaluatePopulation();
			greedySelection(i);
		}
	}

	private void sendOnlookerBees() 
	{
		for( int i = 0; i < employedBeeSize; i++ ) {
			double r = (   (double)Math.random()*32767 / ((double)(32767)+(double)(1)) );
			if( r < prob[i]  ) {
				int maxOnlooker = (int)(prob[i] * onlookerBeeSize);
				
				for( int j = 0; j < maxOnlooker; j++ ) {
					neighborhoodSearch(i);
					evaluatePopulation();
					greedySelection(i);
				}
			}
		}
	}

	private void sendScoutBees() 
	{
		int[] sortedIndices = sortByFitness();
		for( int i = 0; i < scoutBeeSize; i++ ) {
			neighborhoodSearch(sortedIndices[i]);
			evaluatePopulation();
			greedySelection(sortedIndices[i]);
		}
	}

	private void neighborhoodSearch(int foodIndex) 
	{
		double r = ((double) Math.random()*32767 / ((double)(32767)+(double)(1)) );
        param2change = (int)(r * dimension);
        
        neighbour = rand.nextInt(employedBeeSize);
        while( neighbour == foodIndex ) 
        	neighbour = rand.nextInt(employedBeeSize);
        
        for( int j = 0; j < dimension; j++ )
        	solution[j] = Foods[foodIndex][j];
        
        /*v_{ij}=x_{ij}+\phi_{ij}*(x_{kj}-x_{ij}) */
        solution[param2change] = Foods[foodIndex][param2change]+(Foods[foodIndex][param2change]-Foods[neighbour][param2change])*(r-0.5)*2;
	}
	
	/**
	 *  evaluates current and new solution
	 */
	private void evaluatePopulation() 
	{
        MLPNetwork temp = new MLPNetwork(solution, input_data, output_data);
        ObjValSol = calculateObjectiveFunction(temp);
        FitnessSol = calculateFitness(ObjValSol);
	}
	
	private void greedySelection(int foodIndex) 
	{
        if( FitnessSol >= fitness[foodIndex] ) {
        	trial[foodIndex] = 0;
        	for( int j = 0; j < dimension; j++ )
        		Foods[foodIndex][j] = solution[j];
        	
        	MSE[foodIndex] = ObjValSol;
        	fitness[foodIndex] = FitnessSol;
        }
        else
            trial[foodIndex] = trial[foodIndex]+1;
	}

	private void calculateProbabilities() 
	{
		double maxfit = fitness[0];
		for( int i = 1; i < employedBeeSize; i++ ) {
	    	if(fitness[i] > maxfit)
	    		maxfit = fitness[i];
	    }

	    for(int i = 0; i < employedBeeSize; i++ )
	    	prob[i] = (0.9*(fitness[i]/maxfit))+0.1;
		
	}
	
	private double calculateObjectiveFunction(MLPNetwork mlpNetwork) 
	{
		return mlpNetwork.computeMSE();
	}
	
	private double calculateFitness(double fun) 
	{
		double result=0;
		if( fun >= 0)
			result=1/(fun+1);
		else
			result=1+Math.abs(fun);
		return result;
	}
	
	/**
	 * 
	 * @return the indices of the food sources sorted by fitness ASCENDING
	 */
	private int[] sortByFitness() 
	{
		int[] indices = new int[employedBeeSize];
		for( int i = 0; i < employedBeeSize; i++ ) 
			indices[i] = i;
		
		int tempIndex;
		double[] fitnessClone = fitness.clone();
		double tempFitness; 
		
		for( int i = 0; i < employedBeeSize - 1; i++ ) {
			for( int j = 0; j < employedBeeSize - 1; j++ ) {
				if( fitnessClone[j] > fitnessClone[j+1] ) {
					tempFitness = fitnessClone[j];
					tempIndex = indices[j];
					fitnessClone[j] = fitnessClone[j+1];
					indices[j] = indices[j+1];
					fitnessClone[j+1] = tempFitness;
					indices[j+1] = tempIndex;
				}
			}
		}
		return indices;
	}
	
	public double[] getSolution()
	{
		return Params[bestIndex];
	}
	
	public MLPNetwork getBestBee() 
	{
		return new MLPNetwork( Params[bestIndex] );
	}
	
	public void pause()
	{
		pause = true;
	}
	
	public void resume()
	{
		pause = false;
	}
	
	public void terminate()
	{
		terminate = true;
	}

	/*public static void main(String[] args) {
		ABC abc = new ABC(1, 1, 5, 0, 66);
		
		ArrayList<String> filename = new ArrayList<String>();
		filename.add("f1");
		filename.add("f2");
		filename.add("f3");
		double[][] inputVector = { {0, 1, 2, 3, 4, 5}, 
								   {6, 7, 8, 9, 10, 11}, 
								   {12, 13, 14, 15, 16, 17},
								   {18, 19, 20, 21, 22, 23}, 
								   {24, 25, 26, 27, 28, 29}, 
								   {30, 31, 32, 33, 34, 35}
								  };
		double[][] outputVector = { {1, 0, 0, 0, 0, 0}, 
									{0, 1, 0, 0, 0, 0}, 
									{0, 0, 1, 0, 0, 0},
									{0, 0, 0, 1, 0, 0},
									{0, 0, 0, 0, 1, 0},
									{0, 0, 0, 0, 0, 1}};
	
		
		Data data = new Data(filename, inputVector, outputVector);
		abc.train(data);
		
	}*/
	
}
