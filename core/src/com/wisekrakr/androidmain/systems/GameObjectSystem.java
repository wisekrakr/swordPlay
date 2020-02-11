package com.wisekrakr.androidmain.systems;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.wisekrakr.androidmain.GameConstants;
import com.wisekrakr.androidmain.MainGame;
import com.wisekrakr.androidmain.components.Box2dBodyComponent;
import com.wisekrakr.androidmain.components.CollisionComponent;
import com.wisekrakr.androidmain.components.TypeComponent;
import com.wisekrakr.androidmain.components.objects.GameObject;
import com.wisekrakr.androidmain.components.objects.PlayerComponent;
import com.wisekrakr.androidmain.helpers.GameHelper;
import com.wisekrakr.androidmain.helpers.PowerHelper;
import com.wisekrakr.androidmain.retainers.ScoreKeeper;

public class GameObjectSystem extends IteratingSystem implements SystemEntityContext {
    private MainGame game;

    public GameObjectSystem(MainGame game) {
        super(Family.all(GameObject.class).get());
        this.game = game;
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        Box2dBodyComponent bodyComponent = game.getGameThread().getComponentMapperSystem().getBodyComponentMapper().get(entity);
        TypeComponent.Type type = entity.getComponent(TypeComponent.class).getType();

        if(type == TypeComponent.Type.POWER){
            CollisionComponent collisionComponent = game.getGameThread().getComponentMapperSystem().getCollisionComponentMapper().get(entity);

            if (collisionComponent.hitPlayer){
                powerHandler(entity);
                destroy(entity);
                ScoreKeeper.setInitialPowerUps(0);
            }
        }

        try {
            outOfBounds(entity);
            bodyHandler(entity, bodyComponent);

        }catch (Exception e){
            System.out.println(this.getClass() + " " + e + " " + type);

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

    private void powerHandler(Entity entity) {
        CollisionComponent collisionComponent = game.getGameThread().getComponentMapperSystem().getCollisionComponentMapper().get(entity);

        switch (PowerHelper.getPower()){
            case SPEED_BOOST:
                for (Entity enemy: game.getEngine().getEntities()) {
                    if (enemy.getComponent(TypeComponent.class).getType() == TypeComponent.Type.PLAYER) {
                        PlayerComponent playerComponent = game.getGameThread().getComponentMapperSystem().getPlayerComponentMapper().get(enemy);

                        playerComponent.setSpeed(playerComponent.getSpeed() * 2);
                    }
                }
                break;
            case SLOW_MO:
                for (Entity enemy: game.getEngine().getEntities()) {
                    if (enemy.getComponent(TypeComponent.class).getType() == TypeComponent.Type.PLAYER) {
                        PlayerComponent playerComponent = game.getGameThread().getComponentMapperSystem().getPlayerComponentMapper().get(enemy);

                        playerComponent.setSpeed(playerComponent.getSpeed() / 2);
                    }
                }
                break;
            case EXTRA_LIFE:
                ScoreKeeper.setLives(ScoreKeeper.lives + 1);
                break;
        }

        collisionComponent.setHitPlayer(false);
    }
}
