package pacman.ai_structures.behaviour_tree.abst;

import pacman.*;
import pacman.ai_structures.behaviour_tree.BTreeGame;
import pacman.game.Game;

public abstract class Component {
	public abstract boolean Execute(BTreeGame game);
}
