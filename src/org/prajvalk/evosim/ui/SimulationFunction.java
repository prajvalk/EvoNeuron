package org.prajvalk.evosim.ui;

import org.prajvalk.evosim.neuralnetwork.Population;
import org.prajvalk.evosim.shared.Shared;
import org.prajvalk.openwork.engine.OpenWorkFunction;

import java.util.Vector;

public class SimulationFunction extends OpenWorkFunction {

    public Population population;

    @Override
    public void start() {
        population = new Population(25);
    }

    @Override
    public void update() {
        if(!population.done()) population.updateAlive();
        else population.naturalSelection();
    }
}
