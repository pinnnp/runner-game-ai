package ai;

import java.util.ArrayList;

public class Genome {
	private ArrayList<Double> genome;
	private int action = 0;
	public Genome() {
		genome = new ArrayList<Double>();
	}
	
	public void setGenome(int index, double value) {
		genome.set(index, value);
	}
	
	public void setAction(int n) {
		action = n;
	}
}
