package pacman.ai_structures.neural;

import java.util.ArrayList;
import java.util.Collections;

import pacman.Executor;
import pacman.ai_structures.data_management.dir_detect.DDCollectorController;
import pacman.ai_structures.data_management.dir_detect.DirDetectSaverLoader;
import pacman.ai_structures.data_management.dir_detect.DirectionDetect;
import pacman.controllers.KeyBoardInput;
import pacman.controllers.examples.Legacy;
import pacman.game.Constants.MOVE;

public class NeuralDDTrainer {
	public static void main(String[] args){

		NeuralDDTrainer n = new NeuralDDTrainer();
		ArrayList<NDataPoint> PacManTrainingData = new ArrayList<NDataPoint>();
		PacManTrainingData = n.generateTrainingData(DirDetectSaverLoader.LoadPacManData());
		//Randomize the data
		Collections.shuffle(PacManTrainingData);
		
		Neural neural = new Neural(0.1);
		neural.TrainNeural(PacManTrainingData, 5);
		

		Executor exec=new Executor();

		boolean visual=true;

		exec.runGameTimed(new NeuralDDController(neural, 1.5), new Legacy(), visual);
	}

	private ArrayList<NDataPoint> generateTrainingData(DirectionDetect[] pmData) {
		ArrayList<NDataPoint> returnList = new ArrayList<NDataPoint>();
		for(int i = 0; i<pmData.length;i++){
			returnList.add(CreateNDP(pmData[i]));
		}
		return returnList;
	}

	public static NDataPoint CreateNDP(DirectionDetect dd) {
		int inputSize = 16;//dd.enemies.size()+dd.prey.size()+dd.pellets.size()+dd.sPellets.size(); //should be 16
		double[] input = new double[inputSize];
		//pellet
		input[0] = Normalize(CheckWall(dd.pellets.get(MOVE.UP)));
		input[1] = Normalize(CheckWall(dd.pellets.get(MOVE.DOWN)));
		input[2] = Normalize(CheckWall(dd.pellets.get(MOVE.LEFT)));
		input[3] = Normalize(CheckWall(dd.pellets.get(MOVE.RIGHT)));
		//s pellet
		input[4] = Normalize(CheckWall(dd.sPellets.get(MOVE.UP)));
		input[5] = Normalize(CheckWall(dd.sPellets.get(MOVE.DOWN)));
		input[6] = Normalize(CheckWall(dd.sPellets.get(MOVE.LEFT)));
		input[7] = Normalize(CheckWall(dd.sPellets.get(MOVE.RIGHT)));
		//enemy
		input[8] = Normalize(CheckWall(dd.enemies.get(MOVE.UP)));
		input[9] = Normalize(CheckWall(dd.enemies.get(MOVE.DOWN)));
		input[10] = Normalize(CheckWall(dd.enemies.get(MOVE.LEFT)));
		input[11] = Normalize(CheckWall(dd.enemies.get(MOVE.RIGHT)));
		//prey
		input[12] = Normalize(CheckWall(dd.prey.get(MOVE.UP)));
		input[13] = Normalize(CheckWall(dd.prey.get(MOVE.DOWN)));
		input[14] = Normalize(CheckWall(dd.prey.get(MOVE.LEFT)));
		input[15] = Normalize(CheckWall(dd.prey.get(MOVE.RIGHT)));
		
		
		int outputSize = 4;
		int[] output = new int[outputSize];
		switch(dd.chosenMove){
		case UP:
			output = new int[]{1,0,0,0};
			break;
		case DOWN:
			output = new int[]{0,1,0,0};
			break;
		case LEFT:
			output = new int[]{0,0,1,0};
			break;
		case RIGHT:
			output = new int[]{0,0,0,1};
			break;
		case NEUTRAL:
			output = new int[]{0,0,0,0};
			break;
		}
		return new NDataPoint(input, output);
		
	}

	private static Integer CheckWall(Integer i) {
		if(i == null) return DirectionDetect.MAX;
		return i;
	}

	private static double Normalize(Integer i) {
		return (i.doubleValue() / 1000.0);
		
	}
	
}
