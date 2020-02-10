package com.wisekrakr.androidmain.systems;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.wisekrakr.androidmain.MainGame;
import com.wisekrakr.androidmain.GameConstants;
import com.wisekrakr.androidmain.components.*;
import com.wisekrakr.androidmain.components.objects.EnemyComponent;
import com.wisekrakr.androidmain.components.objects.PlayerComponent;
import com.wisekrakr.androidmain.helpers.EntityStyleHelper;
import com.wisekrakr.androidmain.helpers.GameHelper;
import com.wisekrakr.androidmain.retainers.ScoreKeeper;

import java.util.ArrayList;
import java.util.List;

public class EnemySystem extends IteratingSystem implements SystemEntityContext{

    private MainGame game;

    public EnemySystem(MainGame game){
        super(Family.all(EnemyComponent.class).get());
        this.game = game;

    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        EnemyComponent enemyComponent = game.getGameThread().getComponentMapperSystem().getEnemyComponentMapper().get(entity);
        Box2dBodyComponent bodyComponent = game.getGameThread().getComponentMapperSystem().getBodyComponentMapper().get(entity);
        CollisionComponent collisionComponent = game.getGameThread().getComponentMapperSystem().getCollisionComponentMapper().get(entity);

        enemyComponent.setSpeed(EntityStyleHelper.getStyle().getSwordSpeed());

        for (Entity ent: game.getEngine().getEntities()){
            if (ent.getComponent(TypeComponent.class).getType()== TypeComponent.Type.PLAYER && enemyComponent.getPosition() != null){

                float angle = GameHelper.angleBetween(enemyComponent.getPosition(), ent.getComponent(PlayerComponent.class).getPosition());
                enemyComponent.setDirection(angle);
                bodyComponent.body.setTransform(bodyComponent.body.getPosition(), angle);

                if (enemyComponent.getChaseInterval() == 0){
                    enemyComponent.setChaseInterval(game.getGameThread().getTimeKeeper().gameClock);
                }

                if (ent.getComponent(PlayerComponent.class).isMoving()) {

                    if (game.getGameThread().getTimeKeeper().gameClock - enemyComponent.getChaseInterval() > game.getGameThread().getTimeKeeper().getTimeToChase()) {
                        enemyComponent.setChaseInterval(game.getGameThread().getTimeKeeper().gameClock);

                        speedImpulse(enemyComponent, collisionComponent);

                        bodyComponent.body.setLinearVelocity(enemyComponent.getPosition().x * enemyComponent.getSpeed() * MathUtils.cos(enemyComponent.getDirection()),
                                enemyComponent.getPosition().y * enemyComponent.getSpeed() * MathUtils.sin(enemyComponent.getDirection())
                        );
                    }
                }else {
                    bodyComponent.body.setLinearVelocity(0,0);
                }
            }
        }


        if (!enemyComponent.isDestroy()){
            swordHandler(entity);
            if (collisionComponent.hitSword){
                enemyComponent.setDestroy(true);
                collisionComponent.setHitSword(false);
            }
        }else {
            destroy(entity);
        }

        try {
            outOfBounds(entity);
            bodyHandler(entity, bodyComponent);
        }catch (Exception e){
            System.out.println(this.getClass() + " "+ e);
        }

    }

    private void swordHandler(Entity entity){
        EnemyComponent enemyComponent = game.getGameThread().getComponentMapperSystem().getEnemyComponentMapper().get(entity);
        Box2dBodyComponent bodyComponent = game.getGameThread().getComponentMapperSystem().getBodyComponentMapper().get(entity);

        for (Entity ent: enemyComponent.getAttachedEntities()) {
            ent.getComponent(Box2dBodyComponent.class).body.setLinearVelocity(
                    bodyComponent.body.getLinearVelocity().x,
                    bodyComponent.body.getLinearVelocity().y
            );
        }
    }

    @Override
    public void bodyHandler(Entity entity, Box2dBodyComponent bodyComponent) {
        EnemyComponent enemyComponent = game.getGameThread().getComponentMapperSystem().getEnemyComponentMapper().get(entity);

        enemyComponent.setPosition(bodyComponent.body.getPosition());
        enemyComponent.setVelocityX(bodyComponent.body.getLinearVelocity().x);
        enemyComponent.setVelocityY(bodyComponent.body.getLinearVelocity().y);
        enemyComponent.setDirection(bodyComponent.body.getAngle());
    }

    @Override
    public void destroy(Entity entity){
        Box2dBodyComponent bodyComponent = game.getGameThread().getComponentMapperSystem().getBodyComponentMapper().get(entity);
        EnemyComponent enemyComponent = game.getGameThread().getComponentMapperSystem().getEnemyComponentMapper().get(entity);

        enemyComponent.setDestroy(false);

        for (Entity ent: enemyComponent.getAttachedEntities()){
            ent.getComponent(Box2dBodyComponent.class).isDead = true;
        }

        enemyComponent.getAttachedEntities().clear();

        bodyComponent.isDead = true;

        game.getGameThread().getLevelGenerationSystem().getLevelModel().setEnemies(ScoreKeeper.getInitialEnemies() - 1);
    }

    @Override
    public void outOfBounds(Entity entity){
        EnemyComponent enemyComponent = game.getGameThread().getComponentMapperSystem().getEnemyComponentMapper().get(entity);

        if (enemyComponent.getPosition().x - enemyComponent.getWidth()/2 > GameConstants.WORLD_WIDTH
                ||  enemyComponent.getPosition().x + enemyComponent.getWidth()/2 < 0 ||
                enemyComponent.getPosition().y - enemyComponent.getHeight()/2 > GameConstants.WORLD_HEIGHT ||
                enemyComponent.getPosition().y + enemyComponent.getHeight()/2 < 0){
            enemyComponent.setDestroy(true);
        }
    }

    private void speedImpulse(EnemyComponent enemyComponent, CollisionComponent collisionComponent){
        if (collisionComponent.hitSurface) {
            enemyComponent.setBounces(enemyComponent.getBounces() + 1);
            for (int i = enemyComponent.getBounces(); i>0; i--) {
                if (i % 5 == 0) {
                    enemyComponent.setSpeed(enemyComponent.getSpeed() + GameConstants.ENEMY_SPEED/5);
                }
            }
            collisionComponent.setHitSurface(false);
        }
    }
}
