package com.wisekrakr.androidmain.systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.wisekrakr.androidmain.MainGame;
import com.wisekrakr.androidmain.components.*;


public class CollisionSystem extends IteratingSystem {

    private MainGame game;

    @SuppressWarnings("unchecked")
    public CollisionSystem(MainGame game) {
        super(Family.all(CollisionComponent.class).get());
        this.game = game;

    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {

        CollisionComponent collisionComponent = game.getGameThread().getComponentMapperSystem().getCollisionComponentMapper().get(entity);

        Entity collidedEntity = collisionComponent.collisionEntity;

        TypeComponent thisType = ComponentMapper.getFor(TypeComponent.class).get(entity);

        if (thisType.getType().equals(TypeComponent.Type.ENEMY)){
            if (collidedEntity != null) {
                TypeComponent typeComponent = collidedEntity.getComponent(TypeComponent.class);
                if (typeComponent != null) {
                    switch (typeComponent.getType()) {
                        case SCENERY:
                            collisionComponent.setHitSurface(true);
                            break;
                        case ENEMY:
                            collisionComponent.setHitEnemy(true);
                            break;
                        case OBSTACLE:
                            collisionComponent.setHitObstacle(true);
                            break;
                        case PLAYER:
                            collisionComponent.setHitPlayer(true);
                            collidedEntity.getComponent(CollisionComponent.class).setHitEnemy(true);
                            break;
                        case SWORD:
                            collisionComponent.setHitSword(true);
                            collidedEntity.getComponent(CollisionComponent.class).setHitEnemy(true);
                            break;
                    }
                    collisionComponent.collisionEntity = null;
                }
            }
        }
        if (thisType.getType().equals(TypeComponent.Type.POWER)) {
            if (collidedEntity != null) {
                TypeComponent typeComponent = collidedEntity.getComponent(TypeComponent.class);
                if (typeComponent != null) {
                    switch (typeComponent.getType()) {
                        case SCENERY:
                            collisionComponent.setHitSurface(true);
                            break;
                        case ENEMY:
                            collisionComponent.setHitEnemy(true);
                            break;
                        case OBSTACLE:
                            collisionComponent.setHitObstacle(true);
                            break;
                        case PLAYER:
                            collisionComponent.setHitPlayer(true);
                            collidedEntity.getComponent(CollisionComponent.class).setHitPower(true);
                            break;
                        case POWER:
                            collisionComponent.setHitPower(true);
                            break;
                        case SWORD:
                            collisionComponent.setHitSword(true);
                            break;
                    }
                    collisionComponent.collisionEntity = null;
                }
            }
        }
        if (thisType.getType().equals(TypeComponent.Type.PLAYER)) {
            if (collidedEntity != null) {
                TypeComponent typeComponent = collidedEntity.getComponent(TypeComponent.class);
                if (typeComponent != null) {
                    switch (typeComponent.getType()) {
                        case SCENERY:
                            collisionComponent.setHitSurface(true);
                            break;
                        case ENEMY:
                            collisionComponent.setHitEnemy(true);
                            collidedEntity.getComponent(CollisionComponent.class).setHitPlayer(true);
                            break;
                        case OBSTACLE:
                            collisionComponent.setHitObstacle(true);
                            break;
                        case POWER:
                            collisionComponent.setHitPower(true);
                            collidedEntity.getComponent(CollisionComponent.class).setHitPlayer(true);
                            break;
                        case SWORD:
                            collisionComponent.setHitSword(true);
                            collidedEntity.getComponent(CollisionComponent.class).setHitPlayer(true);
                            break;
                    }
                    collisionComponent.collisionEntity = null;
                }
            }
        }
        if (thisType.getType().equals(TypeComponent.Type.SWORD)) {
            if (collidedEntity != null) {
                TypeComponent typeComponent = collidedEntity.getComponent(TypeComponent.class);
                if (typeComponent != null) {
                    switch (typeComponent.getType()){
                        case PLAYER:
                            collisionComponent.setHitPlayer(true);
                            collidedEntity.getComponent(CollisionComponent.class).setHitSword(true);
                            break;
                        case ENEMY:
                            collisionComponent.setHitEnemy(true);
                            collidedEntity.getComponent(CollisionComponent.class).setHitSword(true);
                            break;
                        case SWORD:
                            collisionComponent.setHitSword(true);
                            collidedEntity.getComponent(CollisionComponent.class).setHitSword(true);
                            break;
                    }
                    collisionComponent.collisionEntity = null;
                }
            }
        }
    }
}
