package com.wisekrakr.androidmain.systems;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.wisekrakr.androidmain.MainGame;
import com.wisekrakr.androidmain.GameConstants;
import com.wisekrakr.androidmain.components.Box2dBodyComponent;
import com.wisekrakr.androidmain.components.objects.ShieldComponent;

public class ShieldSystem extends IteratingSystem implements SystemEntityContext {

    private MainGame game;

    public ShieldSystem(MainGame game) {
        super(Family.all(ShieldComponent.class).get());
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
        ShieldComponent shieldComponent = game.getGameThread().getComponentMapperSystem().getShieldComponentMapper().get(entity);

        shieldComponent.setPosition(bodyComponent.body.getPosition());
        shieldComponent.setVelocityX(bodyComponent.body.getLinearVelocity().x);
        shieldComponent.setVelocityY(bodyComponent.body.getLinearVelocity().y);

    }

    @Override
    public void destroy(Entity entity) {
        Box2dBodyComponent bodyComponent = game.getGameThread().getComponentMapperSystem().getBodyComponentMapper().get(entity);

        bodyComponent.isDead = true;
    }

    @Override
    public void outOfBounds(Entity entity) {
        ShieldComponent shieldComponent = game.getGameThread().getComponentMapperSystem().getShieldComponentMapper().get(entity);
        Box2dBodyComponent bodyComponent = game.getGameThread().getComponentMapperSystem().getBodyComponentMapper().get(entity);

        if (shieldComponent.getPosition().x + shieldComponent.getRadius()/2 > GameConstants.WORLD_WIDTH ||
                shieldComponent.getPosition().x - shieldComponent.getRadius()/2 < 0){
            bodyComponent.body.setLinearVelocity(-shieldComponent.getVelocityX(), shieldComponent.getVelocityY());
        }else if (shieldComponent.getPosition().y + shieldComponent.getRadius()/2 > GameConstants.WORLD_HEIGHT ||
                shieldComponent.getPosition().y - shieldComponent.getRadius()/2 < 0){
            bodyComponent.body.setLinearVelocity(shieldComponent.getVelocityX(), -shieldComponent.getVelocityY());
        }
    }
}
