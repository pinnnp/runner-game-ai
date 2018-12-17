package ai;

import static java.lang.Math.round;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

//import java.util.ArrayList;
import game.*;


public class Driver {
	
	private static Generation generation;
	private static final File lastestChromosome = new File("lastestChromosome.txt");
	
	public Driver() {
		//new Runner();
		generation = new Generation();
		int genNum=0;
		System.out.println("Generation #0 started");
		generation.execute();
		writeLastestChromosome(generation.getChromosomes());
		generation.keepBestGenomes();
		System.out.println("Generation #0 ended"+" Fittest: "+generation.getBestChromosomes().get(0).getFitness());
		System.out.println("----------------------------------------------------------");
		while (true) {
			genNum++;
			System.out.println("Generation #"+genNum+" started");
			//crossover and mutation method here
			generation.nextGen();
			generation.execute();
			writeLastestChromosome(generation.getChromosomes());
			generation.keepBestGenomes();
			for (int i = 0; i<30; i++) {System.out.println(generation.bestChromosomes.get(i).getFitness());}
			System.out.println("Generation #"+genNum+" ended"+" Fittest: "+generation.bestChromosomes.get(0).getFitness());
			System.out.println("----------------------------------------------------------");
			//                                                                                    
		}
	}
	public static ArrayList<Chromosome> getLastestChromosome() {
		String[] s1,s2; 
		ArrayList<Chromosome> a;
		if (!lastestChromosome.exists()) {
			try {
				lastestChromosome.createNewFile();
				writeLastestChromosome(new ArrayList<Chromosome>());
			} catch (IOException ex) {
				System.err.println("ERROR creating file");
			}
		} else {
			try {
				BufferedReader reader = new BufferedReader(new FileReader(lastestChromosome));
				String line;
				while ((line = reader.readLine()) != null) {
					for (int j=0; j<100; j++) {
						line = reader.readLine();
						s1 = line.split(" "); //list of genomes
						for (int i=0; i<100; i++) {
							s2 = s1[i].split(",");
							Genome g = new Genome();
							g.setAction(Integer.parseInt(s2[5]));
							g.setGenome(0, Integer.parseInt(s2[0]));
							g.setGenome(1, Integer.parseInt(s2[1]));
							g.setGenome(2, Integer.parseInt(s2[2]));
							g.setGenome(3, Integer.parseInt(s2[3]));
							g.setGenome(4, Integer.parseInt(s2[4]));
							Chromosome c = new Chromosome(1);
							c.chromosome.add(g);
							generation.getChromosomes().add(c);
							for (int k=0; k<6; k++) {
							System.out.println(g.get(k));}
						} reader.close();
					}
				}
				
				
				//lastestChromosome = new ArrayList<Chromosome>(Arrays.asList(currentChromosome));
			} catch (IOException ex) {
				System.err.println("ERROR reading current chromosome");
			}
		}
		return null;
		
	}
	
	public static void setLastestChromosome(ArrayList<Chromosome> c) {
		//if ()
	}
	
	private static void writeLastestChromosome(ArrayList<Chromosome> c) {
		try {
			BufferedWriter writer = new BufferedWriter(new FileWriter(lastestChromosome, false));
			for (Chromosome chro: c) {
				for (Genome g: chro.getChromosome()) {
					for (int i=0; i<6; i++) {
							String s = String.valueOf(g.get(i));
							writer.write(s+",");
					}
					writer.write(g.act()+" ");
				} writer.write("\n");
			}
			writer.close();
		} catch (IOException ex){
			System.err.println("ERROR setting lastest chromosome to file");
		}
	}
	
	public static void main(String[] args) {
		new Driver();
	}

	
	
	
}
