package ai;

import java.util.ArrayList;
import static java.lang.Math.round;
import java.util.Random;

import game.Runner;

public class Chromosome implements Comparable<Object>{
	private ArrayList<Genome> chromosome;
	private int fitness = 0;
	public Chromosome() {
		//INITIALIZE SPEED [0-500]
		
		Random random = new Random(); 
		
		//DETERMINE RANGE OF X
		for (int x = 0; x < Runner.WIDTH; x += 50) {
			//ADD SPEED TO INDEX 0 and SPEED + 10 to 1 of Genome "g"
			for(int speed = 10; speed < 501; speed = (int) round(speed * 1.2)) {
				for (int i = 0; i < 2; i++) {
					Genome g = new Genome();
					int a = random.nextInt(3);
					g.setAction(a);
					g.setGenome(0, speed);
					g.setGenome(1, (int) round(speed * 1.2));
					g.setGenome(2, x);
					g.setGenome(3, x + 50);
					g.setGenome(4, i);
					chromosome.add(g);
				}
			}
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
		return chromosome.size();
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
