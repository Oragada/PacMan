package pacman.ai_structures.behaviour_tree;

import pacman.ai_structures.behaviour_tree.abst.*;

public class Sequencer extends Branch {

	@Override
	public boolean Execute() {
		for(Component c : elements){
			boolean r = c.Execute();
			if(!r){
				return false;
			}
		}
		return true;
	}
	
}
