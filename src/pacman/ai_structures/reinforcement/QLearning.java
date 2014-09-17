package pacman.ai_structures.reinforcement;

import pacman.controllers.Controller;
import pacman.game.Constants.MOVE;
import pacman.game.Game;

public class QLearning extends Controller<MOVE> {
	
	QTable qTable;
	
	
	int GetReward(Game game){
		//TODO
		return 0;
		
	}
	
	MOVE LookupMove(Game game){
		//TODO
		return MOVE.NEUTRAL;
		
	}

	@Override
	public MOVE getMove(Game game, long timeDue) {
		// TODO Auto-generated method stub
		return null;
	}
	

}
