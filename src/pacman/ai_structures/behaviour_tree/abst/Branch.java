package pacman.ai_structures.behaviour_tree.abst;

import java.util.ArrayList;
import java.util.ListIterator;

public abstract class Branch extends Component {
	protected ArrayList<Component> elements = new ArrayList<Component>();
	
	public void Append(Component c){
		elements.add(c);
	}
	
	public void AddToStart(Component c){
		elements.add(0, c);
	}
	
	public ListIterator<Component> Iterate(){
		return elements.listIterator();
	}
}
