package ai;

import java.util.ArrayList;
import java.util.Random;

import game.*;
import game.Runner.STATE;
//import javafx.scene.input.MouseEvent;
//import javax.swing.JButton;

public class Genome implements Comparable<Object>{
	private ArrayList<Double> genome = new ArrayList<Double>(); //Contains speed, x from nearest obstacle, y of obstacle from ground, obstacle height
	private double fitness;
	private int action;
	
	public void setGenome(ArrayList<Double> genome) {
		this.genome = genome;
	}

	public Genome() {
		this.genome.add(getRandomNumberInRange(10.0, 500.0));
		this.genome.add(getRandomNumberInRange(10.0, Runner.WIDTH) - Runner.WIDTH / 3);
		Random random = new Random(); 
		double hole = random.nextBoolean() ? 20.0 : 0.0;
		this.genome.add(hole);
		this.genome.add(10.0 + random.nextInt(120));
		/* 0: Nothing
		 * 1: Jump
		 * 2: Crouch
		 */
		this.action = random.nextInt(2);
	}
	
	public Genome(ArrayList<Integer> indexOfGenes, ArrayList<Double> oldGenome) { //Two parameters must have the same length
		for (int i = 0; i < 4; i++) {
			this.genome.add(Math.random());
		}
		
		for (int i  = 0; i < indexOfGenes.size(); i++) {
			this.genome.set(indexOfGenes.get(i), oldGenome.get(i));
		}
	}
	

	/*public void execute() {
		new Runner();
		Runner.state = STATE.GAME;
		Runner.start();
		while (Runner.state != STATE.OVER) {
			ArrayList<Double> inputs = new ArrayList<Double>();
			inputs.add((double) Runner.game.pSpeed);
			inputs.add((double) Runner.game.pColumnx);
			inputs.add((double) Runner.game.pColumny);
			inputs.add((double) Runner.game.pColumnh);
			
			double output = forward(inputs);
			if (output >= 0.5) {
				System.out.println("Jumped");
				Runner.game.player.jump();
                Runner.game.player.uncrouch();
			} else if (Runner.game.player.jumping == 0) {
                Runner.game.player.crouch();
                System.out.println("crouch");
            }
		}
		//Runner.quit();
		
	}*/
	
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
			
			if (this.genome.get(0) > inputs.get(0)*90/100 && this.genome.get(0) < inputs.get(0)*110/100) {
				count++;
			}
			if (this.genome.get(1) > inputs.get(1)*90/100 && this.genome.get(1) < inputs.get(1)*110/100) {
				count++;
			}
			if (this.genome.get(2) == inputs.get(2)) {
				count++;
			}
			if (this.genome.get(3) > inputs.get(3)*90/100 && this.genome.get(3) < inputs.get(3)*110/100) {
				count++;
			}
			
			if (count > 2) {
				if (this.action == 0) { System.out.println("Do nothing");}
				if (this.action == 1) {
					Runner.game.player.jump();
					Runner.game.player.uncrouch();
					System.out.println("Jumped");
					count = 0;
				}
				if (this.action == 2) {
					if (Runner.game.player.jumping == 0) {
						System.out.println("crouch");
						Runner.game.player.crouch();
						count = 0;
					}
				}
			}
		}
	}
	
	public ArrayList<Double> getGenome() {
		return genome;
	}
	
	public void setFitness(double d) {
		fitness = d;
	}
	
	public double getFitness() {
		return fitness;
	}
	
	public int compareTo(Object o) { //From min to max 
		Genome other = (Genome) o;
		int result;
		if (this.fitness < other.fitness) {
			result = -1;		
		}else if (this.fitness == other.fitness) {
			result = 0;
		}else {
			result = 1;
		}
		return result;
	}
	
	/*public double forward(ArrayList<Double> inputs) {
		double output = 0;
		for (int i = 0; i < inputs.size(); i++) {
			output += inputs.get(i) * this.genome.get(i);
		}
		//System.out.println(output);
		output = Math.tanh(output);
		output = sigmoid(output);
		//System.out.println(output);
		return output;
	}
	
	public double sigmoid(double b) {
		return 1/(1 + Math.pow(Math.E, (-1 * b)));
	}*/
	
	public void mutates(int n) {
		if (n == 0) {
			this.genome.set(n, getRandomNumberInRange(10.0, 500.0));
		}else if(n == 1) {
			this.genome.set(n, getRandomNumberInRange(10.0, Runner.WIDTH) - Runner.WIDTH / 3);
		}else if (n == 2){
			this.genome.set(n, this.genome.get(n)==0.0 ? 20.0:0.0);
		}else {
			Random random = new Random();
			this.genome.set(n, 10.0 + random.nextInt(120));
		}
	}
	
	private static double getRandomNumberInRange(double min, double max) {

		if (min >= max) {
			throw new IllegalArgumentException("max must be greater than min");
		}
		return (double) Math.random() * max + min;
	}
	
	public int act() {
		return this.action;
	}
}
