package org.prajvalk.evosim.cell;

import org.prajvalk.evosim.environment.Environment;
import org.prajvalk.openwork.graphics.OpenWorkColor;
import org.prajvalk.openwork.utility.Colors;

import java.util.ArrayList;

public class Cell {

    private static ArrayList<OpenWorkColor> availableColors = new ArrayList<>();

    public int x;
    public int y;

    public static void initialize() {
        availableColors.add(Colors.AQUAMARINE);
        availableColors.add(Colors.BEIGE);
        availableColors.add(Colors.BLUE);
        availableColors.add(Colors.LIME);
        availableColors.add(Colors.CHOCOLATE);
        availableColors.add(Colors.ORANGE);
        availableColors.add(Colors.BISQUE);
        availableColors.add(Colors.CADET_BLUE);
        availableColors.add(Colors.THISTLE);
        availableColors.add(Colors.DARK_TURQOUSIE);
        availableColors.add(Colors.REBECCA_PURPLE);
        availableColors.add(Colors.BURLYWOOD);
        availableColors.add(Colors.MISTY_ROSE);
    }

    public OpenWorkColor cellColor;

    public int size;
    public int life;
    public int maxlife = 200;
    public Environment environment;
    public boolean isDead = false;

    public Cell(Environment e) {
        cellColor = availableColors.get((int)(Math.random() * availableColors.size()));
        size = 10 + (int)(Math.random() * 10);
        environment = e;
    }

    public void update() {
        if(isDead) return;
        int randMovement = 0;

        if(randMovement == 0) { x++; }
        if(randMovement == 1) { x--; }
        if(randMovement == 2) { y++; }
        if(randMovement == 3) { y--; }
        if(randMovement == 4) { x++; y++; }
        if(randMovement == 5) { x--; y--; }
        if(randMovement == 6) { x++; y--; }
        if(randMovement == 7) { x--; y++; }

        if(life >= maxlife) die();
        life++;
    }

    private void die() {
        isDead = true;
    }

}
