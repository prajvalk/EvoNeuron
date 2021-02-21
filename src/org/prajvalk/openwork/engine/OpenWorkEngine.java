package org.prajvalk.openwork.engine;

import org.prajvalk.openwork.exceptions.OpenWorkDiagnosticIOException;
import org.prajvalk.openwork.graphics.OpenWorkTexture;
import org.prajvalk.openwork.parallel.OpenWorkProcessorController;
import org.prajvalk.openwork.utility.OpenWorkDiagnostics;
import org.prajvalk.openwork.utility.OpenWorkLogSystem;

import javax.swing.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Vector;

public class OpenWorkEngine {

    public OpenWorkEngineShared shared;

    private Vector<OpenWorkFunction> functions;

    private JPanel targetPanel = new JPanel();

    private JFrame targetFrame = new JFrame();

    private String title = "OpenWorkEngine - "+this.toString();

    private long delay;

    public OpenWorkEngine(int width, int height) {
        shared = new OpenWorkEngineShared();
        shared.logger = new OpenWorkLogSystem();
        shared.diagnostics = new OpenWorkDiagnostics();
        shared.textures = new Vector<>(1,1);
        functions = new Vector<>(1,1);
        shared.windowWidth = width;
        shared.windowHeight = height;
    }

    public void initialize() throws OpenWorkDiagnosticIOException {
        OpenWorkProcessorController.initialize();

        for(int i=0;i<functions.size();i++) {
            functions.elementAt(i).setShared(shared);
            functions.elementAt(i).start();
        }

        targetFrame.setSize(shared.windowWidth, shared.windowHeight);
        targetFrame.setTitle(title);
        targetFrame.setResizable(false);

        shared.diagnostics.newDiagnosticNode("engine", "FPL", "TRL", "UIL", "CD", "TTT", "FPS");
    }

    public void addTexture(OpenWorkTexture texture) {
        shared.textures.addElement(texture);
    }

    public void addFunction(OpenWorkFunction function) { functions.addElement(function); }

    public void setDelay(long mindelay) {
        delay = mindelay;
    }

    public void setTitle(String name) {
        title = name;
    }

    public void enableDiagnosticCollection() {
        shared.diagnostics.enable();
    }

    public void run() throws OpenWorkDiagnosticIOException {
        final boolean[] isLoopRunning = { true };

        while (isLoopRunning[0]) {

            // Execute Functions
            long fplStart = System.nanoTime();
            for(int i=0;i<functions.size();i++) functions.elementAt(i).update();
            long fpl = System.nanoTime() - fplStart;

            long trlStart = System.nanoTime();
            OpenWorkTexture finalImage = shared.textures.elementAt(0);
            long trl = System.nanoTime() - trlStart;

            long uilStart = System.nanoTime();
            JLabel image = new JLabel(new ImageIcon(finalImage.getImage()));
            targetPanel.add(image);
            targetFrame.add(targetPanel);
            targetFrame.setVisible(true);
            long uil = System.nanoTime() - uilStart;

            // Wait if Necessary
            long cdStart = System.nanoTime();
            try {
                Thread.sleep(delay);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            long cd = System.nanoTime() - cdStart;

            // Cleanup Old Image or Prepare for New Cycle
            long uil2Start = System.nanoTime();
            targetFrame.remove(targetPanel);
            targetPanel.remove(image);

            // Check if close button pressed
            targetFrame.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosing(WindowEvent e) {
                    isLoopRunning[0] = false;
                    exit();
                }
            });
            uil += (System.nanoTime() - uil2Start);
            long ttt = fpl + trl + uil + cd;
            float fps = (float)( 1.0d / (ttt / Math.pow(10, 9)) );
            shared.diagnostics.addDiagnosticData("engine", ""+fpl, ""+trl, ""+uil, ""+cd, ""+ttt, ""+fps);
        }
    }

    private void exit() {
        // Exit Cleanup
        System.exit(shared.exitstatus);
    }

}
