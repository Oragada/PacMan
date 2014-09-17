package pacman.ai_structures.behaviour_tree;

import pacman.ai_structures.behaviour_tree.abst.*;
import pacman.game.Game;

public class Inverter extends Decorator {

	@Override
	public boolean Execute(BTreeGame game) {
		return !Decorated.Execute(game);
	}

}
