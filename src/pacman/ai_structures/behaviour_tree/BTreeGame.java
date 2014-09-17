package pacman.ai_structures.behaviour_tree;

import pacman.game.Constants.MOVE;
import pacman.game.Game;

public class BTreeGame {
	Game game;
	MOVE currentMove;
	
	BTreeGame(Game game){
		this.game = game;
		currentMove = MOVE.NEUTRAL;
	}
	
	void setCurrMove(MOVE newMove){
		currentMove = newMove;
	}
	
	MOVE getMove(){
		MOVE temp = currentMove;
		currentMove = MOVE.NEUTRAL;
		return temp;
	}

}
