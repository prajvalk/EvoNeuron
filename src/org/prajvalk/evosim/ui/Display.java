package org.prajvalk.evosim.ui;

import org.prajvalk.evosim.environment.Environment;
import org.prajvalk.openwork.graphics.OpenWorkColor;
import org.prajvalk.openwork.graphics.OpenWorkTexture;

public class Display {

    public static void toTexture(OpenWorkTexture texture,Environment e) {

        for(int i=0;i<e.cells.size();i++) {
            if(!e.cells.get(i).isDead) {
                int x = e.cells.get(i).x;
                int y = e.cells.get(i).y;
                int cellSize = e.cells.get(i).size;
                OpenWorkColor color = e.cells.get(i).cellColor;
                for(int j=0;j<=cellSize;j++) drawSquare(texture, color, j, x, y);
            }
        }

    }

    private static void drawSquare(OpenWorkTexture tex, OpenWorkColor color, int radius, int cx, int cy) {
        // Right
        int rmtx = cx + radius;
        int rmty = cy - radius;
        for(int i=1;i<=2*radius;i++,rmty++) setColor(tex, color, rmtx, rmty);

        // Left
        int lmtx = cx - radius;
        int lmty = cy - radius;
        for(int i=1;i<=2*radius;i++,lmty++) setColor(tex, color, lmtx, lmty);

        // Top
        int tmtx = cx - radius;
        int tmty = cy - radius;
        for(int i=1;i<=2*radius;i++,tmtx++) setColor(tex, color, tmtx, tmty);

        // Bottom
        int bmtx = cx - radius;
        int bmty = cy + radius;
        for(int i=1;i<=2*radius+1;i++,bmtx++) setColor(tex, color, bmtx, bmty);
    }

    private static void setColor(OpenWorkTexture tex, OpenWorkColor color, int x, int y) {
        try {
            tex.setColor(color, x, y);
        } catch (ArrayIndexOutOfBoundsException a) {
        }
    }

}
