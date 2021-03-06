package pacman.ai_structures.neural;

public class Connection {
	Node mouth;
	Node bottom;
	
	double weight;
	
	public Connection(Node mouth, Node bottom){
		this.mouth = mouth;
		this.bottom = bottom;
		initialize(mouth, bottom);
	}
	
	Node getOtherEnd(Node node){
		if(node == mouth) return bottom;
		if(node == bottom) return mouth;
		return null;
	}
	
	void initialize(Node m, Node b){
		mouth = m;
		bottom = b;
		weight = (Neural.RAND.nextDouble()*2)-1; //Random weight between -1 and 1
	}

	public void UpdateWeight(double learn) {
//		 * (16) Delta-wij = (learn)*ErrorJ*Oi; // weight increment
		double deltaW = learn*bottom.GetError()*mouth.GetOutput();
//		 * (17) wij = wij + Delta-wij ; // weight update
		weight += deltaW;
	}

}
