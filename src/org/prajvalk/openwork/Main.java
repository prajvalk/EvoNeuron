package org.prajvalk.openwork;

import org.prajvalk.openwork.exceptions.OpenWorkDiagnosticIOException;
import org.prajvalk.openwork.engine.OpenWorkEngine;
import org.prajvalk.openwork.graphing.StandardCartesianGraph;
import org.prajvalk.openwork.utility.Colors;
import org.prajvalk.openwork.utility.Geometry;

public class Main {

    public static void main(String[] args) throws OpenWorkDiagnosticIOException, InterruptedException {
        OpenWorkEngine engine = new OpenWorkEngine(800, 800);
        engine.initialize();

        StandardCartesianGraph texture = new StandardCartesianGraph(800, 800);
        engine.addTexture(texture);

        texture.drawAxes(Colors.RED, Colors.BLUE);
        Geometry.drawArc(0, 0, 10, 0.1f, 0.4f, Colors.BROWN, texture);

        engine.enableDiagnosticCollection();
        engine.run();
    }

}
