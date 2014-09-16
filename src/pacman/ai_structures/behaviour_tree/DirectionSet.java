package pacman.ai_structures.behaviour_tree;

import pacman.ai_structures.behaviour_tree.abst.*;
import pacman.game.Constants.MOVE;
import pacman.entries.pacman.*;

public class DirectionSet extends Leaf {

	MOVE direction;
	EvolvedBehaviourPacman controller;
	
	public DirectionSet(MOVE dir, EvolvedBehaviourPacman ebp){
		direction = dir;
		controller = ebp;
	}
	
	@Override
	public boolean Execute() {
		controller.setDirection(direction);
		// TODO Auto-generated method stub
		return true;
	}

}
