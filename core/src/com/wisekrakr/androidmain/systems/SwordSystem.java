package com.wisekrakr.androidmain.systems;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.wisekrakr.androidmain.MainGame;
import com.wisekrakr.androidmain.GameConstants;
import com.wisekrakr.androidmain.components.*;
import com.wisekrakr.androidmain.components.objects.SwordComponent;

public class SwordSystem extends IteratingSystem implements SystemEntityContext {

    private MainGame game;

    public SwordSystem(MainGame game) {
        super(Family.all(com.wisekrakr.androidmain.components.objects.SwordComponent.class).get());
        this.game = game;
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        Box2dBodyComponent bodyComponent = game.getGameThread().getComponentMapperSystem().getBodyComponentMapper().get(entity);
        CollisionComponent collisionComponent = game.getGameThread().getComponentMapperSystem().getCollisionComponentMapper().get(entity);

        if (collisionComponent.hitEnemy){
            collisionComponent.setHitEnemy(false);
        }else if (collisionComponent.hitPlayer){
            collisionComponent.setHitPlayer(false);
        }

        outOfBounds(entity);
        bodyHandler(entity, bodyComponent);
    }

    @Override
    public void bodyHandler(Entity entity, Box2dBodyComponent bodyComponent) {
        com.wisekrakr.androidmain.components.objects.SwordComponent swordComponent = game.getGameThread().getComponentMapperSystem().getSwordComponentMapper().get(entity);

        swordComponent.setPosition(bodyComponent.body.getPosition());
        swordComponent.setVelocityX(bodyComponent.body.getLinearVelocity().x);
        swordComponent.setVelocityY(bodyComponent.body.getLinearVelocity().y);
        swordComponent.setDirection(bodyComponent.body.getAngle());

    }

    @Override
    public void destroy(Entity entity) {
        Box2dBodyComponent bodyComponent = game.getGameThread().getComponentMapperSystem().getBodyComponentMapper().get(entity);

        bodyComponent.isDead = true;
    }

    @Override
    public void outOfBounds(Entity entity) {
        SwordComponent swordComponent = game.getGameThread().getComponentMapperSystem().getSwordComponentMapper().get(entity);
        Box2dBodyComponent bodyComponent = game.getGameThread().getComponentMapperSystem().getBodyComponentMapper().get(entity);

        if (swordComponent.getPosition().x + swordComponent.getWidth()/2 > GameConstants.WORLD_WIDTH ||
                swordComponent.getPosition().x - swordComponent.getWidth()/2 < 0){
            bodyComponent.body.setLinearVelocity(-swordComponent.getVelocityX(), swordComponent.getVelocityY());
        }else if (swordComponent.getPosition().y + swordComponent.getHeight()/2 > GameConstants.WORLD_HEIGHT ||
                swordComponent.getPosition().y - swordComponent.getHeight()/2 < 0){
            bodyComponent.body.setLinearVelocity(swordComponent.getVelocityX(), -swordComponent.getVelocityY());
        }
    }
}
