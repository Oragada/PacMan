package pacman.ai_structures.neural;

import java.util.ArrayList;
import java.util.Collections;

import pacman.Executor;
import pacman.ai_structures.data_management.dir_detect.DDCollectorController;
import pacman.ai_structures.data_management.dir_detect.DirDetectSaverLoader;
import pacman.ai_structures.data_management.dir_detect.DirectionDetect;
import pacman.ai_structures.data_management.way_detect.WayDetect;
import pacman.ai_structures.data_management.way_detect.WayDetectSaverLoader;
import pacman.controllers.KeyBoardInput;
import pacman.controllers.examples.Legacy;
import pacman.game.Constants.MOVE;

public class NeuralWDTrainer {
	public static void main(String[] args){

		NeuralWDTrainer n = new NeuralWDTrainer();
		ArrayList<NDataPoint> PacManTrainingData = new ArrayList<NDataPoint>();
		PacManTrainingData = n.generateTrainingData(WayDetectSaverLoader.LoadPacManData());
		//Randomize the data
		Collections.shuffle(PacManTrainingData);
		
		

		Executor exec=new Executor();


		Collections.shuffle(PacManTrainingData);
		Neural neural = new Neural(0.3);
		neural.TrainNeural(PacManTrainingData, 5);
		exec.runExperiment(new NeuralWDController(neural, 3), new Legacy(), 20);
		Collections.shuffle(PacManTrainingData);
		neural = new Neural(0.3);
		neural.TrainNeural(PacManTrainingData, 5);
		exec.runExperiment(new NeuralWDController(neural, 3), new Legacy(), 20);
		Collections.shuffle(PacManTrainingData);
		neural = new Neural(0.3);
		neural.TrainNeural(PacManTrainingData, 5);
		exec.runExperiment(new NeuralWDController(neural, 3), new Legacy(), 20);
		Collections.shuffle(PacManTrainingData);
		neural = new Neural(0.3);
		neural.TrainNeural(PacManTrainingData, 5);
		exec.runExperiment(new NeuralWDController(neural, 3), new Legacy(), 20);
		Collections.shuffle(PacManTrainingData);
		neural = new Neural(0.3);
		neural.TrainNeural(PacManTrainingData, 5);
		exec.runExperiment(new NeuralWDController(neural, 3), new Legacy(), 20);

		boolean visual=true;
		exec.runGameTimed(new NeuralWDController(neural, 3), new Legacy(), visual);
	}
	
	/*public static void main(String[] args){ //FIRST PARAMETER SEARCH
		double[] lRates = new double[]{-1.0,0.02,0.05,0.1,0.15,0.2,0.3,0.5,0.75};
		double[] cScales = new double[]{0.0,0.25,0.5,1.0,1.5,2.0,2.5,3.0,3.5,4.0,5.0,6.0,7.0};
//		int[] hNodes = new int[]{2,3,5,7,10,15,22};
		int[] hNodes = new int[]{5};

		ArrayList<NDataPoint> PacManTrainingData = new ArrayList<NDataPoint>();
		PacManTrainingData = NeuralWDTrainer.generateTrainingData(WayDetectSaverLoader.LoadPacManData());

		//Randomize the data
		Collections.shuffle(PacManTrainingData);
		
		for(int l = 0; l<lRates.length;l++){
			for(int c = 0; c<cScales.length;c++){
				for(int h = 0; h<hNodes.length;h++){

					Neural neural = new Neural(lRates[l]);
					neural.TrainNeural(PacManTrainingData, hNodes[h]);
					Executor exec=new Executor();
					System.out.println("Learn: " + lRates[l] + " - Certainty: " + cScales[c]);
					exec.runExperiment(new NeuralWDController(neural, cScales[c]), new Legacy(), 10);
					//exec.runGameTimed(new NeuralWDController(neural, cScales[c]), new Legacy(), true);
				}
			}
		}
	}*/
	
	/*public static void main(String[] args){ //SECOND PARAMETER SEARCH
		double[] lRates = new double[]{0.05,0.1,0.15,0.2,0.3,0.5,0.75};
		double[] cScales = new double[]{2.0,2.5,3.0,3.5,4.0,5.0,6.0};
//		int[] hNodes = new int[]{2,3,5,7,10,15,22};
		int[] hNodes = new int[]{5};

		ArrayList<NDataPoint> PacManTrainingData = new ArrayList<NDataPoint>();
		PacManTrainingData = NeuralWDTrainer.generateTrainingData(WayDetectSaverLoader.LoadPacManData());

		//Randomize the data
		Collections.shuffle(PacManTrainingData);
		
		for(int l = 0; l<lRates.length;l++){
			for(int c = 0; c<cScales.length;c++){
				for(int h = 0; h<hNodes.length;h++){
					for(int i = 0; i<5;i++){
						Collections.shuffle(PacManTrainingData);

						Neural neural = new Neural(lRates[l]);
						neural.TrainNeural(PacManTrainingData, hNodes[h]);
						Executor exec=new Executor();
						System.out.println("Learn: " + lRates[l] + " - Certainty: " + cScales[c]);
						exec.runExperiment(new NeuralWDController(neural, cScales[c]), new Legacy(), 5);
						//exec.runGameTimed(new NeuralWDController(neural, cScales[c]), new Legacy(), true);
					}
				}
			}
		}
	}*/
	
	/*public static void main(String[] args){ //THIRD PARAMETER SEARCH
		double[] lRates = new double[]{0.3,0.4,0.5,0.6,0.7,0.8};
		double[] cScales = new double[]{3.0,3.5,4.0,4.5,5.0};
//		int[] hNodes = new int[]{2,3,5,7,10,15,22};
		int[] hNodes = new int[]{5};

		ArrayList<NDataPoint> PacManTrainingData = new ArrayList<NDataPoint>();
		PacManTrainingData = NeuralWDTrainer.generateTrainingData(WayDetectSaverLoader.LoadPacManData());

		//Randomize the data
		Collections.shuffle(PacManTrainingData);
		
		for(int l = 0; l<lRates.length;l++){
			for(int c = 0; c<cScales.length;c++){
				for(int h = 0; h<hNodes.length;h++){
					for(int i = 0; i<3;i++){
						Collections.shuffle(PacManTrainingData);

						Neural neural = new Neural(lRates[l]);
						neural.TrainNeural(PacManTrainingData, hNodes[h]);
						Executor exec=new Executor();
						System.out.println("Learn: " + lRates[l] + " - Certainty: " + cScales[c]);
						exec.runExperiment(new NeuralWDController(neural, cScales[c]), new Legacy(), 10);
						//exec.runGameTimed(new NeuralWDController(neural, cScales[c]), new Legacy(), true);
					}
				}
			}
		}
	}*/

	private static ArrayList<NDataPoint> generateTrainingData(WayDetect[] pmData) {
		ArrayList<NDataPoint> returnList = new ArrayList<NDataPoint>();
		//int prey = 0;
		//int nonprey = 0;
		for(int i = 0; i<pmData.length;i++){
			NDataPoint ndp = CreateNDP(pmData[i]);
			//if(pmData[i].prey != (double)WayDetect.MAX) returnList.add(ndp);
			returnList.add(ndp);
		}
		
		//System.out.println("Prey: " + prey+", Non-prey: " + nonprey);
		return returnList;
	}

	/*private ArrayList<NDataPoint> generateTrainingData(DirectionDetect[] pmData) {
		ArrayList<NDataPoint> returnList = new ArrayList<NDataPoint>();
		for(int i = 0; i<pmData.length;i++){
			returnList.add(CreateNDP(pmData[i]));
		}
		return returnList;
	}*/

	public static NDataPoint CreateNDP(WayDetect wd) {
		double[] input = new double[4];
		input[0] = Normalize(wd.pellet);
		input[1] = Normalize(wd.spellet);
		input[2] = Normalize(wd.enemy);
		input[3] = Normalize(wd.prey);
		int[] output = new int[]{(wd.chosen?1:0)};
		// TODO Auto-generated method stub
		return new NDataPoint(input, output);
	}

	/*public static NDataPoint CreateNDP(DirectionDetect dd) {
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
		
	}*/

	private static double Normalize(Integer i) {
		return (i.doubleValue() / (double)WayDetect.MAX);
		
	}
	
}
