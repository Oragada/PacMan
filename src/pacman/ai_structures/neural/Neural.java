package pacman.ai_structures.neural;

import java.util.ArrayList;
import java.util.Random;

public class Neural {
	
	public static Random RAND = new Random(); 
	public static double LEARN = 0.05;
	
	boolean keepRunning = true;
	
	ArrayList<Node> input;
	ArrayList<Node> hidden;
	ArrayList<Node> output;
	ArrayList<Connection> connections;
	
	void RunNeural(ArrayList<DataPoint> dataset, int hiddenN, int out){
		
//		 * (1) 	Initialize all weights and biases in network;
		InitializeNetwork(dataset.size(), hiddenN, out);
//		 * (2) 	while terminating condition is not satisfied
		while(keepRunning){
//		 	 * (3) 	for each training tuple X in D
			for(DataPoint x : dataset){
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
		}
	}

	private void InitializeNetwork(int in, int hiddenN, int out) {
		//Generate input layer
		input = new ArrayList<Node>();
		for(int i = 0; i<in; i++){
			input.add(new Node());
		}
		//Generate hidden layer
		hidden = new ArrayList<Node>();
		for(int i = 0; i<hiddenN; i++){
			hidden.add(new Node());
		}
		//Generate output layer
		output = new ArrayList<Node>();
		for(int i = 0; i<out; i++){
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
}
