package test;

import java.util.ArrayList;
import java.util.Collections;

import ai.Chromosome;

public class test {
	public static ArrayList<Chromosome> chromosomes = new ArrayList<Chromosome>();
	public static int[] scores = {278,43,278,43,43,43,43,43,88,43,133,88,133,88,88,88,133,88,88,133,
									88,178,88,43,133,88,43,178,43,43,43,43,43,178,88,88,43,133,43,133,
									133,88,88,88,329,43,43,88,88,88,88,88,133,43,88,178,88,43,43,88,
									88,43,178,43,88,88,43,178,223,43,43,43,328,178,88,88,133,88,88,88,
									88,88,43,88,328,178,178,88,43,88,43, 400,223,178,133,88,43,43,223,43,43,43
									};
	public static void runTest() {
		/*
		for (int i = 0; i < 100; i++) {
			chromosomes.add(new Chromosome());
		}
		for (int i1 = 0; i1 < 100; i1++) {
			chromosomes.get(i1).setFitness(scores[i1]);
		}
		Chromosome c2 = chromosomes.get(0);
		
		chromosomes.sort(Collections.reverseOrder());
		for (Chromosome c : chromosomes) {
			System.out.println(c.getFitness());
		}
		System.out.println(chromosomes.get(0).getFitness());
		*/
		Chromosome c1 = new Chromosome();
		Chromosome c2 = new Chromosome();
		c2 = (Chromosome) c1.clone();
		c1.setFitness(5);
		c1.getChromosome().get(0).getGenome().set(0, 6969.0);
		System.out.println(c1.getChromosome().get(0).get(0)+ " " +c2.getChromosome().get(0).get(0));
		System.out.println(c1.getFitness()+ " " +c2.getFitness());
	}
	
	public static void main(String[]args) {
		runTest();
	}
	
}
