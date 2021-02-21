package org.prajvalk.evosim.neuralnetwork;

import java.util.ArrayList;

public class NeuralNode {

    public int number;
    public float inputSum;
    public float outputValue;
    public int layer;
    public ArrayList<NeuralConnection> outputConnections;

    public NeuralNode(int n) {
        number = n;
    }

    public void engage() {
        if(layer != 0) outputValue = sigmoidActivationFunction(inputSum);
        for (NeuralConnection connection : outputConnections) if (connection.enabled) connection.toNode.inputSum += connection.weight * outputValue;
    }

    public boolean isConnectedTo(NeuralNode n2) {
        if (n2.layer == layer) {
            return false;
        } else if(n2.layer < layer) {
            for(int i=0;i<n2.outputConnections.size();i++) if(n2.outputConnections.get(i).toNode.equals(this)) return true;
        } else {
            for(int i=0;i<n2.outputConnections.size();i++) if(n2.outputConnections.get(i).toNode.equals(this)) return true;
        }
        return false;
    }

    public static float sigmoidActivationFunction(float inp) {
        float y = (float) (1 / (1 + Math.pow((float)Math.E, -4.9f*inp)));
        return y;
    }
}
