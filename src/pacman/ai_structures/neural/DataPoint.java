package pacman.ai_structures.neural;

public class DataPoint {
	
	double[] input;
	double[] output;

	public double getInput(int j) {
		return input[j];
	}

	public double getTargetOutput(int j) {
		return output[j];
	}

}
