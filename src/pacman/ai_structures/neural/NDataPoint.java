package pacman.ai_structures.neural;

public class NDataPoint {
	
	public int[] output;
	
	public double[] input;

	public NDataPoint(double[] inputData, int[] outputData) {
		output = outputData;
		input = inputData;
	}

	public double getInput(int j) {
		return input[j];
	}

	public double getTargetOutput(int j) {
		return output[j];
	}

}
