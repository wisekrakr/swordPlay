package com.wisekrakr.androidmain.systems;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.wisekrakr.androidmain.GameConstants;
import com.wisekrakr.androidmain.MainGame;
import com.wisekrakr.androidmain.components.Box2dBodyComponent;
import com.wisekrakr.androidmain.components.objects.GameObject;

public class GameObjectSystem extends IteratingSystem implements SystemEntityContext {
    private MainGame game;

    public GameObjectSystem(MainGame game) {
        super(Family.all(GameObject.class).get());
        this.game = game;
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        Box2dBodyComponent bodyComponent = game.getGameThread().getComponentMapperSystem().getBodyComponentMapper().get(entity);

        try {
            outOfBounds(entity);
            bodyHandler(entity, bodyComponent);
        }catch (Exception e){
            System.out.println(this.getClass() + " "+ e);
        }
    }

    @Override
    public void bodyHandler(Entity entity, Box2dBodyComponent bodyComponent) {
        GameObject objectComponent = game.getGameThread().getComponentMapperSystem().getObjectComponentMapper().get(entity);

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
        GameObject gameObjectComponent = game.getGameThread().getComponentMapperSystem().getObjectComponentMapper().get(entity);
        Box2dBodyComponent bodyComponent = game.getGameThread().getComponentMapperSystem().getBodyComponentMapper().get(entity);

        if (gameObjectComponent.getPosition().x + gameObjectComponent.getWidth()/2 > GameConstants.WORLD_WIDTH ||
                gameObjectComponent.getPosition().x - gameObjectComponent.getWidth()/2 < 0){
            bodyComponent.body.setLinearVelocity(-gameObjectComponent.getVelocityX(), gameObjectComponent.getVelocityY());
        }else if (gameObjectComponent.getPosition().y + gameObjectComponent.getHeight()/2 > GameConstants.WORLD_HEIGHT ||
                gameObjectComponent.getPosition().y - gameObjectComponent.getHeight()/2 < 0){
            bodyComponent.body.setLinearVelocity(gameObjectComponent.getVelocityX(), -gameObjectComponent.getVelocityY());
        }
    }
}
