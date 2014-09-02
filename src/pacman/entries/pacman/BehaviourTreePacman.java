package pacman.entries.pacman;

import pacman.ai_structures.behaviour_tree.*;
import pacman.controllers.Controller;
import pacman.game.Constants.MOVE;
import pacman.game.Game;

public class BehaviourTreePacman extends Controller<MOVE> {

	//Behaviour Tree
	private Sequencer bTree = new Sequencer();
	
	@Override
	public MOVE getMove(Game game, long timeDue) {
		// TODO Auto-generated method stub
		return MOVE.NEUTRAL;
	}
	
}
