package org.prajvalk.evoneuron;

import java.util.Vector;

public class NEATNeuralNet {
	
	private static final float NN_MUTATION_THRESHOLD = 1.4f;
	
	private static final float NNWT_MUTATION_THRESHOLD = 0.8f;
	
	private Vector<Vector<Perceptron>> neuralnet;
	
	private double bias;
	
	public NEATNeuralNet(int depth) {
		neuralnet = new Vector<>(1, 1);
		bias = Math.random();
	}
	
	public void generateNewNN() {
		Vector<Perceptron> inputlayer = new Vector<Perceptron>(1,1);
		for(int i=0;i<9;i++) inputlayer.addElement(new Perceptron());
		neuralnet.addElement(inputlayer);
		
		for(int i=1;i<neuralnet.size()-1;i++) {
			int currentLayerNumeric = (int)(Math.random() * 5);
			Vector<Perceptron> layer = new Vector<Perceptron>(1,1);
			for(int j=0;j<currentLayerNumeric;j++) layer.addElement(new Perceptron());
			neuralnet.addElement(layer);
		}
		
		Vector<Perceptron> outputlayer = new Vector<Perceptron>(1,1);
		for(int i=0;i<9;i++) outputlayer.addElement(new Perceptron());
		neuralnet.addElement(outputlayer);
	}
	
	public int computeWNN(int... inputs) {
		Vector<Perceptron> inputlayer = neuralnet.elementAt(0);
		double summation = 0.0d;
		for(int i=0;i<inputs.length;i++) {
			double output = inputlayer.elementAt(i).compute(inputs[i]);
			double actOutput = actf(output);
			summation += actOutput;
		}
		summation += bias;
		
		for(int i=1;i<neuralnet.size()-1;i++) {
			Vector<Perceptron> layer = neuralnet.elementAt(i);
			double layersummation = 0.0d;
			for(int j=0;j<layer.size();j++) {
				double output = layer.elementAt(j).compute(summation);
				double actOutput = actf(output);
				layersummation += output;
			}
			summation = layersummation + bias;
		}
		
		Vector<Double> outputVec = new Vector<>(1,1);
		Vector<Perceptron> outputlayer = neuralnet.elementAt(neuralnet.size()-1);
		for(int i=0;i<outputlayer.size();i++) {
			double output = outputlayer.elementAt(i).compute(summation);
			double actOutput = factf(output);
			outputVec.addElement(output);
		}
		
		int maxWeightedOutputID = 0;
		for(int i=0;i<outputVec.size();i++) if(outputVec.elementAt(maxWeightedOutputID) < outputVec.elementAt(i)) maxWeightedOutputID = i;
		return maxWeightedOutputID + 1;
	}
	
	public void mutate(double nnFactor, double nnwtFactor) {
		float nncf = (float)(Math.random() * nnFactor);
		float nnwtcf = (float)(Math.random() * nnwtFactor);
		if(nncf >= NN_MUTATION_THRESHOLD) {
			int events = (int) (nncf - NN_MUTATION_THRESHOLD);
			for(int i=0;i<events;i++) {
			}				
		} 
		if(nnwtcf >= NNWT_MUTATION_THRESHOLD) {
			int events = (int) (nnwtcf - NNWT_MUTATION_THRESHOLD);
		}
	}
	
	public double actf(double inp) {
		return Math.tanh(inp);
	}
	
	public double factf(double inp) {
		if(inp <= 0) return 0;
		return inp;
	}
	
}