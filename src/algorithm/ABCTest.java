package algorithm;

public class ABCTest {
	
public static void main(String[] args) {
		
		BeeColony bee = new BeeColony();
		
		int iter=0;
		int run=0;
		int j=0;
		double mean=0;
		
		double best = 100;
		int bestIndex = 0;
		double[][] params = new double[bee.runtime][bee.dimension];
		//srand(time(NULL));
		for(run=0;run<bee.runtime;run++)
		{
			bee.initializePopulation();
			bee.memorizeBestSource();
			for (iter=0;iter<bee.maxCycle;iter++)
			{
				bee.SendEmployedBees();
				bee.CalculateProbabilities();
				bee.SendOnlookerBees();
				bee.memorizeBestSource();
				bee.SendScoutBees();
			}
			//for(j=0;j<bee.dimension;j++)
			//{
				//System.out.println("GlobalParam[%d]: %f\n",j+1,GlobalParams[j]);
				//System.out.println("GlobalParam["+(j+1)+"]:"+bee.GlobalParams[j]);
			//}
			//System.out.println("%d. run: %e \n",run+1,GlobalMin);
			System.out.println((run+1)+".run:"+bee.GlobalMin);
			bee.GlobalMins[run]=bee.GlobalMin;
			mean=mean+bee.GlobalMin;
			
			if( bee.GlobalMin < best ){
				best = bee.GlobalMin;
				bestIndex = run;
			}
			params[run] = bee.GlobalParams;
			
		}
		mean=mean/bee.runtime;
		//System.out.println("Means of %d runs: %e\n",runtime,mean);
		System.out.println("Means  of "+bee.runtime+"runs: "+mean);
		System.out.println( params[bestIndex].length );
		
		MLPNetwork network = new MLPNetwork();
		network.initWeights(params[bestIndex]);
		double[] input = {1, 0};
		double[] result = network.test(input);
		System.out.println("result: "+result[0] +" rounded: "+Math.round(result[0]));
		
	}
	
}
