package ai;

import java.util.ArrayList;
import java.util.Random;

import game.Runner;

public class Chromosome implements Comparable<Object>{
	private ArrayList<Genome> chromosome;
	private int fitness = 0;
	private int mSize;
	public Chromosome() {
		mSize = 0;
		//INITIALIZE SPEED [0-500]
		
		Random random = new Random(); 
		
		//DETERMINE RANGE OF X
		for (int x = 0; x < Runner.WIDTH; x += 50) {
			Genome g = new Genome();
			int a = random.nextInt(3);
			g.setAction(a);
			//ADD SPEED TO INDEX 0 and SPEED + 10 to 1 of Genome "g"
			double speed = getRandomNumberInRange(10.0, 500.0);
			g.setGenome(0, speed);
			g.setGenome(1, speed + 10); //FIND BEST INCREMENT rather than 10
			
			//ADD COLUMNX TO INDEX 2 and x + 50 to 3 of Genome "g"
			g.setGenome(2, x);
			g.setGenome(3, x + 50); //CAN be more than the Runner.WIDTH (SCREEN)
			
			//ADD COLUMNY TO INDEX 4 and COLUMNY - 10 to 5 of Genome "g"
			double hole = random.nextBoolean() ? 20.0 : 0.0;
			int height = 10 + random.nextInt(120);
			double rHeight = Runner.HEIGHT - height - 120 - hole;
			g.setGenome(4, rHeight);
			g.setGenome(5, rHeight - 10);
			
			//ADD HEIGHT TO INDEX 6 and HEIGHT + 10 to 7 of Genome "g"
			g.setGenome(6, (double) height);
			g.setGenome(7, (double) (height + 10));
			
			chromosome.add(g);
			mSize++;
		}
		
		
	}
	
	public ArrayList<Genome> getChromosome(){
		return chromosome;
	}
	
	public void setFitness(int f) {
		fitness = f;
	}
	
	public int getFitness() {
		return fitness;
	}
	
	public int size() {
		return mSize;
	}
	
	private static double getRandomNumberInRange(double min, double max) {
		if (min >= max) {
			throw new IllegalArgumentException("max must be greater than min");
		}
		return (double) Math.random() * max + min;
	}
	@Override
	public int compareTo(Object o) {
		// TODO Auto-generated method stub
		Chromosome c = (Chromosome) o;
		int result;
		if (this.fitness < c.fitness) {
			result = -1;		
		}else if (this.fitness == c.fitness) {
			result = 0;
		}else {
			result = 1;
		}
		return result;
	}
	
}
