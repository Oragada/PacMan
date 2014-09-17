package pacman.ai_structures.neural;

public class Connection {
	Node mouth;
	Node bottom;
	
	double weight;
	
	Node getOtherEnd(Node node){
		if(node == mouth) return bottom;
		if(node == bottom) return mouth;
		return null;
	}
	
	void initialize(Node m, Node b, double w){
		mouth = m;
		bottom = b;
		weight = (Neural.RAND.nextDouble()*2)-1; //Random weight between -1 and 1
	}

}
