package abcnn;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.JOptionPane;

import ui.AppFrame;
import ui.ABCNNPane;

public class ABC extends Thread {
	
	private int runtime 	 = 0,
				maxCycle 	 = 0,
				dimension 	 = 0,
				foodNumber 	 = 0,
				param2change = 0,
				neighbour 	 = 0,
				limit		 = 0,
				bestIndex	 = 0;
	
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
	private Random rand = new Random();
		
	private ABCNNPane abcnnPane;
	private double[][] input_data;
	private double[][] output_data;
	
	public ABC(ABCNNPane abcnnPane, int runtime, int maxCycle, int foodNumber, int dimension) {
		this.abcnnPane = abcnnPane;
		
		this.runtime = runtime;
		this.maxCycle = maxCycle;
		this.dimension = dimension;
		this.foodNumber = foodNumber;
		
		MSE = new double[foodNumber];
		solution = new double[dimension];
		GlobalParams = new double[dimension];
		GlobalMins = new double[runtime];
		fitness = new double[foodNumber];
		prob = new double[foodNumber];
		trial = new double[foodNumber];    
		
		Foods = new double[foodNumber][dimension];
		Params = new double[runtime][dimension];
		
		networks = new MLPNetwork[foodNumber];
		
		limit = foodNumber*dimension;
	}
	
	/**
	 * sets the training data
	 * @param training_input
	 * @param training_output
	 */
	public void setTrainingData(double[][] input_data, double[][] output_data) {
		this.input_data = input_data;
		this.output_data = output_data;
	}
	
	/**
	 * start ABC
	 */
	@Override
    public void run(){		
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
				abcnnPane.incrementCycle(cycle+1);
			}
			abcnnPane.incrementRuntime(run+1);
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
		abcnnPane.showResult(bestMin, elapsedTime);
		//abcnnPane.print("mean run: "+meanRun/runtime +"\n");
		
		//abcnnPane.print("*****************TRAINING END*****************\n");
		JOptionPane.showMessageDialog(AppFrame.getInstance(), "Done training.");
	}
	
	/**
	 *  initialize all food sources / networks
	 */
	private void initializePopulation() {
		for( int i = 0; i < foodNumber; i++ )
			initializeEachFood(i);
		GlobalMin = MSE[0];
		for( int j = 0; j < dimension; j++ )
			GlobalParams[j] = Foods[0][j];
	}

	private void initializeEachFood(int index) {
		double r;
		for( int j = 0; j < dimension; j++ ) {
			r = ( (double)Math.random()*32767 / ((double)32767+(double)(1)) );
			Foods[index][j] = r * ( ub - lb ) + lb;
			solution[j] = Foods[index][j];
		}
		networks[index] = new MLPNetwork(solution, input_data, output_data);
		MSE[index] = calculateObjectiveFunction(networks[index]);
		fitness[index] = calculateFitness(MSE[index]);
		trial[index] = 0;
	}

	private void memorizeBestSource() {
		for( int i = 0; i < foodNumber; i++ ) 
			if( MSE[i] < GlobalMin ) {
				GlobalMin = MSE[i];
				for( int j = 0;j < dimension; j++)
					GlobalParams[j] = Foods[i][j];
	        }		
	}
	
	private void sendEmployedBees() {
		for( int i = 0; i < foodNumber; i++ ) {
			neighborhoodSearch(i);
			evaluatePopulation();
			greedySelection(i);
		}
	}

	private void sendOnlookerBees() {
		int t = 0, i = 0;
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
		}
		
	}

	private void sendScoutBees() {
		int maxtrialindex = 0;
		for( int i=1; i < foodNumber; i++ ) {
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

	private void neighborhoodSearch(int foodIndex) {
		double r = ((double) Math.random()*32767 / ((double)(32767)+(double)(1)) );
        param2change = (int)(r * dimension);
        
        neighbour = rand.nextInt(foodNumber);
        while( neighbour == foodIndex ) 
        	neighbour = rand.nextInt(foodNumber);
        
        for( int j = 0; j < dimension; j++ )
        	solution[j] = Foods[foodIndex][j];
        
        /*v_{ij}=x_{ij}+\phi_{ij}*(x_{kj}-x_{ij}) */
        solution[param2change] = Foods[foodIndex][param2change]+(Foods[foodIndex][param2change]-Foods[neighbour][param2change])*(r-0.5)*2;
        //try other formula
//        System.out.println( foodIndex+" "+ MSE[foodIndex] +" "+calculateFitness(MSE[foodIndex]) +"  "+fitness[foodIndex] );
//        System.out.println( foodIndex+" "+ ObjValSol+" "+FitnessSol);
        
	}
	
	/**
	 *  evaluates current and new solution
	 */
	private void evaluatePopulation() {
		if( solution[param2change] < lb )
	        	solution[param2change] = lb;
        if( solution[param2change] > ub)
            solution[param2change] = ub;

        MLPNetwork temp = new MLPNetwork(solution, input_data, output_data);
        ObjValSol = calculateObjectiveFunction(temp);//calculateFunction(solution);
        FitnessSol = calculateFitness(ObjValSol);
	}
	
	private void greedySelection(int foodIndex) {
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

	private void calculateProbabilities() {
		 double maxfit = fitness[0];
		    for( int i = 1; i < foodNumber; i++ ) {
		    	if(fitness[i] > maxfit)
		    		maxfit = fitness[i];
		    }

		    for(int i = 0; i < foodNumber; i++ )
		    	prob[i] = (0.9*(fitness[i]/maxfit))+0.1;
		
	}
	
	private double calculateObjectiveFunction(MLPNetwork mlpNetwork) {
		return mlpNetwork.computeMSE();
	}
	
	private double calculateFitness(double fun) {
		double result=0;
		if( fun >= 0)
			result=1/(fun+1);
		else
			result=1+Math.abs(fun);
		return result;
	}
	
	public static void main(String[] args){
		int runtime = 30;
		int maxCycle = 1000;
		int dimension = 9;
		int foodNumber = 10;
		ABC abc = new ABC(null, runtime, maxCycle, foodNumber, dimension);
		abc.run();
		
		double[] input = {0, 1};
		double[] result = abc.test(input);
		
		System.out.println("result: "+result[0] +" rounded: "+Math.round(result[0]));
	}

	public double[] test(double[] input) {
		//System.out.println(bestIndex+1 +" "+GlobalMins[bestIndex]);
		//for( int i = 0; i < dimension; i++ )
			//System.out.println( "p: "+Params[bestIndex][i] );
		
		MLPNetwork best = new MLPNetwork( Params[bestIndex] );
		return best.test(input);
		
	}

	public MLPNetwork getBestBee() {
		return new MLPNetwork( Params[bestIndex] );
	}

}
