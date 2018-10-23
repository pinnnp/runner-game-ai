package ai;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class Generation {
	ArrayList<Genome> genomes = new ArrayList<Genome>();
	ArrayList<Genome> bestGenomes = new ArrayList<Genome>();
	final static int NUM_BEST_GENOMES = 30; // 1 generation has 30 best genomes out of 100 genomes.
	
	public Generation() {
		for (int i = 0; i < 100; i++) {
			genomes.add(new Genome());
		}
	}
	
	//public execute
	
	public void keepBestGenomes() {
		genomes.sort(Collections.reverseOrder());
		for (int i = 0; i < NUM_BEST_GENOMES; i++) {
			bestGenomes.add(genomes.get(i));
		}
	}
	
	public void nextGen() {
		ArrayList<Genome> genomes = new ArrayList<Genome>();
		for (int i=0; i<bestGenomes.size(); i++) {
			genomes.add(bestGenomes.get(i));
		}
	}
	
	public Genome crossover() {
		Random random = new Random();
		Genome newGenome = genomes.get(random.nextInt(NUM_BEST_GENOMES));
		Genome otherGenome = genomes.get(random.nextInt(NUM_BEST_GENOMES));
		int cutLocation = (int) (newGenome.getGenome().size() * Math.random());
		for(int i = 0; i < cutLocation; i++) {
			newGenome.getGenome().set(i, otherGenome.getGenome().get(i));
		}
		return newGenome;
	}
	
	public void mutation() {
		Random random = new Random();
		while(genomes.size() < 100) {
			Genome newGenome = genomes.get(random.nextInt(NUM_BEST_GENOMES));
			ArrayList<Double> genome = newGenome.getGenome();
			for (int i = 0; i < random.nextInt(genome.size()); i++) {
				genome.set(random.nextInt(genome.size()), Math.random());
			}
			newGenome.setGenome(genome);
			genomes.add(newGenome);
			}
	}
	
	public ArrayList<Genome> getBestGenomes() {
		return this.bestGenomes;
	}
	
	public ArrayList<Genome> getGenomes() {
		return this.genomes;
	}
}
