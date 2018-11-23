package ai;

import java.util.ArrayList;
import java.util.Random;

import game.Runner;

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
	
	public int act() {
		return this.action;
	}
	
	public double get(int i) {
		return this.genome.get(i);
	}
	
	public void mutates() {
		Random r = new Random();
		int id = r.nextInt(genome.size());
		while (id == 2 || id == 3) id = r.nextInt(genome.size());
		if (id == 0 || id == 1) {
			double speed = getRandomNumberInRange(10.0, 500.0);
			genome.set(0, speed);
			genome.set(1, speed + 10);
		}
		else if (id == 4 || id == 5) {
			double hole = r.nextBoolean() ? 20.0 : 0.0;
			int height = 10 + r.nextInt(120);
			double rHeight = Runner.HEIGHT - height - 120 - hole;
			genome.set(4, rHeight);
			genome.set(5, rHeight - 10);
		}
		else if (id == 6 || id == 7) {
			int height = 10 + r.nextInt(120);
			genome.set(6, (double) height);
			genome.set(7, (double) (height + 10));
		}
	}
	
	private static double getRandomNumberInRange(double min, double max) {
		if (min >= max) {
			throw new IllegalArgumentException("max must be greater than min");
		}
		return (double) Math.random() * max + min;
	}
}
