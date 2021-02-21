package org.prajvalk.evosim.neuralnetwork;

import java.util.ArrayList;

public class NeuralConnectionHistory {

    public int fromNode;
    public int toNode;
    public int innovationNumber;

    public ArrayList<Integer> innovationNumbers;

    public NeuralConnectionHistory(int from, int to, int ino, ArrayList<Integer> integers) {
        fromNode = from;
        toNode = to;
        innovationNumber = ino;
        innovationNumbers = (ArrayList<Integer>) integers.clone();
    }

    public boolean matches(LogicSystem ls, NeuralNode from, NeuralNode to) {
        if(ls.genes.size() == innovationNumbers.size()) {
            if(from.number == fromNode && to.number == toNode) {
                for(int i=0;i<ls.genes.size();i++) if(!innovationNumbers.contains(ls.genes.get(i).innovationNumber)) return false;
                return true;
            }
        }
        return false;
    }

}
