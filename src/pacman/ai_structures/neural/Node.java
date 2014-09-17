package pacman.ai_structures.neural;

import java.util.ArrayList;

public class Node {
	
	double bias;
	ArrayList<Connection> connections;
	
	double output;
	
	public void initialize(){
		connections = new ArrayList<Connection>();
		bias = (Neural.RAND.nextDouble()*2)-1;
	}
	
	public void activate(ArrayList<Double> weightedConns){
		double summedValue = 0.0;
		for(Double conn : weightedConns){
			summedValue += conn;
		}
		summedValue += bias;
		
		output = activationFunction(summedValue);
	}
	
	double activationFunction(double input){
		return 1/(1+Math.pow(Math.E, input*-1));
	}
	
	void setConnection(Connection conn){
		connections.add(conn);
	}

}
