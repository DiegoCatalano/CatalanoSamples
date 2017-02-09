/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Genetic;

import Catalano.Genetic.BinaryChromosome;
import Catalano.Genetic.Fitness.IFitness;
import Catalano.Genetic.IChromosome;

/**
 *
 * @author Diego
 */
public class Knapsack implements IFitness{

    private int[] values;
    private int[] weights;
    private int maxWeight;
    
    public Knapsack(int[] values, int[] weights, int maxWeight) {
        this.values = values;
        this.weights = weights;
        this.maxWeight = maxWeight;
    }

    @Override
    public double Evaluate(IChromosome chromossome) {
        
        BinaryChromosome bc = (BinaryChromosome)chromossome;
        String binary = bc.toBinary();
        
        int v = 0;
        int w = 0;
        
        for (int i = 0; i < binary.length(); i++) {
            if(binary.charAt(i) == '1'){
                
                if(w+weights[i] <= maxWeight){
                    v += values[i];
                    w += weights[i];
                }
                else{
                    bc.setGene(i, '0');
                }
            }
        }
        
        return v;
    }
    
    /**
     * Compute all the combinatorial possibilities.
     * @return Best solution.
     */
    public BinaryChromosome BruteForce(){
        
        //Number of combinations
        int n = (int)Math.pow(2, values.length);
        
        BinaryChromosome best = null;
        double fit = 0;
        
        BinaryChromosome bc = new BinaryChromosome(values.length);
        for (int i = 0; i < n; i++) {
            boolean[] usage = toBinary(i+1, values.length);
            String bin = toBinaryString(usage);
            bc.setBinary(bin);
            bc.Evaluate(this);
            if(bc.getFitness() > fit){
                fit = bc.getFitness();
                best = (BinaryChromosome)bc.Clone();
            }
        }
        
        return best;
    }
    
    private boolean[] toBinary(int number, int bits) {
        final boolean[] ret = new boolean[bits];
        for (int i = 0; i < bits; i++) {
            ret[bits - 1 - i] = (1 << i & number) != 0;
        }
        return ret;
    }
    
    private String toBinaryString(boolean[] bits){
        
        String bin = "";
        for (int i = 0; i < bits.length; i++) {
            if(bits[i] == true)
                bin += "1";
            else
                bin += "0";
        }
        
        return bin;
    }
}
