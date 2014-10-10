package pacman.ai_structures.data_management;

import pacman.ai_structures.dataRecording.DataSaverLoader;
import pacman.ai_structures.dataRecording.DataTuple;
import pacman.controllers.HumanController;
import pacman.controllers.KeyBoardInput;
import pacman.game.Game;
import pacman.game.Constants.MOVE;

public class DDCollectorController extends HumanController{
	
	public DDCollectorController(KeyBoardInput input){
		super(input);
	}
	
	@Override
	public MOVE getMove(Game game, long dueTime) {
		MOVE move = super.getMove(game, dueTime);
		
		DirectionDetect data = DirectionDetect.getDirDetect(game, move);
				
		DirDetectSaverLoader.SavePacManData(data);		
		return move;
	}
}
