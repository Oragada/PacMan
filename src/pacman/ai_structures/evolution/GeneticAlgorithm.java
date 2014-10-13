package pacman.ai_structures.evolution;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Random;        // for generating random numbers
import java.util.ArrayList;     // arrayLists are more versatile than arrays

import pacman.Executor;
import pacman.controllers.examples.Legacy;
import pacman.controllers.examples.StarterGhosts;


/**
 * Genetic Algorithm sample class <br/>
 * <b>The goal of this GA sample is to maximize the number of capital letters in a String</b> <br/>
 * compile using "javac GeneticAlgorithm.java" <br/>
 * test using "java GeneticAlgorithm" <br/>
 *
 * @author A.Liapis
 */

public class GeneticAlgorithm {
    // --- constants
    static int CHROMOSOME_SIZE=3;
    static int POPULATION_SIZE=100;

    // --- variables:

    /**
     * The population contains an ArrayList of genes (the choice of arrayList over
     * a simple array is due to extra functionalities of the arrayList, such as sorting)
     */
    ArrayList<Gene> mPopulation;
    Random rand;
    
    int optimalFitness;
    int MAX_GEN;
    int PAIRINGS;
    double MUTATIONCHANCE;
	int MUTA_VARI;
	
	int currentGen;

    // --- functions:

    /**
     * Creates the starting population of Gene classes, whose chromosome contents are random
     * @param size: The size of the popultion is passed as an argument from the main class
     */
    public GeneticAlgorithm(int size){
        // initialize the arraylist and each gene's initial weights HERE
        mPopulation = new ArrayList<Gene>();
        for(int i = 0; i < size; i++){
            Gene entry = new Gene();
            entry.randomizeChromosome();
            mPopulation.add(entry);
        }
        
        rand = new Random();
        
        optimalFitness = Integer.MAX_VALUE;
        
        MAX_GEN = 30;
        PAIRINGS = POPULATION_SIZE/4;
        //setPairings();
        MUTATIONCHANCE = 0.7;
        MUTA_VARI = POPULATION_SIZE/20;
        
    }
    
    void setPairings(){
    	/*PAIRINGS = (int) Math.round(POPULATION_SIZE*(
    			((double)(currentGen/2)+5)/
    			(double)(MAX_GEN+5))
    			/2);*/
    }
    /**
     * For all members of the population, runs a heuristic that evaluates their fitness
     * based on their phenotype. The evaluation of this problem's phenotype is fairly simple,
     * and can be done in a straightforward manner. In other cases, such as agent
     * behavior, the phenotype may need to be used in a full simulation before getting
     * evaluated (e.g based on its performance)
     */
    public void evaluateGeneration(){
        for(int i = 0; i < mPopulation.size(); i++){
        	Gene g = mPopulation.get(i);
        	EvolvedDecisionTree tree = new EvolvedDecisionTree(g.getChromosomeElement(0), g.getChromosomeElement(1), g.getChromosomeElement(2));
            Executor exec = new Executor();
            g.setFitness((float) exec.runSampling(tree, new Legacy(), 10));
        }
    }
    /**
     * With each gene's fitness as a guide, chooses which genes should mate and produce offspring.
     * The offspring are added to the population, replacing the previous generation's Genes either
     * partially or completely. The population size, however, should always remain the same.
     * If you want to use mutation, this function is where any mutation chances are rolled and mutation takes place.
     */
    public void produceNextGeneration(){
    	mPopulation.sort(new Comparator<Gene>(){public int compare(Gene x, Gene y){return  (int) (x.mFitness - y.mFitness);}});
    	List<Gene> breeders = mPopulation.subList(PAIRINGS*2, mPopulation.size());
    	ArrayList<Gene> totalOffspring = new ArrayList<Gene>();
    	for(int i = 0; i<PAIRINGS; i++){
    		//get two random breeders
    		int momIndex = rand.nextInt(breeders.size());
    		int dadIndex = momIndex;
    		while(dadIndex == momIndex){
    			dadIndex = rand.nextInt(breeders.size());
    		}
    		Gene mom = breeders.get(momIndex);
    		Gene dad = breeders.get(dadIndex);
    		
    		//combine and produce new Genotypes
    		Gene[] offspring = dad.reproduce(mom);
    		
    		//decide whether mutation happens
    		if(rand.nextDouble() < MUTATIONCHANCE){
    			offspring[0].mutate(MUTA_VARI);
    		}
    		if(rand.nextDouble() < MUTATIONCHANCE){
    			offspring[1].mutate(MUTA_VARI);
    		}
    		totalOffspring.add(offspring[0]);
    		totalOffspring.add(offspring[1]);
    	}
    	//cast away non-breeders
    	//create new population of breeders and offspring
    	mPopulation = new ArrayList<Gene>();
    	mPopulation.addAll(breeders);
    	mPopulation.addAll(totalOffspring);
    	
    	
    }

    // accessors
    /**
     * @return the size of the population
     */
    public int size(){ return mPopulation.size(); }
    /**
     * Returns the Gene at position <b>index</b> of the mPopulation arrayList
     * @param index: the position in the population of the Gene we want to retrieve
     * @return the Gene at position <b>index</b> of the mPopulation arrayList
     */
    public Gene getGene(int index){ return mPopulation.get(index); }


    // Genetic Algorithm maxA testing method
    public static void main(String[] args){
        // Initializing the population (we chose 500 genes for the population,
        // but you can play with the population size to try different approaches)
        GeneticAlgorithm population = new GeneticAlgorithm(POPULATION_SIZE);
        
        // For the sake of this sample, evolution goes on forever.
        // If you wish the evolution to halt (for instance, after a number of
        //   generations is reached or the maximum fitness has been achieved),
        //   this is the place to make any such checks
        while(true){
            // --- evaluate current generation:
            population.evaluateGeneration();
            // --- print results here:
            // we choose to print the average fitness,
            // as well as the maximum and minimum fitness
            // as part of our progress monitoring
            double avgFitness=0.0;
            double minFitness=Double.POSITIVE_INFINITY;
            double maxFitness=Double.NEGATIVE_INFINITY;
            String bestIndividual="";
            String worstIndividual="";
            for(int i = 0; i < population.size(); i++){
                double currFitness = population.getGene(i).getFitness();
                avgFitness += currFitness;
                if(currFitness < minFitness){
                    minFitness = currFitness;
                    worstIndividual = population.getGene(i).getPhenotype();
                }
                if(currFitness > maxFitness){
                    maxFitness = currFitness;
                    bestIndividual = population.getGene(i).getPhenotype();
                }
            }
            if(population.size()>0){ avgFitness = avgFitness/population.size(); }
            String output = "Generation: " + population.currentGen;
            output += "\t AvgFitness: " + avgFitness;
            output += "\t MinFitness: " + minFitness + " (" + worstIndividual +")";
            output += "\t MaxFitness: " + maxFitness + " (" + bestIndividual +")";
            System.out.println(output);
            // produce next generation:
            population.produceNextGeneration();
            population.currentGen++;
            population.setPairings();
            if(population.optimalFitness == maxFitness | population.currentGen > population.MAX_GEN){
            	System.out.println("Best individual: " + bestIndividual);
            	break;
            }
        }
    }
}

