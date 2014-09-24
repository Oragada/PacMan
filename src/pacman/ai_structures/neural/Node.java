package pacman.ai_structures.neural;

import java.util.ArrayList;

public class Node {
	
	double bias;
	ArrayList<Connection> feedingTube;
	ArrayList<Connection> garbageTube;
	
	double output;
	
	double error;
	
	public Node(){
		initialize();
	}
	
	public void initialize(){
		feedingTube = new ArrayList<Connection>();
		garbageTube = new ArrayList<Connection>();
		bias = (Neural.RAND.nextDouble()*2)-1;
	}
	
	public void activate(){
//		 * (8) 	sum of weighted input + bias; //compute the net input of unit j with respect to the previous layer, i
		double summedValue = 0.0;
		for(Connection c : feedingTube){
			Node otherN = c.getOtherEnd(this);
			summedValue = otherN.GetOutput()*c.weight;
		}
		summedValue += bias;
//		 * (9) 	apply activation function to result; // compute the output of each unit j
		output = activationFunction(summedValue);
	}
	
	double activationFunction(double input){
		return 1/(1+Math.pow(Math.E, input*-1));
	}
	
	void setConnection(Connection conn, boolean feedThis){
		if(feedThis) feedingTube.add(conn);
		garbageTube.add(conn);
	}

	public void Input(double input) {
//		 * (6) 	Oj = Ij; // output of an input unit is its actual input value
		output = input;
		
	}

	public void setError(double targetDist) {
//		 * (12) [ErrorJ = Oj*(1-Oj)*](Tj-Oj); // compute the error for OUTPUT
//		 * (14) [ErrorJ = Oj*(1-Oj)*]sum of weighted error from higher layer; // compute the error for HIDDEN with respect to the next higher layer, k
		error = output*(1-output)*targetDist;
		
	}

	public void UpdateBias() {
//		 * (19) DeltaBiasJ = (learn)*ErrorJ ; // bias increment
		double deltaBias = Neural.LEARN*error;
//		 * (20) BiasJ = BiasJ + DeltaBiasJ; // bias update
		bias += deltaBias;
		
	}

	public double SumErrors() {
//		 * (14) ErrorJ = Oj*(1-Oj)*[sum of weighted error from higher layer]; // compute the error with respect to the next higher layer, k
		double totalE = 0;
		for (Connection c : garbageTube){
			Node otherN = c.getOtherEnd(this);
			totalE = otherN.GetError()*c.weight;
		}
		return totalE;
	}

	public double GetError() {
		return error;
	}

	public double GetOutput() {
		return output;
	}

}
