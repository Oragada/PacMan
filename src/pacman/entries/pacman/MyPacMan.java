package pacman.entries.pacman;

import pacman.controllers.Controller;
import pacman.game.Constants.DM;
import pacman.game.Constants.MOVE;
import pacman.game.Game;

/*
 * This is the class you need to modify for your entry. In particular, you need to
 * fill in the getAction() method. Any additional classes you write should either
 * be placed in this package or sub-packages (e.g., game.entries.pacman.mypackage).
 */
public class MyPacMan extends Controller<MOVE>
{
	
	
	private MOVE myMove=MOVE.NEUTRAL;
	
	public MOVE getMove(Game game, long timeDue) 
	{
		//Decision Tree
		if(IsEnemyNearby())
		{
			if(IsSPNearby()){
				myMove = GoSP();
			}
			else
			{
				myMove = Flee();
			}
		}
		else
		{
			if(IsPreyNearby(game)){
				myMove = Hunt();
			}
			else{
				myMove = Eat(game);
			}
		}
		
		//Place your game logic here to play the game as Ms Pac-Man
		
		return myMove;
	}
	
	public boolean IsEnemyNearby(){
		return false;
	}
	
	public boolean IsPreyNearby(Game game){
		//GHOST..getValues();
		return false;
	}
	public boolean IsSPNearby(){
		return false;
	}
	public MOVE GoSP(){
		return MOVE.NEUTRAL;
	}
	public MOVE Flee(){
		return MOVE.NEUTRAL;
	}
	public MOVE Hunt(){
		return MOVE.NEUTRAL;
	}
	public MOVE Eat(Game game){
		int[] pillsHere = game.getActivePillsIndices();
		int closest = -1;
		double closestDist = 0;
		for(int pillI : pillsHere){
			double dist = game.getDistance(game.getPacmanCurrentNodeIndex(), pillI, DM.PATH);
			if(closest == -1 || dist < closestDist){
				closest = pillI;
				closestDist = dist;
			}
		}
		
		return game.getNextMoveTowardsTarget(game.getPacmanCurrentNodeIndex(), closest, DM.PATH);
		
		//return MOVE.NEUTRAL;
	}
}