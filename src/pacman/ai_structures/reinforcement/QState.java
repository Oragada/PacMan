package pacman.ai_structures.reinforcement;

public class QState {
	
	private final int[] data;
	
	QState(int[] d){
		data = d;
	}

	public boolean equals(Object o) {
		if(o.getClass() != this.getClass()) return false;
		if(((QState)o).data.length != data.length) return false; 
		for(int i = 0; i<data.length; i++){
			if(((QState)o).data[i] != data[i]) return false;
		}
		return true;
	}
	
	public int hashCode(){
		int init = 0;
		for(int i = 0; i<data.length;i++){
			init += data[i]*31+(data.length-i);
		}
		return init;
		
	}
	
	public int get(int i){
		if(i < 0 || i >= data.length) return -1;
		return data[i];
	}
	
}
