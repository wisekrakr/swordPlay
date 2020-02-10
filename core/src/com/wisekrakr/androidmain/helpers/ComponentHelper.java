package com.wisekrakr.androidmain.helpers;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.PooledEngine;
import com.badlogic.gdx.math.Vector2;
import com.wisekrakr.androidmain.ComponentInitializer;
import com.wisekrakr.androidmain.components.*;
import com.wisekrakr.androidmain.components.objects.ObstacleComponent;

import java.util.List;

public class ComponentHelper {

    public static ComponentInitializer getComponentInitializer() {
        return componentInitializer;
    }

    private static ComponentInitializer componentInitializer = new ComponentInitializer() {


        @Override
        public void typeComponent(PooledEngine engine, Entity mainEntity, TypeComponent.Type type) {
            TypeComponent typeComponent = engine.createComponent(TypeComponent.class);

            typeComponent.setType(type);

            mainEntity.add(typeComponent);
        }

        @Override
        public void collisionComponent(PooledEngine engine, Entity mainEntity) {
            CollisionComponent collisionComponent = engine.createComponent(CollisionComponent.class);

            mainEntity.add(collisionComponent);
        }

        @Override
        public void transformComponent(PooledEngine engine, Entity mainEntity, float x, float y, float rotation) {
            TransformComponent transformComponent = engine.createComponent(TransformComponent.class);

            transformComponent.position.set(x, y, 0);
            transformComponent.rotation = rotation;

            mainEntity.add(transformComponent);
        }

        @Override
        public void textureComponent(PooledEngine engine, Entity mainEntity) {
            TextureComponent textureComponent = engine.createComponent(TextureComponent.class);

            mainEntity.add(textureComponent);
        }


        @Override
        public void obstacleComponent(PooledEngine engine, Entity mainEntity, float x, float y, float velocityX, float velocityY, float width, float height) {
            com.wisekrakr.androidmain.components.objects.ObstacleComponent obstacleComponent = engine.createComponent(ObstacleComponent.class);

            obstacleComponent.setWidth(width);
            obstacleComponent.setHeight(height);
            obstacleComponent.setVelocityX(velocityX);
            obstacleComponent.setVelocityY(velocityY);
            obstacleComponent.setPosition(new Vector2(x,y));

            mainEntity.add(obstacleComponent);
        }

        @Override
        public void powerUpComponent(PooledEngine engine, Entity mainEntity, float x, float y, float velocityX, float velocityY, float width, float height, PowerHelper.Power power) {
            com.wisekrakr.androidmain.components.objects.PowerUpComponent powerUpComponent = engine.createComponent(com.wisekrakr.androidmain.components.objects.PowerUpComponent.class);

            powerUpComponent.setWidth(width);
            powerUpComponent.setHeight(height);
            powerUpComponent.setVelocityX(velocityX);
            powerUpComponent.setVelocityY(velocityY);
            powerUpComponent.setPosition(new Vector2(x,y));
            PowerHelper.setPowerUp(mainEntity, power);

            mainEntity.add(powerUpComponent);
        }

        @Override
        public void swordComponent(PooledEngine engine, Entity mainEntity, Entity attachedEntity, float velocityX, float velocityY, float width, float height, float direction) {
            com.wisekrakr.androidmain.components.objects.SwordComponent swordComponent = engine.createComponent(com.wisekrakr.androidmain.components.objects.SwordComponent.class);

            swordComponent.setWidth(width);
            swordComponent.setHeight(height);
            swordComponent.setDirection(direction);
            swordComponent.setVelocityX(velocityX);
            swordComponent.setVelocityY(velocityY);
            swordComponent.setAttachedEntity(attachedEntity);

            mainEntity.add(swordComponent);
        }


        @Override
        public void shieldComponent(PooledEngine engine, Entity mainEntity, Entity attachedEntity, float velocityX, float velocityY, float radius) {
            com.wisekrakr.androidmain.components.objects.ShieldComponent shieldComponent = engine.createComponent(com.wisekrakr.androidmain.components.objects.ShieldComponent.class);

            shieldComponent.setRadius(radius);
            shieldComponent.setVelocityX(velocityX);
            shieldComponent.setVelocityY(velocityY);
            shieldComponent.setAttachedEntity(attachedEntity);

            mainEntity.add(shieldComponent);
        }



        @Override
        public void wallComponent(PooledEngine engine, Entity mainEntity, float x, float y, float width, float height) {
            WallComponent wallComponent = engine.createComponent(WallComponent.class);

            wallComponent.setPosition(new Vector2(x, y));
            wallComponent.setWidth(width);
            wallComponent.setHeight(height);

            mainEntity.add(wallComponent);
        }
    };

}
