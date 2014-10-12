package pacman.ai_structures.neural;

import pacman.ai_structures.data_management.dir_detect.DirectionDetect;
import pacman.ai_structures.data_management.way_detect.WayDetect;
import pacman.controllers.Controller;
import pacman.game.Constants.MOVE;
import pacman.game.Game;

public class NeuralWDController extends Controller<MOVE> {

	Neural Brain;
	
	double certaintyScale;
	MOVE prevMove;
	double prevCertainty;
	
	public NeuralWDController(Neural neural, double certain){
		Brain = neural;
		certaintyScale = certain;
	}
	
	@Override
	public MOVE getMove(Game game, long timeDue) {
		MOVE[] moves = game.getPossibleMoves(game.getPacmanCurrentNodeIndex());
		WayDetect[] wds = WayDetect.getWayDetect(game, MOVE.NEUTRAL);
		double[] probabilities = new double[moves.length];
		for(int i = 0; i<wds.length; i++){
			probabilities[i] = Brain.sample(NeuralWDTrainer.CreateNDP(wds[i]))[0];
			//PrintProb(probabilities, moves);
			
		}
		//System.out.println(Certainty(probabilities));
		MOVE m = GetMoveFromProb(probabilities, moves);
		prevMove = m;
		prevCertainty = Certainty(probabilities);
		return m;
	}
	
	private double Certainty(double[] p) {
		int highest = getHighest(p);
		double combinedOthers = 0.0;
		double highVal = 0;
		for(int i = 0; i<p.length;i++){
			if(i != highest) combinedOthers += p[i];
			else highVal = p[i];
			
		}
		
		return highVal/combinedOthers;
		
	}
	
	private int getHighest(double[] arr){
		int highest = -1;
		double highVal = 0.0;
		for(int i = 0; i<arr.length;i++){
			if(highVal < arr[i]){
				highVal = arr[i];
				highest = i;
			}
		}
		return highest;
	}

	private void PrintProb(double[] p, MOVE[] ms) {
		StringBuilder strB = new StringBuilder();
		for(int i = 0; i<ms.length; i++){
			strB.append(ms[i].toString());
			strB.append(": ");
			strB.append(p[i] + "\n");
		}
		
		System.out.print(strB.toString());
	}

	private MOVE GetMoveFromProb(double[] p, MOVE[] ms){
		
		int highest = getHighest(p);
		if(Certainty(p)*certaintyScale < prevCertainty) return prevMove;
		return ms[highest];
		
	}
	

}
