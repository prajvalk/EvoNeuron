package org.prajvalk.evosim.environment;

import org.prajvalk.evosim.cell.Cell;

import java.util.ArrayList;

public class Environment {

    public ArrayList<Cell> cells;

    public int width;
    public int height;

    public Environment(int maxCells, int maxWidth, int maxHeight) {
        ArrayList<Integer> cellXc = new ArrayList<>();
        ArrayList<Integer> cellYc = new ArrayList<>();
        cells = new ArrayList<>();
        width = maxWidth;
        height = maxHeight;

        for(int i=0;i<maxCells;i++) {
            int x = (int)((maxWidth-1) * Math.random());
            int y = (int)((maxHeight-1) * Math.random());

            if(cellXc.contains(x) && cellYc.contains(y)) {
                i--;
                continue;
            }

            Cell c = new Cell(this);
            c.x = x;
            cellXc.add(x);
            c.y = y;
            cellYc.add(y);
            cells.add(c);
        }
    }

    public void update() {
        for(int i=0;i<cells.size();i++) {
            cells.get(i).update();
        }
    }

}
