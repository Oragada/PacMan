package pacman.ai_structures.data_management.way_detect;

import pacman.controllers.HumanController;
import pacman.controllers.KeyBoardInput;
import pacman.game.Game;
import pacman.game.Constants.MOVE;

public class WDCollectorController extends HumanController{
	
	Game prevTick;
	
	public WDCollectorController(KeyBoardInput input){
		super(input);
	}
	
	@Override
	public MOVE getMove(Game game, long dueTime) {
		MOVE move = super.getMove(game, dueTime);
		
		if(prevTick != null){
			WayDetect[] data = WayDetect.getWayDetect(prevTick, game.getPacmanLastMoveMade());
			
			WayDetectSaverLoader.SavePacManData(data);	
		}
		prevTick = game;
		return move;
	}

	private void print(String s) {
		System.out.println(s);
		
	}
}
