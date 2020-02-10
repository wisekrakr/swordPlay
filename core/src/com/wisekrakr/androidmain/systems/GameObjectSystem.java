package com.wisekrakr.androidmain.systems;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.wisekrakr.androidmain.GameConstants;
import com.wisekrakr.androidmain.MainGame;
import com.wisekrakr.androidmain.components.Box2dBodyComponent;
import com.wisekrakr.androidmain.components.CollisionComponent;
import com.wisekrakr.androidmain.components.objects.SwordComponent;

public class GameObjectSystem extends IteratingSystem implements SystemEntityContext {
    private MainGame game;

    public GameObjectSystem(MainGame game) {
        super(Family.all(com.wisekrakr.androidmain.components.objects.GameObject.class).get());
        this.game = game;
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        Box2dBodyComponent bodyComponent = game.getGameThread().getComponentMapperSystem().getBodyComponentMapper().get(entity);

        outOfBounds(entity);
        bodyHandler(entity, bodyComponent);
    }

    @Override
    public void bodyHandler(Entity entity, Box2dBodyComponent bodyComponent) {
        com.wisekrakr.androidmain.components.objects.GameObject objectComponent = game.getGameThread().getComponentMapperSystem().getObjectComponentMapper().get(entity);

        objectComponent.setPosition(bodyComponent.body.getPosition());
        objectComponent.setVelocityX(bodyComponent.body.getLinearVelocity().x);
        objectComponent.setVelocityY(bodyComponent.body.getLinearVelocity().y);
        objectComponent.setDirection(bodyComponent.body.getAngle());

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
