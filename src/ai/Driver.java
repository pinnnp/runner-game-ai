package ai;

import java.util.ArrayList;

import game.*;


public class Driver {
	public Driver() {
		Generation generation = new Generation();
		int genNum=0;
		System.out.println("Generation #0 started");
		runGenomes(generation);
		generation.keepBestGenomes();
		System.out.println("Generation #0 ended"+" Fittest: "+generation.getBestGenomes().get(0).getFitness());
		System.out.println("----------------------------------------------------------");
		while (true) {
			genNum++;
			System.out.println("Generation #"+genNum+" started");
			//crossover and mutation method here
			generation.nextGen();
			for (int i=0; i<40; i++) {generation.genomes.add(generation.crossover());}
			generation.mutation();
			runGenomes(generation);
			generation.keepBestGenomes();
			System.out.println("Generation #"+genNum+" ended"+" Fittest: "+generation.getBestGenomes().get(0).getFitness());
			System.out.println("----------------------------------------------------------");
		}
		
	}
	
	public void runGenomes(Generation generation) {
		for (int i=0; i<generation.genomes.size(); i++ ) {
			// Genome.execute();
				if (Runner.state == Runner.STATE.OVER) {
					generation.getGenomes().get(i).setFitness(Runner.game.getScore());
					System.out.println("Genome #"+i+" ended."+" Fitness: "+generation.genomes.get(i).getFitness());
					break;
				}
			}
		}
	
	
	
}
