package pacman.ai_structures.reinforcement;

import pacman.Executor;
import pacman.ai_structures.data_management.dir_detect.DirectionDetect;
import pacman.controllers.Controller;
import pacman.controllers.examples.AggressiveGhosts;
import pacman.controllers.examples.StarterGhosts;
import pacman.game.Constants.GHOST;
import pacman.game.Constants.MOVE;
import pacman.game.Game;

public class QLearnDDController extends Controller<MOVE> {

	public QLearning qlearn;
	
	public QLearnDDController(){
		qlearn = new QLearning();
	}
	
	public QLearnDDController(QLearning ql){
		qlearn = ql;
	}
	
	public static void main(String[] args){
		Executor exec = new Executor();
		
		QLearning ql = new QLearning();
		
		exec.runExperiment(new QLearnDDController(ql), new AggressiveGhosts(), 1000);
		/*for(int i = 0; i < 1000; i++){
			System.out.println(i);
			exec.runExperiment(new QLearnDDController(ql), new StarterGhosts(), 1);
			//exec.runGameTimed(new QLearnDDController(ql), new StarterGhosts(), true);
			//ql.SetExplor(i);
		}*/
		exec.runGameTimed(new QLearnDDController(ql), new AggressiveGhosts(), true);
	}
	
	@Override
	public MOVE getMove(Game game, long timeDue) {
		DirectionDetect dd = DirectionDetect.getDirDetect(game, MOVE.NEUTRAL);
		
		int[] state = ConvertDD(dd);
		
		MOVE m = qlearn.newState(state, CalculateReward(game));
		
		
		
		return m;
	}

	private int CalculateReward(Game game) {
		int reward = 0;
		if(game.wasPacManEaten()) reward -= 10000;
		for(GHOST g : GHOST.values()){
			if(game.wasGhostEaten(g)) reward += 4000;
		}
		if(game.wasPillEaten()) reward += 100;
		if(game.wasPowerPillEaten()) reward += 2000;
		return reward;
	}

	private int[] ConvertDD(DirectionDetect dd) {
		int[] state = new int[16];
		//Pellets
		state[0] = Discretize(dd.pellets.get(MOVE.UP));
		state[1] = Discretize(dd.pellets.get(MOVE.DOWN));
		state[2] = Discretize(dd.pellets.get(MOVE.LEFT));
		state[3] = Discretize(dd.pellets.get(MOVE.RIGHT));
		//Super Pellets
		state[4] = Discretize(dd.sPellets.get(MOVE.UP));
		state[5] = Discretize(dd.sPellets.get(MOVE.DOWN));
		state[6] = Discretize(dd.sPellets.get(MOVE.LEFT));
		state[7] = Discretize(dd.sPellets.get(MOVE.RIGHT));
		//Enemies
		state[8] = Discretize(dd.enemies.get(MOVE.UP));
		state[9] = Discretize(dd.enemies.get(MOVE.DOWN));
		state[10] = Discretize(dd.enemies.get(MOVE.LEFT));
		state[11] = Discretize(dd.enemies.get(MOVE.RIGHT));
		//Enemies
		state[12] = Discretize(dd.prey.get(MOVE.UP));
		state[13] = Discretize(dd.prey.get(MOVE.DOWN));
		state[14] = Discretize(dd.prey.get(MOVE.LEFT));
		state[15] = Discretize(dd.prey.get(MOVE.RIGHT));
		
		
		return state;
	}

	private static int Discretize(int i) {
		if(i == 1000) return -1;
		//if(i == 0) return 0;
		//return (int)Math.floor(Math.log((double)i));
		return i;
	}

}
