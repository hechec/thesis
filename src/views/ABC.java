package views;

import java.util.ArrayList;
import java.util.Random;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

import dataset.Data;

import abcnn.MLPNetwork;

import ui.ABCNNTab;

public class ABC{
	
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
		
	private TrainPane trainPane;
	private double[][] input_data;
	private double[][] output_data;

	public ABC(int runtime, int maxCycle, int employedBeeSize, 
			int onlookerBeeSize, int dimension) 
	{
		this.trainPane = TrainPane.getInstance();
		
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
	 * start training
	 */
    public void train(Data trainingData) 
	{
    	input_data = trainingData.getInputVector();
		output_data = trainingData.getOutputVector();
		double start = System.currentTimeMillis();
		
		for( int run = 0; run < runtime; run++ ) {
			initializePopulation();
			memorizeBestSource();
			
			for( int cycle = 0; cycle < maxCycle; cycle++ ) {
				sendEmployedBees();
				calculateProbabilities();
				sendOnlookerBees();
				memorizeBestSource();
				sendScoutBees();
				trainPane.incrementCycle(cycle+1);
				//System.out.println("cycle: "+(cycle+1));
			}
			trainPane.incrementRuntime(run+1);
			//for( int i = 0; i < GlobalParams.length; i++ )
				//System.out.println( GlobalParams[i] );
			
			GlobalMins[run] = GlobalMin;
			meanRun += GlobalMin;
			
			Params[run] = GlobalParams;
			if( GlobalMin <= bestMin ){
				bestMin = GlobalMin;
				bestIndex = run;
			}
			//abcnnPane.print("run "+(run+1)+": "+GlobalMin+"\n");
		}
		double elapsedTime = (System.currentTimeMillis() - start) / 1000;
		
		trainPane.displayResult(bestMin, Params[bestIndex], elapsedTime);
		JOptionPane.showMessageDialog(trainPane, "Finished training the network.");
		
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
		//System.out.println("Onlooker Bees:");
		/*int t = 0, i = 0;
		double r;
		while( t < foodNumber ) {
			r = (   (double)Math.random()*32767 / ((double)(32767)+(double)(1)) );
			if( r < prob[i]  ) {
				t++;
				neighborhoodSearch(i);
				evaluatePopulation();
				greedySelection(i);
			}
			i++;
			if(i == foodNumber)
	        	i = 0;
		}*/
		
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
		//sortByFitness();
		//for( int i = 0; i < employedBeeSize; i++ )
		//	System.out.println( fitness[i] +" "+scouts[i]);
		
		for( int i = 0; i < scoutBeeSize; i++ ) {
			
		}
		/*int maxtrialindex = 0;
		for( int i=1; i < employedBeeSize; i++ ) {
			if(trial[i] > trial[maxtrialindex])
				maxtrialindex = i;
		}
		if(trial[maxtrialindex] >= limit)
			initializeEachFood(maxtrialindex);		// only one bee becomes a scout bee ?
		/*int  ctr = 0;
		for( int i = 0; i < foodNumber; i++ ) {
			if( trial[i] >= limit ) {
				initializeEachFood(i);
				neighborhoodSearch(i);
				evaluatePopulation();
				greedySelection(i);
				ctr++;
			}
			//System.out.println( trial[i] );
		}
		System.out.println("SCOUT: "+ctr);*/
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
        
        //System.out.println("i: " + foodIndex + "\th: " + neighbour + "\tj: " + param2change + "\tr: "+ r + 
        //		"\tVij: " + solution[param2change] + "\tXij: " + Foods[foodIndex][param2change] +"\tXhj: "+Foods[neighbour][param2change]);
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
		//System.out.println( "fitness current: "+fitness[foodIndex] +"\tfitness new: "+FitnessSol );
		
        if( FitnessSol >= fitness[foodIndex] ) {
        	//System.out.println( "Replace current solution." );
        	trial[foodIndex] = 0;
        	for( int j = 0; j < dimension; j++ )
        		Foods[foodIndex][j] = solution[j];
        	
        	MSE[foodIndex] = ObjValSol;
        	fitness[foodIndex] = FitnessSol;
        	//System.out.println(foodIndex);
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
	
	int[] scouts;
	
	private void sortByFitness() 
	{
		scouts = new int[employedBeeSize];
		for( int i = 0; i < employedBeeSize; i++ ) 
			scouts[i] = i;
		
		int temp;
		double[] tempFitness = fitness.clone();
		double temp2;
		
		for( int i = 0; i < employedBeeSize - 1; i++ ) {
			for( int j = 0; j < employedBeeSize - 1; j++ ) {
				if( tempFitness[j] > tempFitness[j+1] ) {
					temp2 = tempFitness[j];
					temp = scouts[j];
					tempFitness[j] = tempFitness[j+1];
					scouts[j] = scouts[j+1];
					tempFitness[j+1] = temp2;
					scouts[j+1] = temp;
				}
			}
		}
	}
	
	/***********************************/
	/*                                 */
	/***********************************/
	
	public double[] getSolution()
	{
		return Params[bestIndex];
	}

	public double[] test(double[] input) 
	{
		MLPNetwork best = new MLPNetwork( Params[bestIndex] );
		return best.test(input);
	}

	public MLPNetwork getBestBee() 
	{
		return new MLPNetwork( Params[bestIndex] );
	}
	
	public static void main(String[] args) {
		ABC abc = new ABC(1, 10, 11, 0, 126);
		
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
		
	}
	
}
