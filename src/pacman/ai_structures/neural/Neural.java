package pacman.ai_structures.neural;

import java.util.ArrayList;
import java.util.Random;

import pacman.ai_structures.data_management.DirectionDetect;

public class Neural {
	
	public static Random RAND = new Random(); 
	public static double LEARN = 0.1;
	
	boolean keepRunning = true;
	
	ArrayList<Node> input;
	ArrayList<Node> hidden;
	ArrayList<Node> output;
	ArrayList<Connection> connections;
	
	public static void main(String[] args){
		ArrayList<NDataPoint> xor = new ArrayList<NDataPoint>();
		xor.add(new NDataPoint(new double[]{0.0,0.0}, new int[]{0}));
		xor.add(new NDataPoint(new double[]{1.0,0.0}, new int[]{1}));
		xor.add(new NDataPoint(new double[]{0.0,1.0}, new int[]{1}));
		xor.add(new NDataPoint(new double[]{1.0,1.0}, new int[]{0}));
		
		Neural n = new Neural();
		n.TrainNeural(xor, 2);
	}
	
	void TrainNeural(ArrayList<NDataPoint> dataset, int hiddenN){
		
//		 * (1) 	Initialize all weights and biases in network;
		InitializeNetwork(dataset, hiddenN);
		//Count the number of tuples we have been through
		int t = 0;
//		 * (2) 	while terminating condition is not satisfied
		while(keepRunning){
			t++;
			double accMax = 0;
			double accActual = 0;
			//LEARN = 1.0/t;
			System.out.println("LEARN: " + LEARN + " - T: " + t);
//		 	 * (3) 	for each training tuple X in D
			for(NDataPoint x : dataset){
//		 		 * (4) 	// Propagate the inputs forward:
//		 		 * (5) 	for each input layer unit j
				for(int j = 0; j < input.size(); j++){
//					 * (6) 	Oj = Ij; // output of an input unit is its actual input value
					input.get(j).Input(x.getInput(j));
				}
//		 		 * (7) 	for each hidden or output layer unit j
				for(int j = 0; j< hidden.size(); j++){
//					 * (8) 	sum of weighted input + bias; //compute the net input of unit j with respect to the previous layer, i
//					 * (9) 	apply activation function to result; // compute the output of each unit j
					hidden.get(j).activate();
				}
				for(int j = 0; j<output.size(); j++){
					output.get(j).activate();
				}
				
				//Check whether its working
				if(CheckOutputPac(x)){
					accActual++;
				}
				accMax++;
				
				
				//System.out.println("Still Running");
				//Print output
				//PrintOutput(x);
				
//				 * (10) // Backpropagate the errors:
//				 * (11) for each unit j in the output layer
				for(int j = 0; j<output.size(); j++){
//					 * (12) ErrorJ = Oj*(1-Oj)*(Tj-Oj); // compute the error
					output.get(j).setError(x.getTargetOutput(j)-output.get(j).GetOutput());
				}
//				 * (13) for each unit j in the hidden layers, from the last to the first hidden layer
				for(int j = 0; j<output.size(); j++){
//					 * (14) ErrorJ = Oj*(1-Oj)*sum of weighted error from higher layer; // compute the error with respect to the next higher layer, k
					hidden.get(j).setError(hidden.get(j).SumErrors());
				}
//				 * (15) for each weight wij in network
				for(Connection c : connections){
//					 * (16) Delta-wij = (learn)*ErrorJ*Oi; // weight increment
//					 * (17) wij = wij + Delta-wij ; // weight update
					c.UpdateWeight();
				}
//				 * (18) for each bias BiasJ in network
				for(Node n : output){
//					 * (19) DeltaBiasJ = (learn)*ErrorJ ; // bias increment
//					 * (20) BiasJ = BiasJ + DeltaBiasJ; // bias update
					n.UpdateBias();
				}
				for(Node n : hidden){
					n.UpdateBias();
				}
			}

			//End running
			
			System.out.println("Accuracy: " + accActual/accMax);
			if(t > 250) break;
			
		}
	}

	private boolean CheckOutput(NDataPoint x) {
		boolean doesItHoldUp = true;
		for(int i = 0; i<output.size();i++){
			double target = (double) x.getTargetOutput(i);
			double actual = Math.round(output.get(i).GetOutput());
			boolean thisOne = true;
			if(Math.abs(target - actual) > 0.001){
				thisOne = false;
				doesItHoldUp = doesItHoldUp && thisOne;
			}
		}
		
		
		return doesItHoldUp;
	}
	
	private boolean CheckOutputPac(NDataPoint x){

		int highest = -1;
		double highVal = 0.0;
		int targetVal = -1;
		for(int i = 0; i<output.size();i++){
			if(x.getTargetOutput(i) == 1) targetVal = i;
			if(highVal < output.get(i).GetOutput()){
				highVal = output.get(i).GetOutput();
				highest = i;
			}
		}
		return highest == targetVal;
		
	}

	private void PrintOutput(NDataPoint expected) {
		for(int i = 0; i<output.size();i++){
			System.out.print("Expected: " + expected.getTargetOutput(i));
			System.out.println(" - Output: " + output.get(i).output);
		}
		
	}

	private void InitializeNetwork(ArrayList<NDataPoint> dataset, int hiddenN) {
		//Generate input layer
		input = new ArrayList<Node>();
		for(int i = 0; i<dataset.get(0).input.length; i++){
			input.add(new Node());
		}
		//Generate hidden layer
		hidden = new ArrayList<Node>();
		for(int i = 0; i<hiddenN; i++){
			hidden.add(new Node());
		}
		//Generate output layer
		output = new ArrayList<Node>();
		for(int i = 0; i<dataset.get(0).output.length; i++){
			output.add(new Node());
		}
		//Generate connections
		connections = new ArrayList<Connection>();
		for(Node inN : input){
			for(Node hN : hidden){
				Connection c = new Connection(inN, hN);
				connections.add(c);
				inN.setConnection(c, false);
				hN.setConnection(c, true);
			}
		}
		for(Node hN : hidden){
			for(Node outN : output){
				Connection c = new Connection(hN, outN);
				connections.add(c);
				hN.setConnection(c, false);
				outN.setConnection(c, true);
			}
		}
		
	}

	public double[] sample(NDataPoint x) {
//		 * (4) 	// Propagate the inputs forward:
//		 * (5) 	for each input layer unit j
		for(int j = 0; j < input.size(); j++){
//			 * (6) 	Oj = Ij; // output of an input unit is its actual input value
			input.get(j).Input(x.getInput(j));
		}
//		 * (7) 	for each hidden or output layer unit j
		for(int j = 0; j< hidden.size(); j++){
//		 * (8) 	sum of weighted input + bias; //compute the net input of unit j with respect to the previous layer, i
//		 * (9) 	apply activation function to result; // compute the output of each unit j
			hidden.get(j).activate();
		}
		for(int j = 0; j<output.size(); j++){
			output.get(j).activate();
		}
		
		double[] returnArr = new double[output.size()];
		for(int j = 0; j<returnArr.length;j++){
			returnArr[j] = output.get(j).output;
		}
		
		return returnArr;
					
	}
}
