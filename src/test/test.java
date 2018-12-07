package test;

import java.util.ArrayList;
import java.util.Collections;

import ai.Chromosome;

public class test {
	public static ArrayList<Chromosome> chromosomes = new ArrayList<Chromosome>();
	public static void runTest() {
		for (int i = 0; i < 100; i++) {
			chromosomes.add(new Chromosome());
		}
		int i = 0;
		for (Chromosome c : chromosomes) {
			c.setFitness(i);
			i++;
		}
		for (Chromosome c : chromosomes) {
			System.out.println(c.getFitness());
		}
	}
	
}
