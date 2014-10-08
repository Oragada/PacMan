package pacman.entries.pacman;

import pacman.ai_structures.behaviour_tree.*;
import pacman.controllers.Controller;
import pacman.game.Constants.MOVE;
import pacman.game.Game;

public class EvolvedBehaviourPacman extends Controller<MOVE> {

	//Behaviour Tree
	private Sequencer bTree = new Sequencer();
	
	//Current Move direction dictacted by the behaviourTree
	private MOVE direction;
	
	@Override
	public MOVE getMove(Game game, long timeDue) {
		// TODO Auto-generated method stub
		if(direction != null){
			return direction;
		}
		return MOVE.NEUTRAL;
	}
	
	public void setDirection(MOVE dir){
		direction = dir;
	}
	
}
