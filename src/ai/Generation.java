package ai;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.InputEvent;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

import javax.swing.JFrame;

import game.Game;
import game.HighScore;
import game.Runner;
import game.Runner.STATE;

public class Generation {
	private static ArrayList<Chromosome> chromosomes = new ArrayList<Chromosome>();
	public static ArrayList<Chromosome> bestChromosomes = new ArrayList<Chromosome>();
	public static int cNum;
	final static double MUTATION_RATE = 0.2;
	final static int NUM_BEST_CHROMOSOMES = 30; // 1 generation has 30 best chromosomes out of 100 chromosomes.
	public Generation() {
		for (int i = 0; i < 100; i++) {
			chromosomes.add(new Chromosome());
		}
	}
	
	public Generation(int i) {
		chromosomes = new ArrayList<Chromosome>();
	}
	
	public void keepBestGenomes() {
		bestChromosomes.clear();
		//Collections.sort(chromosomes);
		chromosomes.sort(Collections.reverseOrder());
		for (int i = 0; i < chromosomes.size(); i++) {
			System.out.println(chromosomes.get(i).getFitness());
		}
		for (int i = 0; i < NUM_BEST_CHROMOSOMES; i++) {
			bestChromosomes.add(chromosomes.get(i));
		}
	}
	
	public void nextGen() {
		chromosomes.clear();
		for (int i=0; i < NUM_BEST_CHROMOSOMES; i++) {
			chromosomes.add(bestChromosomes.get(i));
		}
		for (int i=0; i<30; i++) {chromosomes.add(crossover());}
		for (int i=0; i<20; i++) {chromosomes.add(mutation());}
		for (int i=0; i<20; i++) {chromosomes.add(new Chromosome());}
	}
	
	public Chromosome crossover() {
		Random random = new Random();
		int numNew = random.nextInt(NUM_BEST_CHROMOSOMES);
		int numOth = random.nextInt(NUM_BEST_CHROMOSOMES);
		while(numNew == numOth) {
			numOth = random.nextInt(NUM_BEST_CHROMOSOMES);
		}
		Chromosome newC = (Chromosome) chromosomes.get(numNew).clone();
		Chromosome otherC = (Chromosome) chromosomes.get(numOth).clone();
		int cutLocation = random.nextInt(newC.size());
		for(int i = 0; i < cutLocation; i++) {
			newC.getChromosome().get(i).setAction(otherC.getChromosome().get(i).act());;
		}
		return newC;
	}
	
	public Chromosome mutation() {
		Random random = new Random();
		Chromosome newC = (Chromosome) chromosomes.get(random.nextInt(NUM_BEST_CHROMOSOMES)).clone();
		int numMu = random.nextInt((int) (MUTATION_RATE * newC.size()));
		for(; numMu>=0; numMu--) {
			int muLocation = random.nextInt(newC.size());
			newC.getChromosome().get(muLocation).mutates();
		}
		return newC;
	}
	
	public ArrayList<Chromosome> getBestChromosomes() {
		return this.bestChromosomes;
	}
	
	public ArrayList<Chromosome> getChromosomes() {
		return this.chromosomes;
	}
	
	public static void setDead(int score) {
		chromosomes.get(cNum - 1).setFitness(score);
		System.out.println("Chromosome " +cNum+ " ended."+" Fitness: "+chromosomes.get(cNum - 1).getFitness());
	}
	
	public void execute() {
		cNum = 0;
		for (int i = 0; i < chromosomes.size(); i++) {
			Chromosome c = chromosomes.get(i);
			cNum++;
			Runner.state = Runner.STATE.GAME;
			new Runner();
			Runner.start();
			while (Runner.state != STATE.OVER) {
				for (Genome g : c.getChromosome()) {
					if (Game.speed>=g.get(0) && Game.speed<g.get(1) 
							&& Game.pColumnx>=g.get(2) && Game.pColumnx<g.get(3)
							&& Game.hasHole==g.get(4) && Game.jumppable == g.get(5)) {
						if (g.act() == 0) { /*System.out.println("Do nothing");*/ 
						}
						else if (g.act() == 1) {
							if(Runner.game.player.jumping < 3) Runner.game.mousePressed(1);
							try {
								Thread.sleep(300);
							} catch (InterruptedException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}
						else if (g.act() == 2) {
							Runner.game.mousePressed(2);
							try {
								Thread.sleep(300);
							} catch (InterruptedException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}
						if (Runner.state == Runner.STATE.OVER) {
							g.mutates();
							break;
						}
					}else continue;
				}
			}
		} 
	}
	
}