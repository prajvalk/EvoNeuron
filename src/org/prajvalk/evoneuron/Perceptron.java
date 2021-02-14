package org.prajvalk.evoneuron;

public class Perceptron {
	
	private double weight;
	
	public Perceptron() {
		weight = (Math.random() * 2 - 1);
	}
	
	public double compute(double input) {
		return weight * input;
	}
	
	public double adjustWeight(double factor) {
		return weight + factor;
	}
	
}