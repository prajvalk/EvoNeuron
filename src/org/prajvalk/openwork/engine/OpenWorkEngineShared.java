package org.prajvalk.openwork.engine;

import org.prajvalk.openwork.graphics.OpenWorkTexture;
import org.prajvalk.openwork.utility.OpenWorkDiagnostics;
import org.prajvalk.openwork.utility.OpenWorkLogSystem;

import java.util.Vector;

public class OpenWorkEngineShared {

    public Vector<OpenWorkTexture> textures;

    public OpenWorkLogSystem logger;

    public OpenWorkDiagnostics diagnostics;

    public int windowHeight;

    public int windowWidth;

    public int exitstatus = 0;

}
