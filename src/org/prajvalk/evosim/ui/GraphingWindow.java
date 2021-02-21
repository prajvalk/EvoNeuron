package org.prajvalk.evosim.ui;

import org.prajvalk.openwork.engine.OpenWorkEngine;
import org.prajvalk.openwork.engine.OpenWorkFunction;
import org.prajvalk.openwork.exceptions.OpenWorkDiagnosticIOException;

public class GraphingWindow {

    public static void displayWindow() throws OpenWorkDiagnosticIOException {
        OpenWorkEngine engine = new OpenWorkEngine(800, 600);
        OpenWorkFunction fn = new GraphingTestFunction();
        engine.setTitle("Generation Graph Window");
        engine.enableDiagnosticCollection();
        engine.addFunction(fn);
        engine.initialize();
        engine.run();
    }

    public static void main(String[] args) throws OpenWorkDiagnosticIOException {
        displayWindow();
    }

}
