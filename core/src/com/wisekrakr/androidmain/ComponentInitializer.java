package com.wisekrakr.androidmain;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.PooledEngine;
import com.wisekrakr.androidmain.components.EntityStyle;
import com.wisekrakr.androidmain.components.TypeComponent;
import com.wisekrakr.androidmain.helpers.PowerHelper;

import java.util.List;

public interface ComponentInitializer {

    void typeComponent(PooledEngine engine, Entity mainEntity, TypeComponent.Type type);
    void collisionComponent(PooledEngine engine, Entity mainEntity);
    void transformComponent(PooledEngine engine, Entity mainEntity, float x, float y, float rotation);
    void textureComponent(PooledEngine engine, Entity mainEntity);
    void powerUpComponent(PooledEngine engine, Entity mainEntity, float x, float y, float velocityX, float velocityY, float width, float height, PowerHelper.Power power);
}
