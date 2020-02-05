package com.wisekrakr.androidmain.components;

import com.badlogic.ashley.core.Component;


import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Pool;

import java.util.ArrayList;
import java.util.List;

public class EnemyComponent implements Component, Pool.Poolable {
    private Vector2 position = new Vector2();
    private float velocityX = 0;
    private float velocityY = 0;
    private boolean destroy;
    private float width = 0;
    private float height = 0;
    private float direction = 0;
    private float penisLength = 0;
    private float penisGirth = 0;

    private Vector2 initialPosition = new Vector2();
    private int bounces = 0;

    private float speed;
    public void setSpeed(float speed) {
        this.speed = speed;
    }

    public List<Vector2>initialPositions = new ArrayList<Vector2>();

    public float chaseInterval = 0;

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

    public Vector2 getPosition() {
        return position;
    }

    public void setPosition(Vector2 position) {
        this.position = position;
    }

    public Vector2 getInitialPosition() {
        return initialPosition;
    }

    public void setInitialPosition(Vector2 position) {
        this.initialPosition = position;
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

    public int getBounces() {
        return bounces;
    }

    public void setBounces(int bounces) {
        this.bounces = bounces;
    }

    public float getSpeed() {
        return speed;
    }

    public float getPenisLength() {
        return penisLength;
    }

    public void setPenisLength(float penisLength) {
        this.penisLength = penisLength;
    }

    public float getPenisGirth() {
        return penisGirth;
    }

    public void setPenisGirth(float penisGirth) {
        this.penisGirth = penisGirth;
    }

    private List<Entity> attachedEntities;

    public List<Entity> getAttachedEntities() {
        return attachedEntities;
    }

    public void setAttachedEntities(List<Entity> attachedEntities) {
        this.attachedEntities = attachedEntities;
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

        initialPositions = new ArrayList<Vector2>();
        chaseInterval = 0;

        penisLength = 0;
        penisGirth = 0;

        bounces = 0;

        attachedEntities = new ArrayList<Entity>();
    }
}