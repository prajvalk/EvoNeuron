package org.prajvalk.evosim.ui;

import org.prajvalk.evosim.cell.Cell;
import org.prajvalk.evosim.environment.Environment;
import org.prajvalk.openwork.engine.OpenWorkFunction;
import org.prajvalk.openwork.graphics.OpenWorkTexture;
import org.prajvalk.openwork.utility.Colors;
import org.prajvalk.openwork.utility.Textures;

import java.util.Vector;

public class TestFunction extends OpenWorkFunction {

    private static Environment e;

    @Override
    public void start() {
        Cell.initialize();
        e = new Environment(36, 1600, 1000);
    }

    @Override
    public void update() {
        shared.textures = new Vector<>(1,1);
        OpenWorkTexture texture = new OpenWorkTexture(1600, 1000);
        Textures.paint(Colors.WHITE, texture);
        Display.toTexture(texture, e);
        shared.textures.addElement(texture);
        e.update();
    }

}
