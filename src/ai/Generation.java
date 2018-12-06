package ai;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.InputEvent;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

import javax.swing.JFrame;

import game.Runner;
import game.Runner.STATE;

public class Generation {
	ArrayList<Chromosome> chromosomes = new ArrayList<Chromosome>();
	ArrayList<Chromosome> bestChromosomes = new ArrayList<Chromosome>();
	final static double MUTATION_RATE = 0.25;
	final static int NUM_BEST_CHROMOSOMES = 30; // 1 generation has 30 best chromosomes out of 100 chromosomes.
	public Robot robot;
	public Generation() {
		for (int i = 0; i < 100; i++) {
			chromosomes.add(new Chromosome());
		}
	}
	
	//public execute
	
	public void keepBestGenomes() {
		bestChromosomes.clear();
		chromosomes.sort(Collections.reverseOrder());
		for (int i = 0; i < NUM_BEST_CHROMOSOMES; i++) {
			bestChromosomes.add(chromosomes.get(i));
		}
	}
	
	public void nextGen() {
		chromosomes.clear();
		for (int i=0; i < NUM_BEST_CHROMOSOMES; i++) {
			chromosomes.add(bestChromosomes.get(i));
		}
		for (int i=0; i<50; i++) {chromosomes.add(crossover());}
		for (int i=0; i<20; i++) {chromosomes.add(mutation());}
	}
	
	public Chromosome crossover() {
		Random random = new Random();
		int numNew = random.nextInt(NUM_BEST_CHROMOSOMES);
		int numOth = random.nextInt(NUM_BEST_CHROMOSOMES);
		while(numNew == numOth) {
			numOth = random.nextInt(NUM_BEST_CHROMOSOMES);
		}
		Chromosome newC = chromosomes.get(numNew);
		Chromosome otherC = chromosomes.get(numOth);
		int cutLocation = random.nextInt(newC.size());
		for(int i = 0; i < cutLocation; i++) {
			newC.getChromosome().get(i).setAction(otherC.getChromosome().get(i).act());;
		}
		return newC;
	}
	
	public Chromosome mutation() {
		Random random = new Random();
		Chromosome newC = chromosomes.get(random.nextInt(NUM_BEST_CHROMOSOMES));
		int numMu = random.nextInt((int) (MUTATION_RATE * 100));
		for(; numMu>=0; numMu--) {
			int muLocation = random.nextInt(newC.size());
			newC.getChromosome().get(muLocation).mutates();
		}
		return newC;
	}
	
	public ArrayList<Chromosome> getBestGenomes() {
		return this.bestChromosomes;
	}
	
	public ArrayList<Chromosome> getGenomes() {
		return this.chromosomes;
	}
	
	public void execute() {
		
		for (Chromosome c : chromosomes) {
			new Runner();
			try {
				robot = new Robot();
			} catch (AWTException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			Runner.state = STATE.GAME;
			Runner.start();
			int count = 0;
			while (Runner.state != STATE.OVER) {
			
				
				ArrayList<Double> inputs = new ArrayList<Double>();
				inputs.add((double) Runner.game.pSpeed);
				inputs.add((double) Runner.game.pColumnx);
				inputs.add((double) Runner.game.c.haveHole());
			
				//TODO - EDIT THIS PART!!!!
			
				for (Genome g : c.getChromosome()) {
					if (inputs.get(0)>=g.get(0) && inputs.get(0)<=g.get(1)) count++;
					if (inputs.get(1)>=g.get(2) && inputs.get(1)<=g.get(3)) count++;
					if (inputs.get(2)==g.get(4)) count++;
					
					if (count >2) {
						//System.out.println(count);
						//System.out.println(Runner.game.player.y);
						if (g.act() == 0) { /*System.out.println("Do nothing"); */  count = 0; }
						if (g.act() == 1) {
							//robot.mousePress(InputEvent.BUTTON1_DOWN_MASK);
							if (inputs.get(2)==1) {}
							else if(Runner.game.player.jumping < 2) {
								Runner.game.player.jump();
								Runner.game.player.uncrouch();
								}
							if (Runner.state != STATE.OVER) {
								c.setFitness(c.getFitness()+0.0000001);
							}
							 count = 0;
						}
						if (g.act() == 2) {
							if (Runner.game.player.jumping == 0) {
								Runner.game.player.crouch();
							}
							if (Runner.state != STATE.OVER) {
								c.setFitness(c.getFitness()+0.0000001);
							}
							 count = 0;
						}
						if (Runner.state == Runner.STATE.OVER) {
							//generation.getGenomes().get(i).setFitness(Runner.game.getScore());
							System.out.println("Chromosome ended."+" Fitness: "+c.getFitness());
						}
						break;
					}
				}
				
			
			} 
		} 
	}
	
}
