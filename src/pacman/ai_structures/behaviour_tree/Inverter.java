package pacman.ai_structures.behaviour_tree;

import pacman.ai_structures.behaviour_tree.abst.*;

public class Inverter extends Decorator {

	@Override
	public boolean Execute() {
		return !Decorated.Execute();
	}

}
