package com.wisekrakr.androidmain.components.objects;

import com.badlogic.ashley.core.Component;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Pool;

import java.util.List;

public class GameObject implements Component, Pool.Poolable {
    private Vector2 position;
    private float velocityX;
    private float velocityY;
    private boolean destroy;
    private List<Entity> attachedEntities;
    private float width;
    private float height;
    private float direction;
    private float speed;

    @Override
    public void reset() {
        this.setPosition(new Vector2());
        this.setVelocityX(0);
        this.setVelocityY(0);

        this.setWidth(0);
        this.setHeight(0);
        this.setDirection(0);

        this.setDestroy(false);

    }


    public Vector2 getPosition() {
        return position;
    }

    public void setPosition(Vector2 position) {
        this.position = position;
    }


    public float getVelocityX() {
        return velocityX;
    }

    public void setVelocityX(float velocityX) {
        this.velocityX = velocityX;
    }

    public float getVelocityY() {
        return velocityY;
    }

    public void setVelocityY(float velocityY) {
        this.velocityY = velocityY;
    }

    public boolean isDestroy() {
        return destroy;
    }

    public void setDestroy(boolean destroy) {
        this.destroy = destroy;
    }

    public float getWidth() {
        return width;
    }

    public void setWidth(float width) {
        this.width = width;
    }

    public float getHeight() {
        return height;
    }

    public void setHeight(float height) {
        this.height = height;
    }

    public float getDirection() {
        return direction;
    }

    public void setDirection(float direction) {
        this.direction = direction;
    }

    public void setSpeed(float speed) {
        this.speed = speed;
    }

    public float getSpeed() {
        return speed;
    }

    public List<Entity> getAttachedEntities() {
        return attachedEntities;
    }

    public void setAttachedEntities(List<Entity> attachedEntities) {
        this.attachedEntities = attachedEntities;
    }

}
