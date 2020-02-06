package com.wisekrakr.androidmain.components.objects;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Pool;
import com.wisekrakr.androidmain.components.EntityStyle;
import com.wisekrakr.androidmain.components.EntityStyleContext;

import java.util.ArrayList;

public class PlayerComponent extends GameObject implements  Pool.Poolable {

    private boolean isMoving = false;
    private float swordLength;
    private float swordGirth;

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

    public boolean isMoving() {
        return isMoving;
    }

    public void setMoving(boolean moving) {
        isMoving = moving;
    }

    public float getSwordLength() {
        return swordLength;
    }

    public void setSwordLength(float swordLength) {
        this.swordLength = swordLength;
    }

    public float getSwordGirth() {
        return swordGirth;
    }

    public void setSwordGirth(float swordGirth) {
        this.swordGirth = swordGirth;
    }

    @Override
    public void reset() {
        this.setPosition(new Vector2());
        this.setVelocityX(0);
        this.setVelocityY(0);

        this.setWidth(0);
        this.setHeight(0);
        this.setDirection(0);

        this.setSwordGirth(0);
        this.setSwordLength(0);

        this.setAttachedEntities(new ArrayList<Entity>());

        this.setDestroy(false);

        isMoving = false;
    }
}
