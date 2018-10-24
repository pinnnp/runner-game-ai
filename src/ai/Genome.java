package ai;

import java.util.ArrayList;

public class Genome implements Comparable<Object>{
	private ArrayList<Double> genome = new ArrayList<Double>(); //Contains speed, x from nearest obstacle, y of obstacle from ground, obstacle height
	private double fitness;
	
	public void setGenome(ArrayList<Double> genome) {
		this.genome = genome;
	}

	public Genome() {
		for (int i = 0; i < 4; i++) {
			this.genome.add(Math.random());
		}
	}
	
	public Genome(ArrayList<Integer> indexOfGenes, ArrayList<Double> oldGenome) { //Two parameters must have the same length
		for (int i = 0; i < 4; i++) {
			this.genome.add(Math.random());
		}
		
		for (int i  = 0; i < indexOfGenes.size(); i++) {
			this.genome.set(indexOfGenes.get(i), oldGenome.get(i));
		}
	}
	

	public ArrayList<Double> getGenome() {
		return genome;
	}
	
	public void setFitness(double d) {
		fitness = d;
	}
	
	public double getFitness() {
		return fitness;
	}
	
	public int compareTo(Object o) { //From min to max 
		Genome other = (Genome) o;
		int result;
		if (this.fitness < other.fitness) {
			result = -1;		
		}else if (this.fitness == other.fitness) {
			result = 0;
		}else {
			result = 1;
		}
		return result;
	}
	
	public double forward(ArrayList<Double> inputs) {
		double output = 0;
		for (int i = 0; i < inputs.size(); i++) {
			output += inputs.get(i) * this.genome.get(i);
		}
		output = sigmoid(output);
		return output;
	}
	public double sigmoid(double b) {
		return 1/(1 + Math.pow(Math.E, b));
	}
}
