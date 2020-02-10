package com.wisekrakr.androidmain;


import com.wisekrakr.androidmain.retainers.TimeKeeper;
import com.wisekrakr.androidmain.systems.ComponentMapperSystem;
import com.wisekrakr.androidmain.systems.PhysicsSystem;

public class GameThread {

    private final PhysicsSystem physicsSystem;
    private TimeKeeper timeKeeper;
    private ComponentMapperSystem componentMapperSystem;

    protected GameThread(MainGame game) {

        timeKeeper = new TimeKeeper();

        componentMapperSystem = new ComponentMapperSystem();

        physicsSystem = new PhysicsSystem();
        game.getEngine().addSystem(physicsSystem);
    }

    public PhysicsSystem getPhysicsSystem() {
        return physicsSystem;
    }

    public TimeKeeper getTimeKeeper() {
        return timeKeeper;
    }

    public ComponentMapperSystem getComponentMapperSystem() {
        return componentMapperSystem;
    }
}
