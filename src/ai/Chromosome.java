package ai;

import java.util.ArrayList;
import static java.lang.Math.round;
import java.util.Random;

import game.Runner;

public class Chromosome implements Comparable<Object>, Cloneable{
	public ArrayList<Genome> chromosome;
	public double fitness = 0;
	public Chromosome() {
		//INITIALIZE SPEED [0-500]
		chromosome = new ArrayList<Genome>();
		Random random = new Random(); 
		
		//DETERMINE RANGE OF X
		for (int x = 0; x < Runner.WIDTH; x += 5) {
			//ADD SPEED TO INDEX 0 and SPEED + 10 to 1 of Genome "g"
			for(int speed = 10; speed < 501; speed = (int) round(speed * 1.2)) {
				for (int i = 0; i < 2; i++) {
					for(int j = 0; j < 2; j++) {
						Genome g = new Genome();
						int a = random.nextInt(3);
						g.setAction(a);
						g.setGenome(0, speed);
						g.setGenome(1, (int) round(speed * 1.2));
						g.setGenome(2, x);
						g.setGenome(3, x + 5);
						g.setGenome(4, i);
						g.setGenome(5, j);
						chromosome.add(g);
					}
				}
			}
		}
	}
	
	public ArrayList<Genome> getChromosome(){
		return chromosome;
	}
	
	public void setChromosome(ArrayList<Genome> c) {
		ArrayList<Genome> clone = (ArrayList<Genome>) c.clone();
		chromosome = clone;
	}
	
	public void setFitness(double f) {
		fitness = f;
	}
	
	public double getFitness() {
		return this.fitness;
	}
	public int size() {
		return chromosome.size();
	}
	
	public Object clone() {
		Chromosome c = new Chromosome();
		for (int i = 0; i < chromosome.size(); i++) {
			Genome g = new Genome();
			g = (Genome) chromosome.get(i).clone();
			c.chromosome.set(i, g);
		}
		return c;
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
