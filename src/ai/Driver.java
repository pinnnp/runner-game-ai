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
	private BufferedReader reader;
	
	public Driver() {
		//new Runner();
		if (lastestChromosome.exists()) {
			generation = new Generation(1);
			getLastestChromosome();
			//System.out.println(generation.chromosomes.get(4).chromosome.get(0).act());
		} else generation = new Generation();
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
	public static void getLastestChromosome() {
		String[] s1 = new String[22880];
		BufferedReader reader = null;
		if (!lastestChromosome.exists()) {
			try {
				lastestChromosome.createNewFile();
				writeLastestChromosome(new ArrayList<Chromosome>());
			} catch (IOException ex) {
				System.err.println("ERROR creating file");
			}
		} else {
			try {
				reader = new BufferedReader(new FileReader(lastestChromosome));
				String line;
				while ((line = reader.readLine()) != null) {
					int num=0;
					s1 = line.split(";"); //list of genomes
					//System.out.println(s1.length);
					Chromosome c = new Chromosome(1);
						for (int i=0; i<s1.length; i++) {
							//if(i == 0)System.out.println(s1[i]);
							String[] s2 = s1[i].split(",");
							Genome g = new Genome();
							g.setAction(Integer.parseInt(s2[6]));
							g.setGenome(0, Double.parseDouble(s2[0]));
							g.setGenome(1, Double.parseDouble(s2[1]));
							g.setGenome(2, Double.parseDouble(s2[2]));
							g.setGenome(3, Double.parseDouble(s2[3]));
							g.setGenome(4, Double.parseDouble(s2[4]));
							g.setGenome(5, Double.parseDouble(s2[5]));
							c.chromosome.add(g);
							num++;
							/*
							System.out.println(num);
							for (int k=0; k<6; k++) {
							System.out.print(g.get(k)+" ");}
							//System.out.println(generation.chromosomes.);
							System.out.println("\n");
							*/
						} generation.getChromosomes().add(c);
					}
				
				
			} catch (IOException ex) {
				System.err.println("ERROR reading current chromosome");
			} finally {
				try {
					reader.close();
				} catch (IOException ex) {
					
				}
			}
		}	
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
					writer.write(g.act()+";");
				} writer.write("\n");
			}
			writer.close();
		} catch (IOException ex){
			System.err.println("ERROR writing lastest chromosome to file");
		}
	}
	
	public static void main(String[] args) {
		new Driver();
	}

	
	
	
}
