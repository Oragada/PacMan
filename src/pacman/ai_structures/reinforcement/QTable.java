package pacman.ai_structures.reinforcement;

import java.util.HashMap;
import java.util.Random;
import java.util.Set;

import pacman.game.Constants.MOVE;

public class QTable {
	
    Random randomGenerator;
    HashMap<QState, double[]> table;
    int actionRange = MOVE.values().length-1;
    

    // E-GREEDY Q-LEARNING SPECIFIC VARIABLES
    double explorationChance=0.4;
    double gammaValue=0.1;
    double learningRate=0.25;

    //PREVIOUS STATE AND ACTION VARIABLES
    int[] prevState;
    int prevAction;
    
    QTable(){
        randomGenerator = new Random();
        actionRange = MOVE.values().length-1;
        table = new HashMap<QState, double[]>();
    }
    
    int getNextAction(int[] state){
        prevState=(int[])state.clone();
        if(randomGenerator.nextFloat()<explorationChance){
            prevAction=explore();
        } else {
            prevAction=getBestAction(state);
        }
        return prevAction;
    }
    
    public void reset(){
    	prevState = null;
    	prevAction = -1;
    }
    
    public void setEC(double val){
    	
    }
    
    
    int getBestAction(int[] state){
    	double[] actions = getActionsQValues(state);
    	double highest = actions[0];
    	int highDir = 0;
    	for(int i = 0; i<actions.length; i++){
    		if(actions[i] > highest) {
    			highest = actions[i];
    			highDir = i;
    		}
    	}
    	return highDir;
    }

    
    int explore(){
    	return randomGenerator.nextInt(actionRange);
    	//TODO: Make it more useful;
    }

    void updateQvalue(int reward, int[] state){
    	if(prevState != null){
    		int bestA = getBestAction(state);
    		assert table.containsKey(new QState(state));
        	double[] q = table.get(new QState(state));
        	double maxQa = gammaValue*q[bestA];
        	double Qsa = table.get(new QState(prevState))[prevAction];
        	double deltaQSA = learningRate*(reward + maxQa - Qsa);
        	table.get(new QState(prevState))[prevAction] += deltaQSA;
        	
    	}
    	
    }
    
    double[] getActionsQValues(int[] state){
    	//if(!table.containsKey(new QState(state))) System.out.println("No actions");
    	//else System.out.println("Actions exist");
    	double[] actions = table.get(new QState(state));
        
        if(actions==null){
            actions = new double[actionRange];
            for(int i=0;i<actionRange;i++) actions[i]=0.0;
            table.put(new QState(state), actions);
        }
		return actions;
    }
    
    void printQtable(){
    	//System.out.println(table.size());
    	StringBuilder strB = new StringBuilder();
    	Set<QState> keys = table.keySet();
    	for(QState qs : keys){
    		double[] v = table.get(qs);
    		V(1);
    		//qs.get(0)
    		
            System.out.print(V(qs.get(0))+" "+V(qs.get(1))+" "+V(qs.get(2))+" "+V(qs.get(3))); System.out.println("  UP   RIGHT  DOWN  LEFT" );
            System.out.print(V(qs.get(4))+" "+V(qs.get(5))+" "+V(qs.get(6))+" "+V(qs.get(7))); System.out.println(": " + v[0]+"   "+v[1]+"   "+v[2]+"   "+v[3]);
            System.out.println(V(qs.get(8))+" "+V(qs.get(9))+" "+V(qs.get(10))+" "+V(qs.get(11)));
            System.out.println(V(qs.get(12))+" "+V(qs.get(13))+" "+V(qs.get(14))+" "+V(qs.get(15)));
            System.out.println();
    	}
    	/*
        for(String s : table.keySet()){
        	char[] key = s.toCharArray();
        	float[] values = getValues(key);

            System.out.print(key[0]+""+key[1]+""+key[2]);
            System.out.println("  UP   RIGHT  DOWN  LEFT" );
            System.out.print(key[3]+""+key[4]+""+key[5]);
            System.out.println(": " + values[0]+"   "+values[1]+"   "+values[2]+"   "+values[3]);
            System.out.println(key[6]+""+key[7]+""+key[8]);
        }*/
    }
    
    
    private String V(int i) {
		if(new Integer(i).toString().length() == 1){
			return " "+i;
		}
		return ""+i;
		
	}

	public static int ConvertMoveToInt(MOVE move){
    	switch(move){
    	case UP: return 0;
    	case DOWN: return 1;
    	case LEFT: return 2;
    	case RIGHT: return 3;
    	default: return -1;
    	}
    }
	
    public static MOVE ConvertIntToMove(int action){
    	switch(action){
    	case 0: return MOVE.UP;
    	case 1: return MOVE.DOWN;
    	case 2: return MOVE.LEFT;
    	case 3: return MOVE.RIGHT;
    	default: return MOVE.NEUTRAL;
    	}
    }
}
