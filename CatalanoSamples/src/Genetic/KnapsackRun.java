/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Genetic;

/**
 *
 * @author Diego
 */
public class KnapsackRun {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        //Price
        int val[] = {
            10, 40, 30, 50, 20,
            15, 60, 120, 200, 1,
            100, 300, 150, 80, 50,
            300, 100, 5, 20, 2,
            37, 50
        };
        
        //Weights
        int wt[] = {
            9, 4, 6, 3, 2,
            3, 1, 1, 3, 10,
            2, 3, 1, 5, 2,
            6, 2, 3, 1, 4 ,
            2, 2, 1
        };
        
        //Maximum weight
        int W = 20;
        
        Knapsack problem = new Knapsack(val, wt, W);
        Run(problem, new RouletteWheelSelection(), new SinglePointCrossover(), 10000, 0.9f, 0.9f);
        
        
    }
    
    public static void Run(IFitness problem, ISelection selection, ICrossover crossover, int popu, float co, float mu){
        double[] v = new double[100];
        for (int n = 0; n < 1; n++) {
            Population pop = new Population(new BinaryChromosome(22), popu, problem, co, mu);
            pop.setOperators(selection, crossover, new BitFlipMutation());
            
            //if(n == 100) pop.setOperators(selection, crossover, new SwapMutation());
            //if(n == 200) pop.setOperators(selection, crossover, new InsertionMutation());
            //if(n == 300) pop.setOperators(selection, crossover, new ScrambleMutation());

            for (int i = 0; i < 100; i++) {
                pop.RunEpoch();
            }
            
            BinaryChromosome bc = (BinaryChromosome)pop.getBest();
            v[n] = bc.getFitness();
            //System.out.println(bc.getFitness());
        }
    }
    
}
