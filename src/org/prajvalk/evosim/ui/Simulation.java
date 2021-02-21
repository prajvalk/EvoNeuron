package org.prajvalk.evosim.ui;

import org.prajvalk.evosim.shared.Shared;
import org.prajvalk.openwork.engine.OpenWorkEngine;
import org.prajvalk.openwork.engine.OpenWorkFunction;
import org.prajvalk.openwork.exceptions.OpenWorkDiagnosticIOException;
import org.prajvalk.openwork.graphics.OpenWorkTexture;
import org.prajvalk.openwork.utility.Colors;
import org.prajvalk.openwork.utility.Textures;

public class Simulation {

    public static void main(String[] args) throws OpenWorkDiagnosticIOException {
        Shared.outputTexture = new OpenWorkTexture(800, 600);
        Textures.paint(Colors.WHITE, Shared.outputTexture);
        OpenWorkEngine engine = new OpenWorkEngine(800, 600);
        engine.addTexture(Shared.outputTexture);
        OpenWorkFunction function = new SimulationFunction();
        engine.addFunction(function);
        engine.setTitle("EvoNeuron - Simulation Engine");
        engine.setDelay(1);
        engine.initialize();
        engine.run();
    }

}
