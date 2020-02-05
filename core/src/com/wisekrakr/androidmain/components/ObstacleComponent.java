package com.wisekrakr.androidmain.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Pool;


public class ObstacleComponent implements Component, Pool.Poolable{

    private Vector2 position = new Vector2();
    private float velocityX = 0;
    private float velocityY = 0;
    private boolean destroy;
    private float width = 0;
    private float height = 0;
    private float direction = 0;

    private boolean outOfBoundsX = false;
    private boolean outOfBoundsY = false;

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

    public boolean isOutOfBoundsX() {
        return outOfBoundsX;
    }

    public void setOutOfBoundsX(boolean outOfBoundsX) {
        this.outOfBoundsX = outOfBoundsX;
    }

    public boolean isOutOfBoundsY() {
        return outOfBoundsY;
    }

    public void setOutOfBoundsY(boolean outOfBoundsY) {
        this.outOfBoundsY = outOfBoundsY;
    }

    @Override
    public void reset() {
        position = new Vector2();
        velocityX = 0;
        velocityY = 0;
        destroy = false;

        width = 0;
        height = 0;

        direction = 0;

        outOfBoundsX = false;
        outOfBoundsY = false;
    }
}
