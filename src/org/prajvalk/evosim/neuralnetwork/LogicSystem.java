package org.prajvalk.evosim.neuralnetwork;

import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

public class LogicSystem {
    ArrayList<NeuralConnection> genes = new  ArrayList<NeuralConnection>();//a list of connections between nodes which represent the NN
    ArrayList<NeuralNode> nodes = new ArrayList<NeuralNode>();//list of nodes
    int inputs;
    int outputs;
    int layers =2;
    int nextNode = 0;
    int biasNode;
    ArrayList<NeuralNode> network = new ArrayList<NeuralNode>();//a list of the nodes in the order that they need to be considered in the NN
    //--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

    public LogicSystem(int in, int out) {
        int localNextConnectionNumber = 0;
        //set input number and output number
        inputs = in;
        outputs = out;

        //create input nodes
        for (int i = 0; i<inputs; i++) {
            nodes.add(new NeuralNode(i));
            nextNode ++;
            nodes.get(i).layer =0;
        }

        //create output nodes
        for (int i = 0; i < outputs; i++) {
            nodes.add(new NeuralNode(i+inputs));
            nodes.get(i+inputs).layer = 1;
            nextNode++;
        }

        nodes.add(new NeuralNode(nextNode));//bias node
        biasNode = nextNode;
        nextNode++;
        nodes.get(biasNode).layer = 0;

        //connect inputs to all outputs outputs
        for (int i = 0; i < in; i++) {
            //add the bare minimum amount of connections to the array with random weights and unique innovation numbers
            for (int j = 0; j < outputs; j++) {
                if(ThreadLocalRandom.current().nextBoolean()) {
                    genes.add(new NeuralConnection(nodes.get(i), nodes.get(inputs + j), (float) ThreadLocalRandom.current().nextFloat(), localNextConnectionNumber));
                } else {
                    genes.add(new NeuralConnection(nodes.get(i), nodes.get(inputs + j), 0 - (float) ThreadLocalRandom.current().nextFloat(), localNextConnectionNumber));
                }
                localNextConnectionNumber ++;
            }
        }

        //connect the bias
        for (int i = 0; i < outputs; i++) {
            if(ThreadLocalRandom.current().nextBoolean()) {
                genes.add(new NeuralConnection(nodes.get(biasNode), nodes.get(inputs + i), (float) ThreadLocalRandom.current().nextFloat(), localNextConnectionNumber));
            } else {
                genes.add(new NeuralConnection(nodes.get(biasNode), nodes.get(inputs + i), 0 - (float) ThreadLocalRandom.current().nextFloat(), localNextConnectionNumber));
            }
            localNextConnectionNumber  ++;
        }
    }



    //-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
    //returns the node with a matching number
    //sometimes the nodes will not be in order
    public NeuralNode getNode(int nodeNumber) {
        for (int i = 0; i < nodes.size(); i++) {
            if (nodes.get(i).number == nodeNumber) {
                return nodes.get(i);
            }
        }
        return null;
    }


    //---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
    //adds the conenctions going out of a node to that node so that it can acess the next node during feeding forward
    void connectNodes() {

        for (int i = 0; i< nodes.size(); i++) {//clear the connections
            nodes.get(i).outputConnections.clear();
        }

        for (int i = 0; i < genes.size(); i++) {//for each connectionGene
            genes.get(i).fromNode.outputConnections.add(genes.get(i));//add it to node
        }
    }

    //---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
    //feeding in input values into the NN and returning output array
    float[] feedForward(float[] inputValues) {
        //set the outputs of the input nodes
        for (int i =0; i < inputs; i++) {
            nodes.get(i).outputValue = inputValues[i];
        }
        nodes.get(biasNode).outputValue = 1;//output of bias is 1

        for (int i = 0; i< network.size(); i++) {//for each node in the network engage it(see node class for what this does)
            network.get(i).engage();
        }

        //the outputs are nodes[inputs] to nodes [inputs+outputs-1]
        float[] outs = new float[outputs];
        for (int i = 0; i < outputs; i++) {
            outs[i] = nodes.get(inputs + i).outputValue;
        }

        for (int i = 0; i < nodes.size(); i++) {//reset all the nodes for the next feed forward
            nodes.get(i).inputSum = 0;
        }

        return outs;
    }

    //----------------------------------------------------------------------------------------------------------------------------------------
    //sets up the NN as a list of nodes in order to be engaged

    void generateNetwork() {
        connectNodes();
        network = new ArrayList<>();
        //for each layer add the node in that layer, since layers cannot connect to themselves there is no need to order the nodes within a layer

        for (int l = 0; l< layers; l++) {//for each layer
            for (int i = 0; i< nodes.size(); i++) {//for each node
                if (nodes.get(i).layer == l) {//if that node is in that layer
                    network.add(nodes.get(i));
                }
            }
        }
    }
    //-----------------------------------------------------------------------------------------------------------------------------------------
    //mutate the NN by adding a new node
    //it does this by picking a random connection and disabling it then 2 new connections are added
    //1 between the input node of the disabled connection and the new node
    //and the other between the new node and the output of the disabled connection
    void addNode(ArrayList<NeuralConnectionHistory> innovationHistory) {
        //pick a random connection to create a node between
        int randomConnection = (int) (Math.floor(ThreadLocalRandom.current().nextFloat()) * genes.size());

        while (genes.get(randomConnection).fromNode == nodes.get(biasNode)) {//dont disconnect bias
            randomConnection = (int) (Math.floor(ThreadLocalRandom.current().nextFloat()) * genes.size());
        }

        genes.get(randomConnection).enabled = false;//disable it

        int newNodeNo = nextNode;
        nodes.add(new NeuralNode(newNodeNo));
        nextNode ++;
        //add a new connection to the new node with a weight of 1
        int connectionInnovationNumber = getInnovationNumber(innovationHistory, genes.get(randomConnection).fromNode, getNode(newNodeNo));
        genes.add(new NeuralConnection(genes.get(randomConnection).fromNode, getNode(newNodeNo), 1, connectionInnovationNumber));


        connectionInnovationNumber = getInnovationNumber(innovationHistory, getNode(newNodeNo), genes.get(randomConnection).toNode);
        //add a new connection from the new node with a weight the same as the disabled connection
        genes.add(new NeuralConnection(getNode(newNodeNo), genes.get(randomConnection).toNode, genes.get(randomConnection).weight, connectionInnovationNumber));
        getNode(newNodeNo).layer = genes.get(randomConnection).fromNode.layer +1;


        connectionInnovationNumber = getInnovationNumber(innovationHistory, nodes.get(biasNode), getNode(newNodeNo));
        //connect the bias to the new node with a weight of 0
        genes.add(new NeuralConnection(nodes.get(biasNode), getNode(newNodeNo), 0, connectionInnovationNumber));

        //if the layer of the new node is equal to the layer of the output node of the old connection then a new layer needs to be created
        //more accurately the layer numbers of all layers equal to or greater than this new node need to be incrimented
        if (getNode(newNodeNo).layer == genes.get(randomConnection).toNode.layer) {
            for (int i = 0; i< nodes.size() -1; i++) {//dont include this newest node
                if (nodes.get(i).layer >= getNode(newNodeNo).layer) {
                    nodes.get(i).layer ++;
                }
            }
            layers ++;
        }
        connectNodes();
    }

    //------------------------------------------------------------------------------------------------------------------
    //adds a connection between 2 nodes which aren't currently connected
    void addConnection(ArrayList<NeuralConnectionHistory> innovationHistory) {
        //cannot add a connection to a fully connected network
        if (fullyConnected()) {
            System.out.println("Genome:AddConnection: Connection Failed");
            return;
        }


        //get random nodes
        int randomNode1 = (int)(Math.floor(ThreadLocalRandom.current().nextFloat() * nodes.size()));
        int randomNode2 = (int)(Math.floor(ThreadLocalRandom.current().nextFloat() * nodes.size()));
        while (nodes.get(randomNode1).layer == nodes.get(randomNode2).layer
                || nodes.get(randomNode1).isConnectedTo(nodes.get(randomNode2))) { //while the random nodes are no good
            //get new ones
            randomNode1 = (int)(Math.floor(ThreadLocalRandom.current().nextFloat() * nodes.size()));
            randomNode2 = (int)(Math.floor(ThreadLocalRandom.current().nextFloat() * nodes.size()));
        }
        int temp;
        if (nodes.get(randomNode1).layer > nodes.get(randomNode2).layer) {//if the first random node is after the second then switch
            temp =randomNode2  ;
            randomNode2 = randomNode1;
            randomNode1 = temp;
        }

        //get the innovation number of the connection
        //this will be a new number if no identical genome has mutated in the same way
        int connectionInnovationNumber = getInnovationNumber(innovationHistory, nodes.get(randomNode1), nodes.get(randomNode2));
        //add the connection with a random array

        if(ThreadLocalRandom.current().nextBoolean()) {
            genes.add(new NeuralConnection(nodes.get(randomNode1), nodes.get(randomNode2), ThreadLocalRandom.current().nextFloat(), connectionInnovationNumber))
        }
        connectNodes();
    }

    //-------------------------------------------------------------------------------------------------------------------------------------------
    //returns the innovation number for the new mutation
    //if this mutation has never been seen before then it will be given a new unique innovation number
    //if this mutation matches a previous mutation then it will be given the same innovation number as the previous one
    int getInnovationNumber(ArrayList<NeuralConnectionHistory> innovationHistory, NeuralNode from, NeuralNode to) {
        boolean isNew = true;
        int connectionInnovationNumber = NeuralConstants.NEXT_CONNECTION_NO;
        for (int i = 0; i < innovationHistory.size(); i++) {//for each previous mutation
            if (innovationHistory.get(i).matches(this, from, to)) {//if match found
                isNew = false;//its not a new mutation
                connectionInnovationNumber = innovationHistory.get(i).innovationNumber; //set the innovation number as the innovation number of the match
                break;
            }
        }

        if (isNew) {//if the mutation is new then create an arrayList of integers representing the current state of the genome
            ArrayList<Integer> innoNumbers = new ArrayList<Integer>();
            for (int i = 0; i< genes.size(); i++) {//set the innovation numbers
                innoNumbers.add(genes.get(i).innovationNo);
            }

            //then add this mutation to the innovationHistory
            innovationHistory.add(new NeuralConnectionHistory(from.number, to.number, connectionInnovationNumber, innoNumbers));
            NeuralConstants.NEXT_CONNECTION_NO++;
        }
        return connectionInnovationNumber;
    }
    //----------------------------------------------------------------------------------------------------------------------------------------

    //returns whether the network is fully connected or not
    boolean fullyConnected() {
        int maxConnections = 0;
        int[] nodesInLayers = new int[layers];//array which stored the amount of nodes in each layer

        //populate array
        for (int i =0; i< nodes.size(); i++) {
            nodesInLayers[nodes.get(i).layer] +=1;
        }

        //for each layer the maximum amount of connections is the number in this layer * the number of nodes infront of it
        //so lets add the max for each layer together and then we will get the maximum amount of connections in the network
        for (int i = 0; i < layers-1; i++) {
            int nodesInFront = 0;
            for (int j = i+1; j < layers; j++) {//for each layer infront of this layer
                nodesInFront += nodesInLayers[j];//add up nodes
            }

            maxConnections += nodesInLayers[i] * nodesInFront;
        }

        if (maxConnections == genes.size()) {//if the number of connections is equal to the max number of connections possible then it is full
            return true;
        }
        return false;
    }


    //-------------------------------------------------------------------------------------------------------------------------------
    //mutates the genome
    void mutate(ArrayList<NeuralConnectionHistory> innovationHistory) {
        float rand1 = ThreadLocalRandom.current().nextFloat();
        if (rand1<0.8) { // 80% of the time mutate weights
            for (int i = 0; i< genes.size(); i++) {
                genes.get(i).mutateWeight();
            }
        }
        //5% of the time add a new connection
        float rand2 = ThreadLocalRandom.current().nextFloat();
        if (rand2<0.05) {
            addConnection(innovationHistory);
        }


        //3% of the time add a node
        float rand3 = ThreadLocalRandom.current().nextFloat();
        if (rand3<0.03) {
            addNode(innovationHistory);
        }
    }

    //---------------------------------------------------------------------------------------------------------------------------------
    //called when this Genome is better that the other parent
    public LogicSystem crossover(LogicSystem parent2) {
        LogicSystem child = new LogicSystem(inputs, outputs, true);
        child.genes.clear();
        child.nodes.clear();
        child.layers = layers;
        child.nextNode = nextNode;
        child.biasNode = biasNode;
        ArrayList<NeuralConnection> childGenes = new ArrayList<NeuralConnection>();//list of genes to be inherrited form the parents
        ArrayList<Boolean> isEnabled = new ArrayList<Boolean>();
        //all inherrited genes
        for (int i = 0; i< genes.size(); i++) {
            boolean setEnabled = true;//is this node in the chlid going to be enabled

            int parent2gene = matchingGene(parent2, genes.get(i).innovationNo);
            if (parent2gene != -1) {//if the genes match
                if (!genes.get(i).enabled || !parent2.genes.get(parent2gene).enabled) {//if either of the matching genes are disabled

                    if (ThreadLocalRandom.current().nextFloat() < 0.75) {//75% of the time disabel the childs gene
                        setEnabled = false;
                    }
                }
                float rand = ThreadLocalRandom.current().nextFloat() ;
                if (rand<0.5) {
                    childGenes.add(genes.get(i));

                    //get gene from this fucker
                } else {
                    //get gene from parent2
                    childGenes.add(parent2.genes.get(parent2gene));
                }
            } else {//disjoint or excess gene
                childGenes.add(genes.get(i));
                setEnabled = genes.get(i).enabled;
            }
            isEnabled.add(setEnabled);
        }


        //since all excess and disjoint genes are inherrited from the more fit parent (this Genome) the childs structure is no different from this parent | with exception of dormant connections being enabled but this wont effect nodes
        //so all the nodes can be inherrited from this parent
        for (int i = 0; i < nodes.size(); i++) {
            child.nodes.add(nodes.get(i).clone());
        }

        //clone all the connections so that they connect the childs new nodes

        for ( int i =0; i<childGenes.size(); i++) {
            child.genes.add(childGenes.get(i).clone(child.getNode(childGenes.get(i).fromNode.number), child.getNode(childGenes.get(i).toNode.number)));
            child.genes.get(i).enabled = isEnabled.get(i);
        }

        child.connectNodes();
        return child;
    }

    //----------------------------------------------------------------------------------------------------------------------------------------
    //create an empty genome
    public LogicSystem(int in, int out, boolean crossover) {
        //set input number and output number
        inputs = in;
        outputs = out;
    }
    //----------------------------------------------------------------------------------------------------------------------------------------
    //returns whether or not there is a gene matching the input innovation number  in the input genome
    int matchingGene(LogicSystem parent2, int innovationNumber) {
        for (int i =0; i < parent2.genes.size(); i++) {
            if (parent2.genes.get(i).innovationNo == innovationNumber) {
                return i;
            }
        }
        return -1; //no matching gene found
    }
    //----------------------------------------------------------------------------------------------------------------------------------------
    //prints out info about the genome to the console
    void printGenome() {
        System.out.println("Print genome  layers:"+ layers);
        System.out.println("bias node: "  + biasNode);
        System.out.println("nodes");
        for (int i = 0; i < nodes.size(); i++) {
            System.out.print(nodes.get(i).number + ",");
        }
        System.out.println("Genes");
        for (int i = 0; i < genes.size(); i++) {//for each connectionGene
            System.out.println("gene " + genes.get(i).innovationNo+ "From node " + genes.get(i).fromNode.number+ "To node " + genes.get(i).toNode.number+ "is enabled " +genes.get(i).enabled+ "from layer " + genes.get(i).fromNode.layer+ "to layer " + genes.get(i).toNode.layer+ "weight: " + genes.get(i).weight);
        }

        System.out.println();
    }

    //----------------------------------------------------------------------------------------------------------------------------------------
    //returns a copy of this genome
    public LogicSystem clone() {

        LogicSystem clone = new LogicSystem(inputs, outputs, true);

        for (int i = 0; i < nodes.size(); i++) {//copy nodes
            clone.nodes.add(nodes.get(i).clone());
        }

        //copy all the connections so that they connect the clone new nodes

        for ( int i =0; i<genes.size(); i++) {//copy genes
            clone.genes.add(genes.get(i).clone(clone.getNode(genes.get(i).fromNode.number), clone.getNode(genes.get(i).toNode.number)));
        }

        clone.layers = layers;
        clone.nextNode = nextNode;
        clone.biasNode = biasNode;
        clone.connectNodes();

        return clone;
    }
}