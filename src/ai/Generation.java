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
	ArrayList<Genome> genomes = new ArrayList<Genome>();
	ArrayList<Genome> bestGenomes = new ArrayList<Genome>();
	final static double MUTATION_RATE = 0.25;
	final static int NUM_BEST_GENOMES = 30; // 1 generation has 30 best genomes out of 100 genomes.
	public Robot robot;
	public Generation() {
		for (int i = 0; i < 100; i++) {
			genomes.add(new Genome());
		}
	}
	
	//public execute
	
	public void keepBestGenomes() {
		bestGenomes.clear();
		genomes.sort(Collections.reverseOrder());
		for (int i = 0; i < NUM_BEST_GENOMES; i++) {
			bestGenomes.add(genomes.get(i));
		}
	}
	
	public void nextGen() {
		genomes.clear();
		for (int i=0; i<bestGenomes.size(); i++) {
			genomes.add(bestGenomes.get(i));
		}
		for (int i=0; i<50; i++) {genomes.add(crossover());}
		for (int i=0; i<20; i++) {genomes.add(mutation());}
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
			inputs.add((double) Runner.game.pColumny);
			inputs.add((double) Runner.game.pColumnh);
			
			for (Genome g : genomes) {
				if (g.get(0) > inputs.get(0)*90/100 && g.get(0) < inputs.get(0)*110/100) count++;
				if (g.get(1) > inputs.get(1)*90/100 && g.get(1) < inputs.get(1)*110/100) count++;
				if (g.get(2) == inputs.get(2)) count++;
				if (g.get(3) > inputs.get(3)*90/100 && g.get(3) < inputs.get(3)*110/100) count++;
				if (count >2) {
					//System.out.println(count);
					//System.out.println(Runner.game.player.y);
					if (g.act() == 0) { /*System.out.println("Do nothing"); */  count = 0; }
					if (g.act() == 1) {
						//robot.mousePress(InputEvent.BUTTON1_DOWN_MASK);
						if(Runner.game.player.jumping < 2) {
							Runner.game.player.jump();
							Runner.game.player.uncrouch();
							}
						if (Runner.state != STATE.OVER) {
							g.setFitness(g.getFitness()+0.0000001);
						}
						 count = 0;
					}
					if (g.act() == 2) {
						if (Runner.game.player.jumping == 0) {
							Runner.game.player.crouch();
						}
						if (Runner.state != STATE.OVER) {
							g.setFitness(g.getFitness()+0.0000001);
						}
						 count = 0;
					}
					if (Runner.state == Runner.STATE.OVER) {
						//generation.getGenomes().get(i).setFitness(Runner.game.getScore());
						System.out.println("Genome ended."+" Fitness: "+g.getFitness());
					}
					break;
				}
			} 
		} 
	}
	
}
