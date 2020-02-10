package com.wisekrakr.androidmain.components.objects;


import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Pool;
import com.wisekrakr.androidmain.components.EntityStyle;
import com.wisekrakr.androidmain.components.EntityStyleContext;

import java.util.ArrayList;
import java.util.List;

public class EnemyComponent extends GameObject implements  Pool.Poolable {

    private int bounces = 0;
    private float chaseInterval = 0;
    private EntityStyle entityStyle = null;

    public EntityStyleContext getEntityStyleContext() {
        return entityStyleContext;
    }

    private EntityStyleContext entityStyleContext = new EntityStyleContext() {

        @Override
        public EntityStyle getEntityStyle() {
            return entityStyle;
        }

        @Override
        public void setEntityStyle(EntityStyle style) {
            entityStyle = style;
        }

    };

    public float getChaseInterval() {
        return chaseInterval;
    }

    public void setChaseInterval(float chaseInterval) {
        this.chaseInterval = chaseInterval;
    }

    public int getBounces() {
        return bounces;
    }

    public void setBounces(int bounces) {
        this.bounces = bounces;
    }

    @Override
    public void reset() {
        this.setPosition(new Vector2());
        this.setVelocityX(0);
        this.setVelocityY(0);

        this.setWidth(0);
        this.setHeight(0);
        this.setDirection(0);

        this.setAttachedEntities(new ArrayList<Entity>());

        chaseInterval = 0;
        bounces = 0;

        this.setDestroy(false);
    }
}
