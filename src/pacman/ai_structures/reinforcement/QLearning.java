package pacman.ai_structures.reinforcement;

import pacman.game.Constants.MOVE;


public class QLearning {
	// --- variables
    protected int[] state;
    QTable q = new QTable();
    int moveCounter=0;

    public int[] getState(){
        return (int[])state.clone();
    }

    /*public boolean isValidMove(int action){
    	
        return isValidMove(action, state);
    }

    public boolean isValidMove(int action, int[] st){
    	//TODO: adapt
    	return false;
    	
        //char[] nextMap=getNextState(action);
        //return (nextMap==map);
    }*/
    
    public void SetExplor(int round){
    	q.setEC(1.0/((double)round)*0.2+1);
    }

    public String getMoveName(int action){
        return QTable.ConvertIntToMove(action).toString();
    }

    /*void runLearningLoop() {
        int moveCounter=0;

        while(true){
            // PRINT MAP
            System.out.println("MOVE "+moveCounter);
            // CHECK IF WON, THEN RESET
            if(isPacManDead()){
                System.out.println("GOAL REACHED IN "+moveCounter+" MOVES!");
                resetMaze();
                moveCounter=0;
                continue;
            }

            // DETERMINE ACTION
            int action = q.getNextAction(getState());
            System.out.println("MOVING: "+getMoveName(action));
            goToNextState(action);
            moveCounter++;

            // REWARDS AND ADJUSTMENT OF WEIGHTS SHOULD TAKE PLACE HERE
            
            q.updateQvalue(GetReward(), state);
            q.printQtable();

            // COMMENT THE SLEEP FUNCTION IF YOU NEED FAST TRAINING WITHOUT
            // NEEDING TO ACTUALLY SEE IT PROGRESS
            //Thread.sleep(300);
        }
    }*/

	public MOVE newState(int[] st, int reward) {
		state = st;
		
		//Choose an action
		int action = q.getNextAction(getState());
        //System.out.println("MOVING: "+getMoveName(action));
        
        moveCounter++;

		q.updateQvalue(reward, getState());
        //q.printQtable();
        return QTable.ConvertIntToMove(action);
	}

	/*public void updateQ(int reward) {
		q.updateQvalue(reward, getState());
        q.printQtable();
		
	}*/
	
	public void reset(){
		q.reset();
	}
	
	public QLearning(){ 
		q = new QTable();
	}
}
