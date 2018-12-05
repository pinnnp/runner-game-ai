package ai;

import java.util.ArrayList;
import java.util.Random;

import game.Runner;

public class Genome {
	private ArrayList<Double> genome;
	private int action = 0;
	public Genome() {
		genome = new ArrayList<Double>(5);
	}
	
	public void setGenome(int index, double value) {
		genome.set(index, value);
	}
	
	public void setAction(int n) {
		action = n;
	}
	
	public int act() {
		return this.action;
	}
	
	public double get(int i) {
		return this.genome.get(i);
	}
	
	public void mutates() {
		Random random = new Random();
		int a = random.nextInt(3);
		while (a == act()) a = random.nextInt(3);
		setAction(a);
	}
	
	private static double getRandomNumberInRange(double min, double max) {
		if (min >= max) {
			throw new IllegalArgumentException("max must be greater than min");
		}
		return (double) Math.random() * max + min;
	}
}
