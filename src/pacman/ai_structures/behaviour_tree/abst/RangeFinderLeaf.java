package pacman.ai_structures.behaviour_tree.abst;

import pacman.game.Constants.DM;
import pacman.game.Constants.MOVE;
import pacman.game.Game;

public abstract class RangeFinderLeaf extends Leaf{
	MOVE direction;
	int range;
	
	protected double FindClosestInDir(Game game, int[] targets){
		double closestDistance = Double.MAX_VALUE;
		
		for(int i = 0; i < targets.length; i++){
			MOVE dir = game.getNextMoveTowardsTarget(game.getPacmanCurrentNodeIndex(), targets[i], DM.PATH);
			if(dir == direction){
				double dist = game.getDistance(game.getPacmanCurrentNodeIndex(), targets[i], DM.PATH);
				if(dist < closestDistance){
					closestDistance = dist;
				}
			}
		}
		
		return closestDistance;
	}

}
