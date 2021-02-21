package org.prajvalk.evosim.ui;

import org.prajvalk.openwork.engine.OpenWorkFunction;
import org.prajvalk.openwork.utility.Colors;

import java.util.Vector;

public class GraphingTestFunction extends OpenWorkFunction {

    private GraphPlot plot;

    private int x = 0;

    @Override
    public void start() {
        plot = new GraphPlot(shared.windowWidth, shared.windowHeight);
    }

    @Override
    public void update() {
        shared.textures = new Vector<>(1,1);
        int y = 200;
        x++;
        plot.plot(Colors.MAGENTA, y);
        shared.textures.addElement(plot);
    }

}
