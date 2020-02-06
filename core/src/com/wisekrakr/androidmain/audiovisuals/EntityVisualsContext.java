package com.wisekrakr.androidmain.audiovisuals;

import com.badlogic.ashley.core.Entity;

public interface EntityVisualsContext {

    void visualizeEntity(Entity entity);
    void drawObjectViaAtlas(Entity entity, String atlasPath, String regionPath, float width, float height);

}
