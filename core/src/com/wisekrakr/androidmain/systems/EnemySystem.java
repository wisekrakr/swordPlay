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
import com.wisekrakr.androidmain.helpers.EntityStyleHelper;
import com.wisekrakr.androidmain.helpers.GameHelper;

import java.util.ArrayList;
import java.util.List;

public class EnemySystem extends IteratingSystem implements SystemEntityContext{

    private MainGame game;

    private List<Vector2>savedPositions = new ArrayList<Vector2>();

    public EnemySystem(MainGame game){
        super(Family.all(com.wisekrakr.androidmain.components.objects.EnemyComponent.class).get());

        this.game = game;
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        com.wisekrakr.androidmain.components.objects.EnemyComponent enemyComponent = game.getGameThread().getComponentMapperSystem().getEnemyComponentMapper().get(entity);
        Box2dBodyComponent bodyComponent = game.getGameThread().getComponentMapperSystem().getBodyComponentMapper().get(entity);
        CollisionComponent collisionComponent = game.getGameThread().getComponentMapperSystem().getCollisionComponentMapper().get(entity);

        savedPositions = enemyComponent.initialPositions;

        enemyComponent.setSpeed(EntityStyleHelper.getStyle().getSwordSpeed());

        for (Entity ent: game.getEngine().getEntities()){
            if (ent.getComponent(TypeComponent.class).getType()== TypeComponent.Type.PLAYER && enemyComponent.getPosition() != null){

                float angle = GameHelper.angleBetween(enemyComponent.getPosition(), ent.getComponent(com.wisekrakr.androidmain.components.objects.PlayerComponent.class).getPosition());
                enemyComponent.setDirection(angle);
                bodyComponent.body.setTransform(bodyComponent.body.getPosition(), angle);

                if (enemyComponent.chaseInterval == 0){
                    enemyComponent.chaseInterval = game.getGameThread().getTimeKeeper().gameClock;
                }

                if (ent.getComponent(com.wisekrakr.androidmain.components.objects.PlayerComponent.class).isMoving()) {
                    if (game.getGameThread().getTimeKeeper().gameClock - enemyComponent.chaseInterval > game.getGameThread().getTimeKeeper().getTimeToChase()) {
                        enemyComponent.chaseInterval = game.getGameThread().getTimeKeeper().gameClock;

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

        float length = enemyComponent.getSwordLength();
        float girth = enemyComponent.getSwordGirth();
        EntityStyle style = enemyComponent.getEntityStyleContext().getEntityStyle();

        if (!enemyComponent.isDestroy()){
            swordHandler(entity);
            if (collisionComponent.hitSword){
                enemyComponent.setDestroy(true);
                collisionComponent.setHitSword(false);
            }
        }else {
            spawnEnemyAtPoint(style, length, girth);
            destroy(entity);
        }

        try {
            outOfBounds(entity);
            bodyHandler(entity, bodyComponent);
        }catch (Exception e){
            System.out.println(this.getClass() + " "+ e);
        }


//        System.out.println("enemy: " +enemyComponent.getAttachedEntities().size()); //todo remove

    }

    private void swordHandler(Entity entity){
        com.wisekrakr.androidmain.components.objects.EnemyComponent enemyComponent = game.getGameThread().getComponentMapperSystem().getEnemyComponentMapper().get(entity);
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
        com.wisekrakr.androidmain.components.objects.EnemyComponent enemyComponent = game.getGameThread().getComponentMapperSystem().getEnemyComponentMapper().get(entity);

        enemyComponent.setPosition(bodyComponent.body.getPosition());
        enemyComponent.setVelocityX(bodyComponent.body.getLinearVelocity().x);
        enemyComponent.setVelocityY(bodyComponent.body.getLinearVelocity().y);
        enemyComponent.setDirection(bodyComponent.body.getAngle());
    }

    @Override
    public void destroy(Entity entity){
        Box2dBodyComponent bodyComponent = game.getGameThread().getComponentMapperSystem().getBodyComponentMapper().get(entity);
        com.wisekrakr.androidmain.components.objects.EnemyComponent enemyComponent = game.getGameThread().getComponentMapperSystem().getEnemyComponentMapper().get(entity);

        enemyComponent.setDestroy(false);

        for (Entity ent: enemyComponent.getAttachedEntities()){
            ent.getComponent(Box2dBodyComponent.class).isDead = true;
        }

        enemyComponent.getAttachedEntities().clear();

        bodyComponent.isDead = true;
    }

    @Override
    public void outOfBounds(Entity entity){
        com.wisekrakr.androidmain.components.objects.EnemyComponent enemyComponent = game.getGameThread().getComponentMapperSystem().getEnemyComponentMapper().get(entity);

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

    private void spawnEnemyAtPoint(EntityStyle style, float length, float girth){

        for (int i = savedPositions.size(); i>0; i--) {
            game.getGameThread().getEntityFactory().createEnemy(
                    savedPositions.get(i-1).x,
                    savedPositions.get(i-1).y,
                    style, length, girth
            );
        }
    }
}
