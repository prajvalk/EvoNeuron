package org.prajvalk.evosim.neuralnetwork;

import org.prajvalk.evosim.shared.Shared;
import org.prajvalk.openwork.graphics.OpenWorkColor;
import org.prajvalk.openwork.graphics.OpenWorkTexture;
import org.prajvalk.openwork.utility.Colors;

public class Player {
    float fitness;
    Genome brain;
    float[] vision = new float[8];//the input array fed into the neuralNet
    float[] decision = new float[4]; //the out put of the NN
    float unadjustedFitness;
    int lifespan = 0;//how long the player lived for fitness
    int bestScore =0;//stores the score achieved used for replay
    boolean dead;
    int score;
    int gen = 0;
    int size; // Size of the cell
    int posx;
    int posy;
    OpenWorkColor color;
    //---------------------------------------------------------------------------------------------------------------------------------------------------------
    //constructor

    Player() {
        brain = new Genome(8,4);
        dead = false;
        size = 10 + (int)(Math.random() * 10);
        posx = (int) (Math.random() * (Shared.outputTexture.getWidth() - 1));
        posy = (int) (Math.random() * (Shared.outputTexture.getHeight() - 1));
        color = Colors.getRandomColor();
    }

    //---------------------------------------------------------------------------------------------------------------------------------------------------------
    void show() {
        for(int i=1;i<=size;i++) drawSquare(Shared.outputTexture, color, i, posx, posy);
        setColor(Shared.outputTexture, color, posx, posy);
    }
    //---------------------------------------------------------------------------------------------------------------------------------------------------------
    void move() {
//<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<replace
    }
    //---------------------------------------------------------------------------------------------------------------------------------------------------------
    void update() {
//<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<replace
    }
    //----------------------------------------------------------------------------------------------------------------------------------------------------------

    void look() {
//<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<replace

    }






    //---------------------------------------------------------------------------------------------------------------------------------------------------------
    //gets the output of the brain then converts them to actions
    void think() {

        float max = 0;
        int maxIndex = 0;
        //get the output of the neural network
        decision = brain.feedForward(vision);

        for (int i = 0; i < decision.length; i++) {
            if (decision[i] > max) {
                max = decision[i];
                maxIndex = i;
            }
        }

        //<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<replace


    }
    //---------------------------------------------------------------------------------------------------------------------------------------------------------
    //returns a clone of this player with the same brian
    public Player clone() {
        Player clone = new Player();
        clone.brain = brain.clone();
        clone.fitness = fitness;
        clone.brain.generateNetwork();
        clone.gen = gen;
        clone.bestScore = score;
        return clone;
    }

//---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
//since there is some randomness in games sometimes when we want to replay the game we need to remove that randomness
//this fuction does that

    Player cloneForReplay() {
        Player clone = new Player();
        clone.brain = brain.clone();
        clone.fitness = fitness;
        clone.brain.generateNetwork();
        clone.gen = gen;
        clone.bestScore = score;
        //<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<replace
        return clone;
    }

    //---------------------------------------------------------------------------------------------------------------------------------------------------------
    //fot Genetic algorithm
    void calculateFitness() {
        //<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<replace

    }

    //---------------------------------------------------------------------------------------------------------------------------------------------------------
    Player crossover(Player parent2) {
        Player child = new Player();
        child.brain = brain.crossover(parent2.brain);
        child.brain.generateNetwork();
        return child;
    }

    private static void drawSquare(OpenWorkTexture tex, OpenWorkColor color, int radius, int cx, int cy) {
        // Right
        int rmtx = cx + radius;
        int rmty = cy - radius;
        for(int i=1;i<=2*radius;i++,rmty++) setColor(tex, color, rmtx, rmty);

        // Left
        int lmtx = cx - radius;
        int lmty = cy - radius;
        for(int i=1;i<=2*radius;i++,lmty++) setColor(tex, color, lmtx, lmty);

        // Top
        int tmtx = cx - radius;
        int tmty = cy - radius;
        for(int i=1;i<=2*radius;i++,tmtx++) setColor(tex, color, tmtx, tmty);

        // Bottom
        int bmtx = cx - radius;
        int bmty = cy + radius;
        for(int i=1;i<=2*radius+1;i++,bmtx++) setColor(tex, color, bmtx, bmty);
    }

    private static void setColor(OpenWorkTexture tex, OpenWorkColor color, int x, int y) {
        try {
            tex.setColor(color, x, y);
        } catch (ArrayIndexOutOfBoundsException a) {
        }
    }
}