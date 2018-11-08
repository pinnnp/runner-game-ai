package ai;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

import game.Runner;
import game.Runner.STATE;

public class Generation {
	ArrayList<Genome> genomes = new ArrayList<Genome>();
	ArrayList<Genome> bestGenomes = new ArrayList<Genome>();
	final static double MUTATION_RATE = 0.25;
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
	
	public Genome mutation() {
		Random random = new Random();
		Genome newGenome = genomes.get(random.nextInt(NUM_BEST_GENOMES));
		/*
		if(Math.random() < MUTATION_RATE) {
			newGenome.mutates(random.nextInt(3));
		}
		*/
		newGenome.mutates(random.nextInt(3));
		return newGenome;
	}
	
	public ArrayList<Genome> getBestGenomes() {
		return this.bestGenomes;
	}
	
	public ArrayList<Genome> getGenomes() {
		return this.genomes;
	}
	
	public void execute() {
		new Runner();
		Runner.state = STATE.GAME;
		Runner.start();
		int count = 0;
		while (Runner.state != STATE.OVER) {
			
			ArrayList<Double> inputs = new ArrayList<Double>();
			inputs.add((double) Runner.game.pSpeed);
			inputs.add((double) Runner.game.pColumnx);
			inputs.add((double) Runner.game.pColumny);
			inputs.add((double) Runner.game.pColumnh);
			
			for (Genome g : genomes) {
				if (g.get(0) > inputs.get(0)*90/100 && g.get(0) < inputs.get(0)*110/100) count++;
				if (g.get(1) > inputs.get(1)*90/100 && g.get(1) < inputs.get(1)*110/100) count++;
				if (g.get(2) == inputs.get(2)) count++;
				if (g.get(3) > inputs.get(3)*90/100 && g.get(3) < inputs.get(3)*110/100) count++;
			
			if (count >3) {
				if (g.act() == 0) { /*System.out.println("Do nothing");*/ count=0;}
				if (g.act() == 1) {
					if(Runner.game.player.jumping < 2) Runner.game.player.jump();
					Runner.game.player.uncrouch();
					//System.out.println("Jumped");
					count=0;
				}
				if (g.act() == 2) {
					if (Runner.game.player.jumping == 0) {
						//System.out.println("crouch");
						Runner.game.player.crouch();
						count=0;
					}
				}
				if (Runner.state != STATE.OVER) {
					g.setFitness(g.getFitness()+0.0000001);
				}
				if (Runner.state == Runner.STATE.OVER) {
					//generation.getGenomes().get(i).setFitness(Runner.game.getScore());
					System.out.println("Genome ended."+" Fitness: "+g.getFitness());
					//Runner.quit();
				}
			}
			}
		}
	}
}
