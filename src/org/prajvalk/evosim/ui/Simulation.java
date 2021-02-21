package org.prajvalk.evosim.ui;

import org.prajvalk.openwork.engine.OpenWorkEngine;
import org.prajvalk.openwork.engine.OpenWorkFunction;
import org.prajvalk.openwork.exceptions.OpenWorkDiagnosticIOException;

public class Simulation {

    public static void main(String[] args) throws OpenWorkDiagnosticIOException {
        OpenWorkEngine engine = new OpenWorkEngine(1600, 1000);
        OpenWorkFunction function = new TestFunction();
        engine.addFunction(function);
        engine.setTitle("Main Simulation");
        engine.initialize();
        engine.run();
    }

}
