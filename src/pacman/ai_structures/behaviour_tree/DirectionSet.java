package pacman.ai_structures.behaviour_tree;

import pacman.ai_structures.behaviour_tree.abst.*;
import pacman.game.Constants.MOVE;
import pacman.game.Game;
import pacman.entries.pacman.*;

public class DirectionSet extends Leaf {

	MOVE direction;
	
	public DirectionSet(MOVE dir){
		direction = dir;
	}
	
	@Override
	public boolean Execute(BTreeGame game) {
		game.setCurrMove(direction);
		// TODO Auto-generated method stub
		return true;
	}

}
