package pacman.ai_structures.neural;

import pacman.ai_structures.data_management.DirectionDetect;
import pacman.controllers.Controller;
import pacman.game.Constants.MOVE;
import pacman.game.Game;

public class NeuralDDController extends Controller<MOVE> {

	Neural Brain;
	
	double certaintyScale;
	MOVE prevMove;
	double prevCertainty;
	
	public NeuralDDController(Neural neural, double certain){
		Brain = neural;
		certaintyScale = certain;
	}
	
	@Override
	public MOVE getMove(Game game, long timeDue) {
		double[] probabilities = Brain.sample(NeuralDDTrainer.CreateNDP(DirectionDetect.getDirDetect(game, MOVE.NEUTRAL)));
		//PrintProb(probabilities);
		System.out.println(Certainty(probabilities));
		MOVE m = GetMoveFromProb(probabilities);
		prevMove = m;
		prevCertainty = Certainty(probabilities);
		return m;
	}
	
	private double Certainty(double[] p) {
		int highest = -1;
		double highVal = 0.0;
		for(int i = 0; i<p.length;i++){
			if(highVal < p[i]){
				highVal = p[i];
				highest = i;
			}
		}
		double combinedOthers = 0.0;
		for(int i = 0; i<p.length;i++){
			if(i != highest) combinedOthers += p[i];
		}
		
		return highVal/combinedOthers;
		
	}

	private void PrintProb(double[] p) {
		StringBuilder strB = new StringBuilder();
		strB.append("UP:    ");
		strB.append(p[0] + "\n");
		strB.append("DOWN:  ");
		strB.append(p[1] + "\n");
		strB.append("LEFT:  ");
		strB.append(p[2] + "\n");
		strB.append("RIGHT: ");
		strB.append(p[3] + "\n");
		strB.append("\n");
		
		System.out.print(strB.toString());
	}

	private MOVE GetMoveFromProb(double[] p){

		int highest = -1;
		double highVal = 0.0;
		for(int i = 0; i<p.length;i++){
			if(highVal < p[i]){
				highVal = p[i];
				highest = i;
			}
		}
		if(Certainty(p)*certaintyScale < prevCertainty) return prevMove;
		switch(highest){
		case 0: return MOVE.UP;
		case 1: return MOVE.DOWN;
		case 2: return MOVE.LEFT;
		case 3: return MOVE.RIGHT;
		default: return MOVE.NEUTRAL;
		}
		
	}
	

}
