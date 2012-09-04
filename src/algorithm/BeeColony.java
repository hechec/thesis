package algorithm;

public class BeeColony {
	
	/* Control Parameters of ABC algorithm*/
	private int NP = 100;
	private int foodNumber = NP/2;
	
	public int maxCycle = 2500;
	
	/* Problem specific variables*/
	int dimension = 9;//100; /*The number of parameters of the problem to be optimized*/
	double lb = -1.0; /*lower bound of the parameters. */
	double ub = 1.0; /*upper bound of the parameters. lb and ub can be defined as arrays for the problems of which parameters have different bounds*/
	
	private int limit = foodNumber*dimension;

	// number of training data?	
	int runtime = 30;  /*Algorithm can be run many times in order to see its robustness*/

	int dizi1[]=new int[10];
	double foods[][]=new double[foodNumber][dimension];        /*Foods is the population of food sources. Each row of Foods matrix is a vector holding D parameters to be optimized. The number of rows of Foods matrix equals to the FoodNumber*/
	double f[]=new double[foodNumber];        /*f is a vector holding objective function values associated with food sources */
	double fitness[]=new double[foodNumber];      /*fitness is a vector holding fitness (quality) values associated with food sources*/
	double trial[]=new double[foodNumber];         /*trial is a vector holding trial numbers through which solutions can not be improved*/
	double prob[]=new double[foodNumber];          /*prob is a vector holding probabilities of food sources (solutions) to be chosen*/
	double solution[]=new double[dimension];            /*New solution (neighbour) produced by v_{ij}=x_{ij}+\phi_{ij}*(x_{kj}-x_{ij}) j is a randomly chosen parameter and k is a randomlu chosen solution different from i*/
	
	double ObjValSol;              /*Objective function value of new solution*/
	double FitnessSol;              /*Fitness value of new solution*/
	int neighbour, param2change;                   /*param2change corrresponds to j, neighbour corresponds to k in equation v_{ij}=x_{ij}+\phi_{ij}*(x_{kj}-x_{ij})*/

	double GlobalMin;                       /*Optimum solution obtained by ABC algorithm*/
	double GlobalParams[]=new double[dimension];                   /*Parameters of the optimum solution*/
	double GlobalMins[]=new double[runtime];       /*GlobalMins holds the GlobalMin of each run in multiple runs*/
	
	private MLPNetwork[] networks = new MLPNetwork[foodNumber];
	
	public BeeColony() {
		//
	}
	
	/**
	 *	init all food sources 
	 */
	void initializePopulation() {
		for(int i = 0; i < foodNumber; i++)
			initEachFoodSource(i);
		GlobalMin = f[0];
	    for(int i = 0; i < dimension; i++)
	    	GlobalParams[i] = foods[0][i];
	}
	
	/**
	 *	The best food source is memorized 
	 */
	void memorizeBestSource() {
		for( int i = 0; i < foodNumber; i++ ) 
			if( f[i] < GlobalMin ) {
				GlobalMin = f[i];
				for( int j = 0;j < dimension; j++)
					GlobalParams[j] = foods[i][j];
	        }
	 }
	
	void SendEmployedBees() {
		int i,j;
		double r;
		for( i = 0; i < foodNumber; i++ ){
	        /*The parameter to be changed is determined randomly*/
	        r = ((double) Math.random()*32767 / ((double)(32767)+(double)(1)) );
	        param2change = (int)(r * dimension);
	        
	        /*A randomly chosen solution is used in producing a mutant solution of the solution i*/
	        r = (   (double)Math.random()*32767 / ((double)(32767)+(double)(1)) );
	        neighbour = (int)(r*foodNumber);

	        /*Randomly selected solution must be different from the solution i*/        
	        // while(neighbour==i)
	        // {
	        // r = (   (double)Math.random()*32767 / ((double)(32767)+(double)(1)) );
	        // neighbour=(int)(r*FoodNumber);
	        // }
	        for( j = 0; j < dimension; j++ )
	        	solution[j] = foods[i][j];

	        /*v_{ij}=x_{ij}+\phi_{ij}*(x_{kj}-x_{ij}) */
	        r = (   (double)Math.random()*32767 / ((double)(32767)+(double)(1)) );
	        solution[param2change] = foods[i][param2change]+(foods[i][param2change]-foods[neighbour][param2change])*(r-0.5)*2;
	        
	        /*if generated parameter value is out of boundaries, it is shifted onto the boundaries*/
	        if( solution[param2change] < lb )
	           solution[param2change] = lb;
	        if( solution[param2change] > ub)
	           solution[param2change] = ub;
	        
	        MLPNetwork n = new MLPNetwork();
	        n.initWeights(solution);
	        
	        ObjValSol = calculateFunction(n);//calculateFunction(solution);
	        FitnessSol = calculateFitness(ObjValSol);
	        
	        /*a greedy selection is applied between the current solution i and its mutant*/
	        if (FitnessSol > fitness[i]) {
	        	/*If the mutant solution is better than the current solution i, replace the solution with the mutant and reset the trial counter of solution i*/
	        	trial[i] = 0;
	        	for( j = 0; j < dimension; j++)
	        		foods[i][j] = solution[j];
	        	f[i] = ObjValSol;
	        	fitness[i] = FitnessSol;
	        }
	        else {  /*if the solution i can not be improved, increase its trial counter*/
	            trial[i] = trial[i]+1;
	        }
		}
	}
	
	void CalculateProbabilities() {
	    double maxfit = fitness[0];
	    for( int i = 1; i < foodNumber; i++ ) {
	    	if(fitness[i] > maxfit)
	    		maxfit = fitness[i];
	    }

	    for(int i = 0; i < foodNumber; i++ )
	    	prob[i] = (0.9*(fitness[i]/maxfit))+0.1;
	}
	
	void SendOnlookerBees() {
		int i = 0, j, t = 0;
		double r;
		while(t < foodNumber) {
	        r = (   (double)Math.random()*32767 / ((double)(32767)+(double)(1)) );
	        if( r <prob[i]) {	/*choose a food source depending on its probability to be chosen*/
	        	t++;
		        /*The parameter to be changed is determined randomly*/
		        r = ((double)Math.random()*32767 / ((double)(32767)+(double)(1)) );
		        param2change = (int)(r*dimension);
		        
		        /*A randomly chosen solution is used in producing a mutant solution of the solution i*/
		        r = ( (double)Math.random()*32767 / ((double)(32767)+(double)(1)) );
		        neighbour = (int)(r*foodNumber);
	
		        /*Randomly selected solution must be different from the solution i*/        
		        while(neighbour == i) {
		        	//System.out.println(Math.random()*32767+"  "+32767);
		        	r = ( (double)Math.random()*32767 / ((double)(32767)+(double)(1)) );
		        	neighbour = (int)(r*foodNumber);
		        }
		        for( j = 0; j < dimension; j++ )
		        	solution[j] = foods[i][j];
	
		        /*v_{ij}=x_{ij}+\phi_{ij}*(x_{kj}-x_{ij}) */
		        r = (   (double)Math.random()*32767 / ((double)(32767)+(double)(1)) );
		        solution[param2change] = foods[i][param2change]+(foods[i][param2change]-foods[neighbour][param2change])*(r-0.5)*2;
	
		        /*if generated parameter value is out of boundaries, it is shifted onto the boundaries*/
		        if (solution[param2change]<lb)
		           solution[param2change]=lb;
		        if (solution[param2change]>ub)
		           solution[param2change]=ub;   
		        
		        MLPNetwork n = new MLPNetwork();
		        n.initWeights(solution);
		        ObjValSol = calculateFunction(n); //calculateFunction(solution);
		        FitnessSol = calculateFitness(ObjValSol);
		        
		        /*a greedy selection is applied between the current solution i and its mutant*/
		        if(FitnessSol>fitness[i]) {
		        	/*If the mutant solution is better than the current solution i, replace the solution with the mutant and reset the trial counter of solution i*/
		        	trial[i] = 0;
		        	for( j = 0; j < dimension; j++ )
		        		foods[i][j] = solution[j];
		        	f[i] = ObjValSol;
		        	fitness[i] = FitnessSol;
		        }
		        else {   /*if the solution i can not be improved, increase its trial counter*/
		            trial[i] = trial[i]+1;
		        }
	        } /* end if */
	        i++;
	        if(i == foodNumber)
	        	i=0;
		}/* end while*/

	}

	void SendScoutBees() {
		int maxtrialindex = 0;
		for( int i=1; i < foodNumber; i++ ) {
			if(trial[i] > trial[maxtrialindex])
				maxtrialindex = i;
		}
		if(trial[maxtrialindex] >= limit)
			initEachFoodSource(maxtrialindex);
	}
	
	private void initEachFoodSource(int i) {
		int j;
		double r;
	    for( j = 0; j < dimension; j++ ) {
	        r = ( (double)Math.random()*32767 / ((double)32767+(double)(1)) );
	        foods[i][j] = r * ( ub - lb ) + lb;
			solution[j] = foods[i][j];
	    }
	    networks[i] = new MLPNetwork();
	    networks[i].initWeights(solution);
	    
		f[i] = calculateFunction(networks[i]);//f[i] = calculateFunction(solution);
		fitness[i] = calculateFitness(f[i]);
		trial[i] = 0;
	}
	
	double calculateFitness(double fun) {
		double result=0;
		if( fun >= 0)
			result=1/(fun+1);
		else
			result=1+Math.abs(fun);
		return result;
	}
	
	/* MSE? */
	double calculateFunction(double sol[]) {
	     // rastrigin
		 int j;
		 double top=0;
	
		 for(j=0;j<dimension;j++)
		 {
			 top=top+(Math.pow(sol[j],(double)2)-10*Math.cos(2*Math.PI*sol[j])+10);
		 }
		 return top;
	}
	
	private double calculateFunction(MLPNetwork network) {
		return network.computeMSE();
	}

	public static void main(String[] args) {
		BeeColony bee = new BeeColony();
		bee.initializePopulation();
	}
	
}
