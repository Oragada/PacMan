package pacman.ai_structures.behaviour_tree;

import pacman.ai_structures.behaviour_tree.abst.*;
import pacman.game.Game;

public class Sequencer extends Branch {

	@Override
	public boolean Execute(BTreeGame game) {
		for(Component c : elements){
			boolean r = c.Execute(game);
			if(!r){
				return false;
			}
		}
		return true;
	}
	
}
