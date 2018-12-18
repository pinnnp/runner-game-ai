package ai;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.InputEvent;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

import javax.swing.JFrame;

import game.Game;
import game.Runner;
import game.Runner.STATE;

public class Generation {
	ArrayList<Chromosome> chromosomes = new ArrayList<Chromosome>();
	public static ArrayList<Chromosome> bestChromosomes = new ArrayList<Chromosome>();
	final static double MUTATION_RATE = 0.05;
	final static int NUM_BEST_CHROMOSOMES = 30; // 1 generation has 30 best chromosomes out of 100 chromosomes.
	public Robot robot;
	public Generation() {
		for (int i = 0; i < 100; i++) {
			chromosomes.add(new Chromosome());
		}
		System.out.println(chromosomes.get(0).size());
	}
	public Generation(int i) {
		this.chromosomes = new ArrayList<Chromosome>();
	}
	
	//public execute
	
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
		int numMu = random.nextInt((int) (MUTATION_RATE * 100));
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
	
	public void execute() {
		int cNum = 0;
		for (Chromosome c : chromosomes) {
			//System.out.println(c.getChromosome().get(6).get(0));
			c.setFitness(0);
			cNum++;
			new Runner();
			Runner.state = STATE.GAME;
			Runner.start();
			int count = 0;
			while (Runner.state != STATE.OVER) {
				ArrayList<Double> inputs = new ArrayList<Double>();
				
				inputs.add((double) Game.speed);
				inputs.add((double) Game.pColumnx);
				inputs.add((double) Game.hasHole);
				inputs.add((double) Game.jumppable);
				//System.out.println("inputs: "+inputs.get(0)+" "+inputs.get(1)+" "+inputs.get(2));
			
				for (Genome g : c.getChromosome()) {
					//System.out.println("inputs: "+g.get(0)+" "+g.get(1)+" "+g.get(2));
					count = 0;
					if (inputs.get(0)>=g.get(0) && inputs.get(0)<g.get(1)) count++;
					if (inputs.get(1)>=g.get(2) && inputs.get(1)<g.get(3)) count++;
					if (inputs.get(2)==g.get(4) && inputs.get(3) == g.get(5)) count++;
//					System.out.println("g: "+g.get(0)+" "+g.get(2)+" "+g.get(4)+" "+g.get(5));
					if (count==3) {
//						System.out.println("inputs: "+inputs.get(0)+" "+inputs.get(1)+" "+inputs.get(2));
//						System.out.println("g: "+g.get(0)+" "+g.get(2)+" "+g.get(4));
						//System.out.println(count);
						//System.out.println(Runner.game.player.y);
						if (g.act() == 0) { /*System.out.println("Do nothing"); */  
							count = 0;
							/*if (Runner.state != STATE.OVER) {
								c.setFitness(c.getFitness()+1);
							} */
						}
						else if (g.act() == 1) {
							//robot.mousePress(InputEvent.BUTTON1_DOWN_MASK);
							if(Runner.game.player.jumping < 3) Runner.game.mousePressed(1);
							try {
								Thread.sleep(300);
							} catch (InterruptedException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
//							if(Runner.game.player.jumping < 2) {
//								Runner.game.player.jump();
//								Runner.game.player.uncrouch();
//								}
							/*if (Runner.state != STATE.OVER) {
								c.setFitness(c.getFitness()+1);
							}*/
							 count = 0;
						}
						else if (g.act() == 2) {
							Runner.game.mousePressed(2);
							try {
								Thread.sleep(300);
							} catch (InterruptedException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
//							if (Runner.game.player.jumping == 0) {
//								Runner.game.player.crouch();
//							}
							/*if (Runner.state != STATE.OVER) {
								c.setFitness(c.getFitness()+1);
							}*/
							 count = 0;
						}
						if (Runner.state == Runner.STATE.OVER) {
							c.setFitness(Runner.game.getScore());
							System.out.println("Chromosome " +cNum+ " ended."+" Fitness: "+c.getFitness());
							break;
						}
					}else continue;
					
				}
				
			
			} 
		} 
	}
	
}