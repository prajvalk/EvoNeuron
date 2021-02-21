package org.prajvalk.evosim.ui;

import org.prajvalk.openwork.graphics.OpenWorkColor;
import org.prajvalk.openwork.graphics.OpenWorkTexture;
import org.prajvalk.openwork.utility.Colors;

public class GraphPlot extends OpenWorkTexture {

    int currentPlot = 0;

    public GraphPlot(int width, int height) {
        super(width, height);
        reset();
    }

    @Override
    public void setColor(OpenWorkColor color, int x, int y) {
        int offsety = super.getHeight() - y;
        super.setColor(color, x, offsety);
    }

    public void plot(OpenWorkColor color, int y) {
        try {
            if (currentPlot >= super.getWidth()) {
                currentPlot = 0;
                reset();
            }
            setColor(color, currentPlot, y);
            currentPlot++;
        } catch (ArrayIndexOutOfBoundsException aioobe) {
            if(y >= getHeight()) plot(color, (y - getHeight()));
        }
    }

    public void reset() {
        for(int i=0;i<getWidth();i++) for(int j=0;j<getHeight();j++) super.setColor(Colors.WHITE, i, j);
    }
}
