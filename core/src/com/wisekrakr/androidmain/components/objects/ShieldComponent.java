package com.wisekrakr.androidmain.components.objects;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Pool;


public class ShieldComponent extends GameObject implements  Pool.Poolable{

    private float radius = 0;

    private Entity attachedEntity;

    public Entity getAttachedEntity() {
        return attachedEntity;
    }

    public void setAttachedEntity(Entity attachedEntity) {
        this.attachedEntity = attachedEntity;
    }

    public float getRadius() {
        return radius;
    }

    public void setRadius(float radius) {
        this.radius = radius;
    }



    @Override
    public void reset() {
        this.setPosition(new Vector2());
        this.setVelocityX(0);
        this.setVelocityY(0);

        this.setDestroy(false);

        radius = 0;

        attachedEntity = null;
    }
}
