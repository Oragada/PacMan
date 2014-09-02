package pacman.ai_structures.behaviour_tree;

import pacman.ai_structures.behaviour_tree.abst.*;

public class Selector extends Branch {

	@Override
	public boolean Execute() {
		for(Component c : elements){
			boolean r = c.Execute();
			if(r){
				return true;
			}
		}
		return false;
	}

}
