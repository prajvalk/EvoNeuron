package org.prajvalk.openwork.engine;

public abstract class OpenWorkFunction {

    public OpenWorkEngineShared shared;

    public void setShared(OpenWorkEngineShared shared) {
        this.shared = shared;
    }

    public abstract void start();

    public abstract void update();

}
