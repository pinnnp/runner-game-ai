package ai;

//import java.util.ArrayList;
import game.*;


public class Driver {
	public Driver() {
		//new Runner();
		Generation generation = new Generation();
		int genNum=0;
		System.out.println("Generation #0 started");
		generation.execute();
		//runGenomes(generation);
		generation.keepBestGenomes();
		System.out.println("Generation #0 ended"+" Fittest: "+generation.getBestGenomes().get(0).getFitness());
		System.out.println("----------------------------------------------------------");
		while (true) {
			genNum++;
			System.out.println("Generation #"+genNum+" started");
			//crossover and mutation method here
			generation.nextGen();
			generation.execute();
			//runGenomes(generation);
			generation.keepBestGenomes();
			System.out.println("Generation #"+genNum+" ended"+" Fittest: "+generation.getBestGenomes().get(0).getFitness());
			System.out.println("----------------------------------------------------------");
			//                                                                                    
		}
	}
	
	/*public void runGenomes(Generation generation) {
		//for (int i=0; i<generation.genomes.size(); i++ ) {
			generation.execute();
			if (Runner.state == Runner.STATE.OVER) {
					//generation.getGenomes().get(i).setFitness(Runner.game.getScore());
					System.out.println("Genome #"+id+" ended."+" Fitness: "+generation.genomes.get(i).getFitness());
					//Runner.quit();
				}
			
		}*/
	public static void main(String[] args) {
		new Driver();
	}

	
	
	
}
