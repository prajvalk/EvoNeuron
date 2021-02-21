package org.prajvalk.evosim.neuralnetwork;

public class Main {
    int nextConnectionNo = 1000;
    Population pop;
    int speed = 60;


    boolean showBest = true;//true if only show the best of the previous generation
    boolean runBest = false; //true if replaying the best ever game
    boolean humanPlaying = false; //true if the user is playing

    Player humanPlayer;

    boolean runThroughSpecies = false;
    int upToSpecies = 0;
    Player speciesChamp;

    boolean showBrain = false;

    boolean showBestEachGen = false;
    int upToGen = 0;
    Player genPlayerTemp;

    boolean showNothing = false;


//--------------------------------------------------------------------------------------------------------------------------------------------------

    void setup() {
//<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<replace
        pop = new Population(500);
        humanPlayer = new Player();
    }
}