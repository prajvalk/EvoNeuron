package org.prajvalk.openwork.graphing;

import org.prajvalk.openwork.graphics.OpenWorkColor;
import org.prajvalk.openwork.graphics.OpenWorkTexture;

import java.awt.image.BufferedImage;

public class StandardCartesianGraph extends OpenWorkTexture {

    private int offsetX;

    private int offsetY;

    public StandardCartesianGraph(int width, int height) {
        super(width, height);
        offsetX = width/2;
        offsetY = height/2;
    }

    public StandardCartesianGraph(BufferedImage img) {
        super(img);
        offsetX = img.getWidth()/2;
        offsetY = img.getHeight()/2;
    }

    public void drawAxes(OpenWorkColor x, OpenWorkColor y) {
        for(int i = 0; i < getWidth(); i++) super.setColor(x, i, offsetY);
        for(int i = 0; i < getHeight(); i++) super.setColor(y, offsetX, i);
    }

    @Override
    public void setColor(OpenWorkColor color, int x, int y) {
        super.setColor(color, offsetX + x, offsetY - y);
        System.out.println("X: "+(x)+" Y: "+(y));
    }

}
