package org.prajvalk.evosim.neuralnetwork;

import java.util.concurrent.ThreadLocalRandom;

public class NeuralConnection {

    public NeuralNode fromNode;
    public NeuralNode toNode;
    public float weight;
    public boolean enabled;
    public int innovationNo;

    public NeuralConnection(NeuralNode n1, NeuralNode n2, float w, int i) {
        fromNode = n1;
        toNode = n2;
        weight = w;
        enabled = true;
        innovationNo = i;
    }

    public void mutateWeight() {
        double rand = Math.random();
        if(rand < NeuralConstants.WEIGHT_MUTATION_THRESHOLD) {
            if(ThreadLocalRandom.current().nextBoolean()) weight = ThreadLocalRandom.current().nextFloat();
            else weight = - ThreadLocalRandom.current().nextFloat();
        } else {
            weight += newGaussianNumber() / 50.0f;
            if(weight > 1) weight = 1;
            if(weight < -1) weight = -1;
        }
    }

    @Override
    public NeuralConnection clone() {
        NeuralConnection conn = new NeuralConnection(fromNode, toNode, weight, innovationNo);
        conn.enabled = this.enabled;
        return conn;
    }

    public static double newGaussianNumber() {
        return ThreadLocalRandom.current().nextGaussian();
    }

}
